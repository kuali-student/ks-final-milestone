package edu.umd.ks.poc.util;

import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.rule.xmlrouting.StandardGenericXMLRuleAttribute;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.scat.service.ScatService;

public class ScatServiceRuleAttributeImpl extends
		StandardGenericXMLRuleAttribute {
	private static final long serialVersionUID = 1L;

	@Override
	public Element getConfigXML() {
		ScatService scatService = (ScatService) GlobalResourceLoader.getService(new QName("http://edu.umd.ks/poc/lum/lu","ScatService"));
		List<ScatTableInfo> scatTables = scatService.searchScats("DEPARTMENTS");
		List<ScatInfo> scats = scatService.findScats(scatTables.get(0).getTableId());
		
		Element root = getAttributeConfigXML();
		NodeList displayNodes = root.getElementsByTagName("display");
		Node displayNode = displayNodes.item(0);
		for(ScatInfo scat:scats){
			Element values = root.getOwnerDocument().createElement("values");
			values.setAttribute("title", scat.getShortDesc());
			Text text = root.getOwnerDocument().createTextNode(scat.getCode());
			values.appendChild(text);
			displayNode.appendChild(values);
		}
		return root;
	}

    public Element getAttributeConfigXML() {
        return super.getConfigXML();
    }
    
 

}
