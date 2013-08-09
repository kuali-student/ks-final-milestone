package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseExpenditureInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseRevenueInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.FeeInfoConstants;

public abstract class AbstractCourseConfigurer extends org.kuali.student.common.ui.client.configurable.mvc.Configurer implements
    CreditCourseConstants,
    CreditCourseFormatConstants,
    CreditCourseActivityConstants,
    CreditCourseDurationConstants,
    FeeInfoConstants,
    AffiliatedOrgInfoConstants,
    CreditCourseRevenueInfoConstants,
    CreditCourseExpenditureInfoConstants
{
	public static final String COURSE_PROPOSAL_MODEL			= "courseProposalModel";
    public static final String PROPOSAL_REFERENCE_TYPE_KEY      = "referenceType.clu.proposal";
    public static final String PROPOSAL_REFERENCE_OBJECT_TYPE   = "kuali.lu.type.CreditCourse";


    public String getModelId(){
        return COURSE_PROPOSAL_MODEL;
    };

    public String getProposalReferenceTypeKey(){
        return PROPOSAL_REFERENCE_TYPE_KEY;
    }

    public String getProposalReferenceObjectType(){
        return PROPOSAL_REFERENCE_OBJECT_TYPE;
    }

    public abstract String getProposalPath();

    public abstract String getProposalTitlePath();

    public abstract String getCourseTitlePath();

    public abstract String getSectionTitle(DataModel model);

    public abstract String getProposalHeaderTitle(DataModel model);

    public abstract Class<? extends Enum<?>> getViewsEnum();
}
