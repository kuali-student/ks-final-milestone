/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.pagetable;

import com.google.gwt.gen2.table.client.AbstractScrollTable;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class KSScrollTable extends AbstractScrollTable {

    

    /**
     * This constructs a ScrollTable with default ScrollTableImages bundle
     * 
     * 
     * @param dataTable sub-table containing cell data
     * @param headerTable sub-table containing header data
     */
    public KSScrollTable(FixedWidthGrid dataTable, FixedWidthFlexTable headerTable) {
        // TODO Gary Struthers - THIS CONSTRUCTOR NEEDS A JAVADOC
        super(dataTable, headerTable);
        super.setCellPadding(3);
        super.setCellSpacing(0);
    }


    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.AbstractScrollTable#getMaximumColumnWidth(int)
     */
    @Override
    public int getMaximumColumnWidth(int column) {
        
        return 150;//guess
    }

    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.AbstractScrollTable#getMinimumColumnWidth(int)
     */
    @Override
    public int getMinimumColumnWidth(int column) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        return 10;
    }

    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.AbstractScrollTable#getPreferredColumnWidth(int)
     */
    @Override
    public int getPreferredColumnWidth(int column) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        return 60;
    }

    /**
     * This overridden method makes all columns sortable
     * 
     * @see com.google.gwt.gen2.table.client.AbstractScrollTable#isColumnSortable(int)
     */
    @Override
    public boolean isColumnSortable(int column) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        return true;
    }

    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.AbstractScrollTable#isColumnTruncatable(int)
     */
    @Override
    public boolean isColumnTruncatable(int column) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.AbstractScrollTable#isFooterColumnTruncatable(int)
     */
    @Override
    public boolean isFooterColumnTruncatable(int column) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.AbstractScrollTable#isHeaderColumnTruncatable(int)
     */
    @Override
    public boolean isHeaderColumnTruncatable(int column) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        return false;
    }

}
