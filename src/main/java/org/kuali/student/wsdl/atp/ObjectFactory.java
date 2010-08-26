
package org.kuali.student.wsdl.atp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.exceptions.AlreadyExistsException;
import org.kuali.student.wsdl.exceptions.DataValidationErrorException;
import org.kuali.student.wsdl.exceptions.DoesNotExistException;
import org.kuali.student.wsdl.exceptions.InvalidParameterException;
import org.kuali.student.wsdl.exceptions.MissingParameterException;
import org.kuali.student.wsdl.exceptions.OperationFailedException;
import org.kuali.student.wsdl.exceptions.PermissionDeniedException;
import org.kuali.student.wsdl.exceptions.VersionMismatchException;
import org.kuali.student.wsdl.organization.Search;
import org.kuali.student.wsdl.organization.SearchResponse;
import org.kuali.student.wsdl.search.GetSearchCriteriaType;
import org.kuali.student.wsdl.search.GetSearchCriteriaTypeResponse;
import org.kuali.student.wsdl.search.GetSearchCriteriaTypes;
import org.kuali.student.wsdl.search.GetSearchCriteriaTypesResponse;
import org.kuali.student.wsdl.search.GetSearchResultType;
import org.kuali.student.wsdl.search.GetSearchResultTypeResponse;
import org.kuali.student.wsdl.search.GetSearchResultTypes;
import org.kuali.student.wsdl.search.GetSearchResultTypesResponse;
import org.kuali.student.wsdl.search.GetSearchType;
import org.kuali.student.wsdl.search.GetSearchTypeResponse;
import org.kuali.student.wsdl.search.GetSearchTypes;
import org.kuali.student.wsdl.search.GetSearchTypesByCriteria;
import org.kuali.student.wsdl.search.GetSearchTypesByCriteriaResponse;
import org.kuali.student.wsdl.search.GetSearchTypesByResult;
import org.kuali.student.wsdl.search.GetSearchTypesByResultResponse;
import org.kuali.student.wsdl.search.GetSearchTypesResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.atp package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAtpsByDate_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpsByDate");
    private final static QName _Search_QNAME = new QName("http://student.kuali.org/wsdl/atp", "search");
    private final static QName _GetSearchCriteriaTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchCriteriaTypeResponse");
    private final static QName _GetSearchResultTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchResultTypesResponse");
    private final static QName _GetAtpDurationTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpDurationTypeResponse");
    private final static QName _GetSearchResultType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchResultType");
    private final static QName _DoesNotExistException_QNAME = new QName("http://student.kuali.org/wsdl/atp", "DoesNotExistException");
    private final static QName _UpdateMilestone_QNAME = new QName("http://student.kuali.org/wsdl/atp", "updateMilestone");
    private final static QName _GetAtpSeasonalType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpSeasonalType");
    private final static QName _GetSearchResultTypes_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchResultTypes");
    private final static QName _GetMilestonesByDatesAndTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestonesByDatesAndTypeResponse");
    private final static QName _DeleteAtp_QNAME = new QName("http://student.kuali.org/wsdl/atp", "deleteAtp");
    private final static QName _GetAtpSeasonalTypes_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpSeasonalTypes");
    private final static QName _GetAtpsByAtpTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpsByAtpTypeResponse");
    private final static QName _GetDateRangeTypesForAtpTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangeTypesForAtpTypeResponse");
    private final static QName _GetMilestoneTypesForAtpTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestoneTypesForAtpTypeResponse");
    private final static QName _GetDateRangeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangeResponse");
    private final static QName _ValidateDateRangeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "validateDateRangeResponse");
    private final static QName _GetSearchTypesByResultResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchTypesByResultResponse");
    private final static QName _GetSearchTypesByCriteria_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchTypesByCriteria");
    private final static QName _GetDateRangesByDate_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangesByDate");
    private final static QName _GetAtpResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpResponse");
    private final static QName _GetSearchTypesByResult_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchTypesByResult");
    private final static QName _GetSearchCriteriaTypes_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchCriteriaTypes");
    private final static QName _GetSearchCriteriaType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchCriteriaType");
    private final static QName _OperationFailedException_QNAME = new QName("http://student.kuali.org/wsdl/atp", "OperationFailedException");
    private final static QName _VersionMismatchException_QNAME = new QName("http://student.kuali.org/wsdl/atp", "VersionMismatchException");
    private final static QName _AlreadyExistsException_QNAME = new QName("http://student.kuali.org/wsdl/atp", "AlreadyExistsException");
    private final static QName _GetDateRangeTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangeTypeResponse");
    private final static QName _GetMilestoneResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestoneResponse");
    private final static QName _GetMilestoneTypesForAtpType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestoneTypesForAtpType");
    private final static QName _GetMilestone_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestone");
    private final static QName _GetAtpTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpTypesResponse");
    private final static QName _AddDateRange_QNAME = new QName("http://student.kuali.org/wsdl/atp", "addDateRange");
    private final static QName _UpdateAtp_QNAME = new QName("http://student.kuali.org/wsdl/atp", "updateAtp");
    private final static QName _RemoveMilestone_QNAME = new QName("http://student.kuali.org/wsdl/atp", "removeMilestone");
    private final static QName _GetAtpType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpType");
    private final static QName _GetAtpsByDateResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpsByDateResponse");
    private final static QName _GetDateRangeTypes_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangeTypes");
    private final static QName _GetMilestoneTypes_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestoneTypes");
    private final static QName _GetSearchTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchTypesResponse");
    private final static QName _RemoveDateRangeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "removeDateRangeResponse");
    private final static QName _DeleteAtpResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "deleteAtpResponse");
    private final static QName _CreateAtpResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "createAtpResponse");
    private final static QName _UpdateDateRange_QNAME = new QName("http://student.kuali.org/wsdl/atp", "updateDateRange");
    private final static QName _GetAtpTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpTypeResponse");
    private final static QName _GetDateRange_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRange");
    private final static QName _MissingParameterException_QNAME = new QName("http://student.kuali.org/wsdl/atp", "MissingParameterException");
    private final static QName _ValidateDateRange_QNAME = new QName("http://student.kuali.org/wsdl/atp", "validateDateRange");
    private final static QName _GetSearchResultTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchResultTypeResponse");
    private final static QName _AddDateRangeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "addDateRangeResponse");
    private final static QName _GetAtpsByDates_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpsByDates");
    private final static QName _ValidateAtp_QNAME = new QName("http://student.kuali.org/wsdl/atp", "validateAtp");
    private final static QName _GetSearchCriteriaTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchCriteriaTypesResponse");
    private final static QName _GetMilestonesByAtpResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestonesByAtpResponse");
    private final static QName _AddMilestone_QNAME = new QName("http://student.kuali.org/wsdl/atp", "addMilestone");
    private final static QName _GetDateRangeType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangeType");
    private final static QName _RemoveMilestoneResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "removeMilestoneResponse");
    private final static QName _AddMilestoneResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "addMilestoneResponse");
    private final static QName _GetDateRangeTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangeTypesResponse");
    private final static QName _GetAtpDurationTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpDurationTypesResponse");
    private final static QName _UpdateDateRangeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "updateDateRangeResponse");
    private final static QName _RemoveDateRange_QNAME = new QName("http://student.kuali.org/wsdl/atp", "removeDateRange");
    private final static QName _CreateAtp_QNAME = new QName("http://student.kuali.org/wsdl/atp", "createAtp");
    private final static QName _GetMilestoneType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestoneType");
    private final static QName _GetAtpsByDatesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpsByDatesResponse");
    private final static QName _GetDateRangesByAtpResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangesByAtpResponse");
    private final static QName _InvalidParameterException_QNAME = new QName("http://student.kuali.org/wsdl/atp", "InvalidParameterException");
    private final static QName _GetDateRangeTypesForAtpType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangeTypesForAtpType");
    private final static QName _GetMilestoneTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestoneTypesResponse");
    private final static QName _GetMilestoneTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestoneTypeResponse");
    private final static QName _GetSearchTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchTypeResponse");
    private final static QName _GetAtpsByAtpType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpsByAtpType");
    private final static QName _DataValidationErrorException_QNAME = new QName("http://student.kuali.org/wsdl/atp", "DataValidationErrorException");
    private final static QName _GetAtpTypes_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpTypes");
    private final static QName _GetDateRangesByDateResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangesByDateResponse");
    private final static QName _PermissionDeniedException_QNAME = new QName("http://student.kuali.org/wsdl/atp", "PermissionDeniedException");
    private final static QName _GetSearchType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchType");
    private final static QName _GetMilestonesByAtp_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestonesByAtp");
    private final static QName _GetMilestonesByDatesAndType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestonesByDatesAndType");
    private final static QName _UpdateAtpResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "updateAtpResponse");
    private final static QName _GetAtp_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtp");
    private final static QName _ValidateMilestone_QNAME = new QName("http://student.kuali.org/wsdl/atp", "validateMilestone");
    private final static QName _GetMilestonesByDates_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestonesByDates");
    private final static QName _UpdateMilestoneResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "updateMilestoneResponse");
    private final static QName _GetAtpDurationType_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpDurationType");
    private final static QName _GetAtpDurationTypes_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpDurationTypes");
    private final static QName _ValidateAtpResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "validateAtpResponse");
    private final static QName _GetAtpSeasonalTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpSeasonalTypesResponse");
    private final static QName _ValidateMilestoneResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "validateMilestoneResponse");
    private final static QName _GetAtpSeasonalTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getAtpSeasonalTypeResponse");
    private final static QName _GetMilestonesByDatesResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getMilestonesByDatesResponse");
    private final static QName _GetSearchTypesByCriteriaResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchTypesByCriteriaResponse");
    private final static QName _GetSearchTypes_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getSearchTypes");
    private final static QName _SearchResponse_QNAME = new QName("http://student.kuali.org/wsdl/atp", "searchResponse");
    private final static QName _GetDateRangesByAtp_QNAME = new QName("http://student.kuali.org/wsdl/atp", "getDateRangesByAtp");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.atp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateAtpResponse }
     * 
     */
    public CreateAtpResponse createCreateAtpResponse() {
        return new CreateAtpResponse();
    }

    /**
     * Create an instance of {@link GetDateRangeTypesForAtpType }
     * 
     */
    public GetDateRangeTypesForAtpType createGetDateRangeTypesForAtpType() {
        return new GetDateRangeTypesForAtpType();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link MilestoneInfo }
     * 
     */
    public MilestoneInfo createMilestoneInfo() {
        return new MilestoneInfo();
    }

    /**
     * Create an instance of {@link GetDateRangeType }
     * 
     */
    public GetDateRangeType createGetDateRangeType() {
        return new GetDateRangeType();
    }

    /**
     * Create an instance of {@link GetAtpSeasonalType }
     * 
     */
    public GetAtpSeasonalType createGetAtpSeasonalType() {
        return new GetAtpSeasonalType();
    }

    /**
     * Create an instance of {@link DeleteAtp }
     * 
     */
    public DeleteAtp createDeleteAtp() {
        return new DeleteAtp();
    }

    /**
     * Create an instance of {@link AddDateRangeResponse }
     * 
     */
    public AddDateRangeResponse createAddDateRangeResponse() {
        return new AddDateRangeResponse();
    }

    /**
     * Create an instance of {@link ValidateMilestone }
     * 
     */
    public ValidateMilestone createValidateMilestone() {
        return new ValidateMilestone();
    }

    /**
     * Create an instance of {@link DeleteAtpResponse }
     * 
     */
    public DeleteAtpResponse createDeleteAtpResponse() {
        return new DeleteAtpResponse();
    }

    /**
     * Create an instance of {@link MetaInfo }
     * 
     */
    public MetaInfo createMetaInfo() {
        return new MetaInfo();
    }

    /**
     * Create an instance of {@link ValidateDateRange }
     * 
     */
    public ValidateDateRange createValidateDateRange() {
        return new ValidateDateRange();
    }

    /**
     * Create an instance of {@link GetMilestonesByDatesAndType }
     * 
     */
    public GetMilestonesByDatesAndType createGetMilestonesByDatesAndType() {
        return new GetMilestonesByDatesAndType();
    }

    /**
     * Create an instance of {@link GetDateRangeTypeResponse }
     * 
     */
    public GetDateRangeTypeResponse createGetDateRangeTypeResponse() {
        return new GetDateRangeTypeResponse();
    }

    /**
     * Create an instance of {@link GetAtp }
     * 
     */
    public GetAtp createGetAtp() {
        return new GetAtp();
    }

    /**
     * Create an instance of {@link GetAtpTypesResponse }
     * 
     */
    public GetAtpTypesResponse createGetAtpTypesResponse() {
        return new GetAtpTypesResponse();
    }

    /**
     * Create an instance of {@link GetAtpsByAtpType }
     * 
     */
    public GetAtpsByAtpType createGetAtpsByAtpType() {
        return new GetAtpsByAtpType();
    }

    /**
     * Create an instance of {@link GetDateRange }
     * 
     */
    public GetDateRange createGetDateRange() {
        return new GetDateRange();
    }

    /**
     * Create an instance of {@link CreateAtp }
     * 
     */
    public CreateAtp createCreateAtp() {
        return new CreateAtp();
    }

    /**
     * Create an instance of {@link GetAtpDurationTypesResponse }
     * 
     */
    public GetAtpDurationTypesResponse createGetAtpDurationTypesResponse() {
        return new GetAtpDurationTypesResponse();
    }

    /**
     * Create an instance of {@link AddMilestoneResponse }
     * 
     */
    public AddMilestoneResponse createAddMilestoneResponse() {
        return new AddMilestoneResponse();
    }

    /**
     * Create an instance of {@link AtpInfo }
     * 
     */
    public AtpInfo createAtpInfo() {
        return new AtpInfo();
    }

    /**
     * Create an instance of {@link UpdateDateRange }
     * 
     */
    public UpdateDateRange createUpdateDateRange() {
        return new UpdateDateRange();
    }

    /**
     * Create an instance of {@link ValidateDateRangeResponse }
     * 
     */
    public ValidateDateRangeResponse createValidateDateRangeResponse() {
        return new ValidateDateRangeResponse();
    }

    /**
     * Create an instance of {@link GetAtpDurationType }
     * 
     */
    public GetAtpDurationType createGetAtpDurationType() {
        return new GetAtpDurationType();
    }

    /**
     * Create an instance of {@link GetAtpResponse }
     * 
     */
    public GetAtpResponse createGetAtpResponse() {
        return new GetAtpResponse();
    }

    /**
     * Create an instance of {@link GetMilestoneTypesForAtpTypeResponse }
     * 
     */
    public GetMilestoneTypesForAtpTypeResponse createGetMilestoneTypesForAtpTypeResponse() {
        return new GetMilestoneTypesForAtpTypeResponse();
    }

    /**
     * Create an instance of {@link GetDateRangesByAtpResponse }
     * 
     */
    public GetDateRangesByAtpResponse createGetDateRangesByAtpResponse() {
        return new GetDateRangesByAtpResponse();
    }

    /**
     * Create an instance of {@link UpdateMilestoneResponse }
     * 
     */
    public UpdateMilestoneResponse createUpdateMilestoneResponse() {
        return new UpdateMilestoneResponse();
    }

    /**
     * Create an instance of {@link GetDateRangeTypesResponse }
     * 
     */
    public GetDateRangeTypesResponse createGetDateRangeTypesResponse() {
        return new GetDateRangeTypesResponse();
    }

    /**
     * Create an instance of {@link RichTextInfo }
     * 
     */
    public RichTextInfo createRichTextInfo() {
        return new RichTextInfo();
    }

    /**
     * Create an instance of {@link StatusInfo }
     * 
     */
    public StatusInfo createStatusInfo() {
        return new StatusInfo();
    }

    /**
     * Create an instance of {@link GetAtpsByAtpTypeResponse }
     * 
     */
    public GetAtpsByAtpTypeResponse createGetAtpsByAtpTypeResponse() {
        return new GetAtpsByAtpTypeResponse();
    }

    /**
     * Create an instance of {@link GetAtpsByDates }
     * 
     */
    public GetAtpsByDates createGetAtpsByDates() {
        return new GetAtpsByDates();
    }

    /**
     * Create an instance of {@link AddDateRange }
     * 
     */
    public AddDateRange createAddDateRange() {
        return new AddDateRange();
    }

    /**
     * Create an instance of {@link RemoveMilestoneResponse }
     * 
     */
    public RemoveMilestoneResponse createRemoveMilestoneResponse() {
        return new RemoveMilestoneResponse();
    }

    /**
     * Create an instance of {@link ValidateAtpResponse }
     * 
     */
    public ValidateAtpResponse createValidateAtpResponse() {
        return new ValidateAtpResponse();
    }

    /**
     * Create an instance of {@link GetMilestoneTypeResponse }
     * 
     */
    public GetMilestoneTypeResponse createGetMilestoneTypeResponse() {
        return new GetMilestoneTypeResponse();
    }

    /**
     * Create an instance of {@link GetMilestoneTypesForAtpType }
     * 
     */
    public GetMilestoneTypesForAtpType createGetMilestoneTypesForAtpType() {
        return new GetMilestoneTypesForAtpType();
    }

    /**
     * Create an instance of {@link GetAtpsByDateResponse }
     * 
     */
    public GetAtpsByDateResponse createGetAtpsByDateResponse() {
        return new GetAtpsByDateResponse();
    }

    /**
     * Create an instance of {@link GetAtpsByDate }
     * 
     */
    public GetAtpsByDate createGetAtpsByDate() {
        return new GetAtpsByDate();
    }

    /**
     * Create an instance of {@link GetMilestoneTypes }
     * 
     */
    public GetMilestoneTypes createGetMilestoneTypes() {
        return new GetMilestoneTypes();
    }

    /**
     * Create an instance of {@link DateRangeTypeInfo }
     * 
     */
    public DateRangeTypeInfo createDateRangeTypeInfo() {
        return new DateRangeTypeInfo();
    }

    /**
     * Create an instance of {@link UpdateAtpResponse }
     * 
     */
    public UpdateAtpResponse createUpdateAtpResponse() {
        return new UpdateAtpResponse();
    }

    /**
     * Create an instance of {@link GetMilestonesByAtpResponse }
     * 
     */
    public GetMilestonesByAtpResponse createGetMilestonesByAtpResponse() {
        return new GetMilestonesByAtpResponse();
    }

    /**
     * Create an instance of {@link GetMilestonesByDates }
     * 
     */
    public GetMilestonesByDates createGetMilestonesByDates() {
        return new GetMilestonesByDates();
    }

    /**
     * Create an instance of {@link GetAtpSeasonalTypesResponse }
     * 
     */
    public GetAtpSeasonalTypesResponse createGetAtpSeasonalTypesResponse() {
        return new GetAtpSeasonalTypesResponse();
    }

    /**
     * Create an instance of {@link GetDateRangeResponse }
     * 
     */
    public GetDateRangeResponse createGetDateRangeResponse() {
        return new GetDateRangeResponse();
    }

    /**
     * Create an instance of {@link GetAtpType }
     * 
     */
    public GetAtpType createGetAtpType() {
        return new GetAtpType();
    }

    /**
     * Create an instance of {@link GetMilestonesByDatesResponse }
     * 
     */
    public GetMilestonesByDatesResponse createGetMilestonesByDatesResponse() {
        return new GetMilestonesByDatesResponse();
    }

    /**
     * Create an instance of {@link GetDateRangeTypesForAtpTypeResponse }
     * 
     */
    public GetDateRangeTypesForAtpTypeResponse createGetDateRangeTypesForAtpTypeResponse() {
        return new GetDateRangeTypesForAtpTypeResponse();
    }

    /**
     * Create an instance of {@link GetDateRangeTypes }
     * 
     */
    public GetDateRangeTypes createGetDateRangeTypes() {
        return new GetDateRangeTypes();
    }

    /**
     * Create an instance of {@link AtpTypeInfo }
     * 
     */
    public AtpTypeInfo createAtpTypeInfo() {
        return new AtpTypeInfo();
    }

    /**
     * Create an instance of {@link GetAtpDurationTypes }
     * 
     */
    public GetAtpDurationTypes createGetAtpDurationTypes() {
        return new GetAtpDurationTypes();
    }

    /**
     * Create an instance of {@link AtpDurationTypeInfo }
     * 
     */
    public AtpDurationTypeInfo createAtpDurationTypeInfo() {
        return new AtpDurationTypeInfo();
    }

    /**
     * Create an instance of {@link ValidateAtp }
     * 
     */
    public ValidateAtp createValidateAtp() {
        return new ValidateAtp();
    }

    /**
     * Create an instance of {@link ValidationResultInfo }
     * 
     */
    public ValidationResultInfo createValidationResultInfo() {
        return new ValidationResultInfo();
    }

    /**
     * Create an instance of {@link JaxbAttributeList }
     * 
     */
    public JaxbAttributeList createJaxbAttributeList() {
        return new JaxbAttributeList();
    }

    /**
     * Create an instance of {@link GetAtpDurationTypeResponse }
     * 
     */
    public GetAtpDurationTypeResponse createGetAtpDurationTypeResponse() {
        return new GetAtpDurationTypeResponse();
    }

    /**
     * Create an instance of {@link GetMilestone }
     * 
     */
    public GetMilestone createGetMilestone() {
        return new GetMilestone();
    }

    /**
     * Create an instance of {@link UpdateDateRangeResponse }
     * 
     */
    public UpdateDateRangeResponse createUpdateDateRangeResponse() {
        return new UpdateDateRangeResponse();
    }

    /**
     * Create an instance of {@link GetMilestonesByDatesAndTypeResponse }
     * 
     */
    public GetMilestonesByDatesAndTypeResponse createGetMilestonesByDatesAndTypeResponse() {
        return new GetMilestonesByDatesAndTypeResponse();
    }

    /**
     * Create an instance of {@link GetMilestoneType }
     * 
     */
    public GetMilestoneType createGetMilestoneType() {
        return new GetMilestoneType();
    }

    /**
     * Create an instance of {@link GetMilestonesByAtp }
     * 
     */
    public GetMilestonesByAtp createGetMilestonesByAtp() {
        return new GetMilestonesByAtp();
    }

    /**
     * Create an instance of {@link GetAtpTypeResponse }
     * 
     */
    public GetAtpTypeResponse createGetAtpTypeResponse() {
        return new GetAtpTypeResponse();
    }

    /**
     * Create an instance of {@link RemoveDateRange }
     * 
     */
    public RemoveDateRange createRemoveDateRange() {
        return new RemoveDateRange();
    }

    /**
     * Create an instance of {@link GetAtpsByDatesResponse }
     * 
     */
    public GetAtpsByDatesResponse createGetAtpsByDatesResponse() {
        return new GetAtpsByDatesResponse();
    }

    /**
     * Create an instance of {@link GetAtpTypes }
     * 
     */
    public GetAtpTypes createGetAtpTypes() {
        return new GetAtpTypes();
    }

    /**
     * Create an instance of {@link GetDateRangesByDate }
     * 
     */
    public GetDateRangesByDate createGetDateRangesByDate() {
        return new GetDateRangesByDate();
    }

    /**
     * Create an instance of {@link UpdateMilestone }
     * 
     */
    public UpdateMilestone createUpdateMilestone() {
        return new UpdateMilestone();
    }

    /**
     * Create an instance of {@link GetDateRangesByDateResponse }
     * 
     */
    public GetDateRangesByDateResponse createGetDateRangesByDateResponse() {
        return new GetDateRangesByDateResponse();
    }

    /**
     * Create an instance of {@link UpdateAtp }
     * 
     */
    public UpdateAtp createUpdateAtp() {
        return new UpdateAtp();
    }

    /**
     * Create an instance of {@link GetMilestoneTypesResponse }
     * 
     */
    public GetMilestoneTypesResponse createGetMilestoneTypesResponse() {
        return new GetMilestoneTypesResponse();
    }

    /**
     * Create an instance of {@link GetDateRangesByAtp }
     * 
     */
    public GetDateRangesByAtp createGetDateRangesByAtp() {
        return new GetDateRangesByAtp();
    }

    /**
     * Create an instance of {@link RemoveDateRangeResponse }
     * 
     */
    public RemoveDateRangeResponse createRemoveDateRangeResponse() {
        return new RemoveDateRangeResponse();
    }

    /**
     * Create an instance of {@link RemoveMilestone }
     * 
     */
    public RemoveMilestone createRemoveMilestone() {
        return new RemoveMilestone();
    }

    /**
     * Create an instance of {@link ValidateMilestoneResponse }
     * 
     */
    public ValidateMilestoneResponse createValidateMilestoneResponse() {
        return new ValidateMilestoneResponse();
    }

    /**
     * Create an instance of {@link MilestoneTypeInfo }
     * 
     */
    public MilestoneTypeInfo createMilestoneTypeInfo() {
        return new MilestoneTypeInfo();
    }

    /**
     * Create an instance of {@link GetMilestoneResponse }
     * 
     */
    public GetMilestoneResponse createGetMilestoneResponse() {
        return new GetMilestoneResponse();
    }

    /**
     * Create an instance of {@link AtpSeasonalTypeInfo }
     * 
     */
    public AtpSeasonalTypeInfo createAtpSeasonalTypeInfo() {
        return new AtpSeasonalTypeInfo();
    }

    /**
     * Create an instance of {@link GetAtpSeasonalTypeResponse }
     * 
     */
    public GetAtpSeasonalTypeResponse createGetAtpSeasonalTypeResponse() {
        return new GetAtpSeasonalTypeResponse();
    }

    /**
     * Create an instance of {@link GetAtpSeasonalTypes }
     * 
     */
    public GetAtpSeasonalTypes createGetAtpSeasonalTypes() {
        return new GetAtpSeasonalTypes();
    }

    /**
     * Create an instance of {@link DateRangeInfo }
     * 
     */
    public DateRangeInfo createDateRangeInfo() {
        return new DateRangeInfo();
    }

    /**
     * Create an instance of {@link AddMilestone }
     * 
     */
    public AddMilestone createAddMilestone() {
        return new AddMilestone();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpsByDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpsByDate")
    public JAXBElement<GetAtpsByDate> createGetAtpsByDate(GetAtpsByDate value) {
        return new JAXBElement<GetAtpsByDate>(_GetAtpsByDate_QNAME, GetAtpsByDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Search }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "search")
    public JAXBElement<Search> createSearch(Search value) {
        return new JAXBElement<Search>(_Search_QNAME, Search.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchCriteriaTypeResponse")
    public JAXBElement<GetSearchCriteriaTypeResponse> createGetSearchCriteriaTypeResponse(GetSearchCriteriaTypeResponse value) {
        return new JAXBElement<GetSearchCriteriaTypeResponse>(_GetSearchCriteriaTypeResponse_QNAME, GetSearchCriteriaTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchResultTypesResponse")
    public JAXBElement<GetSearchResultTypesResponse> createGetSearchResultTypesResponse(GetSearchResultTypesResponse value) {
        return new JAXBElement<GetSearchResultTypesResponse>(_GetSearchResultTypesResponse_QNAME, GetSearchResultTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpDurationTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpDurationTypeResponse")
    public JAXBElement<GetAtpDurationTypeResponse> createGetAtpDurationTypeResponse(GetAtpDurationTypeResponse value) {
        return new JAXBElement<GetAtpDurationTypeResponse>(_GetAtpDurationTypeResponse_QNAME, GetAtpDurationTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchResultType")
    public JAXBElement<GetSearchResultType> createGetSearchResultType(GetSearchResultType value) {
        return new JAXBElement<GetSearchResultType>(_GetSearchResultType_QNAME, GetSearchResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "DoesNotExistException")
    public JAXBElement<DoesNotExistException> createDoesNotExistException(DoesNotExistException value) {
        return new JAXBElement<DoesNotExistException>(_DoesNotExistException_QNAME, DoesNotExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMilestone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "updateMilestone")
    public JAXBElement<UpdateMilestone> createUpdateMilestone(UpdateMilestone value) {
        return new JAXBElement<UpdateMilestone>(_UpdateMilestone_QNAME, UpdateMilestone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpSeasonalType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpSeasonalType")
    public JAXBElement<GetAtpSeasonalType> createGetAtpSeasonalType(GetAtpSeasonalType value) {
        return new JAXBElement<GetAtpSeasonalType>(_GetAtpSeasonalType_QNAME, GetAtpSeasonalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchResultTypes")
    public JAXBElement<GetSearchResultTypes> createGetSearchResultTypes(GetSearchResultTypes value) {
        return new JAXBElement<GetSearchResultTypes>(_GetSearchResultTypes_QNAME, GetSearchResultTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestonesByDatesAndTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestonesByDatesAndTypeResponse")
    public JAXBElement<GetMilestonesByDatesAndTypeResponse> createGetMilestonesByDatesAndTypeResponse(GetMilestonesByDatesAndTypeResponse value) {
        return new JAXBElement<GetMilestonesByDatesAndTypeResponse>(_GetMilestonesByDatesAndTypeResponse_QNAME, GetMilestonesByDatesAndTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "deleteAtp")
    public JAXBElement<DeleteAtp> createDeleteAtp(DeleteAtp value) {
        return new JAXBElement<DeleteAtp>(_DeleteAtp_QNAME, DeleteAtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpSeasonalTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpSeasonalTypes")
    public JAXBElement<GetAtpSeasonalTypes> createGetAtpSeasonalTypes(GetAtpSeasonalTypes value) {
        return new JAXBElement<GetAtpSeasonalTypes>(_GetAtpSeasonalTypes_QNAME, GetAtpSeasonalTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpsByAtpTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpsByAtpTypeResponse")
    public JAXBElement<GetAtpsByAtpTypeResponse> createGetAtpsByAtpTypeResponse(GetAtpsByAtpTypeResponse value) {
        return new JAXBElement<GetAtpsByAtpTypeResponse>(_GetAtpsByAtpTypeResponse_QNAME, GetAtpsByAtpTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangeTypesForAtpTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangeTypesForAtpTypeResponse")
    public JAXBElement<GetDateRangeTypesForAtpTypeResponse> createGetDateRangeTypesForAtpTypeResponse(GetDateRangeTypesForAtpTypeResponse value) {
        return new JAXBElement<GetDateRangeTypesForAtpTypeResponse>(_GetDateRangeTypesForAtpTypeResponse_QNAME, GetDateRangeTypesForAtpTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestoneTypesForAtpTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestoneTypesForAtpTypeResponse")
    public JAXBElement<GetMilestoneTypesForAtpTypeResponse> createGetMilestoneTypesForAtpTypeResponse(GetMilestoneTypesForAtpTypeResponse value) {
        return new JAXBElement<GetMilestoneTypesForAtpTypeResponse>(_GetMilestoneTypesForAtpTypeResponse_QNAME, GetMilestoneTypesForAtpTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangeResponse")
    public JAXBElement<GetDateRangeResponse> createGetDateRangeResponse(GetDateRangeResponse value) {
        return new JAXBElement<GetDateRangeResponse>(_GetDateRangeResponse_QNAME, GetDateRangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateDateRangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "validateDateRangeResponse")
    public JAXBElement<ValidateDateRangeResponse> createValidateDateRangeResponse(ValidateDateRangeResponse value) {
        return new JAXBElement<ValidateDateRangeResponse>(_ValidateDateRangeResponse_QNAME, ValidateDateRangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchTypesByResultResponse")
    public JAXBElement<GetSearchTypesByResultResponse> createGetSearchTypesByResultResponse(GetSearchTypesByResultResponse value) {
        return new JAXBElement<GetSearchTypesByResultResponse>(_GetSearchTypesByResultResponse_QNAME, GetSearchTypesByResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchTypesByCriteria")
    public JAXBElement<GetSearchTypesByCriteria> createGetSearchTypesByCriteria(GetSearchTypesByCriteria value) {
        return new JAXBElement<GetSearchTypesByCriteria>(_GetSearchTypesByCriteria_QNAME, GetSearchTypesByCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangesByDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangesByDate")
    public JAXBElement<GetDateRangesByDate> createGetDateRangesByDate(GetDateRangesByDate value) {
        return new JAXBElement<GetDateRangesByDate>(_GetDateRangesByDate_QNAME, GetDateRangesByDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpResponse")
    public JAXBElement<GetAtpResponse> createGetAtpResponse(GetAtpResponse value) {
        return new JAXBElement<GetAtpResponse>(_GetAtpResponse_QNAME, GetAtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchTypesByResult")
    public JAXBElement<GetSearchTypesByResult> createGetSearchTypesByResult(GetSearchTypesByResult value) {
        return new JAXBElement<GetSearchTypesByResult>(_GetSearchTypesByResult_QNAME, GetSearchTypesByResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchCriteriaTypes")
    public JAXBElement<GetSearchCriteriaTypes> createGetSearchCriteriaTypes(GetSearchCriteriaTypes value) {
        return new JAXBElement<GetSearchCriteriaTypes>(_GetSearchCriteriaTypes_QNAME, GetSearchCriteriaTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchCriteriaType")
    public JAXBElement<GetSearchCriteriaType> createGetSearchCriteriaType(GetSearchCriteriaType value) {
        return new JAXBElement<GetSearchCriteriaType>(_GetSearchCriteriaType_QNAME, GetSearchCriteriaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionMismatchException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "VersionMismatchException")
    public JAXBElement<VersionMismatchException> createVersionMismatchException(VersionMismatchException value) {
        return new JAXBElement<VersionMismatchException>(_VersionMismatchException_QNAME, VersionMismatchException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "AlreadyExistsException")
    public JAXBElement<AlreadyExistsException> createAlreadyExistsException(AlreadyExistsException value) {
        return new JAXBElement<AlreadyExistsException>(_AlreadyExistsException_QNAME, AlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangeTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangeTypeResponse")
    public JAXBElement<GetDateRangeTypeResponse> createGetDateRangeTypeResponse(GetDateRangeTypeResponse value) {
        return new JAXBElement<GetDateRangeTypeResponse>(_GetDateRangeTypeResponse_QNAME, GetDateRangeTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestoneResponse")
    public JAXBElement<GetMilestoneResponse> createGetMilestoneResponse(GetMilestoneResponse value) {
        return new JAXBElement<GetMilestoneResponse>(_GetMilestoneResponse_QNAME, GetMilestoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestoneTypesForAtpType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestoneTypesForAtpType")
    public JAXBElement<GetMilestoneTypesForAtpType> createGetMilestoneTypesForAtpType(GetMilestoneTypesForAtpType value) {
        return new JAXBElement<GetMilestoneTypesForAtpType>(_GetMilestoneTypesForAtpType_QNAME, GetMilestoneTypesForAtpType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestone")
    public JAXBElement<GetMilestone> createGetMilestone(GetMilestone value) {
        return new JAXBElement<GetMilestone>(_GetMilestone_QNAME, GetMilestone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpTypesResponse")
    public JAXBElement<GetAtpTypesResponse> createGetAtpTypesResponse(GetAtpTypesResponse value) {
        return new JAXBElement<GetAtpTypesResponse>(_GetAtpTypesResponse_QNAME, GetAtpTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDateRange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "addDateRange")
    public JAXBElement<AddDateRange> createAddDateRange(AddDateRange value) {
        return new JAXBElement<AddDateRange>(_AddDateRange_QNAME, AddDateRange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "updateAtp")
    public JAXBElement<UpdateAtp> createUpdateAtp(UpdateAtp value) {
        return new JAXBElement<UpdateAtp>(_UpdateAtp_QNAME, UpdateAtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveMilestone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "removeMilestone")
    public JAXBElement<RemoveMilestone> createRemoveMilestone(RemoveMilestone value) {
        return new JAXBElement<RemoveMilestone>(_RemoveMilestone_QNAME, RemoveMilestone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpType")
    public JAXBElement<GetAtpType> createGetAtpType(GetAtpType value) {
        return new JAXBElement<GetAtpType>(_GetAtpType_QNAME, GetAtpType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpsByDateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpsByDateResponse")
    public JAXBElement<GetAtpsByDateResponse> createGetAtpsByDateResponse(GetAtpsByDateResponse value) {
        return new JAXBElement<GetAtpsByDateResponse>(_GetAtpsByDateResponse_QNAME, GetAtpsByDateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangeTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangeTypes")
    public JAXBElement<GetDateRangeTypes> createGetDateRangeTypes(GetDateRangeTypes value) {
        return new JAXBElement<GetDateRangeTypes>(_GetDateRangeTypes_QNAME, GetDateRangeTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestoneTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestoneTypes")
    public JAXBElement<GetMilestoneTypes> createGetMilestoneTypes(GetMilestoneTypes value) {
        return new JAXBElement<GetMilestoneTypes>(_GetMilestoneTypes_QNAME, GetMilestoneTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchTypesResponse")
    public JAXBElement<GetSearchTypesResponse> createGetSearchTypesResponse(GetSearchTypesResponse value) {
        return new JAXBElement<GetSearchTypesResponse>(_GetSearchTypesResponse_QNAME, GetSearchTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDateRangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "removeDateRangeResponse")
    public JAXBElement<RemoveDateRangeResponse> createRemoveDateRangeResponse(RemoveDateRangeResponse value) {
        return new JAXBElement<RemoveDateRangeResponse>(_RemoveDateRangeResponse_QNAME, RemoveDateRangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "deleteAtpResponse")
    public JAXBElement<DeleteAtpResponse> createDeleteAtpResponse(DeleteAtpResponse value) {
        return new JAXBElement<DeleteAtpResponse>(_DeleteAtpResponse_QNAME, DeleteAtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "createAtpResponse")
    public JAXBElement<CreateAtpResponse> createCreateAtpResponse(CreateAtpResponse value) {
        return new JAXBElement<CreateAtpResponse>(_CreateAtpResponse_QNAME, CreateAtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDateRange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "updateDateRange")
    public JAXBElement<UpdateDateRange> createUpdateDateRange(UpdateDateRange value) {
        return new JAXBElement<UpdateDateRange>(_UpdateDateRange_QNAME, UpdateDateRange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpTypeResponse")
    public JAXBElement<GetAtpTypeResponse> createGetAtpTypeResponse(GetAtpTypeResponse value) {
        return new JAXBElement<GetAtpTypeResponse>(_GetAtpTypeResponse_QNAME, GetAtpTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRange")
    public JAXBElement<GetDateRange> createGetDateRange(GetDateRange value) {
        return new JAXBElement<GetDateRange>(_GetDateRange_QNAME, GetDateRange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "MissingParameterException")
    public JAXBElement<MissingParameterException> createMissingParameterException(MissingParameterException value) {
        return new JAXBElement<MissingParameterException>(_MissingParameterException_QNAME, MissingParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateDateRange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "validateDateRange")
    public JAXBElement<ValidateDateRange> createValidateDateRange(ValidateDateRange value) {
        return new JAXBElement<ValidateDateRange>(_ValidateDateRange_QNAME, ValidateDateRange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchResultTypeResponse")
    public JAXBElement<GetSearchResultTypeResponse> createGetSearchResultTypeResponse(GetSearchResultTypeResponse value) {
        return new JAXBElement<GetSearchResultTypeResponse>(_GetSearchResultTypeResponse_QNAME, GetSearchResultTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDateRangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "addDateRangeResponse")
    public JAXBElement<AddDateRangeResponse> createAddDateRangeResponse(AddDateRangeResponse value) {
        return new JAXBElement<AddDateRangeResponse>(_AddDateRangeResponse_QNAME, AddDateRangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpsByDates }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpsByDates")
    public JAXBElement<GetAtpsByDates> createGetAtpsByDates(GetAtpsByDates value) {
        return new JAXBElement<GetAtpsByDates>(_GetAtpsByDates_QNAME, GetAtpsByDates.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateAtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "validateAtp")
    public JAXBElement<ValidateAtp> createValidateAtp(ValidateAtp value) {
        return new JAXBElement<ValidateAtp>(_ValidateAtp_QNAME, ValidateAtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchCriteriaTypesResponse")
    public JAXBElement<GetSearchCriteriaTypesResponse> createGetSearchCriteriaTypesResponse(GetSearchCriteriaTypesResponse value) {
        return new JAXBElement<GetSearchCriteriaTypesResponse>(_GetSearchCriteriaTypesResponse_QNAME, GetSearchCriteriaTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestonesByAtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestonesByAtpResponse")
    public JAXBElement<GetMilestonesByAtpResponse> createGetMilestonesByAtpResponse(GetMilestonesByAtpResponse value) {
        return new JAXBElement<GetMilestonesByAtpResponse>(_GetMilestonesByAtpResponse_QNAME, GetMilestonesByAtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMilestone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "addMilestone")
    public JAXBElement<AddMilestone> createAddMilestone(AddMilestone value) {
        return new JAXBElement<AddMilestone>(_AddMilestone_QNAME, AddMilestone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangeType")
    public JAXBElement<GetDateRangeType> createGetDateRangeType(GetDateRangeType value) {
        return new JAXBElement<GetDateRangeType>(_GetDateRangeType_QNAME, GetDateRangeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveMilestoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "removeMilestoneResponse")
    public JAXBElement<RemoveMilestoneResponse> createRemoveMilestoneResponse(RemoveMilestoneResponse value) {
        return new JAXBElement<RemoveMilestoneResponse>(_RemoveMilestoneResponse_QNAME, RemoveMilestoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMilestoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "addMilestoneResponse")
    public JAXBElement<AddMilestoneResponse> createAddMilestoneResponse(AddMilestoneResponse value) {
        return new JAXBElement<AddMilestoneResponse>(_AddMilestoneResponse_QNAME, AddMilestoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangeTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangeTypesResponse")
    public JAXBElement<GetDateRangeTypesResponse> createGetDateRangeTypesResponse(GetDateRangeTypesResponse value) {
        return new JAXBElement<GetDateRangeTypesResponse>(_GetDateRangeTypesResponse_QNAME, GetDateRangeTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpDurationTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpDurationTypesResponse")
    public JAXBElement<GetAtpDurationTypesResponse> createGetAtpDurationTypesResponse(GetAtpDurationTypesResponse value) {
        return new JAXBElement<GetAtpDurationTypesResponse>(_GetAtpDurationTypesResponse_QNAME, GetAtpDurationTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDateRangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "updateDateRangeResponse")
    public JAXBElement<UpdateDateRangeResponse> createUpdateDateRangeResponse(UpdateDateRangeResponse value) {
        return new JAXBElement<UpdateDateRangeResponse>(_UpdateDateRangeResponse_QNAME, UpdateDateRangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDateRange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "removeDateRange")
    public JAXBElement<RemoveDateRange> createRemoveDateRange(RemoveDateRange value) {
        return new JAXBElement<RemoveDateRange>(_RemoveDateRange_QNAME, RemoveDateRange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "createAtp")
    public JAXBElement<CreateAtp> createCreateAtp(CreateAtp value) {
        return new JAXBElement<CreateAtp>(_CreateAtp_QNAME, CreateAtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestoneType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestoneType")
    public JAXBElement<GetMilestoneType> createGetMilestoneType(GetMilestoneType value) {
        return new JAXBElement<GetMilestoneType>(_GetMilestoneType_QNAME, GetMilestoneType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpsByDatesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpsByDatesResponse")
    public JAXBElement<GetAtpsByDatesResponse> createGetAtpsByDatesResponse(GetAtpsByDatesResponse value) {
        return new JAXBElement<GetAtpsByDatesResponse>(_GetAtpsByDatesResponse_QNAME, GetAtpsByDatesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangesByAtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangesByAtpResponse")
    public JAXBElement<GetDateRangesByAtpResponse> createGetDateRangesByAtpResponse(GetDateRangesByAtpResponse value) {
        return new JAXBElement<GetDateRangesByAtpResponse>(_GetDateRangesByAtpResponse_QNAME, GetDateRangesByAtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "InvalidParameterException")
    public JAXBElement<InvalidParameterException> createInvalidParameterException(InvalidParameterException value) {
        return new JAXBElement<InvalidParameterException>(_InvalidParameterException_QNAME, InvalidParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangeTypesForAtpType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangeTypesForAtpType")
    public JAXBElement<GetDateRangeTypesForAtpType> createGetDateRangeTypesForAtpType(GetDateRangeTypesForAtpType value) {
        return new JAXBElement<GetDateRangeTypesForAtpType>(_GetDateRangeTypesForAtpType_QNAME, GetDateRangeTypesForAtpType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestoneTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestoneTypesResponse")
    public JAXBElement<GetMilestoneTypesResponse> createGetMilestoneTypesResponse(GetMilestoneTypesResponse value) {
        return new JAXBElement<GetMilestoneTypesResponse>(_GetMilestoneTypesResponse_QNAME, GetMilestoneTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestoneTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestoneTypeResponse")
    public JAXBElement<GetMilestoneTypeResponse> createGetMilestoneTypeResponse(GetMilestoneTypeResponse value) {
        return new JAXBElement<GetMilestoneTypeResponse>(_GetMilestoneTypeResponse_QNAME, GetMilestoneTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchTypeResponse")
    public JAXBElement<GetSearchTypeResponse> createGetSearchTypeResponse(GetSearchTypeResponse value) {
        return new JAXBElement<GetSearchTypeResponse>(_GetSearchTypeResponse_QNAME, GetSearchTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpsByAtpType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpsByAtpType")
    public JAXBElement<GetAtpsByAtpType> createGetAtpsByAtpType(GetAtpsByAtpType value) {
        return new JAXBElement<GetAtpsByAtpType>(_GetAtpsByAtpType_QNAME, GetAtpsByAtpType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataValidationErrorException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "DataValidationErrorException")
    public JAXBElement<DataValidationErrorException> createDataValidationErrorException(DataValidationErrorException value) {
        return new JAXBElement<DataValidationErrorException>(_DataValidationErrorException_QNAME, DataValidationErrorException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpTypes")
    public JAXBElement<GetAtpTypes> createGetAtpTypes(GetAtpTypes value) {
        return new JAXBElement<GetAtpTypes>(_GetAtpTypes_QNAME, GetAtpTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangesByDateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangesByDateResponse")
    public JAXBElement<GetDateRangesByDateResponse> createGetDateRangesByDateResponse(GetDateRangesByDateResponse value) {
        return new JAXBElement<GetDateRangesByDateResponse>(_GetDateRangesByDateResponse_QNAME, GetDateRangesByDateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "PermissionDeniedException")
    public JAXBElement<PermissionDeniedException> createPermissionDeniedException(PermissionDeniedException value) {
        return new JAXBElement<PermissionDeniedException>(_PermissionDeniedException_QNAME, PermissionDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchType")
    public JAXBElement<GetSearchType> createGetSearchType(GetSearchType value) {
        return new JAXBElement<GetSearchType>(_GetSearchType_QNAME, GetSearchType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestonesByAtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestonesByAtp")
    public JAXBElement<GetMilestonesByAtp> createGetMilestonesByAtp(GetMilestonesByAtp value) {
        return new JAXBElement<GetMilestonesByAtp>(_GetMilestonesByAtp_QNAME, GetMilestonesByAtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestonesByDatesAndType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestonesByDatesAndType")
    public JAXBElement<GetMilestonesByDatesAndType> createGetMilestonesByDatesAndType(GetMilestonesByDatesAndType value) {
        return new JAXBElement<GetMilestonesByDatesAndType>(_GetMilestonesByDatesAndType_QNAME, GetMilestonesByDatesAndType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "updateAtpResponse")
    public JAXBElement<UpdateAtpResponse> createUpdateAtpResponse(UpdateAtpResponse value) {
        return new JAXBElement<UpdateAtpResponse>(_UpdateAtpResponse_QNAME, UpdateAtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtp")
    public JAXBElement<GetAtp> createGetAtp(GetAtp value) {
        return new JAXBElement<GetAtp>(_GetAtp_QNAME, GetAtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateMilestone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "validateMilestone")
    public JAXBElement<ValidateMilestone> createValidateMilestone(ValidateMilestone value) {
        return new JAXBElement<ValidateMilestone>(_ValidateMilestone_QNAME, ValidateMilestone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestonesByDates }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestonesByDates")
    public JAXBElement<GetMilestonesByDates> createGetMilestonesByDates(GetMilestonesByDates value) {
        return new JAXBElement<GetMilestonesByDates>(_GetMilestonesByDates_QNAME, GetMilestonesByDates.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMilestoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "updateMilestoneResponse")
    public JAXBElement<UpdateMilestoneResponse> createUpdateMilestoneResponse(UpdateMilestoneResponse value) {
        return new JAXBElement<UpdateMilestoneResponse>(_UpdateMilestoneResponse_QNAME, UpdateMilestoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpDurationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpDurationType")
    public JAXBElement<GetAtpDurationType> createGetAtpDurationType(GetAtpDurationType value) {
        return new JAXBElement<GetAtpDurationType>(_GetAtpDurationType_QNAME, GetAtpDurationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpDurationTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpDurationTypes")
    public JAXBElement<GetAtpDurationTypes> createGetAtpDurationTypes(GetAtpDurationTypes value) {
        return new JAXBElement<GetAtpDurationTypes>(_GetAtpDurationTypes_QNAME, GetAtpDurationTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateAtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "validateAtpResponse")
    public JAXBElement<ValidateAtpResponse> createValidateAtpResponse(ValidateAtpResponse value) {
        return new JAXBElement<ValidateAtpResponse>(_ValidateAtpResponse_QNAME, ValidateAtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpSeasonalTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpSeasonalTypesResponse")
    public JAXBElement<GetAtpSeasonalTypesResponse> createGetAtpSeasonalTypesResponse(GetAtpSeasonalTypesResponse value) {
        return new JAXBElement<GetAtpSeasonalTypesResponse>(_GetAtpSeasonalTypesResponse_QNAME, GetAtpSeasonalTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateMilestoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "validateMilestoneResponse")
    public JAXBElement<ValidateMilestoneResponse> createValidateMilestoneResponse(ValidateMilestoneResponse value) {
        return new JAXBElement<ValidateMilestoneResponse>(_ValidateMilestoneResponse_QNAME, ValidateMilestoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAtpSeasonalTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getAtpSeasonalTypeResponse")
    public JAXBElement<GetAtpSeasonalTypeResponse> createGetAtpSeasonalTypeResponse(GetAtpSeasonalTypeResponse value) {
        return new JAXBElement<GetAtpSeasonalTypeResponse>(_GetAtpSeasonalTypeResponse_QNAME, GetAtpSeasonalTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMilestonesByDatesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getMilestonesByDatesResponse")
    public JAXBElement<GetMilestonesByDatesResponse> createGetMilestonesByDatesResponse(GetMilestonesByDatesResponse value) {
        return new JAXBElement<GetMilestonesByDatesResponse>(_GetMilestonesByDatesResponse_QNAME, GetMilestonesByDatesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByCriteriaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchTypesByCriteriaResponse")
    public JAXBElement<GetSearchTypesByCriteriaResponse> createGetSearchTypesByCriteriaResponse(GetSearchTypesByCriteriaResponse value) {
        return new JAXBElement<GetSearchTypesByCriteriaResponse>(_GetSearchTypesByCriteriaResponse_QNAME, GetSearchTypesByCriteriaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getSearchTypes")
    public JAXBElement<GetSearchTypes> createGetSearchTypes(GetSearchTypes value) {
        return new JAXBElement<GetSearchTypes>(_GetSearchTypes_QNAME, GetSearchTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "searchResponse")
    public JAXBElement<SearchResponse> createSearchResponse(SearchResponse value) {
        return new JAXBElement<SearchResponse>(_SearchResponse_QNAME, SearchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDateRangesByAtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/atp", name = "getDateRangesByAtp")
    public JAXBElement<GetDateRangesByAtp> createGetDateRangesByAtp(GetDateRangesByAtp value) {
        return new JAXBElement<GetDateRangesByAtp>(_GetDateRangesByAtp_QNAME, GetDateRangesByAtp.class, null, value);
    }

}
