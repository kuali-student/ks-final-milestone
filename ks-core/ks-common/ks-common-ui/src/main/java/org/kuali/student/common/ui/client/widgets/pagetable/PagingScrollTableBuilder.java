/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.r1.common.dto.Idable;

import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.CellRenderer;
import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.DefaultTableDefinition;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.user.client.ui.HTML;
/**
 * Constructing a PagingScrollTable from GWT's Incubator is complicated. This class uses the Builder
 * Pattern to simplify it. All public methods, except build() return a reference to the builder
 * so methods can be chained; these methods can be called in any order.
 * Calling build() at the end of the chain returns the constructed PagingScrollTable
 * {@code
 *   pagingScrollTable = new PagingScrollTableBuilder<Person>().tablePixelSize(220, 200).cacheTable(10, 10).
 *     columnDefinitions(createColumnDefinitions()).build(new PersonDTOs().getPersons());
 * }
 * Parameterized type is the Data Transfer Object type which contains a row's data
 *
 * Column Definitions map a table column to a dto's field and set column header

 *
 * PagingOptions are the paging widget. It is created after the builder creates the table and
 * may be positioned anywhere.
 * @see com.google.gwt.gen2.table.client.PagingOptions
 *
 * RowSelectionHandler is added after the builder creates the table.
 * @see com.google.gwt.gen2.table.event.client.RowSelectionHandler
 * @see com.google.gwt.gen2.table.event.client.FixedWidthGrid
 * @see org.kuali.student.common.ui.client.widgets.pagetable.TableSelectionToLabelHandler
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
@Deprecated
public class PagingScrollTableBuilder<RowType extends Idable> {
    private PagingScrollTable<RowType> pagingScrollTable;
    private int tablePixelWidth = 0;
    private int tablePixelHeight = 0;
    private boolean isPagable = false;
    private int numPageRows = 0;
    private int numPages = 0;
    private SelectionPolicy selectionPolicy = SelectionPolicy.MULTI_ROW;
    private List<AbstractColumnDefinition<RowType, ?>> columnDefs;
    private List<Integer> columnPixelWidths = new ArrayList<Integer>();


    /**
     * This constructs the builder
     *
     */
    public PagingScrollTableBuilder() {
        super();
    }


    /**
     * This method defines the table's display size in pixels
     * Required
     *
     * @param tablePixelWidth
     * @param tablePixelHeight
     * @return builder
     */
    public PagingScrollTableBuilder<RowType> tablePixelSize(int tablePixelWidth,int tablePixelHeight) {
        this.tablePixelWidth = tablePixelWidth;
        this.tablePixelHeight = tablePixelHeight;
        return this;
    }

    /**
     * This method defines the table's cache
     * Optional, use for paging table only. If not called, table displays all rows.
     *
     * @param numPageRows to display on a page
     * @param numPages in the table
     * @return builder
     */
    public PagingScrollTableBuilder<RowType> cacheTable(int numPageRows,int numPages) {
        this.numPageRows = numPageRows;
        this.numPages = numPages;
        this.isPagable = true;
        return this;
    }

    /**
     * This method adds the table's column definitions
     * Required
     *
     * @see com.google.gwt.gen2.table.client.AbstractColumnDefinition
     * Set a column definitions preferredWidth in pixels, and the builder will use that to
     * set the column width in the table. This works around setPreferredWidth not setting it itself.
     * ColumnDefinitions may be reused in other PagingScrollTables for the same dto. They are
     * passed to the builder as a
     * {@code List<AbstractColumnDefinition<RowType, ?>>}
     * Where rowType is the dto and '?' is the column type. The column definition's index in the list
     * is its column index in the table.
     *
     * @param columnDefs table display column index equals columnDefs index
     * @return builder
     */
    public PagingScrollTableBuilder<RowType> columnDefinitions(List<AbstractColumnDefinition<RowType, ?>> columnDefs) {
        this.columnDefs = columnDefs;
        return this;
    }
    
    /**
     * This method sets row selection policy. Get this setting from the search definition when it is available.
     * Default is MULTI_ROW
     * Optional
     * @since M5
     * @param selectionPolicy
     * @return builder
     */
    public PagingScrollTableBuilder<RowType> setSelectionPolicy(SelectionPolicy selectionPolicy){
        this.selectionPolicy = selectionPolicy;
        return this;
    }
    /**
     * This method builds the table model. Call at the end of the builder method chain.
     * Required
     *
     *
     * @return the built pagingScrollTable
     */
    @SuppressWarnings("unchecked")//columnDef cast
    public PagingScrollTable<RowType> build(GenericTableModel tableModel) {
        DefaultTableDefinition<RowType> tableDefinition = new DefaultTableDefinition<RowType>();
        if(columnDefs!=null){
	        for (AbstractColumnDefinition columnDef: columnDefs) {
	            columnPixelWidths.add(columnDef.getPreferredColumnWidth());
	            CellRenderer renderer = new CellRenderer(){

					@Override
					public void renderRowValue(Object rowValue,
							ColumnDefinition columnDef, AbstractCellView view) {
						if(rowValue!=null&& rowValue instanceof ResultRow){
							view.setHTML((String)columnDef.getCellValue(rowValue));
						}
					}
	            	
	            };
	            columnDef.setCellRenderer(renderer);
	            tableDefinition.addColumnDefinition(columnDef);
	        }
        }

        if(isPagable){
            pagingScrollTable = new PagingScrollTable<RowType>(tableModel.createCachedTableModel(numPageRows,numPages),tableDefinition);
            pagingScrollTable.setPageSize(numPageRows);

        }else {
            pagingScrollTable = new PagingScrollTable<RowType>(tableModel,tableDefinition);
            pagingScrollTable.setPageSize(tableModel.getRowCount());
        }
        pagingScrollTable.setPixelSize(tablePixelWidth,tablePixelHeight);//FIXME workaround for incubator bug   [KSCOR-225] This table to be replaced in M6
        pagingScrollTable.setEmptyTableWidget(new HTML("There is no data to display"));

        pagingScrollTable.getDataTable().setSelectionPolicy(selectionPolicy);
        
        pagingScrollTable.getHeaderTable().setWidth("100%");
        pagingScrollTable.getDataTable().setWidth("100%");

        return this.pagingScrollTable;
    }

}
