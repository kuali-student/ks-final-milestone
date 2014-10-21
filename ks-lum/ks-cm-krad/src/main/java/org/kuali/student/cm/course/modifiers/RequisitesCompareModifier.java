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
 */
package org.kuali.student.cm.course.modifiers;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.layout.GridLayoutManager;
import org.kuali.rice.krad.uif.modifier.CompareFieldCreateModifier;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.ViewCourseForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.util.CourseProposalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A compare modifier which deals with comparing requisites. It doesn't do a line by line comparison. It simply
 * highlights the entire requisites section.
 */
public class RequisitesCompareModifier extends CompareFieldCreateModifier {

    @Override
    public void performModification(Object model, Component component) {
        if (component == null) {
            return;
        }

        if ((component != null) && !(component instanceof Group)) {
            throw new IllegalArgumentException(
                    "Compare field initializer only support Group components which contain requisites: " + component.getClass());
        }

        Group group = (Group) component;
        CourseInfoWrapper leftDataObject = null;
        CourseInfoWrapper rightDataObject = null;

        if (model instanceof MaintenanceDocumentForm) {
            leftDataObject = (CourseInfoWrapper) ((MaintenanceDocumentForm) model).getDocument().getNewMaintainableObject().getDataObject();
            rightDataObject = (CourseInfoWrapper) ((MaintenanceDocumentForm) model).getDocument().getOldMaintainableObject().getDataObject();
        } else if (model instanceof ViewCourseForm) {
            leftDataObject = ((ViewCourseForm) model).getCourseInfoWrapper();
            rightDataObject = ((ViewCourseForm) model).getCompareCourseInfoWrapper();
        } else {
            throw new RuntimeException("Unsupported model class.");
        }

        boolean isRequisitesEqual = CourseProposalUtil.isRequisitesEqual(leftDataObject.getAgendas(), rightDataObject.getAgendas());

        //  If the requisites aren't equal then highlight the row
        if (! isRequisitesEqual) {
            List<String> rowCssClasses = new ArrayList<>();
            rowCssClasses.add("cm-compare-highlighter");
            if (group.getLayoutManager() instanceof GridLayoutManager) {
                ((GridLayoutManager)group.getLayoutManager()).setRowCssClasses(rowCssClasses);
            }
        }
    }
}
