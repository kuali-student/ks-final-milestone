package org.kuali.student.common.ui.client.widgets.commenttool;

import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
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
    
    private SimplePanel content = new SimplePanel();
    private VerticalFlowPanel editLayout = new VerticalFlowPanel();
    private VerticalFlowPanel viewLayout = new VerticalFlowPanel();
    private HorizontalBlockFlowPanel header = new HorizontalBlockFlowPanel();
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
                editActions.setVisible(false);
                ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

                    @Override
                    public void exec(ConfirmCancelEnum result) {
                        switch(result){
                            case CONFIRM:
                                //update by id
                                //setupViewinfo
                                editActions.setVisible(true);
                                content.setWidget(viewLayout);
                            case CANCEL:
                                editActions.setVisible(true);
                                content.setWidget(viewLayout);
                        }
                        
                    }
                });
                KSRichEditor editor = new KSRichEditor();
                editor.setHTML(Comment.this.info.getCommentText().getFormatted());
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
        
        setupViewInfo();
        header.add(name);
        header.add(datePosted);
        editActions.add(edit);

        editActions.add(delete);
        header.add(editActions);
        footer.add(dateModified);
        viewLayout.add(header);
        viewLayout.add(commentText);
        viewLayout.add(footer);
        content.setWidget(viewLayout);
        this.initWidget(content);
    }
    
    private void setupViewInfo(){
        //FIXME: this will actually call the person service to get person info
        name.setText(info.getMetaInfo().getCreateId());
        if(info.getCommentText() != null){
            commentText.setText(info.getCommentText().getFormatted());
        }
        
        if(info.getMetaInfo().getCreateTime() != null){
            datePosted.setText(info.getMetaInfo().getCreateTime().toString());
        }
        
        if(info.getMetaInfo().getUpdateTime() != null){
            dateModified.setText("Modified" + info.getMetaInfo().getUpdateTime().toString());
        }
    }
    
    
}
