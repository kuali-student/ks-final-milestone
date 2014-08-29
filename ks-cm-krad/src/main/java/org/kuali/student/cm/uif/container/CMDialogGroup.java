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
 * Created by delyea on 8/7/14
 */
package org.kuali.student.cm.uif.container;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.DialogGroup;
import org.kuali.rice.krad.uif.control.TextAreaControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;

/**
 * This class is a workaround to get Dialogs working when using Rice 2.4.1. It binds the explanation field
 * (or any textarea for that matter) to a map rather than to a single field on the form which removes the
 * extra commas that were being added to the system.
 *
 * TODO: Remove this workaround class once KS has been updated to Rice 2.5 (https://jira.kuali.org/browse/KSCM-2560)
 *
 * @author Kuali Student Team
 */
public class CMDialogGroup extends DialogGroup {

    @Override
    public void performInitialization(Object model) {
        super.performInitialization(model);

        for (Component component : getItems()) {
            if (component instanceof InputField) {
                InputField inputField = (InputField)component;
                if (inputField.getControl() instanceof TextAreaControl) {
                    inputField.setPropertyName("uiHelper." + ProposalElementsWrapper.ProposalUIHelper.DIALOG_EXPLANATIONS_PROPERTY + "['" + getId() + "']");
                    inputField.getBindingInfo().setBindToForm(false);
                }
            }
        }
    }

}
