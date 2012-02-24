package org.kuali.student.common.ui.client.reporting;

import java.util.List;

import org.kuali.student.common.ui.client.util.ExportElement;

/**
 * An interface to access the Kuali Student ReportExporter
 * 
 * @author Kuali Student Team
 */
public interface ReportExportWidget {
    
    public String getExportFieldValue();

    public List<ExportElement> getExportElementSubset(ExportElement parent);
    
    public boolean isExportElement();
   
}
