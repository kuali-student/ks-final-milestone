package org.kuali.student.r2.core.class1.state.service;

import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface RelatedObjectHelper {
    public List<String> getRelatedObjectIds(String entityId, ContextInfo contextInfo);
}
