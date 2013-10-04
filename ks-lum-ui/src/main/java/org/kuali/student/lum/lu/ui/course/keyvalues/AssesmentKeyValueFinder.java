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

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

/**
 * KeyValueFinder for result value groups.
 *
 * @author Kuali Student Team
 */
public class AssesmentKeyValueFinder extends KeyValuesBase {

    private LRCService lrcService;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        Predicate typePredicate = PredicateFactory.in("type", LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED,
                LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE,
                LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
        Predicate idPredicate = PredicateFactory.in("id", LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PLUS_MINUS_STANDARD,
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER,
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL,
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY,
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_COMPLETEDNOTATION,
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE);
        qBuilder.setPredicates(PredicateFactory.and(typePredicate, idPredicate));
        try
        {
            List<ResultValuesGroupInfo> list = this.getLRCService().searchForResultValuesGroups(qBuilder.build(), getContextInfo());
            if (list != null) {
                for (ResultValuesGroupInfo info : list) {
                    keyValues.add(new ConcreteKeyValue(info.getKey(), info.getName()));
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return keyValues;
    }

    private LRCService getLRCService() {
        if (lrcService == null)
        {
            QName qname = new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART);
            lrcService = (LRCService) GlobalResourceLoader.getService(qname);
        }
        return lrcService;
    }

    private ContextInfo getContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }

}
