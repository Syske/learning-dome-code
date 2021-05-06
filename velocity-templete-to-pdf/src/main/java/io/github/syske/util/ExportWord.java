package io.github.syske.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: velocity-templete-to-pdf
 * @description: 导出word
 * @author: syske
 * @create: 2020-06-30 17:04
 */

public class ExportWord {

    private static Logger logger = LoggerFactory.getLogger(ExportWord.class);

//
//    public static String export2(Map<String,Object> data) throws Exception {
//        File f = new File("E:\\word\\wm.vm");
//        String vmContext = FileUtils.readFileToString(f,"UTF-8");
//        String re = VelocityUtil.evaluateString(vmContext, data);
//        String filePathName = "E:\\word\\vm.txt";
//        writeFileTxt(re, filePathName);
//        return filePathName;
//    }

    public static String export(Map<String, Object> data, String filePathName) throws Exception {
        String xmlFile = "template/bdz/pre-bdz.xml";
        String vmContext = getXmlString(xmlFile);
        String re = VelocityUtil.evaluateString(vmContext, data);
        writeFileTxt(re, filePathName);
        return filePathName;
    }

    public static String getXmlString(String xmlName) throws Exception {
        logger.info("xmlName==={}", xmlName);
        StringBuffer xml = new StringBuffer();
        try {
            InputStream is = ExportWord.class.getClassLoader().getResourceAsStream(xmlName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                xml.append(line);
            }
            return xml.toString();
        } catch (IOException e) {
            throw new Exception("无法找到要处理的文件，解析配置文件[" + xmlName + "]出错");
        }
    }


    public static void writeFileTxt(String re, String filePathName) throws Exception {
        File file = new File(filePathName).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream outSTr = new FileOutputStream(new File(filePathName));
        BufferedOutputStream buff = new BufferedOutputStream(outSTr);
        buff.write(re.getBytes("UTF-8"));
        buff.flush();
        buff.close();
        outSTr.close();
        logger.info("文件保存成功。保存路径是：" + filePathName);
    }
}
