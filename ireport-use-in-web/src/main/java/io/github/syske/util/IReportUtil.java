package io.github.syske.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

/**
 * @program: ireportuseinweb
 * @description:
 * @author: syske
 * @create: 2019-12-28 11:17
 */
public class IReportUtil {

    /**
     * IReport报表生成pdf
     *
     * @param reprotfileName 报表文件名称
     * @param exportFileName pdf保存完整路径，包含文件名及后缀
     * @param parameters     报表入参参数集
     * @param connection     数据库连接
     * @return
     * @throws IOException
     * @throws JRException
     */
    public static String exportPdfFileServer(
            String reprotfileName, String exportFileName,
            Map<String, Object> parameters, Connection connection)
            throws IOException, JRException {

        String sourceFileName = reprotfileName;
        // 根据文件创建文件的输出流
        JasperRunManager.runReportToPdfFile(sourceFileName, exportFileName,
                parameters, connection);
        return exportFileName;

    }

    /**
     * IReport报表生成pdfStream
     *
     * @param reprotfileName 报表文件名称
     * @param parameters     报表入参参数集
     * @param connection     数据库连接
     * @return
     * @throws IOException
     * @throws JRException
     */
    public static FileOutputStream exportPdfFileServer(String reprotfileName,
                                                       Map<String, Object> parameters, Connection connection)
            throws IOException, JRException {

        FileInputStream sourceFileInputStream = new FileInputStream(
                reprotfileName);
        String exportFileName = UUID.randomUUID().toString() + ".pdf";
        FileOutputStream exportFileOutputStream = new FileOutputStream(
                exportFileName);
        // 根据文件创建文件的输出流
        JasperRunManager.runReportToPdfStream(sourceFileInputStream,
                exportFileOutputStream, parameters, connection);
        return exportFileOutputStream;

    }

}