package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;

public class CluSetHelper {

    private static final long serialVersionUID = 1;

    public enum Properties implements PropertyEnum
    {
        ID ("id"),
        ORGANIZATION ("organization"),
        TITLE ("title"),
        DESCRIPTION ("description"),
        EFFECTIVE_DATE ("effectiveDate"),
        EXPIRATION_DATE ("expirationDate"),
        CLUS ("clus");

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
    
    public CluSetHelper(Data data) {
        this.data = data;
    }
    
    public static CluSetHelper wrap(Data data) {
        if (data == null) {
            return null;
        }
        return new CluSetHelper(data);
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
    
    public void setOrganization(String value) {
        data.set(Properties.ORGANIZATION.getKey(), value);
    }
    
    public String getOrganization() {
        return (String) data.get(Properties.ORGANIZATION.getKey());
    }
    public void setTitle(String value) {
        data.set(Properties.TITLE.getKey(), value);
    }
    public String getTitle() {
        return (String) data.get(Properties.TITLE.getKey());
    }

    public void setDescription(String value) {
        data.set(Properties.DESCRIPTION.getKey(), value);
    }
    public String getDescription() {
        return (String) data.get(Properties.DESCRIPTION.getKey());
    }

    public void setEffectiveDate(String value) {
        data.set(Properties.EFFECTIVE_DATE.getKey(), value);
    }
    public String getEffectiveDate() {
        return (String) data.get(Properties.EFFECTIVE_DATE.getKey());
    }

    public void setExpirationDate(String value) {
        data.set(Properties.EXPIRATION_DATE.getKey(), value);
    }
    public String getExpirationDate() {
        return (String) data.get(Properties.EXPIRATION_DATE.getKey());
    }

    public void setClus(Data value) {
        data.set(Properties.CLUS.getKey(), value);
    }
    public Data getClus() {
        Data clusData = data.get(Properties.CLUS.getKey());
        if (clusData == null) {
            clusData = new Data();
            setClus(clusData);
        }
        return clusData;
    }

}