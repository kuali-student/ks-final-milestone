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
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 * 
 * copy from ks-lum/ks-lum-impl/src/main/java/org/kuali/student/r2/lum/service/search/AtpSeasonTypeSearch.java
 * 
 */

public class AssesmentKeyValueFinder extends KeyValuesBase {
    private LRCService lrcService;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        Predicate predicate = PredicateFactory.in("id", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_COMPLETED,
                LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER,
                LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PF,
                LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PERCENTAGE,
                LrcServiceConstants.RESULT_SCALE_KEY_GRADE_ADMIN
                );
        qBuilder.setPredicates(predicate);
        try
        {
            List<ResultScaleInfo> list = this.getLRCService().searchForResultScales(qBuilder.build(), getContextInfo());
            if (list != null) {
                for (ResultScaleInfo info : list) {
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
