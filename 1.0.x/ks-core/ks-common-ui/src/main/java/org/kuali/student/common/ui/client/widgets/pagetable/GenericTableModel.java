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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.dto.Idable;

import com.google.gwt.gen2.table.client.CachedTableModel;
import com.google.gwt.gen2.table.client.MutableTableModel;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.gen2.table.client.TableModelHelper.SerializableResponse;


/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 * @param <IsSerializiable>
 *
 */
public class GenericTableModel<RowType> extends MutableTableModel<RowType> {
    private List<RowType> rowDTOs = null; 
    private int fromIndex = 0;
    /**
     * A boolean indicating that we should throw an error.
     */
    private boolean errorMode = false;
    /**
     * A boolean indicating that we should return 0 rowDTOs in the response.
     */
    private boolean zeroMode = false;
    /**
     * This constructs an empty table model for the RowType
     * 
     */
    public GenericTableModel() {
        super();
    }
    /**
     * This constructs a populated table model for the RowType
     * 
     */
    public GenericTableModel(List<RowType>rows) {
        super();
        setRows(rows);
    }
    /**
     * This returns cached table model for the RowType
     * 
     */
    public CachedTableModel<RowType>createCachedTableModel(int pageSize,int pages) {
        CachedTableModel<RowType>cachedTableModel = new CachedTableModel<RowType>(this);
        cachedTableModel.setPreCachedRowCount(pageSize);
        cachedTableModel.setPostCachedRowCount(pageSize);
        cachedTableModel.setRowCount(pageSize * pages);
        return cachedTableModel;
    }
    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.MutableTableModel#onRowInserted(int)
     */
    @Override
    protected boolean onRowInserted(int beforeRow) {
        return true;
    }

    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.MutableTableModel#onRowRemoved(int)
     */
    @Override
    protected boolean onRowRemoved(int row) {
        return true;
    }

    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.gen2.table.client.MutableTableModel#onSetRowValue(int, java.lang.Object)
     */
    @Override
    protected boolean onSetRowValue(int row, RowType rowValue) {
        return true;
    }

    /**
     * This overridden method handles only errorMode, zeroMode, and local dtos
     * Production code will need rpc to load server side dto's, that feature can be 
     * added in a subclass and perhaps here if it can be done generically
     * 
     * @see com.google.gwt.gen2.table.client.TableModel#requestRows(com.google.gwt.gen2.table.client.TableModelHelper.Request, com.google.gwt.gen2.table.client.TableModel.Callback)
     */
    @Override
    public void requestRows(Request request, final Callback<RowType> callback) {
        List<RowType> selectedRows = null;
        if (errorMode) {
            // Return an error
            callback.onFailure(new Exception("An error has occured."));
        } else if (zeroMode) {
            // Return an empty result
            selectedRows = new ArrayList<RowType>(0);
            callback.onRowsReady(request, new SerializableResponse(selectedRows));//unchecked warning
        } else {
            // Generate data locally
            int numRows = request.getNumRows();
            //fromIndex = request.getStartRow(); This disables column sort, bug?
            if((fromIndex + numRows) >= rowDTOs.size()) {
                fromIndex = 0;
            } 
            int toIndex = fromIndex + numRows;
            selectedRows = new ArrayList<RowType>();
            for(int i = fromIndex; i < toIndex; i++) {
                RowType e = rowDTOs.get(i);
                selectedRows.add(e);
            }
            fromIndex = toIndex;
            SerializableResponse response = new SerializableResponse(selectedRows);//unchecked warning
            callback.onRowsReady(request, response);//unchecked warning
        }
    }

    /**
     * @param rowDTOs the rowDTOs to set
     */
    public void setRows(List<RowType> rows) {
        
            List<Idable> ids = (List<Idable>)rows;//Force ClassCast exception if list items aren't Idable         
            this.rowDTOs = rows;
    }
    /**
     * This overridden method returns the static size the table
     * 
     * @see com.google.gwt.gen2.table.client.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        // TODO return dynamic size when loading with RPC
        return rowDTOs.size();
    }
    /**
     * @return the errorMode
     */
    public boolean isErrorMode() {
        return errorMode;
    }
    /**
     * @param errorMode the errorMode to set
     */
    public void setErrorMode(boolean errorMode) {
        this.errorMode = errorMode;
    }
    /**
     * @return the zeroMode
     */
    public boolean isZeroMode() {
        return zeroMode;
    }
    /**
     * @param zeroMode the zeroMode to set
     */
    public void setZeroMode(boolean zeroMode) {
        this.zeroMode = zeroMode;
    }
}
