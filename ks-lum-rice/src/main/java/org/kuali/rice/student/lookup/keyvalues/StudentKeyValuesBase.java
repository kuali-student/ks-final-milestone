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
	protected static KeyLabelPair buildKeyLabelPair(String orgId, String orgShortName, String orgLongName, String orgType) {
		if (StringUtils.isBlank(orgShortName)) {
			throw new IllegalArgumentException("Blank value for orgShortName is invalid.");
		}
		return new KeyLabelPair(orgShortName, orgShortName);
	}

}
