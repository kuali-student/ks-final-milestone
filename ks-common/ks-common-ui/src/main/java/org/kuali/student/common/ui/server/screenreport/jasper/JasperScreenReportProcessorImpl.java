package org.kuali.student.common.ui.server.screenreport.jasper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.server.screenreport.ScreenReportProcessor;
import org.kuali.student.r1.common.assembly.data.Data;

/**
 * This is a Jasper implimentation of the ScreenReportProcessor to generate documents in pdf, doc etc using the Jasper
 * library.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Deprecated
public class JasperScreenReportProcessorImpl implements ScreenReportProcessor {

    final Logger LOG = Logger.getLogger(JasperScreenReportProcessorImpl.class);

    private static final String PROPERTIES_FILE = "jasper.properties";

    private static Properties jasperProperties = new Properties();

    public JasperScreenReportProcessorImpl() {
        super();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            jasperProperties.load(inputStream);
        } catch (FileNotFoundException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    @Override
    public byte[] createPdf(Data source, String template, String reportTitle) {
        try {
            JasperPrint jprint = this.prepare(template, reportTitle, source, null);
            return exportPdf(jprint);

        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    public byte[] createPdf(List<ExportElement> source, String template, String reportTitle) {
        try {
            JasperPrint jprint = this.prepare(template, reportTitle, null, source);
            return exportPdf(jprint);

        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }

    /**
     * This method exports a jasperprint object to pdf format.
     * 
     * @param jprint
     * @return
     * @throws JRException
     */
    private byte[] exportPdf(JasperPrint jprint) throws JRException {
        return JasperExportManager.exportReportToPdf(jprint);
    }

    @Override
    public byte[] createXls(Data source, String template, String reportTitle) {
        try {
            JasperPrint jprint = this.prepare(template, reportTitle, source, null);
            return exportXls(jprint);

        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    public byte[] createXls(List<ExportElement> source, String template, String reportTitle) {
        try {
            JasperPrint jprint = this.prepare(template, reportTitle, null, source);
            return exportXls(jprint);

        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }

    
   
    /**
     * This method exports a jasperprint object to excel format.
     * 
     * @param jprint
     * @return
     * @throws JRException
     */
    private byte[] exportXls(JasperPrint jprint) throws JRException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);

        exporter.exportReport();

        return baos.toByteArray();
    }

    @Override
    public String createXml(Data source, String template, String reportTitle) {
        try {
            JasperPrint jprint = prepare(template, reportTitle, source, null);
            return JasperExportManager.exportReportToXml(jprint);

        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    public byte[] createDoc(Data source, String template, String reportTitle) {
        try {
            JasperPrint jprint = prepare(template, reportTitle, source, null);

            return exportDoc(jprint, template);
        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    public byte[] createDoc(List<ExportElement> source, String template, String reportTitle) {
        try {
            JasperPrint jprint = prepare(template, reportTitle, null, source);

            return exportDoc(jprint, template);
        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }

    /**
     * This method exports a jasperprint object to a doc format document.
     * 
     * @param jprint
     * @param template
     * @return
     */
    private byte[] exportDoc(JasperPrint jprint, String template) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JRDocxExporter exporter = new JRDocxExporter();
            
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.setParameter(JRDocxExporterParameter.FRAMES_AS_NESTED_TABLES, false);

            exporter.exportReport();
        } catch (JRException e) {
            LOG.error(e);
        }
        return baos.toByteArray();
    }

    /**
    *
    */
    @Override
    public byte[] createText(List<ExportElement> source, String template, String reportTitle) {
        try {
            JasperPrint jprint = prepare(template, reportTitle, null, source);

            return exportText(jprint, template);
        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }
    
    private byte[] exportText(JasperPrint jprint, String template) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JRTextExporter exporter = new JRTextExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();
        } catch (JRException e) {
            LOG.error(e);
        }
        return baos.toByteArray();
    }
    
    @Override
    public byte[] createRtf(List<ExportElement> source, String template, String reportTitle) {
        try {
            JasperPrint jprint = prepare(template, reportTitle, null, source);

            return exportRtf(jprint, template);
        } catch (JRException e) {
            LOG.error(e);
            return null;
        }
    }
    
    private byte[] exportRtf(JasperPrint jprint, String template) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JRRtfExporter exporter = new JRRtfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();
        } catch (JRException e) {
            LOG.error(e);
        }
        return baos.toByteArray();
    }
   
    /**
     * Compile and generate the report from the template files and datamodel from the UI.
     */
    @SuppressWarnings("unchecked")
    private JasperPrint prepare(String template, String reportTitle, Data dataMap, List<ExportElement> dataList) throws JRException {
        // Compile base report
        String templateLocation = (String) jasperProperties.get(template);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(templateLocation);
        JasperReport jreport = JasperCompileManager.compileReport(is);

        // Preparing parameters
        Map parameters = new HashMap();
        parameters.put("ReportTitle", reportTitle);

        // Add Subreport
        String subreportLocation = (String) jasperProperties.get(template + ".subreport");
        if (subreportLocation != null) {
            InputStream subis = this.getClass().getClassLoader().getResourceAsStream(subreportLocation);
            JasperReport subreport = JasperCompileManager.compileReport(subis);
            parameters.put("SubReport", subreport);
        }

        // Fill the report with the data from the UI.
        JasperPrint jprint;
        if (dataMap != null) {
            jprint = JasperFillManager.fillReport(jreport, parameters, new KSCustomDataSource(dataMap.iterator()));
        } else {
            jprint = JasperFillManager.fillReport(jreport, parameters, new KSCollectionDataSource(dataList, null));

        }
        return jprint;
    }

}
