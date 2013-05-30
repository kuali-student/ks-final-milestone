package org.kuali.rice.krad.uif.layout.extension;

import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.layout.TableLayoutManager;

import java.util.List;

/**
 * This class is written to override the default colSpan calculation done in TableLayoutManager.
 * If this LayoutManager is used, it will rely on the row span and col spans set in the XML and
 * not try to calculate the last column col span on its own
 *
 * Temporary fix for KULRICE-9215
 *
 * @Author kmuthu
 * Date: 3/28/13
 */
public class MultiRowSpanTableLayoutManager extends TableLayoutManager {

    @Override
    protected int calculateNumberOfRows(List<? extends Field> items) {
        int rowCount = 0;

        // check flag that indicates only one row should be created
        if (isSuppressLineWrapping()) {
            return 1;
        }

        if (items.size() % getNumberOfDataColumns() > 0) {
            rowCount = ((items.size() / getNumberOfDataColumns()) + 1);
        } else {
            rowCount = items.size() / getNumberOfDataColumns();
        }
        return rowCount;
    }
}
