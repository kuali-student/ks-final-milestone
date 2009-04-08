package org.kuali.student.lum.ui.requirements.client.controller;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.events.LogoutEvent;
import org.kuali.student.common.ui.client.mvc.events.LogoutHandler;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.view.CourseRequisiteView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

// TODO fix handler registration bug (handlers registered in onLoad, and not deregistered afterwards
public class LumApplication extends Controller {
    
    public enum CourseViews { COURSE_REQUISITES }

    // TODO: to be removed when course info is ready.  Test course.
    private final CourseRequisiteView courseRequisiteView = new CourseRequisiteView(this);
    private final HorizontalPanel panel = new HorizontalPanel();
    private final SimplePanel viewPanel = new SimplePanel();
    private Model<CourseRuleInfo> courseInfo;
    private String courseId;

    public LumApplication(CourseRuleInfo selectedCourse) {
        courseInfo = new Model<CourseRuleInfo>();
        courseInfo.add(selectedCourse);
        this.courseId = selectedCourse.getId();
        super.initWidget(panel);
        panel.add(viewPanel);
        viewPanel.add(courseRequisiteView);
        showDefaultView();
    }

    @Override
    protected void onLoad() {
        // add event handler to show example of controller listening for unchecked events
        addApplicationEventHandler(LogoutEvent.TYPE, new LogoutHandler() {
            public void onLogout(LogoutEvent event) {
                Window.alert("PersonApplication caught logout event");
            }
        });
    }

    // controller operations
    @Override
    public void renderView(View view) {
        // in this case we know that all of our widgets are composites
        // but we could do view specific rendering, e.g. show a lightbox, etc
        viewPanel.setWidget((ViewComposite) view);
    }

    @Override
    protected void hideView(View view) {
        viewPanel.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class<? extends Idable> modelType, ModelRequestCallback callback) {
        if (modelType.equals(CourseRuleInfo.class)) {
            if (courseInfo == null) {
                courseInfo = new Model<CourseRuleInfo>();
            }
            callback.onModelReady(courseInfo);
        } else {
            super.requestModel(modelType, callback);
        }
    }

    @Override
    public void showDefaultView() {
        showView(CourseViews.COURSE_REQUISITES);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        if (viewType instanceof CourseViews) {
            switch ((CourseViews) viewType) {
                case COURSE_REQUISITES:                   
                    PrereqInfo prereqInfo = new PrereqInfo();
                    prereqInfo.setId(courseInfo.get(courseId).getId());
                    prereqInfo.setNaturalLanguage("Test natural language");
                    prereqInfo.setRationale("Test rationalle");
                    prereqInfo.setStatementVO(new StatementVO(courseInfo.get(courseId).getLuStatementByType("kuali.luStatementType.createCourseAcademicReadiness")));
                    courseRequisiteView.setPrereqInfo(prereqInfo);
                    return courseRequisiteView;
                default:
                    // do nothing
            }
        }
        return null;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
