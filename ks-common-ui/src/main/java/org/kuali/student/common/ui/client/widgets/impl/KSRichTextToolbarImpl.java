package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSRichTextToolbarAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSRichTextToolbarAbstract.Strings;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
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

//TODO: Implement using i18n messages
public class KSRichTextToolbarImpl extends KSRichTextToolbarAbstract{ 

	  /**
	   * We use an inner EventHandler class to avoid exposing event methods on the
	   * RichTextToolbar itself.
	   */
	  private class EventHandler implements ClickHandler, ChangeHandler,
	      KeyUpHandler, MouseDownHandler, MouseUpHandler, FocusHandler, BlurHandler{

	    public void onChange(ChangeEvent event) {
	      Widget sender = (Widget) event.getSource();

	      if (sender == backColors) {
	        basic.setBackColor(backColors.getValue(backColors.getSelectedIndex()));
	        backColors.setSelectedIndex(0);
	      } else if (sender == foreColors) {
	        basic.setForeColor(foreColors.getValue(foreColors.getSelectedIndex()));
	        foreColors.setSelectedIndex(0);
	      } else if (sender == fonts) {
	        basic.setFontName(fonts.getValue(fonts.getSelectedIndex()));
	        fonts.setSelectedIndex(0);
	      } else if (sender == fontSizes) {
	        basic.setFontSize(fontSizesConstants[fontSizes.getSelectedIndex() - 1]);
	        fontSizes.setSelectedIndex(0);
	      }
	      inUse = false;
	      richText.setFocus(true);
	    }

	    public void onClick(ClickEvent event) {
	      Widget sender = (Widget) event.getSource();

	      if (sender == bold) {
	        basic.toggleBold();
	      } else if (sender == italic) {
	        basic.toggleItalic();
	      } else if (sender == underline) {
	        basic.toggleUnderline();
	      } else if (sender == subscript) {
	        basic.toggleSubscript();
	      } else if (sender == superscript) {
	        basic.toggleSuperscript();
	      } else if (sender == strikethrough) {
	        extended.toggleStrikethrough();
	      } else if (sender == indent) {
	        extended.rightIndent();
	      } else if (sender == outdent) {
	        extended.leftIndent();
	      } else if (sender == justifyLeft) {
	        basic.setJustification(RichTextArea.Justification.LEFT);
	      } else if (sender == justifyCenter) {
	        basic.setJustification(RichTextArea.Justification.CENTER);
	      } else if (sender == justifyRight) {
	        basic.setJustification(RichTextArea.Justification.RIGHT);
	      } else if (sender == insertImage) {
	        String url = Window.prompt("Enter an image URL:", "http://");
	        if (url != null) {
	          extended.insertImage(url);
	        }
	      } else if (sender == createLink) {
	        String url = Window.prompt("Enter a link URL:", "http://");
	        if (url != null) {
	          extended.createLink(url);
	        }
	      } else if (sender == removeLink) {
	        extended.removeLink();
	      } else if (sender == hr) {
	        extended.insertHorizontalRule();
	      } else if (sender == ol) {
	        extended.insertOrderedList();
	      } else if (sender == ul) {
	        extended.insertUnorderedList();
	      } else if (sender == removeFormat) {
	        extended.removeFormat();
	      } else if (sender == richText) {
	        // We use the RichTextArea's onKeyUp event to update the toolbar status.
	        // This will catch any cases where the user moves the cursur using the
	        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
	        updateStatus();
	      }
	      inUse = false;
	      richText.setFocus(true);
	    }

	    public void onKeyUp(KeyUpEvent event) {
	      Widget sender = (Widget) event.getSource();
	      if (sender == richText) {
	        // We use the RichTextArea's onKeyUp event to update the toolbar status.
	        // This will catch any cases where the user moves the cursur using the
	        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
	        updateStatus();
	      }
	    }

		@Override
		public void onMouseDown(MouseDownEvent event) {
			
			//Widget sender = (Widget) event.getSource();
			richText.setFocus(true);
		}

		@Override
		public void onMouseUp(MouseUpEvent event) {
			Widget sender = (Widget) event.getSource();
			if(sender instanceof ListBox){
				inUse = true;
			}
			else{
				inUse = false;
				//richText.setFocus(true);
			}
			
		}

		@Override
		public void onBlur(BlurEvent event) {
			Widget sender = (Widget) event.getSource();
			if(sender instanceof ListBox){
				lbFocus = false;
				inUse = false;
			}
			else{
				KSRichTextToolbarImpl.this.fireEvent(event);
			}
		}

		@Override
		public void onFocus(FocusEvent event) {
			Widget sender = (Widget) event.getSource();
			if(sender instanceof ListBox){
				lbFocus = true;
			}
			else{
				KSRichTextToolbarImpl.this.fireEvent(event);
			}
			
		}
	  }
	  
	  public boolean inUse(){
		  return inUse;
	  }

