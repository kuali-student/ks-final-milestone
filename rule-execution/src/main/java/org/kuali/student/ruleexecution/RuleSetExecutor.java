package org.kuali.student.ruleexecution;

import org.kuali.student.brms.agenda.entity.Agenda;
import org.kuali.student.brms.agenda.entity.AgendaType;
import org.kuali.student.brms.agenda.entity.Anchor;

public interface RuleSetExecutor {

    public Object execute( Agenda agenda, Object fact );
}
