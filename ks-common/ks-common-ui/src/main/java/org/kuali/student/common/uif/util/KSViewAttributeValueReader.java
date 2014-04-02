/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by delyea on 3/10/14
 */
package org.kuali.student.common.uif.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.datadictionary.exception.AttributeValidationException;
import org.kuali.rice.krad.datadictionary.validation.AttributeValueReader;
import org.kuali.rice.krad.datadictionary.validation.ViewAttributeValueReader;
import org.kuali.rice.krad.datadictionary.validation.capability.Constrainable;
import org.kuali.rice.krad.uif.UifPropertyPaths;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is an override of ViewAttributeValueReader class from Rice. This is needed because the default
 * Rice implementation only fetches InputField elements from the current page and we need all InputFields from
 * all pages.
 *
 * This will either need to be removed or altered after the upgrade to Rice 2.4.
 *
 * @author Kuali Student Team
 */
public class KSViewAttributeValueReader extends ViewAttributeValueReader {
    private ViewModel form;

    private List<Constrainable> inputFields = new ArrayList<Constrainable>();
    private Map<String, InputField> inputFieldMap = new HashMap<String, InputField>();

    /**
     * Constructor for ViewAttributeValueReader, the View must already be indexed and
     * the InputFields must have already be initialized for this reader to work properly
     *
     * @param form model object representing the View's form data
     */
    public KSViewAttributeValueReader(ViewModel form) {
        super(form);
        this.form = form;

        // this below is the customized line which fetches all the pages in this view and finds all InputField objects
        List<InputField> containerInputFields = ComponentUtils.getComponentsOfType(form.getView().getItems(), InputField.class);
        for (InputField field : containerInputFields) {
            if (StringUtils.isNotBlank(field.getName())) {
                inputFields.add(field);
                inputFieldMap.put(field.getName(), field);
            }
        }
    }

    /**
     * Gets the definition which is an InputField on the View/Page
     */
    @Override
    public Constrainable getDefinition(String attributeName) {
        InputField field = inputFieldMap.get(attributeName);
        if (field != null) {
            return field;
        } else {
            return null;
        }
    }

    /**
     * Gets all InputFields (which extend Constrainable)
     *
     * @return
     */
    @Override
    public List<Constrainable> getDefinitions() {
        return inputFields;
    }

    /**
     * Returns the label associated with the InputField which has that AttributeName
     *
     * @param attributeName
     * @return
     */
    @Override
    public String getLabel(String attributeName) {
        InputField field = inputFieldMap.get(attributeName);
        if (field != null) {
            return field.getLabel();
        } else {
            return null;
        }
    }

    /**
     * Not used for this reader, returns null
     *
     * @return null
     */
    @Override
    public Constrainable getEntry() {
        return null;
    }

    /**
     * Returns current attributeName which represents the path
     *
     * @return attributeName set on this reader
     */
    @Override
    public String getPath() {
        return this.attributeName;
    }

    /**
     * Gets the type of value for this AttributeName as represented on the Form
     *
     * @param attributeName
     * @return
     */
    @Override
    public Class<?> getType(String attributeName) {
        Object fieldValue = ObjectPropertyUtils.getPropertyValue(form, attributeName);
        return fieldValue.getClass();
    }

    /**
     * If the current attribute being evaluated is a field of an addLine return false because it should not
     * be evaluated during Validation.
     *
     * @return false if InputField is part of an addLine for a collection, true otherwise
     */
    @Override
    public boolean isReadable() {
        if (attributeName != null && attributeName.contains(UifPropertyPaths.NEW_COLLECTION_LINES)) {
            return false;
        }
        return true;
    }

    /**
     * Return value of the field for the attributeName currently set on this reader
     *
     * @param <X> return type
     * @return value of the field for the attributeName currently set on this reader
     * @throws org.kuali.rice.krad.datadictionary.exception.AttributeValidationException
     */
    @Override
    public <X> X getValue() throws AttributeValidationException {
        X fieldValue = null;
        if (StringUtils.isNotBlank(this.attributeName)) {
            fieldValue = ObjectPropertyUtils.<X>getPropertyValue(form, this.attributeName);
        }
        return fieldValue;
    }

    /**
     * Return value of the field for the attributeName passed in
     *
     * @param attributeName name (which represents a path) of the value to be retrieved on the Form
     * @param <X> return type
     * @return value of that attributeName represents on the form
     * @throws AttributeValidationException
     */
    @Override
    public <X> X getValue(String attributeName) throws AttributeValidationException {
        X fieldValue = null;
        if (StringUtils.isNotBlank(attributeName)) {
            fieldValue = ObjectPropertyUtils.<X>getPropertyValue(form, this.attributeName);
        }
        return fieldValue;
    }

    /**
     * Cones this AttributeValueReader
     *
     * @return AttributeValueReader
     */
    @Override
    public AttributeValueReader clone() {
        ViewAttributeValueReader clone = new ViewAttributeValueReader(form);
        clone.setAttributeName(this.attributeName);
        return clone;
    }

}
