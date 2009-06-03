package org.kuali.student.common.ui.client.widgets.menus;

public abstract class KSBasicMenuAbstract extends KSMenu{
    public abstract boolean isNumberAllItems();

    public abstract void setNumberAllItems(boolean numberAllItems);
    
    public abstract void setTitle(String title);
    
    public abstract void setDescription(String description);
}
