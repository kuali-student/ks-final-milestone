package org.kuali.student.r2.common.model;

import javax.persistence.Column;
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
	@NamedQuery(name="StateProcess.getInitialValidStates", query="select spr.nextState from StateProcessRelationEntity spr where spr.priorState.id is null and spr.process.id=:processKey"),
	@NamedQuery(name="StateProcess.getNextHappyState", query="select spr.nextState from StateProcessRelationEntity spr where spr.priorState.id=:stateKey and spr.process.id=:processKey")
})
public class StateProcessRelationEntity extends MetaEntity{
	@ManyToOne
    @JoinColumn(name="PROCESS_KEY")
    private StateProcessEntity process;
    
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

	public StateProcessEntity getProcess() {
		return process;
	}

	public void setProcess(StateProcessEntity process) {
		this.process = process;
	}

	public StateEntity getNextState() {
		return nextState;
	}

	public void setNextState(StateEntity nextState) {
		this.nextState = nextState;
	}	
}
