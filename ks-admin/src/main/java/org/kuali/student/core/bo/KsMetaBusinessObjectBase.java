package org.kuali.student.core.bo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class KsMetaBusinessObjectBase extends KsBusinessObjectBase implements KsMetaBusinessObject {

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
     * 
     * Uses setter methods to allow behavior from subclass.
     */
    @Override
    public void setMeta(Meta meta) {
        this.setCreateId(meta.getCreateId());
        this.setCreateDate(meta.getCreateTime());
        this.setUpdateId(meta.getUpdateId());
        this.setUpdateDate(meta.getCreateTime());
    }
    
    @Override
    public void prePersist() throws PersistenceBrokerException {
        super.prePersist();

        if (StringUtils.isBlank(this.getCreateId())) {
            this.setCreateId(GlobalVariables.getUserSession().getPrincipalName());
        }

        if (this.getCreateDate() == null) {
            this.setCreateDate(CoreApiServiceLocator.getDateTimeService().getCurrentSqlDate());
        }
    }

    @Override
    public void preUpdate() {
        super.preUpdate();

        this.setUpdateId(GlobalVariables.getUserSession().getPrincipalName());
        this.setUpdateDate(CoreApiServiceLocator.getDateTimeService().getCurrentSqlDate());
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
