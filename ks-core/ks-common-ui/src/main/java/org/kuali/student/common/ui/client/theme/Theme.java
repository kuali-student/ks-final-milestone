/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
/**
 * 
 */
package org.kuali.student.common.ui.client.theme;

import com.google.gwt.core.client.GWT;

/**
 * This interface defines the base class required for implementing a theme for the JCS GWT Components library
 * 
 * @author wilj
 */
public interface Theme {
    public static final Theme INSTANCE = GWT.create(Theme.class);

    public CommonImages getCommonImages();

    public CommonCss getCommonCss();

    public CommonWidgets getCommonWidgets();

    public RichTextEditorImages getRichTextEditorImages();
}
