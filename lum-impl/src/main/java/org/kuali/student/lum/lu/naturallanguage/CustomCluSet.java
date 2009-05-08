package org.kuali.student.lum.lu.naturallanguage;

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
	
	public String getCluAsString(int index) {
		return this.cluSet.getClus().get(index).getOfficialIdentifier().getShortName();
	}
	
	public String getCluSetAsString() {
		StringBuilder sb = new StringBuilder();
		for(Clu clu : this.cluSet.getClus()) {
			sb.append(clu.getOfficialIdentifier().getShortName());
			sb.append(", ");
		}
		return (sb.length() == 0 ? "N/A" : sb.toString().substring(0, sb.toString().length() - 2));
	}
}
