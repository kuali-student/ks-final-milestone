package org.kuali.student.common.ui.client.widgets.buttongroups;

public class ButtonEnumerations {
    public interface ButtonEnum{
        public String getText();
    };
    //TODO use messages service instead of hardcoded strings
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
                    return "Yes";
                case NO:
                    return "No";
            }
            return null;  
        }
    };
    
    public static enum ConfirmCancelEnum implements ButtonEnum{CONFIRM, CANCEL;

        @Override
        public String getText() {
            switch(this){
                case CONFIRM:
                    return "Confirm";
                case CANCEL:
                    return "Cancel";
            }
            return null;  
        }
    };
        
    public static enum YesNoCancelEnum implements ButtonEnum{YES, NO, CANCEL;

        @Override
        public String getText() {
            switch(this){
                case YES:
                    return "Yes";
                case NO:
                    return "No";
                case CANCEL:
                    return "Cancel";
            }
            return null;  
        }
    };
    
    public static enum AddModifyRemoveEnum implements ButtonEnum{ADD, MODIFY, REMOVE;

        @Override
        public String getText() {
            switch(this){
                case ADD:
                    return "Add";
                case MODIFY:
                    return "Modify";
                case REMOVE:
                    return "Remove";
            }
            return null;  
        }
    };
}
