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
 * Created by venkat on 7/14/14
 */
package org.kuali.student.cm.uif.container;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroupBase;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

/**
 *
 * @author Kuali Student Team
 */
public class CMCollectionGroupBase extends CollectionGroupBase {

    public CMCollectionGroupBase(){

    }

    /**
     * This method disables all the widgets at the collection when the document is in 'Initiated' workflow status.
     *
     * @param model
     */
    @Override
    public void performInitialization(Object model) {

        boolean isInitiated = ((MaintenanceDocumentForm)model).getDocument().getDocumentHeader().getWorkflowDocument().isInitiated();

        if (isInitiated) {
            for (Component item : getItems()) {
                if (item instanceof InputField) {
                    ((InputField) item).getControl().setDisabled(true);
                }
            }

            for (Component item : getAddLineActions()) {
                Action action = (Action)item;
                action.setDisabled(true);
            }

            for (Component item : getLineActions()) {
                Action action = (Action)item;
                action.setDisabled(true);
            }

            getAddBlankLineAction().setDisabled(true);

        }

        super.performInitialization(model);

    }

}
