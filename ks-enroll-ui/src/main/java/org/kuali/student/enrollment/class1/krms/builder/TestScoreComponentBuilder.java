/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class TestScoreComponentBuilder implements ComponentBuilder<EnrolPropositionEditor> {

    private CourseService courseService;

    private static final String TEST_CLU_KEY = "kuali.term.parameter.type.test.clu.id";

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(EnrolPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String courseId = termParameters.get(TEST_CLU_KEY);
        if (courseId != null) {
            try {
                CourseInfo courseInfo = this.getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
                propositionEditor.setCourseInfo(courseInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(EnrolPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCourseInfo() != null){
            termParameters.put(TEST_CLU_KEY, propositionEditor.getCourseInfo().getId());
        }
        return termParameters;
    }

    @Override
    public void onSubmit(EnrolPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }
}
