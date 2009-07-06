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

import java.util.Calendar;
import java.util.Date;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.PropertyBinding;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class AdminstrativeLayoutManager {
    
    private DefaultCreateUpdateLayout<CluProposal> layout;
    
    public AdminstrativeLayoutManager() {
        super();
    }

    public AdminstrativeLayoutManager(DefaultCreateUpdateLayout<CluProposal> layout) {
        super();
        this.layout = layout;
    }

    public DefaultCreateUpdateLayout<CluProposal> addSection(String type, String state) {

        addCredits();
        addActiveDates();
        addFinancials();
        addProgramRequirements();
        
        return layout;
    }
    
    private void addCredits() {

        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE, 
                LUConstants.SECTION_CREDITS}, 
                new SimpleConfigurableSection<CluProposal>()                
                .setSectionTitle(LUConstants.SECTION_CREDITS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

    private void addActiveDates() {

        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE, 
                LUConstants.SECTION_ACTIVE_DATES}, 
                new SimpleConfigurableSection<CluProposal>()      
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                // TODO figure out how to get the originating faculty member
                                return new Date(2010,1,1);
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                // TODO figure out startDate
                            }
                        })
                        .setFormField(new KSFormField("startDate", "Start Date")
                        .setWidget(new KSDatePicker())
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("startDAte")))
                        )
                )                
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                // TODO figure out how to get the originating faculty member
                                return new Date(2010,1,1);
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                // TODO figure out endDate
                            }
                        })
                        .setFormField(new KSFormField("endDate", "EndDate")
                        .setWidget(new KSDatePicker())
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("endDAte")))
                        )
                ) 
                .setSectionTitle(LUConstants.SECTION_ACTIVE_DATES)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

    private void addFinancials() {

        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE, 
                LUConstants.SECTION_FINANCIALS}, 
                new SimpleConfigurableSection<CluProposal>()                
                .setSectionTitle(LUConstants.SECTION_FINANCIALS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

    private void addProgramRequirements() {

        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE, 
                LUConstants.SECTION_PROGRAM_REQUIREMENTS}, 
                new SimpleConfigurableSection<CluProposal>()                
                .setSectionTitle(LUConstants.SECTION_PROGRAM_REQUIREMENTS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }
}
