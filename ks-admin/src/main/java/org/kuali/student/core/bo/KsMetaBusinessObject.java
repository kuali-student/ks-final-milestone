package org.kuali.student.core.bo;

import java.util.Date;

public interface KsMetaBusinessObject extends KsBusinessObject {

    public String getCreateId();

    public void setCreateId(String createId);
    
    public Date getCreateDate();

    public void setCreateDate(Date createDate);

    public String getUpdateId();

    public void setUpdateId(String updateId);
    
    public Date getUpdateDate();

    public void setUpdateDate(Date updateDate);
    
    public Meta getMeta();
    
    public void setMeta(Meta meta);

}
