package org.kuali.student.brms.repository.test;

public class Email
{
    private String emailAddress;
    private long average = -1;
    
	public Email() {}
	public Email(String email) { this.emailAddress = email; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String value) { this.emailAddress = value; }
    public long calculateAverage() 
    { 
    	this.average = Math.round( Math.random() * 100 );
    	return this.average;
    }

    public long getAverage()
    {
    	return this.average;
    }

    public void setAverage( long avg )
    {
    	this.average = avg;
    }
}
