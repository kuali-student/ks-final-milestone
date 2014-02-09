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
package org.kuali.student.lum.lu.ui.course.keyvalues;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;

/**
 * 
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class LoCategoryInfoTypeKeyValueFinder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 147855845619407560L;

	private LearningObjectiveService learningObjectiveService;
	
	@SuppressWarnings("deprecation")
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		try {
			List<TypeInfo> types = getLearningObjectiveService().getLoCategoryTypes();
			for (TypeInfo typeInfo : types) {
				keyValues.add(new ConcreteKeyValue(typeInfo.getKey(), typeInfo.getName()));
			}
		} catch (Exception e) {
			throw new RuntimeException("An error occured while getting the TypeInfos for the LoCategoryInfo object.", e);
		}
		return keyValues;
	}
	

	
	private LearningObjectiveService getLearningObjectiveService() {
		if (learningObjectiveService == null) {
			learningObjectiveService = GlobalResourceLoader.getService(new QName(LearningObjectiveServiceConstants.NAMESPACE, LearningObjectiveService.class.getSimpleName()));
		}
		return learningObjectiveService;
	}

}
