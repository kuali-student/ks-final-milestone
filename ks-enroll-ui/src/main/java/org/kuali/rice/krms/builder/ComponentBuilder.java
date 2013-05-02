package org.kuali.rice.krms.builder;

import org.kuali.rice.krms.dto.PropositionEditor;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/03/01
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ComponentBuilder<T extends PropositionEditor> {

    public List<String> getComponentIds();

    public void resolveTermParameters(T propositionEditor, Map<String, String> termParameters);

    public Map<String, String> buildTermParameters(T propositionEditor);

}
