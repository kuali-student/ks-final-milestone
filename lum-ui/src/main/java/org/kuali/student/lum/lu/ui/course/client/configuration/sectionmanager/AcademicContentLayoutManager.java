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

package org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager;

import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.PropertyBinding;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.validator.DictionaryConstraint;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.counting.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class AcademicContentLayoutManager {

    private DefaultCreateUpdateLayout<CluInfo> layout;
    private Map<String, FieldDescriptor> fields;
    private Validator validator;

    public AcademicContentLayoutManager() {
        super();
    }

    public AcademicContentLayoutManager(DefaultCreateUpdateLayout<CluInfo> layout,
            Map<String, FieldDescriptor> fields, Validator validator) {
        super();
        this.layout = layout;
        this.fields = fields;
        this.validator = validator;
    }

    public DefaultCreateUpdateLayout<CluInfo> addSection(String type, String state) {

        addCourseInformation();
        addLearningObjectives();
        addSyllabus();
        addLearningResults();

        return layout;
    }
    private void addCourseInformation() {

        // TODO Need to be able to group fields and potentially control layout of fields.
        //      e.g. sometimes horizontal fields make more sense. ALignment of labels
        
        layout.addSection(new String[] {LUConstants.SECTION_ACADEMIC_CONTENT, "Course Information"}, 
                new SimpleConfigurableSection<CluInfo>()
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                return object.getOfficialIdentifier().getDivision();
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                object.getOfficialIdentifier().setDivision(value.toString());
                            }
                        })
                        .setFormField(new KSFormField("courseSubject", "Course Subject")
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("courseSubject")))
                        )
                )
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                //TODO Tech spec says suffixCode - not sure if this is right field
                                return object.getOfficialIdentifier().getCode(); 
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                //TODO Tech spec says suffixCode - not sure if this is right field
                                object.getOfficialIdentifier().setCode(value.toString());
                            }
                        })
                        .setFormField(new KSFormField("courseNumber", "Course Number")
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("courseNumber")))
                        )
                )
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                return object.getOfficialIdentifier().getLongName(); 
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                object.getOfficialIdentifier().setLongName(value.toString());
                            }
                        })
                        .setFormField(new KSFormField("proposedCourseTitle", "Proposed Course Title")
//            TODO    Tech spec says this may need to be Rich Text
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("proposedCourseTitle")))
                        )
                )
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                return object.getOfficialIdentifier().getShortName(); 
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                object.getOfficialIdentifier().setShortName(value.toString());                                
                            }
                        })
                        .setFormField(new KSFormField("transcriptTitle", "Transcript Title")
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("transcriptTitle")))
                        )
                )      
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                return object.getDesc(); 
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                RichTextInfo info = new RichTextInfo();
                                info.setPlain(value.toString());
                                object.setDesc(info);
                            }
                        })
                        .setFormField(new KSFormField("description", "Description")
                        .setWidget(new KSRichEditor())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("description")))
                        )
                )                
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                return object.getMarketingDesc(); 
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                RichTextInfo info = new RichTextInfo();
                                info.setPlain(value.toString());
                                object.setMarketingDesc(info);
                            }
                        })
                        .setFormField(new KSFormField("rationale", "Rationale")
                        .setWidget(new KSRichEditor())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("rationale")))
                        )
                ) 
                .setSectionTitle("Course Information")
                .setInstructions("\n\nYour department will determine the course number based on " +
                        " course format, restrictions, available numbers and other factors. " +
                        "You can submit a proposal without a course subject and number.")
                .setParentLayout(layout)      

        );
    }

    private void addLearningObjectives() {
        layout.addSection(new String[] {LUConstants.SECTION_ACADEMIC_CONTENT, "Learning Objectives"}, 
                new SimpleConfigurableSection<CluInfo>()
        );
    }

    private void addSyllabus() {
        layout.addSection(new String[] {LUConstants.SECTION_ACADEMIC_CONTENT, "Syllabus"}, 
                new SimpleConfigurableSection<CluInfo>()
        );
    }

    private void addLearningResults() {
        layout.addSection(new String[] {LUConstants.SECTION_ACADEMIC_CONTENT, "Learning Results"}, 
                new SimpleConfigurableSection<CluInfo>()
        );
    }







}
