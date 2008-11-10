package edu.umd.ks.poc.lum.lu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class Lui {
	@Id
	private String luiId;
	private String luiCode;
	@ManyToOne
	private Clu clu;
	@ManyToOne
	private Atp atp;
	private int maxSeats;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "lui")
	private Set<LuiAttribute> attributes;
	
	private String status;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.luiId = UUIDHelper.genStringUUID(this.luiId);
	}

	/**
	 * @return the luiId
	 */
	public String getLuiId() {
		return luiId;
	}

	/**
	 * @param luiId
	 *            the luiId to set
	 */
	public void setLuiId(String luiId) {
		this.luiId = luiId;
	}

	/**
	 * @return the luiCode
	 */
	public String getLuiCode() {
		return luiCode;
	}

	/**
	 * @param luiCode
	 *            the luiCode to set
	 */
	public void setLuiCode(String luiCode) {
		this.luiCode = luiCode;
	}

	/**
	 * @return the clu
	 */
	public Clu getClu() {
		return clu;
	}

	/**
	 * @param clu
	 *            the clu to set
	 */
	public void setClu(Clu clu) {
		this.clu = clu;
	}

	/**
	 * @return the atp
	 */
	public Atp getAtp() {
		return atp;
	}

	/**
	 * @param atp
	 *            the atp to set
	 */
	public void setAtp(Atp atp) {
		this.atp = atp;
	}

	/**
	 * @return the maxSeats
	 */
	public int getMaxSeats() {
		return maxSeats;
	}

	/**
	 * @param maxSeats
	 *            the maxSeats to set
	 */
	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}

	/**
	 * @return the attributes
	 */
	public Set<LuiAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new HashSet<LuiAttribute>();
		}
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Set<LuiAttribute> attributes) {
		this.attributes = attributes;
	}

}
