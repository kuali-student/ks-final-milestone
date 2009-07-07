/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.typemanager;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.PropertyBinding;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout.SaveTypes;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AcademicContentLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AdminstrativeLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AttachmentsLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.KewLinksLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.ProposalInformationLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.StudentEligibilityLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.ViewsLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.proposal.dto.ProposalInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class CreditCourseLayoutManager {

    CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);    
    
    private Validator validator;

//    protected  CreditCourseLayoutManager() {
//        super();
//    }
//
//    public CreditCourseLayoutManager( Validator validator) {
//        super();
//        this.validator = validator;
//    }

    public DefaultCreateUpdateLayout<CluProposal> getCreateUpdateLayout(String type, String state) {

        final DefaultCreateUpdateLayout<CluProposal> layout = new DefaultCreateUpdateLayout<CluProposal>();
        
        addStartSection(layout);
        addViewsSection(layout, type, state);
        addProposalInformationSection(layout, type, state);
        addAcademicContentSection(layout, type, state);
        addStudentEligibilitySection(layout, type, state);
        addAdministrativeSection(layout, type, state);
        addAttachmentsSection(layout, type, state);
        addKewLinksSection(layout, type, state);

        //FIXME: Handlers should probably be added in controller rather than configuration
        layout.addSaveSectionHandler(new SaveHandler(){
            public void onSave(SaveEvent saveEvent) {
                CluProposal cluProposal = (CluProposal)layout.getObject();
                cluProposalRpcServiceAsync.saveProposal(cluProposal, new AsyncCallback<CluProposal>(){
                    
                    @Override
                    public void onFailure(Throwable caught) {
                        //TODO: How to display error and prevent continue                        
                    }

                    @Override
                    public void onSuccess(CluProposal result) {
                        layout.setObject(result);
                        layout.refresh();
                    }                   
                });
                
            }            
        });

        return layout;
    }

	private DefaultCreateUpdateLayout<CluProposal> addStartSection(final DefaultCreateUpdateLayout<CluProposal> layout){
        final SimpleConfigurableSection<CluProposal> startCluProposalSection = new SimpleConfigurableSection<CluProposal>();  
        
        layout.addStartSection(startCluProposalSection
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return object.getCluInfo().getOfficialIdentifier().getLongName();
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
                                cluIdentifier.setLongName((String)value);
                                object.getCluInfo().setOfficialIdentifier(cluIdentifier);
                            }
                        })
                        .setFormField(new KSFormField("proposedCourseTitle", "Proposed Course Title")
                        .setWidget(new TextBox())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
                        )
                )
                .setSectionTitle("Begin Proposal")
                .setParentLayout(layout)
        );
        
        //FIXME: Handlers should probably be added in controller rather than configuration
        layout.addSaveStartSectionHandler(new SaveHandler(){
            public void onSave(SaveEvent saveEvent) {
                startCluProposalSection.updateObject();
                CluProposal cluProposal = (CluProposal)startCluProposalSection.getParentLayout().getObject();
                cluProposal.setProposalInfo(new ProposalInfo());
                if (saveEvent.getSaveType() == SaveTypes.CREATE){
                    cluProposalRpcServiceAsync.createProposal(cluProposal, new AsyncCallback<CluProposal>(){
    
                        @Override
                        public void onFailure(Throwable caught) {
                            //TODO: How to display error and prevent continue                        
                        }
    
                        @Override
                        public void onSuccess(CluProposal result) {
                            ((CluProposal)startCluProposalSection.getParentLayout().getObject()).setCluInfo(result.getCluInfo());
                            ((CluProposal)startCluProposalSection.getParentLayout().getObject()).setWorkflowId(result.getWorkflowId());
                            layout.refresh();
                        }                   
                    });
                } else if (saveEvent.getSaveType() == SaveTypes.WF_CREATE){
//                    cluProposalRpcServiceAsync.createAndRouteProposal(cluProposal, new AsyncCallback<CluProposal>(){
//                        
//                        @Override
//                        public void onFailure(Throwable caught) {
//                            //TODO: How to display error and prevent continue                        
//                        }
//    
//                        @Override
//                        public void onSuccess(CluProposal result) {
//                            ((CluProposal)startCluProposalSection.getParentLayout().getObject()).setCluInfo(result.getCluInfo());
//                            layout.refresh();
//                        }                   
//                    });
                }
            }});
        return layout;
    }
    
    private DefaultCreateUpdateLayout<CluProposal> addViewsSection(DefaultCreateUpdateLayout<CluProposal> layout,
        String type, String state) {
            ViewsLayoutManager manager = new ViewsLayoutManager(layout);
            return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addProposalInformationSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {
        ProposalInformationLayoutManager manager = new ProposalInformationLayoutManager(layout);
        return manager.addSection( type, state);

    }

    private DefaultCreateUpdateLayout<CluProposal> addAcademicContentSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AcademicContentLayoutManager manager = new AcademicContentLayoutManager(layout);
        return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addStudentEligibilitySection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        StudentEligibilityLayoutManager manager = new StudentEligibilityLayoutManager(layout);
        return manager.addSection( type, state);

    }

    private DefaultCreateUpdateLayout<CluProposal> addAdministrativeSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AdminstrativeLayoutManager manager = new AdminstrativeLayoutManager(layout);
        return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addAttachmentsSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AttachmentsLayoutManager manager = new AttachmentsLayoutManager(layout);
        return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addKewLinksSection(
			DefaultCreateUpdateLayout<CluProposal> layout,  String type, String state) {
        KewLinksLayoutManager manager = new KewLinksLayoutManager(layout);
        return manager.addSection( type, state);
	}
}
