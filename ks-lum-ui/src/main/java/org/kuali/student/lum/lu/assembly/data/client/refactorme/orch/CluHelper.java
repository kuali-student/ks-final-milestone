package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;

public class CluHelper {

    private static final long serialVersionUID = 1;
    
    public enum Properties implements PropertyEnum {
        
        ID ("id"),
        NAME ("name"),
        _RUNTIME_DATA ("_runtimeData");

        private final String key;
        
        private Properties (final String key)
        {
            this.key = key;
        }
        
        @Override
        public String getKey ()
        {
            return this.key;
        }
    }
    
    private Data data;

    public CluHelper(Data data) {
        this.data = data;
    }
    
    public static CluHelper wrap(Data data) {
        if (data == null) {
            return null;
        }
        return new CluHelper(data);
    }
    
    public Data getData() {
        return this.data;
    }

    public void setId(String value) {
        data.set(Properties.ID.getKey(), value);
    }
    public String getId() {
        return (String) data.get(Properties.ID.getKey());
    }

    public void setName(String value) {
        data.set(Properties.NAME.getKey(), value);
    }
    public String getName() {
        return (String) data.get(Properties.NAME.getKey());
    }

    public void set_runtimeData(String value) {
        data.set(Properties._RUNTIME_DATA.getKey(), value);
    }
    public RuntimeDataHelper get_runtimeData() {
        return RuntimeDataHelper.wrap((Data) data.get(Properties._RUNTIME_DATA.getKey()));
    }

}
