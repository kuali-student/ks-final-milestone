/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.permission.mock;

/**
 *
 * @author nwright
 */
public class KimPermissionConstants {

    public static final String PERMISSION_SERVICE_NAME_LOCAL_PART = "permissionService";
    public static final String PERMISSION_SERVICE_NAMESPACE = "http://rice.kuali.org/kim/v2_0";
    /**
     * Kim Type IDs
     * These are used to control the comparison of qualifiers
     * The default one does a simple match, i.e. no hierarchy checks
     */
    public static final String DEFAULT_KIM_TYPE_ID = "0";
    public static final String KRAD_VIEW_KIM_TYPE_ID = "68";
    
    /**
     * Namespaces
     */
    public static final String KS_ENRL_NAMESPACE = "KS-ENRL";
    public static final String KS_SYS_NAMESPACE = "KS-SYS";
    public static final String KS_LUM_NAMESPACE = "KS-CM";
    public static final String KUALI_NAMESPACE = "KUALI";
    public static final String KR_BUS_NAMESPACE = "KR-BUS";
    public static final String KR_IDM_NAMESPACE = "KR-IDM";
    public static final String KR_KRAD_NAMESPACE = "KR-KRAD";
    public static final String KR_NS_NAMESPACE = "KR-NS";
    public static final String KR_NTFCN_NAMESPACE = "KR-NTFCN";
    public static final String KR_RULE_NAMESPACE = "KR-RULE";
    public static final String KR_RULE_TEST_NAMESPACE = "KR-RULE-TEST";
    public static final String KR_SYS_NAMESPACE = "KR-SYS";
    public static final String KR_WORKFLOW_NAMESPACE = "KR-WKFLW";
    
    /**
     * Attribute definition
     */
    public static final String SUBJECT_AREA_ATTR_DEFINITION = "subjectArea";
    public static final String DEPARTMENT_ATTR_DEFINITION = "department";
    public static final String VIEW_ID_ATTR_DEFINITION = "viewId";
    /**
     * Permissions
     */
    public static final String CREATE_COURSEOFFERING_PERMISSION = "Create CourseOffering";
    public static final String READ_COURSEOFFERING_PERMISSION = "Read CourseOffering";
    public static final String UPDATE_COURSEOFFERING_PERMISSION = "Update CourseOffering";
    public static final String DELETE_COURSEOFFERING_PERMISSION = "Delete CourseOffering";
    public static final String CREATE_FORMATOFFERING_PERMISSION = "Create FormatOffering";
    public static final String READ_FORMATOFFERING_PERMISSION = "Read FormatOffering";
    public static final String UPDATE_FORMATOFFERING_PERMISSION = "Update FormatOffering";
    public static final String DELETE_FORMATOFFERING_PERMISSION = "Delete FormatOffering";
    public static final String CREATE_ACTIVITYOFFERING_PERMISSION = "Create ActivityOffering";
    public static final String READ_ACTIVITYOFFERING_PERMISSION = "Read ActivityOffering";
    public static final String UPDATE_ACTIVITYOFFERING_PERMISSION = "Update ActivityOffering";
    public static final String DELETE_ACTIVITYOFFERING_PERMISSION = "Delete ActivityOffering";
    public static final String CREATE_ACTIVITYOFFERINGCLUSTER_PERMISSION = "Create ActivityOfferingCluster";
    public static final String READ_ACTIVITYOFFERINGCLUSTER_PERMISSION = "Read ActivityOfferingCluster";
    public static final String UPDATE_ACTIVITYOFFERINGCLUSTER_PERMISSION = "Update ActivityOfferingCluster";
    public static final String DELETE_ACTIVITYOFFERINGCLUSTER_PERMISSION = "Delete ActivityOfferingCluster";
    public static final String CREATE_REGISTRATIONGROUP_PERMISSION = "Create RegistrationGroup";
    public static final String READ_REGISTRATIONGROUP_PERMISSION = "Read RegistrationGroup";
    public static final String UPDATE_REGISTRATIONGROUP_PERMISSION = "Update RegistrationGroup";
    public static final String DELETE_REGISTRATIONGROUP_PERMISSION = "Delete RegistrationGroup";
    public static final String CREATE_SOC_PERMISSION = "Create Soc";
    public static final String READ_SOC_PERMISSION = "Read Soc";
    public static final String UPDATE_SOC_PERMISSION = "Update Soc";
    public static final String DELETE_SOC_PERMISSION = "Delete Soc";
    public static final String ROLLOVER_SOC_PERMISSION = "rollover Soc";
    public static final String CREATE_SOCROLLOVERRESULT_PERMISSION = "Create SocRolloverResult";
    public static final String READ_SOCROLLOVERRESULT_PERMISSION = "Read SocRolloverResult";
    public static final String UPDATE_SOCROLLOVERRESULT_PERMISSION = "Update SocRolloverResult";
    public static final String DELETE_SOCROLLOVERRESULT_PERMISSION = "Delete SocRolloverResult";
    public static final String CREATE_SOCROLLOVERRESULTITEM_PERMISSION = "Create SocRolloverResultItem";
    public static final String READ_SOCROLLOVERRESULTITEM_PERMISSION = "Read SocRolloverResultItem";
    public static final String UPDATE_SOCROLLOVERRESULTITEM_PERMISSION = "Update SocRolloverResultItem";
    public static final String DELETE_SOCROLLOVERRESULTITEM_PERMISSION = "Delete SocRolloverResultItem";
    // krad permissions
    public static final String OPEN_VIEWS_FOR_COURSE_OFFERING_PERMISSION = "Open Views for Course Offering";
    public static final String EDIT_VIEWS_FOR_COURSE_OFFERING_PERMISSION = "Edit Views for Course Offering";
    public static final String OPEN_VIEWS_FOR_SOC_PERMISSION = "Open Views for SOC";
    public static final String EDIT_VIEWS_FOR_SOC_PERMISSION = "Edit Views for SOC";
    
