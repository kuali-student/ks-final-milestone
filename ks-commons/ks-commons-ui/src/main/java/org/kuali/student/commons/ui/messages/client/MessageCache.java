package org.kuali.student.commons.ui.messages.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Contains a cache of loaded I18N messages. Only used when I18N is needed without the MVC framework.
 */
public class MessageCache {
    /**
     * Key used for retrieving preloaded messages from document body. Messages can be preloaded by the servlet/JSP that
     * generates the page and embedded in the page content. TODO: rewrite this to use the "encode for response" mechanism
     * provided by GWT 1.5, or remove as it really isn't used.
     */
    public static final String CACHED_MESSAGES = "kualiStudentCachedMessages";

    private static Map<String, Messages> cachedMessages = new HashMap<String, Messages>();

    static {
        Map<String, String> m = new HashMap<String, String>();
        try {
            Dictionary dict = Dictionary.getDictionary(CACHED_MESSAGES);
            for (String key : dict.keySet()) {
                m.put(key, dict.get(key));
            }
        } catch (Exception e) {
            // TODO catch the correct exception and log it, just means there was no message cache defined
        }
        cachedMessages.put(CACHED_MESSAGES, new Messages(CACHED_MESSAGES, m));
    }

    /**
     * Returns messages cached in the document body.
     * 
     * @return Messages object of messages cached in the document body.
     */
    public Messages getMessages() {
        return cachedMessages.get(CACHED_MESSAGES);
    }

    /**
     * Returns cached messages. Returns null if messages have not yet been loaded.
     * 
     * @param groupName
     *            the name of the group of messages to retrieve.
     * @return cached messages. Returns null if messages have not yet been loaded.
     */
    public Messages getMessages(String groupName) {
        return cachedMessages.get(groupName);
    }

    /**
     * Loads messages from messages service
     * 
     * @param locale
     *            the locale to use when loading the messages
     * @param groupName
     *            the name of the group of messages to retrieve.
     * @param callback
     *            AsyncCallback<Messages> to be called on response.
     */
    public void loadMessages(final String locale, final String groupName, final AsyncCallback<Messages> callback) {
        MessagesService.Util.getInstance().getMessages(locale, groupName, new AsyncCallback<Messages>() {
            public void onFailure(Throwable error) {
                callback.onFailure(error);
            }

            public void onSuccess(Messages messages) {
                cachedMessages.put(groupName, messages);
                callback.onSuccess(messages);
            }
        });

    }

    /**
     * Loads messages from messages service
     * 
     * @param locale
     *            the locale to use when loading the messages
     * @param groupNames
     *            the names of the groups of messages to retrieve.
     * @param callback
     *            AsyncCallback<Map<String, Messages>> to be called on response. Map is keyed by group name.
     */
    public void loadMessages(final String locale, final List<String> groupNames, final AsyncCallback<Map<String, Messages>> callback) {
        MessagesService.Util.getInstance().getMessages(locale, groupNames, new AsyncCallback<Map<String, Messages>>() {
            public void onFailure(Throwable error) {
                callback.onFailure(error);
            }

            public void onSuccess(Map<String, Messages> messages) {
                cachedMessages.putAll(messages);
                callback.onSuccess(messages);
            }
        });
    }

}
