/**
 * 
 */
package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusListenerAdapter;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is a description of what this class does - gtaylor don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class EditLabel extends Composite {

    
    boolean         isEditable          = false;
    boolean         bClickEdit          = true;
    
    FocusPanel      root        = new FocusPanel();
    Widget          editWidget  = null;
    Label       lbl         = new Label();
    
    final   String  text        = "";
    
    
    ClickListener           clickEdit = 
        new ClickListener(){
        public void onClick(Widget sender){
            if(bClickEdit){
                changeToEditMode();
            }
        }
    };
    
    FocusListener           focusLstnr = 
        new FocusListenerAdapter(){
        public void onLostFocus(Widget sender){
            if(bClickEdit){
                changeToLabel();
            }
        }
    };
    
    ChangeListener         changeLstnr = 
        new ChangeListener(){
            public void onChange(Widget sender) {
                lbl.setText(getText(editWidget));               
            }};
    
    
    public EditLabel(){
        this.isEditable = false;
        this.bClickEdit = true;
        setup(new TextBox());
    }
    public EditLabel( Widget w){
        this.isEditable = false;
        this.bClickEdit = true;
        setup(w);
    }
    
    public EditLabel( Widget w, boolean isClickEditable){
        this.isEditable = false;
        this.bClickEdit = isClickEditable;
        setup(w);
    }
    
    public EditLabel(boolean isClickEditable){
        this.isEditable = false;
        this.bClickEdit = isClickEditable;
        setup(new TextBox());
    }
    
    public void clear(){
        if(editWidget instanceof HasText){
            ((HasText)editWidget).setText("");              
        }
        lbl.setText("");
    }
    
    protected void changeToEditMode(){
        if(!isEditable){
            isEditable = true;
            
            setText(editWidget, lbl.getText());         
            editWidget.setVisible(true);
            focusAll(editWidget);           
            lbl.setVisible(false);                      
        }
    }
    protected void changeToLabel(){
        if(isEditable){
            isEditable = false;
            
            lbl.setText(getText(editWidget));                       
            editWidget.setVisible(false);           
            lbl.setVisible(true);
        }
    }
    
    /**
     * This method will get the widget text if possible. 
     * 
     * @param w Some widget passed in. must implement HasText to return.
     * @return will return the text or an empty string
     */
    protected String getText(final Widget w){
        String sRet = "";
        if(w instanceof HasText){
            sRet = ((HasText)w).getText();                                      
        }
        return sRet;
    }
    
    protected void setText(Widget w, String in){        
        if(w instanceof HasText){
           ((HasText)w).setText(in);                                      
        }        
    }
    
    /**
     * This method sets the focus to the widget passed in and selects
     * all text if possible
     * 
     * @param w
     */
    protected void focusAll(Widget w){
        if(w instanceof FocusWidget){
            ((FocusWidget)w).setFocus(true);            
        }
        if(w instanceof TextBoxBase){            
            ((TextBoxBase)w).selectAll();
        }
    }
    
    /**
     * 
     */
    protected void setup(Widget editWidgetIn) {
        this.editWidget = editWidgetIn;
        
        FlowPanel fPanel = new FlowPanel();
        
        fPanel.add(this.editWidget);
        fPanel.add(lbl);
        
        root.add(fPanel);
        root.addClickListener(clickEdit);
        root.addFocusListener(new FocusListenerAdapter(){
            public void onFocus(Widget sender){
                if(isEditable){
                    focusAll(editWidget);
                }
            }
        });
        root.addKeyboardListener(new KeyboardListenerAdapter(){
            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                switch (keyCode) {
                case KeyboardListenerAdapter.KEY_ENTER: // return key
                    changeToEditMode();
                    break;
                case KeyboardListenerAdapter.KEY_ESCAPE: // escape
                    changeToLabel();
                    break;
                }
            }});
        addFocusListener(editWidget, focusLstnr);
        addChangeListener(editWidget, changeLstnr);
                        
        lbl.setWidth("100%");
        lbl.setHeight("100%");
        
        editWidget.setVisible(false);
        lbl.setVisible(true);
        
        
        
        initWidget(root);
    }
    
    
    public void addChangeListener(Widget w, ChangeListener listener){
        if(w instanceof SourcesChangeEvents){
            ((SourcesChangeEvents)w).addChangeListener(listener);
        }
    }
    
    public void addFocusListener(Widget w, FocusListener listener){
        if(w instanceof FocusWidget){
            ((FocusWidget)w).addFocusListener(listener);            
        }
    }
    
    public boolean getIsEditable(){
        return this.isEditable;
    }
    
    public void setIsEditable(boolean isEdit){
        if(isEdit)
            this.changeToEditMode();
        else
            this.changeToLabel();
    }
    
    public void setText(String text){
        lbl.setText(text);
        setText(editWidget,text);
    }
    
    public String getText(){
        return getText(editWidget);
    }
    
    public void setLblStyle(String style) {
        lbl.addStyleName(style);
    }
    public void addEditWidgetStyle(String style) {
        editWidget.addStyleName(style);     
    }
    
    public void addStyleName(String style){
        root.addStyleName(style);
    }
    
    public boolean isClickEditable(){
        return this.bClickEdit;
    }
    public void setClickEditable(boolean clickEditable){
        this.bClickEdit = clickEditable;
    }

    /**
     * This method will update the label with the value in the editWidget.
     * 
     */
    public void updateText(){
        lbl.setText(getText(editWidget));   
    }

    
    
}
