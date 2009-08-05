package org.kuali.student.common.ui.client.widgets.commenttool;

import java.text.DateFormat;

import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.CommentRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.comment.dto.CommentInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class Comment extends Composite{
/*    
    private SimplePanel content = new SimplePanel();
    private VerticalFlowPanel editLayout = new VerticalFlowPanel();
    private VerticalFlowPanel viewLayout = new VerticalFlowPanel();
    private HorizontalBlockFlowPanel header = new HorizontalBlockFlowPanel();
    private HorizontalBlockFlowPanel headerTextContainer = new HorizontalBlockFlowPanel();
    private HorizontalBlockFlowPanel footer = new HorizontalBlockFlowPanel();
    private HorizontalBlockFlowPanel editActions = new HorizontalBlockFlowPanel();
    private static DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
    
    Image edit = KSImages.INSTANCE.editComment().createImage();
    Image delete = KSImages.INSTANCE.deleteComment().createImage();
    
    private KSLabel name = new KSLabel();
    private HTML commentText = new HTML();
    private KSLabel datePosted = new KSLabel();
    private KSLabel dateModified = new KSLabel();
    
    private CommentInfo info;
    
    public Comment(CommentInfo info, CommentRpcServiceAsync commentServiceAsync){
        this.info = info;
        edit.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
            	editLayout.clear();
                editActions.setVisible(false);
                content.addStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

                    @Override
                    public void exec(ConfirmCancelEnum result) {
                        switch(result){
                            case CONFIRM:
                                //update by id
                                //setupViewinfo
                            	content.removeStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                                editActions.setVisible(true);
                                commentServiceAsync.updateComment(referenceId, referenceTypeKey, commentInfo, callback)
                                setupViewLayout();
                                break;
                            case CANCEL:
                            	content.removeStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                                editActions.setVisible(true);
                                
                                setupViewLayout();
                                break;
                        }
                        
                    }
                });
                KSRichEditor editor = new KSRichEditor();
                editor.addStyleName(KSStyles.KS_COMMENT_INLINE_EDIT_EDITOR);
                editor.setHTML(Comment.this.info.getCommentText().getFormatted());
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
                //call comment service to delete
            }
        });
        
        setupDefaultStyles();
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
    }
    
    private void setupViewLayout(){
        
        headerTextContainer.add(name);
        headerTextContainer.add(datePosted);
        editActions.add(edit);
        editActions.add(delete);
        header.add(headerTextContainer);
        header.add(editActions);
        footer.add(dateModified);
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
        
        if(info.getMetaInfo().getUpdateTime() != null){
            dateModified.setText("Last Modified: " + formatter.format(info.getMetaInfo().getUpdateTime()));
        }
        
        content.setWidget(viewLayout);
    }
    */
    
}
