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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;

/**
 * An options finder for a ATP/Term selector.
 */
public class StartTermOptionsFinder extends TermOptionsFinder {

    /**
     * The terms will be in chronological order. This is the upper boundary of the terms that won't be display.
     * If this is null then all terms will be returned.
     */
    public String getBoundaryTermId(ViewModel model) {

        MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
        CourseInfoWrapper wrapper = (CourseInfoWrapper)form.getDocument().getNewMaintainableObject().getDataObject();


        if (wrapper.getUiHelper().isModifyWithNewVersionProposal()){
            return wrapper.getStartTermConstrainingTermId();
        }

        return StringUtils.EMPTY;

    }
}
