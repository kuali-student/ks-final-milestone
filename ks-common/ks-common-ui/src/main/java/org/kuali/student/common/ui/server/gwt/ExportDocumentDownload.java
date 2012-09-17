package org.kuali.student.common.ui.server.gwt;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.util.ExportUtils;

@Deprecated
public class ExportDocumentDownload extends HttpServlet {

    final Logger LOG = Logger.getLogger(ExportDocumentDownload.class);
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // If PDF is generated to to temp file, read it
            String exportId = request.getParameter("exportId");
            String format = request.getParameter("format");
            // Get from session the byte array that was cached by the GWT Servlet
            byte[] bytes = (byte[]) request.getSession().getAttribute(exportId);

            sendPDF(response, bytes, format);
        } catch (Exception ex) {
            // TODO Nina how must we handle exceptions here??
            // do something here
        }
    }

    void sendPDF(HttpServletResponse response, byte[] bytes, String format) throws IOException {
        ServletOutputStream stream = response.getOutputStream();
        if (format.equals(ExportUtils.PDF)) {
            response.setContentType("application/pdf");
            response.addHeader("Content-Type", "application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=export.pdf");
            response.setContentLength((int) bytes.length);
        } else if (format.equals(ExportUtils.XLS)) {
            response.setContentType("application/xls");
            response.addHeader("Content-Type", "application/xls");
            response.addHeader("Content-Disposition", "inline; filename=export.xls");
            response.setContentLength((int) bytes.length);
        } else {
            response.setContentType("application/ms-word");
            response.addHeader("Content-Type", "application/ms-word");
            response.addHeader("Content-Disposition", "inline; filename=export.doc");
            response.setContentLength((int) bytes.length);
        }
        stream.write(bytes);
        stream.close();
    }
}
