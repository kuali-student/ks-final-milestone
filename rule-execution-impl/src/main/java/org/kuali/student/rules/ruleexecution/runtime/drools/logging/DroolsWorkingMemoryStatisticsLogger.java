package org.kuali.student.rules.ruleexecution.runtime.drools.logging;

import org.drools.WorkingMemory;
import org.drools.WorkingMemoryEventManager;
import org.drools.audit.WorkingMemoryLogger;
import org.drools.audit.event.LogEvent;
import org.drools.event.AfterActivationFiredEvent;
import org.drools.event.AfterPackageAddedEvent;
import org.drools.event.AfterPackageRemovedEvent;
import org.drools.event.ObjectInsertedEvent;
import org.drools.event.ObjectRetractedEvent;
import org.drools.event.ObjectUpdatedEvent;
import org.drools.spi.Activation;

public class DroolsWorkingMemoryStatisticsLogger extends WorkingMemoryLogger {

	private String ruleBaseType;
	private DroolsExecutionStatistics stats;
	
    public DroolsWorkingMemoryStatisticsLogger(
    		final WorkingMemoryEventManager workingMemoryEventManager,
    		final String ruleBaseType,
    		final DroolsExecutionStatistics stats) {
        super(workingMemoryEventManager);
        this.ruleBaseType = ruleBaseType;
        this.stats = stats;
    }

    @Override
	public void logEventCreated(LogEvent logEvent) {
    	this.stats.getStatistics().addLogEvent();
    }

	@Override
	public void afterActivationFired(AfterActivationFiredEvent event, WorkingMemory workingMemory) {
    	this.stats.getStatistics().addActivationsFiredEvent();
		Activation activation = event.getActivation();
    	String packageName = activation.getRule().getPackage();
    	String ruleName = activation.getRule().getName();
    	this.stats.addRuleActivation(ruleBaseType, packageName, ruleName);
	}

	@Override
	public void afterPackageAdded(AfterPackageAddedEvent event) {
		this.stats.getStatistics().addPackageAddEvent();
	}

	@Override
	public void afterPackageRemoved(AfterPackageRemovedEvent event) {
		this.stats.getStatistics().addPackageRemoveEvent();
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		this.stats.getStatistics().addObjectInsertEvent();
	}

	@Override
	public void objectRetracted(ObjectRetractedEvent event) {
		this.stats.getStatistics().addObjectRetractEvent();
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		this.stats.getStatistics().addObjectUpdatesEvent();
	}

}
