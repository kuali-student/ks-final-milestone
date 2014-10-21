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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.util.AgendaUtilities;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

/**
 * KeyFinder for Grade Values based on the Grade Scale.
 *
 * @author Kuali Student Team
 */
public class CMGradeValuesKeyFinder extends UifKeyValuesFinderBase {

    private LRCService lrcService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        String resultValuesGroupKey = "";

        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
        RuleEditor ruleEditor = AgendaUtilities.getRuleWrapper(maintenanceForm).getRuleEditor();

        PropositionEditor propositionEditor = PropositionTreeUtil.getProposition(ruleEditor);
        if ((propositionEditor != null) && (propositionEditor instanceof LUPropositionEditor)) {
                resultValuesGroupKey = ((LUPropositionEditor) propositionEditor).getGradeScale();
        }
        
        try {

            if (resultValuesGroupKey != null) {
                ResultValuesGroupInfo rvgInfo = this.getLRCService().getResultValuesGroup(resultValuesGroupKey, this.getContextInfo());
                List<ResultValueInfo> rvInfos = this.getLRCService().getResultValuesByKeys(rvgInfo.getResultValueKeys(), this.getContextInfo());

                Collections.sort(rvInfos, new Comparator<ResultValueInfo>() {

                    @Override
                    public int compare(ResultValueInfo o1, ResultValueInfo o2) {
                        Integer first = Integer.valueOf(o1.getNumericValue());
                        Integer second = Integer.valueOf(o2.getNumericValue());
                        return (second < first) ? -1 : ((second.equals(first)) ? 0 : 1);
                    }
                });

                for (ResultValueInfo info : rvInfos) {
                    keyValues.add(new ConcreteKeyValue(info.getKey(), info.getName()));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve result values", e);
        }

        return keyValues;
    }

    private LRCService getLRCService() {
        if (lrcService == null) {
            QName qname = new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART);
            lrcService = (LRCService) GlobalResourceLoader.getService(qname);
        }
        return lrcService;
    }

    private ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
