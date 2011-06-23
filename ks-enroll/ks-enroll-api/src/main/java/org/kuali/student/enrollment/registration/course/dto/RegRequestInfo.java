package org.kuali.student.enrollment.registration.course.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.enrollment.registration.course.infc.RegRequest;
import org.kuali.student.enrollment.registration.course.infc.RegRequestItem;
import org.kuali.student.enrollment.registration.course.infc.RegResponse;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;

public class RegRequestInfo extends IdEntityInfo implements RegRequest, Serializable {

    private static final long serialVersionUID = 1L;

    private String requestorId;

    private String studentId;

    private String termKey;

    private RegResponse regResponse;

    private List<RegRequestItem> regRequestItems;

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }

    public void setRegResponse(RegResponse regResponse) {
        this.regResponse = regResponse;
    }

    public void setRegRequestItems(List<RegRequestItem> regRequestItems) {
        this.regRequestItems = regRequestItems;
    }

    @Override
    public String getRequestorId() {
        return requestorId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public String getTermKey() {

        return termKey;
    }

    @Override
    public List<RegRequestItem> getRegRequestItems() {
        return regRequestItems;
    }

    @Override
    public RegResponse getRegResponse() {
        return regResponse;
    }

}
