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

package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.util.Date;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.validator.ClientDateParser;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasText;

public class HasTextBinding extends ModelWidgetBindingSupport<HasText> {
    public static HasTextBinding INSTANCE = new HasTextBinding();

    private ClientDateParser dateParser = new ClientDateParser();

    private HasTextBinding() {}

    @Override
    public void setModelValue(HasText object, DataModel model, String path) {
        try {
            QueryPath qPath = QueryPath.parse(path);
            DataType type = model.getType(qPath);
            String newValue = object.getText().trim();

            try {
                switch (type) {
                    case STRING:
                        if (!nullsafeEquals(model.get(qPath), newValue)) {
                            model.set(qPath, newValue);
                            setDirtyFlag(model, qPath);
                        }
                        break;
                    case INTEGER:
                		if(newValue != null && newValue.isEmpty()){
                			Integer value = null;
                			model.set(qPath, value);
                			setDirtyFlag(model, qPath);
                		}
                		else{
                            int intValue = Integer.parseInt(newValue);
                            if (!nullsafeEquals(model.get(qPath), intValue)) {
                                model.set(qPath, intValue);
                                setDirtyFlag(model, qPath);
                            }
                		}
                        break;
                    case LONG:
                        long longValue = Long.parseLong(newValue);
                        if (!nullsafeEquals(model.get(qPath), longValue)) {
                            model.set(qPath, longValue);
                            setDirtyFlag(model, qPath);
                        }
                        break;
                    case FLOAT:
                        float floatValue = Float.parseFloat(newValue);
                        if (!nullsafeEquals(model.get(qPath), floatValue)) {
                            model.set(qPath, floatValue);
                            setDirtyFlag(model, qPath);
                        }
                        break;
                    case DOUBLE:
                        double doubleValue = Double.parseDouble(newValue);
                        if (!nullsafeEquals(model.get(qPath), doubleValue)) {
                            model.set(qPath, doubleValue);
                            setDirtyFlag(model, qPath);
                        }
                        break;
                    case BOOLEAN:
                        if (newValue.equalsIgnoreCase("true") || newValue.equalsIgnoreCase("false")) {
                            boolean booleanValue = Boolean.parseBoolean(newValue);
                            if (!nullsafeEquals(model.get(qPath), booleanValue)) {
                                model.set(qPath, booleanValue);
                                setDirtyFlag(model, qPath);
                            }
                        } else {
                            throw new UnsupportedOperationException("BooleanTypes can only be set with true or false");
                        }
                        break;
                    case DATE:
                        Date dateValue = dateParser.parseDate(newValue);
                        if (!nullsafeEquals(model.get(qPath), dateValue)) {
                            model.set(qPath, dateValue);
                            setDirtyFlag(model, qPath);
                        }
                        break;
                }
            } catch (Exception e) {
                GWT.log("Unable to coerce type for " + path + ", falling back to String", e);
                model.set(qPath, newValue);
            }
        } catch (Exception e) {
            GWT.log("Error setting model value for: " + path, e);
        }
    }

    @Override
    public void setWidgetValue(HasText object, DataModel model, String path) {
        try {
            QueryPath qPath = QueryPath.parse(path);
            
            Object value = null;
            if(model!=null){
            	value = model.get(qPath);
            }

            if (value != null && object != null) {
                if (value instanceof Date) {
                    object.setText(dateParser.toString((Date) value));
                } else {
                    object.setText(value.toString());
                }
            } else if (value == null && object != null) {
                object.setText("");
            }
        } catch (Exception e) {
            GWT.log("Error setting widget value for: " + path, e);
        }
    }

}
