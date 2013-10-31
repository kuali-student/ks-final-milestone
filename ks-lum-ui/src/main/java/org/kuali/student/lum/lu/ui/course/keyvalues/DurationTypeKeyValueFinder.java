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
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

/**
 * 
 * This is the helper class for CourseView
 * It specifically looks for certain keys
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 * 
 */

public class DurationTypeKeyValueFinder extends UifKeyValuesFinderBase {

    private TypeService typeService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        Predicate predicate = PredicateFactory.in("id",
                AtpServiceConstants.DURATION_YEAR_TYPE_KEY,
                AtpServiceConstants.DURATION_FOUR_YEARS_TYPE_KEY,
                AtpServiceConstants.DURATION_HALF_SEMESTER_TYPE_KEY,
                AtpServiceConstants.DURATION_SESSION_TYPE_KEY,
                AtpServiceConstants.DURATION_PERIOD_TYPE_KEY,
                AtpServiceConstants.DURATION_MINI_MESTER_TYPE_KEY,
                AtpServiceConstants.DURATION_MONTH_TYPE_KEY,
                AtpServiceConstants.DURATION_TERM_TYPE_KEY,
                AtpServiceConstants.DURATION_HOURS_TYPE_KEY,
                AtpServiceConstants.DURATION_WEEK_TYPE_KEY,
                AtpServiceConstants.DURATION_TBD_TYPE_KEY,
                AtpServiceConstants.DURATION_MINUTES_TYPE_KEY,
                AtpServiceConstants.DURATION_SEMESTER_TYPE_KEY,
                AtpServiceConstants.DURATION_TWO_YEARS_TYPE_KEY,
                AtpServiceConstants.DURATION_DAY_TYPE_KEY,
                AtpServiceConstants.DURATION_QUARTER_TYPE_KEY);
        qBuilder.setPredicates(predicate);
        try
        {
            List<TypeInfo> list = this.getTypeService().searchForTypes(qBuilder.build(),ContextUtils.getContextInfo());

            if (list != null) {
                for (TypeInfo info : list) {
                    keyValues.add(new ConcreteKeyValue(info.getKey(), info.getName()));
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not retrieve Apt Duration types: " + ex);
        }
        return keyValues;
    }

    private TypeService getTypeService() {
        if (typeService == null)
        {
            QName qname = new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART);
            typeService = (TypeService) GlobalResourceLoader.getService(qname);
        }
        return typeService;
    }
}
