package org.kuali.student.lum.common.client.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.ListBox;

/**
 * Dropdown list components. Extends ListBox to add useful methods and support for underlying model.
 * Each item in dropdown list is associated with corresponding model item.
 *
 * @author Igor
 */
public class DropdownList extends ListBox {

    /**
     * Components model.
     */
    private Map<String, Integer> model = new HashMap<String, Integer>();

    /**
     * Selected index.
     */
    private Integer index = 0;

    public DropdownList() {
    }

    public DropdownList(List<String> list) {
        for (String s : list) {
            addItem(s);
        }
    }

    @Override
    public void addItem(String value) {
        model.put(value, index++);
        super.addItem(value);
    }

    public void setSelectedItem(String value) {
        if (model.get(value) == null) {
            setSelectedIndex(0);
        } else {
            setSelectedIndex(model.get(value));
        }
    }

    public String getSelectedValue() {
        return getValue(getSelectedIndex());
    }

    public void setList(List<String> relationship) {
        for (String s : relationship) {
            addItem(s);
        }
    }

    public void reset() {
        setSelectedIndex(0);
    }

    public void clear() {
        super.clear();
        model.clear();
        index = 0;
    }

    public void removeItem(String value) {
        if (null != model.get(value)) {
            removeItem(model.get(value));
        }
    }
}
