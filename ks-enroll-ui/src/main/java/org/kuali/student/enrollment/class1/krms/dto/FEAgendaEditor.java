package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/08/30
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class FEAgendaEditor extends AgendaEditor {

    private List<RuleEditor> rules;

    public FEAgendaEditor() {
        super();
    }

    public FEAgendaEditor(AgendaDefinition definition) {
        super(definition);
    }

    public List<RuleEditor> getRules() {
        if(this.rules == null){
            this.rules = new ArrayList<RuleEditor>();
        }
        return rules;
    }

    public void setRules(List<RuleEditor> rules) {
        this.rules = rules;
    }

    public boolean isDummyAgenda(){
        if(this.getId()==null){
            if(this.getRules().isEmpty()){
                return true;
            }
        }
        return false;
    }
}