	  private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
	      RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL,
	      RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM,
	      RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
	      RichTextArea.FontSize.XX_LARGE};

	  //private KSImages images = (KSImages) GWT.create(KSImages.class);
	  private Strings strings = (Strings) GWT.create(KSRichTextToolbarAbstract.Strings.class);
	  private EventHandler handler = new EventHandler();

	  private RichTextArea richText;
	  private RichTextArea.BasicFormatter basic;
	  private RichTextArea.ExtendedFormatter extended;
	  
	  //private FocusPanel wrapper = new FocusPanel();
	  private VerticalFlowPanel outer = new VerticalFlowPanel();
	  private HorizontalBlockFlowPanel topPanel = new HorizontalBlockFlowPanel();
	  //private HorizontalBlockFlowPanel bottomPanel = new HorizontalBlockFlowPanel();
	  private ToggleButton bold;
	  private ToggleButton italic;
	  private ToggleButton underline;
	  private ToggleButton subscript;
	  private ToggleButton superscript;
	  private ToggleButton strikethrough;
	  private PushButton indent;
	  private PushButton outdent;
	  private PushButton justifyLeft;
	  private PushButton justifyCenter;
	  private PushButton justifyRight;
	  private PushButton hr;
	  private PushButton ol;
	  private PushButton ul;
	  private PushButton insertImage;
	  private PushButton createLink;
	  private PushButton removeLink;
	  private PushButton removeFormat;

	  private ListBox backColors;
	  private ListBox foreColors;
	  private ListBox fonts;
	  private ListBox fontSizes;
	  
	  private boolean inUse = false;
	  private boolean lbFocus = false;
	  
	/**
	 * This constructs a KSRichTextToolbarImpl
	 * 
	 */
	public KSRichTextToolbarImpl() {
	      
	}

	  /**
	   * init a new toolbar that drives the given rich text area.
	   * 
	   * @param richText the rich text area to be controlled
	   */
	  protected void init(RichTextArea richText) {
	    this.richText = richText;
	    this.basic = richText.getBasicFormatter();
	    this.extended = richText.getExtendedFormatter();
	    initWidget(outer);
	    outer.add(topPanel);
	    //outer.add(bottomPanel);
	    
	    Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
	    	    int type = event.getTypeInt();
	    	    if (Element.is(event.getNativeEvent().getEventTarget())) {
	    	     com.google.gwt.dom.client.Element e = Element.as(event.getNativeEvent().getEventTarget());
	    	     
	    	     if (outer.getElement().isOrHasChild(e)) {
	    	         switch(type)
	    	         {
	    	        	 case Event.ONMOUSEDOWN:
	    	        		 inUse = true;
	    	        		 break;
	    	        	 case Event.ONMOUSEUP:
	    	        		if(lbFocus){
	    	 					inUse = true;
	    	 				}
	    	 				else{
	    	 					inUse = false;
	    	 					KSRichTextToolbarImpl.this.richText.setFocus(true);
	    	 				}
	    	        		break;
	    	        	 default:
	    	        		break;
	    	         }
	    	     }
	    	    
	    	    }
			}
	    });
	    //wrapper.add(outer);
	    
	    //wrapper.addMouseDownHandler(handler);
	    //wrapper.addMouseUpHandler(handler);

	    setStyleName("gwt-RichTextToolbar");
	    richText.addStyleName("hasRichTextToolbar");

	    if (basic != null) {
	      topPanel.add(bold = createToggleButton(Theme.INSTANCE.getRichTextEditorImages().bold(), strings.bold()));          
	      topPanel.add(italic = createToggleButton(Theme.INSTANCE.getRichTextEditorImages().italic(),
	          strings.italic()));
	      topPanel.add(underline = createToggleButton(Theme.INSTANCE.getRichTextEditorImages().underline(),
	          strings.underline()));
	      topPanel.add(subscript = createToggleButton(Theme.INSTANCE.getRichTextEditorImages().subscript(),
	          strings.subscript()));
	      topPanel.add(superscript = createToggleButton(Theme.INSTANCE.getRichTextEditorImages().superscript(),
	          strings.superscript()));
	      topPanel.add(justifyLeft = createPushButton(Theme.INSTANCE.getRichTextEditorImages().justifyLeft(),
	          strings.justifyLeft()));
	      //justifyLeft.getElement().getStyle().setProperty("display", "block");
	      topPanel.add(justifyCenter = createPushButton(Theme.INSTANCE.getRichTextEditorImages().justifyCenter(),
	          strings.justifyCenter()));
	      topPanel.add(justifyRight = createPushButton(Theme.INSTANCE.getRichTextEditorImages().justifyRight(),
	         strings.justifyRight()));
	    }

	    if (extended != null) {
	      topPanel.add(strikethrough = createToggleButton(Theme.INSTANCE.getRichTextEditorImages().strikeThrough(),
	          strings.strikeThrough()));
	      topPanel.add(indent = createPushButton(Theme.INSTANCE.getRichTextEditorImages().indent(), strings.indent()));
	      topPanel.add(outdent = createPushButton(Theme.INSTANCE.getRichTextEditorImages().outdent(),
	          strings.outdent()));
	      topPanel.add(hr = createPushButton(Theme.INSTANCE.getRichTextEditorImages().hr(), strings.hr()));
	      topPanel.add(ol = createPushButton(Theme.INSTANCE.getRichTextEditorImages().ol(), strings.ol()));
	      topPanel.add(ul = createPushButton(Theme.INSTANCE.getRichTextEditorImages().ul(), strings.ul()));
	      topPanel.add(insertImage = createPushButton(Theme.INSTANCE.getRichTextEditorImages().insertImage(),
	          strings.insertImage()));
	      topPanel.add(createLink = createPushButton(Theme.INSTANCE.getRichTextEditorImages().createLink(),
	          strings.createLink()));
	      topPanel.add(removeLink = createPushButton(Theme.INSTANCE.getRichTextEditorImages().removeLink(),
	          strings.removeLink()));
	      topPanel.add(removeFormat = createPushButton(Theme.INSTANCE.getRichTextEditorImages().removeFormat(),
	          strings.removeFormat()));
	    }
	    
	    for(int i = 0; i < topPanel.getWidgetCount(); i++){
	        
	        //topPanel.setCellWidth(topPanel.getWidget(i), 10 + "px");
	    }

	    if (basic != null) {
	        topPanel.add(backColors = createColorList("Background"));
	        topPanel.add(foreColors = createColorList("Foreground"));
	        topPanel.add(fonts = createFontList());
	        topPanel.add(fontSizes = createFontSizes());

	      // We only use these handlers for updating status, so don't hook them up
	      // unless at least basic editing is supported.
	      richText.addKeyUpHandler(handler);
	      richText.addClickHandler(handler);
	    }
	  }

	  private ListBox createColorList(String caption) {
	    ListBox lb = new ListBox();
	    lb.addChangeHandler(handler);
	    lb.addFocusHandler(handler);
	    lb.addBlurHandler(handler);
	    lb.setVisibleItemCount(1);

	    lb.addItem(caption);
	    lb.addItem(strings.white(), "white");
	    lb.addItem(strings.black(), "black");
	    lb.addItem(strings.red(), "red");
	    lb.addItem(strings.green(), "green");
	    lb.addItem(strings.yellow(), "yellow");
	    lb.addItem(strings.blue(), "blue");
	    lb.setWidth("125px");
	    return lb;
	  }

	  private ListBox createFontList() {
	    ListBox lb = new ListBox();
	    lb.addChangeHandler(handler);
	    lb.addFocusHandler(handler);
	    lb.addBlurHandler(handler);
	    lb.setVisibleItemCount(1);

	    lb.addItem(strings.font(), "");
	    lb.addItem(strings.normal(), "");
	    lb.addItem("Times New Roman", "Times New Roman");
	    lb.addItem("Arial", "Arial");
	    lb.addItem("Courier New", "Courier New");
	    lb.addItem("Georgia", "Georgia");
	    lb.addItem("Trebuchet", "Trebuchet");
	    lb.addItem("Verdana", "Verdana");
	    lb.setWidth("125px");
	    return lb;
	  }

	  private ListBox createFontSizes() {
	    ListBox lb = new ListBox();
	    lb.addChangeHandler(handler);
	    lb.addFocusHandler(handler);
	    lb.addBlurHandler(handler);
	    lb.setVisibleItemCount(1);

	    lb.addItem(strings.size());
	    lb.addItem(strings.xxsmall());
	    lb.addItem(strings.xsmall());
	    lb.addItem(strings.small());
	    lb.addItem(strings.medium());
	    lb.addItem(strings.large());
	    lb.addItem(strings.xlarge());
	    lb.addItem(strings.xxlarge());
	    lb.setWidth("100px");
	    return lb;
	  }

	  private PushButton createPushButton(KSImage img, String tip) {
	    PushButton pb = new PushButton(img.getImage());
	    pb.addClickHandler(handler);
	    pb.addMouseDownHandler(handler);
	    pb.addMouseUpHandler(handler);
	    pb.setTitle(tip);
	    
	    return pb;
	  }

	  private ToggleButton createToggleButton(KSImage img, String tip) {
	    ToggleButton tb = new ToggleButton(img.getImage());
	    tb.addClickHandler(handler);
	    tb.addMouseDownHandler(handler);
	    tb.addMouseUpHandler(handler);
	    tb.setTitle(tip);
	    tb.setPixelSize(20, 20);
	    
	    return tb;
	  }

	  /**
	   * Updates the status of all the stateful buttons.
	   */
	  private void updateStatus() {
	    if (basic != null) {
	      bold.setDown(basic.isBold());
	      italic.setDown(basic.isItalic());
	      underline.setDown(basic.isUnderlined());
	      subscript.setDown(basic.isSubscript());
	      superscript.setDown(basic.isSuperscript());
	    }

	    if (extended != null) {
	      strikethrough.setDown(extended.isStrikethrough());
	    }
	  }

	@Override
	protected void onDetach() {
		inUse = false;
		lbFocus = false;
		super.onDetach();
	}
	  
	  
}
