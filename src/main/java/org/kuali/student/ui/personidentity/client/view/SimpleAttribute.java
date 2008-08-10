/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Garey
 *
 */
public class SimpleAttribute extends FlexTable {

	public SimpleAttribute(){
		super();
	}
	
	public SimpleAttribute(Map<String, String> map) {
		super();
		setAttributes(map);
	}
	
	public void setAttributes(Map<String, String> map){
		
		Set<String> l = map.keySet();			
		Iterator<String> i = l.iterator();
		int r=0;
		while(i.hasNext()){
			String key = i.next();
			String value = map.get(key);
			this.setWidget(r, 0, new Label(key));
			this.setWidget(r, 1, new Label(value));
			r++;
		}
		
		
	}
	
}
