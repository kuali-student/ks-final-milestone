package org.kuali.student.common.ui.client.widgets.table.scroll;

public abstract class Row {
	private boolean isSelected = false;
    private boolean highlighted = false;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public abstract Object getCellData(String columnId);
//	abstract boolean isCellEditable(String columnId);
	public abstract void setCellData(String columnId, Object newValue);

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}