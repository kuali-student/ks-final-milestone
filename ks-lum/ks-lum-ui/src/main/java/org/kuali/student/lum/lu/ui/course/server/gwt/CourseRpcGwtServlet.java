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
package org.kuali.student.lum.lu.ui.course.server.gwt;

import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class CourseRpcGwtServlet extends AbstractBaseDataOrchestrationRpcGwtServlet implements CourseRpcService {

    private static final long serialVersionUID = 1L;
    
    private static final String DEFAULT_METADATA_STATE = "active";
    private static final String DEFAULT_METADATA_TYPE = null;
    private static final String WF_TYPE_CLU_DOCUMENT = "CluCreditCourseProposal";


    @Override
    protected String deriveAppIdFromData(Data data) {
        CreditCourseHelper clu = CreditCourseHelper.wrap(data);
        if(clu!=null&&clu.getData()!=null){
            return clu.getId();
        }
        return null;  
     }

    @Override
    protected String deriveDocContentFromData(Data data) {
        // TODO hjohnson - WHAT GOES HERE?
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        return DEFAULT_METADATA_STATE;
    }

    @Override
    protected String getDefaultMetaDataType() {
        return DEFAULT_METADATA_TYPE;
    }

    @Override
    protected String getDefaultWorkflowDocumentType() {
        return WF_TYPE_CLU_DOCUMENT;
    }

}
