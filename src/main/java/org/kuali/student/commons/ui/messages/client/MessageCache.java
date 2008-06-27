package org.kuali.student.commons.ui.messages.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MessageCache {
	public static final String CACHED_MESSAGES = "kualiStudentCachedMessages";
	private static String locale = "en";
	
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
		
		// TODO detect locale
	}

	public Messages getMessages() {
		return cachedMessages.get(CACHED_MESSAGES);
	}
	public Messages getMessages(String groupName) {
		return cachedMessages.get(groupName);
	}

	public void loadMessages(final String groupName, final AsyncCallback callback) {
		MessagesService.Util.getInstance().getMessages(locale, groupName, new AsyncCallback() {
			public void onFailure(Throwable arg0) {
				callback.onFailure(arg0);
			}
			public void onSuccess(Object arg0) {
				cachedMessages.put(groupName, (Messages) arg0);
				callback.onSuccess(arg0);
			}
		});
		
	}
	
	public void loadMessages(final List<String> groupNames, final AsyncCallback callback) {
		MessagesService.Util.getInstance().getMessages(locale, groupNames, new AsyncCallback() {
			public void onFailure(Throwable arg0) {
				callback.onFailure(arg0);
			}
			public void onSuccess(Object arg0) {
				for (Messages m : (List<Messages>)arg0) {
					cachedMessages.put(m.getGroupName(), m);	
				}
				callback.onSuccess(arg0);
			}
		});
	}
	
	public static String getLocale() {
		return locale;
	}
	
	
	
}
