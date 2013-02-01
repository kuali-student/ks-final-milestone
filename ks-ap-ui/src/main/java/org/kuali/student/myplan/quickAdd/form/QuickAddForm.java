package org.kuali.student.myplan.quickAdd.form;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.context.PlanConstants;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 9/25/12
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuickAddForm extends UifFormBase {
    private final Logger logger = Logger.getLogger(QuickAddForm.class);

    /**
     * The return code for a plan item add, change, or delete.
     */
    public enum REQUEST_STATUS {
        /*  The requested operation was successful. */
        SUCCESS,
        /*  The requested operation was unnecessary (e.g. the plan item was already deleted), but appropriate
         *  javascript events were generated/available.
         *
         *  TODO: Looks like this status may not be necessary. */
        NOOP,
        /* The requested operation failed. */
        ERROR
    }

    private REQUEST_STATUS requestStatus;
    private String atpId;
    private List<String> suggestions;
    private String courseCd;
    private String courseId;
    private String planType;
    private String termYear;

    /**
     * A list of javascript events as:
     * EVENT_NAME
     * param1: p1
     * param2: p2
     * PLAN_ITEM_ADDED
     * itemType: plannedCourse
     * planItem: pi1
     * courseId: c1
     */
    private Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> javascriptEvents;


    public Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> getJavascriptEvents() {
        return javascriptEvents;
    }

    public void setJavascriptEvents(Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> javascriptEvents) {
        this.javascriptEvents = javascriptEvents;
    }


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public REQUEST_STATUS getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(REQUEST_STATUS requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public String getCourseCd() {
        return courseCd;
    }

    public void setCourseCd(String courseCd) {
        this.courseCd = courseCd;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getTermYear() {
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }

    /**
     * Returns the list of events that should be
     */
    public String getJavascriptEventsAsJSON() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonOut = null;
        try {
            //  Turn the list of javascript events into a string of JSON.
            jsonOut = mapper.writeValueAsString(javascriptEvents);
            jsonOut = StringEscapeUtils.unescapeJava(jsonOut);
        } catch (Exception e) {
            logger.error("Could not convert javascript events to JSON.", e);
            jsonOut = "";
        }

        //  TODO: Determine if there is a config that can be set to avoid having to do this.
        jsonOut = jsonOut.replaceAll("\"\\{", "{");
        jsonOut = jsonOut.replaceAll("}\"", "}");

        return jsonOut;
    }

}
