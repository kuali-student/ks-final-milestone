package org.kuali.student.common.ui.server.screenreport;

import java.util.List;

import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.r1.common.assembly.data.Data;

public interface ScreenReportProcessor {

    public byte[] createPdf(Data source, String template, String reportTitle);

    public byte[] createPdf(List<ExportElement> source, String template, String reportTitle);

    public String createXml(Data source, String template, String reportTitle);
    
    public byte[] createXls(Data source, String template, String reportTitle);

    public byte[] createXls(List<ExportElement> source, String template, String reportTitle);

    public byte[] createDoc(Data source, String template, String reportTitle);

    public byte[] createDoc(List<ExportElement> source, String template, String reportTitle);

    public byte[] createText(List<ExportElement> source, String template, String reportTitle);
    
    public byte[] createRtf(List<ExportElement> source, String template, String reportTitle);

}