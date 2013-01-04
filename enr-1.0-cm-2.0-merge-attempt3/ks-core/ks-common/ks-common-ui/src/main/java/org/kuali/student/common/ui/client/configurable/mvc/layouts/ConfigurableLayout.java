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

package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

public interface ConfigurableLayout {
	public void addStartSection(SectionView section);
	
    public void addSection(String[] hierarchy, SectionView section);
    
	/**
 	 * Check to see if current/all section(s) is valid (ie. does not contain any errors)
 	 * 
	 * @param validationResults List of validation results for the layouts model.
	 * @param checkCurrentSectionOnly true if errors should be checked on current section only, false if all sections should be checked 
	 * @return true if the specified sections (all or current) has any validation errors
	 */
	public boolean isValid(List<ValidationResultInfo> validationResults, boolean checkCurrentSectionOnly);
}
