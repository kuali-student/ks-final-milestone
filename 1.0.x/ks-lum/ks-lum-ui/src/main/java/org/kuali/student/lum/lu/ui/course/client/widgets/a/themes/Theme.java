/*
 * Copyright 2009 Johnson Consulting Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.themes;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.CommonImages;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.CommonMessages;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.ScrollImages;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableImages;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableMessages;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportImages;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export.ExportMessages;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.notifications.NotificationImages;

/**
 * This interface defines the base class required for implementing a theme for the JCS GWT Components library
 * @author wilj
 *
 */
public interface Theme {
	//public static final Theme INSTANCE = GWT.create(Theme.class);

	CommonImages getCommonImages();

	CommonMessages getCommonMessages();

	ExportImages getExportImages();

	ExportMessages getExportMessages();

	NotificationImages getNotificationImages();

	ScrollImages getScrollImages();

	TableImages getTableImages();

	TableMessages getTableMessages();
}
