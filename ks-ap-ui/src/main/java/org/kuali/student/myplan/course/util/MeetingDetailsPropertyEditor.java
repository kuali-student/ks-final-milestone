package org.kuali.student.myplan.course.util;

import org.apache.log4j.Logger;
import org.kuali.student.myplan.course.dataobject.MeetingDetails;

import java.beans.PropertyEditorSupport;
import java.util.List;

public class MeetingDetailsPropertyEditor extends PropertyEditorSupport {
	private final static Logger logger = Logger.getLogger(MeetingDetailsPropertyEditor.class);
	
	public final static String TO_BE_ARRANGED = "to be arranged";

	
	@Override
	public void setValue(Object value) {
		if (value == null) {
			logger.error("MeetingDetails was null");
			return;
		}
		
		if ( ! (value instanceof List)) {
            logger.error(String.format("Value was type [%s] instead of MeetingDetails.", value.getClass()));
            return;
        }

        super.setValue(value);
	}
	
	String template =
		"<div class='meetingdetails'>" +
			"<span class='meetingdays'>%s</span>" +
			"<span class='meetingtime'>%s</span>" +
			"<span class='meetingbuilding'>%s</span>" +
			"<span class='meetingroom'>%s</span>" +
		"</div>";

	
    @Override
    public String getAsText() {
    	List<MeetingDetails> list = (List<MeetingDetails>) super.getValue();
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append( "<div class='meetingdetailslist'>" );
    	
    	for( MeetingDetails m : list ) {

    		boolean tba = false;
    		
	    	String building = m.getBuilding();
	    	if( building == null || "".equals( building )) {
	    		building = TO_BE_ARRANGED;
	    		tba = true;
	    	}
	    	else if( !"NOC".equals( building) && !building.startsWith("*") && "seattle".equalsIgnoreCase(m.getCampus())) {
	    		building = String.format("<a href='http://uw.edu/maps/?%s' target='_blank'>%s</a>", building, building);
	    	}

	    	String days = m.getDays();
	    	if( days == null ) {
	    		days = "";
	    	}
	    	if( "".equals( days ) && !tba ) {
	    		days = TO_BE_ARRANGED;
	    	}
	    	
	    	String time = m.getTime();
	    	if( time == null ) {
	    		time = "";
	    	}
	    	
			String room = m.getRoom();
	    	if( room == null ) {
	    		room = "";
	    	}
	    	if( "".equals( room ) && !tba ) {
	    		room = TO_BE_ARRANGED;
	    	}
			
			String temp = String.format(template, days, time, building, room);
	    	sb.append(temp );
    	}
    	sb.append( "</div>" );

    	String result = sb.toString();
    	result = result.replace( '\'', '\"' );
		return result;
    }
	

}
