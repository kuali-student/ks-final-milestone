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

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class LinkWrapper extends Composite{
	
	public LinkWrapper(String linkName, Widget w){
		String id = HTMLPanel.createUniqueId();
		//HTMLPanel panel = new HTMLPanel("<a href='javascript:return false;' id='" + id + "'></a>");
		HTMLPanel panel = new HTMLPanel("<a href='#'"+ linkName +" id='" + id + "'></a>");
		panel.add(w, id);
	}
	
	public LinkWrapper(String text){
		KSLabel label = new KSLabel(text);
		String id = HTMLPanel.createUniqueId();
		//HTMLPanel panel = new HTMLPanel("<a href='javascript:return false;' id='" + id + "'></a>");
		HTMLPanel panel = new HTMLPanel("<a href='#'"+ text +" id='" + id + "'></a>");
		panel.add(label, id);
	}
	
	public LinkWrapper(NavigationHandler handler, HasClickHandlers w){
		if(handler.getUrl() != null && !handler.getUrl().isEmpty()){
			String id = HTMLPanel.createUniqueId();
			HTMLPanel panel = new HTMLPanel("<a href='?url='"+ handler.getUrl() +" id='" + id + "'></a>");
			panel.add((Widget)w, id);
		}
		
	}
}
