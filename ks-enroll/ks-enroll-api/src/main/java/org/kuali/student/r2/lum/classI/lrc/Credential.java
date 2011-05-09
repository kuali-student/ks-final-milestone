package org.kuali.student.r2.lum.classI.lrc;

import java.util.Date;

import org.kuali.student.common.dto.RichTextInfo;

public interface Credential {
	
	
	  /**
     * Name of the credential.
     */
    public String getName() ;
    /**
     * Description of the credential.
     */
    public RichTextInfo getDesc() ;

    /**
     * Value of the credential.
     */
    public String getValue() ;

    /**
     * Date and time that this credential value became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() ;

    /**
     * Date and time that this credential value expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() ;

  
    /**
     * Unique identifier for a credential type.
     */
    public String getType() ;
    /**
     * Unique identifier for a credential value.
     */
    public String getId() ;

}
