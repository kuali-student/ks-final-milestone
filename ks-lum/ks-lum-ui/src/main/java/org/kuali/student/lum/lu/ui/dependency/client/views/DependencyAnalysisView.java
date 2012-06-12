package org.kuali.student.lum.lu.ui.dependency.client.views;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.filter.FilterEvent;
import org.kuali.student.common.ui.client.widgets.filter.FilterEventHandler;
import org.kuali.student.common.ui.client.widgets.filter.FilterResetEventHandler;
import org.kuali.student.common.ui.client.widgets.filter.KSFilterOptions;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulePreviewWidget;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.CluSetDetailsWidget;
import org.kuali.student.lum.common.client.widgets.CluSetRetriever;
import org.kuali.student.lum.common.client.widgets.CluSetRetrieverImpl;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController.DependencyViews;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcService;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcServiceAsync;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyResultPanel;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyResultPanel.DependencyTypeSection;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ClusetView.Picker;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.ModelDefinition;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
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
	protected KSFilterOptions dependencyFilter;
	protected HorizontalBlockFlowPanel resultContainer = new HorizontalBlockFlowPanel();
	
	private final BlockingTask loadDataTask = new BlockingTask("Retrieving Data");
	
	private KSDocumentHeader header;
		
	public DependencyAnalysisView(Controller controller) {
		super(controller, "Dependency Analysis", DependencyViews.MAIN);
        this.initWidget(container);
        this.addStyleName("blockLayout");
        this.addStyleName("ks-dependency-container");
	}
	
	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (!initialized) {
    		KSBlockingProgressIndicator.addTask(initializingTask);

    		//This loads search definitions for the dependency analysis search 
            metadataServiceAsync.getMetadata("dependency", null, null, new KSAsyncCallback<Metadata>(){

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
		this.header = new KSDocumentHeader();
		
        header.setTitle("Dependency Analysis");        

        //Get search definition for dependency analysis trigger search and create trigger picker          
		Metadata metaData = searchDefinition.getMetadata("courseId");
		final KSPicker triggerPicker = new Picker(metaData.getInitialLookup(), metaData.getAdditionalLookups());
        ((HasWatermark)triggerPicker.getInputWidget()).setWatermarkText("Enter course code");	
        triggerPicker.getInputWidget().ensureDebugId("Dependency-Analysis-Course-Code");

        //Setup the "go" button for trigger picker
        KSButton goButton = new KSButton("Go", ButtonStyle.PRIMARY_SMALL);
		goButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {				
                selectedCourseId = triggerPicker.getValue().toString();
                KSSuggestBox suggestBox = (KSSuggestBox)triggerPicker.getInputWidget();
                IdableSuggestion selectedSuggestion = suggestBox.getCurrentSuggestion();
                
                // We need to make sure we only search for courses that have been selected from the suggest box
                if(selectedSuggestion != null && selectedSuggestion.getReplacementString().equalsIgnoreCase(suggestBox.getText())){
	                selectedCourseName = selectedSuggestion.getAttrMap().get("lu.resultColumn.luOptionalLongName");
	                if (!selectedCourseId.equals(UtilConstants.IMPOSSIBLE_CHARACTERS)){
	    				KSBlockingProgressIndicator.addTask(loadDataTask);
	                	selectedCourseCd = triggerPicker.getDisplayValue();                
		                if (depResultPanel != null){
		                	resultContainer.remove(depResultPanel);
		                }
		                depResultPanel = new DependencyResultPanel();
		                depResultPanel.setHeaderTitle(selectedCourseCd + " - " + selectedCourseName);		
		                resultContainer.add(depResultPanel);
		                resultContainer.setVisible(true);
		                updateDependencyResults();
		                
		                ((DependencyAnalysisController) DependencyAnalysisView.this.getController()).showExport(isExportButtonActive());
		                header.showPrint(true);
		                header.setPrintContent(depResultPanel); // we only want to print the results panel
	                }
	                
                }
			}
			
		});

		
        //Create search section with the trigger picker and go button
		HorizontalPanel pickerPanel = new HorizontalPanel();
		pickerPanel.add(triggerPicker);
		pickerPanel.add(goButton);
        
		FlowPanel searchPanel = new FlowPanel();
		searchPanel.addStyleName("ks-dependency-search");
		searchPanel.add(new KSLabel("Search for item to view its dependencies"));
		searchPanel.add(pickerPanel);		
		
		//Add widgets to view
		container.setTitleWidget(header);
		container.addStyleName("blockLayout");
        container.addWidget(searchPanel);
        
        List<LookupMetadata> lookups = new ArrayList<LookupMetadata>();
        metaData = searchDefinition.getMetadata("filter");
        lookups.add(metaData.getInitialLookup());
        
        dependencyFilter = new KSFilterOptions(metaData.getAdditionalLookups());
        dependencyFilter.addFilterEventHandler(new FilterEventHandler(){

			@Override
			public void onDeselect(FilterEvent e) {
				depResultPanel.hide(e.getFilterKey(), e.getFilterValue());
			}

			@Override
			public void onSelect(FilterEvent e) {
				if (e.isInitialSelect()){
					depResultPanel.hideAll();
				}
				depResultPanel.show(e.getFilterKey(), e.getFilterValue());			
			}
        	
        });
        
        dependencyFilter.addFilterResetEventHandler(new FilterResetEventHandler(){

			@Override
			public void onReset() {
				depResultPanel.showAll();				
			}        	
        });
        
        resultContainer.add(dependencyFilter);
        resultContainer.setVisible(false);
        
        container.addWidget(resultContainer);
	}

	/*
	 * This method calls the dependency analysis search with the selected trigger from the picker,
	 * and updates the DependencyResultPanel with the results from the search. 
	 */
	protected void updateDependencyResults(){
		//Setup up sections for DependencyResultPanel		
		depResultPanel.addSection(LUUIConstants.DEP_SECTION_COURSE,"Course Dependencies");		
		depResultPanel.addSection(LUUIConstants.DEP_SECTION_PROGRAM,"Program Dependencies");
		depResultPanel.addSection(LUUIConstants.DEP_SECTION_COURSE_SET, "Course Set Inclusions");		
			
		dependencyFilter.reset();
		
		//Setup and invoke the dependency analysis search and process the results
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
					
					//TODO: This should not use hard-coded result columns 
					String cluCode = "";
					String cluName = "";
					String cluType = "";
					String dependencyType = "";
					String dependencyTypeName = "";
					String reqComponentIds = "";
					String reqRootId = "";
					String cluId = "";
					String curriculumOversightNames = "";
					String dependencySectionKey = "";
					Boolean diffAdminOrg = false;
					String parentCluId = "";
					
					for (SearchResultCell searchResultCell : searchResultRow.getCells ()){
						if (searchResultCell.getKey().equals ("lu.resultColumn.luOptionalCode")) {
							cluCode = searchResultCell.getValue();	
						} if (searchResultCell.getKey().equals ("lu.resultColumn.cluId")) {
							cluId = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalLongName")){
							cluName = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.cluType")){
							cluType = searchResultCell.getValue();
							if (cluType.equals(LUUIConstants.CLU_TYPE_CREDIT_COURSE)){
								dependencySectionKey = LUUIConstants.DEP_SECTION_COURSE;
							} else if (cluType != null){
								dependencySectionKey = LUUIConstants.DEP_SECTION_PROGRAM;
							}
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyType")){
							dependencyType = searchResultCell.getValue();
							if (dependencyType.equals("cluSet")){
								dependencySectionKey = LUUIConstants.DEP_SECTION_COURSE_SET;
							} else if (LUUIConstants.DEP_TYPE_JOINT.equals(dependencyType) || 
										LUUIConstants.DEP_TYPE_CROSS_LISTED.equals(dependencyType)){
								dependencySectionKey = LUUIConstants.DEP_SECTION_COURSE;;
							}
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyTypeName")){
							dependencyTypeName = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyRequirementComponentIds")){
							reqComponentIds = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyRootId")){
							reqRootId = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalOversightCommitteeNames")){
							curriculumOversightNames = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyRequirementDifferentAdminOrg")){
							diffAdminOrg = ("true").equals(searchResultCell.getValue());
						} else if (searchResultCell.getKey().equals("lu.resultColumn.parentCluId")){
							parentCluId = searchResultCell.getValue();
						}
						
							
					}
					
					//Get the dependency type section to add the dependency to, create new one if this is the first dependency for the t
					DependencyTypeSection typeSection = depResultPanel.getDependencyTypeSection(dependencySectionKey, dependencyType);
					if (typeSection == null){						
						typeSection = depResultPanel.addDependencyTypeSection(dependencySectionKey, dependencyType, getDependencyTypeLabel(dependencySectionKey, dependencyType, dependencyTypeName));
					}
					
					//If dependency has details, create the details widget
					VerticalFieldLayout depDetails = null;
					if (hasDependencyDetails(dependencyType)){
						depDetails = getDependencyDetails(dependencySectionKey, dependencyType, cluCode, reqRootId, reqComponentIds);
						KSLabel curricOversightLabel = new KSLabel("Curriculum Oversight: " + curriculumOversightNames);
						curricOversightLabel.addStyleName("ks-dependency-oversight");
						depDetails.addWidget(curricOversightLabel);
					}

					//Add the dependency to the dependency section
					typeSection.addDependencyItem(getDependencyLabel(dependencySectionKey, dependencyType, cluId, cluCode, cluName, cluType, diffAdminOrg, parentCluId), depDetails);
				}
				
				depResultPanel.finishLoad();
				KSBlockingProgressIndicator.removeTask(loadDataTask);
			}
			
		});
	}
	

	private VerticalFieldLayout getDependencyDetails(String dependencySectionKey, String dependencyType, final String cluCode, final String rootId, String reqComponentIds){
		VerticalFieldLayout depDetails = new VerticalFieldLayout();
		depDetails.addStyleName("KS-Dependency-Details");
		if (reqComponentIds != null && !reqComponentIds.isEmpty()){
			List<String> reqComponentIdList = Arrays.asList(reqComponentIds.split(","));

			final VerticalFlowPanel simpleRequirement = new VerticalFlowPanel();			
			simpleRequirement.addStyleName("KS-Dependency-Simple-Rule");
			
			//For programs, add ability to display both the simple requirement and complex requirement for the details
			if (LUUIConstants.DEP_SECTION_PROGRAM.equals(dependencySectionKey)){
				final KSButton complexReqLabel = new KSButton("Display complex requirement", ButtonStyle.DEFAULT_ANCHOR);
				final KSButton simpleReqLabel = new KSButton("Display simple requirement", ButtonStyle.DEFAULT_ANCHOR);
				
				//Initialize the complex requirement panel, set it so it is not initially open (ie. not visible)
				final FlowPanel complexContent = new FlowPanel();
				final CollapsablePanel complexRequirement = GWT.create(CollapsablePanel.class);
				complexRequirement.initialise("", complexContent, false, false);
                
				complexContent.addStyleName("KS-Dependency-Complex-Rule");
				depDetails.addWidget(simpleRequirement);
				depDetails.addWidget(complexRequirement);
				depDetails.addWidget(complexReqLabel);
				depDetails.addWidget(simpleReqLabel);
				
				simpleReqLabel.setVisible(false);
				
				//Click handler to display the complex requirement, which will go and fetch the requirement components
				//if displaying for first time.
				complexReqLabel.addClickHandler(new ClickHandler(){
					public void onClick(ClickEvent event) {
						simpleReqLabel.setVisible(true);
						simpleRequirement.setVisible(false);
						complexReqLabel.setVisible(false);

						if (complexContent.getWidgetCount() <= 0){
							KSBlockingProgressIndicator.addTask(loadDataTask);
							//TODO: This code copied from program screens, need common reusable code
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
				
				//Click handler to display the simple requirement only
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
						SpanPanel requirement = new SpanPanel();
						requirement.setHTML(reqCompNL.substring(0,codeIdx) + 
								" <b>" + selectedCourseCd + "</b>" + reqCompNL.substring(codeIdx + selectedCourseCd.length()));
						simpleRequirement.add(requirement);
					}								
				}							
			});
		}

		return depDetails;
	}
	
	//This generates the label for the dependency type section (eg. Pre-req requirement, course set inclusion, etc).
	private SpanPanel getDependencyTypeLabel(String dependencySectionKey, String dependencyType, String dependencyTypeName) {
		SpanPanel header = new SpanPanel();
		if (dependencyType.equals("joint") || dependencyType.equals("crossListed")){
			header.setHTML("<b>" + selectedCourseCd + "</b> is <b>" + dependencyTypeName + "</b> with the following ");
		} else if (dependencySectionKey.equals("course sets")){
			header.setHTML("<b>" + selectedCourseCd + "</b> belongs to the following course sets");
		} else {
			header.setHTML("<b>" + selectedCourseCd + "</b> is a/an <b>" + dependencyTypeName + "</b> for the following " + dependencySectionKey);			
		}
    	header.setStyleName("KS-DependencyType-Label");
		return header;
	}
	
	/*
	 * This generates the title for the actual dependency (eg. course, course set, program), which may include links to 
	 * view the actual course, course set or program
	 */
	private SpanPanel getDependencyLabel(final String dependencySectionKey, String dependencyType, final String cluId,  String cluCode, String cluName, final String cluType, boolean diffAdminOrg, final String parentCluId){
		
		//Figure out the view hyperlink for course/program/course set screen based on dependencyType
		Anchor viewLinkAnchor = null;
		final String viewLinkUrl;
		if (LUUIConstants.CLU_TYPE_CREDIT_COURSE.equals(cluType) && 
				!LUUIConstants.DEP_TYPE_CROSS_LISTED.equals(dependencyType)){
			viewLinkAnchor = new Anchor("View Course");
			viewLinkUrl = AppLocations.Locations.VIEW_COURSE.toString(); 
		} else if ("kuali.lu.type.MajorDiscipline".equals(cluType) || "kuali.lu.type.Variation".equals(cluType)){
			viewLinkAnchor = new Anchor("View Program ");
			viewLinkUrl = AppLocations.Locations.VIEW_PROGRAM.toString(); 
		} else if ("kuali.lu.type.CoreProgram".equals(cluType)){
			viewLinkAnchor = new Anchor("View Program ");
			viewLinkUrl = AppLocations.Locations.VIEW_CORE_PROGRAM.toString(); 
		} else if (cluType.startsWith("kuali.lu.type.credential")){
			viewLinkAnchor = new Anchor("View Program ");
			viewLinkUrl = AppLocations.Locations.VIEW_BACC_PROGRAM.toString(); 
		} else if (LUUIConstants.DEP_TYPE_COURSE_SET.equals(dependencyType)){
			viewLinkAnchor = new Anchor("View Course Set");
			viewLinkUrl = AppLocations.Locations.VIEW_CLU_SET.toString();
		} else {
			viewLinkUrl = null;
		}
		
		//Add click handler to open new window for user to view the course/program/cluset in more detail
		if (viewLinkAnchor != null){
	    	viewLinkAnchor.addStyleName("KS-Dependency-View-Link");
	        viewLinkAnchor.addClickHandler(new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
					String url =  "http://" + Window.Location.getHost() + Window.Location.getPath() +
						"?view=" + viewLinkUrl;
					if("kuali.lu.type.Variation".equals(cluType)){
					    url += "&docId=" + URL.encodeQueryString(parentCluId) + "&variationId=" + URL.encodeQueryString(cluId);
					}else {
					    url += "&docId=" + URL.encodeQueryString(cluId);
					}
					String features = "height=600,width=960,dependent=0,directories=1," +
							"fullscreen=1,location=1,menubar=1,resizable=1,scrollbars=1,status=1,toolbar=1";
					Window.open(url, HTMLPanel.createUniqueId(), features);				
				}
			});
		}		
			
		//Determine if we need to display differing org highlight
		String orgHighlight = (diffAdminOrg ? " <span class=\"ks-dependency-highlight\">Different Org</span> ":"");
		
		//Create dependency item header label
		String headerLabel = "";
		if (LUUIConstants.DEP_TYPE_CROSS_LISTED.equals(dependencyType)){
			headerLabel = cluCode + " - " + selectedCourseName + orgHighlight;
		} else if (LUUIConstants.DEP_TYPE_COURSE_SET.equals(dependencyType)){
			headerLabel = cluName;
		} else {
			headerLabel = cluCode + " - " + cluName + orgHighlight;
		}
    	
		//Build header widget to return
		SpanPanel header = new SpanPanel();		
		header.setStyleName("KS-DependencyType-Label");	
		header.setHTML(headerLabel);
		if (viewLinkAnchor != null){
			header.add(viewLinkAnchor);
		}
		
		return header;		
	}

	protected boolean hasDependencyDetails(String dependencyType){
		//Only course and programs have dependency details, since there could be multiple program types, easier to
		//check those that don't have details to show.
		return !(LUUIConstants.DEP_TYPE_JOINT.equals(dependencyType) || 
					LUUIConstants.DEP_TYPE_CROSS_LISTED.equals(dependencyType) || 
					LUUIConstants.DEP_TYPE_COURSE_SET.equals(dependencyType));
	}
	
    private String getTotalCreditsString(int min, int max) {
        return "Expected Total Credits:" + min + "-" + max;
    }

    //TODO: This code copied from Program summary, need to create reusable version for this and program summary
    protected Map<String, Widget> getCluSetWidgetList(StatementTreeViewInfo rule) {
        Map<String, Widget> widgetList = new HashMap<String, Widget>();
        Set<String> cluSetIds = new HashSet<String>();
        findCluSetIds(rule, cluSetIds);
        for (String clusetId : cluSetIds) {
            widgetList.put(clusetId, new CluSetDetailsWidget(clusetId, cluSetRetriever));
        }

        return widgetList;
    }

    //TODO: This code copied from Program summary, need to create reusable version for this and program summary
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

	public KSDocumentHeader getHeader() {
		return header;
	}

	@Override
	public boolean isExportButtonActive() {
		return true;
	}

	public DependencyResultPanel getDepResultPanel() {
		return depResultPanel;
	}

}
