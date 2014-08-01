
/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by meswaran on 8/1/2014
 */
package org.kuali.student.cm.course.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.cm.course.form.wrapper.CourseJointInfoWrapper;
import org.kuali.student.cm.course.service.util.CourseCodeSearchUtil;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for create course initial view.
 */
public class CreateCourseInitialViewHelper extends ViewHelperServiceImpl {

    private transient CluService cluService;

    public List<String> searchForCourses(String courseNumber) {
        List<CourseJointInfoWrapper> courseJointInfoWrappers = CourseCodeSearchUtil.searchForCourseJointInfos(courseNumber, getCluService());
        List<String> courseCodeList = new ArrayList<String>();
        for (CourseJointInfoWrapper courseJointInfoWrapper : courseJointInfoWrappers) {
            courseCodeList.add(courseJointInfoWrapper.getCourseCode());
        }
        return courseCodeList;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluService.class.getSimpleName()));
        }
        return cluService;
    }


}
