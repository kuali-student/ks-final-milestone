package org.kuali.student.enrollment.uif.view;

import org.kuali.rice.krad.uif.view.LookupView;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 6/20/12
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSLookupView extends LookupView{

    protected String defaultSingleLookupResultAction;

    public String getDefaultSingleLookupResultAction() {
        return defaultSingleLookupResultAction;
    }

    public void setDefaultSingleLookupResultAction(String defaultSingleLookupResultAction) {
        this.defaultSingleLookupResultAction = defaultSingleLookupResultAction;
    }
}
