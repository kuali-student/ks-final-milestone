package org.kuali.student.commons.ui.viewmetadata.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.validators.client.ClientValidator;
import org.kuali.student.commons.ui.validators.client.Validator;

/**
 * Bean containing "field" metadata.
 */
public class FieldMetaData implements Serializable {

    private static final long serialVersionUID = 2530340875044427566L;
    private String fieldName = null;
    private Map<String, String> attributes = new HashMap<String, String>();
    private ViewMetaData parentView = null;

    /**
     * Returns the ViewMetaData object representing the view this field is associated with
     * 
     * @return the ViewMetaData object representing the view this field is associated with
     */
    public ViewMetaData getParentView() {
        return parentView;
    }

    /**
     * Sets the ViewMetaData object representing the view this field is associated with
     * 
     * @param parentView
     *            the ViewMetaData object representing the view this field is associated with
     */
    public void setParentView(ViewMetaData parentView) {
        this.parentView = parentView;
    }

    /**
     * Creates an empty FieldMetaData object
     */
    public FieldMetaData() {
        super();
    }

    /**
     * Creates a new FieldMetaData object with the specified field name and attributes
     * 
     * @param fieldName
     *            the name of the field
     * @param attributes
     *            the field attributes
     */
    public FieldMetaData(String fieldName, Map<String, String> attributes) {
        super();
        this.fieldName = fieldName;
        this.attributes = attributes;
    }

    /**
     * Returns the field name
     * 
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field name
     * 
     * @param fieldName
     *            the field name
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Returns the field's display label. Translates it against the internationalization messages.
     * 
     * @return the field's display label
     */
    public String getDisplayLabel() {
        return parentView.getMessages().get(fieldName);
    }

    /**
     * Returns the class name of the widget to be used when displaying the field.
     * 
     * @return the class name of the widget to be used when displaying the field
     */
    public String getWidgetClassName() {
        return attributes.get("widgetClassName");
    }

    /**
     * Returns the field's tab index, or 0 if not specified
     * 
     * @return the field's tab index, or 0 if not specified
     */
    public int getTabIndex() {
        int result = 0;
        String s = attributes.get("tabIndex");
        if ((s != null) && (s.trim().length() > 0)) {
            result = Integer.parseInt(s);
        }
        return result;
    }

    /**
     * Returns the validator script associated with the field
     * 
     * @return the validator script associated with the field
     */
    public String getValidatorScript() {
        return attributes.get("validatorScript");
    }

    /**
     * Returns an instance of Validator populated with the parent view's internationalization messages and the field's
     * attributes
     * 
     * @return new Validator instance
     */
    public Validator getValidatorInstance() {
        String script = getValidatorScript();
        Validator result = new ClientValidator(script, attributes);
        result.setMessages(parentView.getMessages());
        return result;
    }

    /**
     * Returns the FieldMetaData's underlying attribute map
     * 
     * @return the FieldMetaData's underlying attribute map
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Returns the CSS style name associated with the field
     * 
     * @return the CSS style name associated with the field
     */
    public String getStyleName() {
        return attributes.get("styleName");
    }

}
