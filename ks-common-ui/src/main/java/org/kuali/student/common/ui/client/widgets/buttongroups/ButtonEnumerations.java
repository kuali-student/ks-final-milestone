package org.kuali.student.common.ui.client.widgets.buttongroups;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;


public class ButtonEnumerations {
    public interface ButtonEnum{
        public String getText();
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
};
}
