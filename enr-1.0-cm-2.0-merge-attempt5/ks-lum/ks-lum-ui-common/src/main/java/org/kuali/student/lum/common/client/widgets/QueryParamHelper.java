package org.kuali.student.lum.common.client.widgets;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;

public class QueryParamHelper {

    public enum Properties implements PropertyEnum
    {
        VALUE ("value"),
        LIST_VALUE ("listValue"),
        KEY ("key");

        private final String key;

        private Properties (final String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }
    
    private Data data;
    
    public QueryParamHelper(Data data) {
        this.data = data;
    }
    
    public static QueryParamHelper wrap(Data data) {
        if (data == null) {
            return null;
        }
        return new QueryParamHelper(data);
    }
    
    public void setValue(String value) {
        data.set(Properties.VALUE.getKey(), value);
    }
    
    public String getValue() {
        return (String) data.get(Properties.VALUE.getKey());
    }
    
    public void setListValue(Data value) {
        data.set(Properties.LIST_VALUE.getKey(), value);
    }
    
    public Data getListValue() {
        Data listValue = data.get(Properties.LIST_VALUE.getKey());
        if (listValue == null) {
            listValue = new Data();
            setListValue(listValue);
        }
        return listValue;
    }
    
    public void setKey(String value) {
        data.set(Properties.KEY.getKey(), value);
    }
    
    public String getKey() {
        return (String) data.get(Properties.KEY.getKey());
    }
    
    public Data getData() {
        return data;
    }
    
}