    /**
     * permission templates
     */
    public static final String CAN_INVOKE_SERVICE_METHOD_TEMPLATE_NAME = "Can Invoke Service Method";
    public static final String OPEN_VIEW_TEMPLATE_NAME = "Open View";
    public static final String EDIT_VIEW_TEMPLATE_NAME = "Edit View";
    public static final String USE_VIEW_TEMPLATE_NAME = "Use View";
    public static final String VIEW_FIELD_TEMPLATE_NAME = "View Field";
    public static final String EDIT_FIELD_TEMPLATE_NAME = "Edit Field";
    public static final String VIEW_GROUP_TEMPLATE_NAME = "View Group";
    public static final String EDIT_GROUP_TEMPLATE_NAME = "Edit Group";
    public static final String VIEW_WIDGET_TEMPLATE_NAME = "View Widget";
    public static final String EDIT_WIDGET_TEMPLATE_NAME = "Edit Widget";
    public static final String PERFORM_ACTION_TEMPLATE_NAME = "Perform Action";
    public static final String VIEW_LINE_TEMPLATE_NAME = "View Line";
    public static final String EDIT_LINE_TEMPLATE_NAME = "Edit Line";
    public static final String VIEW_LINE_FIELD_TEMPLATE_NAME = "View Line Field";
    public static final String EDIT_LINE_FIELD_TEMPLATE_NAME = "Edit Line Field";
    public static final String PERFORM_LINE_ACTION_TEMPLATE_NAME = "Perform Line Action";
    
    /**
     * Role Names
     */
    public static final String KUALI_STUDENT_COURSE_OFFERING_CENTRAL_ADMIN_ROLE_NAME = "Kuali Student Course Offering Central Admin";
    public static final String KUALI_STUDENT_COURSE_OFFERING_DEPARTMENTAL_ADMIN_ROLE_NAME = "Kuali Student Course Offering Departmental Admin";
}
