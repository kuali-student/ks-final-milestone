package org.kuali.student.common.uif.element;

import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.widget.Inquiry;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 7/10/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
// TODO: KSENROLL-8478 temporarily overriding KRAD's Inquiry as it was messing up the hover over. should be resolved in Rice 2.3.1

public class KSUifInquiry extends Inquiry {

    private String title;


    public void buildInquiryLink(Object dataObject, String propertyName, Class<?> inquiryObjectClass,
                                 Map<String, String> inquiryParams) {
        super.buildInquiryLink(dataObject, propertyName, inquiryObjectClass, inquiryParams);
        getInquiryLink().setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
