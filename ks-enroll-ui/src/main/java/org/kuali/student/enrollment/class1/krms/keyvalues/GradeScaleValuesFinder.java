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
* Created by Paul on 2013/01/10
*/
package org.kuali.student.enrollment.class1.krms.keyvalues;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
* This class //TODO ...
*
* @author Kuali Student Team
*/
public class GradeScaleValuesFinder extends KeyValuesBase {

    private LRCService lrcService;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        Predicate predicate = PredicateFactory.equal("type", LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        qBuilder.setPredicates(predicate);
        try
        {
            List<ResultScaleInfo> list = this.getLRCService().searchForResultScales(qBuilder.build(), getContextInfo());
            if (list != null) {
                for (ResultScaleInfo info : list) {
                    keyValues.add(new ConcreteKeyValue(info.getKey(), info.getName()));
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return keyValues;
    }

    private LRCService getLRCService() {
        if (lrcService == null)
        {
            QName qname = new QName(LrcServiceConstants.NAMESPACE,LrcServiceConstants.SERVICE_NAME_LOCAL_PART);
            lrcService = (LRCService) GlobalResourceLoader.getService(qname);
        }
        return lrcService;
    }

    private ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
