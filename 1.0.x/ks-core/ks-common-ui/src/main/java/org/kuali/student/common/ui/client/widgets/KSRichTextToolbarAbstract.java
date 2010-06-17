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


import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RichTextArea;

public abstract class KSRichTextToolbarAbstract extends Composite{ 


	  /**
	   * This {@link Constants} interface is used to make the toolbar's strings
	   * internationalizable.
	   */
	  public interface Strings extends Constants {

	    String black();

	    String blue();

	    String bold();

	    String color();

	    String createLink();

	    String font();

	    String green();

	    String hr();

	    String indent();

	    String insertImage();

	    String italic();

	    String justifyCenter();

	    String justifyLeft();

	    String justifyRight();

	    String large();

	    String medium();

	    String normal();

	    String ol();

	    String outdent();

	    String red();

	    String removeFormat();

	    String removeLink();

	    String size();

	    String small();

	    String strikeThrough();

	    String subscript();

	    String superscript();

	    String ul();

	    String underline();

	    String white();

	    String xlarge();

	    String xsmall();

	    String xxlarge();

	    String xxsmall();

	    String yellow();
	  }

	  
	  public abstract boolean inUse();

	  /**
	   * init a new toolbar that drives the given rich text area.
	   * 
	   * @param richText the rich text area to be controlled
	   */
	  protected abstract void init(RichTextArea richText);

}
