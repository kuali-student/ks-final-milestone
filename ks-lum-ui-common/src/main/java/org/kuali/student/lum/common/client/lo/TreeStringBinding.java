package org.kuali.student.lum.common.client.lo;

import com.google.gwt.user.client.ui.Label;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.r1.common.assembly.data.Data;

import java.util.*;

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
            RichTextHelper descriptionHelper = new RichTextHelper(loInfoHelper.getDescr());
            Data categoriesData = loDisplayInfoHelper.getCategoryInfoList();
            List<String> categories = new ArrayList<String>();

            if (null != categoriesData) {
                Iterator<Data.Property> itr = categoriesData.realPropertyIterator();

                while (itr.hasNext()) {
                    Data.Property catProp = itr.next();
                    Data catData = catProp.getValue();
                    LoCategoryInfoHelper category = new LoCategoryInfoHelper(catData);
                    categories.add(category.getName());
                }
            }
            
            int index = 0;
            String sequence = loInfoHelper.getSequence();
            if (sequence != null) {
                index = Integer.parseInt(sequence);
            } 
            StringTreeStructure childModel = new StringTreeStructure(index, descriptionHelper.getPlain(), categories);
            transform(loDisplayInfoHelper.getDisplayInfoList(), childModel);
            rootModel.addChild(childModel);
        }
    }

    private void bind(StringTreeStructure stringTreeStructure, KSListPanel ksListPanel) {
        stringTreeStructure.sortChildren();
        List<StringTreeStructure> firstLevelChildren = stringTreeStructure.getChildren();
        for (StringTreeStructure firstLevelChild : firstLevelChildren) {
            addElement(firstLevelChild, ksListPanel);
        }
    }

    private void addElement(StringTreeStructure element, KSListPanel listPanel) {
        if (element.hasChildren()) {
            KSListPanel subPanel = new KSListPanel();
            element.sortChildren();
            for (StringTreeStructure child : element.getChildren()) {
                addElement(child, subPanel);
            }
            if (!element.getCategories().isEmpty()) {
                listPanel.add(subPanel, element.getValue() + element.getCategoriesString());
            } else {
                listPanel.add(subPanel, element.getValue());
            }
        } else {
            if (!element.getCategories().isEmpty()) {
                listPanel.add(new Label(element.getValue() + element.getCategoriesString()));
            } else {
                listPanel.add(new Label(element.getValue()));
            }
        }
    }

    private static class StringTreeStructure {

        private String value;
        private int index;

        private List<StringTreeStructure> children = new ArrayList<StringTreeStructure>();
        private List<String> categories = new LinkedList<String>();

        public StringTreeStructure(int index, String value, List<String> categories) {
            this.setIndex(index);
            this.value = value;
            this.categories = categories;
        }

        public void sortChildren() {
            Collections.sort(children, new Comparator<StringTreeStructure>() {

                @Override
                public int compare(StringTreeStructure o1, StringTreeStructure o2) {
                    return o1.getIndex() - o2.getIndex();
                }
            });
        }

        public StringTreeStructure() {}

        public List<String> getCategories() {
            return categories;
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

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public String getCategoriesString() {
            String result = " (";
            for (int i = 0; i < categories.size(); i++) {
                if (i != categories.size() - 1) {
                    result = result + categories.get(i) + ", ";
                } else {
                    result = result + categories.get(i) + ")";
                }
            }
            return result;
        }
    }
}
