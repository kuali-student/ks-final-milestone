package org.kuali.student.brms.repository;

public class BRMSRepositoryException extends Exception 
{
    public BRMSRepositoryException( Throwable cause )
    {
    	super( cause );
    }

    public BRMSRepositoryException( String msg, Throwable cause )
    {
    	super( msg, cause );
    }

    public BRMSRepositoryException( String msg )
    {
        super(msg);
    }
}
