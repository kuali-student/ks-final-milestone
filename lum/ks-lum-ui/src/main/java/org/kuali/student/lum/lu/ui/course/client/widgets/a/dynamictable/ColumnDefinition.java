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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines a DynamicTable column
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class ColumnDefinition<T> {
	/**
	 * Marker interface for column attributes
	 * @author wilj
	 *
	 */
	public interface ColumnAttribute {

	}

	private final String name;
	private final boolean sortable;
	private ColumnRenderer<T> renderer;
	private final Map<Class<? extends ColumnAttribute>, List<ColumnAttribute>> attributes = new HashMap<Class<? extends ColumnAttribute>, List<ColumnAttribute>>();

	private boolean visible = true;

	/**
	 * Creates a new ColumnDefinition
	 * @param name key used for identifying the column, such as a bean property name or database column name
	 * @param sortable true if the table can be sorted by this column
	 * @param visible true if the column is visible (column visibility can be changed at runtime)
	 * @param renderer the ColumnRenderer used to draw the column's header and cell contents
	 * @param attributes varargs list of ColumnAttributes
	 */
	public ColumnDefinition(final String name, final boolean sortable, final boolean visible,
			final ColumnRenderer<T> renderer, final ColumnAttribute... attributes) {
		super();
		this.name = name;
		this.sortable = sortable;
		this.visible = visible;
		this.renderer = renderer;
		for (final ColumnAttribute attribute : attributes) {
			addAttribute(attribute);
		}
	}

	/**
	 * Adds an attribute to this column
	 * @param attribute the ColumnAttribute to add
	 */
	public void addAttribute(final ColumnAttribute attribute) {
		final Class<? extends ColumnAttribute> c = attribute.getClass();
		List<ColumnAttribute> list = attributes.get(c);
		if (list == null) {
			list = new ArrayList<ColumnAttribute>();
			attributes.put(c, list);
		}
		list.add(attribute);
	}

	/**
	 * Returns any column attributes matching the specified class
	 * @param attributeClass the class of the attribute
	 * @return any column attributes matching the specified class
	 */
	public List<ColumnAttribute> getAttributes(final Class<? extends ColumnAttribute> attributeClass) {
		return attributes.get(attributeClass);
	}

	/**
	 * Returns the column's key name (not the display label)
	 * @return the column's key name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The ColumnRenderer used to draw the column's header and cell contents
	 * @return ColumnRenderer used to draw the column's header and cell contents
	 */
	public ColumnRenderer<T> getRenderer() {
		return renderer;
	}

	/**
	 * Returns true if this column has one or more instance of the specified attribute
	 * @param attributeClass the class of the attribute
	 * @return true if this column has one or more instance of the specified attribute
	 */
	public boolean hasAttribute(final Class<? extends ColumnAttribute> attributeClass) {
		return attributes.containsKey(attributeClass);
	}

	/**
	 * Returns true if the table can be sorted by this column
	 * @return true if the table can be sorted by this column
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * Returns true if the column is currently visible
	 * @return true if the column is currently visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Removes the specified attribute instance from this column
	 * @param instance the instance of ColumnAttribute to remove
	 */
	public void removeAttributeInstance(final ColumnAttribute instance) {
		final List<ColumnAttribute> list = attributes.get(instance.getClass());
		if (list != null) {
			list.remove(instance);
			if (list.isEmpty()) {
				attributes.remove(instance.getClass());
			}
		}
	}

	protected void setRenderer(final ColumnRenderer<T> renderer) {
		this.renderer = renderer;
	}

	/**
	 * Sets whether this column is visible.  Note that any tables using this column will have to be redrawn for the change to take effect.
	 * @param visible true to show the column, false to hide it
	 */
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}

}
