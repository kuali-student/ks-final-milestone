/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.common.uif.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.Collection;
import java.util.List;

/**
 * Util methods related to the presentation layer spring controller.
 *
 * @author Kuali Student Team
 */
public class KSControllerHelper {

    public static ViewHelperService getViewHelperService(UifFormBase form){
        if (form.getView() != null && form.getView().getViewHelperService() != null){
            return form.getView().getViewHelperService();
        }else{
            return form.getPostedView().getViewHelperService();
        }
    }

    /**
     * Returns the line index from where the user takes an action.
     *
     * @see #getSelectedCollectionItem(org.kuali.rice.krad.web.form.UifFormBase)
     * @param form uif form
     * @return the selected index
     */
    public static int getSelectedCollectionLineIndex(UifFormBase form){

        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);

        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Unable to determine the selected collection path");
        }

        int selectedLineIndex = -1;
        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Unable to determine the selected line index");
        }

        return selectedLineIndex;
    }

    /**
     * This returns the current collection line item from where the user takes an action.
     *
     * @see #getSelectedCollectionLineIndex(org.kuali.rice.krad.web.form.UifFormBase)
     * @param form uif form
     * @return object at the selected collection line
     */
    public static Object getSelectedCollectionItem(UifFormBase form) {

        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set");
        }

        int selectedLineIndex = getSelectedCollectionLineIndex(form);

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        Object selectedObject = ((List<Object>) collection).get(selectedLineIndex);

        return selectedObject;
    }
}
