package org.kuali.student.brms.repository.test;

public class Message 
{
	private String message;
	private boolean valid;
	public Message() {}
	public Message( boolean valid, String message ) 
	{ 
		this.valid = valid; 
		this.message = message; 
	}
    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }
    public boolean isValid() { return valid; }
    public void setValid(boolean value) { this.valid = value; }
}
