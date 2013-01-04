package org.kuali.student.common.ui.client.widgets.table.scroll;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TableDemoPanel extends Composite{
	DefaultTableModel tableModel = new DefaultTableModel();
	public TableDemoPanel(){
		super.initWidget(createDemoPage());
	}
	private Widget createDemoPage(){
		VerticalPanel p = new VerticalPanel();
		tableModel.setMultipleSelectable(true);
		final Table table = createTable();
		p.add(table);
		table.getScrollPanel().addScrollHandler(new ScrollHandler(){
            @Override
            public void onScroll(ScrollEvent event) {
                System.out.println(table.getScrollPanel().getOffsetHeight());
                System.out.println(table.getScrollPanel().getScrollPosition());
            }
		});
		table.getScrollPanel().setHeight("700px");
		Button appendDataButton = new Button("Append Data");
		appendDataButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Course c = new Course();
				c.setId(1);
				c.setName("1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				tableModel.addRow(new CourseRow(c));
				
				c = new Course();
				c.setId(2);
				c.setName("b");
				tableModel.addRow(new CourseRow(c));
				
				c = new Course();
				c.setId(3);
				c.setName("c");
				tableModel.addRow(new CourseRow(c));
				
				c = new Course();
				c.setId(4);
				c.setName("d");
				tableModel.addRow(new CourseRow(c));
				
				c = new Course();
				c.setId(4);
				c.setName("d");
				tableModel.addRow(new CourseRow(c));
				
				c = new Course();
				c.setId(4);
				c.setName("d");
				tableModel.addRow(new CourseRow(c));
				
				tableModel.fireTableDataChanged();
			}
			
		});
		p.add(appendDataButton);
		return p;

	}
    private Table createTable(){		
	    Column col1 = new Column();
	    col1.setId("id");
		col1.setName("ID");
		col1.setWidth("100px");
		
		col1.setAscendingRowComparator(new CourseIdAscendingRowComparator());
		col1.setDescendingRowComparator(new CourseIdDescendingRowComparator());
		     
		Column col2 = new Column();
		col2.setId("name");
		col2.setName("Name");
		col2.setWidth("300px");

		Column col3 = new Column();
		col3.setId("isFull");
		col3.setName("Full");
		col3.setWidth("500px");
		
		tableModel.installCheckBoxRowHeaderColumn();
		
		tableModel.addColumn(col1);
		tableModel.addColumn(col2);
		tableModel.addColumn(col3);

		Table table = new Table();
		table.setTableModel(tableModel);
		return table;

  }
}
class CourseIdAscendingRowComparator extends RowComparator{
	@Override
	public int compare(Row row0, Row row1) {
		Integer id0 = (Integer)row0.getCellData("id");
		Integer id1 = (Integer)row1.getCellData("id");
		return id0.compareTo(id1);
	}
	
}
class CourseIdDescendingRowComparator extends RowComparator{
	@Override
	public int compare(Row row0, Row row1) {
		Integer id0 = (Integer)row0.getCellData("id");
		Integer id1 = (Integer)row1.getCellData("id");
		return id1.compareTo(id0);
	}
	
}
class CourseRow extends Row{
	private Course course;
	public CourseRow(Course c){
		course = c;
	}
	
	@Override
	public Object getCellData(String columnId) {
		if ("id".equals(columnId)) {
			return course.getId();
		} else if ("name".equals(columnId)) {
			return course.getName();
		} else if ("isFull".equals(columnId)) {
			return course.isFull();
		}
		return null;
	}
	@Override
	public void setCellData(String columnId, Object newValue) {
		if(newValue == null){
			newValue = "";
		}
    	if ("id".equals(columnId)) {
			course.setId(Integer.parseInt(newValue.toString()));
		} else if ("name".equals(columnId)) {
			course.setName(newValue.toString());
		} else if ("isFull".equals(columnId)) {
			course.setFull((Boolean)newValue);
		}
	}
	@Override
    public String toString(){
		return course.getId()+"";
	}
}

class Course {
	private int id;
	private String name;
	private boolean isFull;

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return ""+id;
	}
}