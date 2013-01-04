package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.repository.PropositionParameterBo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropositionEditor extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;

    private PropositionBo proposition;
    private String termSpecId;
    private String termParameter;
    private String type;
    private List<TermParameter> termParameterList = new ArrayList<TermParameter>();

    public PropositionEditor() {
        super();
        proposition = new PropositionBo();
    }

    public PropositionEditor(PropositionBo proposition) {
        super();
        this.proposition = proposition;
    }

    public PropositionBo getProposition() {
        return proposition;
    }

    public void setProposition(PropositionBo proposition) {
        this.proposition = proposition;
    }

    public String getTermSpecId() {
        return termSpecId;
    }

    public void setTermSpecId(String componentId) {
        this.termSpecId = componentId;
    }

    public String getTermParameter() {
        return termParameter;
    }

    public void setTermParameter(String termParameter) {
        this.termParameter = termParameter;
    }

    public List<TermParameter> getTermParameterList() {
        return termParameterList;
    }

    public void setTermParameterList(List<TermParameter> termParameterList) {
        this.termParameterList = termParameterList;
    }

    public String getId(){
        return proposition.getId();
    }

    public String getDescription(){
        return proposition.getDescription();
    }

    public String getCompoundOpCode(){
        return proposition.getCompoundOpCode();
    }

    public List<PropositionParameterBo> getParameters(){
        return proposition.getParameters();
    }

    public String getTypeId(){
        return proposition.getTypeId();
    }

    public void setTypeId(String typeId){
        proposition.setTypeId(typeId);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
