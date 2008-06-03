package org.kuali.student.ruleexecution;

import org.kuali.student.brms.agenda.entity.Agenda;

public interface RuleSetExecutor {

    public Object execute( Agenda agenda, Object ruleSet, Object fact );
}
