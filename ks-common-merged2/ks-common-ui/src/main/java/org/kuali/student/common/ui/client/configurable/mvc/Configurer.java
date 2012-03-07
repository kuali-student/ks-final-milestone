package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.ModelDefinition;
import org.kuali.student.r1.common.assembly.data.QueryPath;

import com.google.gwt.user.client.ui.Widget;

/**
 * A configurer defines the configuration of screens in KS, in particular this is where you add views,
 * sections, and fields to a LayoutController preferably passed in in an configurer's implementation.
 * This abstract class contains helper methods to do this.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class Configurer {
    protected ModelDefinition modelDefinition;
    protected String type = "";
    //FIXME: WJG: I think state should be removed from the configurer
    protected String state = "";
    protected String nextState = "";
    protected String groupName = "";

    /**
     * Sets the modelDefinition which is the metadata backing the fields to be configured,
     * this needs to be set before adding any fields in the configurer
     * @param modelDefinition
     */
    public void setModelDefinition(ModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }

    public ModelDefinition getModelDefinition() {
        return modelDefinition;
    }

    /**
     * Generates a message info to be used in your field descriptor to get the label for the field.
     * Used by the field descriptor with the application context to determine the label to show based on
     * the labelKey
     * @param labelKey key of the message - must match a message in your messages (stored in the db)
     * @return
     */
    public MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo(groupName, type, state, labelKey);
    }
    
    /**
     * Gets the string corresponding to the label key passed in from the application messages
     * @param labelKey
     * @return
     */
    public String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }
    
    public String getLabel(String labelKey, String fieldKey) {
        String parentPath = Application.getApplicationContext().getParentPath();
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata metadata = modelDefinition.getMetadata(path);
        
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey, metadata);
    }

    /**
     * Gets a section title which is an h1 element using the label key passed to retrieve the corresponding
     * message
     * @param labelKey
     * @return
     */
    protected SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    }

    /**
     * Gets a section title which is an h1 element using the label key passed to retrieve the corresponding
     * message
     * @param labelKey
     * @return
     */
    protected SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    }

    /**
     * Gets a section title which is an h1 element using the label key passed to retrieve the corresponding
     * message
     * @param labelKey
     * @return
     */
    protected SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    }

    /**
     * Gets a section title which is an h1 element using the label key passed to retrieve the corresponding
     * message
     * @param labelKey
     * @return
     */
    protected SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    }

    /**
     * Gets a section title which is an h1 element using the label key passed to retrieve the corresponding
     * message
     * @param labelKey
     * @return
     */
    protected SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }

    /**
     * Adds a field with the field key specified to the passed in section.  
     * Returns the generated FieldDescriptor.  The field will have no label in this case.
     * The widget will be auto generated in field descriptor based on the field's metadata in the
     * model definition defined in this configurer - using the default widget factory.  The fieldKey passed
     * in MUST match a logical path in the metadata (ex. proposal/proposalTitle, etc.)
     * 
     * Note: It also is acceptable to not use this helper method and add the FieldDescriptor directly
     * to the section
     * 
     * @param section
     * @param fieldKey
     * @return
     */
    public FieldDescriptor addField(Section section, String fieldKey) {
        return addField(section, fieldKey, null, null, null);
    }
    
    /**
     * Adds a field with the field key specified to the passed in section.  
     * Returns the generated FieldDescriptor.  Uses the message key provided to generate the field label.
     * The widget will be auto generated in field descriptor based on the field's metadata in the
     * model definition defined in this configurer - using the default widget factory.  The fieldKey passed
     * in MUST match a logical path in the metadata (ex. proposal/proposalTitle, etc.)
     * 
     * Note: It also is acceptable to not use this helper method and add the FieldDescriptor directly
     * to the section
     * 
     * @param section
     * @param fieldKey
     * @param messageKey
     * @return
     */
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
        return addField(section, fieldKey, messageKey, null, null);
    }
    
    /**
     * Adds a field with the field key specified to the passed in section.  
     * Returns the generated FieldDescriptor.  Uses the message key provided to generate the field label.
     * The widget is not autogenerated based on the metadata in the defined model definition, but instead
     * the passed in widget is used.  The widget must be a type that is compatible with the default bindings
     * (such as being a widget which implements HasText or HasValue), unless a custom binding is set on the
     * returned FieldDescriptor from this method manually.
     * 
     * Note: It also is acceptable to not use this helper method and add the FieldDescriptor directly
     * to the section
     * 
     * @param section
     * @param fieldKey
     * @param messageKey
     * @param widget
     * @return
     */
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
        return addField(section, fieldKey, messageKey, widget, null);
    }
    
    /**
     * Adds a field with the field key specified to the passed in section.  
     * Returns the generated FieldDescriptor.  Uses the message key provided to generate the field label.
     * The widget will be auto generated in field descriptor based on the field's metadata in the
     * model definition defined in this configurer - using the default widget factory.  The fieldKey passed
     * in MUST match a logical path in the metadata (ex. proposal/proposalTitle, etc.).  The parentPath
     * will be concatenated onto the front of the fieldKey.
     * 
     * Note: It also is acceptable to not use this helper method and add the FieldDescriptor directly
     * to the section
     * 
     * @param section
     * @param fieldKey
     * @param messageKey
     * @param parentPath
     * @return
     */
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(section, fieldKey, messageKey, null, parentPath);
    }
    
    /**
     * Adds a field with the field key specified to the passed in section.  
     * Returns the generated FieldDescriptor.  Uses the message key provided to generate the field label.
     * The widget is not autogenerated based on the metadata in the defined model definition, but instead
     * the passed in widget is used.  The widget must be a type that is compatible with the default bindings
     * (such as being a widget which implements HasText or HasValue), unless a custom binding is set on the
     * returned FieldDescriptor from this method manually.  The parentPath
     * will be concatenated onto the front of the fieldKey.
     * 
     * Note: It also is acceptable to not use this helper method and add the FieldDescriptor directly
     * to the section
     * 
     * @param section
     * @param fieldKey
     * @param messageKey
     * @param widget
     * @param parentPath
     * @return
     */
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
        return fd;
    }
    
    /**
     * Read only variant of the corresponding addField method.  This method will generate the read only version
     * of the widget from the metadata found in the model definition that matches the fieldKey.
     * 
     * @param section
     * @param fieldKey
     * @return
     */
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey) {
        return addReadOnlyField(section, fieldKey, null, null, null);
    }    
    
    /**
     * Read only variant of the corresponding addField method.  This method will generate the read only version
     * of the widget from the metadata found in the model definition that matches the fieldKey.
     * 
     * @param section
     * @param fieldKey
     * @param messageKey
     * @return
     */
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey, MessageKeyInfo messageKey) {
        return addReadOnlyField(section, fieldKey, messageKey, null, null);
    }
    
    /**
     * Read only variant of the corresponding addField method.  Uses widget passed in.
     * 
     * @param section
     * @param fieldKey
     * @param messageKey
     * @param widget
     * @return
     */
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
        return addReadOnlyField(section, fieldKey, messageKey, widget, null);
    }
    
    /**
     * Read only variant of the corresponding addField method.  This method will generate the read only version
     * of the widget from the metadata found in the model definition that matches the fieldKey.
     * 
     * @param section
     * @param fieldKey
     * @param messageKey
     * @param parentPath
     * @return
     */
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addReadOnlyField(section, fieldKey, messageKey, null, parentPath);
    }
    
    /**
     * Read only variant of the corresponding addField method.  Uses widget passed in.
     * 
     * @param section
     * @param fieldKey
     * @param messageKey
     * @param widget
     * @param parentPath
     * @return
     */
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget != null) {                                               
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
        return fd;
    }

    /**
     * The initial state of the objects for the screen
     * 
     * @return
     */
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNextState() {
		return nextState;
	}

	public void setNextState(String nextState) {
		this.nextState = nextState;
	}       
}
