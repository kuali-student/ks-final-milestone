package org.kuali.student.common.uif.element;

import org.kuali.rice.krad.uif.element.Action;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 7/10/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSUifActionButton extends Action {

    public static final String BUTTON_SIZE_DEFAULT = "default";
    public static final String BUTTON_SIZE_MINI = "mini";
    public static final String BUTTON_SIZE_SMALL = "small";
    public static final String BUTTON_SIZE_LARGE = "large";

    public static final String BUTTON_DEFAULT_CSS_CLASS = "btn";
    public static final String BUTTON_PRIMARY_CSS_CLASS = "btn-primary";
    public static final String BUTTON_INFO_CSS_CLASS = "btn-info";
    public static final String BUTTON_SUCCESS_CSS_CLASS = "btn-success";
    public static final String BUTTON_WARNING_CSS_CLASS = "btn-warning";
    public static final String BUTTON_DANGER_CSS_CLASS = "btn-danger";
    public static final String BUTTON_INVERSE_CSS_CLASS = "btn-inverse";
    public static final String BUTTON_LINK_CSS_CLASS = "btn-link";

    public static final String BUTTON_SIZE_MINI_CSS_CLASS = "btn-mini";
    public static final String BUTTON_SIZE_SMALL_CSS_CLASS = "btn-small";
    public static final String BUTTON_SIZE_LARGE_CSS_CLASS = "btn-large";

//    protected String buttonSize = BUTTON_SIZE_DEFAULT;
//
//    public String getButtonSize() {
//        return buttonSize;
//    }

    public void setButtonSize(String buttonSize) {
//        this.buttonSize = buttonSize;
        if (BUTTON_SIZE_MINI.equals(buttonSize)) {
            super.addStyleClass(BUTTON_SIZE_MINI_CSS_CLASS);
        } else if (BUTTON_SIZE_SMALL.equals(buttonSize)) {
            super.addStyleClass(BUTTON_SIZE_SMALL_CSS_CLASS);
        } else if (BUTTON_SIZE_LARGE.equals(buttonSize)) {
            super.addStyleClass(BUTTON_SIZE_LARGE_CSS_CLASS);
        }
    }
}
