package org.kuali.rice.krms.builder;

import org.kuali.rice.krms.dto.PropositionEditor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/04/02
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTextComponentBuilder implements ComponentBuilder<PropositionEditor>{

    private static final String TEXT_KEY = "kuali.term.parameter.type.free.text";

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(PropositionEditor propositionEditor, Map<String, String> termParameters) {
        String text = termParameters.get(TEXT_KEY);
        propositionEditor.setTermParameter(text);
    }

    @Override
    public Map<String, String> buildTermParameters(PropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        termParameters.put(TEXT_KEY, propositionEditor.getTermParameter());
        return termParameters;
    }
}
