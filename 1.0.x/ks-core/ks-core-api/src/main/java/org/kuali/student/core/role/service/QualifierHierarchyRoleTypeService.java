/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.role.service;

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.kim.bo.types.dto.AttributeSet;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 */
@WebService(name = "QHRoleTypeService", targetNamespace = "http://student.kuali.org/wsdl/qhRoleType")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface QualifierHierarchyRoleTypeService {

    public boolean doesRoleQualifierMatchQualification(AttributeSet qualification, AttributeSet roleQualifier);
    
    public boolean doRoleQualifiersMatchQualification(AttributeSet qualification, List<AttributeSet> roleQualifierList);
    
    public List<AttributeSet> getAllImpliedQualifications(AttributeSet qualification);
    
    public List<AttributeSet> getAllImplyingQualifications(AttributeSet qualification);
    
    public List<String> getAcceptedAttributeNames();
}
