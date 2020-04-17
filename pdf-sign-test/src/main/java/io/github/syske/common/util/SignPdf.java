package io.github.syske.common.util;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.security.*;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.UUID;

/**
 * @program: SignPdf
 * @description:
 * @author: syske
 * @create: 2019-12-03 22:54
 */

public class SignPdf {
    private static Logger logger = Logger.getLogger(SignPdf.class);
    private static final String PASSWORD = "123456"; // 秘钥密码
    private static final String KEY_STORE_PATH = "d:\\keystore.p12"; // 秘钥文件路径

    private SignPdf() {
    }

    /**
     * 图片签章，指定签名坐标位置
     *
     * @param signPdfSrc
     *            签名的PDF文件
     * @param signedPdfOutFile
     *            签名后的的PDF文件
     * @param signImage
     *            签名图片完整路径
     * @param x
     *            以左下角为原点x坐标值
     * @param y
     *            以左下角为原点Y坐标值
     * @param numberOfPages
     *            签名页码，如果是最后一页则传null
     * @param pageStyle
     *            页面布局，横向或者纵向
     * @throws Exception
     */
    public static void sign(String signPdfSrc, String signedPdfOutFile,
                            String signImage, Float x, Float y, Integer numberOfPages,
                            PageStyle pageStyle) throws Exception {
        sign(signPdfSrc, signedPdfOutFile, signImage, x, y, null,
                numberOfPages, pageStyle);
    }

    /**
     * 图片签章，指定关键字
     *
     * @param signPdfSrc
     *            签名的PDF文件
     * @param signedPdfFile
     *            签名后的的PDF文件
     * @param signImage
     *            签名图片完整路径
     * @param keyWords
     *            关键字
     * @param numberOfPages
     *            签名页码，如果是最后一页则传null
     * @param pageStyle
     *            页面布局，横向或者纵向
     */
    public static void sign(String signPdfSrc, String signedPdfFile,
                            String signImage, String keyWords, Integer numberOfPages,
                            PageStyle pageStyle) throws Exception {
        sign(signPdfSrc, signedPdfFile, signImage, null, null, keyWords,
                numberOfPages, pageStyle);
    }

    /**
     * 私人签章
     *
     * @param signPdfSrc
     *            签名的PDF文件
     * @param signedPdfOutFile
     *            签名后的的PDF文件
     * @param signImage
     *            签名图片完整路径
     * @param x
     *            以左下角为原点x坐标
     * @param y
     *            以左下角为原点y坐标
     * @param keyWords
     *            关键字
     * @param numberOfPages
     *            签名页码，如果是最后一页则传null
     * @param pageStyle
     *            页面布局，横向或者纵向
     * @return
     */
    public static void sign(String signPdfSrc, String signedPdfOutFile,
                            String signImage, Float x, Float y, String keyWords,
                            Integer numberOfPages, PageStyle pageStyle) throws Exception {
        File signPdfSrcFile = new File(signPdfSrc);
        PdfReader reader = null;
        ByteArrayOutputStream signPDFData = null;
        PdfStamper stp = null;
        FileInputStream fos = null;
        FileOutputStream pdfOutputStream = null;
        try {
            BouncyCastleProvider provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            KeyStore ks = KeyStore.getInstance("PKCS12",
                    new BouncyCastleProvider());
            fos = new FileInputStream(KEY_STORE_PATH);
            // 私钥密码 为Pkcs生成证书是的私钥密码 123456
            ks.load(fos, PASSWORD.toCharArray());
            String alias = ks.aliases().nextElement();
            PrivateKey key = (PrivateKey) ks.getKey(alias,
                    PASSWORD.toCharArray());
            Certificate[] chain = ks.getCertificateChain(alias);
            reader = new PdfReader(signPdfSrc); // 也可以输入流的方式构建
            signPDFData = new ByteArrayOutputStream();
            numberOfPages = numberOfPages == null ? reader.getNumberOfPages()
                    : 0;

            // 临时pdf文件
            File temp = new File(signPdfSrcFile.getParent(),
                    System.currentTimeMillis() + ".pdf");
            stp = PdfStamper.createSignature(reader, signPDFData, '\0', temp,
                    true);
            stp.setFullCompression();
            PdfSignatureAppearance sap = stp.getSignatureAppearance();
            sap.setReason("数字签名，不可改变");
            // 使用png格式透明图片
            Image image = Image.getInstance(signImage);

            sap.setImageScale(0);
            sap.setSignatureGraphic(image);
            sap.setRenderingMode(RenderingMode.GRAPHIC);
            float llx = 0f;
            float lly = 0f;

            float signImageWidth = image.getWidth();
            float signImageHeight = image.getHeight();

            float signImageHeightSocale = 85 / signImageWidth * signImageHeight;
            if (keyWords != null && !keyWords.isEmpty()) {
                KeyWordInfo keyWordInfo = getKeyWordLocation(numberOfPages,
                        keyWords, reader);
                Rectangle pageSize = reader.getPageSize(numberOfPages);

                float width = pageSize.getWidth();
                if (PageStyle.PAGE_STYLE_LANDSCAPE.equals(pageStyle)) {
                    llx = keyWordInfo.getY() + (float) keyWordInfo.getHeight();
                    lly = width - keyWordInfo.getX() - signImageHeightSocale
                            / 2;
                } else if (PageStyle.PAGE_STYLE_PORTRAIT.equals(pageStyle)) {
                    llx = keyWordInfo.getX() + (float) keyWordInfo.getWidth()*keyWords.length() ;
                    lly = keyWordInfo.getY() - signImageHeightSocale / 2;
                }

            } else if (x != null && y != null) {
                llx = x;
                lly = y;
            } else {
                throw new Exception("坐标和关键字不能同时为空！");
            }

            float urx = llx + 85;
            float ury = lly + signImageHeightSocale;
            // 是对应x轴和y轴坐标
            sap.setVisibleSignature(new Rectangle(llx, lly, urx, ury),
                    numberOfPages,
                    UUID.randomUUID().toString().replaceAll("-", ""));
            stp.getWriter().setCompressionLevel(5);
            ExternalDigest digest = new BouncyCastleDigest();
            ExternalSignature signature = new PrivateKeySignature(key,
                    DigestAlgorithms.SHA512, provider.getName());
            MakeSignature.signDetached(sap, digest, signature, chain, null,
                    null, null, 0, CryptoStandard.CADES);
            stp.close();
            reader.close();
            pdfOutputStream = new FileOutputStream(signedPdfOutFile);
            pdfOutputStream.write(signPDFData.toByteArray());
            pdfOutputStream.close();
        } catch (KeyStoreException e) {
            logger.error("签名验证失败", e);
            throw new Exception("签名验证失败", e);
        } catch (FileNotFoundException e) {
            logger.error("文件未找到", e);
            throw new Exception("文件未找到", e);
        } catch (IOException e) {
            logger.error("IO异常", e);
            throw new Exception("IO异常", e);
        } catch (Exception e) {
            logger.error("签章失败", e);
            throw new Exception("签章失败", e);
        } finally {
            if (signPDFData != null) {
                try {
                    signPDFData.close();
                } catch (IOException e) {
                    logger.error("资源关闭失败", e);
                    throw new Exception("资源关闭失败", e);
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error("资源关闭失败", e);
                    throw new Exception("资源关闭失败", e);
                }
            }
            if (pdfOutputStream != null) {
                try {
                    pdfOutputStream.close();
                } catch (IOException e) {
                    logger.error("资源关闭失败", e);
                    throw new Exception("资源关闭失败", e);
                }
            }

        }
    }

