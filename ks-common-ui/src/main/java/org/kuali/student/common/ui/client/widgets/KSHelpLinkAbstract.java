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

package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.dto.HelpInfo;

import com.google.gwt.user.client.ui.Composite;


public abstract class KSHelpLinkAbstract extends Composite { 

    public enum HelpLinkState {
        DEFAULT,
        OK,
        ERROR
    }


    public abstract HelpInfo getHelpInfo();

    public abstract void setHelpInfo(HelpInfo helpInfo);

    public abstract HelpLinkState getState();
    
    public abstract void setStateDefault();
    public abstract void setStateOK();
    public abstract void setStateOK(String text);

	
    public abstract void setStateError();
    public abstract void setStateError(String text); 
   
}
