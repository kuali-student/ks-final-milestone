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

package org.kuali.student.core.organization.ui.client.view;

import java.util.Arrays;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.ListItems;

class SingleListItem implements ListItems {
    
    private String item;

    public SingleListItem(String item) {
        this.item = item;
    }

    @Override
    public List<String> getAttrKeys() {
        return null;
    }

    @Override
    public String getItemAttribute(String id, String attrkey) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public List<String> getItemIds() {
        return Arrays.asList(item);
    }

    @Override
    public String getItemText(String id) {
        if (id.equals(item))
            return item;
        return null;
    }

}
