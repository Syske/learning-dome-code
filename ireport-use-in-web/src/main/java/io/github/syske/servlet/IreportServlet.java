package io.github.syske.servlet;

import io.github.syske.db.DBHelper;
import io.github.syske.util.IReportUtil;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class IreportServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request,
                          javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request,
                         javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        Connection connection = DBHelper.getConnection();
        parameters.put("username", request.getParameter("username"));
        try {
            ServletContext context = request.getSession().getServletContext();

            String sourceFileName = context.getRealPath("ireport/"
                    + "ireport-demo.jasper");
            IReportUtil.exportPdfFileServer(sourceFileName, "D:/test.pdf",parameters, connection);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
