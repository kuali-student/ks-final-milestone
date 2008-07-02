/**
 * 
 */
package org.kuali.student.commons.ui.propertychangesupport;

/**
 * Base class providing property change support implementation.
 */
public class BasePropertyChange {

    protected transient PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public PropertyChangeSupport getPropertyChangeSupport() {
        return changes;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        changes.addPropertyChangeListener(propertyName, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) {
        changes.removePropertyChangeListener(propertyName, l);
    }

    public void clearPropertyChangeListeners() {
        PropertyChangeListener[] listeners = changes.getPropertyChangeListeners();
        for (int i = 0; (listeners != null) && (i < listeners.length); i++) {
            this.removePropertyChangeListener(listeners[i]);
        }
    }

}
