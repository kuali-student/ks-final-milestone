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

import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
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
public class AttachmentsLayoutManager {
    
    private DefaultCreateUpdateLayout<CluProposal> layout;
    
    public AttachmentsLayoutManager() {
        super();
    }

    public AttachmentsLayoutManager(DefaultCreateUpdateLayout<CluProposal> layout) {
        super();
        this.layout = layout;
    }

    public DefaultCreateUpdateLayout<CluProposal> addSection(String type, String state) {

        addSupportingDocs();

        return layout;
    }
    

    private void addSupportingDocs() {

        layout.addSection(new String[] {LUConstants.SECTION_ATTACHMENTS, 
                LUConstants.SECTION_SUPPORTING_DOCUMENTS}, 
                new SimpleConfigurableSection<CluProposal>()                
                .setSectionTitle(LUConstants.SECTION_SUPPORTING_DOCUMENTS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

}
