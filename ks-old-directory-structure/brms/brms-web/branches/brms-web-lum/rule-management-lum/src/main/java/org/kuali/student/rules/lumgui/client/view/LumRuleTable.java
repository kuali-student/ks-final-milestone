package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.rules.lumgui.client.model.ILumModelObject;
import org.kuali.student.rules.lumgui.client.model.RequirementComponentVO;
import org.kuali.student.rules.lumgui.client.model.StatementVO;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LumRuleTable<T extends ILumModelObject> extends Composite implements ModelWidget<T> {
    
    private Controller controller;
    private final SimplePanel mainPanel = new SimplePanel();    
    private boolean loaded = false;
    private T modelObject; 
    private FlexTable table;
    private String modelObjectFieldName;
    
    public LumRuleTable(String modelObjectfieldName) {
        super.initWidget(mainPanel);
        this.modelObjectFieldName = modelObjectfieldName;
    }

    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            // get a reference to our parent controller
            controller = MVC.findParentController(this);
        }
    }

    public void add(T modelObject) {
        this.modelObject = modelObject;
        redrawTable();
    }

    public void addBulk(Collection<T> collection) {
        if (collection != null && !collection.isEmpty()) {
            this.modelObject = collection.iterator().next();
        }
        redrawTable();
    }

    public void clear() {
        modelObject = null;
        redrawTable();
    }

    public List<T> getItems() {
        List<T> items = new ArrayList<T>();
        items.add(modelObject);
        return items;
    }

    public T getSelection() {
        return modelObject;
    }

    public void remove(T modelObject) {
        clear();
    }

    public void select(T modelObject) {
        this.modelObject = modelObject;
        redrawTable();
    }

    public void update(T modelObject) {
        this.modelObject = modelObject;
        redrawTable();
    }
    
    private void redrawTable() {
        if (table != null) {
            mainPanel.remove(table);
        }
        table = new FlexTable();
        StatementVO statement = (StatementVO) modelObject.
            getValue(modelObjectFieldName);
        if (statement != null) {
            table.setWidget(0, 0, getStatementPanel(statement, true));
        }
        mainPanel.add(table);
    }
    
    private void addBorder(Widget widget) {
        widget.getElement().getStyle().setProperty("border", "1px solid red");
    }
    
    /**
     * 
     * lays out the statement by calling getStatementPanel/getRequirmentComponentPanel
     * recursively using depth first search
     * algorithm.
     * 
     * @param statement
     */
    private FlexTable getStatementPanel(StatementVO statement, boolean parent) {
        FlexTable subTable = new FlexTable();
        subTable.setCellPadding(0);
        subTable.setCellSpacing(0);
        addBorder(subTable);
        if (statement.isNested()) {
            Label operatorLabel = new Label(statement.getOperator().toString());
            operatorLabel.setWidth("50px");
            subTable.setWidget(0, 0, 
                    operatorLabel);
            subTable.getFlexCellFormatter().setRowSpan(
                    0, 0, statement.getStatements().size());
            subTable.getFlexCellFormatter().setHorizontalAlignment(
                    0, 0, HasHorizontalAlignment.ALIGN_CENTER);
            for (int rowIndex = 0, numStatements = (statement.getStatements() == null)? 0 :
                statement.getStatements().size(); rowIndex < numStatements; rowIndex++
            ) {
                StatementVO subStatement = statement.getStatements().get(rowIndex);
                FlexTable subStatementPanel = getStatementPanel(subStatement, false);
                int colIndex = (rowIndex == 0)? 1 : 0;
                if (rowIndex == 0) {
//                    System.out.println(statement.getId() + " adding left border");
//                    addLeftBorder(subTable);
                } else {
//                    System.out.println(statement.getId() + " adding top border");
//                    addTopBorder(subTable);
//                    System.out.println(statement.getId() + " adding left border");
//                    addLeftBorder(subTable);
                }
                subTable.setWidget(rowIndex, colIndex, subStatementPanel);
            }
        } else {
            Label operatorLabel = new Label(statement.getOperator().toString());
            operatorLabel.setWidth("50px");
            subTable.setWidget(0, 0, 
                    operatorLabel);
            subTable.getFlexCellFormatter().setRowSpan(
                    0, 0, statement.getRequirementComponents().size());
            subTable.getFlexCellFormatter().setHorizontalAlignment(
                    0, 0, HasHorizontalAlignment.ALIGN_CENTER);
            for (int rowIndex = 0, numRequirementComponents = 
                (statement.getRequirementComponents() == null)? 0 :
                    statement.getRequirementComponents().size(); 
            rowIndex < numRequirementComponents; rowIndex++
            ) {
                RequirementComponentVO requirementComponent =
                    statement.getRequirementComponents().get(rowIndex);
                int colIndex = (rowIndex == 0)? 1 : 0;
                subTable.setWidget(rowIndex, colIndex, 
                        getRequirmentComponentPanel(requirementComponent));
            }
        }
        return subTable;
    }
    
    private Panel getRequirmentComponentPanel(RequirementComponentVO requirementComponent) {
        SimplePanel requirementComponentPanel = new SimplePanel();
        Label lbDescription = new Label(requirementComponent.getDesc());
        lbDescription.setWidth("400px");
        addBorder(requirementComponentPanel);
        requirementComponentPanel.add(lbDescription);
        return requirementComponentPanel;
    }
    
}
