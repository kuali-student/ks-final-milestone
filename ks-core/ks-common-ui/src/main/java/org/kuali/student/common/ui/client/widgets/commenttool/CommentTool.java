package org.kuali.student.common.ui.client.widgets.commenttool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.service.CommentRpcService;
import org.kuali.student.common.ui.client.service.CommentRpcServiceAsync;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CommentTool implements HasReferenceId, ToolView {

    private CommentRpcServiceAsync commentServiceAsync = GWT.create(CommentRpcService.class);
    private String referenceId;
    private String referenceTypeKey;
    private String referenceType;
    private String referenceState;
    private KSLightBox commentLightBox;
    private KSLabel loggedInUserNameLabel = new KSLabel();
    private String loggedInUserId;
    private KSTextArea commentTextArea = new KSTextArea();
    private KSButton cancelEditButton = new KSButton("Cancel");
    private KSButton submitCommentButton = new KSButton("Submit");
    private FlexTable commentsTableLayout = new FlexTable();
    private static final DateFormat df = new SimpleDateFormat("MMMM dd, yyyy - hh:mmaaa");
    private Controller controller;    
    private Enum<?> viewEnum;
    private String viewName;    //View name is being used as menu item label   
    VerticalFlowPanel loggedInLabelsPanel = new VerticalFlowPanel();
    VerticalFlowPanel commentEditPanel = new VerticalFlowPanel();
    KSLabel notAuthorizedToAddComments = new KSLabel("The document must be saved before Comments can be added.");
    private EditMode editMode = EditMode.ADD_COMMENT;
    private CommentInfo selectedComment;
    private List<Callback<EditMode>> editControlsCallbacks = new ArrayList<Callback<EditMode>>();
    private String commentTypeKey;
    private Map<String, String> referenceAttributes;
    private KSLabel proposalTitle = new KSLabel();

    private enum EditMode {
        ADD_COMMENT, UPDATE_COMMENT
    }
    
    public CommentTool(Enum<?> viewEnum, String viewName, String commentTypeKey) {
        this.viewName = viewName;
        this.viewEnum = viewEnum;
        this.commentTypeKey = commentTypeKey;
        init();
    }
    
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback){
        onReadyCallback.exec(true);
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#beforeHide()
     */
    @Override
    public boolean beforeHide() {
        return true;
    }


    /**
     * @see org.kuali.student.common.ui.client.mvc.View#getController()
     */
    @Override
    public Controller getController() {
        return this.controller;
    }


    /**
     * @see org.kuali.student.common.ui.client.mvc.View#getName()
     */
    @Override
    public String getName() {
        return this.viewName;
    }

    public Enum<?> getViewEnum() {
        return viewEnum;
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
     */
    @Override
    public void updateModel() {
        //There is no model to update here, reference model is read-only        
    }
    
    public void setController(Controller controller){
        this.controller = controller;
    }

    
    public Widget asWidget(){
        return commentLightBox.getWidget();
    }
    
    @Override
    public String collectHistory(String historyStack) {
        return null;
    }

    @Override
    public void onHistoryEvent(String historyStack) {
        
    }

    @Override
    public void collectBreadcrumbNames(List<String> names) {
        names.add(this.getName());
        
    }

    private OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

        @Override
        public void exec(OkEnum result) {
            if(result == OkEnum.Ok){
            }

        }
    });
    
    @Override
    public void clear() {
    }

    private void init() {
        commentLightBox = new KSLightBox();
        ScrollPanel scrollPanel = new ScrollPanel();
        VerticalFlowPanel contentPanel = new VerticalFlowPanel();
        // light box title and instructions
        SectionTitle title = SectionTitle.generateH1Title("Proposal Comments");
        title.addStyleName("ks-layout-header");
        HTML htmlLabel = new HTML("All comments posted here will be visible to authors, <b>and</b> " +
        		"to reviewers after you submit the proposal.");
        title.setStyleName("cluProposalTitleSection");
        proposalTitle.setVisible(false);
        contentPanel.add(proposalTitle);
        contentPanel.add(title);
        contentPanel.add(htmlLabel);
        
        // comments section title
        KSLabel leaveACommentLabel = new KSLabel("Leave a Comment");
        leaveACommentLabel.getElement().getStyle().setProperty("borderBottom", "1px solid #D8D8D8");
        leaveACommentLabel.getElement().getStyle().setProperty("marginTop", "2em");
        contentPanel.add(leaveACommentLabel);
        
        // comments section
        KSLabel loggedInAsLabel = new KSLabel("Logged in as:");
        loggedInLabelsPanel.add(loggedInAsLabel);
        String userId = Application.getApplicationContext().getUserId();
        loggedInUserId = userId;
        loggedInUserNameLabel.setText(userId);
        loggedInLabelsPanel.add(loggedInUserNameLabel);
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
                        newComment.setCommentText(text);
//                        newComment.setType("commentType." + referenceType + "." + referenceState);
                        newComment.setType(commentTypeKey);

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
        
        HorizontalPanel commentSectionPanel = new HorizontalPanel();
        commentSectionPanel.add(loggedInLabelsPanel);
        commentSectionPanel.add(commentEditPanel);
        commentSectionPanel.add(notAuthorizedToAddComments);
        contentPanel.add(commentSectionPanel);
        
        // comments table
        scrollPanel.add(commentsTableLayout);
        contentPanel.add(scrollPanel);
        
        
//        scrollPanel.setHeight(height)
        
        commentLightBox.setWidget(contentPanel);
        commentLightBox.setSize(400, 400);
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
                result = true;
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
    
    private void redrawCommentsTable(List<CommentInfo> commentInfos) {
        commentsTableLayout.clear();
        editControlsCallbacks.clear();
        
        if (commentInfos != null) {
            int rowIndex = 0;
            for (final CommentInfo commentInfo : commentInfos) {
                int columnIndex = 0;
                if (rowIndex == 0) {
                    StringBuilder titleTextSb = new StringBuilder();
                    titleTextSb.append("Comments (").append(commentInfos.size()).append(")");
                    SectionTitle commentsSectionHeader = SectionTitle.generateH3Title(titleTextSb.toString());
                    commentsSectionHeader.getElement().getStyle().setProperty("borderBottom", "1px solid #D8D8D8");
                    commentsTableLayout.setWidget(rowIndex, columnIndex, commentsSectionHeader);
                    commentsTableLayout.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 3);
                    rowIndex++;
                }
                VerticalFlowPanel userNameAndTime = new VerticalFlowPanel();
                // TODO use user id for now change to user name
                String userId = commentInfo.getMetaInfo().getUpdateId();
                Date createTime = commentInfo.getMetaInfo().getCreateTime();
                userNameAndTime.add(new KSLabel(userId));
                userNameAndTime.add(new KSLabel(df.format(createTime)));
                userNameAndTime.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
                commentsTableLayout.setWidget(rowIndex, columnIndex, userNameAndTime);
                columnIndex++;
                
                RichTextInfo commentRT = commentInfo.getCommentText();
                String commentText = commentRT.getPlain();
                KSLabel commentTextLabel = new KSLabel(commentText);
                commentTextLabel.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
                commentTextLabel.setWidth("150px");
                commentTextLabel.getElement().getStyle().setProperty("wordWrap", "break-word");
                commentsTableLayout.setWidget(rowIndex, columnIndex, commentTextLabel);
                columnIndex++;
                
                final Button editButton = new Button("Edit");
                final Button deleteButton = new Button("Delete");
                editControlsCallbacks.add(new Callback<EditMode>() {
                    @Override
                    public void exec(EditMode result) {
                        switch (editMode) {
                            case UPDATE_COMMENT:
                                editButton.setStyleName("KS-CommentButtonDisabled");
                                deleteButton.setStyleName("KS-CommentButtonDisabled");
                                editButton.setEnabled(false);
                                deleteButton.setEnabled(false);
                                break;
                            case ADD_COMMENT:
                                editButton.setStyleName("KS-CommentButton");
                                deleteButton.setStyleName("KS-CommentButton");
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
                        setEditMode(EditMode.UPDATE_COMMENT);
                    }
                });
                deleteButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        try {
                            commentServiceAsync.removeComment(commentInfo.getId(), referenceId, referenceTypeKey, new KSAsyncCallback<StatusInfo>(){

                                @Override
                                public void handleFailure(Throwable caught) {
                                    GWT.log("remove Comment Failed", caught);
                                }

                                @Override
                                public void onSuccess(StatusInfo result) {
                                    if(result.getSuccess()){
                                        Window.alert("Your comment was deleted successfully");
                                    }
                                    refreshComments();
                                }

                            });
                        } catch (Exception e) {
                            GWT.log("remove Comment Failed", e);
                        }
                    }
                });
                commentsTableLayout.setWidget(rowIndex, columnIndex, editButton);
                columnIndex++;
                commentsTableLayout.setWidget(rowIndex, columnIndex, deleteButton);
                columnIndex++;
                if (userId == null || !userId.equals(this.loggedInUserId)) {
                    editButton.setVisible(false);
                    deleteButton.setVisible(false);
                }
                
                rowIndex++;
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

                                if(comment1.getMetaInfo().getCreateTime().after(comment2.getMetaInfo().getCreateTime())){
                                    return -1;
                                }
                                else if(comment1.getMetaInfo().getCreateTime().before(comment2.getMetaInfo().getCreateTime())){
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
    public KSImage getImage() {
        return Theme.INSTANCE.getCommonImages().getCommentIcon();
    }

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

}
