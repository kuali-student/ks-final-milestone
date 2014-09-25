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
import org.kuali.student.cm.course.form.wrapper.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoDisplayWrapperModel;

import java.util.List;

/**
 *
 * @see CMCourseCollectionCompareModifierBase
 * @author Kuali Student Team
 */
public class CMCourseLOCollectionCompareModifier extends CMCourseCollectionCompareModifierBase {


    @Override
    public void performCollectionCompare(Object model, Component component) {
        LoDisplayWrapperModel los;
        LoDisplayWrapperModel losCompare;

        if (model instanceof ViewCourseForm){
            ViewCourseForm form = (ViewCourseForm)model;
            los = form.getCourseInfoWrapper().getLoDisplayWrapperModel();
            losCompare = form.getCompareCourseInfoWrapper().getLoDisplayWrapperModel();
        } else if (model instanceof MaintenanceDocumentForm){
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            los = ((CourseInfoWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).getLoDisplayWrapperModel();
            losCompare = ((CourseInfoWrapper)form.getDocument().getOldMaintainableObject().getDataObject()).getLoDisplayWrapperModel();
        } else {
            throw new RuntimeException("Does not support");
        }

        int index = 0;
        for (LoDisplayInfoWrapper lo : los.getLoWrappers()) {

            if (!lo.isFakeObjectForCompare()){
                LoDisplayInfoWrapper loToCompare = getLOFromCompare(losCompare.getLoWrappers(),index);

                if (loToCompare != null){
    //                if (!StringUtils.equals(lo.getLoInfo().getId(), loToCompare.getLoInfo().getId())){
                    if (!StringUtils.equals(lo.getLoInfo().getDescr().getPlain(), loToCompare.getLoInfo().getDescr().getPlain())){
                        lo.setHightlightRow(true);
                        loToCompare.setHightlightRow(true);
                    }
                } else {
                    lo.setHightlightRow(true);
                }
            }

            index++;

        }

//        if (losCompare.getLoWrappers().size() > los.getLoWrappers().size()){
//            while (losCompare.getLoWrappers().size() > index) {
//                losCompare.getLoWrappers().get(index).setHightlightRow(true);
//                index++;
//            }
//        }
    }

    protected LoDisplayInfoWrapper getLOFromCompare(List<LoDisplayInfoWrapper> compareLOs, int index){
        if (compareLOs.isEmpty() || compareLOs.size() <= index){
            return null;
        }
        return compareLOs.get(index);
    }
}
