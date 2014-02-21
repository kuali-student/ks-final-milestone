package org.kuali.student.ap.audit.service.model;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AuditDataSource implements DataSource {

    private String html;
    private String auditId;

    public AuditDataSource(String html, String auditId) {
        this.html = html;
        this.auditId = auditId;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream( html.getBytes() );
        return in;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public String getName() {
        return auditId;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
