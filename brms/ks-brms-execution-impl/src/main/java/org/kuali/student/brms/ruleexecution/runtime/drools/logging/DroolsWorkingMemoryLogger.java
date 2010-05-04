/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.ruleexecution.runtime.drools.logging;

import org.drools.WorkingMemory;
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
import org.drools.event.KnowledgeRuntimeEventManager;
import org.drools.event.ObjectInsertedEvent;
import org.drools.event.ObjectRetractedEvent;
import org.drools.event.ObjectUpdatedEvent;
import org.drools.spi.Activation;
import org.kuali.student.brms.util.LoggingStringBuilder;

public class DroolsWorkingMemoryLogger extends WorkingMemoryLogger {

	private LoggingStringBuilder stringBuilder;

	public DroolsWorkingMemoryLogger() {
		super();
	}
	
    public DroolsWorkingMemoryLogger(final KnowledgeRuntimeEventManager workingMemoryEventManager) {
        super(workingMemoryEventManager);
        this.stringBuilder = new LoggingStringBuilder();
    }

    public DroolsWorkingMemoryLogger(final KnowledgeRuntimeEventManager workingMemoryEventManager, 
    		final LoggingStringBuilder stringBuilder) {
        super(workingMemoryEventManager);
        this.stringBuilder = stringBuilder;
    }

    public void clear() {
    	this.stringBuilder = new LoggingStringBuilder();
    }

    public StringBuilder getLog() {
    	this.stringBuilder.trimToSize();
    	return this.stringBuilder.getStringBuilder();
    }

    @Override
	public void logEventCreated(final LogEvent logEvent) {
    	this.stringBuilder.append("Event Created: " + logEvent.getType());
    }

	@Override
	public void activationCancelled(final ActivationCancelledEvent event,
			final WorkingMemory workingMemory) {
		super.activationCancelled(event, workingMemory);
    	this.stringBuilder.append("Activation Cancelled: " + event);
	}

	@Override
	public void activationCreated(final ActivationCreatedEvent event,
			final WorkingMemory workingMemory) {
		super.activationCreated(event, workingMemory);
    	this.stringBuilder.append("Activation Created: " + event);
	}

	@Override
	public void afterFunctionRemoved(final AfterFunctionRemovedEvent event) {
		super.afterFunctionRemoved(event);
    	this.stringBuilder.append("Function Removed: " + event);
	}

	@Override
	public void afterActivationFired(final AfterActivationFiredEvent event,
			final WorkingMemory workingMemory) {
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
	public void afterPackageAdded(final AfterPackageAddedEvent event) {
		super.afterPackageAdded(event);
		this.stringBuilder.append("Package Added: " + event.getPackage());
	}

	@Override
	public void afterPackageRemoved(final AfterPackageRemovedEvent event) {
		super.afterPackageRemoved(event);
		this.stringBuilder.append("Package Removed: " + event);
	}

	@Override
	public void afterRuleAdded(final AfterRuleAddedEvent event) {
		super.afterRuleAdded(event);
		this.stringBuilder.append("Rule Added: " + event);
	}

	@Override
	public void afterRuleRemoved(final AfterRuleRemovedEvent event) {
		super.afterRuleRemoved(event);
		this.stringBuilder.append("Rule Removed: " + event);
	}

	@Override
	public void objectInserted(final ObjectInsertedEvent event) {
		super.objectInserted(event);
		this.stringBuilder.append("Object Inserted: " + event);
	}

	@Override
	public void objectRetracted(final ObjectRetractedEvent event) {
		super.objectRetracted(event);
		this.stringBuilder.append("Object Retracted: " + event);
	}

	@Override
	public void objectUpdated(final ObjectUpdatedEvent event) {
		super.objectUpdated(event);
		this.stringBuilder.append("Object Updated: " + event);
	}
}
