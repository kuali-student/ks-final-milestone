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

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.BooleanValue;
import org.kuali.student.common.assembly.data.Data.DataValue;
import org.kuali.student.common.assembly.data.Data.DateValue;
import org.kuali.student.common.assembly.data.Data.DoubleValue;
import org.kuali.student.common.assembly.data.Data.FloatValue;
import org.kuali.student.common.assembly.data.Data.IntegerValue;
import org.kuali.student.common.assembly.data.Data.LongValue;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.assembly.data.Data.ShortValue;
import org.kuali.student.common.assembly.data.Data.StringValue;
import org.kuali.student.common.assembly.data.Data.TimeValue;
import org.kuali.student.common.assembly.data.Data.TimestampValue;
import org.kuali.student.common.assembly.data.Data.Value;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.MetadataInterrogator;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.TranslatableValueWidget;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;

import com.google.gwt.core.client.GWT;

/**
 * Model widget binding for HasDataValue widgets.  These are widgets which deal with KS data types
 * directly.
 * 
 * @author Kuali Student Team
 *
 */
public class HasDataValueBinding extends ModelWidgetBindingSupport<HasDataValue>{

	public static HasDataValueBinding INSTANCE = new HasDataValueBinding();
	
	private HasDataValueBinding(){}
	
	@Override
	public void setModelValue(HasDataValue widget, DataModel model, String path) {
		QueryPath qPath = QueryPath.parse(path);
        Value value = widget.getValue();
        if (!nullsafeEquals(model.get(qPath), value)) {
            setDirtyFlag(model, qPath);
        }
        if(value != null){
        	if (widget instanceof KSSelectedList ){
        		//This gets a value with _runtimeData translations for all selected items,
        		//otherwise translations get lost and KSSelectedList would have to lookup values via a search call.
        		model.set(qPath, ((KSSelectedList)widget).getValueWithTranslations());
        	} else {
        		model.set(qPath, value);
        	}
        }
	}

	@Override
	public void setWidgetValue(HasDataValue widget, DataModel model, String path) {
			
		QueryPath qPath = QueryPath.parse(path);
		Object value = null;
		if(model!=null){
        	value = model.get(qPath);
        }
        
        
        if (value != null && widget != null) {
           
            if (value instanceof Data) {
                DataValue dv = new DataValue((Data) value);
                if (widget instanceof TranslatableValueWidget) {
                    Metadata fieldMetadata = model.getMetadata(qPath);
                    if (MetadataInterrogator.isRepeating(fieldMetadata)) {
                        Map<String, String> translations = new HashMap<String, String>();
                        Iterator<Property> iter = ((Data) value).iterator();
                        while (iter.hasNext()) {
                            Property p = iter.next();
                            if(!"_runtimeData".equals(p.getKey())){
	                            QueryPath translationPath = new QueryPath();
	                            translationPath.add(new Data.StringKey(qPath.toString()));
	                            translationPath.add(new Data.StringKey("_runtimeData"));
	                            translationPath.add(new Data.IntegerKey((Integer)p.getKey()));
	                            translationPath.add(new Data.StringKey("id-translation"));
	                            String translation = model.get(translationPath.toString());
	                            String id = p.getValue().toString();
	                            translations.put(id, translation);
                            }
                        }
                        translations = MapUtil.sortByValue(translations);
                        ((TranslatableValueWidget)widget).setValue(translations);
                    }
                } else {
                    widget.setValue(dv);
                }
            } else if (value instanceof String) {
            	if(widget instanceof TranslatableValueWidget) {
            		QueryPath translationPath = qPath.subPath(0, qPath.size()-1);
        	        translationPath.add(new Data.StringKey("_runtimeData"));
        	        translationPath.add(new Data.StringKey((String)qPath.get(qPath.size() - 1).get()));
        	        translationPath.add(new Data.StringKey("id-translation"));
        	        
        	        String translation = model.get(translationPath.toString());
        	        if(translation != null && !translation.isEmpty()) {
        	            ((TranslatableValueWidget)widget).setValue((String)value, translation);
        	        } else {
        	            widget.setValue(new StringValue((String)value));
        	        }
            	} else {
            		widget.setValue(new StringValue((String)value));
            	}
            }
            else if(value instanceof Boolean){
            	widget.setValue(new BooleanValue((Boolean)value));
            }
            else if(value instanceof Integer){
            	widget.setValue(new IntegerValue((Integer)value));
            }
            else if(value instanceof Double){
            	widget.setValue(new DoubleValue((Double)value));
            }
            else if(value instanceof Float){
            	widget.setValue(new FloatValue((Float)value));
            }
            else if(value instanceof Date){
            	widget.setValue(new DateValue((Date)value));
            }
            else if(value instanceof Long){
            	widget.setValue(new LongValue((Long)value));
            }
            else if(value instanceof Short){
            	widget.setValue(new ShortValue((Short)value));
            }
            else if(value instanceof Time){
            	widget.setValue(new TimeValue((Time)value));
            }
            else if(value instanceof Timestamp){
            	widget.setValue(new TimestampValue((Timestamp)value));
            }
            else{
            	widget.setValue(null);
            	GWT.log("Warning: a valid Data.Value datatype was not provided in HasDataValueBinding setWidget", null);
            }

        } else if (widget != null) {
            try {
                widget.setValue(null);
            } catch (RuntimeException e) {
                GWT.log("Warning: Ignoring error attempting to setValue for " + widget.getClass().getName(), e);
            }
        }
	}

    /**
     * 
     * This class is used to sort the values of a map.
     * 
     */
    private static class MapUtil {
        public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
            List<Map.Entry<K, V>> list =
                    new LinkedList<Map.Entry<K, V>>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });

            Map<K, V> result = new LinkedHashMap<K, V>();
            for (Map.Entry<K, V> entry : list) {
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }
    }
}
