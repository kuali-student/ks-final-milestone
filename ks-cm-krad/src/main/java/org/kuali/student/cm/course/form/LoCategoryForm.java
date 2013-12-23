package org.kuali.student.cm.course.form;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

public class LoCategoryForm extends UifFormBase {

    private List<LoCategoryInfo> loCategories;

    public List<LoCategoryInfo> getLoCategories() {
        if (loCategories == null) {
            loCategories = new ArrayList<LoCategoryInfo>(0);
        }
        return loCategories;
    }

    public void setLoCategories(List<LoCategoryInfo> loCategories) {
        this.loCategories = loCategories;
    }

}
