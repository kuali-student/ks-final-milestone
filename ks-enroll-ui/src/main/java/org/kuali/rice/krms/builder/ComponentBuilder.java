package org.kuali.rice.krms.builder;

import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/03/01
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ComponentBuilder {

    public List<String> getComponentIds();

    public void resolveTermParameters(PropositionEditor propositionEditor, Map<String, String> termParameters);

    public Map<String, String> buildTermParameters(PropositionEditor propositionEditor);

}
