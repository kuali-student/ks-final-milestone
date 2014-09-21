/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by venkat on 9/20/14
 */
package org.kuali.student.cm.course.modifiers;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.ViewCourseForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.OutcomeReviewSection;

import java.util.List;

/**
 *
 * @author Kuali Student Team
 */
public class ViewCourseOutcomeCollectionModifier extends ViewCourseCollectionModifierBase {

    @Override
    public void performCollectionCompare(Object model, Component component) {
        List<OutcomeReviewSection> outcomes;
        List<OutcomeReviewSection> outcomeCompares;

        if (model instanceof ViewCourseForm){
            ViewCourseForm form = (ViewCourseForm)model;
            outcomes = form.getCourseInfoWrapper().getReviewProposalDisplay().getCourseLogisticsSection().getOutcomes();
            outcomeCompares = form.getCompareCourseInfoWrapper().getReviewProposalDisplay().getCourseLogisticsSection().getOutcomes();
        } else if (model instanceof MaintenanceDocumentForm){
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            outcomes = ((CourseInfoWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).getReviewProposalDisplay().getCourseLogisticsSection().getOutcomes();
            outcomeCompares = ((CourseInfoWrapper)form.getDocument().getOldMaintainableObject().getDataObject()).getReviewProposalDisplay().getCourseLogisticsSection().getOutcomes();
        } else {
            throw new RuntimeException("Does not support");
        }

        int index = 0;
        for (OutcomeReviewSection outcome : outcomes) {


            OutcomeReviewSection compareOutcome = getOutcomeCompare(outcomeCompares,index);
            if (compareOutcome != null) {

                if (!StringUtils.equals(outcome.getOutComeValue(),compareOutcome.getOutComeValue()) ||
                    !StringUtils.equals(outcome.getOutComeType(),compareOutcome.getOutComeType())){
                    outcome.setHightlightRow(true);
                }
            } else {
                compareOutcome.setHightlightRow(true);
            }

            index++;

        }
    }

    protected OutcomeReviewSection getOutcomeCompare(List<OutcomeReviewSection> outcomeCompares, int index){
        if (outcomeCompares.isEmpty() || outcomeCompares.size() <= index){
            return null;
        }
        return outcomeCompares.get(index);
    }

}