package org.kuali.student.core.comments.ui.client.widgets.commenttool;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.gen2.logging.shared.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.comments.ui.client.service.CommentRpcService;
import org.kuali.student.core.comments.ui.client.service.CommentRpcServiceAsync;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.r2.common.dto.RichTextInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommentTool implements HasReferenceId {

    private CommentRpcServiceAsync commentServiceAsync = GWT.create(CommentRpcService.class);
    private String referenceId;
    private String referenceTypeKey;
    private String referenceType;
    private String referenceState;
    private KSLightBox commentLightBox;
    private HTML loggedInUserNameHTML = new HTML();
    private String loggedInUserId;
    protected KSTextArea commentTextArea = new KSTextArea();
    protected KSButton cancelEditButton = new KSButton("Cancel");
    private KSButton submitCommentButton = new KSButton("Submit");
    private FlexTable commentsTableLayout = new FlexTable();
    //private static final DateFormat df = new SimpleDateFormat("MMMM dd, yyyy - hh:mmaaa");
    private Controller controller;    
    private Enum<?> viewEnum;
    private String viewName;    //View name is being used as menu item label   
    VerticalFlowPanel loggedInLabelsPanel = new VerticalFlowPanel();
    VerticalFlowPanel commentEditPanel = new VerticalFlowPanel();
    KSLabel notAuthorizedToAddComments = new KSLabel("The document must be saved before Comments can be added.");
    protected EditMode editMode = EditMode.ADD_COMMENT;
    private CommentInfo selectedComment;
    protected List<Callback<EditMode>> editControlsCallbacks = new ArrayList<Callback<EditMode>>();
    private String commentTypeKey;
    private Map<String, String> referenceAttributes;
    private KSLabel proposalTitle = new KSLabel();
    private String title;
    protected HTML htmlLabel;
    protected SectionTitle leaveACommentTitle;
    protected HorizontalPanel commentSectionPanel;
    private KSButton editButton;
    private KSButton deleteButton;
    protected Map<Integer, KSButton> editButtonMap = new HashMap<Integer, KSButton>();
    protected Map<Integer, KSButton> deleteButtonMap = new HashMap<Integer, KSButton>();

    public enum EditMode {
        ADD_COMMENT, UPDATE_COMMENT, VIEW_COMMENT
    }
    
    public CommentTool(Enum<?> viewEnum, String viewName, String commentTypeKey, String title) {
        this.viewName = viewName;
        this.viewEnum = viewEnum;
        this.commentTypeKey = commentTypeKey;
        this.title = title;
        init();
        
    }
    
    public Controller getController() {
        return this.controller;
    }


    public void setController(Controller controller){
        this.controller = controller;
    }

    
    public Widget asWidget(){
        return commentLightBox.getWidget();
    }
    
    private OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

        @Override
        public void exec(OkEnum result) {
            if(result == OkEnum.Ok){
                Log.info("Creating new button panel.");
            }

        }
    });
    
    protected void setCommentsWarningText(VerticalFlowPanel contentPanel)
    {
        htmlLabel = new HTML("<b>All comments posted here will be visible to authors, and " +
                "to reviewers after you submit the proposal.</b>");
        contentPanel.add(htmlLabel);
    }
    
    //Used to get the number of comments that will be used in the Comments(x) row 
    protected int getDecisionsCommentsCount(List<CommentInfo> commentInfos)
    {
        return commentInfos.size();
    }
    
    protected KSLabel commentTextLabelProperties(RichTextInfo commentRT)
    {
        String commentText = commentRT.getPlain();
        KSLabel commentTextLabel = new KSLabel(commentText);
        commentTextLabel.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
        //Change this line to configure the size of the table cell that displays the comment
        commentTextLabel.setWidth("120px");
        commentTextLabel.getElement().getStyle().setProperty("wordWrap", "break-word");
        return commentTextLabel;
    }
    
    private void init() {
        commentLightBox = new KSLightBox();
        VerticalFlowPanel contentPanel = new VerticalFlowPanel();
        // light box title and instructions
        SectionTitle title = SectionTitle.generateH2Title(this.title);
        title.addStyleName("ks-layout-header");
        title.setStyleName("cluProposalTitleSection");
        proposalTitle.setVisible(false);
        contentPanel.add(proposalTitle);
        commentLightBox.setNonCaptionHeader(title);
        setCommentsWarningText(contentPanel);
        
        // comments section title
        leaveACommentTitle = SectionTitle.generateH3Title("Leave a Comment");
        leaveACommentTitle.getElement().getStyle().setProperty("borderBottom", "1px solid #D8D8D8");
        leaveACommentTitle.getElement().getStyle().setProperty("marginTop", "2em");
        contentPanel.add(leaveACommentTitle);
        
        // comments section
        HTML loggedInAsLabel = new HTML("<b>Logged in as:<b/>");
        loggedInLabelsPanel.add(loggedInAsLabel);
        final String userId = Application.getApplicationContext().getSecurityContext().getUserId();
        commentServiceAsync.getUserRealName(userId, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                loggedInUserNameHTML.setHTML("<b>" + userId + "</b>");
            }
            @Override
            public void onSuccess(String result) {
                if (result != null && !result.isEmpty()) {
                    loggedInUserNameHTML.setHTML("<b>" + result + "</b>");
                } else {
                    loggedInUserNameHTML.setHTML("<b>" + userId + "</b>");
                }
            }
        });
        
        loggedInUserId = userId;
        loggedInLabelsPanel.add(loggedInUserNameHTML);
        commentTextArea.setSize("500", "100");
        commentEditPanel.add(commentTextArea);
        FlowPanel buttonsPanel = new FlowPanel();
        buttonsPanel.add(cancelEditButton);
        cancelEditButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setEditMode(EditMode.ADD_COMMENT);
            }
        });
        buttonsPanel.add(submitCommentButton);
        submitCommentButton.setEnabled(false);
        commentEditPanel.add(buttonsPanel);
        buttonsPanel.addStyleName("KS-Comment-Button-Panel");
        
        commentTextArea.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                submitCommentButton.setEnabled(true);
            }
        });
        
        // if edit mode is add coment call addComment
        // if edit mode is edit comment call updateComment
        submitCommentButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                switch (editMode) {
                    case ADD_COMMENT: {
                        CommentInfo newComment = new CommentInfo();

                        RichTextInfo text = new RichTextInfo();
                        text.setFormatted(commentTextArea.getText());
                        text.setPlain(commentTextArea.getText());
                        newComment.setType(commentTypeKey);
                        newComment.setReferenceId(referenceId);
                        newComment.setReferenceTypeKey(referenceTypeKey);
                        newComment.setState(DtoState.ACTIVE.toString());
                        newComment.setCommentText(text);

                        try {
                            commentServiceAsync.addComment(referenceId, referenceTypeKey, newComment, new KSAsyncCallback<CommentInfo>(){

                                @Override
                                public void handleFailure(Throwable caught) {
                                    GWT.log("Add Comment Failed", caught);
                                }

                                @Override
                                public void onSuccess(CommentInfo result) {
                                    refreshComments();
                                }
                            });
                        } catch (Exception e) {
                            GWT.log("Add Comment Failed", e);
                        }
                        break;
                    }
                    case UPDATE_COMMENT: {
                        if (selectedComment != null) {
                            RichTextInfo text = new RichTextInfo();
                            text.setFormatted(commentTextArea.getText());
                            text.setPlain(commentTextArea.getText());
                            selectedComment.setReferenceId(referenceId);
                            selectedComment.setReferenceTypeKey(referenceTypeKey);
                            selectedComment.setCommentText(text);
//                            selectedComment.setType("commentType." + referenceType + "." + referenceState);
                            selectedComment.setType(commentTypeKey);
                        }

                        try {
                            commentServiceAsync.updateComment(referenceId, referenceTypeKey, selectedComment, new KSAsyncCallback<CommentInfo>(){

                                @Override
                                public void handleFailure(Throwable caught) {
                                    GWT.log("Add Comment Failed", caught);
                                }

                                @Override
                                public void onSuccess(CommentInfo result) {
                                    refreshComments();
                                    setEditMode(EditMode.ADD_COMMENT);
                                }
                            });
                        } catch (Exception e) {
                            GWT.log("Add Comment Failed", e);
                        }
                        break;
                    }
                }
            }
        });
        
        commentSectionPanel = new HorizontalPanel();
        commentSectionPanel.add(loggedInLabelsPanel);
        commentSectionPanel.add(commentEditPanel);
        commentSectionPanel.add(notAuthorizedToAddComments);
        contentPanel.add(commentSectionPanel);
        
        // comments table
        contentPanel.add(commentsTableLayout);
        
        
