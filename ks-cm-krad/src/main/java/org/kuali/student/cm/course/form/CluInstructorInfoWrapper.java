/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.form;

import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;

/**
 * Wrapper around the {@link CluInstructorInfo} instance for use with KRAD UI components like the StackedCollection
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class CluInstructorInfoWrapper extends CluInstructorInfo {
	
	private static final long serialVersionUID = 7495209564517379554L;

	private String displayName;
	
	private String givenName;
	
	private String principalName;
	
	public CluInstructorInfoWrapper() {
		
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	
	public String getPrincipalName() {
		return principalName;
	}
	
}
