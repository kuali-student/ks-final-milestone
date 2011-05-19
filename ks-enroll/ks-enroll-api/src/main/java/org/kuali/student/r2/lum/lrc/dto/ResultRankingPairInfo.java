package org.kuali.student.r2.lum.lrc.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.dto.EntityInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultRankingPair;

public class ResultRankingPairInfo extends EntityInfo implements
		ResultRankingPair, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement
	private Integer ranking ;
	@XmlElement
	private String resultId;
	@XmlAttribute
	private String id ;
	
	public ResultRankingPairInfo(){
		super();
		this.ranking = null;
		this.resultId = null;
		this.id = null;
	}
	
	public ResultRankingPairInfo(ResultRankingPair resultRankingPair){
		super(resultRankingPair);
		if(resultRankingPair!=null){
			this.ranking = resultRankingPair.getRanking();
			this.resultId = resultRankingPair.getResultId();
			this.id = resultRankingPair.getId();
			
		}
	
	}
	
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
