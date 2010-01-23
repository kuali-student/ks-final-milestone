package org.kuali.student.commons.ui.messages.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.messages.client.MessagesService;
import org.kuali.student.commons.ui.messages.server.impl.MessagesServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * GWT service wrapper for internationalization messages.
 */
public class MessagesServiceImplGWT extends RemoteServiceServlet implements MessagesService {
    private static final long serialVersionUID = 1L;
    private static MessagesService service = new MessagesServiceImpl();

    /**
     * @see org.kuali.student.commons.ui.messages.client.MessagesService
     */
    @Override
    public Messages getMessages(String locale, String groupName) {
        return service.getMessages(locale, groupName);
    }

    /**
     * @see org.kuali.student.commons.ui.messages.client.MessagesService
     */
    @Override
    public Map<String, Messages> getMessages(String locale, List<String> groupNames) {
        return service.getMessages(locale, groupNames);
    }

    /**
     * @see org.kuali.student.commons.ui.messages.client.MessagesService
     */
    @Override
    public List<String> getGroupNames(String locale) {
        return service.getGroupNames(locale);
    }

    /**
     * @see org.kuali.student.commons.ui.messages.client.MessagesService
     */
    @Override
    public List<String> getLocales() {
        return service.getLocales();
    }

}
