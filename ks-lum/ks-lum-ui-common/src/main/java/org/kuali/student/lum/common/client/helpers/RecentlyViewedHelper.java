package org.kuali.student.lum.common.client.helpers;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.history.HistoryManager;

public class RecentlyViewedHelper{
	
	public static List<RecentDocInfo> recentlyViewedDocs = new ArrayList<RecentDocInfo>();
	public static List<HasRecentlyViewedData> dependants = new ArrayList<HasRecentlyViewedData>();
	public static final int MAX_RECENT_HISTORY = 8;
	
	public static void addCurrentDocument(String name){
		RecentDocInfo info = new RecentDocInfo(name, HistoryManager.collectHistoryStack());
		for(int i=0; i < recentlyViewedDocs.size(); i++){
			if(info.getLocation().equals(recentlyViewedDocs.get(i).getLocation())){
				recentlyViewedDocs.remove(i);
				break;
			}
		}
		recentlyViewedDocs.add(0, info);
		if(recentlyViewedDocs.size() > MAX_RECENT_HISTORY){
			recentlyViewedDocs.remove(MAX_RECENT_HISTORY + 1);
		}
		for(int i =0; i < dependants.size(); i++){
			dependants.get(i).update();
		}
	}
	
	public static void addDocument(String name, String specificLocation){
		RecentDocInfo info = new RecentDocInfo(name, specificLocation);
		for(int i=0; i < recentlyViewedDocs.size(); i++){
			if(info.getLocation().equals(recentlyViewedDocs.get(i).getLocation())){
				recentlyViewedDocs.remove(i);
				break;
			}
		}
		recentlyViewedDocs.add(0, info);
		if(recentlyViewedDocs.size() > MAX_RECENT_HISTORY){
			recentlyViewedDocs.remove(MAX_RECENT_HISTORY + 1);
		}
		for(int i =0; i < dependants.size(); i++){
			dependants.get(i).update();
		}
	}
	
	public static List<RecentDocInfo> getRecentlyViewed(){
		return recentlyViewedDocs;
	}
	
	public static void addDependant(HasRecentlyViewedData object){
		dependants.add(object);
	}

	public static void updateTitle(String oldTitle, String newTitle, String docId) {
		for(int i=0; i < recentlyViewedDocs.size(); i++){
			if(oldTitle.equals(recentlyViewedDocs.get(i).getName()) 
					&& recentlyViewedDocs.get(i).getLocation().contains(docId)){
				recentlyViewedDocs.get(i).setName(newTitle);
				break;
			}
		}
		for(int i =0; i < dependants.size(); i++){
			dependants.get(i).update();
		}
	}

}
