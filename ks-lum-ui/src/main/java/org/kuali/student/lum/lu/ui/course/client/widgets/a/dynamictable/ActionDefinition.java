/*
 * Copyright 2009 Johnson Consulting Services
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ActionHandler;

/**
 * Defines the mapping between an ActionHandler and its name
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class ActionDefinition<T> {
	private final String name;
	private final ActionHandler<T> handler;

	/**
	 * Creates a new ActionDefinition
	 * @param name the name of the action
	 * @param handler the handler to invoke when this action is performed
	 */
	public ActionDefinition(final String name, final ActionHandler<T> handler) {
		super();
		this.name = name;
		this.handler = handler;
	}

	/**
	 * Returns the ActionHandler
	 * @return the ActionHandler
	 */
	public ActionHandler<T> getHandler() {
		return handler;
	}

	/** Returns the action's name
	 * @return the action's name
	 */
	public String getName() {
		return name;
	}

}
