package org.kuali.student.lum.common.client.widgets;

//import java.text.SimpleDateFormat;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.reporting.ReportExportWidget;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;

import java.util.*;

public class CluSetDetailsWidget extends Composite implements ReportExportWidget {

    private CluSetInformation cluSetInformation;
    private SimplePanel mainPanel;
    private FlexTable detailsTable = new FlexTable();
    private boolean showClus;
    private Map<String, Boolean> showCluSetFlags = new HashMap<String, Boolean>();
    //private static final SimpleDateFormat DT_FOMRAT = new SimpleDateFormat("MM/dd/yyyy");
    private CluSetRetriever cluSetRetriever;
    // private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync;
    private BlockingTask retrievingTask = new BlockingTask("Retrieving details");

    public CluSetDetailsWidget(CluSetInformation cluSetInformation, CluSetRetriever cluSetRetriever) {
        mainPanel = new SimplePanel();
        this.initWidget(mainPanel);
        this.cluSetRetriever = cluSetRetriever;
        setCluSetInformation(cluSetInformation);
        redraw();
    }

    public CluSetDetailsWidget(String cluSetId, CluSetRetriever cluSetRetriever) {
        mainPanel = new SimplePanel();
        this.initWidget(mainPanel);
        this.cluSetRetriever = cluSetRetriever;

        KSBlockingProgressIndicator.addTask(retrievingTask);
        cluSetRetriever.getCluSetInformation(cluSetId, new Callback<CluSetInformation>() {
            @Override
            public void exec(CluSetInformation result) {
                if (result != null) {
                    setCluSetInformation(result);
                    redraw();
                }
                KSBlockingProgressIndicator.removeTask(retrievingTask);
            }
        });
    }

