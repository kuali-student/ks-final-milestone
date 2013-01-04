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

package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.user.client.ui.Composite;

public abstract class KSButtonAbstract extends Composite implements HasClickHandlers, HasMouseOverHandlers, HasMouseOutHandlers{

	public static enum ButtonStyle{
		PRIMARY("ks-button-primary", "ks-button-primary-disabled"),
		SECONDARY("ks-button-secondary", "ks-button-secondary-disabled"),
		PRIMARY_SMALL("ks-button-primary-small", "ks-button-primary-small-disabled"),
		SECONDARY_SMALL("ks-button-secondary-small", "ks-button-secondary-small-disabled"),
		FORM_SMALL("ks-form-button-small", null),
		FORM_LARGE("ks-form-button-large", null),
		HELP("ks-form-module-elements-help", null),
		DELETE("ks-form-module-elements-delete", null),
		ANCHOR_LARGE_CENTERED("ks-link-large", "ks-link-large-disabled"),
		DEFAULT_ANCHOR("ks-link", "ks-link-disabled");
		
		private String style;
		private String disabledStyle;

		private ButtonStyle(String style, String disabledStyle){
			this.style = style;
			this.disabledStyle = disabledStyle;
		}
		
		public String getStyle(){
			return style;
		}
		
		public String getDisabledStyle() {
			return disabledStyle;
		}
	};
	
	public abstract boolean isEnabled();

	public abstract void setEnabled(boolean enabled);
	
	public abstract void setText(String text);
	
    public abstract void init();
    
	public abstract void init(String text);
	
	public abstract void init(String text, ButtonStyle style);		

	public abstract void init(String text, ClickHandler handler);

    public abstract void init(String text, ButtonStyle style, ClickHandler handler);
}
