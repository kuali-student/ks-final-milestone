package org.kuali.student.common.eventing;

import org.kuali.student.r2.common.dto.ContextInfo;

import java.io.Serializable;

/**
 * An EventMessage is a message that is sent to indicate an event has happened. For example, a user updates
 * an Activity's seat count; an event should be broadcast telling the system that seat counts have changed
 * maybe you should process people from a waitlist.
 */
public class EventMessage implements Serializable{

    // valid event actions
    public static enum Action {
        CREATE, UPDATE, DELETE
    }

    String id;  // id of the object this event relates to
    String action;  // action type: CREATE, UPDATE, DELETE
    String type;    // corresponds to the type associated with all KS objects. ex: kuali.lui.type.activity.offering.lecture
    ContextInfo contextInfo; // context information about the event.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "id='" + id + '\'' +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", contextInfo=" + contextInfo +
                '}';
    }
}
