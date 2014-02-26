package org.kuali.student.ap.i18n.form;

import org.kuali.rice.krad.web.form.UifFormBase;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/10/14
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class POCResourceBundleFormImpl extends UifFormBase {
    private String locale;

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