//        scrollPanel.setHeight(height)
        
        commentLightBox.setWidget(contentPanel);
        setEditMode(EditMode.ADD_COMMENT);
    }
    
    private void checkPermissionsAndRedrawTable(final List<CommentInfo> commentInfos) {
        // check permission to see if user can comment
        commentServiceAsync.isAuthorizedAddComment(referenceId, referenceTypeKey, new KSAsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Error checking permission for adding comments: ", caught);
                throw new RuntimeException("Error checking Permissions: ", caught);
            }

            @Override
            public void onSuccess(Boolean result) {
                GWT.log("User is " + ((result) ? "" : "not ") + 
                        "authorized to add comment.", null);
                if(referenceId != null && !(referenceId.isEmpty())){
                    notAuthorizedToAddComments.setVisible(false);
                    commentEditPanel.setVisible(result);
                    commentsTableLayout.setVisible(true);
                    redrawCommentsTable(commentInfos);
                }
                else{
                    notAuthorizedToAddComments.setVisible(true);
                    commentEditPanel.setVisible(false);
                    commentsTableLayout.setVisible(false);
                }
            }

        });
    }
    
    protected void redrawCommentsTable(List<CommentInfo> commentInfos) {
        commentsTableLayout.clear();
        editControlsCallbacks.clear();
        
        if (commentInfos != null) {
            int rowIndex = 0;
            int commentCounter = 0;
            for (final CommentInfo commentInfo : commentInfos) {
                int columnIndex = 0;
                final KSButton editButton = new KSButton("Edit", ButtonStyle.DEFAULT_ANCHOR);
                final KSButton deleteButton = new KSButton("Delete", ButtonStyle.DEFAULT_ANCHOR);
                if (commentInfo.getType() != null && 
                        commentInfo.getType().startsWith("kuali.comment.type.workflowDecisionRationale")) {
                    // do not display comments for workflow decision rationale.
                    continue;
                }
                if (rowIndex == 0) {
                    StringBuilder titleTextSb = new StringBuilder();
                    titleTextSb.append("Comments (").append(getDecisionsCommentsCount(commentInfos)).append(")");
                    SectionTitle commentsSectionHeader = SectionTitle.generateH3Title(titleTextSb.toString());
                    commentsSectionHeader.getElement().getStyle().setProperty("borderBottom", "1px solid #D8D8D8");
                    commentsTableLayout.setWidget(rowIndex, columnIndex, commentsSectionHeader);
                    commentsTableLayout.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 3);
                    rowIndex++;
                }
                if (commentCounter > 0) {
                    VerticalFlowPanel space = new VerticalFlowPanel();
                    space.getElement().getStyle().setPaddingBottom(5d, Style.Unit.PX);
                    commentsTableLayout.setWidget(rowIndex, columnIndex, space);
                    commentsTableLayout.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 3);
                    rowIndex++;
                }
                VerticalFlowPanel userNameAndTime = new VerticalFlowPanel();
                final HTML userNameLabel = new HTML();
                final String principalId = commentInfo.getMeta().getUpdateId();
                commentServiceAsync.getUserRealNameByPrincipalId(principalId, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        userNameLabel.setHTML("<b>" + principalId + "</b>");
                    }
                    @Override
                    public void onSuccess(String result) {
                        if (result != null && !result.isEmpty()) {
                            userNameLabel.setHTML("<b>" + result + "</b>");
                        } else {
                            userNameLabel.setHTML("<b>" + principalId + "</b>");
                        }
                    }
                });
                Date createTime = commentInfo.getMeta().getCreateTime();
                userNameAndTime.add(userNameLabel);
                DateFormat df = new SimpleDateFormat("MMMM dd, yyyy - hh:mmaaa");
                userNameAndTime.add(new KSLabel(df.format(createTime)));
                userNameAndTime.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
                commentsTableLayout.setWidget(rowIndex, columnIndex, userNameAndTime);
                columnIndex++;
                KSLabel commentTextLabel=commentTextLabelProperties(commentInfo.getCommentText());
                commentsTableLayout.setWidget(rowIndex, columnIndex, commentTextLabel);
                columnIndex++;
                editButton.getElement().getStyle().setPadding(5d, Style.Unit.PX);
                editControlsCallbacks.add(new Callback<EditMode>() {
                    @Override
                    public void exec(EditMode result) {
                        switch (editMode) {
                            case UPDATE_COMMENT:
//                                editButton.setStyleName("KS-CommentButtonDisabled");
//                                deleteButton.setStyleName("KS-CommentButtonDisabled");
                                editButton.setEnabled(false);
                                deleteButton.setEnabled(false);
                                break;
                            case ADD_COMMENT:
//                                editButton.setStyleName("KS-CommentButton");
//                                deleteButton.setStyleName("KS-CommentButton");
                                editButton.setEnabled(true);
                                deleteButton.setEnabled(true);
                                break;
                        }
                    }
                });
                
                editButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        String commentText = (commentInfo == null ||
                                commentInfo.getCommentText() == null)? "" :
                                    commentInfo.getCommentText().getPlain();
                        selectedComment = commentInfo;
                        commentTextArea.setText(commentText);
                        commentTextArea.setFocus(true);
                        setEditMode(EditMode.UPDATE_COMMENT);
                    }
                });
                deleteButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                            final ConfirmationDialog confirmDeletion =
                                new ConfirmationDialog("Delete Comment",  
                                    "You are about to delete a comment.  Are you sure?");
                            confirmDeletion.getConfirmButton().addClickHandler(new ClickHandler(){
                                @Override
                                public void onClick(ClickEvent event) {
                                    try {
                                        commentServiceAsync.removeComment(commentInfo.getId(), 
                                                referenceId, referenceTypeKey, 
                                                new KSAsyncCallback<StatusInfo>(){

                                            @Override
                                            public void handleFailure(Throwable caught) {
                                                GWT.log("remove Comment Failed", caught);
                                            }

                                            @Override
                                            public void onSuccess(StatusInfo result) {
                                                confirmDeletion.hide();
                                                refreshComments();
                                            }

                                        });
                                    } catch (Exception e) {
                                        GWT.log("remove Comment Failed", e);
                                    }
                                }
                            });
                            confirmDeletion.show();
                    }
                });
                commentsTableLayout.setWidget(rowIndex, columnIndex, editButton);
                editButtonMap.put(commentCounter, editButton);
                columnIndex++;
                commentsTableLayout.setWidget(rowIndex, columnIndex, deleteButton);
                deleteButtonMap.put(commentCounter, deleteButton);
                columnIndex++;
                
                commentServiceAsync.getPrincipalNameByPrincipalId(principalId, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // What do we do here?
                        String warning = "Unable to find PrincipalName via PrincipalId["+principalId+"]";                        
                    }
                    @Override
                    public void onSuccess(String principalName) {
                        if (principalName == null || !principalName.equals(getLoggedInUserId())) {
                            editButton.setVisible(false);
                            deleteButton.setVisible(false);
                        }
                    }
                });
                
                rowIndex++;
                
                commentCounter++;
            }
            setEditMode(EditMode.ADD_COMMENT);
        }
    }
    
    public void setEditMode(EditMode editMode) {
        this.editMode = editMode;
        switch (editMode) {
            case UPDATE_COMMENT:
                cancelEditButton.setVisible(true);
                if (editControlsCallbacks != null) {
                    for (Callback<EditMode> callback : editControlsCallbacks) {
                        callback.exec(EditMode.UPDATE_COMMENT);
                    }
                }
                break;
            case ADD_COMMENT:
                cancelEditButton.setVisible(false);
                commentTextArea.setText("");
                if (editControlsCallbacks != null) {
                    for (Callback<EditMode> callback : editControlsCallbacks) {
                        callback.exec(EditMode.ADD_COMMENT);
                    }
                }
                break;
            case VIEW_COMMENT:
                htmlLabel.setVisible(false);
                leaveACommentTitle.setVisible(false);
                commentSectionPanel.setVisible(false);
                
                for (int i = 0; i < editButtonMap.size(); i++) {
                    editButtonMap.get(i).setVisible(false);                    
                }
                for (int i = 0; i < deleteButtonMap.size(); i++) {
                    deleteButtonMap.get(i).setVisible(false);                    
                }

                break;
        }
    }
    
    public void refreshComments(){
        //rpc call to get all current comments and populate into comment list
        try {
            commentServiceAsync.getComments(referenceId, referenceTypeKey, new KSAsyncCallback<List<CommentInfo>>(){

                @Override
                public void handleFailure(Throwable caught) {
                    GWT.log("getComments failed", caught);
                }

                @Override
                public void onSuccess(List<CommentInfo> result) {
                    if (result != null && !result.isEmpty()) {
                        Collections.sort(result, new Comparator<CommentInfo>(){

                            @Override
                            public int compare(CommentInfo comment1, CommentInfo comment2) {

                                if(comment1.getMeta().getCreateTime().after(comment2.getMeta().getCreateTime())){
                                    return -1;
                                }
                                else if(comment1.getMeta().getCreateTime().before(comment2.getMeta().getCreateTime())){
                                    return 1;
                                }
                                else{
                                    //equal
                                    return 0;
                                }

                            }
                        });
                    }
                    checkPermissionsAndRedrawTable(result);
                }
            });
        } catch (Exception e) {
            Window.alert("Failed to refresh Comments");
            GWT.log("refresh Comments Failed",e);
        }


    }
