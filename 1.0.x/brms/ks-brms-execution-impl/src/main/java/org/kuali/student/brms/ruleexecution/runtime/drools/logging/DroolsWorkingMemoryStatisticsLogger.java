package org.kuali.student.brms.ruleexecution.runtime.drools.logging;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.drools.WorkingMemory;
import org.drools.audit.WorkingMemoryLogger;
import org.drools.audit.event.LogEvent;
import org.drools.event.AfterActivationFiredEvent;
import org.drools.event.AfterPackageAddedEvent;
import org.drools.event.AfterPackageRemovedEvent;
import org.drools.event.BeforeActivationFiredEvent;
import org.drools.event.KnowledgeRuntimeEventManager;
import org.drools.event.ObjectInsertedEvent;
import org.drools.event.ObjectRetractedEvent;
import org.drools.event.ObjectUpdatedEvent;
import org.drools.spi.Activation;

public class DroolsWorkingMemoryStatisticsLogger extends WorkingMemoryLogger {

	private String ruleBaseType;
	private DroolsExecutionStatistics stats;
	private final ConcurrentMap<String, Long> executionTimeMap = new ConcurrentHashMap<String, Long>();
	
	public DroolsWorkingMemoryStatisticsLogger() {
		super();
	}

	public DroolsWorkingMemoryStatisticsLogger(
    		final KnowledgeRuntimeEventManager workingMemoryEventManager,
    		final String ruleBaseType,
    		final DroolsExecutionStatistics stats) {
        super(workingMemoryEventManager);
        this.ruleBaseType = ruleBaseType;
        this.stats = stats;
    }

    private String getId(Activation activation) {
    	return activation.getRule().getPackage() + "." + activation.getRule().getName();
    }
    
    @Override
	public void logEventCreated(final LogEvent logEvent) {
    	this.stats.getStatistics().addLogEvent();
    }

	@Override
    public void beforeActivationFired(final BeforeActivationFiredEvent event, final WorkingMemory workingMemory) {
    	long time = System.nanoTime();
    	this.executionTimeMap.put(getId(event.getActivation()), Long.valueOf(time));
    }

	@Override
	public void afterActivationFired(final AfterActivationFiredEvent event, final WorkingMemory workingMemory) {
    	long time = System.nanoTime();
		this.stats.getStatistics().addActivationsFiredEvent();
		Activation activation = event.getActivation();
    	String packageName = activation.getRule().getPackage();
    	String ruleName = activation.getRule().getName();
    	long executionTime = time - executionTimeMap.get(getId(activation)).longValue();
    	this.stats.addRuleActivation(ruleBaseType, packageName, ruleName, executionTime);
	}

	@Override
	public void afterPackageAdded(final AfterPackageAddedEvent event) {
		this.stats.getStatistics().addPackageAddEvent();
	}

	@Override
	public void afterPackageRemoved(final AfterPackageRemovedEvent event) {
		this.stats.getStatistics().addPackageRemoveEvent();
	}

	@Override
	public void objectInserted(final ObjectInsertedEvent event) {
		this.stats.getStatistics().addObjectInsertEvent();
	}

	@Override
	public void objectRetracted(final ObjectRetractedEvent event) {
		this.stats.getStatistics().addObjectRetractEvent();
	}

	@Override
	public void objectUpdated(final ObjectUpdatedEvent event) {
		this.stats.getStatistics().addObjectUpdatesEvent();
	}

}
