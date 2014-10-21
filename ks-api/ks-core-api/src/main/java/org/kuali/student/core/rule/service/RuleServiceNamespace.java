package org.kuali.student.core.rule.service;

import org.kuali.student.core.rule.dto.RuleInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * This class holds the constants used by the Rule service
 */
public class RuleServiceNamespace {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "rule";
    public static final String REF_OBJECT_URI_RULE = NAMESPACE + "/" + RuleInfo.class.getSimpleName();
    public static final String SERVICE_NAME_LOCAL_PART = "RuleService";
}
