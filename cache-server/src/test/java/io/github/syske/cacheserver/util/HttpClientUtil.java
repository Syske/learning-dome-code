package io.github.syske.cacheserver.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;


/**
 * @program: cache-server
 * @description:
 * @author: syske
 * @create: 2020-05-28 13:38
 */
public class HttpClientUtil {
    private Log log = LogFactory.getLog(getClass());

    public String doPost(String url, String paramString) throws Exception {

        return this.doPost(url, paramString, null);
    }


    /**
     * 发送 post请求
     *
     * @param url
     * @param paramString 请求参数
     * @param headerMap   请求头
     * @return
     * @throws Exception
     */
    public String doPost(String url, String paramString, Map<String, String> headerMap) throws Exception {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(paramString, "UTF-8");

        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 响应模型
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            log.debug("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                responseContent = EntityUtils.toString(responseEntity, "UTF-8");
                log.debug("响应内容长度为:" + responseEntity.getContentLength());
                log.debug("响应内容为:" + responseContent);

            }
        } catch (ClientProtocolException e) {
            log.error("后端系统调用异常", e);
            throw new Exception("后端系统调用异常", e);
        } catch (ParseException e) {
            log.error("后端系统调用异常", e);
            throw new Exception("后端系统调用异常", e);
        } catch (IOException e) {
            log.error("后端系统调用异常", e);
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
                log.error("后端系统调用异常", e);
                throw new Exception("后端系统调用异常", e);
            }
        }
        return responseContent;
    }


    /**
     * 发送get请求
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public String doGet(String url, String param) throws Exception {
        String result = "";
        BufferedReader in = null;
        try {
            String urlstring = url + "?" + param;
            URL realurl = new URL(urlstring);
            log.debug("请求的服务器主机域名：" + realurl.getHost().toString());
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
