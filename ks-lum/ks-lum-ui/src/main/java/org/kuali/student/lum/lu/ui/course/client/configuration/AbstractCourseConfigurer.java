package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.workflow.ui.client.widgets.ContentConfigurer;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseExpenditureInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseRevenueInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;

public abstract class AbstractCourseConfigurer extends org.kuali.student.common.ui.client.configurable.mvc.Configurer
    implements ContentConfigurer, 
    CreditCourseConstants,
    CreditCourseFormatConstants,
    CreditCourseActivityConstants,
    CreditCourseDurationConstants,
    FeeInfoConstants,
    AffiliatedOrgInfoConstants,
    CreditCourseRevenueInfoConstants,
    CreditCourseExpenditureInfoConstants
{
    public static final String CLU_PROPOSAL_MODEL               = "cluProposalModel";
    public static final String PROPOSAL_REFERENCE_TYPE_KEY      = "referenceType.clu.proposal";
    public static final String PROPOSAL_REFERENCE_OBJECT_TYPE   = "kuali.lu.type.CreditCourse";


    public String getModelId(){
        return CLU_PROPOSAL_MODEL;
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

    public abstract String getWorkflowDocumentType();

    public abstract String getSectionTitle(DataModel model);

    public abstract String getProposalHeaderTitle(DataModel model);

    public abstract Class<? extends Enum<?>> getViewsEnum();
}
