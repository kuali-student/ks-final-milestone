package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.table.ExpressionParser;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.TreeTable;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.lum.ui.requirements.client.model.ILumModelObject;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LumRulePanel <T extends ILumModelObject> extends Composite implements ModelWidget<T> {

    private T modelObject;
    private ExpressionParser parser;
    private TreeTable ruleTable;
    private Panel mainPanel;
    private String modelObjectFieldName;
    private HTML error;
    private String preferredWidth;
    private String preferredHeight;
    
    public LumRulePanel(String modelObjectFieldName) {
        init(modelObjectFieldName);
    }
    
    public void setPreferredSize(String width, String height) {
        this.preferredWidth = width;
        this.preferredHeight = height;
    }
    
    private void init(String modelObjectFieldName) {
        mainPanel = new VerticalPanel();
        super.initWidget(mainPanel);
        initUI(modelObjectFieldName);
    }
    
    private void initUI(String modelObjectFieldName) {
        this.modelObjectFieldName = modelObjectFieldName;
        parser = new ExpressionParser();
        error = new HTML();
        mainPanel.add(error);
    }
    
    private void refreshRuleTable(String booleanAlgebra) {
        error.setText("");
        if (ruleTable != null) {
            mainPanel.remove(ruleTable);
        }
        if (booleanAlgebra != null) {
            Node root = parser.parse(booleanAlgebra);
            if(parser.hasError()){  
                StringBuilder sb = new StringBuilder("Error Message: ");  
                for(String error: parser.getErrorMessage()){  
                    sb.append(error+",");  
                }  
                error.setHTML(sb.toString());  
                return;  
            }
            ruleTable = new TreeTable();
            mainPanel.add(ruleTable);
            ruleTable.buildTable(root);
            ruleTable.setSize(preferredWidth, preferredHeight);
        }
    }
    
    public void add(T modelObject) {
        this.modelObject = modelObject;
    }

    public void addBulk(Collection<T> collection) {
        if (collection != null && !collection.isEmpty()) {
            this.modelObject = collection.iterator().next();
        }
    }

    public void clear() {
        modelObject = null;
        
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
    }

    public void update(T modelObject) {
        this.modelObject = modelObject;
        String booleanAlgebra = (String)modelObject.getValue(modelObjectFieldName);
        refreshRuleTable(booleanAlgebra);
    }

}
