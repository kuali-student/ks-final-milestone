package org.kuali.student.lum.lu.ui.dependency.client.views;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.ModelDefinition;
import org.kuali.student.common.search.dto.SearchParam;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultCell;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.util.UtilConstants;
import org.kuali.student.common.ui.client.widgets.HasWatermark;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulePreviewWidget;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.CluSetDetailsWidget;
import org.kuali.student.lum.common.client.widgets.CluSetRetriever;
import org.kuali.student.lum.common.client.widgets.CluSetRetrieverImpl;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController.DependencyViews;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcService;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcServiceAsync;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyResultPanel;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyResultPanel.DependencyTypeSection;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ClusetView.Picker;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DependencyAnalysisView extends ViewComposite{
       
	private static CluSetRetriever cluSetRetriever = new CluSetRetrieverImpl();
	
    protected final BlockingTask initializingTask = new BlockingTask("Loading");
    
    protected DependencyAnalysisRpcServiceAsync depRpcServiceAsync = GWT.create(DependencyAnalysisRpcService.class);
    protected MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
    protected SearchRpcServiceAsync searchServiceAsync = GWT.create(SearchRpcService.class);

	private ModelDefinition searchDefinition;
	private VerticalFieldLayout container = new VerticalFieldLayout();
	protected boolean initialized = false;
	
	protected String selectedCourseId;
	protected String selectedCourseCd;
	protected String selectedCourseName;
	
	protected DependencyResultPanel depResultPanel;
	
	private final BlockingTask loadDataTask = new BlockingTask("Retrieving Data");
		
	public DependencyAnalysisView(Controller controller) {
		super(controller, "Dependency Analysis", DependencyViews.MAIN);
        this.initWidget(container);
        this.addStyleName("blockLayout");
        this.addStyleName("ks-menu-layout-rightColumn");
	}
	
	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (!initialized) {
    		KSBlockingProgressIndicator.addTask(initializingTask);
            metadataServiceAsync.getMetadata("search", null, null, new KSAsyncCallback<Metadata>(){

                @Override
                public void handleFailure(Throwable caught) {
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                    throw new RuntimeException("Failed to load search definitions.", caught);                        
                }

                @Override
                public void onSuccess(Metadata result) {
                	searchDefinition = new DataModelDefinition(result);
                	init();
                	onReadyCallback.exec(true);                	
                    initialized = true;
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                }                
            });	        
        } else {
        	onReadyCallback.exec(true);
        }
	}

	protected void init(){
		KSDocumentHeader header = new KSDocumentHeader();
        header.setTitle("Dependency Analysis");

        HorizontalPanel pickerPanel = new HorizontalPanel();
		Metadata metaData = searchDefinition.getMetadata("courseId");
		final KSPicker triggerPicker = new Picker(metaData.getInitialLookup(), metaData.getAdditionalLookups());

        ((HasWatermark)triggerPicker.getInputWidget()).setWatermarkText("Enter course code");		
		KSButton goButton = new KSButton("Go", ButtonStyle.PRIMARY_SMALL);
		goButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {				
                selectedCourseId = triggerPicker.getValue().toString();
                KSSuggestBox suggestBox = (KSSuggestBox)triggerPicker.getInputWidget();
                IdableSuggestion selectedSuggestion = suggestBox.getCurrentSuggestion();
                
                selectedCourseName = selectedSuggestion.getAttrMap().get("lu.resultColumn.luOptionalLongName");
                if (!selectedCourseId.equals(UtilConstants.IMPOSSIBLE_CHARACTERS)){
    				KSBlockingProgressIndicator.addTask(loadDataTask);
                	selectedCourseCd = triggerPicker.getDisplayValue();                
	                if (depResultPanel != null){
	                	container.remove(depResultPanel);
	                }
	                depResultPanel = new DependencyResultPanel();
	                depResultPanel.setHeaderTitle(selectedCourseCd + " - " + selectedCourseName);		
	                container.add(depResultPanel);
	                updateDependencyResults();
                }
			}
			
		});

		
		pickerPanel.add(triggerPicker);
		pickerPanel.add(goButton);
        
		FlowPanel searchPanel = new FlowPanel();
		searchPanel.addStyleName("ks-dependency-search");
		searchPanel.add(new KSLabel("Search for item to view its dependencies"));
		searchPanel.add(pickerPanel);
		
		
		container.setTitleWidget(header);
		container.addStyleName("blockLayout");
        container.addWidget(searchPanel);
				
	}

	protected void updateDependencyResults(){
		
		depResultPanel.addSection("courses","Course Dependencies");		
		depResultPanel.addSection("programs","Program Dependencies");
		depResultPanel.addSection("course sets", "Course Set Inclusions");		

			
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("lu.search.dependencyAnalysis");
		
		SearchParam searchParam = new SearchParam();
		searchParam.setKey("lu.queryParam.luOptionalCluId");
		searchParam.setValue(selectedCourseId);				
		searchRequest.getParams().add(searchParam);
				
		searchServiceAsync.search(searchRequest, new KSAsyncCallback<SearchResult>(){

			@Override
			public void onSuccess(SearchResult searchResults) {				
				for (SearchResultRow searchResultRow : searchResults.getRows ()) {
					String cluCode = "";
					String cluName = "";
					String cluType = "";
					String dependencyType = "";
					String dependencyTypeName = "";
					String reqComponentIds = "";
					String reqRootId = "";
					String cluId = "";
					String curriculumOversightNames = "";
					
					for (SearchResultCell searchResultCell : searchResultRow.getCells ()){
						if (searchResultCell.getKey().equals ("lu.resultColumn.luOptionalCode")) {
							cluCode = searchResultCell.getValue();	
						} if (searchResultCell.getKey().equals ("lu.resultColumn.cluId")) {
							cluId = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalLongName")){
							cluName = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.cluType")){
							cluType = searchResultCell.getValue();
							if (cluType.equals("kuali.lu.type.CreditCourse")){
								cluType = "courses";
							} else if (cluType != null){
								cluType = "programs";
							}
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyType")){
							dependencyType = searchResultCell.getValue();
							if (dependencyType.equals("cluSet")){
								cluType = "course sets";
							}
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyTypeName")){
							dependencyTypeName = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyRequirementComponentIds")){
							reqComponentIds = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyRootId")){
							reqRootId = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalOversightCommitteeNames")){
							curriculumOversightNames = searchResultCell.getValue();
						}
					}
					
					DependencyTypeSection typeSection = depResultPanel.getDependencyTypeSection(cluType, dependencyType);
					if (typeSection == null){						
						typeSection = depResultPanel.addDependencyTypeSection(cluType, dependencyType, getDependencyTypeLabel(cluType, dependencyTypeName));
					}
					
					VerticalFieldLayout depDetails = getDependencyDetails(cluType, dependencyType, cluCode, reqRootId, reqComponentIds);
					KSLabel curricOversightLabel = new KSLabel("Curriculum Oversight: " + curriculumOversightNames);
					curricOversightLabel.addStyleName("ks-dependency-oversight");
					depDetails.addWidget(curricOversightLabel);
					typeSection.addDependencyItem(getDependencyLabel(cluType, cluId, cluCode, cluName), depDetails);
				}
				depResultPanel.finishLoad();
				KSBlockingProgressIndicator.removeTask(loadDataTask);
			}
			
		});
	}
	

	private VerticalFieldLayout getDependencyDetails(String cluType, String dependencyType, final String cluCode, final String rootId, String reqComponentIds){
		VerticalFieldLayout depDetails = new VerticalFieldLayout();
		depDetails.addStyleName("KS-Dependency-Details");
		if (reqComponentIds != null && !reqComponentIds.isEmpty()){
			List<String> reqComponentIdList = Arrays.asList(reqComponentIds.split(","));
			final SpanPanel simpleRequirement = new SpanPanel();
			
			simpleRequirement.addStyleName("KS-Dependency-Simple-Rule");
			
			//Add expand to display complex program rules
			if (cluType.equals("programs")){
				final KSButton complexReqLabel = new KSButton("Display complex requirement", ButtonStyle.DEFAULT_ANCHOR);
				final KSButton simpleReqLabel = new KSButton("Display simple requirement", ButtonStyle.DEFAULT_ANCHOR);
				
				final FlowPanel complexContent = new FlowPanel();
				final CollapsablePanel complexRequirement = new CollapsablePanel("", complexContent, false, false);				
				
				complexContent.addStyleName("KS-Dependency-Complex-Rule");
				depDetails.addWidget(simpleRequirement);
				depDetails.addWidget(complexRequirement);
				depDetails.addWidget(complexReqLabel);
				depDetails.addWidget(simpleReqLabel);
				
				simpleReqLabel.setVisible(false);
				
				complexReqLabel.addClickHandler(new ClickHandler(){
					public void onClick(ClickEvent event) {
						simpleReqLabel.setVisible(true);
						simpleRequirement.setVisible(false);
						complexReqLabel.setVisible(false);

						if (complexContent.getWidgetCount() <= 0){
							KSBlockingProgressIndicator.addTask(loadDataTask);
							depRpcServiceAsync.getProgramRequirement(rootId, new KSAsyncCallback<ProgramRequirementInfo>(){
								public void onSuccess(ProgramRequirementInfo progReqInfo) {															
							        int minCredits = (progReqInfo.getMinCredits() == null ? 0 : progReqInfo.getMinCredits());
							        int maxCredits = (progReqInfo.getMaxCredits() == null ? 0 : progReqInfo.getMaxCredits());
							        String plainDesc = (progReqInfo.getDescr() == null ? "" : progReqInfo.getDescr().getPlain());
							        final RulePreviewWidget rulePreviewWidget = new RulePreviewWidget(1, progReqInfo.getShortTitle(),
							                getTotalCreditsString(minCredits, maxCredits),
							                plainDesc, progReqInfo.getStatement(),
							                true, getCluSetWidgetList(progReqInfo.getStatement()));
							        complexContent.add(rulePreviewWidget);
							        KSBlockingProgressIndicator.removeTask(loadDataTask);
									complexRequirement.open();
								}							
							});
						} else {
							complexRequirement.open();
						}
					}					
				});
				
				simpleReqLabel.addClickHandler(new ClickHandler(){
					public void onClick(ClickEvent event) {
						complexReqLabel.setVisible(true);						
						complexRequirement.close();
						simpleRequirement.setVisible(true);
						simpleReqLabel.setVisible(false);						
					}
				});
			} else {
				depDetails.addWidget(simpleRequirement);
			}
			
			depRpcServiceAsync.getRequirementComponentNL(reqComponentIdList, new KSAsyncCallback<List<String>>(){
				public void onSuccess(List<String> results) {
					for (String reqCompNL:results){
						int codeIdx = reqCompNL.indexOf(selectedCourseCd);						
						simpleRequirement.setHTML(reqCompNL.substring(0,codeIdx) + 
								" <b>" + selectedCourseCd + "</b>" + reqCompNL.substring(codeIdx + selectedCourseCd.length()));
					}								
				}							
			});
		}

		return depDetails;
	}
	
	private SpanPanel getDependencyTypeLabel(String cluType, String dependencyType) {
		SpanPanel header = new SpanPanel();
		if (cluType.equals("course sets")){
			header.setHTML("<b>" + selectedCourseCd + "</b> belongs to the following course sets");
		} else {
			header.setHTML("<b>" + selectedCourseCd + "</b> is a/an <b>" + dependencyType + "</b> for the following " + cluType);			
		}
    	header.setStyleName("KS-DependencyType-Label");
		return header;
	}
	
	private SpanPanel getDependencyLabel(final String cluType, final String cluId,  String cluCode, String cluName){
		SpanPanel header = new SpanPanel();
		final String viewLink;
		Anchor viewCourse;
		if (cluType.equals("courses")){
			viewCourse = new Anchor("View Course");
			viewLink = AppLocations.Locations.VIEW_COURSE.toString(); 
		} else {
			viewCourse = new Anchor("View Program");
			viewLink = AppLocations.Locations.VIEW_PROGRAM.toString(); 
		}

		header.setHTML(cluCode + " - " + cluName);
    	header.setStyleName("KS-DependencyType-Label");
    	viewCourse.addStyleName("KS-Dependency-View-Link");
        viewCourse.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				String url =  "http://" + Window.Location.getHost() + Window.Location.getPath() +
					"?view=" + viewLink + "&docId=" + cluId;
				String features = "height=600,width=960,dependent=0,directories=1," +
						"fullscreen=1,location=1,menubar=1,resizable=1,scrollbars=1,status=1,toolbar=1";
				Window.open(url, HTMLPanel.createUniqueId(), features);				
			}
		});
    	header.add(viewCourse);
		return header;		
	}

    private String getTotalCreditsString(int min, int max) {
        return "Expected Total Credits:" + min + "-" + max;
    }

    protected Map<String, Widget> getCluSetWidgetList(StatementTreeViewInfo rule) {
        Map<String, Widget> widgetList = new HashMap<String, Widget>();
        Set<String> cluSetIds = new HashSet<String>();
        findCluSetIds(rule, cluSetIds);
        for (String clusetId : cluSetIds) {
            widgetList.put(clusetId, new CluSetDetailsWidget(clusetId, cluSetRetriever));
        }

        return widgetList;
    }

    private void findCluSetIds(StatementTreeViewInfo rule, Set<String> list) {

        List<StatementTreeViewInfo> statements = rule.getStatements();
        List<ReqComponentInfo> reqComponentInfos = rule.getReqComponents();

        if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            for (StatementTreeViewInfo statement : statements) {
                findCluSetIds(statement, list); // inside set the children of this statementTreeViewInfo
            }
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
            for (ReqComponentInfo reqComponent : reqComponentInfos) {
                List<ReqCompFieldInfo> fieldInfos = reqComponent.getReqCompFields();
                for (ReqCompFieldInfo fieldInfo : fieldInfos) {
                    if (RulesUtil.isCluSetWidget(fieldInfo.getType())) {
                        list.add(fieldInfo.getValue());
                    }
                }
            }
        }
    }

}
