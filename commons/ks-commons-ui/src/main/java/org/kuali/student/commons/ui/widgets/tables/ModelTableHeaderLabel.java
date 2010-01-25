package org.kuali.student.commons.ui.widgets.tables;

import org.kuali.student.commons.ui.widgets.tables.PagingModelTable.ExposedStyles;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * 
 * Composite widget used to render a column header in a ModelTable 
 * 
 * @author Kuali Student Team
 *
 */
public class ModelTableHeaderLabel extends Composite implements HasText {
    final HorizontalPanel panel = new HorizontalPanel();
    final Label label = new Label();
    final Image sortedAscending = new Image("images/sort-arrow-ascending.png");
    final Image sortedDescending = new Image("images/sort-arrow-descending.png");
    
    /**
     * Creates an empty header label
     *
     */
    public ModelTableHeaderLabel() {
        this("");
    }
    /**
     * 
     * Creates a header label with the specified text
     * 
     * @param text the text to display
     */
    public ModelTableHeaderLabel(String text) {
        label.setText(text);
        panel.add(label);
        panel.add(sortedAscending);
        panel.add(sortedDescending);
        sortedAscending.addStyleName(ExposedStyles.MODELTABLE_SORTIMAGE.toString());
        sortedDescending.addStyleName(ExposedStyles.MODELTABLE_SORTIMAGE.toString());
        label.setStyleName(ExposedStyles.MODELTABLE_COLUMN_HEADER.toString());
        panel.setWidth("100%");
        clearSorted();
        super.initWidget(panel);
    }

    /**
     * 
     * Returns the label text
     * 
     * @see com.google.gwt.user.client.ui.HasText#getText()
     */
    public String getText() {
        return label.getText();
    }

    /**
     * 
     * Sets the label text
     * 
     * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
     */
    public void setText(String text) {
        label.setText(text);
    }
    
    /**
     * 
     * Sets the column as sorted. Does not actually sort the column,
     * but instead indicates whether the sort icon should be displayed
     * 
     * @param ascending
     */
    public void setSorted(boolean ascending) {
        sortedAscending.setVisible(ascending);
        sortedDescending.setVisible(!ascending);
    }
    
    /**
     * 
     * Clears the flags indicating that the column is sorted, hiding the sort icon.
     *
     */
    public void clearSorted() {
        sortedAscending.setVisible(false);
        sortedDescending.setVisible(false);
    }
    
}
