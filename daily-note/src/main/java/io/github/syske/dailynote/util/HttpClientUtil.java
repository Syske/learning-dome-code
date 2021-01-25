package io.github.syske.dailynote.util;

/**
 * @program: public-server
 * @description: http工具类
 * @author: syske
 * @create: 2020-03-16 14:00
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;
import java.util.Map.Entry;

/**
 * http请求工具类
 *
 * @author liujiong
 */
public class HttpClientUtil {

    private Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static PoolingHttpClientConnectionManager pcm;//httpclient连接池
    private CloseableHttpClient httpClient = null; //http连接
    private int connectTimeout = 120000;//连接超时时间
    private int connectionRequestTimeout = 10000;//从连接池获取连接超时时间
    private int socketTimeout = 300000;//获取数据超时时间
    private String charset = "utf-8";
    private RequestConfig requestConfig = null;//请求配置
    private Builder requestConfigBuilder = null;//build requestConfig

    private List<NameValuePair> nvps = new ArrayList<>();
    private List<Header> headers = new ArrayList<>();
    private String requestParam = "";

    static {
        pcm = new PoolingHttpClientConnectionManager();
        pcm.setMaxTotal(50);//整个连接池最大连接数
        pcm.setDefaultMaxPerRoute(50);//每路由最大连接数，默认值是2
    }

    /**
     * 默认设置
     *
     * @author Liu Jiong
     * @createDate 2016年10月30日
     */
    private static HttpClientUtil defaultInit() {
        HttpClientUtil httpUtil = new HttpClientUtil();
        if (httpUtil.requestConfig == null) {
            httpUtil.requestConfigBuilder = RequestConfig.custom().setConnectTimeout(httpUtil.connectTimeout)
                    .setConnectionRequestTimeout(httpUtil.connectionRequestTimeout)
                    .setSocketTimeout(httpUtil.socketTimeout);
            httpUtil.requestConfig = httpUtil.requestConfigBuilder.build();
        }
        return httpUtil;
    }

    /**
     * 初始化 httpUtil
     */
    public static HttpClientUtil init() {
        HttpClientUtil httpUtil = defaultInit();
        if (httpUtil.httpClient == null) {
            httpUtil.httpClient = HttpClients.custom().setConnectionManager(pcm).build();
        }
        return httpUtil;
    }

    /**
     * 初始化 httpUtil
     */
    public static HttpClientUtil init(Map<String, String> paramMap) {
        HttpClientUtil httpUtil = init();
        httpUtil.setParamMap(paramMap);
        return httpUtil;
    }

