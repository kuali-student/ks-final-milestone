package org.kuali.student.lum.nlt.naturallanguage.util;

import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluSet;

public class CustomCluSet {

	private CluSet cluSet;
	
	public CustomCluSet(CluSet cluSet) {
		this.cluSet = cluSet;
	}

	public CluSet getCluSet() {
		return this.cluSet;
	}
	
	public String getCluAsShortName(int index) {
		return this.cluSet.getClus().get(index).getOfficialIdentifier().getShortName();
	}
	
	public String getCluAsCode(int index) {
		return this.cluSet.getClus().get(index).getOfficialIdentifier().getCode();
	}
	
	public String getCluSetAsShortName() {
		StringBuilder sb = new StringBuilder();
		for(Clu clu : this.cluSet.getClus()) {
			sb.append(clu.getOfficialIdentifier().getShortName());
			sb.append(", ");
		}
		return getString(sb);
	}

	public String getCluSetAsLongName() {
		StringBuilder sb = new StringBuilder();
		for(Clu clu : this.cluSet.getClus()) {
			sb.append(clu.getOfficialIdentifier().getShortName());
			sb.append(", ");
		}
		return getString(sb);
	}

	public String getCluSetAsCode() {
		StringBuilder sb = new StringBuilder();
		for(Clu clu : this.cluSet.getClus()) {
			sb.append(clu.getOfficialIdentifier().getCode());
			sb.append(", ");
		}
		return getString(sb);
	}
	
	private String getString(StringBuilder sb) {
		return (sb.length() == 0 ? "No Clus available in CluSet" : sb.toString().substring(0, sb.toString().length() - 2));
	}
	
	public String toString() {
		if(this.cluSet == null) {
			return "Null CluSet";
		}
		return "id=" + this.cluSet.getId();
	}
}
