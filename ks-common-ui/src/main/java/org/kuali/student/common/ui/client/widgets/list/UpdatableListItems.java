package org.kuali.student.common.ui.client.widgets.list;


public interface UpdatableListItems extends ListItems {
    public void update(String id, String attrkey, String attrvalue);
    public void remove(String id);
    public void add(String id);
}
