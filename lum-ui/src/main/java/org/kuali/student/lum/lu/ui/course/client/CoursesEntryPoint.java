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
package org.kuali.student.lum.lu.ui.course.client;

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.view.CourseProposal;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * Entry point for courses 
 * 
 * @author Kuali Student Team
 *
 */
public class CoursesEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        CourseProposal proposal = new CourseProposal();
        proposal.addSaveHandler(new SaveHandler(){

            public void onSave(SaveEvent event) {
                CourseProposal proposal = (CourseProposal)event.getSource();
                CluInfo clu = proposal.getCourseProposalClu();
                //TODO: Call Lu Service to save the clu, or should the CourseProposal
                //widget handle the save.
                Window.alert("Saving CLU: " + clu.getOfficialIdentifier().getLongName());
            }
            
        });
        proposal.show();                
    }
}
