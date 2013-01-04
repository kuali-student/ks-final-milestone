package org.kuali.student.lum.common.client.lo;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;
import org.kuali.student.r2.common.dto.MetaInfo;

import java.util.Date;

/**
 * @author Igor
 */
public class LoCategoryInfoHelper {
    private Data data;
    
    public enum Properties implements PropertyEnum
    {
        ID ("id"),
        DESCR ("descr"),
        EFFECTIVE_DATE ("effectiveDate"),
        EXPIRATION_DATE ("expirationDate"),
        LO_REPOSITORY_KEY ("loRepositoryKey"),
        NAME ("name"),
        STATE_KEY ("stateKey"),
        TYPE_KEY ("typeKey"),
        META ("meta");
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

    public void setDescr(Data descData) {
        HelperUtil.setDataField(Properties.DESCR, data, descData);
    }

    public Data getDescr() {
        return HelperUtil.getDataField(Properties.DESCR, data);
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

    public void setLoRepositoryKey(String loRepositoryKey) {
        data.set(Properties.LO_REPOSITORY_KEY.getKey(), loRepositoryKey);
    }

    public String getLoRepositoryKey() {
        return (String) data.get(Properties.LO_REPOSITORY_KEY.getKey());
    }

    public void setName(String name) {
        data.set(Properties.NAME.getKey(), name);
    }

    public String getName() {
        return (String) data.get(Properties.NAME.getKey());
    }

    public void setStateKey(String stateKey) {
        data.set(Properties.STATE_KEY.getKey(), stateKey);
    }

    public String getStateKey() {
        return (String) data.get(Properties.STATE_KEY.getKey());
    }

    public void setTypeKey(String typeKey) {
        data.set(Properties.TYPE_KEY.getKey(), typeKey);
    }

    public String getTypeKey() {
        return (String) data.get(Properties.TYPE_KEY.getKey());
    }

    public void setMeta(Data meta) {
        data.set(Properties.META.getKey(), meta);
    }

    public Data getMeta() {
        return (Data) data.get(Properties.META.getKey());
    }

}
