package org.kuali.student.common.ui.client.widgets;



import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.RichTextArea.BasicFormatter;
import com.google.gwt.user.client.ui.RichTextArea.ExtendedFormatter;

/**
 * KS default rich text editor
 * 
 * TODO implement with a clean toolbar and i18n
 */
public class KSRichEditor extends Composite {
	private final Panel content = new VerticalPanel();
	private final Panel toolbar = new HorizontalPanel();
	private final RichTextArea textArea = new RichTextArea();
	
	private boolean focused = false;
	
	private boolean isUsedInPopup = false;
	
	private final FocusListener focusListener = new FocusListener() {
		public void onFocus(Widget sender) {
			focused = true;
		}
		public void onLostFocus(Widget sender) {
			focused = false;
		}
	};
	
	private final Timer focusTimer = new Timer() {
		public void run() {
			toolbar.setVisible(focused);
		}
	};
	
	public KSRichEditor(){
		this(false);
	}
	
	public KSRichEditor(boolean isUsedInPopup) {
	    this.isUsedInPopup = isUsedInPopup;
		super.initWidget(content);
		content.add(toolbar);
		content.add(textArea);
				setupToolbar();
		if(isUsedInPopup){
	        toolbar.setVisible(true);
		    
		}else{
		    //popup = new AutoHidePopupPanel();
		textArea.addFocusListener(focusListener);
		toolbar.setVisible(false);
/*		popup.addWindowCloseListener(new WindowCloseListener() {
			public void onWindowClosed() {
                textArea.setHTML(popup.getHTML());
			}
			public String onWindowClosing() {
				return null;
			}
		});*/
		}
	}
	
	protected void onLoad() {
		super.onLoad();
		if(!isUsedInPopup){
	        focusTimer.scheduleRepeating(250);
		    
		}
	}
	
	public RichTextArea getRichTextArea(){
	    return textArea;
	}
	protected void onUnload() {
		super.onUnload();
        if(!isUsedInPopup){

		focusTimer.cancel();
        }
	}

	private void setupToolbar() {
		// Get formatters - will be null if not available
		final ExtendedFormatter ef = textArea.getExtendedFormatter();
		final BasicFormatter bf = textArea.getBasicFormatter();
        if (bf != null && !isUsedInPopup) {
            addToolbarWidget(new PushButton(new Image("images/popup2.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    //popup.setHTML(textArea.getHTML());
                    //popup.showPopup();
                }
            }), "Enlarge to edit");
        }

		
		// A button to toggle 'bold' - BasicFormatters and above - note that the
		// button won't be shown if the browser can't do this so no checking is
		// required when the button is pressed
		if (bf != null) {
			addToolbarWidget(new PushButton(new Image("images/bold.gif"), new ClickListener() {
				public void onClick(Widget sender) {
					bf.toggleBold();
				}
			}), "Toggle bold text");
		}
		
        if (bf != null) {
            addToolbarWidget(new PushButton(new Image("images/italic.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    bf.toggleItalic();
                }
            }),"Toggle italic");
        }
        
        if (bf != null) {
            addToolbarWidget(new PushButton(new Image("images/underline.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    bf.toggleUnderline();
                }
            }), "Underline text");
        }
        
		// A button to add horizontal rule - ExtendedFormatters only - note that the
		// button won't be shown if the browser can't do this so no checking is
		// required when the button is pressed
		if (ef != null) {
			addToolbarWidget(new PushButton(new Image("images/hr.gif"), new ClickListener() {
				public void onClick(Widget sender) {
					ef.insertHorizontalRule();
				}
			}), "Draw horizontal rule");
		}

        // A button to format as an ordered list 
        if (ef != null) {
            addToolbarWidget(new PushButton(new Image("images/orderedlist.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    ef.insertOrderedList();
                }
            }), "Format as ordered list");
        }
        
        // A button to format as an ordered list 
        if (ef != null) {
            addToolbarWidget(new PushButton(new Image("images/unorderedlist.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    ef.insertUnorderedList();
                }
            }), "Format as unordered list");
        }
        
        if (ef != null) {
            addToolbarWidget(new PushButton(new Image("images/leftalign.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    ef.leftIndent();
                }
            }), "Left align text");
        }
	          

        if (ef != null) {
            addToolbarWidget(new PushButton(new Image("images/rightalign.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    ef.rightIndent();
                }
            }), "Right align text");
        }


        
        if (ef != null) {
            addToolbarWidget(new PushButton(new Image("images/strikethrough.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    ef.toggleStrikethrough();
                }
            }), "Strikethrough text");
        }

        if (ef != null) {
            addToolbarWidget(new PushButton(new Image("images/subscript.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    ef.toggleSubscript();
                }
            }), "Subscript text");
        }

        if (ef != null) {
            addToolbarWidget(new PushButton(new Image("images/superscript.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    ef.toggleSuperscript();
                }
            }), "Superscript text");
        }
        

	}


	private void addToolbarWidget(FocusWidget widget,
	                              String titleText) {
		toolbar.add(widget);
		widget.addFocusListener(focusListener);
		widget.setTitle(titleText);
	}
	
	
	
	
	// delegate methods to RichTextArea
	public String getHTML() {
		return textArea.getHTML();
	}

	public String getText() {
		return textArea.getText();
	}

	public void setHTML(String html) {
		textArea.setHTML(html);
	}

	public void setText(String text) {
		textArea.setText(text);
	}
}