    /**
     * 验证初始化
     */
    public static HttpClientUtil initWithAuth(String ip, int port, String username, String password) {
        HttpClientUtil httpUtil = defaultInit();
        if (httpUtil.httpClient == null) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(ip, port, AuthScope.ANY_REALM), new UsernamePasswordCredentials(username, password));
            httpUtil.httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider)
                    .setConnectionManager(pcm).build();
        }
        return httpUtil;
    }

    /**
     * 设置请求头
     */
    public HttpClientUtil setHeader(String name, String value) {
        Header header = new BasicHeader(name, value);
        headers.add(header);
        return this;
    }

    /**
     * 设置请求头
     */
    public HttpClientUtil setHeaderMap(Map<String, String> headerMap) {
        for (Entry<String, String> param : headerMap.entrySet()) {
            Header header = new BasicHeader(param.getKey(), param.getValue());
            headers.add(header);
        }
        return this;
    }

    /**
     * 设置请求参数
     */
    public HttpClientUtil setParam(String name, String value) {
        nvps.add(new BasicNameValuePair(name, value));
        return this;
    }

    /**
     * 设置请求参数
     */
    public HttpClientUtil setParamMap(Map<String, String> paramMap) {
        for (Entry<String, String> param : paramMap.entrySet()) {
            nvps.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        return this;
    }

    /**
     * 设置字符串参数
     */
    public HttpClientUtil setStringParam(String requestParam) {
        this.requestParam = requestParam;
        return this;
    }

    /**
     * 设置连接超时时间
     */
    public HttpClientUtil setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        this.requestConfigBuilder = requestConfigBuilder.setConnectTimeout(connectTimeout);
        requestConfig = requestConfigBuilder.build();
        return this;
    }

    /**
     * http get 请求
     */
    public Map<String, String> get(String url) {
        Map<String, String> resultMap = new HashMap<>();
        //获取请求URI
        URI uri = getUri(url);
        if (uri != null) {
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            if (!CollectionUtils.isEmpty(headers)) {
                Header[] header = new Header[headers.size()];
                httpGet.setHeaders(headers.toArray(header));
            }

            //执行get请求
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                return getHttpResult(response, url, httpGet, resultMap);
            } catch (Exception e) {
                httpGet.abort();
                resultMap.put("result", e.getMessage());
                logger.error("获取http GET请求返回值失败 url======" + url, e);
            }
        }
        return resultMap;
    }

    /**
     * http post 请求
     */
    public Map<String, String> post(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (!CollectionUtils.isEmpty(headers)) {
            Header[] header = new Header[headers.size()];
            httpPost.setHeaders(headers.toArray(header));
        }
        if (!CollectionUtils.isEmpty(nvps)) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
            } catch (UnsupportedEncodingException e) {
                logger.error("http post entity form error", e);
            }
        }
        if (!StringUtils.isEmpty(requestParam)) {
            try {
                httpPost.setEntity(new StringEntity(requestParam, charset));
            } catch (UnsupportedCharsetException e) {
                logger.error("http post entity form error", e);
            }
        }
        Map<String, String> resultMap = new HashMap<>();
        //执行post请求
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return getHttpResult(response, url, httpPost, resultMap);
        } catch (Exception e) {
            httpPost.abort();
            resultMap.put("result", e.getMessage());
            logger.error("获取http POST请求返回值失败 url======" + url, e);
        }
        return resultMap;
    }

    /**
     * post 上传文件
     */
    public Map<String, String> postUploadFile(String url, Map<String, File> fileParam) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (!CollectionUtils.isEmpty(headers)) {
            Header[] header = new Header[headers.size()];
            httpPost.setHeaders(headers.toArray(header));
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (fileParam != null) {
            for (Entry<String, File> entry : fileParam.entrySet()) {
                //将要上传的文件转化为文件流
                FileBody fileBody = new FileBody(entry.getValue());
                //设置请求参数
                builder.addPart(entry.getKey(), fileBody);
            }
        }

        if (!CollectionUtils.isEmpty(nvps)) {
            for (NameValuePair nvp : nvps) {
                String value = nvp.getValue();
                if (!StringUtils.isEmpty(value)) {
                    builder.addTextBody(nvp.getName(), value, ContentType.create("text/plain", charset));
                }
            }
        }
        httpPost.setEntity(builder.build());
        Map<String, String> resultMap = new HashMap<>();
        //执行post请求
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return getHttpResult(response, url, httpPost, resultMap);
        } catch (Exception e) {
            httpPost.abort();
            resultMap.put("result", e.getMessage());
            logger.error("获取http postUploadFile 请求返回值失败 url======" + url, e);
        }
        return resultMap;
    }

    /**
     * 获取请求返回值
     */
    private Map<String, String> getHttpResult(CloseableHttpResponse response, String url, HttpUriRequest request, Map<String, String> resultMap) {
        String result = "";
        int statusCode = response.getStatusLine().getStatusCode();
        resultMap.put("statusCode", statusCode + "");
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                result = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);//释放连接
            } catch (Exception e) {
                logger.error("获取http请求返回值解析失败", e);
                request.abort();
            }
        }
        if (statusCode != 200) {
            result = "HttpClient status code :" + statusCode + "  request url===" + url;
            logger.info("HttpClient status code :" + statusCode + "  request url===" + url);
            request.abort();
        }
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 获取重定向url返回的location
     */
    public String redirectLocation(String url) {
        String location = "";
        //获取请求URI
        URI uri = getUri(url);
        if (uri != null) {
            HttpGet httpGet = new HttpGet(uri);
            requestConfig = requestConfigBuilder.setRedirectsEnabled(false).build();//设置自动重定向false
            httpGet.setConfig(requestConfig);
            if (!CollectionUtils.isEmpty(headers)) {
                Header[] header = new Header[headers.size()];
                httpGet.setHeaders(headers.toArray(header));
            }

            try {
                //执行get请求
                CloseableHttpResponse response = httpClient.execute(httpGet);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {//301 302
                    Header header = response.getFirstHeader("Location");
                    if (header != null) {
                        location = header.getValue();
                    }
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    EntityUtils.consume(entity);
                }
            } catch (Exception e) {
                logger.error("获取http GET请求获取 302 Location失败 url======" + url, e);
                httpGet.abort();
            }
        }
        return location;
    }

    /**
     * 获取输入流
     */
    public InputStream getInputStream(String url) {
        //获取请求URI
        URI uri = getUri(url);
        if (uri != null) {
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            if (!CollectionUtils.isEmpty(headers)) {
                Header[] header = new Header[headers.size()];
                httpGet.setHeaders(headers.toArray(header));
            }
            //执行get请求
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    logger.info("HttpClient status code :" + statusCode + "  request url===" + url);
                    httpGet.abort();
                } else {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        InputStream in = entity.getContent();
                        return in;
                    }
                }
            } catch (Exception e) {
                logger.error("获取http GET inputStream请求失败 url======" + url, e);
                httpGet.abort();
            }
        }
        return null;
    }

    private URI getUri(String url) {
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (!CollectionUtils.isEmpty(nvps)) {
                uriBuilder.setParameters(nvps);
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            logger.error("url 地址异常", e);
        }
        return uri;
    }


    /**
     * from请求
     * @param url
     * @param params
     * @return
     */
    public static String form(String url, Map<String, String> params) {
        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            //// POST 只能为大写，严格限制，post会不识别
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            //一定要有返回值，否则无法把请求发送给server端。
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public static void main(String[] args) {
        /*Map<String, String> map = new HashMap<>();
        JSONObject param = new JSONObject();
        param.put("activity_id","60:video:comets:10011#2");
        param.put("state",3);
        param.put("attr_tags","");
        param.put("msg","");
        map.put("param",param.toJSONString());
        String form = form("http://yp5ntd.natappfree.cc/CnInteraction/services/commentForHd/auditCallBackNew", map);
        System.out.println(form);*/


        JSONObject params = new JSONObject();
        params.put("video_id","60_8be004799c424688949704814ea0d16d");
        params.put("state",3+"");
        params.put("attr_tags","");
        params.put("msg","");
        HttpClientUtil httpUtil = HttpClientUtil.init();
        httpUtil.setParam("param",params.toJSONString());
        String URL ="http://abcd.com/v1/ShenHeInfo/shenheNotify?platform=AUDIT&token=94cead75c";
        Map<String, String> post = httpUtil.post(URL);
        System.out.println(post.get("result"));
    }

    /**
     * POST请求 默认是 UTF-8 编码
     * post("http://xxx.com/search", "a=bcd", "b=xxx");
     * @param url
     * @param params 请单个编写，内部会自动将等号后字符编码
     * @return
     */
    public static String post(String url, String... params){
        return post(url, Charset.forName("UTF-8"), params);
    }

    /**
     * POST请求 默认是 UTF-8 编码
     * post("http://xxx.com/search", "a=bcd", "b=xxx");
     * @param url
     * @param params 请单个编写，内部会自动将等号后字符编码
     * @return
     */
    public static String post(String url, Charset charset, String... params) {
        PrintWriter out = null;
        InputStream in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(300000);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());
            out.print(encodeParams(charset, params));
            out.flush();

            in = conn.getInputStream();
            result = readInputStream(charset, in);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                try {
                    if (out != null) {
                        out.close();
                    }
                } finally {
                    if (in != null) {
                        in.close();
                    }
                }
            } catch (Exception ex) {
            }
        }
        return result;
    }


    private static String readInputStream(Charset charset, InputStream in)
            throws IOException {
        byte[] bytes = new byte[1024];
        int length = -1;
        ByteArrayOutputStream byteOutput= new ByteArrayOutputStream();
        while ((length = in.read(bytes)) != -1) {
            byteOutput.write(bytes, 0, length);
        }
        return new String(byteOutput.toByteArray(), charset);
    }

    public static String encodeParams(String... params){
        return encodeParams(Charset.forName("UTF-8"), params);
    }

    /**
     * 将等号后面的字符串编码
     * @param params
     * @return
     */
    private static String encodeParams(Charset charset, String... params){
        if(params == null || params.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(String str : params) {
            int index = str.indexOf("=");
            if(index == -1) {
                sb.append(str);
            }else {
                try {
                    sb.append(str.substring(0, index)).append("=").append(URLEncoder.encode(str.substring(index + 1), charset.name()));
                } catch (UnsupportedEncodingException e) {//肯定不会出现
                }
            }

            sb.append("&");
        }
        if(sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public String doPost(String url, String paramString) throws Exception {

        return this.doPost(url, paramString, null);
    }


    /**
     * 发送 post请求
     * @param url
     * @param paramString 请求参数
     * @param headerMap 请求头
     * @return
     * @throws Exception
     */
    public String doPost(String url, String paramString, Map<String, String> headerMap) throws Exception {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CookieStore cookieStore = new BasicCookieStore();

        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(paramString, "UTF-8");

        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        if(headerMap != null) {
            Set<String> keys = headerMap.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                //httpPost.setHeader(iterator.next(), headerMap.get(iterator.next()));
                String next = iterator.next();
                httpPost.addHeader(next, headerMap.get(next));
            }
        }

        // 响应模型
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            logger.debug("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                responseContent = EntityUtils.toString(responseEntity, "UTF-8");
                logger.debug("响应内容长度为:" + responseEntity.getContentLength());
                logger.debug("响应内容为:" + responseContent);

            }
        } catch (ClientProtocolException e) {
            logger.error("后端系统调用异常", e);
            throw new Exception("后端系统调用异常", e);
        } catch (ParseException e) {
            logger.error("后端系统调用异常", e);
            throw new Exception("后端系统调用异常", e);
        } catch (IOException e) {
            logger.error("后端系统调用异常", e);
            throw new Exception("后端系统调用异常", e);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("后端系统调用异常", e);
                throw new Exception("后端系统调用异常", e);
            }
        }
        return responseContent;
    }



    /**
     * 发送get请求
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public String doGet(String url, String param) throws Exception{
        String result = "";
        BufferedReader in = null;
        try {
            String urlstring = url + "?" + param;
            URL realurl = new URL(urlstring);
            logger.debug("请求的服务器主机域名：" + realurl.getHost().toString());
            //打开与此URL的连接
            URLConnection connection = realurl.openConnection();
            //设置请求连接时间和读取数据时间
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(7000);
            //建立实际的连接
            connection.connect();
            //读取获取的数据
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                    Charset.forName("UTF-8")));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw new Exception("发送GET请求出现异常！", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                throw new Exception("关闭请求流出现异常！", e2);
            }
        }
        return result;
    }
}
