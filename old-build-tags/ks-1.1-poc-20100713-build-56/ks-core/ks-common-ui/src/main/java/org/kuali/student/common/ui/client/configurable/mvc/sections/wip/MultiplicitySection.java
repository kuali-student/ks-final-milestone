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
 * User: hjohnson
 * Date: 2-Jun-2010
 * Time: 3:26:53 PM
 *
 */

package org.kuali.student.common.ui.client.configurable.mvc.sections.wip;

import org.kuali.student.common.ui.client.configurable.mvc.binding.wip.MultiplicityGroupBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.wip.MultiplicityTableBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip.MultiplicityGroup;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip.MultiplicityTable;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.TableFieldLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;

import com.google.gwt.user.client.ui.Widget;

public class MultiplicitySection extends BaseSection {

    private MultiplicityConfiguration config;
    private Widget widget;

    /**
     *       !!!! WORK IN PROGRESS !!!!
     *  
     *  Creates a section containing a multiplicity widget based on the configuration
     */
    public MultiplicitySection(MultiplicityConfiguration config) {
        this.config = config;
        initialize();
    }

    private void initialize() {
        buildLayout();
        this.add(layout);
    }

    private void buildLayout() {

        switch (config.getLayoutType()) {
            case GROUP:
                layout = new VerticalFieldLayout();
                widget = new MultiplicityGroup(config);
                config.getParentFd().setFieldWidget(widget);
                config.getParentFd().setWidgetBinding(new MultiplicityGroupBinding());
                this.addField(config.getParentFd());
                
                break;
            case TABLE:	
                layout = new TableFieldLayout();
                widget = new MultiplicityTable(config);                              
                config.getParentFd().setFieldWidget(widget);
                config.getParentFd().setWidgetBinding(new MultiplicityTableBinding());
                this.addField(config.getParentFd());
                
                break;
            default:
                layout = null;
            }

    }
    
    public void setParentPath(String parentPath) {
    	if (widget instanceof MultiplicityGroup) {
    		((MultiplicityGroup)widget).setParentPath(parentPath);
    	}
    	else {
    		//TODO Need this for MultiplicityTable ???
    	}
    	
    }
    
}
