package org.kuali.student.myplan.course.util;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.myplan.course.dataobject.MeetingDetails;

public class MeetingDetailsPropertyEditor extends PropertyEditorSupport {
    private final static Logger logger = Logger.getLogger(MeetingDetailsPropertyEditor.class);

    public final static String TO_BE_ARRANGED = "To be arranged";

    @Override
    public void setValue(Object value) {
        if (value == null) {
            logger.error("MeetingDetails was null");
            return;
        }

        if (!(value instanceof List)) {
            logger.error(String.format("Value was type [%s] instead of MeetingDetails.", value.getClass()));
            return;
        }

        super.setValue(value);
    }

    @Override
    public String getAsText() {
        List<MeetingDetails> list = (List<MeetingDetails>) super.getValue();

        StringBuilder sb = new StringBuilder();
        sb.append("<div class='meetingdetailslist'>");

        for (MeetingDetails m : list) {

            boolean tba = false;

            StringBuilder temp = new StringBuilder();
            temp.append("<div class='meetingdetails'>");

            String days = m.getDays();
            String time = m.getTime();
            String building = m.getBuilding();
            String room = m.getRoom();
            String campus = m.getCampus();

            // If the days and building are empty, section is TBA
            if ((days == null || days.equals("")) && (time == null || time.equals(""))) {
                tba = true;
            }

            if (days == null) days = "";
            if (time == null) time = "";
            if (building == null) building = "";
            if (room == null) room = "";
            if (campus == null) campus = "";

            if (!tba) {
                temp.append("<span class='meetingdays'>" + days + "</span>");
                temp.append("<span class='meetingtime'>" + time + "</span>");
            } else {
                temp.append("<span class='meetingtba'>" + TO_BE_ARRANGED + "</span>");
            }

            if (!building.equals("NOC") && !building.startsWith("*") && campus.equalsIgnoreCase("seattle")) {
                temp.append("<span class='meetingbuilding'><a href='http://uw.edu/maps/?" + building + "' target='_blank'>" + building + "</a></span>");
            } else {
                temp.append("<span class='meetingbuilding'>" + building + "</span>");
            }

            temp.append("<span class='meetingroom'>" + room + "</span>");

            temp.append("</div>");

            sb.append(temp);
        }

        sb.append("</div>");

        String result = sb.toString();
        result = result.replace('\'', '\"');
        return result;
    }

}
