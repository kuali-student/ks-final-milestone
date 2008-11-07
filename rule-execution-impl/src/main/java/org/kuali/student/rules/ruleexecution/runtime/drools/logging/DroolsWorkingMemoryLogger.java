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
import org.kuali.student.rules.ruleexecution.util.LoggingStringBuilder;

public class DroolsWorkingMemoryLogger extends WorkingMemoryLogger {

	private LoggingStringBuilder stringBuilder;
	
    public DroolsWorkingMemoryLogger(final WorkingMemoryEventManager workingMemoryEventManager) {
        super(workingMemoryEventManager);
        this.stringBuilder = new LoggingStringBuilder();
    }

    public DroolsWorkingMemoryLogger(final WorkingMemoryEventManager workingMemoryEventManager, 
    		final LoggingStringBuilder stringBuilder) {
        super(workingMemoryEventManager);
        this.stringBuilder = stringBuilder;
    }

    public void clear() {
    	this.stringBuilder = new LoggingStringBuilder();
    }

    public StringBuilder getLog() {
    	this.stringBuilder.trimToSize();
    	return this.stringBuilder.getStringBuffer();
    }

    @Override
	public void logEventCreated(LogEvent logEvent) {
    	this.stringBuilder.append("Event Created: " + logEvent.getType());
    }

	@Override
	public void activationCancelled(ActivationCancelledEvent event,
			WorkingMemory workingMemory) {
		super.activationCancelled(event, workingMemory);
    	this.stringBuilder.append("Activation Cancelled: " + event);
	}

	@Override
	public void activationCreated(ActivationCreatedEvent event,
			WorkingMemory workingMemory) {
		super.activationCreated(event, workingMemory);
    	this.stringBuilder.append("Activation Created: " + event);
	}

	@Override
	public void afterFunctionRemoved(AfterFunctionRemovedEvent event) {
		super.afterFunctionRemoved(event);
    	this.stringBuilder.append("Function Removed: " + event);
	}

	@Override
	public void afterActivationFired(AfterActivationFiredEvent event,
			WorkingMemory workingMemory) {
		super.afterActivationFired(event, workingMemory);
    	Activation activation = event.getActivation();
    	this.stringBuilder.append("Activation Fired: ");
    	this.stringBuilder.append("\tActivation Number:   " + activation.getActivationNumber());
    	this.stringBuilder.append("\tPropagation Context: " + activation.getPropagationContext().getType());
    	this.stringBuilder.append("\tAgenda Group:        " + activation.getAgendaGroup().getName());
    	this.stringBuilder.append("\tPackage:             " + activation.getRule().getPackage());
    	this.stringBuilder.append("\tRule:                " + activation.getRule().getName());
    	this.stringBuilder.append("\tTuple:               " + activation.getTuple());
    	this.stringBuilder.append("Object source: " + event.getSource());
	}

	@Override
	public void afterPackageAdded(AfterPackageAddedEvent event) {
		super.afterPackageAdded(event);
		this.stringBuilder.append("Package Added: " + event.getPackage());
	}

	@Override
	public void afterPackageRemoved(AfterPackageRemovedEvent event) {
		super.afterPackageRemoved(event);
		this.stringBuilder.append("Package Removed: " + event);
	}

	@Override
	public void afterRuleAdded(AfterRuleAddedEvent event) {
		super.afterRuleAdded(event);
		this.stringBuilder.append("Rule Added: " + event);
	}

	@Override
	public void afterRuleRemoved(AfterRuleRemovedEvent event) {
		super.afterRuleRemoved(event);
		this.stringBuilder.append("Rule Removed: " + event);
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		super.objectInserted(event);
		this.stringBuilder.append("Object Inserted: " + event);
	}

	@Override
	public void objectRetracted(ObjectRetractedEvent event) {
		super.objectRetracted(event);
		this.stringBuilder.append("Object Retracted: " + event);
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		super.objectUpdated(event);
		this.stringBuilder.append("Object Updated: " + event);
	}
}
