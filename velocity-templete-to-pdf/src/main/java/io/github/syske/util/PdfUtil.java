package io.github.syske.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: velocity-templete-to-pdf
 * @description:
 * @author: syske
 * @create: 2020-06-30 17:12
 */
public class PdfUtil {
   private static Logger logger = LoggerFactory.getLogger(PdfUtil.class);

    /**
     *
     * @param o 写入的数据
     * @param out 自定义保存pdf的文件流
     * @param templatePath pdf模板路径
     */
    // 利用模板生成pdf
    public static void fillTemplate(Map<String,Object> o, OutputStream out, String templatePath)
    throws Exception {
        PdfReader reader = null;
        ByteArrayOutputStream bos = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                System.out.println(name);
                String value = o.get(name)!=null?o.get(name).toString():null;
                form.setField(name,value);
            }
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            int numberOfPages = reader.getNumberOfPages();
            for(int i = 1; i <= numberOfPages; i++) {
                PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), i);
                copy.addPage(importPage);
            }

            doc.close();

        } catch (IOException e) {
            logger.error("文件生成失败", e);
            throw new Exception("文件生成失败");
        } catch (DocumentException e) {
            logger.error("文件生成失败", e);
            throw new Exception("文件生成失败");
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (stamper != null) {
                stamper.close();
            }
        }
    }
    public static void main(String[] args) {
        // 模板路径
        String templatePath = "F:\\Users\\sysker\\Desktop\\2019-2020年度协议\\form\\2019年陕西省省级医疗照顾人员服务协议书（医院）.pdf";
        // 生成的新文件路径
        String newPDFPath = "E:/2019年陕西省省级医疗照顾人员服务协议书（医院）.pdf";
        FileOutputStream out = null;// 输出流
        try {
            out = new FileOutputStream(newPDFPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String,Object> parameter = new HashMap<String, Object>();
        parameter.put("yfmc", "乙方名称2");
        parameter.put("fddbr", "法定代表人2");
        parameter.put("fdlxdh", "法定代表人联系电话2");
        parameter.put("yfdz", "乙方地址2");
        parameter.put("yfyzbm", "乙方邮政编码2");
        parameter.put("yflxr", "乙方联系人2");
        parameter.put("yflxdh", "乙方联系电话2");
        parameter.put("yydj", "医院等级");
        parameter.put("khyh", "开户银行2");
        parameter.put("yhzhmc", "银行账户名称2");
        parameter.put("yhzh", "银行账号2");
        parameter.put("yhhh", "银行行号2");
        parameter.put("wlipdz", "网络ip地址2");
        parameter.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));

        try {
            fillTemplate(parameter, out, templatePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
