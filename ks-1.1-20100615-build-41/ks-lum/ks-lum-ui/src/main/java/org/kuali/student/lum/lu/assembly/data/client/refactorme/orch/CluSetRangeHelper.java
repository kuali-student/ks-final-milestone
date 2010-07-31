package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;

public class CluSetRangeHelper {

    public enum Properties implements PropertyEnum
    {
        ID ("id"),
        SEARCH_TYPE_KEY ("searchTypeKey"),
        QUERY_PARAMS ("queryParams");

        
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
    
    public CluSetRangeHelper(Data data) {
        this.data = data;
    }
    
    public static CluSetRangeHelper wrap(Data data) {
        if (data == null) {
            return null;
        }
        return new CluSetRangeHelper(data);
    }

    public void setId(String value) {
        data.set(Properties.ID.getKey(), value);
    }
    
    public String getId() {
        return data.get(Properties.ID.getKey());
    }

    public void setSearchTypeKey(String value) {
        data.set(Properties.SEARCH_TYPE_KEY.getKey(), value);
    }
    
    public String getSearchTypeKey() {
        return data.get(Properties.SEARCH_TYPE_KEY.getKey());
    }
    
    public void setQueryParams(Data value) {
        data.set(Properties.QUERY_PARAMS.getKey(), value);
    }
    public Data getQueryParams() {
        Data queryParams = data.get(Properties.QUERY_PARAMS.getKey());
        if (queryParams == null) {
            queryParams = new Data();
            setQueryParams(queryParams);
        }
        return queryParams;
    }
    
    public Data getData() {
        return data;
    }
}
