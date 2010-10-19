package org.kuali.student.core.bo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class MetaBusinessObjectBase extends KsBusinessObjectBase implements MetaBusinessObject {

    private static final long serialVersionUID = -6804800481530556124L;

    
    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;


    /**
     * This will take properties and create a Meta object to return, the value is not persisted.
     * This is to support the KS embedded style Meta object for future compatibility.  OJB/KNS
     * do not seem to support embedded entities (called Nested in OJB terms).
     */
    @Override
    public Meta getMeta() {
        Meta meta = new Meta();
        
        meta.setCreateId(createId);
        meta.setCreateTime(createDate);
        meta.setUpdateId(updateId);
        meta.setUpdateTime(updateDate);
        
        return meta;
    }

    /**
     * This will set the internal properties from the incoming Meta object.
     * This is to support the KS embedded style Meta object for future compatibility.  OJB/KNS
     * do not seem to support embedded entities (called Nested in OJB terms).
     */
    @Override
    public void setMeta(Meta meta) {
        this.createId = meta.getCreateId();
        this.createDate = meta.getCreateTime();
        this.updateId = meta.getUpdateId();
        this.updateDate = meta.getCreateTime();
    }
    
    @Override
    public void beforeInsert(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
        super.beforeInsert(persistenceBroker);

        if (StringUtils.isBlank(createId)) {
            this.createId = GlobalVariables.getUserSession().getPrincipalName();
        }

        if (createDate == null) {
            this.createDate = KNSServiceLocator.getDateTimeService().getCurrentSqlDate();
        }
    }

    @Override
    public void beforeUpdate(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
        super.beforeUpdate(persistenceBroker);

        this.updateId = GlobalVariables.getUserSession().getPrincipalName();
        this.updateDate = KNSServiceLocator.getDateTimeService().getCurrentSqlDate();
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
