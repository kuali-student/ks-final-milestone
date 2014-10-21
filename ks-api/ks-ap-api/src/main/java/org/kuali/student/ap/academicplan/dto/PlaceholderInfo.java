package org.kuali.student.ap.academicplan.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.ap.academicplan.infc.Placeholder;
import org.w3c.dom.Element;



/**
 * Placeholder message structure
 *
 * @Author mguilla
 * Date: 1/21/14
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlaceholderInfo", propOrder = { "id", "typeKey", "exclusion", "parm1", "parm2", "parm3",  "_futureElements"})
public class PlaceholderInfo implements Placeholder {

	@XmlAttribute
    private String id;
    
	@XmlAttribute
    private String typeKey;
	
    @XmlAttribute
    private boolean exclusion;
    
	@XmlAttribute
    private String parm1;
    
	@XmlAttribute
    private String parm2;
    
	@XmlAttribute
    private String parm3;
	
	@XmlAttribute
    private String parm4;
	
    @XmlAnyElement
    private List<Element> _futureElements;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	@Override
	public String getType() {
		return typeKey;
	}

	@Override
	public boolean isExclusion() {
		return exclusion;
	}

	public void setExclusion(boolean exclusion) {
		this.exclusion = exclusion;
	}

	@Override
	public String getParm1() {
		return parm1;
	}

	public void setParm1(String parm1) {
		this.parm1 = parm1;
	}

	@Override
	public String getParm2() {
		return parm2;
	}

	public void setParm2(String parm2) {
		this.parm2 = parm2;
	}

	@Override
	public String getParm3() {
		return parm3;
	}

	public void setParm3(String parm3) {
		this.parm3 = parm3;
	}

	public String getParm4() {
		return parm4;
	}

	public void setParm4(String parm4) {
		this.parm4 = parm4;
	}

	public List<Element> get_futureElements() {
		return _futureElements;
	}


	public void set_futureElements(List<Element> _futureElements) {
		this._futureElements = _futureElements;
	}


   
}
