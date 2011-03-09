package org.kuali.student.lum.lu.ui.dependency.client.views;


import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.ModelDefinition;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.HasWatermark;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.dependency.client.controllers.DependencyAnalysisController.DependencyViews;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcService;
import org.kuali.student.lum.lu.ui.dependency.client.service.DependencyAnalysisRpcServiceAsync;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ClusetView.Picker;

import com.google.gwt.core.client.GWT;

public class DependencyAnalysisView extends ViewComposite{
       
    protected final BlockingTask initializingTask = new BlockingTask("Loading");
    
    protected DependencyAnalysisRpcServiceAsync depRpcServiceAsync = GWT.create(DependencyAnalysisRpcService.class);
    protected MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);

	private ModelDefinition searchDefinition;
	private VerticalFieldLayout container = new VerticalFieldLayout();
	protected boolean initialized = false;
	protected String selectedCourseId;
	protected KSLabel courseTitleLabel = new KSLabel();
    protected SectionTitle sectionTitle = SectionTitle.generateH3Title("");	
    protected VerticalSection section = new VerticalSection(sectionTitle);

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
                    sectionTitle.setText(result.getDisplayKey());
                    updateDependencyResults();
                }
            }
        });

		courseTitleLabel.addStyleName("cluSetTitle");
		container.setTitleWidget(header);
		container.addStyleName("blockLayout");
		container.setInstructions("Search for an item to view its dependenciess");
        container.addWidget(triggerPicker);
		container.add(section);
				
        section.addStyleName(LUUIConstants.STYLE_SECTION_DIVIDER);
        section.setVisible(false);
        
        CollapsableSection preReq = new CollapsableSection("This course is a pre-requisite for the following courses");                
        CollapsableSection coReq = new CollapsableSection("This course is a co-requisite for the following courses");
        CollapsableSection antiReq = new CollapsableSection("This course is a anti-requisite for the following courses");
        
        section.add(preReq);
        section.add(coReq);
        section.add(antiReq);        
	}

	protected void updateDependencyResults(){
		section.setVisible(true);
	}
}
