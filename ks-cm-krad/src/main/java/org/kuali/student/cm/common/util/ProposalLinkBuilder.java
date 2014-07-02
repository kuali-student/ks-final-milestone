package org.kuali.student.cm.common.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.student.cm.course.form.CourseInfoWrapper;

import java.util.Properties;

/**
 * A utility class for building URLs for proposals.
 */
public class ProposalLinkBuilder {

    /**
     * Hide the constructor since this class has all static methods
     */
    private ProposalLinkBuilder() {}

    /**
     * Constructs a text URL for a particular course proposal.
     */
    public static String buildCourseProposalUrl(String methodToCall, String pageId, String workflowDocId) {
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(KRADConstants.PARAMETER_COMMAND, KRADConstants.METHOD_DISPLAY_DOC_SEARCH_VIEW);
        props.put(KRADConstants.RETURN_LOCATION_PARAMETER, "cmHome?methodToCall=start&viewId=curriculumHomeView");

        props.put(UifParameters.DATA_OBJECT_CLASS_NAME, CourseInfoWrapper.class.getCanonicalName());
        if (StringUtils.isNotBlank(pageId)) {
            props.put(UifParameters.PAGE_ID, pageId);
        }
        props.put(KRADConstants.PARAMETER_DOC_ID, workflowDocId);

        String courseBaseUrl = CurriculumManagementConstants.ControllerRequestMappings.CREATE_COURSE.replaceFirst("/", "");
        return UrlFactory.parameterizeUrl(courseBaseUrl, props);
    }
}
