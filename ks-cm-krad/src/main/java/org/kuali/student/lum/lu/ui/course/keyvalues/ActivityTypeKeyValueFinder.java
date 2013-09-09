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
package org.kuali.student.lum.lu.ui.course.keyvalues;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

/**
 * 
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class ActivityTypeKeyValueFinder extends UifKeyValuesFinderBase {

    private CluService cluService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        try
        {
            List<TypeInfo> list = this.getCluService().getLuTypes(ContextUtils.getContextInfo());

            if (list != null) {
                for (TypeInfo info : list) {
                    if ( info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_DIRECTED_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_EXP_LEARNING_OTHER_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_HOMEWORK_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_SEMINAR_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_TUTORIAL_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_WEBDISCUSS_TYPE_KEY) ||
                            info.getKey().equals(CluServiceConstants.COURSE_ACTIVITY_WEBLECTURE_TYPE_KEY))
                        keyValues.add(new ConcreteKeyValue(info.getKey(), info.getName()));
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not retrieve Clu Info types: " + ex);
        }
        return keyValues;
    }

    private CluService getCluService() {
        if (cluService == null)
        {
            QName qname = new QName(CluServiceConstants.NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART);
            cluService = (CluService) GlobalResourceLoader.getService(qname);
        }
        return cluService;
    }

    private final Hashtable<String, String> luTypes = new Hashtable<String, String>() {
        {
            put("kuali.lu.type.activity.Directed", "Directed");
            put("kuali.lu.type.activity.Discussion", "Discussion");
            put("kuali.lu.type.activity.ExperientialLearningOROther", "Experiential Learning/Other");
            put("kuali.lu.type.activity.Homework", "Homework");
            put("kuali.lu.type.activity.Lab", "Lab");
            put("kuali.lu.type.activity.Lecture", "Lecture");
            put("kuali.lu.type.activity.Seminar", "Lecture or Seminar");
            put("kuali.lu.type.activity.Tutorial", "Tutorial");
            put("kuali.lu.type.activity.WebDiscussion", "Web Discussion");
            put("kuali.lu.type.activity.WebLecture", "Web Lecture");
        }
    };
}
