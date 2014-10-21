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
 * Created by venkat on 9/20/14
 */
package org.kuali.student.cm.course.modifiers;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.layout.GridLayoutManager;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleUtils;
import org.kuali.rice.krad.uif.modifier.ComponentModifierBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base Modifier to find the data difference in Collection elements and mark it for compare highlight.
 * {@link CMCourseFieldCompareModifier} will compare only field differences. This modifier will take care of
 * the collections compare.
 *
 * @author Kuali Student Team
 */
public abstract class CMCourseCollectionCompareModifierBase extends ComponentModifierBase implements ViewCourseCollectionModifier {

    @Override
    public void performModification(Object model, Component component) {

        if ((component != null) && !(component instanceof CollectionGroup)) {
            throw new IllegalArgumentException(
                    "CMCourseFormatCollectionCompareModifier only support CollectionGroup components, found type: " + component.getClass());
        }

        if (component == null) {
            return;
        }

        CollectionGroup group = (CollectionGroup)component;

        if (!(((StackedLayoutManager)group.getLayoutManager()).getLineGroupPrototype().getLayoutManager() instanceof GridLayoutManager)) {
            throw new RuntimeException("This modifier suppports only Grid Layout manager prototype.");
        }

        List<DataField> itemFields = ViewLifecycleUtils.getElementsOfTypeDeep(group, DataField.class);

        List<String> hiddenProperties = new ArrayList<>();
        hiddenProperties.add("hightlightRow");

        if (!itemFields.isEmpty()){
            itemFields.get(0).setAdditionalHiddenPropertyNames(hiddenProperties);
        }

        performCollectionCompare(model,component);

        //This document load on client look for the highlight=true hidden fields and adds the highlighter css to all the rows.
        group.setOnDocumentReadyScript("jQuery(\"input[name$='hightlightRow'][value=true]\").closest(\"tbody\").children(\"tr\").addClass(\"cm-compare-highlighter\");");

    }

    @Override
    public Set<Class<? extends Component>> getSupportedComponents() {
        Set<Class<? extends Component>> components = new HashSet<Class<? extends Component>>();
        components.add(CollectionGroup.class);

        return components;
    }

    public abstract void performCollectionCompare(Object model, Component component);

}
