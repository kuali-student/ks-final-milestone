package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.kuali.student.r2.common.dto.BulkStatusInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by helium on 2014/04/09.
 */
public class ExamOfferingResult {

    private String key;
    private Map<String, String> context;
    private List<ExamOfferingResult> children;

    public ExamOfferingResult() {
        super();
        children = new ArrayList<ExamOfferingResult>();
    }

    public ExamOfferingResult(String key) {
        this();
        this.key = key;
        this.context = new HashMap<String, String>();
    }

    public ExamOfferingResult(String key, Map<String, String> context) {
        this();
        this.key = key;
        this.context = context;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public List<ExamOfferingResult> getChildren() {
        return children;
    }

    public void setChildren(List<ExamOfferingResult> children) {
        this.children = children;
    }

}
