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
        public ButtonEnum getActionType();
        public ButtonEnum getCancelType();
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
    
        @Override
        public ButtonEnum getActionType() {
            return Ok;
        }
    
        @Override
        public ButtonEnum getCancelType() {
            return Ok;
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
	
    @Override
    public ButtonEnum getActionType() {
        return YES;
    }

    @Override
    public ButtonEnum getCancelType() {
        return NO;
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
    @Override
    public ButtonEnum getActionType() {
        return CONFIRM;
    }
    @Override
    public ButtonEnum getCancelType() {
        return CANCEL;
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
		switch(this){
			case CANCEL:
				return ButtonStyle.ANCHOR_LARGE_CENTERED;
			default:
				return ButtonStyle.PRIMARY;
		}
		
	}
    @Override
    public ButtonEnum getActionType() {
        return YES;
    }

    @Override
    public ButtonEnum getCancelType() {
        return NO;
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
    @Override
    public ButtonEnum getActionType() {
        return null;
    }

    @Override
    public ButtonEnum getCancelType() {
        return null;
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
    @Override
    public ButtonEnum getActionType() {
        return SEND;
    }

    @Override
    public ButtonEnum getCancelType() {
        return CANCEL;
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
    @Override
    public ButtonEnum getActionType() {
        return GO;
    }

    @Override
    public ButtonEnum getCancelType() {
        return CANCEL;
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
    @Override
    public ButtonEnum getActionType() {
        return CREATE;
    }

    @Override
    public ButtonEnum getCancelType() {
        return CANCEL;
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
            switch(this){
            case SEARCH:
                return ButtonStyle.PRIMARY;
            case CANCEL:
                return ButtonStyle.ANCHOR_LARGE_CENTERED;
            }
            return ButtonStyle.PRIMARY;
        }
        @Override
        public ButtonEnum getActionType() {
            return SEARCH;
        }
        @Override
        public ButtonEnum getCancelType() {
            return CANCEL;
        }
    };

    public static enum AddCancelEnum implements ButtonEnum{ADD, CANCEL;

        @Override
        public String getText() {
            switch(this){
                case ADD:
                    return context.getMessage("add");
                case CANCEL:
                    return context.getMessage("cancel");
            }
            return null;
        }
    	@Override
        public ButtonStyle getStyle() {
            switch(this){
            case ADD:
                return ButtonStyle.PRIMARY;
            case CANCEL:
                return ButtonStyle.ANCHOR_LARGE_CENTERED;
            }
            return ButtonStyle.PRIMARY;
        }
        @Override
        public ButtonEnum getActionType() {
            return ADD;
        }
        @Override
        public ButtonEnum getCancelType() {
            return CANCEL;
        }
    };

    public static enum UpdateCancelEnum implements ButtonEnum{UPDATE, CANCEL;

        @Override
        public String getText() {
            switch(this){
                case UPDATE:
                    return context.getMessage("update");
                case CANCEL:
                    return context.getMessage("cancel");
            }
            return null;
        }
    	@Override
        public ButtonStyle getStyle() {
            switch(this){
            case UPDATE:
                return ButtonStyle.PRIMARY;
            case CANCEL:
                return ButtonStyle.DEFAULT_ANCHOR;
            }
            return ButtonStyle.PRIMARY;
        }
        @Override
        public ButtonEnum getActionType() {
            return UPDATE;
        }
        @Override
        public ButtonEnum getCancelType() {
            return CANCEL;
        }
    };

    public static enum ContinueCancelEnum implements ButtonEnum{CONTINUE, CANCEL;

        @Override
        public String getText() {
            switch(this){
                case CONTINUE:
                    return context.getMessage("continue");
                case CANCEL:
                    return context.getMessage("cancel");
            }
            return null;
        }
    	@Override
        public ButtonStyle getStyle() {
            switch(this){
            case CONTINUE:
                return ButtonStyle.PRIMARY;
            case CANCEL:
                return ButtonStyle.DEFAULT_ANCHOR;
            }
            return ButtonStyle.PRIMARY;
        }
        @Override
        public ButtonEnum getActionType() {
            return CONTINUE;
        }
        @Override
        public ButtonEnum getCancelType() {
            return CANCEL;
        }
    };

    public static enum SaveCancelEnum implements ButtonEnum{SAVE, CANCEL;

        @Override
        public String getText() {
            switch(this){
                case SAVE:
                    return context.getMessage("save");
                case CANCEL:
                    return context.getMessage("cancel");
            }
            return null;
        }
    	@Override
        public ButtonStyle getStyle() {
            switch(this){
            case SAVE:
                return ButtonStyle.PRIMARY;
            case CANCEL:
                return ButtonStyle.DEFAULT_ANCHOR;
            }
            return ButtonStyle.PRIMARY;
        }
        @Override
        public ButtonEnum getActionType() {
            return SAVE;
        }
        @Override
        public ButtonEnum getCancelType() {
            return CANCEL;
        }
    };

    public static enum SaveContinueCancelEnum implements ButtonEnum{SAVE_CONTINUE, CANCEL;

        @Override
        public String getText() {
            switch(this){
                case SAVE_CONTINUE:
                    return context.getMessage("saveContinue");
                case CANCEL:
                    return context.getMessage("cancel");
            }
            return null;
        }
    	@Override
        public ButtonStyle getStyle() {
            switch(this){
            case SAVE_CONTINUE:
                return ButtonStyle.PRIMARY;
            case CANCEL:
                return ButtonStyle.DEFAULT_ANCHOR;
            }
            return ButtonStyle.PRIMARY;
        }
        @Override
        public ButtonEnum getActionType() {
            return SAVE_CONTINUE;
        }
        @Override
        public ButtonEnum getCancelType() {
            return CANCEL;
        }
    };
    
    public static enum ConfirmApprovalCancelEnum implements ButtonEnum{CONFIRM, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case CONFIRM:
                return context.getMessage("confirmApproval");
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
    @Override
    public ButtonEnum getActionType() {
        return CONFIRM;
    }
    @Override
    public ButtonEnum getCancelType() {
        return CANCEL;
    }	
    };
    
    public static enum AcknowledgeCancelEnum implements ButtonEnum{ACKNOWLEDGE, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case ACKNOWLEDGE:
                return context.getMessage("acknowledge");
            case CANCEL:
                return context.getMessage("cancel");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
        switch(this){
        case ACKNOWLEDGE:
            return ButtonStyle.PRIMARY;
        case CANCEL:
            return ButtonStyle.DEFAULT_ANCHOR;
        }
		return ButtonStyle.PRIMARY;
	}
    @Override
    public ButtonEnum getActionType() {
        return ACKNOWLEDGE;
    }
    @Override
    public ButtonEnum getCancelType() {
        return CANCEL;
    }	
    };
    
    public static enum RejectCancelEnum implements ButtonEnum{REJECT, CANCEL;

    @Override
    public String getText() {
        switch(this){
            case REJECT:
                return context.getMessage("reject");
            case CANCEL:
                return context.getMessage("cancel");
        }
        return null;  
    }
	@Override
	public ButtonStyle getStyle() {
        switch(this){
        case REJECT:
            return ButtonStyle.PRIMARY;
        case CANCEL:
            return ButtonStyle.DEFAULT_ANCHOR;
        }
		return ButtonStyle.PRIMARY;
	}
    @Override
    public ButtonEnum getActionType() {
        return REJECT;
    }
    @Override
    public ButtonEnum getCancelType() {
        return CANCEL;
    }	
    };
}
