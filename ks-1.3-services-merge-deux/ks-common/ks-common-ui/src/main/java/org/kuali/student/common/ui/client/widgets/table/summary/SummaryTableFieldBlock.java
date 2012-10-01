package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;


public class SummaryTableFieldBlock extends SummaryTableBlock{
	
	private List<SummaryTableMultiplicityFieldRow> multiplicityList = new ArrayList<SummaryTableMultiplicityFieldRow>();
	
    public SummaryTableFieldBlock(){
        
    }
    
    public void addSummaryTableFieldRow(SummaryTableFieldRow aRow){
        super.add(aRow);
    }
    
    public void addSummaryMultiplicity(MultiplicityConfiguration config){
    	SummaryTableMultiplicityFieldRow aRow = new SummaryTableMultiplicityFieldRow(config);
    	multiplicityList.add(aRow);
    	super.add(aRow);
    }
    
	public List<SummaryTableMultiplicityFieldRow> getMultiplicityList() {
		return multiplicityList;
	}

	public void setMultiplicityList(List<SummaryTableMultiplicityFieldRow> multiplicityList) {
		this.multiplicityList = multiplicityList;
	}
}
