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
import org.kuali.rice.krad.uif.container.TabGroup;
import org.kuali.rice.krad.uif.modifier.ComponentModifierBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A component modifier which provides a way to prevent sections from being rendered in the tab group.
 */
public class TabGroupTabsModifier extends ComponentModifierBase {

    private List<String> excludeItemIds = new ArrayList<>();

    /**
     * The list of component IDs which will not be rendered.
     */
    public List<String> getExcludeItemIds() {
       return excludeItemIds;
    }

    public void setExcludeItemIds(List<String> excludeItemIds) {
       this.excludeItemIds = excludeItemIds;
    }

    @Override
    public void performModification(Object model, Component component) {
        if (component == null) {
            return;
        }

        if (!(component instanceof TabGroup)) {
            throw new IllegalArgumentException(
                    "Compare field initializer only support TabGroup components: " + component.getClass());
        }

        //  No need to continue if there are no exclude ids.
        if (getExcludeItemIds().isEmpty()) {
            return;
        }

        TabGroup tabGroup = (TabGroup) component;

        /*
         * Iterate through the list of items in the group, creating a new list (minus the excluded components) which will
         * replace the existing list.
         */
        List<Component> items = new ArrayList<>();
        for (Component c : tabGroup.getItems()) {
            String componentId = c.getId();
            if ( ! getExcludeItemIds().contains(componentId)) {
                items.add(c);
            }
        }
        tabGroup.setItems(items);
    }

    @Override
    public Set<Class<? extends Component>> getSupportedComponents() {
        Set<Class<? extends Component>> components = new HashSet<>();
        components.add(TabGroup.class);
        return components;
    }
}
