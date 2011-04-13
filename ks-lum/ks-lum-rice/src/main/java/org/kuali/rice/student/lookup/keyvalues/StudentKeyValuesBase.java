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
package org.kuali.rice.student.lookup.keyvalues;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.student.core.organization.service.OrganizationService;

/**
 * Convenience class used to help out key values classes
 *
 */
public abstract class StudentKeyValuesBase extends KeyValuesBase {

	private static OrganizationService organizationService;

	protected static OrganizationService getOrganizationService() {
		if (organizationService == null) {
	        organizationService = (OrganizationService) GlobalResourceLoader
                .getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
		}
		return organizationService;
	}

	/**
	 * Builds a valid {@link KeyLabelPair} object for use in Student system KeyValue classes. Will throw an {@link IllegalArgumentException}
	 * if the parameters needed are not passed.
	 * 
	 * @param orgId
	 * @param orgShortName
	 * @param orgLongName
	 * @param orgType
	 * @return
	 */
	
	//** question from Bonnie: Why the input parameters have orgId, orgShortName, orgLongName and orgType,
	//** but the output of KeyLabelPair is constructed with orgShortName for both key and label?
	//** This looks weird for me.
	protected static KeyLabelPair buildKeyLabelPair(String orgId, String orgShortName, String orgLongName, String orgType) {
		if (StringUtils.isBlank(orgShortName)) {
			throw new IllegalArgumentException("Blank value for orgShortName is invalid.");
		}
		//	return new KeyLabelPair(orgShortName, orgShortName);
		return new KeyLabelPair(orgId, orgShortName);
	}

}
