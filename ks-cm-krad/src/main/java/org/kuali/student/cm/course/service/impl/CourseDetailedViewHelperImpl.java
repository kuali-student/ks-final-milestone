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
 * Created by venkat on 7/22/14
 */
package org.kuali.student.cm.course.service.impl;

import org.kuali.rice.krad.uif.util.LifecycleElement;
import org.kuali.student.cm.course.service.CourseDetailedViewHelper;

/**
 * This is the view helper service used at Course Detailed view. To avoid code duplication and proposal review
 * and course view are pretty much same, we're extending from <class>CourseInfoMaintainableImpl</class>
 * to retrieve the course information and populate all the information to the <class>ReviewProposalDisplay</class>,
 * which is the one being used by course view and proposal review as the model to display all the information at the ui.
 * The down side of this approach is we're using a <class>Maintainable</class> at the regular view but it's really
 * worth as it's not going to cause any issues and we're avoiding a lot of code duplications.
 *
 * @author Kuali Student Team
 */
public class CourseDetailedViewHelperImpl extends CourseInfoMaintainableImpl implements CourseDetailedViewHelper {

    /**
     * As we're extending from <code>CourseInfoMaintainableImpl</code>, we dont want any of the
     * maintenance document related logics here. Overriding to avoid all the maintenance document
     * related logic
     *
     * @param element
     * @param model
     */
    @Override
    public void performCustomApplyModel(LifecycleElement element, Object model) {

    }

    /**
     * As we're extending from <code>CourseInfoMaintainableImpl</code>, we dont want any of the
     * maintenance document related logics here. Overriding to avoid all the maintenance document
     * related logic
     * @param element
     * @param model
     * @param parent
     */
    @Override
    public void performCustomFinalize(LifecycleElement element, Object model, LifecycleElement parent) {

    }

    /**
     * As we're extending from <code>CourseInfoMaintainableImpl</code>, we dont want any of the
     * maintenance document related logics here. Overriding to avoid all the maintenance document
     * related logic
     * @param model
     */
    @Override
    public void performCustomViewFinalize(Object model) {

    }
}
