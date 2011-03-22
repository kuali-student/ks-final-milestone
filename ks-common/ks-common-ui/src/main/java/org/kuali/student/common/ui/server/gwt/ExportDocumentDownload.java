package org.kuali.student.common.ui.server.gwt;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ExportDocumentDownload extends HttpServlet {

    final Logger LOG = Logger.getLogger(ExportDocumentDownload.class);
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // If PDF is generated to to temp file, read it
             String exportId = request.getParameter("exportId");
             // Get from session the byte array that was cached by the GWT Servlet
             byte[] bytes = (byte[]) request.getSession().getAttribute(exportId);
             
            sendPDF(response, bytes, "export.pdf");
        } catch (Exception ex) {
            // TODO Nina how must we handle exceptions here??
            //do something here
        }
    }

    void sendPDF(HttpServletResponse response, byte[] bytes, String name) throws IOException {
        ServletOutputStream stream = null;

        stream = response.getOutputStream();
        response.setContentType("application/pdf");
        response.addHeader("Content-Type", "application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=" + name);
        response.setContentLength((int) bytes.length);
        stream.write(bytes);
        stream.close();
    }
}
