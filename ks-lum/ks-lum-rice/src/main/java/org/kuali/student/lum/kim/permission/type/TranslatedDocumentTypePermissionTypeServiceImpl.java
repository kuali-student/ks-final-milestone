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

/**
 * 
 */
package org.kuali.student.lum.kim.permission.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.rice.core.xml.dto.AttributeSet;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.rice.kns.service.impl.DocumentTypePermissionTypeServiceImpl;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.lum.kim.KimQualificationHelper;

/**
 * Permission Type to be used for
 *
 */
public class TranslatedDocumentTypePermissionTypeServiceImpl extends DocumentTypePermissionTypeServiceImpl {

	private static Set<List<String>> attributes = new HashSet<List<String>>();

	{
		checkRequiredAttributes = true;
		// add document number as one required attribute set
		List<String> listOne = new ArrayList<String>();
		listOne.add( KimConstants.AttributeConstants.DOCUMENT_NUMBER );
		attributes.add(listOne);
		// add document type name and KEW application id as one required attribute set
		List<String> listTwo = new ArrayList<String>();
		listTwo.add( KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME );
		listTwo.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
		attributes.add(listTwo);
		// add each proposal reference type as a required attribute set
		for (String proposalReferenceType : StudentIdentityConstants.QUALIFICATION_PROPOSAL_ID_REF_TYPES) {
	        List<String> tempList = new ArrayList<String>();
	        tempList.add( proposalReferenceType );
	        attributes.add(tempList);
        }
//		List<String> listFour = new ArrayList<String>();
//		listFour.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
//		listFour.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE );
//		attributes.add(listFour);

	}

	@Override
    public AttributeSet translateInputAttributeSet(AttributeSet qualification) {
		return KimQualificationHelper.translateInputAttributeSet(super.translateInputAttributeSet(qualification));
	}

	@Override
    protected void validateRequiredAttributesAgainstReceived(AttributeSet receivedAttributes) {
		// first check KS required attributes
	    KimQualificationHelper.validateRequiredAttributesAgainstReceived(attributes, receivedAttributes, isCheckRequiredAttributes(), COMMA_SEPARATOR);
	    // if required KS attributes pass... test parent class required attributes
	    super.validateRequiredAttributesAgainstReceived(receivedAttributes);
    }

}
