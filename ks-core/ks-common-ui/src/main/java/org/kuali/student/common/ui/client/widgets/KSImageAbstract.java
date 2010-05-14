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
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

/**
 * This class defines methods for implementations of a DisclosureSection.
 * Developers shouldn't reference this class, but should use KSImage
 * directly.
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public abstract class KSImageAbstract extends Composite{

/**
	 * This method is a work around because GWT.Create can't call
     * constructors with arguments.
	 * 
	 * @param url
	 */
	protected abstract void init(String url); 
	
	/**
	 * This method is a work around because GWT.Create can't call
     * constructors with arguments.
	 * 
	 * @param url
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 */
	protected abstract void init(String url, int left, int top, int width, int height);

	protected abstract void init(ImageResource resource);

	public abstract void addClickHandler(ClickHandler handler);

	public abstract Image getImage();
}