//    private void redrawCommentsTable(FlexTable tableLayout) {
//        StringBuilder titleTextSb = new StringBuilder();
//        titleTextSb.append("Comments (").append(docInfos.size()).append(")");
//        SectionTitle uploadedFileSectionHeader = SectionTitle.generateH3Title(titleTextSb.toString());
//        uploadedFileSectionHeader.getElement().getStyle().setProperty("borderBottom", "1px solid #D8D8D8");
//        tableLayout.setWidget(rowIndex, columnIndex, uploadedFileSectionHeader);
//        tableLayout.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 3);
//    }
    
    @Override
    public String getReferenceId() {
        return referenceId;
    }

    @Override
    public String getReferenceState() {
        return referenceState;
    }

    @Override
    public String getReferenceType() {
        return referenceType;
    }

    @Override
    public String getReferenceTypeKey() {
        return referenceTypeKey;
    }

    @Override
    public void setReferenceId(String id) {
        this.referenceId = id;
    }

    @Override
    public void setReferenceState(String state) {
        this.referenceState = state;
    }

    @Override
    public void setReferenceType(String type) {
        this.referenceType = type;
    }

    @Override
    public void setReferenceTypeKey(String key) {
        this.referenceTypeKey = key;
    }
    
    public void setReferenceAttributes(Map<String, String> referenceAttributes) {
        this.referenceAttributes = referenceAttributes;
    }
    
    public void show() {
        controller.requestModel(ReferenceModel.class, 
                new ModelRequestCallback<ReferenceModel>(){
            public void onModelReady(ReferenceModel model) {
                String proposalTitleString = null;
                setReferenceId(model.getReferenceId());
                setReferenceTypeKey(model.getReferenceTypeKey());
                setReferenceType(model.getReferenceType());
                setReferenceState(model.getReferenceState());
                setReferenceAttributes(model.getReferenceAttributes());
                proposalTitleString = (model.getReferenceAttributes() == null)? null : 
                    model.getReferenceAttributes().get("name");
                if (proposalTitleString != null && !proposalTitleString.trim().isEmpty()) {
                    proposalTitle.setText(proposalTitleString);
                    proposalTitle.setVisible(true);
                } else {
                    proposalTitle.setText("");
                    proposalTitle.setVisible(false);
                }
                refreshComments();
                commentLightBox.show();
            }

            public void onRequestFail(Throwable cause) {
                Window.alert(cause.toString());
            }
        });
    }
    
    public String getLoggedInUserId() {
        return loggedInUserId;
    }

}
