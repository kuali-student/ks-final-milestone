
package org.kuali.student.wsdl.document;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.exceptions.DataValidationErrorException;
import org.kuali.student.wsdl.exceptions.DoesNotExistException;
import org.kuali.student.wsdl.exceptions.InvalidParameterException;
import org.kuali.student.wsdl.exceptions.MissingParameterException;
import org.kuali.student.wsdl.exceptions.OperationFailedException;
import org.kuali.student.wsdl.exceptions.PermissionDeniedException;
import org.kuali.student.wsdl.exceptions.VersionMismatchException;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.document package. 
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

    private final static QName _GetDocumentsByIdList_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentsByIdList");
    private final static QName _RemoveDocumentCategoryFromDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "removeDocumentCategoryFromDocumentResponse");
    private final static QName _GetDocumentTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentTypeResponse");
    private final static QName _InvalidParameterException_QNAME = new QName("http://student.kuali.org/wsdl/document", "InvalidParameterException");
    private final static QName _ValidateDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "validateDocumentResponse");
    private final static QName _AddDocumentCategoryToDocument_QNAME = new QName("http://student.kuali.org/wsdl/document", "addDocumentCategoryToDocument");
    private final static QName _RemoveDocumentCategoryFromDocument_QNAME = new QName("http://student.kuali.org/wsdl/document", "removeDocumentCategoryFromDocument");
    private final static QName _GetDocumentCategory_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentCategory");
    private final static QName _GetDocumentCategoriesResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentCategoriesResponse");
    private final static QName _GetDocumentTypes_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentTypes");
    private final static QName _GetDocumentsByIdListResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentsByIdListResponse");
    private final static QName _DeleteDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "deleteDocumentResponse");
    private final static QName _GetDocumentCategories_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentCategories");
    private final static QName _GetDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentResponse");
    private final static QName _GetDocumentTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentTypesResponse");
    private final static QName _UpdateDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "updateDocumentResponse");
    private final static QName _DeleteDocument_QNAME = new QName("http://student.kuali.org/wsdl/document", "deleteDocument");
    private final static QName _AddDocumentCategoryToDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "addDocumentCategoryToDocumentResponse");
    private final static QName _CreateDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "createDocumentResponse");
    private final static QName _GetDocument_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocument");
    private final static QName _DataValidationErrorException_QNAME = new QName("http://student.kuali.org/wsdl/document", "DataValidationErrorException");
    private final static QName _CreateDocument_QNAME = new QName("http://student.kuali.org/wsdl/document", "createDocument");
    private final static QName _UpdateDocument_QNAME = new QName("http://student.kuali.org/wsdl/document", "updateDocument");
    private final static QName _GetCategoriesByDocument_QNAME = new QName("http://student.kuali.org/wsdl/document", "getCategoriesByDocument");
    private final static QName _GetDocumentCategoryResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentCategoryResponse");
    private final static QName _GetCategoriesByDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/document", "getCategoriesByDocumentResponse");
    private final static QName _GetDocumentType_QNAME = new QName("http://student.kuali.org/wsdl/document", "getDocumentType");
    private final static QName _MissingParameterException_QNAME = new QName("http://student.kuali.org/wsdl/document", "MissingParameterException");
    private final static QName _VersionMismatchException_QNAME = new QName("http://student.kuali.org/wsdl/document", "VersionMismatchException");
    private final static QName _PermissionDeniedException_QNAME = new QName("http://student.kuali.org/wsdl/document", "PermissionDeniedException");
    private final static QName _OperationFailedException_QNAME = new QName("http://student.kuali.org/wsdl/document", "OperationFailedException");
    private final static QName _DoesNotExistException_QNAME = new QName("http://student.kuali.org/wsdl/document", "DoesNotExistException");
    private final static QName _ValidateDocument_QNAME = new QName("http://student.kuali.org/wsdl/document", "validateDocument");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.document
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateDocumentResponse }
     * 
     */
    public UpdateDocumentResponse createUpdateDocumentResponse() {
        return new UpdateDocumentResponse();
    }

    /**
     * Create an instance of {@link GetDocumentCategoriesResponse }
     * 
     */
    public GetDocumentCategoriesResponse createGetDocumentCategoriesResponse() {
        return new GetDocumentCategoriesResponse();
    }

    /**
     * Create an instance of {@link GetCategoriesByDocumentResponse }
     * 
     */
    public GetCategoriesByDocumentResponse createGetCategoriesByDocumentResponse() {
        return new GetCategoriesByDocumentResponse();
    }

    /**
     * Create an instance of {@link GetDocumentType }
     * 
     */
    public GetDocumentType createGetDocumentType() {
        return new GetDocumentType();
    }

    /**
     * Create an instance of {@link ValidateDocumentResponse }
     * 
     */
    public ValidateDocumentResponse createValidateDocumentResponse() {
        return new ValidateDocumentResponse();
    }

    /**
     * Create an instance of {@link AddDocumentCategoryToDocument }
     * 
     */
    public AddDocumentCategoryToDocument createAddDocumentCategoryToDocument() {
        return new AddDocumentCategoryToDocument();
    }

    /**
     * Create an instance of {@link GetCategoriesByDocument }
     * 
     */
    public GetCategoriesByDocument createGetCategoriesByDocument() {
        return new GetCategoriesByDocument();
    }

    /**
     * Create an instance of {@link UpdateDocument }
     * 
     */
    public UpdateDocument createUpdateDocument() {
        return new UpdateDocument();
    }

    /**
     * Create an instance of {@link DeleteDocument }
     * 
     */
    public DeleteDocument createDeleteDocument() {
        return new DeleteDocument();
    }

    /**
     * Create an instance of {@link GetDocumentCategoryResponse }
     * 
     */
    public GetDocumentCategoryResponse createGetDocumentCategoryResponse() {
        return new GetDocumentCategoryResponse();
    }

    /**
     * Create an instance of {@link CreateDocument }
     * 
     */
    public CreateDocument createCreateDocument() {
        return new CreateDocument();
    }

    /**
     * Create an instance of {@link GetDocumentCategories }
     * 
     */
    public GetDocumentCategories createGetDocumentCategories() {
        return new GetDocumentCategories();
    }

    /**
     * Create an instance of {@link GetDocumentTypeResponse }
     * 
     */
    public GetDocumentTypeResponse createGetDocumentTypeResponse() {
        return new GetDocumentTypeResponse();
    }

    /**
     * Create an instance of {@link ValidationResultInfo }
     * 
     */
    public ValidationResultInfo createValidationResultInfo() {
        return new ValidationResultInfo();
    }

    /**
     * Create an instance of {@link GetDocument }
     * 
     */
    public GetDocument createGetDocument() {
        return new GetDocument();
    }

    /**
     * Create an instance of {@link GetDocumentTypesResponse }
     * 
     */
    public GetDocumentTypesResponse createGetDocumentTypesResponse() {
        return new GetDocumentTypesResponse();
    }

    /**
     * Create an instance of {@link GetDocumentTypes }
     * 
     */
    public GetDocumentTypes createGetDocumentTypes() {
        return new GetDocumentTypes();
    }

    /**
     * Create an instance of {@link JaxbAttributeList }
     * 
     */
    public JaxbAttributeList createJaxbAttributeList() {
        return new JaxbAttributeList();
    }

    /**
     * Create an instance of {@link RemoveDocumentCategoryFromDocumentResponse }
     * 
     */
    public RemoveDocumentCategoryFromDocumentResponse createRemoveDocumentCategoryFromDocumentResponse() {
        return new RemoveDocumentCategoryFromDocumentResponse();
    }

    /**
     * Create an instance of {@link GetDocumentCategory }
     * 
     */
    public GetDocumentCategory createGetDocumentCategory() {
        return new GetDocumentCategory();
    }

    /**
     * Create an instance of {@link DocumentInfo }
     * 
     */
    public DocumentInfo createDocumentInfo() {
        return new DocumentInfo();
    }

    /**
     * Create an instance of {@link GetDocumentsByIdList }
     * 
     */
    public GetDocumentsByIdList createGetDocumentsByIdList() {
        return new GetDocumentsByIdList();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link DeleteDocumentResponse }
     * 
     */
    public DeleteDocumentResponse createDeleteDocumentResponse() {
        return new DeleteDocumentResponse();
    }

    /**
     * Create an instance of {@link ValidateDocument }
     * 
     */
    public ValidateDocument createValidateDocument() {
        return new ValidateDocument();
    }

    /**
     * Create an instance of {@link RichTextInfo }
     * 
     */
    public RichTextInfo createRichTextInfo() {
        return new RichTextInfo();
    }

    /**
     * Create an instance of {@link RemoveDocumentCategoryFromDocument }
     * 
     */
    public RemoveDocumentCategoryFromDocument createRemoveDocumentCategoryFromDocument() {
        return new RemoveDocumentCategoryFromDocument();
    }

    /**
     * Create an instance of {@link GetDocumentResponse }
     * 
     */
    public GetDocumentResponse createGetDocumentResponse() {
        return new GetDocumentResponse();
    }

    /**
     * Create an instance of {@link StatusInfo }
     * 
     */
    public StatusInfo createStatusInfo() {
        return new StatusInfo();
    }

    /**
     * Create an instance of {@link MetaInfo }
     * 
     */
    public MetaInfo createMetaInfo() {
        return new MetaInfo();
    }

    /**
     * Create an instance of {@link DocumentCategoryInfo }
     * 
     */
    public DocumentCategoryInfo createDocumentCategoryInfo() {
        return new DocumentCategoryInfo();
    }

    /**
     * Create an instance of {@link DocumentTypeInfo }
     * 
     */
    public DocumentTypeInfo createDocumentTypeInfo() {
        return new DocumentTypeInfo();
    }

    /**
     * Create an instance of {@link DocumentBinaryInfo }
     * 
     */
    public DocumentBinaryInfo createDocumentBinaryInfo() {
        return new DocumentBinaryInfo();
    }

    /**
     * Create an instance of {@link CreateDocumentResponse }
     * 
     */
    public CreateDocumentResponse createCreateDocumentResponse() {
        return new CreateDocumentResponse();
    }

    /**
     * Create an instance of {@link AddDocumentCategoryToDocumentResponse }
     * 
     */
    public AddDocumentCategoryToDocumentResponse createAddDocumentCategoryToDocumentResponse() {
        return new AddDocumentCategoryToDocumentResponse();
    }

    /**
     * Create an instance of {@link GetDocumentsByIdListResponse }
     * 
     */
    public GetDocumentsByIdListResponse createGetDocumentsByIdListResponse() {
        return new GetDocumentsByIdListResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentsByIdList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentsByIdList")
    public JAXBElement<GetDocumentsByIdList> createGetDocumentsByIdList(GetDocumentsByIdList value) {
        return new JAXBElement<GetDocumentsByIdList>(_GetDocumentsByIdList_QNAME, GetDocumentsByIdList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDocumentCategoryFromDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "removeDocumentCategoryFromDocumentResponse")
    public JAXBElement<RemoveDocumentCategoryFromDocumentResponse> createRemoveDocumentCategoryFromDocumentResponse(RemoveDocumentCategoryFromDocumentResponse value) {
        return new JAXBElement<RemoveDocumentCategoryFromDocumentResponse>(_RemoveDocumentCategoryFromDocumentResponse_QNAME, RemoveDocumentCategoryFromDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentTypeResponse")
    public JAXBElement<GetDocumentTypeResponse> createGetDocumentTypeResponse(GetDocumentTypeResponse value) {
        return new JAXBElement<GetDocumentTypeResponse>(_GetDocumentTypeResponse_QNAME, GetDocumentTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "InvalidParameterException")
    public JAXBElement<InvalidParameterException> createInvalidParameterException(InvalidParameterException value) {
        return new JAXBElement<InvalidParameterException>(_InvalidParameterException_QNAME, InvalidParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "validateDocumentResponse")
    public JAXBElement<ValidateDocumentResponse> createValidateDocumentResponse(ValidateDocumentResponse value) {
        return new JAXBElement<ValidateDocumentResponse>(_ValidateDocumentResponse_QNAME, ValidateDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDocumentCategoryToDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "addDocumentCategoryToDocument")
    public JAXBElement<AddDocumentCategoryToDocument> createAddDocumentCategoryToDocument(AddDocumentCategoryToDocument value) {
        return new JAXBElement<AddDocumentCategoryToDocument>(_AddDocumentCategoryToDocument_QNAME, AddDocumentCategoryToDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDocumentCategoryFromDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "removeDocumentCategoryFromDocument")
    public JAXBElement<RemoveDocumentCategoryFromDocument> createRemoveDocumentCategoryFromDocument(RemoveDocumentCategoryFromDocument value) {
        return new JAXBElement<RemoveDocumentCategoryFromDocument>(_RemoveDocumentCategoryFromDocument_QNAME, RemoveDocumentCategoryFromDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentCategory")
    public JAXBElement<GetDocumentCategory> createGetDocumentCategory(GetDocumentCategory value) {
        return new JAXBElement<GetDocumentCategory>(_GetDocumentCategory_QNAME, GetDocumentCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentCategoriesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentCategoriesResponse")
    public JAXBElement<GetDocumentCategoriesResponse> createGetDocumentCategoriesResponse(GetDocumentCategoriesResponse value) {
        return new JAXBElement<GetDocumentCategoriesResponse>(_GetDocumentCategoriesResponse_QNAME, GetDocumentCategoriesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentTypes")
    public JAXBElement<GetDocumentTypes> createGetDocumentTypes(GetDocumentTypes value) {
        return new JAXBElement<GetDocumentTypes>(_GetDocumentTypes_QNAME, GetDocumentTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentsByIdListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentsByIdListResponse")
    public JAXBElement<GetDocumentsByIdListResponse> createGetDocumentsByIdListResponse(GetDocumentsByIdListResponse value) {
        return new JAXBElement<GetDocumentsByIdListResponse>(_GetDocumentsByIdListResponse_QNAME, GetDocumentsByIdListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "deleteDocumentResponse")
    public JAXBElement<DeleteDocumentResponse> createDeleteDocumentResponse(DeleteDocumentResponse value) {
        return new JAXBElement<DeleteDocumentResponse>(_DeleteDocumentResponse_QNAME, DeleteDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentCategories }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentCategories")
    public JAXBElement<GetDocumentCategories> createGetDocumentCategories(GetDocumentCategories value) {
        return new JAXBElement<GetDocumentCategories>(_GetDocumentCategories_QNAME, GetDocumentCategories.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentResponse")
    public JAXBElement<GetDocumentResponse> createGetDocumentResponse(GetDocumentResponse value) {
        return new JAXBElement<GetDocumentResponse>(_GetDocumentResponse_QNAME, GetDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentTypesResponse")
    public JAXBElement<GetDocumentTypesResponse> createGetDocumentTypesResponse(GetDocumentTypesResponse value) {
        return new JAXBElement<GetDocumentTypesResponse>(_GetDocumentTypesResponse_QNAME, GetDocumentTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "updateDocumentResponse")
    public JAXBElement<UpdateDocumentResponse> createUpdateDocumentResponse(UpdateDocumentResponse value) {
        return new JAXBElement<UpdateDocumentResponse>(_UpdateDocumentResponse_QNAME, UpdateDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "deleteDocument")
    public JAXBElement<DeleteDocument> createDeleteDocument(DeleteDocument value) {
        return new JAXBElement<DeleteDocument>(_DeleteDocument_QNAME, DeleteDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDocumentCategoryToDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "addDocumentCategoryToDocumentResponse")
    public JAXBElement<AddDocumentCategoryToDocumentResponse> createAddDocumentCategoryToDocumentResponse(AddDocumentCategoryToDocumentResponse value) {
        return new JAXBElement<AddDocumentCategoryToDocumentResponse>(_AddDocumentCategoryToDocumentResponse_QNAME, AddDocumentCategoryToDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "createDocumentResponse")
    public JAXBElement<CreateDocumentResponse> createCreateDocumentResponse(CreateDocumentResponse value) {
        return new JAXBElement<CreateDocumentResponse>(_CreateDocumentResponse_QNAME, CreateDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocument")
    public JAXBElement<GetDocument> createGetDocument(GetDocument value) {
        return new JAXBElement<GetDocument>(_GetDocument_QNAME, GetDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataValidationErrorException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "DataValidationErrorException")
    public JAXBElement<DataValidationErrorException> createDataValidationErrorException(DataValidationErrorException value) {
        return new JAXBElement<DataValidationErrorException>(_DataValidationErrorException_QNAME, DataValidationErrorException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "createDocument")
    public JAXBElement<CreateDocument> createCreateDocument(CreateDocument value) {
        return new JAXBElement<CreateDocument>(_CreateDocument_QNAME, CreateDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "updateDocument")
    public JAXBElement<UpdateDocument> createUpdateDocument(UpdateDocument value) {
        return new JAXBElement<UpdateDocument>(_UpdateDocument_QNAME, UpdateDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCategoriesByDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getCategoriesByDocument")
    public JAXBElement<GetCategoriesByDocument> createGetCategoriesByDocument(GetCategoriesByDocument value) {
        return new JAXBElement<GetCategoriesByDocument>(_GetCategoriesByDocument_QNAME, GetCategoriesByDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentCategoryResponse")
    public JAXBElement<GetDocumentCategoryResponse> createGetDocumentCategoryResponse(GetDocumentCategoryResponse value) {
        return new JAXBElement<GetDocumentCategoryResponse>(_GetDocumentCategoryResponse_QNAME, GetDocumentCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCategoriesByDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getCategoriesByDocumentResponse")
    public JAXBElement<GetCategoriesByDocumentResponse> createGetCategoriesByDocumentResponse(GetCategoriesByDocumentResponse value) {
        return new JAXBElement<GetCategoriesByDocumentResponse>(_GetCategoriesByDocumentResponse_QNAME, GetCategoriesByDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "getDocumentType")
    public JAXBElement<GetDocumentType> createGetDocumentType(GetDocumentType value) {
        return new JAXBElement<GetDocumentType>(_GetDocumentType_QNAME, GetDocumentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "MissingParameterException")
    public JAXBElement<MissingParameterException> createMissingParameterException(MissingParameterException value) {
        return new JAXBElement<MissingParameterException>(_MissingParameterException_QNAME, MissingParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionMismatchException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "VersionMismatchException")
    public JAXBElement<VersionMismatchException> createVersionMismatchException(VersionMismatchException value) {
        return new JAXBElement<VersionMismatchException>(_VersionMismatchException_QNAME, VersionMismatchException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "PermissionDeniedException")
    public JAXBElement<PermissionDeniedException> createPermissionDeniedException(PermissionDeniedException value) {
        return new JAXBElement<PermissionDeniedException>(_PermissionDeniedException_QNAME, PermissionDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "DoesNotExistException")
    public JAXBElement<DoesNotExistException> createDoesNotExistException(DoesNotExistException value) {
        return new JAXBElement<DoesNotExistException>(_DoesNotExistException_QNAME, DoesNotExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/document", name = "validateDocument")
    public JAXBElement<ValidateDocument> createValidateDocument(ValidateDocument value) {
        return new JAXBElement<ValidateDocument>(_ValidateDocument_QNAME, ValidateDocument.class, null, value);
    }

}
