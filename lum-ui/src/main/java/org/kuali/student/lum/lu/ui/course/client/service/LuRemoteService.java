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
package org.kuali.student.lum.lu.ui.course.client.service;

import org.kuali.student.lum.lu.dto.CluInfo;

/**
 * This class lists all of the methods that the remote calls between client and servlet make, 
 * most of these will be verbatim from the web service.   
 * 
 * @author Kuali Student Team
 *
 */
public interface LuRemoteService {
    public CluInfo createClu(String luTypeKey, CluInfo cluInfo);
}
