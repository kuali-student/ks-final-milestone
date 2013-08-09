/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.service;

import java.util.ArrayList;

import org.kuali.student.r2.common.messages.dto.MessageInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.r2.common.dto.LocaleInfo;

public interface MessagesRpcServiceAsync {
    
    public void getMessageGroups(AsyncCallback<ArrayList<String>> callback);
    
    public void getLocales(AsyncCallback<ArrayList<LocaleInfo>> callback);

    public void getMessage(
            String localeKey, 
            String messageGroupKey, 
            String messageKey, 
            AsyncCallback<MessageInfo> callback);
    
    public void getMessagesByGroup(
            String localeKey, 
            String messageGroupKey, 
            AsyncCallback<ArrayList<MessageInfo>> callback);
    
    public void getMessagesByGroups(
            String localeKey, 
            ArrayList<String> messageGroupKeys, 
            AsyncCallback<ArrayList<MessageInfo>> callback);
    
    public void updateMessage(
            String localeKey, 
            String messageGroupKey, 
            String messageKey,
            MessageInfo messageInfo, 
            AsyncCallback<MessageInfo> callback);
    
    public void createMessage(MessageInfo messageInfo, 
            AsyncCallback<MessageInfo> callback);
}
