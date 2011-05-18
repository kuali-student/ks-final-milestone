package org.kuali.student.lum.lu.ui.main.client.configuration;

import java.util.List;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.common.ui.client.widgets.layout.ContentBlockLayout;
import org.kuali.student.common.ui.client.widgets.layout.LinkContentBlock;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.ui.course.client.widgets.RecentlyViewedBlock;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CurriculumHomeConfigurer implements CurriculumHomeConstants {

    protected Metadata searchMetadata;

    public Widget configure(Metadata searchMeta) {
        this.searchMetadata = searchMeta;
        ContentBlockLayout layout = new ContentBlockLayout(getMessage(CURRICULUM_MANAGEMENT));
        layout.addContentTitleWidget(getHowToWidget());
        layout.addContentTitleWidget(getActionListLink());

        //Create
        LinkContentBlock create = new LinkContentBlock(
                getMessage(CREATE),
                getMessage(CREATE_DESC));
        create.addNavLinkWidget(getMessage(CREATE_COURSE), getCreateCourseClickHandler());
        create.addNavLinkWidget(getMessage(CREATE_PROGRAM), AppLocations.Locations.EDIT_PROGRAM.getLocation());        


        //View + Modify
        LinkContentBlock viewModify = new LinkContentBlock(
                getMessage(VIEW_MODIFY),
                getMessage(VIEW_MODIFY_DESC));
        SectionTitle courses = SectionTitle.generateH4Title(getMessage("courses"));
        courses.addStyleName("bold");
        viewModify.add(courses);
        viewModify.addNavLinkWidget(getMessage(BROWSE_CATALOG), AppLocations.Locations.BROWSE_CATALOG.getLocation());
        viewModify.add(getFindCoursesWidget());
        viewModify.add(getFindProposalsWidget());
        SectionTitle programs = SectionTitle.generateH4Title(getMessage("programs"));
        programs.addStyleName("bold");
        viewModify.add(programs);
        viewModify.addNavLinkWidget(getMessage(BROWSE_PROGRAM), AppLocations.Locations.BROWSE_PROGRAM.getLocation());
        viewModify.add(getFindMajorsWidget());
        viewModify.add(getFindCoreProgramWidget());
        viewModify.add(getFindCredentialProgramWidget());

        //RecentlyViewed
        RecentlyViewedBlock recent = new RecentlyViewedBlock(
                getMessage(RECENTLY_VIEWED),
                getMessage(RV_DESC));
        recent.addStyleName("recentlyViewed-block");

        //Tools
        LinkContentBlock tools = new LinkContentBlock(
                getMessage(TOOLS),
                getMessage(TOOLS_DESC));
        tools.addNavLinkWidget(getMessage(COURSE_SETS), AppLocations.Locations.MANAGE_CLU_SETS.getLocation());
        tools.addNavLinkWidget(getMessage(LO_CATEGORIES), AppLocations.Locations.MANAGE_LO_CATEGORIES.getLocation());
        tools.addNavLinkWidget(getMessage(DEP_ANALYSIS), AppLocations.Locations.DEPENDENCY_ANALYSIS.getLocation());
        
        //Coming soon
        Label learningObjectives = new Label(getMessage(LOS));
        learningObjectives.setTitle("Coming Soon");
        learningObjectives.setStyleName("contentBlock-navLink-disabled");
        tools.add(learningObjectives);

        //Add all blocks
        layout.addContentBlock(create);
        layout.addContentBlock(viewModify);
        recent.addBlock(tools);
        layout.addContentBlock(recent);

        return layout;
    }

	private Widget getFindCredentialProgramWidget() {
        Anchor anchor = createNavigationWidget(getMessage(FIND_CREDENTIALS));
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ProgramRegistry.setCreateNew(true);
                ViewContext baccViewContext = new ViewContext();
                baccViewContext.setId(ProgramClientConstants.CREDENTIAL_BACCALAUREATE_PROGRAM);
                Application.navigate(AppLocations.Locations.VIEW_BACC_PROGRAM.getLocation(), baccViewContext);
            }
        });
        return anchor;
    }

    private Widget getFindCoreProgramWidget() {
        Anchor anchor = createNavigationWidget(getMessage(FIND_CORES));
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ProgramRegistry.setCreateNew(true);
                Application.navigate(AppLocations.Locations.VIEW_CORE_PROGRAM.getLocation());
            }
        });
        return anchor;
    }


    protected Widget getFindProposalsWidget() {
        final Widget searchWidget;
        if (searchMetadata != null) {
            Metadata metadata = searchMetadata.getProperties().get("findProposal");
            searchWidget = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
            SearchPanel panel = ((KSPicker) searchWidget).getSearchPanel();
            if (panel != null) {
                panel.setMutipleSelect(false);
            }
            ((KSPicker) searchWidget).setAdvancedSearchCallback(new Callback<List<SelectedResults>>() {

                @Override
                public void exec(List<SelectedResults> result) {
                	if (result != null && result.size() > 0) {
	                    SelectedResults value = result.get(0);
	                    ViewContext viewContext = new ViewContext();
	                    viewContext.setId(value.getResultRow().getId());
	                    viewContext.setAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME, value.getResultRow().getValue("proposal.resultColumn.proposalOptionalType"));
	                    viewContext.setIdType(IdType.KS_KEW_OBJECT_ID);
	                    Application.navigate(AppLocations.Locations.COURSE_PROPOSAL.getLocation(), viewContext);
	                    ((KSPicker) searchWidget).getSearchWindow().hide();
                	}
                }
            });
        } else {
            searchWidget = new Label(getMessage(FIND_PROPOSALS));
            searchWidget.setStyleName("contentBlock-navLink-disabled");
        }
        searchWidget.setStyleName("contentBlock-navLink");
        return searchWidget;
    }

    protected Widget getFindCoursesWidget() {
        Widget searchWidget;
        if (searchMetadata != null) {
            Metadata metadata = searchMetadata.getProperties().get("findCourse");
            searchWidget = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
            SearchPanel panel = ((KSPicker) searchWidget).getSearchPanel();
            if (panel != null) {
                panel.setMutipleSelect(false);
            }
            ((KSPicker) searchWidget).addValuesChangeHandler(new ValueChangeHandler<List<String>>() {
                public void onValueChange(ValueChangeEvent<List<String>> event) {
                    List<String> selection = event.getValue();
                    ViewContext viewContext = new ViewContext();
                    viewContext.setId(selection.get(0));
                    viewContext.setIdType(IdType.OBJECT_ID);
                    Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
                }
            });
            searchWidget.setStyleName("contentBlock-navLink");
        } else {
            searchWidget = new Label(getMessage(FIND_COURSES));
            searchWidget.setStyleName("contentBlock-navLink-disabled");
        }
        return searchWidget;
    }

    protected ClickHandler getCreateCourseClickHandler() {
    	return new ClickHandler(){
    		
			@Override
			public void onClick(ClickEvent event) {
	            
				//Create a dialog for course selection
	            final KSLightBox dialog = new KSLightBox(getMessage("createCourse"));
	            VerticalPanel layout = new VerticalPanel();
	            layout.addStyleName("ks-form-module-fields");
	            
	            final KSButton startProposalButton = new KSButton(getMessage("startProposal"));
	            
	            dialog.addButton(startProposalButton);
	            Anchor cancelLink = new Anchor("Cancel");
	            cancelLink.addClickHandler(new ClickHandler(){
					public void onClick(ClickEvent event) {
						dialog.hide();
					}
	            });
	            dialog.addButton(cancelLink);
	            
	            HorizontalPanel titlePanel = new HorizontalPanel();
	            KSLabel titleLabel = new KSLabel(getMessage("createCourseSubTitle"));
	            titleLabel.addStyleName("bold");
	            AbbrButton helpButton = new AbbrButton(AbbrButtonType.HELP);
	            helpButton.setHoverHTML(getMessage("createCourseSubTitle-help"));
	            helpButton.getHoverPopup().getElement().getStyle().setZIndex(999999);
	            titlePanel.add(titleLabel);
	            titlePanel.add(helpButton);
	            
	            layout.add(titlePanel);
	            
	            KSLabel instructionText = new KSLabel(getMessage("createCourseSubTitle-instruct"));
	 
	            layout.add(instructionText);
	            
	            final CopyCourseSearchPanel copyCourseSearchPanel = new CopyCourseSearchPanel(searchMetadata, new Callback<Boolean>(){
					public void exec(Boolean result) {
						if(result){
							startProposalButton.setEnabled(true);
						}else{
							startProposalButton.setEnabled(false);
						}
					}
	            }, getMessage("courseToCopy"), getMessage("courseInvalidValue"), new String[]{getMessage("selectByCourseCode"), getMessage("selectByCourseTitle")}, new String[]{"approvedCourses", "approvedCoursesByTitle"});
	            

	            final CopyCourseSearchPanel copyProposalSearchPanel = new CopyCourseSearchPanel(searchMetadata, new Callback<Boolean>(){
					public void exec(Boolean result) {
						if(result){
							startProposalButton.setEnabled(true);
						}else{
							startProposalButton.setEnabled(false);
						}
					}
	            }, getMessage("proposalToCopy"), getMessage("proposalInvalidValue"), new String[]{getMessage("selectByProposalTitle"), getMessage("selectByProposedCourse")}, new String[]{"proposedCoursesByTitle", "proposedCoursesByCode"});
	           
	            final KSRadioButton radioOptionBlank = new KSRadioButton("createNewCreditCourseButtonGroup", getMessage("startBlankProposal"));
	            final KSRadioButton radioOptionCopyCourse = new KSRadioButton("createNewCreditCourseButtonGroup", getMessage("copyApprovedCourse"));
	            final KSRadioButton radioOptionCopyProposal = new KSRadioButton("createNewCreditCourseButtonGroup", getMessage("copyProposedCourse"));
	            final KSCheckBox adminOptionCheckbox = new KSCheckBox(getMessage("useCurriculumReview"));
	            
	            radioOptionBlank.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						if(event.getValue()){
							if ("admin".equals(Application.getApplicationContext().getUserId())){
				            	adminOptionCheckbox.setVisible(true);
				            	adminOptionCheckbox.setEnabled(true);
				            	adminOptionCheckbox.setValue(false);
				            }
							copyCourseSearchPanel.setVisible(false);
							copyProposalSearchPanel.setVisible(false);
							startProposalButton.setEnabled(true);
						}
					}
	            });
	            radioOptionBlank.setValue(true);
	            
	            radioOptionCopyCourse.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						if(event.getValue()){
							copyCourseSearchPanel.setVisible(true);
							copyProposalSearchPanel.setVisible(false);
							adminOptionCheckbox.setEnabled(false);
							adminOptionCheckbox.setValue(true);
							copyCourseSearchPanel.clear();
							copyProposalSearchPanel.clear();
							startProposalButton.setEnabled(false);
						}
					}
	            });
	            
	            radioOptionCopyProposal.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						if(event.getValue()){
							copyCourseSearchPanel.setVisible(false);
							copyProposalSearchPanel.setVisible(true);
							adminOptionCheckbox.setEnabled(false);
							adminOptionCheckbox.setValue(true);
							copyCourseSearchPanel.clear();
							copyProposalSearchPanel.clear();
							startProposalButton.setEnabled(false);
						}
					}
	            });
	            
	            //FIXME: This should check permissions for admin functionality rather than just admin user
	            if ("admin".equals(Application.getApplicationContext().getUserId())){
	            	adminOptionCheckbox.setValue(false);
	            	adminOptionCheckbox.setVisible(true);
	            } else {
	            	adminOptionCheckbox.setValue(true);
	            	adminOptionCheckbox.setVisible(false);	            	
	            }
	            
	            layout.add(radioOptionBlank);
	            layout.add(radioOptionCopyCourse);
	            layout.add(copyCourseSearchPanel);
	            layout.add(radioOptionCopyProposal);
	            layout.add(copyProposalSearchPanel);
	            layout.add(new KSLabel(""));
	            layout.add(adminOptionCheckbox);
	            
	            startProposalButton.addClickHandler(new ClickHandler(){
					public void onClick(ClickEvent event) {
						if(radioOptionBlank.getValue() && adminOptionCheckbox.getValue()){
							Application.navigate(AppLocations.Locations.COURSE_PROPOSAL.getLocation());
						} else if (radioOptionBlank.getValue()){
							Application.navigate(AppLocations.Locations.COURSE_ADMIN.getLocation());
						}else if(radioOptionCopyCourse.getValue()){
		                    ViewContext viewContext = new ViewContext();
		                    viewContext.setId(copyCourseSearchPanel.getValue());
		                    viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
		                    if (adminOptionCheckbox.getValue()){
		                    	Application.navigate(AppLocations.Locations.COURSE_PROPOSAL.getLocation(), viewContext);
		                    } else {
		                    	Application.navigate(AppLocations.Locations.COURSE_ADMIN.getLocation(), viewContext);
		                    }
						}else if(radioOptionCopyProposal.getValue()){
		                    ViewContext viewContext = new ViewContext();
		                    viewContext.setId(copyProposalSearchPanel.getValue());
		                    viewContext.setIdType(IdType.COPY_OF_KS_KEW_OBJECT_ID);
		                    viewContext.getAttributes().remove(StudentIdentityConstants.DOCUMENT_TYPE_NAME);
		                    if (adminOptionCheckbox.getValue()){
		                    	Application.navigate(AppLocations.Locations.COURSE_PROPOSAL.getLocation(), viewContext);
		                    } else {
		                    	Application.navigate(AppLocations.Locations.COURSE_ADMIN.getLocation(), viewContext);
		                    }
						}
						dialog.hide();
					}
				});
	            
	            
	            dialog.setWidget(layout);
	            dialog.show();			
    		}
   		};
    }
    
    protected Widget getFindMajorsWidget() {
        final Widget searchWidget;
        if (searchMetadata != null) {
            Metadata metadata = searchMetadata.getProperties().get("findMajor");
            searchWidget = new KSPicker(metadata.getInitialLookup(), metadata.getAdditionalLookups());
            SearchPanel panel = ((KSPicker) searchWidget).getSearchPanel();
            if (panel != null) {
                panel.setMutipleSelect(false);
            }
            ((KSPicker) searchWidget).setAdvancedSearchCallback(new Callback<List<SelectedResults>>() {

                @Override
                public void exec(List<SelectedResults> result) {
                    SelectedResults value = result.get(0);
                    ViewContext viewContext = new ViewContext();
                    viewContext.setId(value.getResultRow().getId());
                    String cluType = value.getResultRow().getValue("lu.resultColumn.luOptionalType");
                    if (cluType != null) {
                        viewContext.setAttribute(ProgramConstants.TYPE, cluType);
                    }
                    String variationId = value.getResultRow().getValue("lu.resultColumn.variationId");
                    if (variationId != null && !variationId.trim().isEmpty()) {
                        viewContext.setAttribute(ProgramConstants.VARIATION_ID, variationId);
                    }
                    viewContext.setIdType(IdType.OBJECT_ID);
                    ProgramRegistry.setCreateNew(true);
                    Application.navigate(AppLocations.Locations.VIEW_PROGRAM.getLocation(), viewContext);
                    ((KSPicker) searchWidget).getSearchWindow().hide();
                }
            });

        } else {
            searchWidget = new Label(getMessage(FIND_MAJORS));
            searchWidget.setStyleName("contentBlock-navLink-disabled");
        }
        searchWidget.setStyleName("contentBlock-navLink");
        return searchWidget;
    }

    protected Widget getHowToWidget() {
        Anchor widget = new Anchor(getMessage(HOW_TO));
        widget.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final KSLightBox pop = new KSLightBox();
                pop.setWidget(new CurriculumHomeHelpTable());
                pop.setSize(800, 600);
                pop.show();
            }
        });
        widget.setStyleName("contentBlock-navLink");
        return widget;
    }

    protected Widget getActionListLink() {
        Hyperlink widget = new Hyperlink(getMessage(ACTIONLIST), AppLocations.Locations.HOME.getLocation());
        widget.setStyleName("contentBlock-navLink");
        return widget;
    }

    private String getMessage(String key) {
        return Application.getApplicationContext().getMessage(key);
    }

    private Anchor createNavigationWidget(String title) {
        Anchor anchor = new Anchor(title);
        anchor.addStyleName("contentBlock-navLink");
        return anchor;
    }

}
