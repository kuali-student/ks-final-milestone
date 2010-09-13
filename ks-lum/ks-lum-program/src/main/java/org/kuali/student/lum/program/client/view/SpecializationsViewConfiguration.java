package org.kuali.student.lum.program.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.VariationRegistry;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class SpecializationsViewConfiguration extends AbstractSectionConfiguration {

    public SpecializationsViewConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SPECIALIZATIONS, ProgramProperties.get().program_menu_sections_specializations(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        configurer.addReadOnlyField(rootSection, ProgramConstants.VARIATIONS, new MessageKeyInfo(""), new VerticalPanel()).setWidgetBinding(new VariationsBinding());
    }


    private static class VariationsBinding extends ModelWidgetBindingSupport<VerticalPanel> {

        @Override
        public void setModelValue(VerticalPanel verticalPanel, DataModel model, String path) {
            throw new UnsupportedOperationException("Method is not implemented");
        }

        @Override
        public void setWidgetValue(VerticalPanel verticalPanel, final DataModel model, String path) {
            verticalPanel.clear();
            Data variationMap = model.get(path);
            if (variationMap != null) {
                for (Data.Property property : variationMap) {
                    final Data variationData = property.getValue();
                    Anchor anchor = new Anchor(getVariationName(variationData));
                    anchor.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            VariationRegistry.setData(variationData);
                            String id = (String) model.get("id");
                            ViewContext viewContext = new ViewContext();
                            viewContext.setId(id);
                            viewContext.setIdType(ViewContext.IdType.OBJECT_ID);
                            HistoryManager.navigate("/HOME/CURRICULUM_HOME/VARIATION_VIEW", viewContext);
                        }
                    });
                    verticalPanel.add(anchor);
                }
            }
        }

        private String getVariationName(Data data) {
            StringBuilder nameBuilder = new StringBuilder();
            nameBuilder.append(data.<Object>get(ProgramConstants.LONG_TITLE));
            nameBuilder.append(" (").append(data.<Object>get(ProgramConstants.CODE)).append(")");
            return nameBuilder.toString();

        }
    }
}
