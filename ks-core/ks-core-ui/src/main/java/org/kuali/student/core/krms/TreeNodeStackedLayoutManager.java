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
package org.kuali.student.core.krms;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.DataBinding;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.collections.LineBuilderContext;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.layout.StackedLayoutManagerBase;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleUtils;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class TreeNodeStackedLayoutManager extends StackedLayoutManagerBase {

    @Override
    public void buildLine(LineBuilderContext lineBuilderContext) {
        super.buildLine(lineBuilderContext);

        if (lineBuilderContext.isAddLine()) {
            String nodePath = this.getNodePath(lineBuilderContext.getCollectionGroup().getContext());
            if (nodePath != null) {
                for (Group group : this.getStackedGroups()) {
                    //Set the nodepath on the add line group so that progressive rendering can use the #np
                    ComponentUtils.pushObjectToContext(group, UifConstants.ContextVariableNames.NODE_PATH, nodePath);
                }
            }
        }

    }

    private String getNodePath(Map<String, Object> context) {
        if (context.containsKey(UifConstants.ContextVariableNames.NODE_PATH)) {
            return (String) context.get(UifConstants.ContextVariableNames.NODE_PATH);

        } else if (context.containsKey(UifConstants.ContextVariableNames.PARENT)) {
            Component parent = (Component) context.get(UifConstants.ContextVariableNames.PARENT);
            return this.getNodePath(parent.getContext());
        }
        return null;
    }

}
