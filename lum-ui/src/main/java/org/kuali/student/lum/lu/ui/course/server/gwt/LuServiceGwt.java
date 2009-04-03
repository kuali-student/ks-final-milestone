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

import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class LuServiceGwt extends RemoteServiceServlet implements LuRpcService{

    private static final long serialVersionUID = 1L;
    LuRpcService luRpcService;
    
    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.LuRemoteService#createClu(java.lang.String, org.kuali.student.lum.lu.dto.CluInfo)
     */
    @Override
    public CluInfo createClu(String luTypeKey, CluInfo cluInfo) {
        return luRpcService.createClu(luTypeKey, cluInfo);
    }

}
