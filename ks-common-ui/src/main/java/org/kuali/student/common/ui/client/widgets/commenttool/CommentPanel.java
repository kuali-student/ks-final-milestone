package org.kuali.student.common.ui.client.widgets.commenttool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.CommentRpcService;
import org.kuali.student.common.ui.client.service.CommentRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CommentPanel extends ToolView implements HasReferenceId{

	private String referenceId;
    private String referenceKey;
    
	private CommentRpcServiceAsync commentServiceAsync = GWT.create(CommentRpcService.class);
	
    private VerticalFlowPanel layout = new VerticalFlowPanel();
    private VerticalFlowPanel commentList = new VerticalFlowPanel();
    private SectionTitle headerTitle = SectionTitle.generateH1Title("Proposal Comments");
    private SectionTitle createTitle = SectionTitle.generateH2Title("Leave a Comment");
    private VerticalFlowPanel createPanel = new VerticalFlowPanel();
    private SimplePanel commentEditorPanel = new SimplePanel();
    private KSRichEditor commentEditor = new KSRichEditor();
    private List<Comment> comments = new ArrayList<Comment>();
    private KSLabel loggedInAs = new KSLabel();
    private KSLabel seeComments = new KSLabel("See Comments from");
    private KSLabel loadingComments = new KSLabel("Loading Comments");
    private KSDropDown commentTypes = new KSDropDown();
    private boolean showingEditButtons = true;
    
    private Comparator<CommentInfo> commentInfoComparator = new Comparator<CommentInfo>(){

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
    };
    
    private static DateTimeFormat formatter = DateTimeFormat.getMediumDateTimeFormat();
    
    public OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

        @Override
        public void exec(OkEnum result) {
            if(result == OkEnum.Ok){
                CommentInfo newComment = new CommentInfo();
                
                RichTextInfo text = new RichTextInfo();
                text.setFormatted(commentEditor.getHTML());
                text.setPlain(commentEditor.getText());
                newComment.setCommentText(text);
                newComment.setType("commentType.type1");

                try {
					commentServiceAsync.addComment(referenceId, referenceKey, newComment, new AsyncCallback<CommentInfo>(){

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
							
						}

						@Override
						public void onSuccess(CommentInfo result) {
							Comment comment = new Comment(result);
					        comments.add(0, comment);
					        commentList.insert(comment, 0);
					        commentEditor.setHTML("");
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}

            }
            
        }
    });
    
    public CommentPanel(Enum<?> viewEnum, String viewName) {
		super(viewEnum, viewName);
	} 
      
    @Override
    protected Widget createWidget() {
        buttonPanel.setButtonText(OkEnum.Ok, "Submit");
        //FIXME: get person logged in as
        loggedInAs.setText("PersonId Here");
        
        loggedInAs.addStyleName(KSStyles.KS_COMMENT_LOGIN_USER);
        createPanel.addStyleName(KSStyles.KS_COMMENT_CREATE_PANEL);
        commentEditor.addStyleName(KSStyles.KS_COMMENT_CREATE_EDITOR);
        
        layout.add(headerTitle);
        createPanel.add(createTitle);
        commentEditorPanel.setWidget(commentEditor);
        createPanel.add(commentEditorPanel);
        createPanel.add(loggedInAs);
        createPanel.add(buttonPanel);
        
        //TEST CODE
        createPanel.add(new KSButton("Get Comments", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				try {
					commentServiceAsync.getComments(referenceId, referenceKey, new AsyncCallback<List<CommentInfo>>(){

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
							
						}

						@Override
						public void onSuccess(List<CommentInfo> result) {
							String out = "";
							Collections.sort(result, commentInfoComparator);
							for(CommentInfo i: result){
								out = out + i.getCommentText().getPlain() + "\n\n";
							}
							Window.alert(out);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
        }));
        
        layout.add(createPanel);
        layout.add(commentList);
        return layout;
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        refreshComments();
    }

    @Override
    public String getReferenceId() {
        return referenceId;
    }

    @Override
    public String getReferenceKey() {
        return referenceKey;
    }

    @Override
    public void setReferenceId(String id) {
        referenceId = id;
    }

    @Override
    public void setReferenceKey(String key) {
        referenceKey = key;
    }
    
    
    public void refreshComments(){
        commentList.clear();
        commentList.add(loadingComments);
        //rpc call to get all current comments and populate into comment list
    	try {
			commentServiceAsync.getComments(referenceId, referenceKey, new AsyncCallback<List<CommentInfo>>(){

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					
				}

				@Override
				public void onSuccess(List<CommentInfo> result) {
					comments.clear();
					Collections.sort(result, commentInfoComparator);
					for(CommentInfo c: result){
						Comment commentWidget = new Comment(c);
						comments.add(commentWidget);
						commentList.add(commentWidget);
					}
					commentList.remove(loadingComments);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
    }
    
    private void showCommentsEditActions(boolean show){
    	if(show){
    		//FIXME do a check to see if the current person can edit for each comment in the list then show the actions, else
    		//dont show them
    		if(!showingEditButtons){
	    		createPanel.setVisible(show);
	    		commentEditorPanel.setWidget(commentEditor);
	    		for(Comment c: comments){
	        		c.showEditActions(show);
	        	}
	    		showingEditButtons = true;
    		}
    	}
    	else{
    		if(showingEditButtons){
	    		createPanel.setVisible(show);
	    		commentEditorPanel.remove(commentEditor);
	    		
	    		for(Comment c: comments){
	        		c.showEditActions(show);
	        	}
	    		showingEditButtons = false;
    		}
    	}
    	
    }
    
    private KSRichEditor editor = new KSRichEditor();
    
    private class Comment extends Composite{
        
        private SimplePanel content = new SimplePanel();
        private VerticalFlowPanel editLayout = new VerticalFlowPanel();
        private VerticalFlowPanel viewLayout = new VerticalFlowPanel();
        private HorizontalBlockFlowPanel header = new HorizontalBlockFlowPanel();
        private HorizontalBlockFlowPanel headerTextContainer = new HorizontalBlockFlowPanel();
        private HorizontalBlockFlowPanel footer = new HorizontalBlockFlowPanel();
        private HorizontalBlockFlowPanel editActions = new HorizontalBlockFlowPanel();
        
        Image edit = KSImages.INSTANCE.editComment().createImage();
        Image delete = KSImages.INSTANCE.deleteComment().createImage();
        
        private KSLabel name = new KSLabel();
        private HTML commentText = new HTML();
        private KSLabel datePosted = new KSLabel();
        private KSLabel dateModified = new KSLabel();
        
        private CommentInfo info;
        
        public Comment(CommentInfo info){
            this.info = info;
            edit.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                	editLayout.clear();
                    //editActions.setVisible(false);
                	showCommentsEditActions(false);
                    content.addStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                    editor.setHTML(Comment.this.info.getCommentText().getFormatted());
                    
                    ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){
                        @Override
                        public void exec(ConfirmCancelEnum result) {
                            switch(result){
                                case CONFIRM:
                                	
                                    Comment.this.info.getCommentText().setFormatted(CommentPanel.this.editor.getHTML());
                                    Comment.this.info.getCommentText().setPlain(CommentPanel.this.editor.getText());
                                    
                                	try {
										commentServiceAsync.updateComment(referenceId, referenceKey, Comment.this.info, new AsyncCallback<CommentInfo>(){

											@Override
											public void onFailure(Throwable caught) {
												caught.printStackTrace();
												
											}

											@Override
											public void onSuccess(CommentInfo result) {
												Comment.this.info = result;
												content.removeStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
												editor.setText("");
												editor.removeFromParent();
			                                    setupViewLayout();
											}
											
										});
									} catch (Exception e) {
										e.printStackTrace();
									}
                                	
                                    break;
                                case CANCEL:
                                	content.removeStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                                	editor.setText("");
                                	editor.removeFromParent();
                                    setupViewLayout();
                                    break;
                            }
                            
                        }
                    });
                    
                    buttonPanel.addStyleName(KSStyles.KS_COMMENT_INLINE_EDIT);
                    buttonPanel.setContent(editor);
                    editLayout.add(header);
                    editLayout.add(buttonPanel);
                    content.setWidget(editLayout);
                }
            });
            
            delete.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                	try {
						commentServiceAsync.removeComment(Comment.this.info.getId(), referenceId, referenceKey, new AsyncCallback<StatusInfo>(){

							@Override
							public void onFailure(Throwable caught) {
								caught.printStackTrace();
								
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
						e.printStackTrace();
					}
                }
            });
            
            setupDefaultStyles();
            headerTextContainer.add(name);
            headerTextContainer.add(datePosted);
            editActions.add(edit);
            editActions.add(delete);
            header.add(headerTextContainer);
            header.add(editActions);
            footer.add(dateModified);
            setupViewLayout();

            this.initWidget(content);
        }
        
        private void setupDefaultStyles(){
        	content.addStyleName(KSStyles.KS_COMMENT_CONTAINER);
        	header.addStyleName(KSStyles.KS_COMMENT_HEADER);
        	headerTextContainer.addStyleName(KSStyles.KS_COMMENT_HEADER_LEFT);
        	footer.addStyleName(KSStyles.KS_COMMENT_FOOTER);
        	edit.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	delete.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	editActions.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON_PANEL);
        	name.addStyleName(KSStyles.KS_COMMENT_NAME);
        	commentText.addStyleName(KSStyles.KS_COMMENT_TEXT);
        	datePosted.addStyleName(KSStyles.KS_COMMENT_DATE_CREATED);
        	dateModified.addStyleName(KSStyles.KS_COMMENT_DATE_MODIFIED);
        	editor.setStyleName(KSStyles.KS_COMMENT_INLINE_EDIT_EDITOR);
        }
        
        private void setupViewLayout(){
            
            viewLayout.clear();
            viewLayout.add(header);
            viewLayout.add(commentText);
            viewLayout.add(footer);  
            
            //FIXME: this will actually call the person service to get person info
            name.setText(info.getMetaInfo().getCreateId());
            
            if(info.getCommentText() != null){
                commentText.setHTML(info.getCommentText().getFormatted());
            }
            if(info.getMetaInfo().getCreateTime() != null){
                datePosted.setText(" " + formatter.format(info.getMetaInfo().getCreateTime()));
            }
            if(info.getMetaInfo().getUpdateTime() != null && !(info.getMetaInfo().getUpdateTime().equals(info.getMetaInfo().getCreateTime()))){
                dateModified.setText("Last Modified: " + formatter.format(info.getMetaInfo().getUpdateTime()));
            }
            
            content.setWidget(viewLayout);
            
            
            showCommentsEditActions(true);
        }
        
        public void showEditActions(boolean show){
        	editActions.setVisible(show);
        }
        
    }
}