    private void redraw() {
        List<CluInformation> clus = null;
        List<CluSetInfo> cluSets = null;
        MembershipQueryInfo membershipQueryInfo = null;
        List<CluInformation> clusInRange = null;
        int rowIndex = 0;
        mainPanel.clear();
        detailsTable.clear();
        if (cluSetInformation == null) {
            return;
        } else {
            clus = cluSetInformation.getClus();
            cluSets = cluSetInformation.getCluSets();
            membershipQueryInfo = cluSetInformation.getMembershipQueryInfo();
            clusInRange = cluSetInformation.getClusInRange();
        }
        StringBuilder titleTextSb = new StringBuilder();
        titleTextSb.append("INDIVIDUAL COURSE(S)");
        KSLabel coursesHeader = new KSLabel(titleTextSb.toString());
        // coursesHeader.getElement().getStyle().setProperty("borderBottom", "1px solid #D8D8D8");
        detailsTable.setWidget(rowIndex, 0, coursesHeader);
        detailsTable.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
        if (cluSets != null && cluSets.size() > 0 || clusInRange != null && clusInRange.size() > 0) {
            coursesHeader.setVisible(true);
        } else {
            coursesHeader.setVisible(false);
        }
        {
            // show/hide clus
            int numClus = (clus == null) ? 0 : clus.size();
            StringBuilder hideClusTextSb = new StringBuilder();
            showClus = true;
            if (showClus) {
                hideClusTextSb.append("Hide courses(").append(numClus).append(")");
            } else {
                hideClusTextSb.append("Show courses(").append(numClus).append(")");
            }
            KSButton hideClusButton = new KSButton(hideClusTextSb.toString(), ButtonStyle.DEFAULT_ANCHOR);
            detailsTable.setWidget(rowIndex, 1, hideClusButton);
            hideClusButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    showClus = !showClus;
                    redraw();
                }
            });
            if (numClus > 10) {
                hideClusButton.setVisible(true);
            } else {
                hideClusButton.setVisible(false);
            }
        }

        rowIndex++;
        if (clus != null && showClus) {
            for (CluInformation clu : clus) {
                addClusDisplayToTable(rowIndex, clu);
                rowIndex++;
            }
        }
        if (cluSets != null) {
            if (cluSets.size() > 0) {
                KSLabel spacer = new KSLabel();
                spacer.getElement().getStyle().setPaddingTop(10, Style.Unit.PX);
                detailsTable.setWidget(rowIndex, 0, spacer);
                detailsTable.getFlexCellFormatter().setColSpan(rowIndex, 0, 3);
                rowIndex++;
            }
            for (final CluSetInfo cluSet : cluSets) {
                int columnIndex = 0;
                final String cluSetId = cluSet.getId();
                HorizontalPanel cluSetNamePanel = new HorizontalPanel();
                Anchor cluSetNameLabel = new Anchor(cluSet.getName());
                cluSetNameLabel.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        String url = "http://" + Window.Location.getHost() + Window.Location.getPath() + "?view=" + AppLocations.Locations.VIEW_CLU_SET + "&docId=" + cluSetId;
                        String features = "height=600,width=960,dependent=0,directories=1," + "fullscreen=1,location=1,menubar=1,resizable=1,scrollbars=1,status=1,toolbar=1";
                        Window.open(url, HTMLPanel.createUniqueId(), features);

                    }
                });
                KSLabel itemType = new KSLabel("Course Set");
                itemType.getElement().getStyle().setProperty("color", "grey");
                itemType.getElement().getStyle().setPaddingLeft(5, Style.Unit.PX);
                cluSetNamePanel.add(cluSetNameLabel);
                cluSetNamePanel.add(itemType);
                boolean showCluSet = (showCluSetFlags.get(cluSet.getId()) == null) ? false : showCluSetFlags.get(cluSet.getId()).booleanValue();
                detailsTable.setWidget(rowIndex, columnIndex, cluSetNamePanel);
                detailsTable.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 1);
                columnIndex++;

                // show/hide sub cluSet details
                // increment columnIndex to make the show/hide link to show at the right most column
                columnIndex++;
                StringBuilder hideCluSetTextSb = new StringBuilder();
                if (showCluSet) {
                    hideCluSetTextSb.append("Hide Courses");
                } else {
                    hideCluSetTextSb.append("Show Courses");
                }
                KSButton hideCluSetButton = new KSButton(hideCluSetTextSb.toString(), ButtonStyle.DEFAULT_ANCHOR);
                detailsTable.setWidget(rowIndex, columnIndex, hideCluSetButton);
                rowIndex++;
                hideCluSetButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        Boolean showCluSetDetails = showCluSetFlags.get(cluSet.getId());
                        showCluSetDetails = (showCluSetDetails == null) ? false : showCluSetDetails;
                        showCluSetFlags.put(cluSet.getId(), !showCluSetDetails);
                        Boolean newShowCluSetDetails = !showCluSetDetails;
                        if (newShowCluSetDetails) {
                            CluSetInformation subCluSetInformation = cluSetInformation.getSubCluSetInformations().get(cluSet.getId());
                            if (subCluSetInformation == null) {
                                cluSetRetriever.getCluSetInformation(cluSet.getId(), new Callback<CluSetInformation>() {
                                    @Override
                                    public void exec(CluSetInformation result) {
                                        if (result != null) {
                                            cluSetInformation.getSubCluSetInformations().put(cluSet.getId(), result);
                                            redraw();
                                        }
                                    }
                                });
                            } else {
                                redraw();
                            }
                        } else {
                            redraw();
                        }
                    }
                });
                if (showCluSet) {
                    CluSetInformation subCluSetInformation = cluSetInformation.getSubCluSetInformations().get(cluSet.getId());
                    CluSetDetailsWidget subCluSetWidget = new CluSetDetailsWidget(subCluSetInformation, cluSetRetriever);
                    subCluSetWidget.getElement().getStyle().setPaddingLeft(20, Style.Unit.PX);
                    detailsTable.setWidget(rowIndex, 0, subCluSetWidget);
                    detailsTable.getFlexCellFormatter().setColSpan(rowIndex, 0, 3);
                    rowIndex++;
                }

            }
        }
        if (membershipQueryInfo != null) {
            List<SearchParamInfo> searchParams = membershipQueryInfo.getQueryParamValues();
            if (searchParams != null) {
                HorizontalPanel queryDisplayPanel = new HorizontalPanel();
                for (SearchParamInfo searchParam : searchParams) {
                    KSLabel paramDescLabel = new KSLabel();
                    KSLabel paramValueLabel = new KSLabel();
                    String paramDesc = translateSearchKey(searchParam.getKey());
                    paramDescLabel.setText(paramDesc);
                    Object value = searchParam.getValues().get(0);
                    String displayValue = "";
                    if (value instanceof Date) {
                        DateTimeFormat DT_FORMAT =  com.google.gwt.i18n.client.DateTimeFormat.getFormat("MM/dd/yyyy") ;
                        //java.text.SimpleDateFormat DT_FOMRAT = new  java.text.SimpleDateFormat("MM/dd/yyyy");

                        displayValue = DT_FORMAT.format((Date) value);
                    } else {
                        displayValue = value.toString();
                    }
                    paramDescLabel.getElement().getStyle().setProperty("color", "grey");
                    paramDescLabel.getElement().getStyle().setPaddingRight(5, Style.Unit.PX);
                    paramValueLabel.setText(displayValue);
                    paramValueLabel.getElement().getStyle().setPaddingRight(10, Style.Unit.PX);
                    queryDisplayPanel.getElement().getStyle().setPaddingTop(10, Style.Unit.PX);
                    queryDisplayPanel.add(paramDescLabel);
                    queryDisplayPanel.add(paramValueLabel);
                }
                detailsTable.setWidget(rowIndex, 0, queryDisplayPanel);
                detailsTable.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
                rowIndex++;
            }
            if (clusInRange != null) {
                for (CluInformation clu : clusInRange) {
                    addClusDisplayToTable(rowIndex, clu);
                    rowIndex++;
                }
            } else {
                Window.alert("num clusInRange is null");
            }
        }
        mainPanel.setWidget(detailsTable);
    }

    private void addClusDisplayToTable(int rowIndex, final CluInformation clu) {
        int columnIndex = 0;
        KSButton cluCodeLink = new KSButton(clu.getCode(), ButtonStyle.DEFAULT_ANCHOR);
        cluCodeLink.getElement().getStyle().setPaddingLeft(10, Style.Unit.PX);
        detailsTable.setWidget(rowIndex, columnIndex, cluCodeLink);
        detailsTable.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 1);
        cluCodeLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String url =  "http://" + Window.Location.getHost() + Window.Location.getPath();
                if("kuali.lu.type.Variation".equals(clu.getType())){
                    url += "?view=" + AppLocations.Locations.VIEW_VARIATION;
                    url += "&docId=" + URL.encodeQueryString(clu.getParentCluId()) + "&variationId=" + URL.encodeQueryString(clu.getVerIndependentId());
                    url += "&idType=" + IdType.OBJECT_ID;
                }else if("kuali.lu.type.MajorDiscipline".equals(clu.getType())){
                    url += "?view=" + AppLocations.Locations.VIEW_PROGRAM;
                    url += "&docId=" + URL.encodeQueryString(clu.getVerIndependentId());
                    url += "&idType=" + IdType.OBJECT_ID;                    
                }else if("kuali.lu.type.CreditCourse".equals(clu.getType())){
                    url += "?view=" + AppLocations.Locations.VIEW_COURSE;
                    url += "&docId=" + URL.encodeQueryString(clu.getVerIndependentId());
                    url += "&idType=" + IdType.OBJECT_ID;
                }else if("kuali.lu.type.CoreProgram".equals(clu.getType())){
                    url += "?view=" + AppLocations.Locations.VIEW_CORE_PROGRAM;
                    url += "&docId=" + URL.encodeQueryString(clu.getVerIndependentId());
                    url += "&idType=" + IdType.OBJECT_ID;
                }else if("kuali.lu.type.credential.Baccalaureate".equals(clu.getType())){
                    url += "?view=" + AppLocations.Locations.VIEW_BACC_PROGRAM;
                    url += "&docId=" + URL.encodeQueryString(clu.getVerIndependentId());
                    url += "&idType=" + IdType.OBJECT_ID;
                }else if("kuali.lu.type.credential.Doctoral".equals(clu.getType())){
                    url += "?view=" + AppLocations.Locations.VIEW_BACC_PROGRAM;
                    url += "&docId=" + URL.encodeQueryString(clu.getVerIndependentId());
                    url += "&idType=" + IdType.OBJECT_ID;
                }else if("kuali.lu.type.credential.Masters".equals(clu.getType())){
                    url += "?view=" + AppLocations.Locations.VIEW_BACC_PROGRAM;
                    url += "&docId=" + URL.encodeQueryString(clu.getVerIndependentId());
                    url += "&idType=" + IdType.OBJECT_ID;
                }else{
                    //show error, don't know how to handle Clu type
                    KSNotifier.add(new KSNotification("This widget does not know how to open learning units of type "+clu.getType(), false, true, 5000));
                    return;
                }
                String features = "height=600,width=960,dependent=0,directories=1," + "fullscreen=1,location=1,menubar=1,resizable=1,scrollbars=1,status=1,toolbar=1";
                Window.open(url, HTMLPanel.createUniqueId().replace("-", "_"), features);
            }
        });
        columnIndex++;

        HTML cluTitleLabel = new HTML("<h5>" + clu.getTitle() + "</h5>");
        detailsTable.setWidget(rowIndex, columnIndex, cluTitleLabel);
        detailsTable.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 1);
        columnIndex++;

        if (clu.getCredits() != null && !clu.getCredits().trim().isEmpty()) {
            HTML cluCreditsLabel = new HTML("<h5>" + clu.getCredits() + " credits" + "</h5>");
            detailsTable.setWidget(rowIndex, columnIndex, cluCreditsLabel);
            detailsTable.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 1);
            columnIndex++;
        }
    }

    private String translateSearchKey(String searchKey) {
        String result = "";
        if (searchKey != null && searchKey.equals("lu.queryParam.luOptionalDivision")) {
            result = "Department";
        } else if (searchKey != null && searchKey.equals("lu.queryParam.luOptionalCrsNoRange")) {
            result = "Course Number Range";
        } else if (searchKey != null && searchKey.equals("lo.queryParam.loDescPlain")) {
            result = "Description";
        } else if (searchKey != null && searchKey.equals("lu.queryParam.luOptionalEffectiveDate1")) {
            result = "Effective From";
        } else if (searchKey != null && searchKey.equals("lu.queryParam.luOptionalEffectiveDate2")) {
            result = "Effective To";
        }

        return result;
    }

    public CluSetInformation getCluSetInformation() {
        return cluSetInformation;
    }

    public void setCluSetInformation(CluSetInformation cluSetInformation) {
        this.cluSetInformation = cluSetInformation;
    }

    public String toString() {
        return detailsTable.toString();
    }

    @Override
    public boolean isExportElement() {
        return true;
    }

    @Override
    public List<ExportElement> getExportElementSubset(ExportElement parent) {
        List<CluInformation> items = this.cluSetInformation.getClus();
        ArrayList<ExportElement> subItems = new ArrayList<ExportElement>();
        for (int i = 0; i < items.size(); i++) {
            ExportElement subelement = GWT.create(ExportElement.class);
            subelement.setViewName(parent.getViewName());
            subelement.setSectionName(parent.getSectionName());
            subelement.setFieldValue("<b>" + items.get(i).getCode() + " " + items.get(i).getTitle() + "</b>");
            subelement.setFieldValue2(items.get(i).getCredits() + " credits");
            subelement.setPrintType(ExportElement.PROPOSAL);
            subItems.add(subelement);
        }
        return subItems;
    }

    @Override
    public String getExportFieldValue() {
        return null;
    }

}
