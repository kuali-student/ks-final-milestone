package org.kuali.student.core.ui.propertychangesupport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import junit.framework.TestCase;

import org.junit.Test;

public class BasicPropertyChangeSupportTest extends TestCase {
	
	@Test
	public void testBasicPropertyChangeSupport() {
		String firstName = "Lloyd";
		String lastName = "Christmas";
		
		MyModelObject source = new MyModelObject();
		MyChangeListener listener = new MyChangeListener();
		
		source.addPropertyChangeListener(listener);
		source.setFirstName(firstName);
		assertEquals("firstName event did not fire", firstName, listener.getFirstName());
		
		source.clearPropertyChangeListeners();
		source.addPropertyChangeListener("lastName", listener);
		source.setLastName(lastName);
		assertEquals("lastName event did not fire", lastName, listener.getLastName());
		
		
	}
	
	private static class MyModelObject {
		PropertyChangeSupport changes = new PropertyChangeSupport(
				this);

		String firstName = null;
		String lastName = null;
		
		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			changes.firePropertyChange("firstName", this.firstName, firstName);
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			changes.firePropertyChange("lastName", this.lastName, lastName);
			this.lastName = lastName;
		}
		
		public void addPropertyChangeListener(PropertyChangeListener l) {
			changes.addPropertyChangeListener(l);
		}

		public void addPropertyChangeListener(String propertyName,
				PropertyChangeListener l) {
			changes.addPropertyChangeListener(propertyName, l);
		}
		
		public void removePropertyChangeListener(PropertyChangeListener l) {
			changes.removePropertyChangeListener(l);
		}

		public void removePropertyChangeListener(String propertyName,
				PropertyChangeListener l) {
			changes.removePropertyChangeListener(propertyName, l);
		}
		
		public void clearPropertyChangeListeners(){
	        PropertyChangeListener[] listeners = changes.getPropertyChangeListeners();
	        for( int i=0; listeners != null && i < listeners.length; i++ ){
	            this.removePropertyChangeListener( listeners[i] );
	        }
	    }

	}
	
	private static class MyChangeListener implements PropertyChangeListener {
		String firstName = null;
		String lastName = null;
		
		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("firstName")) {
				firstName = (String) evt.getNewValue();
			} else if (evt.getPropertyName().equals("lastName")) {
				lastName = (String) evt.getNewValue();
			}  
			
		}

		
		
	}
}
