
package org.kuali.student.wsdl.comment;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.commentservice.AddComment;
import org.kuali.student.wsdl.commentservice.AddCommentResponse;
import org.kuali.student.wsdl.commentservice.AddTag;
import org.kuali.student.wsdl.commentservice.AddTagResponse;
import org.kuali.student.wsdl.commentservice.GetComment;
import org.kuali.student.wsdl.commentservice.GetCommentResponse;
import org.kuali.student.wsdl.commentservice.GetCommentTypes;
import org.kuali.student.wsdl.commentservice.GetCommentTypesForReferenceType;
import org.kuali.student.wsdl.commentservice.GetCommentTypesForReferenceTypeResponse;
import org.kuali.student.wsdl.commentservice.GetCommentTypesResponse;
import org.kuali.student.wsdl.commentservice.GetComments;
import org.kuali.student.wsdl.commentservice.GetCommentsByType;
import org.kuali.student.wsdl.commentservice.GetCommentsByTypeResponse;
import org.kuali.student.wsdl.commentservice.GetCommentsResponse;
import org.kuali.student.wsdl.commentservice.GetReferenceTypes;
import org.kuali.student.wsdl.commentservice.GetReferenceTypesResponse;
import org.kuali.student.wsdl.commentservice.GetTag;
import org.kuali.student.wsdl.commentservice.GetTagResponse;
import org.kuali.student.wsdl.commentservice.GetTagTypes;
import org.kuali.student.wsdl.commentservice.GetTagTypesResponse;
import org.kuali.student.wsdl.commentservice.GetTags;
import org.kuali.student.wsdl.commentservice.GetTagsByType;
import org.kuali.student.wsdl.commentservice.GetTagsByTypeResponse;
import org.kuali.student.wsdl.commentservice.GetTagsResponse;
import org.kuali.student.wsdl.commentservice.RemoveComment;
import org.kuali.student.wsdl.commentservice.RemoveCommentResponse;
import org.kuali.student.wsdl.commentservice.RemoveComments;
import org.kuali.student.wsdl.commentservice.RemoveCommentsResponse;
import org.kuali.student.wsdl.commentservice.RemoveTag;
import org.kuali.student.wsdl.commentservice.RemoveTagResponse;
import org.kuali.student.wsdl.commentservice.RemoveTags;
import org.kuali.student.wsdl.commentservice.RemoveTagsResponse;
import org.kuali.student.wsdl.commentservice.UpdateComment;
import org.kuali.student.wsdl.commentservice.UpdateCommentResponse;
import org.kuali.student.wsdl.commentservice.ValidateComment;
import org.kuali.student.wsdl.commentservice.ValidateCommentResponse;
import org.kuali.student.wsdl.dictionary.GetObjectStructure;
import org.kuali.student.wsdl.dictionary.GetObjectStructureResponse;
import org.kuali.student.wsdl.dictionary.GetObjectTypes;
import org.kuali.student.wsdl.dictionary.GetObjectTypesResponse;
import org.kuali.student.wsdl.exceptions.AlreadyExistsException;
import org.kuali.student.wsdl.exceptions.DataValidationErrorException;
import org.kuali.student.wsdl.exceptions.DoesNotExistException;
import org.kuali.student.wsdl.exceptions.InvalidParameterException;
import org.kuali.student.wsdl.exceptions.MissingParameterException;
import org.kuali.student.wsdl.exceptions.OperationFailedException;
import org.kuali.student.wsdl.exceptions.PermissionDeniedException;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.comment package. 
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

    private final static QName _RemoveComments_QNAME = new QName("http://student.kuali.org/wsdl/comment", "removeComments");
    private final static QName _GetTagsResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getTagsResponse");
    private final static QName _AddTagResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "addTagResponse");
    private final static QName _GetTagsByTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getTagsByTypeResponse");
    private final static QName _GetComments_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getComments");
    private final static QName _GetCommentTypesForReferenceTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getCommentTypesForReferenceTypeResponse");
    private final static QName _ValidateCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "validateCommentResponse");
    private final static QName _RemoveCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "removeCommentResponse");
    private final static QName _GetCommentTypesForReferenceType_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getCommentTypesForReferenceType");
    private final static QName _DataValidationErrorException_QNAME = new QName("http://student.kuali.org/wsdl/comment", "DataValidationErrorException");
    private final static QName _GetObjectStructure_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getObjectStructure");
    private final static QName _AlreadyExistsException_QNAME = new QName("http://student.kuali.org/wsdl/comment", "AlreadyExistsException");
    private final static QName _GetReferenceTypes_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getReferenceTypes");
    private final static QName _OperationFailedException_QNAME = new QName("http://student.kuali.org/wsdl/comment", "OperationFailedException");
    private final static QName _PermissionDeniedException_QNAME = new QName("http://student.kuali.org/wsdl/comment", "PermissionDeniedException");
    private final static QName _GetCommentsResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getCommentsResponse");
    private final static QName _GetTag_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getTag");
    private final static QName _ValidateComment_QNAME = new QName("http://student.kuali.org/wsdl/comment", "validateComment");
    private final static QName _RemoveTag_QNAME = new QName("http://student.kuali.org/wsdl/comment", "removeTag");
    private final static QName _GetObjectTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getObjectTypesResponse");
    private final static QName _GetObjectStructureResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getObjectStructureResponse");
    private final static QName _GetComment_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getComment");
    private final static QName _UpdateComment_QNAME = new QName("http://student.kuali.org/wsdl/comment", "updateComment");
    private final static QName _GetCommentTypes_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getCommentTypes");
    private final static QName _GetTagResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getTagResponse");
    private final static QName _AddTag_QNAME = new QName("http://student.kuali.org/wsdl/comment", "addTag");
    private final static QName _AddCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "addCommentResponse");
    private final static QName _GetCommentsByTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getCommentsByTypeResponse");
    private final static QName _GetCommentsByType_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getCommentsByType");
    private final static QName _InvalidParameterException_QNAME = new QName("http://student.kuali.org/wsdl/comment", "InvalidParameterException");
    private final static QName _GetObjectTypes_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getObjectTypes");
    private final static QName _RemoveComment_QNAME = new QName("http://student.kuali.org/wsdl/comment", "removeComment");
    private final static QName _GetTagTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getTagTypesResponse");
    private final static QName _GetCommentTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getCommentTypesResponse");
    private final static QName _AddComment_QNAME = new QName("http://student.kuali.org/wsdl/comment", "addComment");
    private final static QName _UpdateCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "updateCommentResponse");
    private final static QName _RemoveCommentsResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "removeCommentsResponse");
    private final static QName _MissingParameterException_QNAME = new QName("http://student.kuali.org/wsdl/comment", "MissingParameterException");
    private final static QName _RemoveTagResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "removeTagResponse");
    private final static QName _GetReferenceTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getReferenceTypesResponse");
    private final static QName _DoesNotExistException_QNAME = new QName("http://student.kuali.org/wsdl/comment", "DoesNotExistException");
    private final static QName _RemoveTagsResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "removeTagsResponse");
    private final static QName _GetTagTypes_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getTagTypes");
    private final static QName _RemoveTags_QNAME = new QName("http://student.kuali.org/wsdl/comment", "removeTags");
    private final static QName _GetCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getCommentResponse");
    private final static QName _GetTags_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getTags");
    private final static QName _GetTagsByType_QNAME = new QName("http://student.kuali.org/wsdl/comment", "getTagsByType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.comment
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CommentTypeInfo }
     * 
     */
    public CommentTypeInfo createCommentTypeInfo() {
        return new CommentTypeInfo();
    }

    /**
     * Create an instance of {@link TagTypeInfo }
     * 
     */
    public TagTypeInfo createTagTypeInfo() {
        return new TagTypeInfo();
    }

    /**
     * Create an instance of {@link TagInfo }
     * 
     */
    public TagInfo createTagInfo() {
        return new TagInfo();
    }

    /**
     * Create an instance of {@link CommentInfo }
     * 
     */
    public CommentInfo createCommentInfo() {
        return new CommentInfo();
    }

    /**
     * Create an instance of {@link ValidationResultInfo }
     * 
     */
    public ValidationResultInfo createValidationResultInfo() {
        return new ValidationResultInfo();
    }

    /**
     * Create an instance of {@link MetaInfo }
     * 
     */
    public MetaInfo createMetaInfo() {
        return new MetaInfo();
    }

    /**
     * Create an instance of {@link ReferenceTypeInfo }
     * 
     */
    public ReferenceTypeInfo createReferenceTypeInfo() {
        return new ReferenceTypeInfo();
    }

    /**
     * Create an instance of {@link JaxbAttributeList }
     * 
     */
    public JaxbAttributeList createJaxbAttributeList() {
        return new JaxbAttributeList();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link StatusInfo }
     * 
     */
    public StatusInfo createStatusInfo() {
        return new StatusInfo();
    }

    /**
     * Create an instance of {@link RichTextInfo }
     * 
     */
    public RichTextInfo createRichTextInfo() {
        return new RichTextInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveComments }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "removeComments")
    public JAXBElement<RemoveComments> createRemoveComments(RemoveComments value) {
        return new JAXBElement<RemoveComments>(_RemoveComments_QNAME, RemoveComments.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getTagsResponse")
    public JAXBElement<GetTagsResponse> createGetTagsResponse(GetTagsResponse value) {
        return new JAXBElement<GetTagsResponse>(_GetTagsResponse_QNAME, GetTagsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTagResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "addTagResponse")
    public JAXBElement<AddTagResponse> createAddTagResponse(AddTagResponse value) {
        return new JAXBElement<AddTagResponse>(_AddTagResponse_QNAME, AddTagResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagsByTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getTagsByTypeResponse")
    public JAXBElement<GetTagsByTypeResponse> createGetTagsByTypeResponse(GetTagsByTypeResponse value) {
        return new JAXBElement<GetTagsByTypeResponse>(_GetTagsByTypeResponse_QNAME, GetTagsByTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComments }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getComments")
    public JAXBElement<GetComments> createGetComments(GetComments value) {
        return new JAXBElement<GetComments>(_GetComments_QNAME, GetComments.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentTypesForReferenceTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getCommentTypesForReferenceTypeResponse")
    public JAXBElement<GetCommentTypesForReferenceTypeResponse> createGetCommentTypesForReferenceTypeResponse(GetCommentTypesForReferenceTypeResponse value) {
        return new JAXBElement<GetCommentTypesForReferenceTypeResponse>(_GetCommentTypesForReferenceTypeResponse_QNAME, GetCommentTypesForReferenceTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "validateCommentResponse")
    public JAXBElement<ValidateCommentResponse> createValidateCommentResponse(ValidateCommentResponse value) {
        return new JAXBElement<ValidateCommentResponse>(_ValidateCommentResponse_QNAME, ValidateCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "removeCommentResponse")
    public JAXBElement<RemoveCommentResponse> createRemoveCommentResponse(RemoveCommentResponse value) {
        return new JAXBElement<RemoveCommentResponse>(_RemoveCommentResponse_QNAME, RemoveCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentTypesForReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getCommentTypesForReferenceType")
    public JAXBElement<GetCommentTypesForReferenceType> createGetCommentTypesForReferenceType(GetCommentTypesForReferenceType value) {
        return new JAXBElement<GetCommentTypesForReferenceType>(_GetCommentTypesForReferenceType_QNAME, GetCommentTypesForReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataValidationErrorException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "DataValidationErrorException")
    public JAXBElement<DataValidationErrorException> createDataValidationErrorException(DataValidationErrorException value) {
        return new JAXBElement<DataValidationErrorException>(_DataValidationErrorException_QNAME, DataValidationErrorException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getObjectStructure")
    public JAXBElement<GetObjectStructure> createGetObjectStructure(GetObjectStructure value) {
        return new JAXBElement<GetObjectStructure>(_GetObjectStructure_QNAME, GetObjectStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "AlreadyExistsException")
    public JAXBElement<AlreadyExistsException> createAlreadyExistsException(AlreadyExistsException value) {
        return new JAXBElement<AlreadyExistsException>(_AlreadyExistsException_QNAME, AlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReferenceTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getReferenceTypes")
    public JAXBElement<GetReferenceTypes> createGetReferenceTypes(GetReferenceTypes value) {
        return new JAXBElement<GetReferenceTypes>(_GetReferenceTypes_QNAME, GetReferenceTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "PermissionDeniedException")
    public JAXBElement<PermissionDeniedException> createPermissionDeniedException(PermissionDeniedException value) {
        return new JAXBElement<PermissionDeniedException>(_PermissionDeniedException_QNAME, PermissionDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getCommentsResponse")
    public JAXBElement<GetCommentsResponse> createGetCommentsResponse(GetCommentsResponse value) {
        return new JAXBElement<GetCommentsResponse>(_GetCommentsResponse_QNAME, GetCommentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTag }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getTag")
    public JAXBElement<GetTag> createGetTag(GetTag value) {
        return new JAXBElement<GetTag>(_GetTag_QNAME, GetTag.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "validateComment")
    public JAXBElement<ValidateComment> createValidateComment(ValidateComment value) {
        return new JAXBElement<ValidateComment>(_ValidateComment_QNAME, ValidateComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTag }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "removeTag")
    public JAXBElement<RemoveTag> createRemoveTag(RemoveTag value) {
        return new JAXBElement<RemoveTag>(_RemoveTag_QNAME, RemoveTag.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getObjectTypesResponse")
    public JAXBElement<GetObjectTypesResponse> createGetObjectTypesResponse(GetObjectTypesResponse value) {
        return new JAXBElement<GetObjectTypesResponse>(_GetObjectTypesResponse_QNAME, GetObjectTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getObjectStructureResponse")
    public JAXBElement<GetObjectStructureResponse> createGetObjectStructureResponse(GetObjectStructureResponse value) {
        return new JAXBElement<GetObjectStructureResponse>(_GetObjectStructureResponse_QNAME, GetObjectStructureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getComment")
    public JAXBElement<GetComment> createGetComment(GetComment value) {
        return new JAXBElement<GetComment>(_GetComment_QNAME, GetComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "updateComment")
    public JAXBElement<UpdateComment> createUpdateComment(UpdateComment value) {
        return new JAXBElement<UpdateComment>(_UpdateComment_QNAME, UpdateComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getCommentTypes")
    public JAXBElement<GetCommentTypes> createGetCommentTypes(GetCommentTypes value) {
        return new JAXBElement<GetCommentTypes>(_GetCommentTypes_QNAME, GetCommentTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getTagResponse")
    public JAXBElement<GetTagResponse> createGetTagResponse(GetTagResponse value) {
        return new JAXBElement<GetTagResponse>(_GetTagResponse_QNAME, GetTagResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTag }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "addTag")
    public JAXBElement<AddTag> createAddTag(AddTag value) {
        return new JAXBElement<AddTag>(_AddTag_QNAME, AddTag.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "addCommentResponse")
    public JAXBElement<AddCommentResponse> createAddCommentResponse(AddCommentResponse value) {
        return new JAXBElement<AddCommentResponse>(_AddCommentResponse_QNAME, AddCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentsByTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getCommentsByTypeResponse")
    public JAXBElement<GetCommentsByTypeResponse> createGetCommentsByTypeResponse(GetCommentsByTypeResponse value) {
        return new JAXBElement<GetCommentsByTypeResponse>(_GetCommentsByTypeResponse_QNAME, GetCommentsByTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentsByType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getCommentsByType")
    public JAXBElement<GetCommentsByType> createGetCommentsByType(GetCommentsByType value) {
        return new JAXBElement<GetCommentsByType>(_GetCommentsByType_QNAME, GetCommentsByType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "InvalidParameterException")
    public JAXBElement<InvalidParameterException> createInvalidParameterException(InvalidParameterException value) {
        return new JAXBElement<InvalidParameterException>(_InvalidParameterException_QNAME, InvalidParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getObjectTypes")
    public JAXBElement<GetObjectTypes> createGetObjectTypes(GetObjectTypes value) {
        return new JAXBElement<GetObjectTypes>(_GetObjectTypes_QNAME, GetObjectTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "removeComment")
    public JAXBElement<RemoveComment> createRemoveComment(RemoveComment value) {
        return new JAXBElement<RemoveComment>(_RemoveComment_QNAME, RemoveComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getTagTypesResponse")
    public JAXBElement<GetTagTypesResponse> createGetTagTypesResponse(GetTagTypesResponse value) {
        return new JAXBElement<GetTagTypesResponse>(_GetTagTypesResponse_QNAME, GetTagTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getCommentTypesResponse")
    public JAXBElement<GetCommentTypesResponse> createGetCommentTypesResponse(GetCommentTypesResponse value) {
        return new JAXBElement<GetCommentTypesResponse>(_GetCommentTypesResponse_QNAME, GetCommentTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "addComment")
    public JAXBElement<AddComment> createAddComment(AddComment value) {
        return new JAXBElement<AddComment>(_AddComment_QNAME, AddComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "updateCommentResponse")
    public JAXBElement<UpdateCommentResponse> createUpdateCommentResponse(UpdateCommentResponse value) {
        return new JAXBElement<UpdateCommentResponse>(_UpdateCommentResponse_QNAME, UpdateCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveCommentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "removeCommentsResponse")
    public JAXBElement<RemoveCommentsResponse> createRemoveCommentsResponse(RemoveCommentsResponse value) {
        return new JAXBElement<RemoveCommentsResponse>(_RemoveCommentsResponse_QNAME, RemoveCommentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "MissingParameterException")
    public JAXBElement<MissingParameterException> createMissingParameterException(MissingParameterException value) {
        return new JAXBElement<MissingParameterException>(_MissingParameterException_QNAME, MissingParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTagResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "removeTagResponse")
    public JAXBElement<RemoveTagResponse> createRemoveTagResponse(RemoveTagResponse value) {
        return new JAXBElement<RemoveTagResponse>(_RemoveTagResponse_QNAME, RemoveTagResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReferenceTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getReferenceTypesResponse")
    public JAXBElement<GetReferenceTypesResponse> createGetReferenceTypesResponse(GetReferenceTypesResponse value) {
        return new JAXBElement<GetReferenceTypesResponse>(_GetReferenceTypesResponse_QNAME, GetReferenceTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "DoesNotExistException")
    public JAXBElement<DoesNotExistException> createDoesNotExistException(DoesNotExistException value) {
        return new JAXBElement<DoesNotExistException>(_DoesNotExistException_QNAME, DoesNotExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTagsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "removeTagsResponse")
    public JAXBElement<RemoveTagsResponse> createRemoveTagsResponse(RemoveTagsResponse value) {
        return new JAXBElement<RemoveTagsResponse>(_RemoveTagsResponse_QNAME, RemoveTagsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getTagTypes")
    public JAXBElement<GetTagTypes> createGetTagTypes(GetTagTypes value) {
        return new JAXBElement<GetTagTypes>(_GetTagTypes_QNAME, GetTagTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTags }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "removeTags")
    public JAXBElement<RemoveTags> createRemoveTags(RemoveTags value) {
        return new JAXBElement<RemoveTags>(_RemoveTags_QNAME, RemoveTags.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getCommentResponse")
    public JAXBElement<GetCommentResponse> createGetCommentResponse(GetCommentResponse value) {
        return new JAXBElement<GetCommentResponse>(_GetCommentResponse_QNAME, GetCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTags }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getTags")
    public JAXBElement<GetTags> createGetTags(GetTags value) {
        return new JAXBElement<GetTags>(_GetTags_QNAME, GetTags.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagsByType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/comment", name = "getTagsByType")
    public JAXBElement<GetTagsByType> createGetTagsByType(GetTagsByType value) {
        return new JAXBElement<GetTagsByType>(_GetTagsByType_QNAME, GetTagsByType.class, null, value);
    }

}
