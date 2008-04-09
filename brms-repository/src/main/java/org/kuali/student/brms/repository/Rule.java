package org.kuali.student.brms.repository;

import java.util.Calendar;

public interface Rule extends Item
{
    public void setBinaryContent( byte[] binaryContent );

    public byte[] getBinaryContent();

    public String getContent();

    public void setContent( String content );
    
    public String getFormat();

    public void setFormat( String format );
    
    public void setEffectiveDate(Calendar effectiveDate);
    
    public Calendar getEffectiveDate();
    
    public void setExpiryDate(Calendar expiryDate);
    
    public Calendar getExpiryDate();
}
