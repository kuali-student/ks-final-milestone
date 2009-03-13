package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


/**
 * KSRadiobuttonList is a list of radio buttons.
 * 
 * @author Kuali Student Team 
 *
 */
public class KSRadioButtonList extends KSSelectItemWidgetAbstract implements ClickHandler{
    private KSSelectItemWidgetAbstract selectItemWidget = GWT.create(KSRadioButtonListImpl.class);

    
	/**
	 * This constructs a new radiobutton list using the name passed in as the group/list name.
	 * 
	 * @param name the name of the radio button group
	 */
	public KSRadioButtonList(String name) {
        initWidget(selectItemWidget);
        init(name);
	}
	
	protected void init(String name) {
	    selectItemWidget.init(name);
	}


	/**
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
	 */
	public void deSelectItem(String id) {
	    selectItemWidget.deSelectItem(id);	
	}

	/**
	 * @see org.kuali.student.common.ui.client.widgets.list.KSSele ctItemWidgetAbstract#getSelectedItems()
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

    /**
     * Sets the ListItems object to be used for generating this radio button list.
     * 
     * @param listItems the ListItems used for generating this radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setListItems(org.kuali.student.common.ui.client.widgets.list.ListItems)
     */
    public void setListItems(ListItems listItems) {
        selectItemWidget.setListItems(listItems);      
    }

    /**
     * Processes an onClick event
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onClick(com.google.gwt.event.dom.client.ClickEvent)
     */
    @Override
    public void onClick(ClickEvent event) {
        selectItemWidget.onClick(event);
        
    }

    /**
     * Sets if this radio button list can have multiple selections or not.  (Not implemented yet????)
     * 
     * @param isMultipleSelect true if multiple can be selected, false otherwise.
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setMultipleSelect(boolean)
     */
    public void setMultipleSelect(boolean isMultipleSelect) {}

    /**
     * This overridden method is not used
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
     */
    @Override
    public void onLoad() {}
   
    /**
     * Adds a SelectionChangeHandler to handle a selection change event (when the user picks another item).
     * 
     * @paran handler a SelectionChangeHandler to handle change events on the radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#addSelectionChangeHandler(org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler)
     */
    public void addSelectionChangeHandler(SelectionChangeHandler handler) {
        selectItemWidget.addSelectionChangeHandler(handler);
    }

    protected void fireChangeEvent() {
        selectItemWidget.fireChangeEvent();
    }

    /**
     * Gets the ListItems object used in this radio button list.
     * 
     * @return the ListItems object used in the radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getListItems()
     */
    public ListItems getListItems() {
        return selectItemWidget.getListItems();
    }

    /**
     * Gets the name of this radio button list
     * 
     * @return the name of this radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getName()
     */
    public String getName() {
        return selectItemWidget.getName();
    }

    /**
     * Sets the name of this radio button list
     * 
     * @param name the new name of the radio button list
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setName(java.lang.String)
     */
    public void setName(String name) {
        selectItemWidget.setName(name);
    }

}


