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

package org.kuali.student.common.ui.client.widgets.buttongroups;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;


public class ButtonEnumerations {
    public interface ButtonEnum{
        public String getText();
        public ButtonStyle getStyle();
    };

    final static ApplicationContext context = Application.getApplicationContext();

    public static enum OkEnum implements ButtonEnum{Ok;

    @Override
    public String getText() {
        switch(this){
            case Ok:
                return "Ok";
        }
        return null;  
    }

	@Override
	public ButtonStyle getStyle() {
		return ButtonStyle.PRIMARY;
	}
    };


    public static enum YesNoEnum implements ButtonEnum{YES, NO;

    @Override
    public String getText() {
        switch(this){
            case YES:
                return context.getMessage("yes");
            case NO:
                return context.getMessage("no");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
		return ButtonStyle.PRIMARY;
	}
    };

    public static enum ConfirmCancelEnum implements ButtonEnum{CONFIRM, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case CONFIRM:
                return context.getMessage("confirm");
            case CANCEL:
                return context.getMessage("cancel");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
        switch(this){
        case CONFIRM:
            return ButtonStyle.PRIMARY;
        case CANCEL:
            return ButtonStyle.DEFAULT_ANCHOR;
        }
		return ButtonStyle.PRIMARY;
	}
    };

    public static enum YesNoCancelEnum implements ButtonEnum{YES, NO, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case YES:
                return context.getMessage("yes");
            case NO:
                return context.getMessage("no");
            case CANCEL:
                return context.getMessage("cancel");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
		return ButtonStyle.PRIMARY;
	}
    };

    public static enum AddModifyRemoveEnum implements ButtonEnum{ADD, MODIFY, REMOVE;

    @Override
    public String getText() {
        switch(this){
            case ADD:
                return context.getMessage("add");
            case MODIFY:
                return context.getMessage("modify");
            case REMOVE:
                return context.getMessage("remove");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
		return ButtonStyle.PRIMARY;
	}
    };

    public static enum SendCancelEnum implements ButtonEnum{SEND, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case SEND:
                return context.getMessage("send");
            case CANCEL:
                return context.getMessage("cancel");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
		return ButtonStyle.PRIMARY;
	}
    };

    public static enum GoCancelEnum implements ButtonEnum{GO, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case GO:
                return context.getMessage("go");
            case CANCEL:
                return context.getMessage("cancel");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
		return ButtonStyle.PRIMARY;
	}
    };
    
    public static enum CreateCancelEnum implements ButtonEnum{CREATE, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case CREATE:
                return context.getMessage("create");
            case CANCEL:
                return context.getMessage("cancel");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
		return ButtonStyle.PRIMARY;
	}
    };
    
    public static enum SearchCancelEnum implements ButtonEnum{SEARCH, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case SEARCH:
                return context.getMessage("search");
            case CANCEL:
                return context.getMessage("cancel");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
		return ButtonStyle.PRIMARY;
	}
    };
}
