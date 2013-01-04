package org.kuali.student.common.ui.client.reporting;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.util.ExportElement;

/**
 * An interface to access the Kuali Student ReportExporter
 * 
 * @author Kuali Student Team
 */
public interface ReportExport {

    /**
     * Name of report if overwritten on sub class.
     */
    public String exportTemplateName = null;
    
    /**
     * 
     * This method implement the "Generic Export" of a windows content to Jasper based on the format the user selected.
     * This method can be overwritten on a subclass to do specific export to the specific view
     * 
     */    
    public void doReportExport(List<ExportElement> exportElements, String format, String reportTitle);

    /**
     * 
     * This method needs to be implemented on every controller that want's to export the data
     * 
     * @return
     */
    public DataModel getExportDataModel();

    /**
     * 
     * This overridden method only needs to be implemented if the Generic Export reports has to be 
     * overwritten for a particular COntroller.
     * 
     */
    public String getExportTemplateName();
    
    /**
     * 
     * This method returns array of ExportElements which represent the elements on the view that must be exported
     * 
     * @return
     */
    public List<ExportElement> getExportElementsFromView();
   
}
