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

import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.PropertyBinding;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AcademicContentLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AdminstrativeLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AttachmentsLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.ProposalInformationLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.StudentEligibilityLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.ViewsLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.dto.ProposalInfo;

import com.google.gwt.user.client.ui.TextBox;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class CreditCourseLayoutManager {

    private Map<String, FieldDescriptor> fields;
    private Validator validator;

    public CreditCourseLayoutManager() {
        super();
    }

    public CreditCourseLayoutManager(Map<String, FieldDescriptor> fields, Validator validator) {
        super();
        this.fields = fields;
        this.validator = validator;
    }

    public DefaultCreateUpdateLayout<CluProposal> getCreateUpdateLayout(String type, String state) {

        DefaultCreateUpdateLayout<CluProposal> layout = new DefaultCreateUpdateLayout<CluProposal>();

        layout = addStartSection(layout);
        layout = addViewsSection(layout, type, state);
        layout = addAuthorAndCollaboratorsSection(layout, type, state);
        layout = addAcademicContentSection(layout, type, state);
        layout = addStudentEligibilitySection(layout, type, state);
        layout = addAdministrativeSection(layout, type, state);
        layout = addAttachmentsSection(layout, type, state);
                
        return layout;
    }

    private DefaultCreateUpdateLayout<CluProposal> addStartSection(DefaultCreateUpdateLayout<CluProposal> layout){
        layout.addStartSection(new SimpleConfigurableSection<CluProposal>()
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return null;
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                            }
                        })
                        .setFormField(new KSFormField("proposedCourseTitle", "Proposed Course Title")
                        .setWidget(new TextBox())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
                        )
                )
                .setSectionTitle("Begin Proposal"));
        return layout;
    }
    
    private DefaultCreateUpdateLayout<CluProposal> addViewsSection(DefaultCreateUpdateLayout<CluProposal> layout,
        String type, String state) {
            ViewsLayoutManager manager = new ViewsLayoutManager(layout, 
                    fields, validator);
            return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addAuthorAndCollaboratorsSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {
        ProposalInformationLayoutManager manager = new ProposalInformationLayoutManager(layout, 
                fields, validator);
        return manager.addSection( type, state);

    }

    private DefaultCreateUpdateLayout<CluProposal> addAcademicContentSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AcademicContentLayoutManager manager = new AcademicContentLayoutManager(layout, 
                fields, validator);
        return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addStudentEligibilitySection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        StudentEligibilityLayoutManager manager = new StudentEligibilityLayoutManager(layout, 
                fields, validator);
        return manager.addSection( type, state);

    }

    private DefaultCreateUpdateLayout<CluProposal> addAdministrativeSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AdminstrativeLayoutManager manager = new AdminstrativeLayoutManager(layout, 
                fields, validator);
        return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addAttachmentsSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AttachmentsLayoutManager manager = new AttachmentsLayoutManager(layout, 
                fields, validator);
        return manager.addSection( type, state);
    }

}
