/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.ui.server.gwt;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.GwtExportRpcService;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.common.ui.server.screenreport.ScreenReportProcessor;
import org.kuali.student.r1.common.assembly.data.Data;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Deprecated
@SuppressWarnings("serial")
public class ExportGwtRpcServlet extends RemoteServiceServlet implements GwtExportRpcService {

    final Logger logger = Logger.getLogger(ExportGwtRpcServlet.class);

    private ScreenReportProcessor reportProcessor;

    public ScreenReportProcessor getReportProcessor() {
        return reportProcessor;
    }

    public void setReportProcessor(ScreenReportProcessor reportProcessor) {
        this.reportProcessor = reportProcessor;
    }

    @Override
    public String reportExport(List<ExportElement> exportElements, Data root, String templateName, String exportFormat, String reportTitle) {
        String exportId = null;
        boolean exportBasedOnView = true; // TODO Nina do we want this as a system Property??
        try {
            // If templateName is null, then default to default Template ie base.template
            if (templateName == null) {
                templateName = "base.template";
            }
            logger.info("Template Name = " + templateName + " For format = " + exportFormat);
            byte[] exportOutput = null;
            if (exportBasedOnView) {
                exportOutput = exportBasedOnView(exportElements, templateName, exportFormat, reportTitle);

            } else {
                exportOutput = exportBasedOnDataModel(root, templateName, exportFormat, reportTitle);
            }
            exportId = this.getExportId();
            logger.info("Export succesful - Export ID = " + exportId);
            getThreadLocalRequest().getSession(true).setAttribute(exportId, exportOutput);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
        return exportId;
    }

    private byte[] exportBasedOnDataModel(Data root, String templateName, String exportFormat, String reportTitle) {
        byte[] exportOutput = null;
        if (exportFormat.equals(ExportUtils.PDF)) {
            exportOutput = reportProcessor.createPdf(root, templateName, reportTitle);
        } else if (exportFormat.equals(ExportUtils.DOC)) {
            exportOutput = reportProcessor.createDoc(root, templateName, reportTitle);

        } else if (exportFormat.equals(ExportUtils.XLS)) {
            exportOutput = reportProcessor.createXls(root, templateName, reportTitle);
        }
        return exportOutput;
    }

    private byte[] exportBasedOnView(List<ExportElement> exportElements, String templateName, String exportFormat, String reportTitle) {
        byte[] exportOutput = null;
        if (exportFormat.equals(ExportUtils.PDF)) {
            exportOutput = reportProcessor.createPdf(exportElements, templateName, reportTitle);
        } else if (exportFormat.equals(ExportUtils.DOC)) {
            exportOutput = reportProcessor.createDoc(exportElements, templateName, reportTitle);

        } else if (exportFormat.equals(ExportUtils.XLS)) {
            exportOutput = reportProcessor.createXls(exportElements, templateName, reportTitle);
        }
        return exportOutput;
    }

    private String getExportId() {
        return UUID.randomUUID().toString();
    }

}