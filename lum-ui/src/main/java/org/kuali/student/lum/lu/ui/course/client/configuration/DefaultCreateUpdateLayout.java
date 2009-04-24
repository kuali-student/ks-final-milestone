package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.LayoutConfigurationException;
import org.kuali.student.common.ui.client.configurable.LayoutSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DefaultCreateUpdateLayout<T extends Object> extends ConfigurableLayout<T> {
	private final HorizontalPanel panel = new HorizontalPanel();
	private final SimplePanel menuPanel = new SimplePanel();
	private final SimplePanel contentPanel = new SimplePanel();
	
	private final Map<String, KSMenuItemData> sectionMap = new HashMap<String, KSMenuItemData>();
	private final List<KSMenuItemData> topLevelMenuItems = new ArrayList<KSMenuItemData>();
	
	public DefaultCreateUpdateLayout() {
		super.initWidget(panel);
		panel.add(menuPanel);
		panel.add(contentPanel);
	}
	

	@Override
	public ConfigurableLayout<T> addSection(final String[] hierarchy,
			final LayoutSection<T> section) {
		String path = "";
		KSMenuItemData current = null;
		for (int i=0; i<hierarchy.length; i++) {
			path = "/" + hierarchy[i];
			KSMenuItemData item = sectionMap.get(path);
			if (item == null) {
				item = new KSMenuItemData(hierarchy[i]);
				if (current == null) {
					topLevelMenuItems.add(item);
				} else {
					current.addSubItem(item);
				}
				sectionMap.put(path, item);
			} else {
				current = item;
			}
		}
		
		/*
		current.setClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showSection(section);
			}
		});
		*/
		
		return this;
	}
	
	protected void showSection(LayoutSection<T> section) {
		contentPanel.clear();
		contentPanel.setWidget(section);
	}

	@Override
	public void render() {
		final KSAccordionMenu sectionMenu = new KSAccordionMenu();
		sectionMenu.setItems(topLevelMenuItems);
		menuPanel.clear();
		menuPanel.add(sectionMenu);
		
		contentPanel.clear();
		
	}




}
