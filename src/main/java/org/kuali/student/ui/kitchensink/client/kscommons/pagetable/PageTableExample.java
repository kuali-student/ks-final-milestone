package org.kuali.student.ui.kitchensink.client.kscommons.pagetable;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;


import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.gen2.table.client.CachedTableModel;
import com.google.gwt.gen2.table.client.DefaultTableDefinition;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.TableDefinition;


public class PageTableExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    PagingScrollTable<Staffer> pagingScrollTable;
    
    final KSLabel label = new KSLabel("Paging and scrollable table with sortable columns: ", false);
    private Roster tableModel;
    /**
     * The {@link CachedTableModel} around the main table model.
     */
    private CachedTableModel<Staffer> cachedTableModel = null;
    
    /**
     * The {@link DefaultTableDefinition}.
     */
    private DefaultTableDefinition<Staffer> tableDefinition = null;

    public PageTableExample(int pageSize) {

        createCachedTableModel(pageSize);
        TableDefinition<Staffer> tableDef = createTableDefinition();
        pagingScrollTable = new PagingScrollTable<Staffer>(cachedTableModel,tableDef);
        pagingScrollTable.setPageSize(pageSize);
        pagingScrollTable.setEmptyTableWidget(new HTML(
            "There is no data to display"));


        pagingScrollTable.setPixelSize(460,200);//FIXME workaround for incubator bug
        main.addStyleName(STYLE_EXAMPLE);

        main.add(label);
        PagingOptions pagingOptions = new PagingOptions(pagingScrollTable);
        main.add(pagingOptions);
        main.add(pagingScrollTable);
        super.initWidget(main);
    }
    public PageTableExample() {
        this(10);
    }
  
    private void createCachedTableModel(int pageSize) {
        tableModel = new Roster();
        cachedTableModel = new CachedTableModel<Staffer>(tableModel);
        cachedTableModel.setPreCachedRowCount(pageSize);
        cachedTableModel.setPostCachedRowCount(pageSize);
        cachedTableModel.setRowCount(pageSize * 10);
    }
    
    private TableDefinition<Staffer> createTableDefinition() {
        tableDefinition = new DefaultTableDefinition<Staffer>();
        
        StafferColumnDefinition<String> columnDefInstitution = new StafferColumnDefinition<String>(
           "Institution" ){

                    @Override
                    public String getCellValue(Staffer rowValue) {        
                        return rowValue.getInstitution();
                    }

                    @Override
                    public void setCellValue(Staffer rowValue, String cellValue) {
                        rowValue.setInstitution(cellValue);   
                    }
        };
        columnDefInstitution.setColumnSortable(true);
        tableDefinition.addColumnDefinition(columnDefInstitution);
        
        StafferColumnDefinition<String> columnDefName = new StafferColumnDefinition<String>(
        "First and Last Name" ){

                 @Override
                 public String getCellValue(Staffer rowValue) {        
                     return rowValue.getName();
                 }

                 @Override
                 public void setCellValue(Staffer rowValue, String cellValue) {
                     rowValue.setName(cellValue);   
                 }
        };
        columnDefName.setColumnSortable(true);
        tableDefinition.addColumnDefinition(columnDefName);
/*        
        StafferColumnDefinition<String> columnDefFTE = new StafferColumnDefinition<String>(
        "FTE %" ){

                 @Override
                 public String getCellValue(Staffer rowValue) {        
                     return rowValue.getFtePercent();
                 }

                 @Override
                 public void setCellValue(Staffer rowValue, String cellValue) {
                     rowValue.setFtePercent(cellValue);   
                 }
        };
        tableDefinition.addColumnDefinition(columnDefFTE);

        StafferColumnDefinition<String> columnDefComment = new StafferColumnDefinition<String>(
        "Comment" ){

                 @Override
                 public String getCellValue(Staffer rowValue) {        
                     return rowValue.getComment();
                 }

                 @Override
                 public void setCellValue(Staffer rowValue, String cellValue) {
                     rowValue.setComment(cellValue);   
                 }
        };
        tableDefinition.addColumnDefinition(columnDefComment);
*/        
        StafferColumnDefinition<String> columnDefPrimaryProjectRole = new StafferColumnDefinition<String>(
        "Primary Project Role" ){

                 @Override
                 public String getCellValue(Staffer rowValue) {        
                     return rowValue.getPrimaryProjectRole();
                 }

                 @Override
                 public void setCellValue(Staffer rowValue, String cellValue) {
                     rowValue.setPrimaryProjectRole(cellValue);   
                 }
        };
        columnDefPrimaryProjectRole.setColumnSortable(true);
        tableDefinition.addColumnDefinition(columnDefPrimaryProjectRole);
        
        
        StafferColumnDefinition<String> columnDefPrimaryTeam = new StafferColumnDefinition<String>(
        "Primary Team" ){

                 @Override
                 public String getCellValue(Staffer rowValue) {        
                     return rowValue.getPrimaryTeam();
                 }

                 @Override
                 public void setCellValue(Staffer rowValue, String cellValue) {
                     rowValue.setPrimaryTeam(cellValue);   
                 }
        };
        columnDefPrimaryTeam.setColumnSortable(true);
        tableDefinition.addColumnDefinition(columnDefPrimaryTeam);
        
 /*       
        StafferColumnDefinition<String> columnDefSubTeamName = new StafferColumnDefinition<String>(
        "Sub Team Name" ){

                 @Override
                 public String getCellValue(Staffer rowValue) {        
                     return rowValue.getSubTeamName();
                 }

                 @Override
                 public void setCellValue(Staffer rowValue, String cellValue) {
                     rowValue.setSubTeamName(cellValue);   
                 }
        };
        tableDefinition.addColumnDefinition(columnDefSubTeamName);*/
        return tableDefinition; 
    }
    
    /**
     * @return the cached table model
     */
    public CachedTableModel<Staffer> getCachedTableModel() {
      return cachedTableModel; 
    }
    /**
     * @return the tableDefinition
     */
    public DefaultTableDefinition<Staffer> getTableDefinition() {
        return tableDefinition;
    }
    /**
     * @return the tableModel
     */
    public Roster getTableModel() {
        return tableModel;
    }

    public void insertDataRow(int beforeRow) {
      getCachedTableModel().insertRow(beforeRow);
    }
}