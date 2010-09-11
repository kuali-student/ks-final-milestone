
package org.kuali.student.wsdl.commentservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.commentservice package. 
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

    private final static QName _AddTag_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "addTag");
    private final static QName _AddCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "addCommentResponse");
    private final static QName _RemoveComment_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "removeComment");
    private final static QName _GetCommentsByTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getCommentsByTypeResponse");
    private final static QName _GetCommentsByType_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getCommentsByType");
    private final static QName _GetCommentTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getCommentTypesResponse");
    private final static QName _GetTagTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getTagTypesResponse");
    private final static QName _AddComment_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "addComment");
    private final static QName _UpdateCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "updateCommentResponse");
    private final static QName _RemoveCommentsResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "removeCommentsResponse");
    private final static QName _GetReferenceTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getReferenceTypesResponse");
    private final static QName _RemoveTagResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "removeTagResponse");
    private final static QName _GetTagTypes_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getTagTypes");
    private final static QName _RemoveTagsResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "removeTagsResponse");
    private final static QName _GetTags_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getTags");
    private final static QName _GetTagsByType_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getTagsByType");
    private final static QName _RemoveTags_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "removeTags");
    private final static QName _GetCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getCommentResponse");
    private final static QName _RemoveComments_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "removeComments");
    private final static QName _GetTagsByTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getTagsByTypeResponse");
    private final static QName _GetTagsResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getTagsResponse");
    private final static QName _AddTagResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "addTagResponse");
    private final static QName _GetCommentTypesForReferenceTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getCommentTypesForReferenceTypeResponse");
    private final static QName _GetComments_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getComments");
    private final static QName _GetCommentTypesForReferenceType_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getCommentTypesForReferenceType");
    private final static QName _ValidateCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "validateCommentResponse");
    private final static QName _RemoveCommentResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "removeCommentResponse");
    private final static QName _GetReferenceTypes_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getReferenceTypes");
    private final static QName _GetTag_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getTag");
    private final static QName _ValidateComment_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "validateComment");
    private final static QName _RemoveTag_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "removeTag");
    private final static QName _GetCommentsResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getCommentsResponse");
    private final static QName _GetComment_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getComment");
    private final static QName _UpdateComment_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "updateComment");
    private final static QName _GetTagResponse_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getTagResponse");
    private final static QName _GetCommentTypes_QNAME = new QName("http://student.kuali.org/wsdl/commentService", "getCommentTypes");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.commentservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateCommentResponse }
     * 
     */
    public ValidateCommentResponse createValidateCommentResponse() {
        return new ValidateCommentResponse();
    }

    /**
     * Create an instance of {@link UpdateCommentResponse }
     * 
     */
    public UpdateCommentResponse createUpdateCommentResponse() {
        return new UpdateCommentResponse();
    }

    /**
     * Create an instance of {@link GetCommentsResponse }
     * 
     */
    public GetCommentsResponse createGetCommentsResponse() {
        return new GetCommentsResponse();
    }

    /**
     * Create an instance of {@link RemoveCommentResponse }
     * 
     */
    public RemoveCommentResponse createRemoveCommentResponse() {
        return new RemoveCommentResponse();
    }

    /**
     * Create an instance of {@link AddCommentResponse }
     * 
     */
    public AddCommentResponse createAddCommentResponse() {
        return new AddCommentResponse();
    }

    /**
     * Create an instance of {@link GetTagResponse }
     * 
     */
    public GetTagResponse createGetTagResponse() {
        return new GetTagResponse();
    }

    /**
     * Create an instance of {@link RemoveComments }
     * 
     */
    public RemoveComments createRemoveComments() {
        return new RemoveComments();
    }

    /**
     * Create an instance of {@link GetTagsByTypeResponse }
     * 
     */
    public GetTagsByTypeResponse createGetTagsByTypeResponse() {
        return new GetTagsByTypeResponse();
    }

    /**
     * Create an instance of {@link AddTag }
     * 
     */
    public AddTag createAddTag() {
        return new AddTag();
    }

    /**
     * Create an instance of {@link GetTagsByType }
     * 
     */
    public GetTagsByType createGetTagsByType() {
        return new GetTagsByType();
    }

    /**
     * Create an instance of {@link GetCommentsByType }
     * 
     */
    public GetCommentsByType createGetCommentsByType() {
        return new GetCommentsByType();
    }

    /**
     * Create an instance of {@link AddTagResponse }
     * 
     */
    public AddTagResponse createAddTagResponse() {
        return new AddTagResponse();
    }

    /**
     * Create an instance of {@link GetTagsResponse }
     * 
     */
    public GetTagsResponse createGetTagsResponse() {
        return new GetTagsResponse();
    }

    /**
     * Create an instance of {@link RemoveTagResponse }
     * 
     */
    public RemoveTagResponse createRemoveTagResponse() {
        return new RemoveTagResponse();
    }

    /**
     * Create an instance of {@link GetTag }
     * 
     */
    public GetTag createGetTag() {
        return new GetTag();
    }

    /**
     * Create an instance of {@link GetTagTypes }
     * 
     */
    public GetTagTypes createGetTagTypes() {
        return new GetTagTypes();
    }

    /**
     * Create an instance of {@link GetCommentTypesForReferenceType }
     * 
     */
    public GetCommentTypesForReferenceType createGetCommentTypesForReferenceType() {
        return new GetCommentTypesForReferenceType();
    }

    /**
     * Create an instance of {@link RemoveCommentsResponse }
     * 
     */
    public RemoveCommentsResponse createRemoveCommentsResponse() {
        return new RemoveCommentsResponse();
    }

    /**
     * Create an instance of {@link RemoveTag }
     * 
     */
    public RemoveTag createRemoveTag() {
        return new RemoveTag();
    }

    /**
     * Create an instance of {@link GetCommentResponse }
     * 
     */
    public GetCommentResponse createGetCommentResponse() {
        return new GetCommentResponse();
    }

    /**
     * Create an instance of {@link GetCommentTypesResponse }
     * 
     */
    public GetCommentTypesResponse createGetCommentTypesResponse() {
        return new GetCommentTypesResponse();
    }

    /**
     * Create an instance of {@link GetCommentTypes }
     * 
     */
    public GetCommentTypes createGetCommentTypes() {
        return new GetCommentTypes();
    }

    /**
     * Create an instance of {@link RemoveTags }
     * 
     */
    public RemoveTags createRemoveTags() {
        return new RemoveTags();
    }

    /**
     * Create an instance of {@link GetReferenceTypes }
     * 
     */
    public GetReferenceTypes createGetReferenceTypes() {
        return new GetReferenceTypes();
    }

    /**
     * Create an instance of {@link GetComment }
     * 
     */
    public GetComment createGetComment() {
        return new GetComment();
    }

    /**
     * Create an instance of {@link GetReferenceTypesResponse }
     * 
     */
    public GetReferenceTypesResponse createGetReferenceTypesResponse() {
        return new GetReferenceTypesResponse();
    }

    /**
     * Create an instance of {@link UpdateComment }
     * 
     */
    public UpdateComment createUpdateComment() {
        return new UpdateComment();
    }

    /**
     * Create an instance of {@link AddComment }
     * 
     */
    public AddComment createAddComment() {
        return new AddComment();
    }

    /**
     * Create an instance of {@link GetCommentTypesForReferenceTypeResponse }
     * 
     */
    public GetCommentTypesForReferenceTypeResponse createGetCommentTypesForReferenceTypeResponse() {
        return new GetCommentTypesForReferenceTypeResponse();
    }

    /**
     * Create an instance of {@link RemoveComment }
     * 
     */
    public RemoveComment createRemoveComment() {
        return new RemoveComment();
    }

    /**
     * Create an instance of {@link GetTagTypesResponse }
     * 
     */
    public GetTagTypesResponse createGetTagTypesResponse() {
        return new GetTagTypesResponse();
    }

    /**
     * Create an instance of {@link RemoveTagsResponse }
     * 
     */
    public RemoveTagsResponse createRemoveTagsResponse() {
        return new RemoveTagsResponse();
    }

    /**
     * Create an instance of {@link GetComments }
     * 
     */
    public GetComments createGetComments() {
        return new GetComments();
    }

    /**
     * Create an instance of {@link GetTags }
     * 
     */
    public GetTags createGetTags() {
        return new GetTags();
    }

    /**
     * Create an instance of {@link ValidateComment }
     * 
     */
    public ValidateComment createValidateComment() {
        return new ValidateComment();
    }

    /**
     * Create an instance of {@link GetCommentsByTypeResponse }
     * 
     */
    public GetCommentsByTypeResponse createGetCommentsByTypeResponse() {
        return new GetCommentsByTypeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTag }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "addTag")
    public JAXBElement<AddTag> createAddTag(AddTag value) {
        return new JAXBElement<AddTag>(_AddTag_QNAME, AddTag.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "addCommentResponse")
    public JAXBElement<AddCommentResponse> createAddCommentResponse(AddCommentResponse value) {
        return new JAXBElement<AddCommentResponse>(_AddCommentResponse_QNAME, AddCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "removeComment")
    public JAXBElement<RemoveComment> createRemoveComment(RemoveComment value) {
        return new JAXBElement<RemoveComment>(_RemoveComment_QNAME, RemoveComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentsByTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getCommentsByTypeResponse")
    public JAXBElement<GetCommentsByTypeResponse> createGetCommentsByTypeResponse(GetCommentsByTypeResponse value) {
        return new JAXBElement<GetCommentsByTypeResponse>(_GetCommentsByTypeResponse_QNAME, GetCommentsByTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentsByType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getCommentsByType")
    public JAXBElement<GetCommentsByType> createGetCommentsByType(GetCommentsByType value) {
        return new JAXBElement<GetCommentsByType>(_GetCommentsByType_QNAME, GetCommentsByType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getCommentTypesResponse")
    public JAXBElement<GetCommentTypesResponse> createGetCommentTypesResponse(GetCommentTypesResponse value) {
        return new JAXBElement<GetCommentTypesResponse>(_GetCommentTypesResponse_QNAME, GetCommentTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getTagTypesResponse")
    public JAXBElement<GetTagTypesResponse> createGetTagTypesResponse(GetTagTypesResponse value) {
        return new JAXBElement<GetTagTypesResponse>(_GetTagTypesResponse_QNAME, GetTagTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "addComment")
    public JAXBElement<AddComment> createAddComment(AddComment value) {
        return new JAXBElement<AddComment>(_AddComment_QNAME, AddComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "updateCommentResponse")
    public JAXBElement<UpdateCommentResponse> createUpdateCommentResponse(UpdateCommentResponse value) {
        return new JAXBElement<UpdateCommentResponse>(_UpdateCommentResponse_QNAME, UpdateCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveCommentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "removeCommentsResponse")
    public JAXBElement<RemoveCommentsResponse> createRemoveCommentsResponse(RemoveCommentsResponse value) {
        return new JAXBElement<RemoveCommentsResponse>(_RemoveCommentsResponse_QNAME, RemoveCommentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReferenceTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getReferenceTypesResponse")
    public JAXBElement<GetReferenceTypesResponse> createGetReferenceTypesResponse(GetReferenceTypesResponse value) {
        return new JAXBElement<GetReferenceTypesResponse>(_GetReferenceTypesResponse_QNAME, GetReferenceTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTagResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "removeTagResponse")
    public JAXBElement<RemoveTagResponse> createRemoveTagResponse(RemoveTagResponse value) {
        return new JAXBElement<RemoveTagResponse>(_RemoveTagResponse_QNAME, RemoveTagResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getTagTypes")
    public JAXBElement<GetTagTypes> createGetTagTypes(GetTagTypes value) {
        return new JAXBElement<GetTagTypes>(_GetTagTypes_QNAME, GetTagTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTagsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "removeTagsResponse")
    public JAXBElement<RemoveTagsResponse> createRemoveTagsResponse(RemoveTagsResponse value) {
        return new JAXBElement<RemoveTagsResponse>(_RemoveTagsResponse_QNAME, RemoveTagsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTags }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getTags")
    public JAXBElement<GetTags> createGetTags(GetTags value) {
        return new JAXBElement<GetTags>(_GetTags_QNAME, GetTags.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagsByType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getTagsByType")
    public JAXBElement<GetTagsByType> createGetTagsByType(GetTagsByType value) {
        return new JAXBElement<GetTagsByType>(_GetTagsByType_QNAME, GetTagsByType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTags }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "removeTags")
    public JAXBElement<RemoveTags> createRemoveTags(RemoveTags value) {
        return new JAXBElement<RemoveTags>(_RemoveTags_QNAME, RemoveTags.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getCommentResponse")
    public JAXBElement<GetCommentResponse> createGetCommentResponse(GetCommentResponse value) {
        return new JAXBElement<GetCommentResponse>(_GetCommentResponse_QNAME, GetCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveComments }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "removeComments")
    public JAXBElement<RemoveComments> createRemoveComments(RemoveComments value) {
        return new JAXBElement<RemoveComments>(_RemoveComments_QNAME, RemoveComments.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagsByTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getTagsByTypeResponse")
    public JAXBElement<GetTagsByTypeResponse> createGetTagsByTypeResponse(GetTagsByTypeResponse value) {
        return new JAXBElement<GetTagsByTypeResponse>(_GetTagsByTypeResponse_QNAME, GetTagsByTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getTagsResponse")
    public JAXBElement<GetTagsResponse> createGetTagsResponse(GetTagsResponse value) {
        return new JAXBElement<GetTagsResponse>(_GetTagsResponse_QNAME, GetTagsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTagResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "addTagResponse")
    public JAXBElement<AddTagResponse> createAddTagResponse(AddTagResponse value) {
        return new JAXBElement<AddTagResponse>(_AddTagResponse_QNAME, AddTagResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentTypesForReferenceTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getCommentTypesForReferenceTypeResponse")
    public JAXBElement<GetCommentTypesForReferenceTypeResponse> createGetCommentTypesForReferenceTypeResponse(GetCommentTypesForReferenceTypeResponse value) {
        return new JAXBElement<GetCommentTypesForReferenceTypeResponse>(_GetCommentTypesForReferenceTypeResponse_QNAME, GetCommentTypesForReferenceTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComments }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getComments")
    public JAXBElement<GetComments> createGetComments(GetComments value) {
        return new JAXBElement<GetComments>(_GetComments_QNAME, GetComments.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentTypesForReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getCommentTypesForReferenceType")
    public JAXBElement<GetCommentTypesForReferenceType> createGetCommentTypesForReferenceType(GetCommentTypesForReferenceType value) {
        return new JAXBElement<GetCommentTypesForReferenceType>(_GetCommentTypesForReferenceType_QNAME, GetCommentTypesForReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "validateCommentResponse")
    public JAXBElement<ValidateCommentResponse> createValidateCommentResponse(ValidateCommentResponse value) {
        return new JAXBElement<ValidateCommentResponse>(_ValidateCommentResponse_QNAME, ValidateCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveCommentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "removeCommentResponse")
    public JAXBElement<RemoveCommentResponse> createRemoveCommentResponse(RemoveCommentResponse value) {
        return new JAXBElement<RemoveCommentResponse>(_RemoveCommentResponse_QNAME, RemoveCommentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReferenceTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getReferenceTypes")
    public JAXBElement<GetReferenceTypes> createGetReferenceTypes(GetReferenceTypes value) {
        return new JAXBElement<GetReferenceTypes>(_GetReferenceTypes_QNAME, GetReferenceTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTag }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getTag")
    public JAXBElement<GetTag> createGetTag(GetTag value) {
        return new JAXBElement<GetTag>(_GetTag_QNAME, GetTag.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "validateComment")
    public JAXBElement<ValidateComment> createValidateComment(ValidateComment value) {
        return new JAXBElement<ValidateComment>(_ValidateComment_QNAME, ValidateComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTag }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "removeTag")
    public JAXBElement<RemoveTag> createRemoveTag(RemoveTag value) {
        return new JAXBElement<RemoveTag>(_RemoveTag_QNAME, RemoveTag.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getCommentsResponse")
    public JAXBElement<GetCommentsResponse> createGetCommentsResponse(GetCommentsResponse value) {
        return new JAXBElement<GetCommentsResponse>(_GetCommentsResponse_QNAME, GetCommentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getComment")
    public JAXBElement<GetComment> createGetComment(GetComment value) {
        return new JAXBElement<GetComment>(_GetComment_QNAME, GetComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateComment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "updateComment")
    public JAXBElement<UpdateComment> createUpdateComment(UpdateComment value) {
        return new JAXBElement<UpdateComment>(_UpdateComment_QNAME, UpdateComment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getTagResponse")
    public JAXBElement<GetTagResponse> createGetTagResponse(GetTagResponse value) {
        return new JAXBElement<GetTagResponse>(_GetTagResponse_QNAME, GetTagResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommentTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/commentService", name = "getCommentTypes")
    public JAXBElement<GetCommentTypes> createGetCommentTypes(GetCommentTypes value) {
        return new JAXBElement<GetCommentTypes>(_GetCommentTypes_QNAME, GetCommentTypes.class, null, value);
    }

}
