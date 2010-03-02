package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.StringValue;
import org.kuali.student.core.assembly.data.Data.ShortValue;
import org.kuali.student.core.assembly.data.Data.LongValue;
import org.kuali.student.core.assembly.data.Data.DateValue;
import org.kuali.student.core.assembly.data.Data.IntegerValue;
import org.kuali.student.core.assembly.data.Data.TimeValue;
import org.kuali.student.core.assembly.data.Data.TimestampValue;
import org.kuali.student.core.assembly.data.Data.BooleanValue;
import org.kuali.student.core.assembly.data.Data.FloatValue;
import org.kuali.student.core.assembly.data.Data.DoubleValue;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;

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
        	model.set(qPath, value);
        }
	}

	@Override
	public void setWidgetValue(HasDataValue widget, DataModel model, String path) {
		QueryPath qPath = QueryPath.parse(path);
        Object value = model.get(qPath);


        if (value != null && widget != null) {
           
            if(value instanceof Data){
            	widget.setValue(new DataValue((Data)value));
            }
            else if(value instanceof String){
            	widget.setValue(new StringValue((String)value));
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

}
