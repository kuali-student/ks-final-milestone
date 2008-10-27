package org.kuali.student.rules.ruleexecution.runtime.drools.logging;

import org.drools.WorkingMemory;
import org.drools.WorkingMemoryEventManager;
import org.drools.audit.WorkingMemoryLogger;
import org.drools.audit.event.LogEvent;
import org.drools.event.ActivationCancelledEvent;
import org.drools.event.ActivationCreatedEvent;
import org.drools.event.AfterActivationFiredEvent;
import org.drools.event.AfterFunctionRemovedEvent;
import org.drools.event.AfterPackageAddedEvent;
import org.drools.event.AfterPackageRemovedEvent;
import org.drools.event.AfterRuleAddedEvent;
import org.drools.event.AfterRuleRemovedEvent;
import org.drools.event.ObjectInsertedEvent;
import org.drools.event.ObjectRetractedEvent;
import org.drools.event.ObjectUpdatedEvent;
import org.drools.spi.Activation;

public class DroolsWorkingMemoryLogger extends WorkingMemoryLogger {

	private LoggingStringBuffer stringBuffer = new LoggingStringBuffer();
	
    public DroolsWorkingMemoryLogger(final WorkingMemoryEventManager workingMemoryEventManager) {
        super(workingMemoryEventManager);
    }

    public void clear() {
    	this.stringBuffer = new LoggingStringBuffer();
    }

    public StringBuffer getLog() {
    	this.stringBuffer.trimToSize();
    	return this.stringBuffer.getStringBuffer();
    }

    @Override
	public void logEventCreated(LogEvent logEvent) {
    	this.stringBuffer.append("Event Created: " + logEvent.getType());
    }

	@Override
	public void activationCancelled(ActivationCancelledEvent event,
			WorkingMemory workingMemory) {
		super.activationCancelled(event, workingMemory);
    	this.stringBuffer.append("Activation Cancelled: " + event);
	}

	@Override
	public void activationCreated(ActivationCreatedEvent event,
			WorkingMemory workingMemory) {
		super.activationCreated(event, workingMemory);
    	this.stringBuffer.append("Activation Created: " + event);
	}

	@Override
	public void afterFunctionRemoved(AfterFunctionRemovedEvent event) {
		super.afterFunctionRemoved(event);
    	this.stringBuffer.append("Function Removed: " + event);
	}

	@Override
	public void afterActivationFired(AfterActivationFiredEvent event,
			WorkingMemory workingMemory) {
		super.afterActivationFired(event, workingMemory);
    	Activation activation = event.getActivation();
    	this.stringBuffer.append("Activation Fired: ");
    	this.stringBuffer.append("\tActivation Number:   " + activation.getActivationNumber());
    	this.stringBuffer.append("\tPropagation Context: " + activation.getPropagationContext().getType());
    	this.stringBuffer.append("\tAgenda Group:        " + activation.getAgendaGroup().getName());
    	this.stringBuffer.append("\tPackage:             " + activation.getRule().getPackage());
    	this.stringBuffer.append("\tRule:                " + activation.getRule().getName());
    	this.stringBuffer.append("\tTuple:               " + activation.getTuple());
    	this.stringBuffer.append("Object source: " + event.getSource());
	}

	@Override
	public void afterPackageAdded(AfterPackageAddedEvent event) {
		super.afterPackageAdded(event);
		this.stringBuffer.append("Package Added: " + event.getPackage());
	}

	@Override
	public void afterPackageRemoved(AfterPackageRemovedEvent event) {
		super.afterPackageRemoved(event);
		this.stringBuffer.append("Package Removed: " + event);
	}

	@Override
	public void afterRuleAdded(AfterRuleAddedEvent event) {
		super.afterRuleAdded(event);
		this.stringBuffer.append("Rule Added: " + event);
	}

	@Override
	public void afterRuleRemoved(AfterRuleRemovedEvent event) {
		super.afterRuleRemoved(event);
		this.stringBuffer.append("Rule Removed: " + event);
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		super.objectInserted(event);
		this.stringBuffer.append("Object Inserted: " + event);
	}

	@Override
	public void objectRetracted(ObjectRetractedEvent event) {
		super.objectRetracted(event);
		this.stringBuffer.append("Object Retracted: " + event);
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		super.objectUpdated(event);
		this.stringBuffer.append("Object Updated: " + event);
	}

	private static class LoggingStringBuffer {
		private int counter = 0;
		private StringBuffer stringBuffer = new StringBuffer();
		
		public StringBuffer append(String s) {
			String f = String.format("%1$6d: %2$s", ++counter, s);
			this.stringBuffer.append(f);
			this.stringBuffer.append("\n");
			return stringBuffer;
		}
		
		public void trimToSize() {
			this.stringBuffer.trimToSize();
		}

		public StringBuffer getStringBuffer() {
			return this.stringBuffer;
		}
		
		public String toString() {
			return this.stringBuffer.toString();
		}
	}
	
}
