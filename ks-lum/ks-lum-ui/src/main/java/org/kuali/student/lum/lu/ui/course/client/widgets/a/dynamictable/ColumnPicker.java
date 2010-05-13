/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.LightBox;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Callback;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.StyleEnum;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author wilj
 *
 */
public class ColumnPicker<T> {
	public interface IsSelectedAdapter<T> {
		boolean isSelected(ColumnDefinition<T> column);
	}

	public enum Styles implements StyleEnum {
		COLUMNPICKER("ColumnPicker"),
		COLUMNPICKER_SCROLLPANEL("ColumnPicker-ScrollPanel"),
		COLUMNPICKER_INSTRUCTIONS("ColumnPicker-Instructions"),
		COLUMNPICKER_ERRORMESSAGE("ColumnPicker-ErrorMessage"),
		COLUMNPICKER_OKBUTTON("ColumnPicker-OkButton"),
		COLUMNPICKER_CANCELBUTTON("ColumnPicker-CancelButton"),
		COLUMNPICKER_CHECKBOX("ColumnPicker-CheckBox"),
		COLUMNPICKER_SELECTALL("ColumnPicker-SelectAll");

		private final String style;

		private Styles(final String style) {
			this.style = style;
		}

		/* (non-Javadoc)
		 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.util.StyleEnum#getStyle()
		 */
		@Override
		public String getStyle() {
			return this.style;
		}

	}

	private final List<ColumnDefinition<T>> columns;
	private boolean selectionChanged = false;
	private final Map<ColumnDefinition<T>, Boolean> selection = new HashMap<ColumnDefinition<T>, Boolean>();

	private final String instructions;
	private final IsSelectedAdapter<T> isSelectedAdapter;

	public ColumnPicker(final List<ColumnDefinition<T>> columns, final String instructions,
			final IsSelectedAdapter<T> isSelectedAdapter) {
		this.columns = columns;
		this.instructions = instructions;
		this.isSelectedAdapter = isSelectedAdapter;
	}

	public Map<ColumnDefinition<T>, Boolean> getSelection() {
		return this.selection;
	}

	public boolean isSelectionChanged() {
		return this.selectionChanged;
	}

	public void show(final Callback<Boolean> closeCallback) {

		final LightBox box = new LightBox(RootPanel.get());
		final ScrollPanel scroll = new ScrollPanel();
		final VerticalPanel panel = new VerticalPanel();
		final VerticalPanel columnList = new VerticalPanel();
		final Label instr = new Label(instructions);
		final HorizontalPanel buttons = new HorizontalPanel();
		final Hyperlink selectAll = new Hyperlink("SelectAll", "");
		final Label mustSelectOne = new Label("MustSelectAtLeastOneColumn");

		final Map<CheckBox, ColumnDefinition<T>> map = new HashMap<CheckBox, ColumnDefinition<T>>();
		for (final ColumnDefinition<T> col : columns) {
			final CheckBox check = new CheckBox(col.getRenderer().getDisplayName());
			check.setValue(col.isVisible());
			check.addStyleName(Styles.COLUMNPICKER_CHECKBOX.getStyle());
			columnList.add(check);
			map.put(check, col);
		}

		final Button ok = new Button("OK", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				selection.clear();
				boolean atLeastOneSelected = false;
				for (final CheckBox check : map.keySet()) {
					final ColumnDefinition<T> col = map.get(check);
					selection.put(col, check.getValue());
					final boolean selected = check.getValue();
					atLeastOneSelected |= selected;
					if (selected ^ isSelectedAdapter.isSelected(col)) {
						selectionChanged = true;
					}
				}
				if (atLeastOneSelected) {
					mustSelectOne.setVisible(false);
					box.hide();
					closeCallback.exec(true);
				} else {
					mustSelectOne.setVisible(true);
				}
			}
		});
		final Button cancel = new Button("Cancel", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				box.hide();
				closeCallback.exec(false);
			}
		});

		selectAll.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent arg0) {
				for (final CheckBox check : map.keySet()) {
					check.setValue(true);
				}
			}
		});

		buttons.add(ok);
		buttons.add(cancel);

		scroll.setWidget(columnList);
		panel.add(instr);
		panel.add(scroll);
		panel.add(selectAll);

		mustSelectOne.setVisible(false);
		panel.add(mustSelectOne);
		panel.add(buttons);
		box.setWidget(panel);
		panel.setCellHorizontalAlignment(buttons, HasHorizontalAlignment.ALIGN_CENTER);
		scroll.addStyleName(Styles.COLUMNPICKER_SCROLLPANEL.getStyle());
		panel.addStyleName(Styles.COLUMNPICKER.getStyle());
		instr.addStyleName(Styles.COLUMNPICKER_INSTRUCTIONS.getStyle());
		mustSelectOne.addStyleName(Styles.COLUMNPICKER_ERRORMESSAGE.getStyle());
		selectAll.addStyleName(Styles.COLUMNPICKER_SELECTALL.getStyle());
		ok.addStyleName(Styles.COLUMNPICKER_OKBUTTON.getStyle());
		cancel.addStyleName(Styles.COLUMNPICKER_CANCELBUTTON.getStyle());
		box.show();

	}
}
