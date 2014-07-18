package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * This is a generic way to send messages back to the UI. It was originally
 * created as a way to send error messages to the UI
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserMessageResult", propOrder = {
        "genericMessage", "detailedMessage", "consoleMessage", "type", "actionLinks"})
public class UserMessageResult {

    public static final class MessageTypes {
        public static final String OK = "ok";
        public static final String WARN = "warn";
        public static final String ERROR = "error";
    };

    private String genericMessage;  // ex: "Unable to add to cart"
    private String detailedMessage; // ex: "The Reg Group you entered is not in the Offered State.
    private String consoleMessage;  // ex: "The Reg Group you entered is not in the Offered State. [id:1102134 state:canceled]
    private String type; // ex: success, warning, error

    private List<Link> actionLinks; // You might want to provide an action link back to the user. like: "retry" or "submit error to admin"


    public String getGenericMessage() {
        return genericMessage;
    }

    public void setGenericMessage(String genericMessage) {
        this.genericMessage = genericMessage;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }

    public String getConsoleMessage() {
        return consoleMessage;
    }

    public void setConsoleMessage(String consoleMessage) {
        this.consoleMessage = consoleMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Link> getActionLinks() {
        return actionLinks;
    }

    public void setActionLinks(List<Link> actionLinks) {
        this.actionLinks = actionLinks;
    }
}
