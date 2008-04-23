package org.kuali.student.poc.learningunit.lu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
@Entity
@TableGenerator(name = "idGen")
public class Clu {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private String cluId;

	private String cluCategory;

	private String description;

	@ManyToOne
	private LuType luType;

	private String cluShortTitle;

	private String cluCode;

	@ManyToOne
	private Atp atpStart;
	
	@ManyToOne
	private Atp atpEnd;

	private Long learningResultType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clu")
	private Set<LuAttribute> attributes;
	
	/**
	 * @return the cluId
	 */
	public String getCluId() {
		return cluId;
	}

	/**
	 * @param cluId the cluId to set
	 */
	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	/**
	 * @return the cluCategory
	 */
	public String getCluCategory() {
		return cluCategory;
	}

	/**
	 * @param cluCategory the cluCategory to set
	 */
	public void setCluCategory(String cluCategory) {
		this.cluCategory = cluCategory;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the cluShortTitle
	 */
	public String getCluShortTitle() {
		return cluShortTitle;
	}

	/**
	 * @param cluShortTitle the cluShortTitle to set
	 */
	public void setCluShortTitle(String cluShortTitle) {
		this.cluShortTitle = cluShortTitle;
	}

	/**
	 * @return the cluCode
	 */
	public String getCluCode() {
		return cluCode;
	}

	/**
	 * @param cluCode the cluCode to set
	 */
	public void setCluCode(String cluCode) {
		this.cluCode = cluCode;
	}

	/**
	 * @return the atpStart
	 */
	public Atp getAtpStart() {
		return atpStart;
	}

	/**
	 * @param atpStart the atpStart to set
	 */
	public void setAtpStart(Atp atpStart) {
		this.atpStart = atpStart;
	}

	/**
	 * @return the atpEnd
	 */
	public Atp getAtpEnd() {
		return atpEnd;
	}

	/**
	 * @param atpEnd the atpEnd to set
	 */
	public void setAtpEnd(Atp atpEnd) {
		this.atpEnd = atpEnd;
	}

	/**
	 * @return the learningResultType
	 */
	public Long getLearningResultType() {
		return learningResultType;
	}

	/**
	 * @param learningResultType the learningResultType to set
	 */
	public void setLearningResultType(Long learningResultType) {
		this.learningResultType = learningResultType;
	}

	/**
	 * @return the luType
	 */
	public LuType getLuType() {
		return luType;
	}

	/**
	 * @param luType the luType to set
	 */
	public void setLuType(LuType luType) {
		this.luType = luType;
	}

	/**
	 * @return the attributes
	 */
	public Set<LuAttribute> getAttributes() {
		if(attributes==null){
			attributes = new HashSet<LuAttribute>();
		}
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Set<LuAttribute> attributes) {
		this.attributes = attributes;
	}
}
