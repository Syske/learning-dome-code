package lss.medicare.reckoner.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lss.SIAndBankServices.utility.SFTPUtils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
    private transient static Logger log = LoggerFactory.getLogger(Base64Util.class);

    /**
     * base64�ַ���תbyte
     *
     * @param imageBase64Str
     * @return
     */
    public static byte[] base64topng(String imageBase64Str) {
        byte[] b1 = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            if (imageBase64Str.indexOf("data:image/jpeg;base64,") != -1) {
                b1 = decoder.decodeBuffer(imageBase64Str.replaceAll("data:image/jpeg;base64,", ""));
            } else if (imageBase64Str.indexOf("data:image/png;base64,") != -1) {
                b1 = decoder.decodeBuffer(imageBase64Str.replaceAll(
                        "data:image/png;base64,", ""));
            } else if (imageBase64Str.indexOf("data:image/jpg;base64,") != -1) {
                b1 = decoder.decodeBuffer(imageBase64Str.replaceAll(
                        "data:image/jpg;base64,", ""));
            } else {
                b1 = decoder.decodeBuffer(imageBase64Str);
            }
            for (int i = 0; i < b1.length; ++i) {
                if (b1[i] < 0) {// �����쳣����
                    b1[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b1;
    }

    /**
     * ����ͼƬ
     *
     * @param realPath
     * @param imageStr base64�ַ���
     * @return imgName
     */
    public static String filePath(String realPath, String imageStr) {
        String imgPath = "";
        String imgName = "";
        try {
            byte[] imageBytes = base64topng(imageStr);
            File f1 = new File(realPath);
            if (!f1.exists()) {
                f1.mkdir();
            }
            imgName = UUID.randomUUID().toString() + ".jpg";
            imgPath = realPath + "/" + imgName;
            File f2 = new File(imgPath);
            if (!f2.exists()) {
                f2.createNewFile();
            }
            OutputStream out = new FileOutputStream(imgPath);
            out.write(imageBytes);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgName;
    }


    /**
     * base64�ַ���ת����ͼƬ
     *
     * @param imgData     ͼƬ����
     * @param imgFilePath ��ŵ�����·��
     * @return
     * @throws IOException
     */
    public static String GenerateImage(String filePath, String imgData) throws IOException { // ���ֽ������ַ�������Base64���벢����ͼƬ
        String imgName = UUID.randomUUID().toString() + ".jpg";
        if (imgData == null) // ͼ������Ϊ��
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + imgName);
            // Base64����
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// �����쳣����
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (IOException e) {
            log.error("ͼƬת��ʧ�ܣ�" + e);
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
        return imgName;
    }

    /**
     * base64�ַ���ת����ͼƬ
     *
     * @param imgData     ͼƬ����
     * @param imgFilePath ��ŵ�sftp·��
     * @return
     * @throws IOException
     */
    public static String saveFileToFileServerForWB(String imgFilePath, String imgData) throws IOException { // ���ֽ������ַ�������Base64���벢����ͼƬ
        String imgName = UUID.randomUUID().toString() + ".jpg";
        if (imgData == null) // ͼ������Ϊ��
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64����
            byte[] b = decoder.decodeBuffer(imgData);
            uploadFile(new ByteArrayInputStream(b), imgName, imgFilePath);
        } catch (IOException e) {
            log.error("ͼƬ����ʧ�ܣ�" + e);
            e.printStackTrace();
        } catch (SftpException e) {
            log.error("ͼƬ����ʧ�ܣ�" + e);
            e.printStackTrace();
        }
        return imgName;
    }

    /**
     * ����ͼƬ���ļ�������
     *
     * @param imgSavePath ��ŵ�sftp·��
     * @param imageStr    base64�ַ���
     * @return imgName
     */
    public static String saveFileToFileServerForWX(String imgSavePath, String imageStr) {
        String imgName = UUID.randomUUID().toString() + ".jpg";
        try {
            byte[] imageBytes = base64topng(imageStr);
            uploadFile(new ByteArrayInputStream(imageBytes), imgName, imgSavePath);
        } catch (Exception e) {
            log.error("ͼƬ����ʧ�ܣ�" + e);
            e.printStackTrace();
        }
        return imgName;
    }


    /**
     * ͼƬת����base64�ַ���
     *
     * @param imgPath
     * @return
     */
    public static String GetImageStr(String imgPath) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
        String imgFile = imgPath;// �������ͼƬ
        InputStream in = null;
        byte[] data = null;
        String encode = null; // ����Base64��������ֽ������ַ���
        // ���ֽ�����Base64����
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // ��ȡͼƬ�ֽ�����
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error("ͼƬת��ʧ�ܣ�" + e);
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * ΢�Žӿ��ϴ�����
     *
     * @param fileInput    �ļ���
     * @param saveFileName �����ļ���
     * @param fileSavePath ����·��
     * @throws FileNotFoundException
     * @throws SftpException
     */
    public static void uploadFile(InputStream fileInput, String saveFileName, String fileSavePath) throws FileNotFoundException,
            SftpException {
        SFTPUtils sftp = new SFTPUtils(PropertiesUtils.SFTP_USERNAME_FILE_SERVER,
                PropertiesUtils.SFTP_PASSWORD_FILE_SERVER, PropertiesUtils.STFP_URL_FILE_SERVER,
                Integer.parseInt(PropertiesUtils.SFTP_PORT_FILE_SERVER));

        ChannelSftp csftp = sftp.login();

        sftp.upload(fileSavePath, saveFileName, fileInput, csftp);
        sftp.logout(csftp);

    }

}
