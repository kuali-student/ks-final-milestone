package org.kuali.student.common.ui.client.mvc.history;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HistoryToken implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String key;
    Map<String, String> parameters = new HashMap<String, String>();
    
    public HistoryToken() {
        
    }
    
    public HistoryToken(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Map<String, String> getParameters() {
        return parameters;
    }
    
}