    /**
     * 查找关键字定位
     *
     * @param numberOfPages
     * @param keyWords
     *            关键字
     * @param reader
     * @return
     * @throws IOException
     */
    private static KeyWordInfo getKeyWordLocation(Integer numberOfPages,
                                                  final String keyWords, PdfReader reader) throws IOException {
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(
                reader);

        final KeyWordInfo keyWordInfo = new KeyWordInfo();

        pdfReaderContentParser.processContent(numberOfPages,
                new RenderListener() {
                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText(); // 整页内容

                        if (null != text && text.contains(keyWords)) {
                            Rectangle2D.Float boundingRectange = textRenderInfo
                                    .getBaseline().getBoundingRectange();
                            float leftY = (float) boundingRectange.getMinY() - 1;
                            float rightY = (float) boundingRectange.getMaxY() + 1;

                            logger.debug(boundingRectange.x + "--"
                                    + boundingRectange.y + "---");

                            keyWordInfo.setHeight(rightY - leftY);
                            keyWordInfo.setWidth((rightY - leftY)
                                    * keyWords.length());
                            keyWordInfo.setX(boundingRectange.x);
                            keyWordInfo.setY(boundingRectange.y);
                        }

                    }

                    @Override
                    public void renderImage(ImageRenderInfo arg0) {}

                    @Override
                    public void endTextBlock() {}

                    @Override
                    public void beginTextBlock() {}
                });
        return keyWordInfo;
    }

    private static class KeyWordInfo {
        private float x;
        private float y;
        private double width;
        private double height;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }
    }

    enum PageStyle {
        PAGE_STYLE_LANDSCAPE, // 横向
        PAGE_STYLE_PORTRAIT // 纵向
    }

    public static PageStyle getPageStyle_LANDSCAPE() {
        return PageStyle.PAGE_STYLE_LANDSCAPE;
    }

    public static PageStyle getPageStyle_PORTRAIT() {
        return PageStyle.PAGE_STYLE_PORTRAIT;
    }

    public static void main(String[] args) throws Exception {
        sign("E:\\workSpeace\\learning_files\\projects\\learning-dome-code\\ireport-demo\\ireport-demo.pdf",//
                "D:\\signed-35.pdf",
                "E:\\workSpeace\\learning_files\\yzz.png",
                null, null, "领导签字", null, PageStyle.PAGE_STYLE_PORTRAIT);
        // read();

        // test();
    }



}