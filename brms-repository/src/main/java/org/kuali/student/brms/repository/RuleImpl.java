package org.kuali.student.brms.repository;

import java.util.Calendar;

public class RuleImpl extends ItemImpl implements Rule
{
	private byte[] binaryContent = null;
	private String content = null;
	private String format = null;
	
	private Calendar effectiveDate = null;
	private Calendar expiryDate = null;
	
	/**
	 * 
	 * @param name Asset's name
	 * @param content Rule source
	 * @param binaryContent Binary content
	 * @param effectiveDate Date asset becomes effective
	 * @param expiryDate Date asset expires
	 */
	public RuleImpl( String uuid, String name ) 
	{
		super( uuid, name );
	}

    /**
     * Returns the binary data as a byte array.
     * 
     * @return Binary data as a byte array
     */
    public byte[] getBinaryContent() 
    {
    	return this.binaryContent;
    }

    public void setBinaryContent( byte[] binaryContent )
    {
    	this.binaryContent = binaryContent;
    }

    public void setContent( String content )
    {
    	this.content = content;
    }
    
    /**
     * Returns the rule source string.
     * If this is a binary asset, this will return null (use #getBinaryContent instead).
     * 
     * @return Rule source
     */
    public String getContent()
    {
    	return this.content;
    }
    
    public void setFormat( String format )
    {
    	this.format = format;
    }

	public String getFormat() {
		return this.format;
	}

	public void setEffectiveDate(Calendar effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

    /**
     * Return the date the rule becomes effective.
     * 
     * @return Date the rule becomes effective
     */
    public Calendar getEffectiveDate()
    {
    	return this.effectiveDate;
    }

	public void setExpiryDate(Calendar expiryDate) {
		this.expiryDate = expiryDate;
	}

    /**
     * Returns the date the rule expires.
     * 
     * @return Date the rule expires
     */
    public Calendar getExpiryDate()
    {
    	return this.expiryDate;
    }

}