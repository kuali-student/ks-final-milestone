package org.kuali.student.poc.client.admin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.utilities.Callback;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.ui.personidentity.client.view.Info;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.Widget;

public class AdminTreeMenu extends Composite {
	
	final Tree tree = new Tree();
	
	ViewMetaData viewMetadata = ApplicationContext.getViews().get(AdminPanel.VIEW_NAME);
	Messages messages = viewMetadata.getMessages();
	
	TreeItem editMessages = null;
	TreeItem editViewMetadata = null;
	
	Map<String, TreeItem> localeItems = new HashMap<String, TreeItem>();
	Map<String, TreeItem> groupItems = new HashMap<String, TreeItem>();
	Map<TreeItem, String> groupItemsReverse = new HashMap<TreeItem, String>();
	
	Set<Callback<List<String>>> groupSelectionCallbacks = new HashSet<Callback<List<String>>>();
	
	boolean loaded = false;
	
	public AdminTreeMenu() {
		super.initWidget(tree);
	}
	
	protected void onLoad() {
		if (!loaded) {
			loaded = true;
			tree.setHeight("100%");
			tree.setWidth("100%");
			
			editMessages = tree.addItem(messages.get("editMessages"));
			editViewMetadata = tree.addItem(messages.get("editViewMetadata"));
			tree.addTreeListener(new TreeListener() {
				public void onTreeItemSelected(final TreeItem item) {
                    if (groupItems.values().contains(item)) {
                        String key = groupItemsReverse.get(item);
                        List<String> list = Arrays.asList(key.split(":"));
                        for (Callback<List<String>> callback : groupSelectionCallbacks) {
                            callback.onResult(list);
                        }
                    } else {
                        for (Callback<List<String>> callback : groupSelectionCallbacks) {
                            callback.onResult(null);
                        }
                    }
                    // TODO figure out why items auto-expand after being collapsed
//                    DeferredCommand.addCommand(new Command() {
//                        public void execute() {
//                            if (item.getChildCount() > 0) {
//                                item.setState(!item.getState(), false);
//                            }
//                        }
//                    });
 
                }
	
				public void onTreeItemStateChanged(TreeItem item) {
				}
				
			});
		}
	}
	
	
	public void addMessageGroup(String locale, String groupName) {
		if (!localeItems.containsKey(locale)) {
			localeItems.put(locale, editMessages.addItem(locale));
		}
		TreeItem localeItem = localeItems.get(locale);
		TreeItem groupItem = localeItem.addItem(groupName);
		groupItems.put(locale + ":" + groupName, groupItem);
		groupItemsReverse.put(groupItem, locale + ":" + groupName);
	}
	
	public void addMessageGroupSelectionListener(Callback<List<String>> callback) {
		groupSelectionCallbacks.add(callback);
	}

}
