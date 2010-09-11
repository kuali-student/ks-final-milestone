
package org.kuali.student.wsdl.course;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.dictionary.GetObjectStructure;
import org.kuali.student.wsdl.dictionary.GetObjectStructureResponse;
import org.kuali.student.wsdl.dictionary.GetObjectTypes;
import org.kuali.student.wsdl.dictionary.GetObjectTypesResponse;
import org.kuali.student.wsdl.exceptions.AlreadyExistsException;
import org.kuali.student.wsdl.exceptions.CircularRelationshipException;
import org.kuali.student.wsdl.exceptions.DataValidationErrorException;
import org.kuali.student.wsdl.exceptions.DependentObjectsExistException;
import org.kuali.student.wsdl.exceptions.DoesNotExistException;
import org.kuali.student.wsdl.exceptions.InvalidParameterException;
import org.kuali.student.wsdl.exceptions.MissingParameterException;
import org.kuali.student.wsdl.exceptions.OperationFailedException;
import org.kuali.student.wsdl.exceptions.PermissionDeniedException;
import org.kuali.student.wsdl.exceptions.UnsupportedActionException;
import org.kuali.student.wsdl.exceptions.VersionMismatchException;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.course package. 
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

    private final static QName _GetCourseLos_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseLos");
    private final static QName _DeleteCourse_QNAME = new QName("http://student.kuali.org/wsdl/course", "deleteCourse");
    private final static QName _InvalidParameterException_QNAME = new QName("http://student.kuali.org/wsdl/course", "InvalidParameterException");
    private final static QName _GetCourseResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseResponse");
    private final static QName _ValidateCourseResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "validateCourseResponse");
    private final static QName _GetObjectTypes_QNAME = new QName("http://student.kuali.org/wsdl/course", "getObjectTypes");
    private final static QName _DeleteCourseStatement_QNAME = new QName("http://student.kuali.org/wsdl/course", "deleteCourseStatement");
    private final static QName _UpdateCourseStatementResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "updateCourseStatementResponse");
    private final static QName _DoesNotExistException_QNAME = new QName("http://student.kuali.org/wsdl/course", "DoesNotExistException");
    private final static QName _GetCourseStatements_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseStatements");
    private final static QName _MissingParameterException_QNAME = new QName("http://student.kuali.org/wsdl/course", "MissingParameterException");
    private final static QName _GetCourseActivities_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseActivities");
    private final static QName _GetCourseFormatsResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseFormatsResponse");
    private final static QName _DeleteCourseStatementResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "deleteCourseStatementResponse");
    private final static QName _CreateCourse_QNAME = new QName("http://student.kuali.org/wsdl/course", "createCourse");
    private final static QName _GetCourse_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourse");
    private final static QName _GetCourseFormats_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseFormats");
    private final static QName _CircularRelationshipException_QNAME = new QName("http://student.kuali.org/wsdl/course", "CircularRelationshipException");
    private final static QName _GetCourseLosResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseLosResponse");
    private final static QName _CreateCourseStatement_QNAME = new QName("http://student.kuali.org/wsdl/course", "createCourseStatement");
    private final static QName _GetCourseStatementsResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseStatementsResponse");
    private final static QName _DeleteCourseResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "deleteCourseResponse");
    private final static QName _UpdateCourseStatement_QNAME = new QName("http://student.kuali.org/wsdl/course", "updateCourseStatement");
    private final static QName _UpdateCourseResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "updateCourseResponse");
    private final static QName _AlreadyExistsException_QNAME = new QName("http://student.kuali.org/wsdl/course", "AlreadyExistsException");
    private final static QName _OperationFailedException_QNAME = new QName("http://student.kuali.org/wsdl/course", "OperationFailedException");
    private final static QName _PermissionDeniedException_QNAME = new QName("http://student.kuali.org/wsdl/course", "PermissionDeniedException");
    private final static QName _ValidateCourseStatementResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "validateCourseStatementResponse");
    private final static QName _ValidateCourseStatement_QNAME = new QName("http://student.kuali.org/wsdl/course", "validateCourseStatement");
    private final static QName _VersionMismatchException_QNAME = new QName("http://student.kuali.org/wsdl/course", "VersionMismatchException");
    private final static QName _CreateCourseResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "createCourseResponse");
    private final static QName _DataValidationErrorException_QNAME = new QName("http://student.kuali.org/wsdl/course", "DataValidationErrorException");
    private final static QName _GetObjectStructure_QNAME = new QName("http://student.kuali.org/wsdl/course", "getObjectStructure");
    private final static QName _GetObjectTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "getObjectTypesResponse");
    private final static QName _ValidateCourse_QNAME = new QName("http://student.kuali.org/wsdl/course", "validateCourse");
    private final static QName _GetObjectStructureResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "getObjectStructureResponse");
    private final static QName _UpdateCourse_QNAME = new QName("http://student.kuali.org/wsdl/course", "updateCourse");
    private final static QName _UnsupportedActionException_QNAME = new QName("http://student.kuali.org/wsdl/course", "UnsupportedActionException");
    private final static QName _DependentObjectsExistException_QNAME = new QName("http://student.kuali.org/wsdl/course", "DependentObjectsExistException");
    private final static QName _CreateCourseStatementResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "createCourseStatementResponse");
    private final static QName _GetCourseActivitiesResponse_QNAME = new QName("http://student.kuali.org/wsdl/course", "getCourseActivitiesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.course
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateCourse }
     * 
     */
    public CreateCourse createCreateCourse() {
        return new CreateCourse();
    }

    /**
     * Create an instance of {@link AdminOrgInfo }
     * 
     */
    public AdminOrgInfo createAdminOrgInfo() {
        return new AdminOrgInfo();
    }

    /**
     * Create an instance of {@link DeleteCourseStatementResponse }
     * 
     */
    public DeleteCourseStatementResponse createDeleteCourseStatementResponse() {
        return new DeleteCourseStatementResponse();
    }

    /**
     * Create an instance of {@link ValidateCourseStatementResponse }
     * 
     */
    public ValidateCourseStatementResponse createValidateCourseStatementResponse() {
        return new ValidateCourseStatementResponse();
    }

    /**
     * Create an instance of {@link DeleteCourseResponse }
     * 
     */
    public DeleteCourseResponse createDeleteCourseResponse() {
        return new DeleteCourseResponse();
    }

    /**
     * Create an instance of {@link GetCourseFormatsResponse }
     * 
     */
    public GetCourseFormatsResponse createGetCourseFormatsResponse() {
        return new GetCourseFormatsResponse();
    }

    /**
     * Create an instance of {@link GetCourseStatements }
     * 
     */
    public GetCourseStatements createGetCourseStatements() {
        return new GetCourseStatements();
    }

    /**
     * Create an instance of {@link RichTextInfo }
     * 
     */
    public RichTextInfo createRichTextInfo() {
        return new RichTextInfo();
    }

    /**
     * Create an instance of {@link GetCourseLosResponse }
     * 
     */
    public GetCourseLosResponse createGetCourseLosResponse() {
        return new GetCourseLosResponse();
    }

    /**
     * Create an instance of {@link ReqComponentTypeInfo.ReqCompFieldTypes }
     * 
     */
    public ReqComponentTypeInfo.ReqCompFieldTypes createReqComponentTypeInfoReqCompFieldTypes() {
        return new ReqComponentTypeInfo.ReqCompFieldTypes();
    }

    /**
     * Create an instance of {@link DeleteCourse }
     * 
     */
    public DeleteCourse createDeleteCourse() {
        return new DeleteCourse();
    }

    /**
     * Create an instance of {@link UpdateCourseResponse }
     * 
     */
    public UpdateCourseResponse createUpdateCourseResponse() {
        return new UpdateCourseResponse();
    }

    /**
     * Create an instance of {@link ValidateCourseStatement }
     * 
     */
    public ValidateCourseStatement createValidateCourseStatement() {
        return new ValidateCourseStatement();
    }

    /**
     * Create an instance of {@link GetCourseActivitiesResponse }
     * 
     */
    public GetCourseActivitiesResponse createGetCourseActivitiesResponse() {
        return new GetCourseActivitiesResponse();
    }

    /**
     * Create an instance of {@link StatusInfo }
     * 
     */
    public StatusInfo createStatusInfo() {
        return new StatusInfo();
    }

    /**
     * Create an instance of {@link CreateCourseStatement }
     * 
     */
    public CreateCourseStatement createCreateCourseStatement() {
        return new CreateCourseStatement();
    }

    /**
     * Create an instance of {@link CourseFeeInfo }
     * 
     */
    public CourseFeeInfo createCourseFeeInfo() {
        return new CourseFeeInfo();
    }

    /**
     * Create an instance of {@link CourseCrossListingInfo }
     * 
     */
    public CourseCrossListingInfo createCourseCrossListingInfo() {
        return new CourseCrossListingInfo();
    }

    /**
     * Create an instance of {@link ReqComponentTypeInfo }
     * 
     */
    public ReqComponentTypeInfo createReqComponentTypeInfo() {
        return new ReqComponentTypeInfo();
    }

    /**
     * Create an instance of {@link AmountInfo }
     * 
     */
    public AmountInfo createAmountInfo() {
        return new AmountInfo();
    }

    /**
     * Create an instance of {@link CourseExpenditureInfo }
     * 
     */
    public CourseExpenditureInfo createCourseExpenditureInfo() {
        return new CourseExpenditureInfo();
    }

    /**
     * Create an instance of {@link MetaInfo }
     * 
     */
    public MetaInfo createMetaInfo() {
        return new MetaInfo();
    }

    /**
     * Create an instance of {@link LoCategoryInfo }
     * 
     */
    public LoCategoryInfo createLoCategoryInfo() {
        return new LoCategoryInfo();
    }

    /**
     * Create an instance of {@link BaseConstraint }
     * 
     */
    public BaseConstraint createBaseConstraint() {
        return new BaseConstraint();
    }

    /**
     * Create an instance of {@link ValidateCourse }
     * 
     */
    public ValidateCourse createValidateCourse() {
        return new ValidateCourse();
    }

    /**
     * Create an instance of {@link UpdateCourseStatementResponse }
     * 
     */
    public UpdateCourseStatementResponse createUpdateCourseStatementResponse() {
        return new UpdateCourseStatementResponse();
    }

    /**
     * Create an instance of {@link GetCourseActivities }
     * 
     */
    public GetCourseActivities createGetCourseActivities() {
        return new GetCourseActivities();
    }

    /**
     * Create an instance of {@link FormatInfo }
     * 
     */
    public FormatInfo createFormatInfo() {
        return new FormatInfo();
    }

    /**
     * Create an instance of {@link TimeAmountInfo }
     * 
     */
    public TimeAmountInfo createTimeAmountInfo() {
        return new TimeAmountInfo();
    }

    /**
     * Create an instance of {@link CluInstructorInfo }
     * 
     */
    public CluInstructorInfo createCluInstructorInfo() {
        return new CluInstructorInfo();
    }

    /**
     * Create an instance of {@link CourseVariationInfo }
     * 
     */
    public CourseVariationInfo createCourseVariationInfo() {
        return new CourseVariationInfo();
    }

    /**
     * Create an instance of {@link LoInfo }
     * 
     */
    public LoInfo createLoInfo() {
        return new LoInfo();
    }

    /**
     * Create an instance of {@link GetCourseStatementsResponse }
     * 
     */
    public GetCourseStatementsResponse createGetCourseStatementsResponse() {
        return new GetCourseStatementsResponse();
    }

    /**
     * Create an instance of {@link ReqComponentInfo }
     * 
     */
    public ReqComponentInfo createReqComponentInfo() {
        return new ReqComponentInfo();
    }

    /**
     * Create an instance of {@link ObjectStructureDefinition }
     * 
     */
    public ObjectStructureDefinition createObjectStructureDefinition() {
        return new ObjectStructureDefinition();
    }

    /**
     * Create an instance of {@link LoDisplayInfo }
     * 
     */
    public LoDisplayInfo createLoDisplayInfo() {
        return new LoDisplayInfo();
    }

    /**
     * Create an instance of {@link MustOccurConstraint }
     * 
     */
    public MustOccurConstraint createMustOccurConstraint() {
        return new MustOccurConstraint();
    }

    /**
     * Create an instance of {@link CommonLookupParam }
     * 
     */
    public CommonLookupParam createCommonLookupParam() {
        return new CommonLookupParam();
    }

    /**
     * Create an instance of {@link ValidCharsConstraint }
     * 
     */
    public ValidCharsConstraint createValidCharsConstraint() {
        return new ValidCharsConstraint();
    }

    /**
     * Create an instance of {@link RequiredConstraint }
     * 
     */
    public RequiredConstraint createRequiredConstraint() {
        return new RequiredConstraint();
    }

    /**
     * Create an instance of {@link FieldDefinition }
     * 
     */
    public FieldDefinition createFieldDefinition() {
        return new FieldDefinition();
    }

    /**
     * Create an instance of {@link GetCourseResponse }
     * 
     */
    public GetCourseResponse createGetCourseResponse() {
        return new GetCourseResponse();
    }

    /**
     * Create an instance of {@link ObjectStructureDefinitionWrapper }
     * 
     */
    public ObjectStructureDefinitionWrapper createObjectStructureDefinitionWrapper() {
        return new ObjectStructureDefinitionWrapper();
    }

    /**
     * Create an instance of {@link CaseConstraint }
     * 
     */
    public CaseConstraint createCaseConstraint() {
        return new CaseConstraint();
    }

    /**
     * Create an instance of {@link ValidationResultInfo }
     * 
     */
    public ValidationResultInfo createValidationResultInfo() {
        return new ValidationResultInfo();
    }

    /**
     * Create an instance of {@link GetCourseLos }
     * 
     */
    public GetCourseLos createGetCourseLos() {
        return new GetCourseLos();
    }

    /**
     * Create an instance of {@link CreateCourseResponse }
     * 
     */
    public CreateCourseResponse createCreateCourseResponse() {
        return new CreateCourseResponse();
    }

    /**
     * Create an instance of {@link Constraint }
     * 
     */
    public Constraint createConstraint() {
        return new Constraint();
    }

    /**
     * Create an instance of {@link ValidateCourseResponse }
     * 
     */
    public ValidateCourseResponse createValidateCourseResponse() {
        return new ValidateCourseResponse();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link GetCourseFormats }
     * 
     */
    public GetCourseFormats createGetCourseFormats() {
        return new GetCourseFormats();
    }

    /**
     * Create an instance of {@link ResultComponentInfo }
     * 
     */
    public ResultComponentInfo createResultComponentInfo() {
        return new ResultComponentInfo();
    }

    /**
     * Create an instance of {@link JaxbAttributeList }
     * 
     */
    public JaxbAttributeList createJaxbAttributeList() {
        return new JaxbAttributeList();
    }

    /**
     * Create an instance of {@link DeleteCourseStatement }
     * 
     */
    public DeleteCourseStatement createDeleteCourseStatement() {
        return new DeleteCourseStatement();
    }

    /**
     * Create an instance of {@link CurrencyAmountInfo }
     * 
     */
    public CurrencyAmountInfo createCurrencyAmountInfo() {
        return new CurrencyAmountInfo();
    }

    /**
     * Create an instance of {@link UpdateCourseStatement }
     * 
     */
    public UpdateCourseStatement createUpdateCourseStatement() {
        return new UpdateCourseStatement();
    }

    /**
     * Create an instance of {@link ActivityInfo }
     * 
     */
    public ActivityInfo createActivityInfo() {
        return new ActivityInfo();
    }

    /**
     * Create an instance of {@link UpdateCourse }
     * 
     */
    public UpdateCourse createUpdateCourse() {
        return new UpdateCourse();
    }

    /**
     * Create an instance of {@link CommonLookup }
     * 
     */
    public CommonLookup createCommonLookup() {
        return new CommonLookup();
    }

    /**
     * Create an instance of {@link CreateCourseStatementResponse }
     * 
     */
    public CreateCourseStatementResponse createCreateCourseStatementResponse() {
        return new CreateCourseStatementResponse();
    }

    /**
     * Create an instance of {@link CourseInfo }
     * 
     */
    public CourseInfo createCourseInfo() {
        return new CourseInfo();
    }

    /**
     * Create an instance of {@link CourseJointInfo }
     * 
     */
    public CourseJointInfo createCourseJointInfo() {
        return new CourseJointInfo();
    }

    /**
     * Create an instance of {@link ReqCompFieldTypeInfo }
     * 
     */
    public ReqCompFieldTypeInfo createReqCompFieldTypeInfo() {
        return new ReqCompFieldTypeInfo();
    }

    /**
     * Create an instance of {@link CourseRevenueInfo }
     * 
     */
    public CourseRevenueInfo createCourseRevenueInfo() {
        return new CourseRevenueInfo();
    }

    /**
     * Create an instance of {@link AffiliatedOrgInfo }
     * 
     */
    public AffiliatedOrgInfo createAffiliatedOrgInfo() {
        return new AffiliatedOrgInfo();
    }

    /**
     * Create an instance of {@link StatementTreeViewInfo }
     * 
     */
    public StatementTreeViewInfo createStatementTreeViewInfo() {
        return new StatementTreeViewInfo();
    }

    /**
     * Create an instance of {@link LookupConstraint }
     * 
     */
    public LookupConstraint createLookupConstraint() {
        return new LookupConstraint();
    }

    /**
     * Create an instance of {@link GetCourse }
     * 
     */
    public GetCourse createGetCourse() {
        return new GetCourse();
    }

    /**
     * Create an instance of {@link WhenConstraint }
     * 
     */
    public WhenConstraint createWhenConstraint() {
        return new WhenConstraint();
    }

    /**
     * Create an instance of {@link ReqCompFieldInfo }
     * 
     */
    public ReqCompFieldInfo createReqCompFieldInfo() {
        return new ReqCompFieldInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseLos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseLos")
    public JAXBElement<GetCourseLos> createGetCourseLos(GetCourseLos value) {
        return new JAXBElement<GetCourseLos>(_GetCourseLos_QNAME, GetCourseLos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCourse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "deleteCourse")
    public JAXBElement<DeleteCourse> createDeleteCourse(DeleteCourse value) {
        return new JAXBElement<DeleteCourse>(_DeleteCourse_QNAME, DeleteCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "InvalidParameterException")
    public JAXBElement<InvalidParameterException> createInvalidParameterException(InvalidParameterException value) {
        return new JAXBElement<InvalidParameterException>(_InvalidParameterException_QNAME, InvalidParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseResponse")
    public JAXBElement<GetCourseResponse> createGetCourseResponse(GetCourseResponse value) {
        return new JAXBElement<GetCourseResponse>(_GetCourseResponse_QNAME, GetCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateCourseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "validateCourseResponse")
    public JAXBElement<ValidateCourseResponse> createValidateCourseResponse(ValidateCourseResponse value) {
        return new JAXBElement<ValidateCourseResponse>(_ValidateCourseResponse_QNAME, ValidateCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getObjectTypes")
    public JAXBElement<GetObjectTypes> createGetObjectTypes(GetObjectTypes value) {
        return new JAXBElement<GetObjectTypes>(_GetObjectTypes_QNAME, GetObjectTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCourseStatement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "deleteCourseStatement")
    public JAXBElement<DeleteCourseStatement> createDeleteCourseStatement(DeleteCourseStatement value) {
        return new JAXBElement<DeleteCourseStatement>(_DeleteCourseStatement_QNAME, DeleteCourseStatement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCourseStatementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "updateCourseStatementResponse")
    public JAXBElement<UpdateCourseStatementResponse> createUpdateCourseStatementResponse(UpdateCourseStatementResponse value) {
        return new JAXBElement<UpdateCourseStatementResponse>(_UpdateCourseStatementResponse_QNAME, UpdateCourseStatementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "DoesNotExistException")
    public JAXBElement<DoesNotExistException> createDoesNotExistException(DoesNotExistException value) {
        return new JAXBElement<DoesNotExistException>(_DoesNotExistException_QNAME, DoesNotExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseStatements }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseStatements")
    public JAXBElement<GetCourseStatements> createGetCourseStatements(GetCourseStatements value) {
        return new JAXBElement<GetCourseStatements>(_GetCourseStatements_QNAME, GetCourseStatements.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "MissingParameterException")
    public JAXBElement<MissingParameterException> createMissingParameterException(MissingParameterException value) {
        return new JAXBElement<MissingParameterException>(_MissingParameterException_QNAME, MissingParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseActivities")
    public JAXBElement<GetCourseActivities> createGetCourseActivities(GetCourseActivities value) {
        return new JAXBElement<GetCourseActivities>(_GetCourseActivities_QNAME, GetCourseActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseFormatsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseFormatsResponse")
    public JAXBElement<GetCourseFormatsResponse> createGetCourseFormatsResponse(GetCourseFormatsResponse value) {
        return new JAXBElement<GetCourseFormatsResponse>(_GetCourseFormatsResponse_QNAME, GetCourseFormatsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCourseStatementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "deleteCourseStatementResponse")
    public JAXBElement<DeleteCourseStatementResponse> createDeleteCourseStatementResponse(DeleteCourseStatementResponse value) {
        return new JAXBElement<DeleteCourseStatementResponse>(_DeleteCourseStatementResponse_QNAME, DeleteCourseStatementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCourse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "createCourse")
    public JAXBElement<CreateCourse> createCreateCourse(CreateCourse value) {
        return new JAXBElement<CreateCourse>(_CreateCourse_QNAME, CreateCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourse")
    public JAXBElement<GetCourse> createGetCourse(GetCourse value) {
        return new JAXBElement<GetCourse>(_GetCourse_QNAME, GetCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseFormats }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseFormats")
    public JAXBElement<GetCourseFormats> createGetCourseFormats(GetCourseFormats value) {
        return new JAXBElement<GetCourseFormats>(_GetCourseFormats_QNAME, GetCourseFormats.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CircularRelationshipException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "CircularRelationshipException")
    public JAXBElement<CircularRelationshipException> createCircularRelationshipException(CircularRelationshipException value) {
        return new JAXBElement<CircularRelationshipException>(_CircularRelationshipException_QNAME, CircularRelationshipException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseLosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseLosResponse")
    public JAXBElement<GetCourseLosResponse> createGetCourseLosResponse(GetCourseLosResponse value) {
        return new JAXBElement<GetCourseLosResponse>(_GetCourseLosResponse_QNAME, GetCourseLosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCourseStatement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "createCourseStatement")
    public JAXBElement<CreateCourseStatement> createCreateCourseStatement(CreateCourseStatement value) {
        return new JAXBElement<CreateCourseStatement>(_CreateCourseStatement_QNAME, CreateCourseStatement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseStatementsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseStatementsResponse")
    public JAXBElement<GetCourseStatementsResponse> createGetCourseStatementsResponse(GetCourseStatementsResponse value) {
        return new JAXBElement<GetCourseStatementsResponse>(_GetCourseStatementsResponse_QNAME, GetCourseStatementsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCourseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "deleteCourseResponse")
    public JAXBElement<DeleteCourseResponse> createDeleteCourseResponse(DeleteCourseResponse value) {
        return new JAXBElement<DeleteCourseResponse>(_DeleteCourseResponse_QNAME, DeleteCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCourseStatement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "updateCourseStatement")
    public JAXBElement<UpdateCourseStatement> createUpdateCourseStatement(UpdateCourseStatement value) {
        return new JAXBElement<UpdateCourseStatement>(_UpdateCourseStatement_QNAME, UpdateCourseStatement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCourseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "updateCourseResponse")
    public JAXBElement<UpdateCourseResponse> createUpdateCourseResponse(UpdateCourseResponse value) {
        return new JAXBElement<UpdateCourseResponse>(_UpdateCourseResponse_QNAME, UpdateCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "AlreadyExistsException")
    public JAXBElement<AlreadyExistsException> createAlreadyExistsException(AlreadyExistsException value) {
        return new JAXBElement<AlreadyExistsException>(_AlreadyExistsException_QNAME, AlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "PermissionDeniedException")
    public JAXBElement<PermissionDeniedException> createPermissionDeniedException(PermissionDeniedException value) {
        return new JAXBElement<PermissionDeniedException>(_PermissionDeniedException_QNAME, PermissionDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateCourseStatementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "validateCourseStatementResponse")
    public JAXBElement<ValidateCourseStatementResponse> createValidateCourseStatementResponse(ValidateCourseStatementResponse value) {
        return new JAXBElement<ValidateCourseStatementResponse>(_ValidateCourseStatementResponse_QNAME, ValidateCourseStatementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateCourseStatement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "validateCourseStatement")
    public JAXBElement<ValidateCourseStatement> createValidateCourseStatement(ValidateCourseStatement value) {
        return new JAXBElement<ValidateCourseStatement>(_ValidateCourseStatement_QNAME, ValidateCourseStatement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionMismatchException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "VersionMismatchException")
    public JAXBElement<VersionMismatchException> createVersionMismatchException(VersionMismatchException value) {
        return new JAXBElement<VersionMismatchException>(_VersionMismatchException_QNAME, VersionMismatchException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCourseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "createCourseResponse")
    public JAXBElement<CreateCourseResponse> createCreateCourseResponse(CreateCourseResponse value) {
        return new JAXBElement<CreateCourseResponse>(_CreateCourseResponse_QNAME, CreateCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataValidationErrorException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "DataValidationErrorException")
    public JAXBElement<DataValidationErrorException> createDataValidationErrorException(DataValidationErrorException value) {
        return new JAXBElement<DataValidationErrorException>(_DataValidationErrorException_QNAME, DataValidationErrorException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getObjectStructure")
    public JAXBElement<GetObjectStructure> createGetObjectStructure(GetObjectStructure value) {
        return new JAXBElement<GetObjectStructure>(_GetObjectStructure_QNAME, GetObjectStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getObjectTypesResponse")
    public JAXBElement<GetObjectTypesResponse> createGetObjectTypesResponse(GetObjectTypesResponse value) {
        return new JAXBElement<GetObjectTypesResponse>(_GetObjectTypesResponse_QNAME, GetObjectTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateCourse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "validateCourse")
    public JAXBElement<ValidateCourse> createValidateCourse(ValidateCourse value) {
        return new JAXBElement<ValidateCourse>(_ValidateCourse_QNAME, ValidateCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getObjectStructureResponse")
    public JAXBElement<GetObjectStructureResponse> createGetObjectStructureResponse(GetObjectStructureResponse value) {
        return new JAXBElement<GetObjectStructureResponse>(_GetObjectStructureResponse_QNAME, GetObjectStructureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCourse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "updateCourse")
    public JAXBElement<UpdateCourse> createUpdateCourse(UpdateCourse value) {
        return new JAXBElement<UpdateCourse>(_UpdateCourse_QNAME, UpdateCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsupportedActionException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "UnsupportedActionException")
    public JAXBElement<UnsupportedActionException> createUnsupportedActionException(UnsupportedActionException value) {
        return new JAXBElement<UnsupportedActionException>(_UnsupportedActionException_QNAME, UnsupportedActionException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DependentObjectsExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "DependentObjectsExistException")
    public JAXBElement<DependentObjectsExistException> createDependentObjectsExistException(DependentObjectsExistException value) {
        return new JAXBElement<DependentObjectsExistException>(_DependentObjectsExistException_QNAME, DependentObjectsExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCourseStatementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "createCourseStatementResponse")
    public JAXBElement<CreateCourseStatementResponse> createCreateCourseStatementResponse(CreateCourseStatementResponse value) {
        return new JAXBElement<CreateCourseStatementResponse>(_CreateCourseStatementResponse_QNAME, CreateCourseStatementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCourseActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/course", name = "getCourseActivitiesResponse")
    public JAXBElement<GetCourseActivitiesResponse> createGetCourseActivitiesResponse(GetCourseActivitiesResponse value) {
        return new JAXBElement<GetCourseActivitiesResponse>(_GetCourseActivitiesResponse_QNAME, GetCourseActivitiesResponse.class, null, value);
    }

}
