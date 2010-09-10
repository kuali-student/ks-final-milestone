package org.kuali.student.lum.lu.ui.course.client.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.application.ViewContext.IdType;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.common.client.lo.CategoryManagement;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CurriculumHomeView extends ViewComposite{
	
	private final SpanPanel container = new SpanPanel();
	private final KSListPanel list = new KSListPanel();
	MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
	CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
	
	private final KSButton categoryManagement = new KSButton("Category Management", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
            Button closeButton = new Button("Close");
            
            final KSLightBox pop = new KSLightBox();
            VerticalPanel mainPanel = new VerticalPanel();
            mainPanel.add(new CategoryManagement());
            mainPanel.add(closeButton);
            
            closeButton.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    pop.hide();
                }
            });
            
            pop.setWidget(mainPanel);
            pop.show();
		}
	});
	
	public CurriculumHomeView(Controller controller, Enum<?> viewType) {
		super(controller, "", viewType);
		container.add(SectionTitle.generateH2Title("Curriculum Management"));
		container.add(list);
		this.initWidget(container);
		this.setupLinks();
	}
	
	
	
	public void addWidget(Widget w){
		container.insert(w, 0);
	}
	
	private void setupLinks(){
		list.add(new Hyperlink("Start Blank Proposal", "/HOME/CURRICULUM_HOME/COURSE_PROPOSAL"));
		list.add(new Hyperlink("Clu Set Management", "/HOME/CURRICULUM_HOME/CLU_SETS"));
		list.add(new Hyperlink("Course Catalog", "/HOME/CURRICULUM_HOME/COURSE_CATALOG"));
//		list.add(new Hyperlink("Program", "/HOME/CURRICULUM_HOME/PROGRAM_VIEW"));
		list.add(categoryManagement);
		addIfPermitted(PermissionType.SEARCH, "Courses");
        addIfPermitted(PermissionType.SEARCH, "Proposals");
        addIfPermitted(PermissionType.SEARCH, "Majors");
        list.add(new Hyperlink("Create a Major Discipline", "/HOME/CURRICULUM_HOME/PROGRAM_CREATE"));
		//list.add(new Hyperlink("Variation", "/HOME/CURRICULUM_HOME/VARIATION_VIEW"));
        list.addStyleName("KS-CurriculumHome-LinkList");

	}
	
    private void addIfPermitted(PermissionType permType, String searchType) {
    	addIfPermitted(permType, searchType, new HashMap<String,String>());
    }
    
    private void addIfPermitted(PermissionType permType, final String searchType, Map<String,String> permissionAttributes) {
        cluProposalRpcServiceAsync.isAuthorized(permType, permissionAttributes, new KSAsyncCallback<Boolean>() {
            @Override
            public void handleFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Boolean result) {
                //NOTE: quick hack; does not matter because this all goes away with new Curriculum Management home screen
                if(result) {
                    if (searchType.equals("Courses")) {
                        addCourseSearchWindow(); 
                    } else if (searchType.equals("Majors")){
                        addMajorSearchWindow();
                    } else {
                        addProposalSearchWindow();
                    }
                }                
            }
        });
    }
	
    private void addCourseSearchWindow(){
        metadataServiceAsync.getMetadata("search", "", "", new KSAsyncCallback<Metadata>() {
            @Override
            public void handleFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Metadata metadata) {
                metadata = metadata.getProperties().get("findCourseTmp");  //TEMP until we have new home page screen where we have suggest box instead of a link
                KSPicker courseSearchWindow = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
                courseSearchWindow.addValuesChangeHandler(new ValueChangeHandler<List<String>>(){
                    public void onValueChange(ValueChangeEvent<List<String>> event) {
                        List<String> selection = event.getValue();
                        ViewContext viewContext = new ViewContext();
                        viewContext.setId(selection.get(0));
                        viewContext.setIdType(IdType.OBJECT_ID);
                        Application.navigate("/HOME/CURRICULUM_HOME/VIEW_COURSE", viewContext);
                    }                    
                }); 
                list.add(courseSearchWindow);
            }
        });         
    }

    private void addProposalSearchWindow(){
                        
        metadataServiceAsync.getMetadata("search", "", "", new KSAsyncCallback<Metadata>() {
            @Override
            public void handleFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Metadata metadata) {
                metadata = metadata.getProperties().get("findProposal");                
                KSPicker proposalSearchWindow = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
                proposalSearchWindow.addValuesChangeHandler(new ValueChangeHandler<List<String>>(){
                    public void onValueChange(ValueChangeEvent<List<String>> event) {
                        List<String> selection = event.getValue();
                        ViewContext viewContext = new ViewContext();
                        viewContext.setId(selection.get(0));
                        viewContext.setIdType(IdType.KS_KEW_OBJECT_ID);
                        Application.navigate("/HOME/CURRICULUM_HOME/COURSE_PROPOSAL", viewContext);
                    }                    
                });
                list.add(proposalSearchWindow);
            }
        });       
    }
    
    private void addMajorSearchWindow(){
        metadataServiceAsync.getMetadata("search", "", "", new KSAsyncCallback<Metadata>() {
            @Override
            public void handleFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Metadata metadata) {
                metadata = metadata.getProperties().get("findMajor");  
                KSPicker searchWindow = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
                searchWindow.addValuesChangeHandler(new ValueChangeHandler<List<String>>(){
                    public void onValueChange(ValueChangeEvent<List<String>> event) {
                        List<String> selection = event.getValue();
                        ViewContext viewContext = new ViewContext();
                        viewContext.setId(selection.get(0));
                        viewContext.setIdType(IdType.OBJECT_ID);
                        Application.navigate("/HOME/CURRICULUM_HOME/PROGRAM_VIEW", viewContext);
                    }                    
                }); 
                list.add(searchWindow);
            }
        });         
    }

}
