/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.configurable.mvc.binding;

import org.kuali.student.common.ui.client.mvc.DataModel;

/**
 * A model widget binding is used to translate data from the model to something the client side widget
 * can use to display it's data.  It is also used to translate from widget data to the appropriate expected
 * model data.
 * 
 * @author Kuali Student Team
 */
public interface ModelWidgetBinding<WidgetType> {

    /**
     * Sets the model with the value from the widget
     * @param widget Widget to interpret the data from
     * @param DataModel model to have a value/values added to
     * @param path where in the model to store the data
     */
    public abstract void setModelValue(WidgetType widget, DataModel model, String path);

    /**
     * Sets the passed in widget with the appropriate data from the model
     * @param widget the widget to have its value set
     * @param model DataModel to retrieve the data from
     * @param path where in the model to get the data
     */
    public abstract void setWidgetValue(WidgetType widget, DataModel model, String path);

}
