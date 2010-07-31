package org.kuali.student.lum.lu.ui.tools.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabbedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;

/**
 * @author Igor
 */
public class MajorDisciplineController extends TabbedSectionLayout {

    private ViewComposite mainPanel;

    private SectionView sectionView = new VerticalSectionView(ControllerView.MAIN, "Major Discipline Panel", "1");

    public static enum ControllerView {
        MAIN
    }

    public MajorDisciplineController() {
        super(MajorDisciplineController.class.getName(), new KSTitleContainerImpl("Major Discipline"));
        mainPanel = new MajorDisciplinePanel(this, "Major Discipline Panel");
        addSection(new String[]{"First Tab"}, sectionView);
    }

    @Override
    public void showDefaultView(final Callback<Boolean> onReadyCallback) {
        showView(ControllerView.MAIN, onReadyCallback);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        ControllerView controllerView = (ControllerView) viewType;
        if (controllerView == ControllerView.MAIN) {
            return mainPanel;
        }
        return null;
    }
}
