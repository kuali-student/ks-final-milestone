/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.common.ui.client.mvc.ApplicationEventHandler;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.Composite;

@Deprecated
public abstract class ConfigurableLayout<T extends Idable> extends Composite {
	private T object = null;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
	
	public abstract ConfigurableLayout<T> addSection(String[] hierarchy, LayoutSection<T>section);
	public abstract void selectSection(String...hierarchy);
	public abstract void render();
	
    @SuppressWarnings("unchecked")
    public HandlerRegistration addApplicationEventHandler(Type type, ApplicationEventHandler handler) {
        return addHandler(handler, type);
    }
}
