/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.List;

import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Garey
 */
public class LuTreePanel extends Composite {

    Tree luTree = null;
    boolean loaded = false;

    public void addTreeListener(TreeListener listener) {
        luTree.addTreeListener(listener);
    }

    /**
     * 
     */
    public LuTreePanel() {
        VerticalPanel vPanel = new VerticalPanel();
        luTree = new Tree();

        vPanel.add(luTree);

        initWidget(vPanel);
    }

    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;

            luTree.addTreeListener(LuTypeItem.GET_CLU);
            luTree.addTreeListener(CluDisplayItem.GET_LUI);

            DeferredCommand.addCommand(new Command() {
                public void execute() {

                    LearningUnitController.findLuTypes(new AsyncCallback() {
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(Object result) {
                            List<GwtLuTypeInfo> lTypes = (List<GwtLuTypeInfo>) result;
                            if (lTypes != null) {
                                populateTree(lTypes);
                            }
                        }
                    });
                }
            });

        }
    }

    protected void populateTree(List<GwtLuTypeInfo> lTypes) {
        if (lTypes != null) {
            this.luTree.removeItems();
            DOM.setStyleAttribute(this.getElement(), "paddingLeft", "0px");
            for (GwtLuTypeInfo luTypeInfo : lTypes) {
                LuTypeItem luItem = new LuTypeItem(luTypeInfo);
                this.luTree.addItem(luItem);
            }
        }

    }

}
