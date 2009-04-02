package org.kuali.student.lum.ui.requirements.client.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.lum.ui.requirements.client.controller.CoreqManager;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseRequisiteView extends ViewComposite {
//    private final Model<Address> model = new Model<Address>();
//    private final AddressManager manager = new AddressManager(model);;
    private final Panel mainPanel = new SimplePanel();
    private final Model<PrereqInfo> model = new Model<PrereqInfo>();
    private final PrereqManager prereqManager = new PrereqManager(model);
    private final CoreqManager coreqManager = new CoreqManager();
    private String cluId;
    
    public CourseRequisiteView(Controller controller, String cluId) {
        super(controller, "Course Requisites");
        super.initWidget(mainPanel);
        this.cluId = cluId;
        layoutMainPanel(mainPanel);
//        model.addModelChangeHandler(new ModelChangeHandler<Address>() {
//            public void onModelChange(ModelChangeEvent<Address> event) {
//                if (person != null) {
//                    switch (event.getAction()) {
//                        case ADD:
//                        case UPDATE:
//                            person.getAddresses().put(event.getValue().getId(), event.getValue());
//                            break;
//                        case REMOVE:
//                            person.getAddresses().remove(event.getValue().getId());
//                            break;
//                    }
//                    firePersonUpdate();
//                }
//            }
//        });
    }
    
    public void setPrereqInfo(PrereqInfo prereqInfo) {
        if (model.getValues() != null && !model.getValues().isEmpty()) {
            for (PrereqInfo oldPrereqInfo : model.getValues()) {
                model.remove(oldPrereqInfo);
            }
        }
        model.add(prereqInfo);
    }
    
    private void layoutMainPanel(Panel parentPanel) {
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(prereqManager);
        verticalPanel.add(coreqManager);
    }

}
