package org.kuali.student.myplan.audit.service;

import java.util.HashMap;

public enum uAchieveReportStatus
{
    COMPLETE ( "C", "Complete" ),
    FORCED ( "F", "Complete, but forced" ),
    INPROGRESS ( "I", "Complete using in progress courses" ),
    TEXTREQ ( "T", "Text requirement" ),
    NOTUSED ( "X", "Requirement not used" ),
    NOTCOMPLETE ( "N", "Not complete" ),
    BADCODE ( "", "Bad code" );

    private String code;
    private String message;
    private static HashMap<String, uAchieveReportStatus> map = new HashMap<String, uAchieveReportStatus>();

    static
    {
        for( uAchieveReportStatus ugh : uAchieveReportStatus.values() )
        {
            map.put( ugh.code, ugh );
        }
    }

    uAchieveReportStatus( String code, String message ) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() { return message; }

    public boolean same( String value )
    {
        if( !map.containsKey( value )) return false;
        return this == map.get( value );
    }

    public static uAchieveReportStatus translate( String value )
    {
        uAchieveReportStatus result = map.get( value );
        return result != null ? result : BADCODE;
    }

}
