package org.kuali.student.enrollment.class1.state.StateHelper;

import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface RelatedObjectHelper {
    public List<String> getRelatedObjectIds(String entityId, ContextInfo contextInfo);
}
