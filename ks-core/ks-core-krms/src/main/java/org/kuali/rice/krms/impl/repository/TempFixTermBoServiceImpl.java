package org.kuali.rice.krms.impl.repository;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krad.data.DataObjectService;
import org.kuali.rice.krad.data.PersistenceOption;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.impl.util.KrmsImplConstants;

/**
 * This class is a temp fix to resolve the issue https://jira.kuali.org/browse/KSENROLL-12746
 * It was coded to use the constant TERM_ID("termId") instead of "term.id"
 */
public class TempFixTermBoServiceImpl extends TermBoServiceImpl {

    protected DataObjectService dataObjectService;

    @Override
    public void setDataObjectService(DataObjectService dataObjectService) {
        this.dataObjectService = dataObjectService;
        super.setDataObjectService(dataObjectService);
    }

    @Override
    public void updateTerm(TermDefinition term) throws RiceIllegalArgumentException {
        if (term == null) {
            throw new IllegalArgumentException("term is null");
        }

        // must already exist to be able to update
        final String termId = term.getId();
        final TermBo existing = dataObjectService.find(TermBo.class, termId);

        if (existing == null) {
            throw new IllegalStateException("the term resolver does not exist: " + term);
        }

        final TermDefinition toUpdate;

        if (!existing.getId().equals(term.getId())) {
            // if passed in id does not match existing id, correct it
            final TermDefinition.Builder builder = TermDefinition.Builder.create(term);
            builder.setId(existing.getId());
            toUpdate = builder.build();
        } else {
            toUpdate = term;
        }

        // copy all updateable fields to bo
        TermBo boToUpdate = TermBo.from(toUpdate);

        // delete any old, existing parameters

//        QueryByCriteria crit =
//                QueryByCriteria.Builder.forAttribute("term.id", toUpdate.getId()).build();
//        dataObjectService.deleteMatching(TermParameterBo.class, crit);

        // update the rule and create new attributes
        dataObjectService.save(boToUpdate, PersistenceOption.FLUSH);
    }
}
