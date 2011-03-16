package org.kuali.student.common.ui.server.screenreport;

import java.util.List;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.ui.client.util.ExportElement;

public interface ScreenReportProcessor {

    public byte[] createPdf(Data source, String template);

    public byte[] createPdf(List<ExportElement> source, String template);

    public String createXml(Data source, String template);
    
    public byte[] createXls(Data source, String template);

    public byte[] createXls(List<ExportElement> source, String template);

    public byte[] createDoc(Data source, String template);

    public byte[] createDoc(List<ExportElement> source, String template);

}