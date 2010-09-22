package org.kuali.student.lum.program.client.major;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentTool;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor
 */
public class MajorController extends ProgramController {

    /**
     * Constructor.
     *
     * @param programModel
     */
    public MajorController(String name, DataModel programModel, ViewContext viewContext) {
        super(name, programModel, viewContext);
    }

    @Override
    public void requestModel(Class modelType, ModelRequestCallback callback) {
        if (modelType == ReferenceModel.class) {
            ReferenceModel referenceModel = new ReferenceModel();
            referenceModel.setReferenceId((String) programModel.get("id"));
            referenceModel.setReferenceTypeKey(ProgramConstants.MAJOR_TYPE_ID);
            referenceModel.setReferenceType(ProgramConstants.MAJOR_OBJECT_ID);
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("name", (String) programModel.get("name"));
            referenceModel.setReferenceAttributes(attributes);
            callback.onModelReady(referenceModel);
        } else {
            super.requestModel(modelType, callback);
        }
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(createCommentPanel());
    }

    private Widget createCommentPanel() {
        final CommentTool commentTool = new CommentTool(ProgramSections.COMMENTS, "Comments", "kuali.comment.type.generalRemarks", "Program Comments");
        commentTool.setController(this);
        KSButton commentsButton = new KSButton(ProgramProperties.get().comments_button(), KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                commentTool.show();
            }
        });
        return commentsButton;
    }
}
