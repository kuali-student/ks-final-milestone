package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationRequest;
import org.kuali.student.enrollment.lpr.infc.RequestOption;
import org.kuali.student.r2.common.dto.IdEntityInfo;


public class LuiPersonRelationRequestInfo extends IdEntityInfo implements LuiPersonRelationRequest, Serializable {

    private static final long serialVersionUID = 1L;

    private String newLuiId;
    
    private String existingLuiId;
    
    private List <RequestOption> requestOptions;
    
    @Override
    public String getNewLuiId() {
        return newLuiId;
    }

    @Override
    public String getExistingLuiId() {
        return existingLuiId;
    }


    @Override
    public List<RequestOption> requestOptions() {
        return requestOptions;
    }


}
