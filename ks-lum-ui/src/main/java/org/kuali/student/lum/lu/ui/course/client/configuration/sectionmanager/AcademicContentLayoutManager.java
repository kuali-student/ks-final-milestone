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
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.counting.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.counting.KSTextArea;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;

import com.google.gwt.user.client.ui.FileUpload;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class AcademicContentLayoutManager {

    private DefaultCreateUpdateLayout<CluProposal> layout;

    public AcademicContentLayoutManager() {
        super();
    }

    public AcademicContentLayoutManager(DefaultCreateUpdateLayout<CluProposal> layout) {
        super();
        this.layout = layout;
    }

    public DefaultCreateUpdateLayout<CluProposal> addSection(String type, String state) {

        addCourseInformation();
        addLearningObjectives();
        addSyllabus();
        addLearningResults();

        return layout;
    }
    private void addCourseInformation() {

        layout.addSection(new String[] {LUConstants.SECTION_ACADEMIC_CONTENT, 
                LUConstants.SECTION_COURSE_INFORMATION}, 
                new SimpleConfigurableSection<CluProposal>()
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                CluIdentifierInfo cluIdentifier;
                                cluIdentifier = object.getCluInfo().getOfficialIdentifier();
                                if (cluIdentifier != null){
                                    return cluIdentifier.getDivision();
                                }
                                return "";
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                object.getCluInfo().getOfficialIdentifier().setDivision(value.toString());
                            }
                        })
                        .setFormField(new KSFormField("courseSubject", "Course Subject")
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("courseSubject")))
                        )
                )
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                //TODO Tech spec says suffixCode - not sure if this is right field
                                
                                CluIdentifierInfo cluIdentifier;
                                cluIdentifier = object.getCluInfo().getOfficialIdentifier();
                                if (cluIdentifier != null){
                                    return cluIdentifier.getCode();
                                }
                                return ""; 
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                //TODO Tech spec says suffixCode - not sure if this is right field
                                object.getCluInfo().getOfficialIdentifier().setCode(value.toString());
                            }
                        })
                        .setFormField(new KSFormField("courseNumber", "Course Number")
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("courseNumber")))
                        )
                )
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                CluIdentifierInfo cluIdentifier;
                                cluIdentifier = object.getCluInfo().getOfficialIdentifier();
                                if (cluIdentifier != null){
                                    return cluIdentifier.getLongName();
                                }
                                return "";  
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                object.getCluInfo().getOfficialIdentifier().setLongName(value.toString());
                            }
                        })
                        .setFormField(new KSFormField("proposedCourseTitle", "Proposed Course Title")
//                      TODO    Tech spec says this may need to be Rich Text
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("proposedCourseTitle")))
                        )
                )
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return new Boolean(false); 
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                
                            }
                        })
                        .setFormField(new KSFormField("hasAlternateCourseNumbers", "Alternate Course Numbers?")
                        .setWidget(new KSCheckBox())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("proposedCourseTitle")))
                        )
                )
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                CluIdentifierInfo cluIdentifier;
                                cluIdentifier = object.getCluInfo().getOfficialIdentifier();
                                if (cluIdentifier != null){
                                    return cluIdentifier.getShortName();
                                }
                                return "";  
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                object.getCluInfo().getOfficialIdentifier().setShortName(value.toString());                                
                            }
                        })
                        .setFormField(new KSFormField("transcriptTitle", "Transcript Title")
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("transcriptTitle")))
                        )
                )      
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                RichTextInfo richText = object.getCluInfo().getDesc();
                                if (richText != null){
                                    return object.getCluInfo().getDesc().getFormatted();
                                } else {
                                    return "";
                                }
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                RichTextInfo info = new RichTextInfo();
                                info.setPlain(value.toString());
                                object.getCluInfo().setDesc(info);
                            }
                        })
                        .setFormField(new KSFormField("description", "Description")
                        .setWidget(new KSRichEditor())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("description")))
                        )
                )                
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                RichTextInfo richText = object.getCluInfo().getDesc();
                                if (richText != null){
                                    return object.getCluInfo().getDesc().getFormatted();
                                } else {
                                    return "";
                                } 
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                RichTextInfo info = new RichTextInfo();
                                info.setPlain(value.toString());
                                object.getCluInfo().setMarketingDesc(info);
                            }
                        })
                        .setFormField(new KSFormField("rationale", "Rationale")
                        .setWidget(new KSRichEditor())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("rationale")))
                        )
                ) 
                .setSectionTitle(LUConstants.SECTION_COURSE_INFORMATION)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)      
        );
    }

    private void addLearningObjectives() {
        layout.addSection(new String[] {LUConstants.SECTION_ACADEMIC_CONTENT, 
                LUConstants.SECTION_LEARNING_OBJECTIVES}, 
                new SimpleConfigurableSection<CluProposal>()
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                           //TODO not sure where this comes from
                                return "Dummy value";
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
//TODO not sure where this goes
                            }
                        })
                        .setFormField(new KSFormField("learningObjectives", "Learning Objectives")
                        .setWidget(new KSRichEditor())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("courseSubject")))
                        )
                )
                .setSectionTitle(LUConstants.SECTION_LEARNING_OBJECTIVES)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)      
        );
    }

    private void addSyllabus() {
        layout.addSection(new String[] {LUConstants.SECTION_ACADEMIC_CONTENT, 
                LUConstants.SECTION_SYLLABUS}, 
                new SimpleConfigurableSection<CluProposal>()
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return "Dummy value";
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                //TODO figure out linkedFileSyllabus()
                            }
                        })
                        .setFormField(new KSFormField("linkedFileSyllabus", "Linked File Syllabus")
                        .setWidget(new FileUpload())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("courseTopics")))
                        )
                )                
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return "Dummy value";
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                //TODO figure out courseTopics()
                            }
                        })
                        .setFormField(new KSFormField("courseTopics", "Course Topics")
                        .setWidget(new KSTextBox())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("courseTopics")))
                        )
                )
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return "Dummy value";
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                //TODO figure out requiredTexts()
                            }
                        })
                        .setFormField(new KSFormField("requiredTexts", "Required Texts")
                        .setWidget(new KSRichEditor())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("requiredTexts")))
                        )
                )
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return "Dummy value";
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                //TODO figure out assignments()
                            }
                        })
                        .setFormField(new KSFormField("assignments", "Assignments")
                        .setWidget(new KSTextArea(500))
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("assignments")))
                        )
                )
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return "Dummy value";
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                //TODO figure out evaluation()
                            }
                        })
                        .setFormField(new KSFormField("evaluation", "Evaluation")
                        .setWidget(new KSTextArea(500))
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("evaluation")))
                        )
                )                
                .setSectionTitle(LUConstants.SECTION_SYLLABUS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)
        );
    }

    private void addLearningResults() {
        layout.addSection(new String[] {LUConstants.SECTION_ACADEMIC_CONTENT, 
                LUConstants.SECTION_LEARNING_RESULTS}, 
                new SimpleConfigurableSection<CluProposal>()
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return "Dummy value";
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                //TODO figure out evaluation()
                            }
                        })
                        .setFormField(new KSFormField("evaluationApproach", "Evaluation Approach")
                        .setWidget(new KSDropDown())
                        .setHelpInfo(new HelpInfo("myhelpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("evaluationApproach")))
                        )
                )                 
                .setSectionTitle(LUConstants.SECTION_LEARNING_RESULTS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)
        );
    }







}
