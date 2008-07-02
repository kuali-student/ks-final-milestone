package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusListenerAdapter;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Kuali Student Team
 *         </p>
 *         This class is used when you want to have a widget that
 */
public class EditLabel extends Composite {

    boolean isEditable = false;
    boolean bClickEdit = true;

    FocusPanel root = new FocusPanel();
    final TextBox tBox = new TextBox();
    final Label lbl = new Label();

    final String text = "";

    ClickListener clickEdit = new ClickListener() {
        public void onClick(Widget sender) {
            if (bClickEdit) {
                changeToTextBox();
            }
        }
    };

    FocusListener focusLstnr = new FocusListenerAdapter() {
        @Override
        public void onLostFocus(Widget sender) {
            if (bClickEdit) {
                changeToLabel();
            }
        }
    };

    public EditLabel() {
        isEditable = false;
        bClickEdit = true;
        setup();
    }

    public EditLabel(boolean isClickEditable) {
        isEditable = false;
        bClickEdit = isClickEditable;
        setup();
    }

    public void clear() {
        tBox.setText("");
        lbl.setText("");
    }

    protected void changeToTextBox() {
        if (!isEditable) {
            isEditable = true;
            tBox.setText(lbl.getText());
            tBox.setVisible(true);
            lbl.setVisible(false);
            tBox.setFocus(true);
            tBox.selectAll();
        }
    }

    protected void changeToLabel() {
        if (isEditable) {
            isEditable = false;
            lbl.setText(tBox.getText());
            tBox.setVisible(false);
            lbl.setVisible(true);
        }
    }

    /**
     * 
     */
    protected void setup() {

        FlowPanel fPanel = new FlowPanel();

        fPanel.add(tBox);
        fPanel.add(lbl);

        root.add(fPanel);
        root.addClickListener(clickEdit);
        root.addFocusListener(new FocusListenerAdapter() {
            @Override
            public void onFocus(Widget sender) {
                if (isEditable) {
                    tBox.setFocus(true);
                    tBox.selectAll();
                }
            }
        });
        root.addKeyboardListener(new KeyboardListenerAdapter() {
            @Override
            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                switch (keyCode) {
                    case KeyboardListener.KEY_ENTER: // return key
                        changeToTextBox();
                        break;
                    case KeyboardListener.KEY_ESCAPE: // escape
                        changeToLabel();
                        break;
                }
            }
        });
        tBox.addFocusListener(focusLstnr);

        lbl.setWidth("100%");
        lbl.setHeight("100%");

        tBox.setVisible(false);
        lbl.setVisible(true);

        initWidget(root);
    }

    public boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEdit) {
        if (isEdit) {
            changeToTextBox();
        } else {
            changeToLabel();
        }
    }

    public void setText(String text) {
        lbl.setText(text);
        tBox.setText(text);
    }

    public String getText() {
        return tBox.getText();
    }

    public void setLblStyle(String style) {
        lbl.addStyleName(style);
    }

    public void setTBoxStyle(String style) {
        tBox.addStyleName(style);
    }

    @Override
    public void addStyleName(String style) {
        root.addStyleName(style);
    }

    public boolean isClickEditable() {
        return bClickEdit;
    }

    public void setClickEditable(boolean clickEditable) {
        bClickEdit = clickEditable;
    }

}
