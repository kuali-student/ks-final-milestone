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
package org.kuali.student.cm.course.form.wrapper;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.util.DTOWrapper;
import org.kuali.student.common.util.DisplayWrapper;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;

import java.util.HashMap;
import java.util.Map;

public class CourseJointInfoWrapper extends CourseJointInfo implements DisplayWrapper, DTOWrapper {

	private static final long serialVersionUID = -3581960069878061510L;
	
	private String searchBy;
	
	private String courseCode;

    protected Map<String, Object> extensionData;
	
	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	@Override
	public String toString() {
		return courseCode;
	}

    @Override
    public boolean isUserEntered() {
        if (StringUtils.isNotBlank(courseCode)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isNewDto() {
        return false;
    }

    @Override
    public Map<String, Object> getExtensionData() {
        return extensionData;
    }

    /**
     * Provides a way to add additional data to the wrapper object.
     *
     * @param key
     * @param value
     */
    @Override
    public void putExtensionData(String key,Object value) {
        if (extensionData == null){
            extensionData = new HashMap<>();
        }
        extensionData.put(key, value);
    }
}
