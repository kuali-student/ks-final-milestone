/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

/**
 * KSTextArea wraps gwt TextArea. This class provides most of the same functionality, but sets KS css styles for its default
 * look and a variety of TextArea events (for improved browser compatibility and customizability).
 * 
 * @author Kuali Student Team
 */
public class KSTextArea extends TextArea implements HasWatermark {
    private boolean hasWatermark = false;
    private boolean watermarkShowing = false;
    private String watermarkText;
    private int left = 0;
    private int maxLength = 0;

    /**
     * Creates a new empty text area.
     */
    public KSTextArea() {
        super();
        setupDefaultStyle();
    }

    /**
     * Creates a new text area using the text area element specified.
     * 
     * @param element
     *            a <TextArea> element
     */
    public KSTextArea(Element element) {
        super(element);
        setupDefaultStyle();
    }

    public KSTextArea(final Label countLabel, final int maximumChar) {
        super();
        setupDefaultStyle();
        this.setMaxLength(maximumChar);
        setLabel(countLabel);
        this.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                setLabel(countLabel);
            }
        });

        this.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                setLabel(countLabel);
            }
        });

    }
    
    public KSTextArea(final Label countLabel) {
        super();
        setupDefaultStyle();
        countLabel.setText(this.maxLength + " characters left");
        this.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                setLabel(countLabel);
            }
        });

        this.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                setLabel(countLabel);
            }
        });

    }

    /**
     * This method sets the default style for the text area and text area events.
     */
    private void setupDefaultStyle() {
        addStyleName("KS-Textarea");

        this.addBlurHandler(new BlurHandler() {
            public void onBlur(BlurEvent event) {
                KSTextArea.this.removeStyleName("KS-Textarea-Focus");

            }
        });

        this.addFocusHandler(new FocusHandler() {
            public void onFocus(FocusEvent event) {
                KSTextArea.this.addStyleName("KS-Textarea-Focus");

            }
        });

        this.addMouseOverHandler(new MouseOverHandler() {
            public void onMouseOver(MouseOverEvent event) {
                KSTextArea.this.addStyleName("KS-Textarea-Hover");

            }
        });

        this.addMouseOutHandler(new MouseOutHandler() {

            public void onMouseOut(MouseOutEvent event) {
                KSTextArea.this.removeStyleName("KS-Textarea-Hover");

            }

        });

    }

    @Override
    public void setWatermarkText(String text) {
        if (!hasWatermark) {
            hasWatermark = true;
            watermarkText = text;
            if (getText() == null || getText().isEmpty()) {
                addStyleName("watermark-text");
                KSTextArea.super.setText(watermarkText);
                watermarkShowing = true;
            }

            this.addFocusHandler(new FocusHandler() {

                @Override
                public void onFocus(FocusEvent event) {
                    if (watermarkShowing) {
                        removeStyleName("watermark-text");
                        KSTextArea.super.setText("");
                        watermarkShowing = false;
                    }
                }
            });

            this.addBlurHandler(new BlurHandler() {

                @Override
                public void onBlur(BlurEvent event) {
                    if (getText() == null || getText().isEmpty()) {
                        addStyleName("watermark-text");
                        KSTextArea.super.setText(watermarkText);
                        watermarkShowing = true;
                    }
                }
            });
        } else {
            watermarkText = text;
            if (getText() == null || getText().isEmpty()) {
                addStyleName("watermark-text");
                KSTextArea.super.setText(watermarkText);
                watermarkShowing = true;
            }
        }
    }

    @Override
    public boolean hasWatermark() {
        return hasWatermark;
    }

    @Override
    public boolean watermarkShowing() {
        return watermarkShowing;
    }

    @Override
    public String getText() {
        if (!watermarkShowing) {
            return super.getText();
        }
        return null;
    }

    @Override
    public String getValue() {
        if (!watermarkShowing) {
            return super.getValue();
        }
        return null;
    }

    @Override
    public void setValue(String value) {
        if (hasWatermark) {
            if (value == null || (value != null && value.isEmpty())) {
                super.setValue(watermarkText);
                addStyleName("watermark-text");
                watermarkShowing = true;
            } else {
                super.setValue(value);
                removeStyleName("watermark-text");
                watermarkShowing = false;
            }
        } else {
            super.setValue(value);
        }
    }

    @Override
    public void setText(String text) {
        String oldValue = super.getText();
        if (hasWatermark) {
            if (text == null || (text != null && text.isEmpty())) {
                super.setText(watermarkText);
                addStyleName("watermark-text");
                watermarkShowing = true;
            } else {
                super.setText(text);
                removeStyleName("watermark-text");
                watermarkShowing = false;
            }
        } else {
            super.setText(text);
        }
        ValueChangeEvent.fireIfNotEqual(this, oldValue, text);
    }

    public void setLabel(final Label countLabel) {
        left = this.maxLength - KSTextArea.this.getText().length();
        if (KSTextArea.this.getText().length() > this.maxLength) {
            countLabel.setText("Please remove " + left * -1 + " characters");
        } else {
            countLabel.setText(left + " characters left");
        }
        if ((left <= (this.maxLength * 0.1)) || (left <= 10)) {
            countLabel.getElement().setAttribute("style", "color: red;");
        } else {
            countLabel.getElement().removeAttribute("style");
        }
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }
    
}
