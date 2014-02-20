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

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class DatesKeyValuesFinder extends UifKeyValuesFinderBase {
    private transient AtpService atpService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.in("typeKey", AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY,
                AtpServiceConstants.ATP_MINI_MESTER_1_A_TYPE_KEY, AtpServiceConstants.ATP_MINI_MESTER_1_B_TYPE_KEY,
                AtpServiceConstants.ATP_SESSION_1_TYPE_KEY, AtpServiceConstants.ATP_SESSION_2_TYPE_KEY));

        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<AtpInfo> searchResult = this.getAtpService().searchForAtps(qbc,
                    ContextUtils.createDefaultContextInfo());

            sortResultList(searchResult);

            populateAtpInfoWithStartEndDateCheck(model, keyValues, searchResult);

        } catch (Exception ex) {
            throw new RuntimeException("Could not retrieve the ATP duration Dates: " + ex);
        }
        return keyValues;
    }

    /**
     * populates the AtpInfo for the list This method is used to ensures that EndDate is
     * always greater than startDate.
     * 
     * @param model
     * @param keyValues
     * @param searchResult
     */
    private void populateAtpInfoWithStartEndDateCheck(ViewModel model, List<KeyValue> keyValues,
            List<AtpInfo> searchResult) {
        if (model instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm courseForm = (MaintenanceDocumentForm) model;
            final CourseInfo courseInfo = ((CourseInfoWrapper) courseForm.getDocument().getNewMaintainableObject().getDataObject()).getCourseInfo();
            if (courseInfo.isPilotCourse()) {

                for (int i = 0; i < searchResult.size(); i++) {
                    if (courseInfo.getStartTerm().equals(searchResult.get(i).getId().toString())) {
                        break;
                    }
                    else {
                        searchResult.remove(searchResult.get(i));
                        i--;
                    }
                }
            }
            for (AtpInfo result : searchResult) {
                keyValues.add(new ConcreteKeyValue(result.getId(), result.getName()));
            }
        }
    }

    /**
     * 
     * Sorts the resultList in Asc order
     * 
     * @param searchResult
     */
    private void sortResultList(List<AtpInfo> searchResult) {
        Collections.sort(searchResult, new Comparator<AtpInfo>() {
            public int compare(AtpInfo m1, AtpInfo m2) {
                return m1.getCode().compareTo(m2.getCode());
            }
        });
    }

    private AtpService getAtpService() {
        if (atpService == null)
        {
            QName qname = new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART);
            atpService = (AtpService) GlobalResourceLoader.getService(qname);
        }
        return atpService;
    }
}
