package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasName;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team 
 *
 */
public class KSRadioButtonList extends Composite implements HasName, ClickHandler{
    private KSSelectItemWidgetAbstract selectItemWidget = GWT.create(KSRadioButtonListImpl.class);

    
	public KSRadioButtonList(String name) {
        initWidget(selectItemWidget);
        init(name);
	}
	
	protected void init(String name) {
	    selectItemWidget.init(name);
	}


	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
	 */
	public void deSelectItem(String id) {
	    selectItemWidget.deSelectItem(id);	
	}

	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
	 */
	public List<String> getSelectedItems() {
	    return selectItemWidget.getSelectedItems();
	}


	
	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
	 */
	public void selectItem(String id) {
	    selectItemWidget.selectItem(id);
	}

    public void setListItems(ListItems listItems) {
        selectItemWidget.setListItems(listItems);      
    }

    @Override
    public void onClick(ClickEvent event) {
        selectItemWidget.onClick(event);
        
    }

    public void setMultipleSelect(boolean isMultipleSelect) {}

    /**
     * This overridden method is not used
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
     */
    @Override
    public void onLoad() {}
   
    public void addSelectionChangeHandler(SelectionChangeHandler handler) {
        selectItemWidget.addSelectionChangeHandler(handler);
    }

    protected void fireChangeEvent() {
        selectItemWidget.fireChangeEvent();
    }

    public ListItems getListItems() {
        return selectItemWidget.getListItems();
    }

    public String getName() {
        return selectItemWidget.getName();
    }

    public void setName(String name) {
        selectItemWidget.setName(name);
    }

}


