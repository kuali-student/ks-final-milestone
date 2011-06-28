package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

public class OperationStatusInfo implements Serializable {
    
    private String overallRegStatus;

    private List<String> messages;

    private List<String> warnings;

    private List<String> errors;
    
    
    
}
