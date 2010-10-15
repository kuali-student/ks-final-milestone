package org.kuali.student.lum.common.client.widgets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;

public class CluSetDetailsWidget extends Composite {
    
    private CluSetInformation cluSetInformation;
    private SimplePanel mainPanel;
    private FlexTable detailsTable = new FlexTable();
    private boolean showClus;
    private Map<String, Boolean> showCluSetFlags = new HashMap<String, Boolean>();
    private static final SimpleDateFormat DT_FOMRAT = new SimpleDateFormat("MM/dd/yyyy");
    private CluSetRetriever cluSetRetriever;
//    private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync;
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
        coursesHeader.getElement().getStyle().setProperty("borderBottom", "1px solid #D8D8D8");
        detailsTable.setWidget(rowIndex, 0, coursesHeader);
        detailsTable.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
        if (cluSets != null && cluSets.size() > 0 ||
                clusInRange != null && clusInRange.size() > 0) {
            coursesHeader.setVisible(true);
        } else {
            coursesHeader.setVisible(false);
        }
        {
            // show/hide clus
            int numClus = (clus == null)? 0 : clus.size();
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
                Hyperlink cluSetNameLabel = new Hyperlink(cluSet.getName(),
                        "/HOME/CURRICULUM_HOME/CLU_SETS" + "/" +
                        "VIEW" + "&docId=" + cluSetId);
                KSLabel itemType = new KSLabel("Course Set");
                itemType.getElement().getStyle().setProperty("color", "grey");
                itemType.getElement().getStyle().setPaddingLeft(5, Style.Unit.PX);
                cluSetNamePanel.add(cluSetNameLabel);
                cluSetNamePanel.add(itemType);
                boolean showCluSet = (showCluSetFlags.get(cluSet.getId()) == null)? false :
                    showCluSetFlags.get(cluSet.getId()).booleanValue();
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
                        showCluSetDetails = (showCluSetDetails == null)? false : showCluSetDetails;
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
            List<SearchParam> searchParams = membershipQueryInfo.getQueryParamValueList();
            if (searchParams != null) {
                HorizontalPanel queryDisplayPanel = new HorizontalPanel();
                for (SearchParam searchParam : searchParams) {
                    KSLabel paramDescLabel = new KSLabel();
                    KSLabel paramValueLabel = new KSLabel();
                    String paramDesc = translateSearchKey(searchParam.getKey());
                    paramDescLabel.setText(paramDesc);
                    Object value = searchParam.getValue();
                    String displayValue = "";
                    if (value instanceof Date) {
                        displayValue = DT_FOMRAT.format((Date)value);
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
                ViewContext viewContext = new ViewContext();
                viewContext.setId(clu.getId());
                viewContext.setIdType(IdType.OBJECT_ID);
                Application.navigate("/HOME/CURRICULUM_HOME/VIEW_COURSE", viewContext);
            }
        });
        columnIndex++;
        
        KSLabel cluTitleLabel = new KSLabel(clu.getTitle());
        detailsTable.setWidget(rowIndex, columnIndex, cluTitleLabel);
        detailsTable.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 1);
        columnIndex++;
        
        if (clu.getCredits() != null && !clu.getCredits().trim().isEmpty()) {
            KSLabel cluCreditsLabel = new KSLabel(clu.getCredits());
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
        } else if (searchKey != null && searchKey.equals("lu.queryParam.luOptionalEffectiveDate2")) {
            
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
}
