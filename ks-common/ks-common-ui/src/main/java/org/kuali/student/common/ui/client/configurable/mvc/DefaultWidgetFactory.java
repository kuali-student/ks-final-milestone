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

package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.impl.DefaultWidgetFactoryImpl;
import org.kuali.student.r1.common.assembly.data.LookupParamMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.MetadataInterrogator;
import org.kuali.student.r1.common.assembly.data.MetadataInterrogator.ConstraintIds;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * Returns a corresponding auto generated widget based on metadata.  Not all fields can have their
 * widgets reliably generated (or it may be better to not use the default), in these cases pass the
 * custom widget into FieldDescriptor directly.
 * <br>
 * Things within the metadata taken into account:
 * access = meta.getWriteAccess(); <br>
 * isMultiLine - uses text area for multiline<br>
 * isRepeating - if this is true and the metadata has lookupdata generates a picker that can be used to produce a list<br>
 * isRichText = MetadataInterrogator.hasConstraint(meta, ConstraintIds.RICH_TEXT); <br>
 * maxLength - affects how long a text field will be<br>
 * type - affects why kind of widget is generated<br>
 * lookupMeta - generates a widget that is backed by a search and based on attributes in its lookupMeta<br>
 * additionalLookups - additional lookups for the generated widget<br>
 * canEdit <br>
 * canUnmask <br>
 * canView <br>
 * 
 * Note that this class can use gwt deferred binding to have its DefaultWidgetFactory implementation
 * overridden with a custom one, though the result is high impact for as most fields rely on this class.
 * 
 * Default widget abstract class - see DefaultWidgetFactoryImpl for implementation.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class DefaultWidgetFactory {
	private static final DefaultWidgetFactory instance = GWT.create(DefaultWidgetFactoryImpl.class);
	public static DefaultWidgetFactory getInstance() {
		return instance;
	}
	
	/**
	 * Gets the input widget by auto generating based on the metadata within the FieldDescriptor passed in
	 * @param meta
	 * @return
	 */
	public abstract Widget getWidget(FieldDescriptor field);
	
	/**
	 * Gets the input widget by auto generating based on the metadata passed in
	 * @param meta
	 * @return
	 */
	public abstract Widget getWidget(Metadata meta);
	
	/**
	 * Gets the read only widget by auto generating based on the metadata passed in
	 * @param meta
	 * @return
	 */
	public abstract Widget getReadOnlyWidget(Metadata meta);
	
	
	/**
	 * Generates a widget based on the LookupParamMetadata passed in
	 * @param meta
	 * @return
	 */
	public abstract Widget getWidget(LookupParamMetadata meta);
	
}
 
