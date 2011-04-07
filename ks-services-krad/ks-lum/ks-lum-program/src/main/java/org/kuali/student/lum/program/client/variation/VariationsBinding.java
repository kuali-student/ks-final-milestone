package org.kuali.student.lum.program.client.variation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramUtils;
import org.kuali.student.lum.program.client.events.UpdateEvent;
import org.kuali.student.lum.program.client.major.MajorManager;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class VariationsBinding extends ModelWidgetBindingSupport<FlexTable> {

    private String url;

    private boolean editable;

    private Configuration configuration;

    public VariationsBinding(String url, boolean editable) {
        this.url = url;
        this.editable = editable;
    }

    public VariationsBinding(String url, boolean editable, Configuration configuration) {
        this(url, editable);
        this.configuration = configuration;
    }

    @Override
    public void setModelValue(FlexTable table, DataModel model, String path) {
        GWT.log("VariationsBinding.setModelValue...", null);
    }

    @Override
    public void setWidgetValue(final FlexTable table, final DataModel model, String path) {
        table.clear();
        final Data variationMap = model.get(path);
        if (variationMap != null) {
            int row = 0;
            for (final Data.Property property : variationMap) {
                final Data variationData = property.getValue();
                final int currentRow = row;
                Anchor anchor = new Anchor(getVariationName(variationData));
                anchor.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        ProgramRegistry.setData(variationData);
                        ProgramRegistry.setRow(currentRow);
                        ProgramUtils.addCredentialProgramDataToVariation(variationData, model);
                        String id = (String) model.get("id");
                        ViewContext viewContext = new ViewContext();
                        viewContext.setId(id);
                        viewContext.setIdType(IdType.OBJECT_ID);
                        HistoryManager.navigate(url, viewContext);
                    }
                });
                table.setWidget(row, 0, anchor);
                if (editable) {
                    KSButton removeButton = new KSButton(ProgramProperties.get().common_remove());
                    table.setWidget(row, 1, removeButton);                                             
                    removeButton.addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                            if (Window.confirm("Are you sure you want to delete specialization?")) {
                                variationMap.remove(new Data.IntegerKey(currentRow));
                                MajorManager.getEventBus().fireEvent(new UpdateEvent());
                            }
                        }
                    });
                    if (configuration != null && configuration.checkPermission(model)) {
                        removeButton.setEnabled(false);
                    }

                }
                row++;
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