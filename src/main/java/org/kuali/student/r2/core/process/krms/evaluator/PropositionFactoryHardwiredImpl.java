package org.kuali.student.r2.core.process.krms.evaluator;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.process.krms.proposition.PersonLivingProposition;

/**
 * Factory for getting the required load calculation rule given a key
 */
public class PropositionFactoryHardwiredImpl implements PropositionFactory {

    public PropositionFactoryHardwiredImpl() {
    }
    // checks
    public static final String AGENDA_ID_IS_ALIVE = "kuali.agenda.is.alive";
    public Proposition getProposition(String propositionId, ContextInfo contextInfo) throws DoesNotExistException,
            OperationFailedException {
        if (propositionId.equals(AGENDA_ID_IS_ALIVE)) {
            PersonLivingProposition prop = new PersonLivingProposition();
            return prop;
        }
        throw new DoesNotExistException("unknown/unsupported proposition " + propositionId);
    }
}
