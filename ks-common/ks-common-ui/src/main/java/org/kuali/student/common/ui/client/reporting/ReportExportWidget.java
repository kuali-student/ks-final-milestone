package org.kuali.student.common.ui.client.reporting;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.util.ExportElement;

/**
 * An interface to access the Kuali Student ReportExporter
 * 
 * @author Kuali Student Team
 */
public interface ReportExportWidget {

    public ArrayList<ExportElement> getExportElementsWidget(String viewName, String sectionName);
   
}
