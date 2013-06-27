/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by bobhurt on 5/3/13
 */
package org.kuali.rice.krad.uif.widget;

import org.kuali.rice.krad.datadictionary.parse.BeanTag;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.view.View;

import java.util.List;

/**
 * Widget that provides dynamic select options to the user as they enter the
 * value (also known as auto-complete).  A "Combobox" is also possible which
 * provides a button to start a search or (if no query term is entered) to
 * list the entire table (caution!).
 *
 * <p>
 * Widget is backed by an <code>AttributeQuery</code> that provides the
 * configuration for executing a query server side that will retrieve the valid
 * option values.
 * </p>
 *
 * @author Kuali Student Team
 */
@BeanTag(name = "suggest2", parent = "Uif-Suggest2")
public class Suggest2 extends Suggest {

    private boolean comboboxButtonEnabled = false;
    private boolean customEntryAllowed = true;
    private boolean loadingImageEnabled = true;


    public boolean isComboboxButtonEnabled() {
        return comboboxButtonEnabled;
    }
    public void setComboboxButtonEnabled(boolean comboboxButtonEnabled) {
        this.comboboxButtonEnabled = comboboxButtonEnabled;
    }

    public boolean isCustomEntryAllowed() {
        return customEntryAllowed;
    }
    public void setCustomEntryAllowed(boolean customEntryAllowed) {
        this.customEntryAllowed = customEntryAllowed;
    }

    public boolean isLoadingImageEnabled() {
        return loadingImageEnabled;
    }
    public void setLoadingImageEnabled(boolean loadingImageEnabled) {
        this.loadingImageEnabled = loadingImageEnabled;
    }
}
