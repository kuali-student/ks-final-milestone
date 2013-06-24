/**
 * Copyright 2005-2012 The Kuali Foundation
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
import java.util.List;
import java.util.Locale;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
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

//        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
//        Predicate predicate = PredicateFactory.in("id",
//                LuiServiceConstants.DIRECTED_ACTIVITY_OFFERING_TYPE_KEY,
//                LuiServiceConstants.DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY,
//                LuiServiceConstants.HOMEWORK_ACTIVITY_OFFERING_TYPE_KEY,
//                LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY,
//                LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
//                LuiServiceConstants.SEMINAR_ACTIVITY_OFFERING_TYPE_KEY,
//                LuiServiceConstants.TUTORIAL_ACTIVITY_OFFERING_TYPE_KEY,
//                LuiServiceConstants.WEB_DISCUSS_ACTIVITY_OFFERING_TYPE_KEY,
//                LuiServiceConstants.WEB_LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
//        qBuilder.setPredicates(predicate);
        String luTypeKey = CluServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY+
                CluServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY+
                CluServiceConstants.COURSE_ACTIVITY_TUTORIAL_TYPE_KEY+
                CluServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY+
                CluServiceConstants.COURSE_ACTIVITY_WEBLECTURE_TYPE_KEY+
                CluServiceConstants.COURSE_ACTIVITY_WEBDISCUSS_TYPE_KEY+
                CluServiceConstants.COURSE_ACTIVITY_DIRECTED_TYPE_KEY;
        try
        {
            List<TypeInfo> list = this.getCluService().getLuTypes(getContextInfo());

            if (list != null) {
                for (TypeInfo info : list) {
                    keyValues.add(new ConcreteKeyValue(info.getKey(), info.getName()));
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not retrieve Lui Info types: " + ex);
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

    private ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);

        return contextInfo;
    }
}
