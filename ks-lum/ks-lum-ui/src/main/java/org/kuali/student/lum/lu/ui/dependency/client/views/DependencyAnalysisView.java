package org.kuali.student.lum.lu.ui.dependency.client.views;


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
import org.kuali.student.common.ui.client.widgets.HasWatermark;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController.DependencyViews;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcService;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcServiceAsync;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyResultPanel;
import org.kuali.student.lum.lu.ui.dependency.client.widgets.DependencyResultPanel.DependencyTypeSection;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ClusetView.Picker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class DependencyAnalysisView extends ViewComposite{
       
    protected final BlockingTask initializingTask = new BlockingTask("Loading");
    
    protected DependencyAnalysisRpcServiceAsync depRpcServiceAsync = GWT.create(DependencyAnalysisRpcService.class);
    protected MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
    protected SearchRpcServiceAsync searchServiceAsync = GWT.create(SearchRpcService.class);

	private ModelDefinition searchDefinition;
	private VerticalFieldLayout container = new VerticalFieldLayout();
	protected boolean initialized = false;
	
	protected String selectedCourseId;
	protected String selectedCourseCd;
	
	protected DependencyResultPanel depResultPanel;
		
	public DependencyAnalysisView(Controller controller) {
		super(controller, "Dependency Analysis", DependencyViews.MAIN);
        this.initWidget(container);
        this.addStyleName("blockLayout");
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

		Metadata metaData = searchDefinition.getMetadata("courseId");
		KSPicker triggerPicker = new Picker(metaData.getInitialLookup(), metaData.getAdditionalLookups());

        ((HasWatermark)triggerPicker.getInputWidget()).setWatermarkText("Enter course code");
		triggerPicker.addBasicSelectionCompletedCallback(new Callback<SelectedResults>() {
            @Override
            public void exec(SelectedResults result) {
                if (result != null && result.getReturnKey() != null && result.getReturnKey().trim().length() > 0) {
                    selectedCourseId = result.getReturnKey();
                    selectedCourseCd = result.getDisplayKey();
                    if (depResultPanel != null){
                    	container.remove(depResultPanel);
                    }
                    depResultPanel = new DependencyResultPanel();
                    depResultPanel.setHeaderTitle(selectedCourseCd);		
                    container.add(depResultPanel);
                    updateDependencyResults();
                }
            }
        });

		container.setTitleWidget(header);
		container.addStyleName("blockLayout");
		container.setInstructions("Search for an item to view its dependenciess");
        container.addWidget(triggerPicker);
				
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
					String cluDetailsStr = "";
					VerticalFieldLayout crsDetails = new VerticalFieldLayout();
					crsDetails.addStyleName("KS-Indent");
					for (SearchResultCell searchResultCell : searchResultRow.getCells ()){
						if (searchResultCell.getKey().equals ("lu.resultColumn.luOptionalCode")) {
							cluCode = searchResultCell.getValue();							
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalLongName")){
							cluName = searchResultCell.getValue();
						} else if (searchResultCell.getKey().equals("lu.resultColumn.cluType")){
							cluType = searchResultCell.getValue();
							if (cluType.equals("kuali.lu.type.CreditCourse")){
								cluType = "courses";
							} else if (cluType != null){
								cluType = "programs";
							} else {
								cluType = "course sets";
							}
						} else if (searchResultCell.getKey().equals("lu.resultColumn.luOptionalDependencyType")){
							dependencyType = searchResultCell.getValue();
						} else {
							cluDetailsStr += searchResultCell.getKey() + "=" + searchResultCell.getValue() + " ";
						}
					}
					
					crsDetails.addWidget(new KSLabel("Details coming soon"));
					DependencyTypeSection typeSection = depResultPanel.getDependencyTypeSection(cluType, dependencyType);
					if (typeSection == null){						
						typeSection = depResultPanel.addDependencyTypeSection(cluType, dependencyType, getTypeLabel(cluType, dependencyType));
					}
					typeSection.addDependencyItem(cluCode + " - " + cluName, crsDetails);
				}
				
			}
		});
	}
	

	private String getTypeLabel(String cluType, String dependencyType) {			
		return selectedCourseCd + " is an " + dependencyType + " for the following " + cluType + ":";
	}			

}
