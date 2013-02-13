package org.kuali.student.myplan.course.dataobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jasonosgood
 * Date: 12/5/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingDetails {
	private String term;

    private List<ActivityOfferingItem> activityOfferingItemList;

    public String getTerm() {
    	return term;
    }
    
    public void setTerm( String term ) {
    	this.term = term;
    }
    
    public List<ActivityOfferingItem> getActivityOfferingItemList() {
        if (activityOfferingItemList == null) {
            activityOfferingItemList = new ArrayList<ActivityOfferingItem>();
        }
        return activityOfferingItemList;
    }

    public void setActivityOfferingItemList(List<ActivityOfferingItem> activityOfferingItemList) {
        this.activityOfferingItemList = activityOfferingItemList;
    }

    public boolean isStandalone() {
    	for( ActivityOfferingItem item : getActivityOfferingItemList() ) {
    		if( !item.isStandalone() ) return false;
    	}
    	return true;
    }
}
