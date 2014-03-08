package org.kuali.student.r2.common.messenger;

import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.MapMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This class ...
 *
 * @author Kuali Student Team
 */
public class UserMessageMap {

    // this is the more performent way to access a thread safe singleton
    public static final UserMessageMap INSTANCE = new UserMessageMap();

    // Stores a KEY and a Start Timestamp
    private Map<String, Queue<UserMessage>> messageMap = new HashMap<String, Queue<UserMessage>>();

    /**
     * This is a singleton so it must not be public
     */
    protected UserMessageMap() {
    }

    /**
     *
     * @param key
     * @param userMessage
     */
    public synchronized void addMessageForKey(String key, UserMessage userMessage) {
        if(!messageMap.containsKey(key)){
            messageMap.put(key, new LinkedList<UserMessage>());
        }
        Queue<UserMessage> queue = messageMap.get(key);
        queue.add(userMessage);

    }

    /**
     *
     * @param key
     * @return
     */
    public synchronized List<UserMessage> getMessagesForKey(String key) {
        List<UserMessage> messages = new ArrayList<UserMessage>();
        if(messageMap.containsKey(key)){

            Queue<UserMessage> queue = messageMap.remove(key);
            while(!queue.isEmpty()){
                messages.add(queue.poll());
            }
        }
        return messages;

    }

}
