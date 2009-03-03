package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.images.TextIcons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

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
