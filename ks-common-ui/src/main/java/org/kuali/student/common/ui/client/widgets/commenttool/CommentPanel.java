package org.kuali.student.common.ui.client.widgets.commenttool;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.counting.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.comment.dto.CommentInfo;

import com.google.gwt.user.client.ui.Composite;

public class CommentPanel extends Composite{
    private VerticalFlowPanel layout = new VerticalFlowPanel();
    private VerticalFlowPanel commentList = new VerticalFlowPanel();
    private KSRichEditor commentEditor = new KSRichEditor();
    private List<Comment> comments = new ArrayList<Comment>();
    private KSLabel seeComments = new KSLabel("See Comments from");
    private KSLabel loadingComments = new KSLabel("Loading Comments");
    private KSDropDown commentTypes = new KSDropDown();
    private String referenceId;
    private String referenceKey;
    
    public OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

        @Override
        public void exec(OkEnum result) {
            if(result == OkEnum.Ok){
                //RPC call to create comment here
                CommentInfo createdComment = new CommentInfo();
                Comment comment = new Comment(createdComment);
                comments.add(0, comment);
                commentList.insert(comment, 0);
            }
            
        }
    });
    
    
    public CommentPanel(){
        buttonPanel.setButtonText(OkEnum.Ok, "Submit");
        buttonPanel.setContent(commentEditor);
        refreshComments();
        layout.add(buttonPanel);
        layout.add(commentList);
        this.initWidget(layout);
    }
    
    public void refreshComments(){
        commentList.clear();
        commentList.add(loadingComments);
        //rpc call to get all current comments and populate into comment list
        //possibly require a sort on comments by descending order of creation date THEN
        commentList.clear();
        for(Comment c: comments){
            commentList.add(c);
        }
    }
    
    private void sortCommentsByDate(){
        
    }
}
