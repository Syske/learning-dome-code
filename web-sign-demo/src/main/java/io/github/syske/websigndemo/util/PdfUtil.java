package io.github.syske.websigndemo.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * @program: szyb-public-server
 * @description: pdf工具包
 * @author: syske
 * @create: 2020-12-28 15:44
 */
public class PdfUtil {
    private final static Logger logger = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * 根据pdf模板，生成pdf
     *
     * @param templateURL      模板完整文件名(远程文件)
     * @param saveFile 保存文件的完整文件名（包含文件路径）
     * @param parameters       参数
     * @throws DocumentException
     */
    public static void exportPdf(URL templateURL, File saveFile, Map<String, String> parameters) throws Exception {
        PdfReader pdfReader = new PdfReader(templateURL);
        exportPdf(pdfReader, saveFile, parameters);
    }

    /**
     * 根据pdf模板，生成pdf
     *
     * @param templatePath     模板完整文件名（包含文件路径）
     * @param saveFullFileName 保存文件的完整文件名（包含文件路径）
     * @param parameters       参数
     * @throws DocumentException
     */
    public static void exportPdf(String templatePath, File saveFullFileName, Map<String, String> parameters) throws Exception {
        PdfReader pdfReader = new PdfReader(templatePath);
        exportPdf(pdfReader, saveFullFileName, parameters);
    }

    /**
     * 根据pdf模板，生成pdf
     *
     * @param pdfReader        PdfReader
     * @param saveFullFileName 保存文件的完整文件名（包含文件路径）
     * @param parameters       参数
     * @throws DocumentException
     */
    public static void exportPdf(PdfReader pdfReader, File saveFullFileName, Map<String, String> parameters) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(saveFullFileName);
            byteArrayOutputStream = new ByteArrayOutputStream();
            PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
            //获取模板所有域参数
            AcroFields acroFields = pdfStamper.getAcroFields();

            //解决中文字体不显示的问题
            BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontArrayList = new ArrayList<BaseFont>();
            fontArrayList.add(baseFont);
            acroFields.setSubstitutionFonts(fontArrayList);

            for (String key : parameters.keySet()) {
                acroFields.setField(key, parameters.get(key));
            }

            pdfStamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
            pdfStamper.flush();
            pdfStamper.close();
            //设置纸张，可以在Excel制作是设定好纸张大小
            Document doc = new Document(PageSize.A4);
            PdfCopy copy = new PdfCopy(doc, fileOutputStream);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(byteArrayOutputStream.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
        } catch (Exception e) {
            throw new Exception("生成pdf文件失败", e);
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    throw new Exception("生成pdf文件失败，关闭资源失败");
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new Exception("生成pdf文件失败，关闭资源失败");
                }
            }
            if (pdfReader != null) {
                pdfReader.close();
            }
        }
    }

    /**
     * pdf转图片
     *
     * @param pdfFullName pdf完整文件名
     * @param imgSavePath 图片保存路径
     * @throws Exception
     */
    public static void pdf2Pic(String pdfFullName, String imgSavePath) throws Exception {
        org.icepdf.core.pobjects.Document document = new org.icepdf.core.pobjects.Document();
        document.setFile(pdfFullName);
        //缩放比例
        float scale = 2.5f;
        //旋转角度
        float rotation = 0f;
        // 文件名
        String pdfFileName = new File(pdfFullName).getName();
        try {
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = (BufferedImage)
                        document.getPageImage(i, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);

                String imgName = pdfFileName + i + ".png";
                System.out.println(imgName);
                File file = new File(imgSavePath + imgName);
                ImageIO.write(image, "png", file);

                image.flush();
            }
        } catch (Exception e) {
            throw new Exception("pdf文件转图片失败", e);
        }
        document.dispose();
    }

    /**
     * 返回图片base64编码
     * @param pdfFullName
     * @param pageNumber
     * @return
     * @throws Exception
     */
    public static String pdf2Pic(String pdfFullName, int pageNumber) throws Exception {
        float scale = 2.5f;
        float rotation = 0f;
        File tempFile = new File(pdfFullName);
        org.icepdf.core.pobjects.Document document = new org.icepdf.core.pobjects.Document();
        document.setFile(tempFile.getAbsolutePath());
        String pdfFileName = tempFile.getName();
        BufferedImage image = (BufferedImage)
                document.getPageImage(pageNumber, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
        String imgName = pdfFileName + pageNumber + ".png";
        logger.debug("导出图片名称：" + imgName);
        File tempImgFile = new File("./" + imgName);
        logger.debug("文件绝对路径：" + tempImgFile.getAbsolutePath());
        try {
            ImageIO.write(image, "png", tempImgFile);
            image.flush();
            document.dispose();
            return Base64Util.getFileBase64Str(tempImgFile);
        } catch (PDFException e) {
            throw new Exception("pdf转图片失败", e);
        } catch (PDFSecurityException e) {
            throw new Exception("pdf转图片失败", e);
        } catch (IOException e) {
            throw new Exception("pdf转图片失败", e);
        } finally {
            // 删除临时文件
            if (tempFile.exists()) {
                tempFile.delete();
            }
            if(tempImgFile.exists()) {
                tempImgFile.delete();
            }
        }
    }

    /**
     * pdf转图片
     *
     * @param pdfFullName  pdf完整文件名
     * @param imgSavePath 图片保存路径
     * @param pageNumber 需要导出的pdf页码
     * @param scale 缩放比例
     * @param rotation 旋转角度
     * @throws Exception
     */
    public static void pdf2Pic(String pdfFullName, String imgSavePath,
                               int pageNumber, float scale, float rotation) throws Exception {
        org.icepdf.core.pobjects.Document document = new org.icepdf.core.pobjects.Document();
        document.setFile(pdfFullName);
        String pdfFileName = new File(pdfFullName).getName();
        BufferedImage image = (BufferedImage)
                document.getPageImage(pageNumber, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
        String imgName = pdfFileName + pageNumber + ".png";
        logger.debug("导出图片名称：" + imgName);
        File file = new File(imgSavePath + imgName);
        ImageIO.write(image, "png", file);
        image.flush();
        document.dispose();
    }


}
