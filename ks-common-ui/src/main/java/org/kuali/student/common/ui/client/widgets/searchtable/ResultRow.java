package org.kuali.student.common.ui.client.widgets.searchtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dto.Idable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResultRow implements IsSerializable, Idable{
    private String id;
    private Map<String, String> columnValues = new HashMap<String, String>();
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id; 
    }

    public String getValue(String columnKey) {
        return columnValues.get(columnKey);
    }

    public void setValue(String columnKey, String value) {
        columnValues.put(columnKey, value);
        
    }

}
