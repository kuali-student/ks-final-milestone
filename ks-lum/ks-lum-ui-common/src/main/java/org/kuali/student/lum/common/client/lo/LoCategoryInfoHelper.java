package org.kuali.student.lum.common.client.lo;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.core.dto.MetaInfo;

import java.util.Date;

/**
 * @author Igor
 */
public class LoCategoryInfoHelper {
    private Data data;
    
    public enum Properties implements PropertyEnum
    {
        ID ("id"),
        DESC ("desc"),
        EFFECTIVE_DATE ("effectiveDate"),
        EXPIRATION_DATE ("expirationDate"),
        LO_REPOSITORY ("loRepository"),
        NAME ("name"),
        STATE ("state"),
        TYPE ("type"),
        META ("metaInfo");
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

    public LoCategoryInfoHelper() {
        this.data = new Data();
    }

    public LoCategoryInfoHelper(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setId(String id) {
        data.set(Properties.ID.getKey(), id);
    }

    public String getId() {
        return (String) data.get(Properties.ID.getKey());
    }

    public void setDesc(Data descData) {
        HelperUtil.setDataField(Properties.DESC, data, descData);
    }

    public Data getDesc() {
        return HelperUtil.getDataField(Properties.DESC, data);
    }

    public void setEffectiveDate(Date effectiveDate) {
        data.set(Properties.EFFECTIVE_DATE.getKey(), effectiveDate);
    }

    public Date getEffectiveDate() {
        return (Date) data.get(Properties.EFFECTIVE_DATE.getKey());
    }

    public void setExpirationDate(Date expirationDate) {
        data.set(Properties.EXPIRATION_DATE.getKey(), expirationDate);
    }

    public Date getExpirationDate() {
        return (Date) data.get(Properties.EXPIRATION_DATE.getKey());
    }

    public void setLoRepository(String loRepository) {
        data.set(Properties.LO_REPOSITORY.getKey(), loRepository);
    }

    public String getLoRepository() {
        return (String) data.get(Properties.LO_REPOSITORY.getKey());
    }

    public void setName(String name) {
        data.set(Properties.NAME.getKey(), name);
    }

    public String getName() {
        return (String) data.get(Properties.NAME.getKey());
    }

    public void setState(String state) {
        data.set(Properties.STATE.getKey(), state);
    }

    public String getState() {
        return (String) data.get(Properties.STATE.getKey());
    }

    public void setType(String type) {
        data.set(Properties.TYPE.getKey(), type);
    }

    public String getType() {
        return (String) data.get(Properties.TYPE.getKey());
    }

}
