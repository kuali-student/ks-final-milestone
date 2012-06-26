package org.kuali.student.r2.common.class1.state.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.MetaEntity;


@Entity
@Table(name = "KSEN_STATEPROCESS_RELTN")
@NamedQueries({
	@NamedQuery(name="StateProcess.getInitialValidStates", query="select spr.nextState from StateLifecycleRelationEntity spr where spr.priorState.id is null and spr.process.id=:processKey"),
	@NamedQuery(name="StateProcess.getNextHappyState", query="select spr.nextState from StateLifecycleRelationEntity spr where spr.priorState.id=:stateKey and spr.process.id=:processKey")
})
public class StateLifecycleRelationEntity extends MetaEntity {
	@ManyToOne
    @JoinColumn(name="PROCESS_KEY")
    private LifecycleEntity process;
    
	@ManyToOne
    @JoinColumn(name="PRIOR_STATEKEY")
    private StateEntity priorState;

	@ManyToOne
    @JoinColumn(name="NEXT_STATEKEY")
    private StateEntity nextState;

	public StateEntity getPriorState() {
		return priorState;
	}

	public void setPriorState(StateEntity priorState) {
		this.priorState = priorState;
	}

	public LifecycleEntity getProcess() {
		return process;
	}

	public void setProcess(LifecycleEntity process) {
		this.process = process;
	}

	public StateEntity getNextState() {
		return nextState;
	}

	public void setNextState(StateEntity nextState) {
		this.nextState = nextState;
	}	
}
