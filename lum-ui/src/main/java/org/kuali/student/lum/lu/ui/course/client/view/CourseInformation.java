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
package org.kuali.student.lum.lu.ui.course.client.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseNumber;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseInformation extends CourseSection{   
    
    /**
     * @param controller
     * @param name
     */
    public CourseInformation(Controller controller) {
        super(controller, "CourseInformation", "Course Information");
    }    
    
    public void init() {
        addFormField("courseNumber", "Course Number", new CourseNumber());
        addTextField("proposedTitle", "Proposed Course Title");
        addTextField("transcriptTitle", "Transcript Title");
        
        addFormField("description","Decription", new KSTextArea());        
        addFormField("rationale","Rationale", new KSTextArea());        
    }

    public void redraw() {
        CluInfo clu = getCourseInformation();

        getForm().setFieldValue("proposedTitle", clu.getOfficialIdentifier().getLongName());
        getForm().setFieldValue("transcriptTitle", clu.getOfficialIdentifier().getShortName());
        if (clu.getDesc() != null){
            getForm().setFieldValue("description", clu.getDesc().getFormatted());
        }
    }

    public CluInfo convert(){
        CluInfo clu = getCourseInformation();
        
        clu.getOfficialIdentifier().setLongName(getForm().getFieldText("proposedTitle"));
        clu.getOfficialIdentifier().setShortName(getForm().getFieldText("transcriptTitle"));
        
        String desc = getForm().getFieldText("description");
        if (clu.getDesc() == null){
            RichTextInfo richTextDesc = new RichTextInfo();
            richTextDesc.setFormatted(desc);
            clu.setDesc(richTextDesc);
        } else {
            clu.getDesc().setFormatted(desc);
        }
        
        String rationale = getForm().getFieldText("rationale");
        if (clu.getMarketingDesc() == null){
            RichTextInfo richTextRationale = new RichTextInfo();
            richTextRationale.setFormatted(rationale);
        } else {
            clu.getMarketingDesc().setFormatted(rationale);
        }
        
        return clu;
    }
}
