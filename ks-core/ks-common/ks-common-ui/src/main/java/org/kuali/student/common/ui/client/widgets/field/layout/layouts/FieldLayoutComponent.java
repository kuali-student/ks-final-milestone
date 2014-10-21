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

package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

public interface FieldLayoutComponent {
	
    public static final String HELP_MESSAGE_KEY = "-help";
    public static final String INSTRUCT_MESSAGE_KEY = "-instruct";
    public static final String EXAMPLES_MESSAGE_KEY = "-examples";
    public static final String CONSTRAINT_MESSAGE_KEY = "-constraints";
    public static final String WATERMARK_MESSAGE_KEY = "-watermark";
    public static final String NAME_MESSAGE_KEY = "-name";
	public String getKey();
	public void setKey(String key);
}
