package org.kuali.student.lum.program.client.view;

import com.google.gwt.user.client.ui.Label;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.common.client.lo.LoDisplayInfoHelper;
import org.kuali.student.lum.common.client.lo.LoInfoHelper;
import org.kuali.student.lum.common.client.lo.RichTextHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class TreeStringBinding extends ModelWidgetBindingSupport<KSListPanel> {

    @Override
    public void setModelValue(KSListPanel widget, DataModel model, String path) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public void setWidgetValue(KSListPanel listPanel, DataModel model, String path) {
        listPanel.clear();
        Data loData = model.get(path);
        if (loData != null) {
            StringTreeStructure rootModel = new StringTreeStructure();
            transform(loData, rootModel);
            bind(rootModel, listPanel);
        }
    }

    private void transform(Data loData, StringTreeStructure rootModel) {
        for (Data.Property property : loData) {
            Data loDisplayInfoData = property.getValue();
            LoDisplayInfoHelper loDisplayInfoHelper = new LoDisplayInfoHelper(loDisplayInfoData);
            LoInfoHelper loInfoHelper = new LoInfoHelper(loDisplayInfoHelper.getLoInfo());
            RichTextHelper descriptionHelper = new RichTextHelper(loInfoHelper.getDesc());
            StringTreeStructure childModel = new StringTreeStructure(descriptionHelper.getPlain());
            transform(loDisplayInfoHelper.getDisplayInfoList(), childModel);
            rootModel.addChild(childModel);
        }
    }

    private void bind(StringTreeStructure stringTreeStructure, KSListPanel ksListPanel) {
        List<StringTreeStructure> firstLevelChildren = stringTreeStructure.getChildren();
        for (StringTreeStructure firstLevelChild : firstLevelChildren) {
            addElement(firstLevelChild, ksListPanel);
        }
    }

    private void addElement(StringTreeStructure element, KSListPanel listPanel) {
        if (element.hasChildren()) {
            KSListPanel subPanel = new KSListPanel();
            for (StringTreeStructure child : element.getChildren()) {
                addElement(child, subPanel);
            }
            listPanel.add(subPanel, element.getValue());
        } else {
            listPanel.add(new Label(element.getValue()));
        }
    }

    private static class StringTreeStructure {

        private String value;

        private List<StringTreeStructure> children = new ArrayList<StringTreeStructure>();

        public StringTreeStructure(String value) {
            this.value = value;
        }

        public StringTreeStructure() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<StringTreeStructure> getChildren() {
            return children;
        }

        public void setChildren(List<StringTreeStructure> children) {
            this.children = children;
        }

        public void addChild(StringTreeStructure child) {
            children.add(child);
        }

        public boolean hasChildren() {
            return !children.isEmpty();
        }
    }
}
