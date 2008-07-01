package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.ui.ListBox;

/**
 * @author Kuali Student Team
 *         </p>
 *         The standard gwt ListBox with a couple extra methods I commonly use.
 */
public class SearchListBox extends ListBox {

    public int findIndex(String value) {
        int iRet = -1;
        if (value == null)
            return iRet;
        for (int i = 0; i < this.getItemCount(); i++) {
            if (value.equals(this.getValue(i))) {
                iRet = i;
            }
        }
        return iRet;
    }

    public void setItemSelected(String value, boolean selected) throws java.lang.IndexOutOfBoundsException {
        int index = this.findIndex(value);
        if (index >= 0)
            this.setItemSelected(index, selected);
    }

    public String getSelectedValue() {
        return this.getValue(this.getSelectedIndex());
    }

    public String getSelectedText() {
        return this.getItemText(this.getSelectedIndex());
    }

}
