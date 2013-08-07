package org.kuali.student.lum.lu.ui.main.client.configuration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.user.client.ui.*;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.*;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.common.ui.client.widgets.layout.ContentBlockLayout;
import org.kuali.student.common.ui.client.widgets.layout.LinkContentBlock;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.ui.course.client.widgets.RecentlyViewedBlock;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionType;

import java.util.ArrayList;
import java.util.List;

public class CurriculumHomeConfigurer implements CurriculumHomeConstants {

	protected Metadata searchMetadata;
	protected final KSCheckBox useCurricReviewCheckbox = new KSCheckBox(getMessage("useCurriculumReview"));

	public CurriculumHomeConfigurer(){
        
    }
	
	public Widget configure(Metadata searchMeta) {
        this.searchMetadata = searchMeta;
        final ContentBlockLayout layout = new ContentBlockLayout(getMessage(CURRICULUM_MANAGEMENT));
        ArrayList<String> permissionList=new ArrayList<String>();
                
        layout.addContentTitleWidget(getHowToWidget());
        layout.addContentTitleWidget(getActionListLink());

        //TODO: Fix to improve performance, so permissions don't have to be loaded every time
        Application.getApplicationContext().getSecurityContext().loadPermissionsByPermissionType(PermissionType.INITIATE);
        permissionList.add(LUUIPermissions.USE_CREATE_COURSE_BY_PROPOSAL);
        permissionList.add(LUUIPermissions.USE_CREATE_COURSE_BY_ADMIN_PROPOSAL);
        permissionList.add(LUUIPermissions.USE_CREATE_PROGRAM_BY_PROPOSAL);       
        permissionList.add(LUUIPermissions.USE_BROWSE_CATALOG_SCREEN);
        permissionList.add(LUUIPermissions.USE_FIND_COURSE_SCREEN);
        permissionList.add(LUUIPermissions.USE_FIND_COURSE_PROPOSAL_SCREEN);
        permissionList.add(LUUIPermissions.USE_BROWSE_PROGRAM_SCREEN);
        permissionList.add(LUUIPermissions.USE_FIND_PROGRAM_SCREEN);
        permissionList.add(LUUIPermissions.USE_FIND_PROGRAM_PROPOSAL_SCREEN);
        permissionList.add(LUUIPermissions.USE_VIEW_CORE_PROGRAMS_SCREEN);
        permissionList.add(LUUIPermissions.USE_VIEW_CREDENTIAL_PROGRAMS_SCREEN);
        permissionList.add(LUUIPermissions.USE_VIEW_COURSE_SET_MANAGEMENT_SCREENS);
        permissionList.add(LUUIPermissions.USE_LO_CATEGORY_SCREEN);
        permissionList.add(LUUIPermissions.USE_DEPENDENCY_ANALYSIS_SCREEN);    
        
        Application.getApplicationContext().getSecurityContext().loadScreenPermissions(permissionList, new Callback<Boolean>(){

            @Override
            public void exec(Boolean result) {
                //Create Block
                final LinkContentBlock create = new LinkContentBlock(getMessage(CREATE), getMessage(CREATE_DESC));
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_CREATE_COURSE_BY_PROPOSAL)||
                        Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_CREATE_COURSE_BY_ADMIN_PROPOSAL)){
                        
                    create.addNavLinkWidget(getMessage(CREATE_COURSE), getCreateCourseClickHandler());
                }
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_CREATE_PROGRAM_BY_PROPOSAL)){
                 
                    create.addNavLinkWidget(getMessage(CREATE_PROGRAM), AppLocations.Locations.EDIT_PROGRAM.getLocation());
                }
                
                //View + Modify
                final LinkContentBlock viewModify = new LinkContentBlock(getMessage(VIEW_MODIFY), getMessage(VIEW_MODIFY_DESC));
                SectionTitle courses = SectionTitle.generateH4Title(getMessage("courses"));
                courses.addStyleName("bold");
                viewModify.add(courses);
                
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_BROWSE_CATALOG_SCREEN)){
                    
                    viewModify.addNavLinkWidget(getMessage(BROWSE_CATALOG), AppLocations.Locations.BROWSE_CATALOG.getLocation());
                }
            
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_FIND_COURSE_SCREEN)){
                                 
                    viewModify.add(getFindCoursesWidget());
                }
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_FIND_COURSE_PROPOSAL_SCREEN)){
                    
                    viewModify.add(getFindCourseProposalsWidget()); 
                }
                
                SectionTitle programs = SectionTitle.generateH4Title(getMessage("programs"));
                programs.addStyleName("bold");
                viewModify.add(programs);
                                    
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_BROWSE_PROGRAM_SCREEN)){
                    
                     viewModify.addNavLinkWidget(getMessage(BROWSE_PROGRAM), AppLocations.Locations.BROWSE_PROGRAM.getLocation());
                }
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_FIND_PROGRAM_SCREEN)){
                    
                    viewModify.add(getFindMajorsWidget());
                }
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_FIND_PROGRAM_PROPOSAL_SCREEN)){
                    
                    viewModify.add(getFindProgramProposalsWidget());        
                }
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_VIEW_CORE_PROGRAMS_SCREEN)){
                    
                    viewModify.add(getViewCoreProgramWidget());
                }
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_VIEW_CREDENTIAL_PROGRAMS_SCREEN)){
                    
                    viewModify.add(getViewCredentialProgramWidget());
                }
                
                //RecentlyViewed
                RecentlyViewedBlock recent = new RecentlyViewedBlock(getMessage(RECENTLY_VIEWED), getMessage(RV_DESC));
                recent.addStyleName("recentlyViewed-block");

                //Tools
                final LinkContentBlock tools = new LinkContentBlock(getMessage(TOOLS), getMessage(TOOLS_DESC));
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_VIEW_COURSE_SET_MANAGEMENT_SCREENS)){
                    
                     tools.addNavLinkWidget(getMessage(COURSE_SETS), AppLocations.Locations.MANAGE_CLU_SETS.getLocation());
                }
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_LO_CATEGORY_SCREEN)){
                    
                    tools.addNavLinkWidget(getMessage(LO_CATEGORIES), AppLocations.Locations.MANAGE_LO_CATEGORIES.getLocation());
                }
                
                if(Application.getApplicationContext().getSecurityContext().checkCachedScreenPermission(LUUIPermissions.USE_DEPENDENCY_ANALYSIS_SCREEN)){
                    
                    tools.addNavLinkWidget(getMessage(DEP_ANALYSIS), AppLocations.Locations.DEPENDENCY_ANALYSIS.getLocation());
                }
                
                //Add all blocks
                layout.addContentBlock(create);
                layout.addContentBlock(viewModify);
                recent.addBlock(tools);
                layout.addContentBlock(recent);
            }}
        );
        return layout;
    }

	private Widget getViewCredentialProgramWidget() {
        final Widget searchWidget;
        if (searchMetadata != null) {
            Metadata metadata = searchMetadata.getProperties().get("findCredentialProgram");
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
                    viewContext.setIdType(IdType.OBJECT_ID);
                    ProgramRegistry.setCreateNew(true);
                    Application.navigate(AppLocations.Locations.VIEW_BACC_PROGRAM.getLocation(), viewContext);
                    ((KSPicker) searchWidget).getSearchWindow().hide();
                }
            });

        } else {
            searchWidget = new Label(getMessage(FIND_CREDENTIALS));
            searchWidget.setStyleName("contentBlock-navLink-disabled");
        }
        searchWidget.setStyleName("contentBlock-navLink");
        return searchWidget;
	}

    private Widget getViewCoreProgramWidget() {
        final Widget searchWidget;
        if (searchMetadata != null) {
            Metadata metadata = searchMetadata.getProperties().get("findCoreProgram");
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
                    viewContext.setIdType(IdType.OBJECT_ID);
                    ProgramRegistry.setCreateNew(true);
                    Application.navigate(AppLocations.Locations.VIEW_CORE_PROGRAM.getLocation(), viewContext);
                    ((KSPicker) searchWidget).getSearchWindow().hide();
                }
            });

        } else {
            searchWidget = new Label(getMessage(FIND_CORES));
            searchWidget.setStyleName("contentBlock-navLink-disabled");
        }
        searchWidget.setStyleName("contentBlock-navLink");
        return searchWidget;
    }

    
    
    /**
     * 
     * This method will call getFindProposalsWidget() and return
     * proposals for courses.
     * 
     * @return
     */
    protected Widget getFindCourseProposalsWidget(){
        return getFindProposalsWidget(true);
    }
    /**
     * 
     * This method will call getFindProposalsWidget() and return
     * proposals for programs.
     * 
     * @return
     */
    protected Widget getFindProgramProposalsWidget(){
        return getFindProposalsWidget(false);
    }
    
    /**
     * 
     * This method will generate the find proposals widget.
     * <p>
     * It is used by course and program.  Rather than call it directly,
     * call either getFindCourseProposalsWidget() or getFindProgramProposalsWidget()
     * to make the code a bit clearer.
     * 
     * @param isCourseSearch true for course, false for program
     * @return the widget
     */
    protected Widget getFindProposalsWidget(boolean isCourseSearch) {
        final Widget searchWidget;
        
        // Load a search to get back either programs or courses
        String searchMetadataPropName = "";
        if (isCourseSearch){
            searchMetadataPropName = "findCourseProposal";
        }
        else {
            searchMetadataPropName = "findProgramProposal"; 
        }
        
        if (searchMetadata != null) {
            
            // Find search in the search meta data
            // 
            Metadata metadata = searchMetadata.getProperties().get(searchMetadataPropName);
          
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
	                    String proposalType = value.getResultRow().getValue("proposal.resultColumn.proposalOptionalType");
	                    viewContext.setAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME, proposalType);
	                    viewContext.setIdType(IdType.KS_KEW_OBJECT_ID);
	                    if("kuali.proposal.type.course.retire".equals(proposalType)) {
                            Application.navigate(AppLocations.Locations.COURSE_RETIRE_BY_PROPOSAL.getLocation(), viewContext);
	                    } else if("kuali.proposal.type.course.create.admin".equals(proposalType)||"kuali.proposal.type.course.modify.admin".equals(proposalType)){
	                    	Application.navigate(AppLocations.Locations.COURSE_ADMIN.getLocation(), viewContext);
	                    } else if (proposalType.startsWith("kuali.proposal.type.course")){
	                    	Application.navigate(AppLocations.Locations.COURSE_PROPOSAL.getLocation(), viewContext);
	                    } else {
	                    	ProgramRegistry.setCreateNew(true);
	                    	Application.navigate(AppLocations.Locations.PROGRAM_PROPOSAL.getLocation(), viewContext);
	                    }
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
	            final KSLightBox dialog = new KSLightBox(getMessage("createCourse"),KSLightBox.Size.MEDIUM);
	            final VerticalPanel layout = new VerticalPanel();
	            layout.addStyleName("ks-form-module-fields");
	            
	            final KSButton startProposalButton = new KSButton(getMessage("startProposal"));
	            
	            dialog.addButton(startProposalButton);
	            KSButton cancelLink = new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler(){
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
	            
	            radioOptionBlank.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
					public void onValueChange(ValueChangeEvent<Boolean> event) {

							copyCourseSearchPanel.setVisible(false);
							copyProposalSearchPanel.setVisible(false);
							startProposalButton.setEnabled(true);
							useCurricReviewCheckbox.setEnabled(true);

					}
	            });
	            radioOptionBlank.setValue(true);
	            
	            radioOptionCopyCourse.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						if(event.getValue()){
							copyCourseSearchPanel.setVisible(true);
							copyProposalSearchPanel.setVisible(false);
							useCurricReviewCheckbox.setEnabled(false);
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
							useCurricReviewCheckbox.setEnabled(false);
							copyCourseSearchPanel.clear();
							copyProposalSearchPanel.clear();
							startProposalButton.setEnabled(false);
						}
					}
	            });
	            	            

	    		Application.getApplicationContext().getSecurityContext().checkScreenPermission("useCurriculumReview", new Callback<Boolean>() {

	    					@Override
	    					public void exec(Boolean result) {

	    						final boolean isAuthorized = result;
	    			            
	    						//Only authorized users (eg. admin) are shown the "Use Curriculum Review" check box
	    						if (isAuthorized){
	    							//An authorized user by default is allowed to bypass the curriculum review process.
	    							//They may choose to use it if desired and hence given the option to change it.
	    			            	useCurricReviewCheckbox.setValue(false);
	    			            	useCurricReviewCheckbox.setVisible(true);
	    			            } else {
	    			            	//Unauthorized users must use curriculum review process and does not get option to change  
	    			            	useCurricReviewCheckbox.setValue(true);
	    			            	useCurricReviewCheckbox.setVisible(false);	            	
	    			            }

	    						//Setup dialog layout
	    			            layout.add(radioOptionBlank);
	    			            layout.add(radioOptionCopyCourse);
	    			            layout.add(copyCourseSearchPanel);
	    			            layout.add(radioOptionCopyProposal);
	    			            layout.add(copyProposalSearchPanel);
	    			            layout.add(new KSLabel(""));
	    			            layout.add(useCurricReviewCheckbox);
	    			            
	    			            //Setup start proposal click handler
	    			            startProposalButton.addClickHandler(new ClickHandler(){
	    							public void onClick(ClickEvent event) {
	    								
	    								//Clicking this button will navigate user to appropriate screens based on selections
	    								
    				                    ViewContext viewContext = new ViewContext();
    				                    
    				                    //Start New Proposal
	    								if(radioOptionBlank.getValue())	{
                                            Log.info("Do nothing, empty view context indicates new proposal");
	    									//Do nothing, empty view context indicates new proposal
	    								
	    								//Copy Course
	    								} else if (radioOptionCopyCourse.getValue()) {	    									    						
	    									//Setup view context to open copy from existing course
	    									viewContext.setId(copyCourseSearchPanel.getValue());
	    				                    viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
	    				                    
	    								//Copy Proposal
	    								} else if(radioOptionCopyProposal.getValue()){	    									
	    									//Setup view context to copy from existing proposal
	    									viewContext.setId(copyProposalSearchPanel.getValue());
	    				                    viewContext.setIdType(IdType.COPY_OF_KS_KEW_OBJECT_ID);	    				                   	    				                    
	    								}
	    								
    									//Based on curriculum review, navigate user to either admin or standard proposal screens
    									if (useCurricReviewCheckbox.getValue()){	    										
    										Application.navigate(AppLocations.Locations.COURSE_PROPOSAL.getLocation(),viewContext);		    										
    									} else {
    										Application.navigate(AppLocations.Locations.COURSE_ADMIN.getLocation(),viewContext);
    									}	    									

	    								dialog.hide();
	    							}
	    						});
	    			            	    			            
	    			            dialog.setWidget(layout);
	    			            dialog.show();				    						
	    					}
	    				});	            
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
                pop.setWidget((Widget)GWT.create(CurriculumHomeHelpTable.class));
                //pop.setWidget(new CurriculumHomeHelpTable());
                pop.setSize(800, 680);
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

}
