package edu.umd.ks.poc.lum.scat.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;

import edu.umd.ks.poc.lum.scat.dao.ScatDao;
import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.scat.entity.Scat;
import edu.umd.ks.poc.lum.scat.entity.ScatTable;
import edu.umd.ks.poc.lum.scat.service.ScatService;

@WebService(endpointInterface = "edu.umd.ks.poc.lum.scat.service.ScatService", serviceName = "ScatService", portName = "ScatService", targetNamespace = "http://edu.umd.ks/poc/lum/scat")
@Transactional
public class ScatServiceImpl implements ScatService {

	private ScatDao dao;
	private ScatService externalScatService;

	@Override
	public List<ScatInfo> findScats(String scatTableNum) {
		List<ScatInfo> results = toScatInfoList(dao.fetchScatTable(scatTableNum).getScats());
		if (results == null && externalScatService != null) {
			results = externalScatService.findScats(scatTableNum);
		}
		return results;
	}



	@Override
	public List<ScatTableInfo> searchScats(String searchString) {
		List<ScatTableInfo> results = toScatTableInfoList(dao.searchScats(searchString));
		if (results == null && externalScatService != null) {
			results = externalScatService.searchScats(searchString);
		}
		return results;
	}



	@Override
	public int addScatCodesToScatTable(String scatTableId,
			List<ScatInfo> scatInfoList) {
		ScatTable scatTable=dao.fetchScatTable(scatTableId);
		for(ScatInfo scatInfo: scatInfoList){
			Scat scat = toScat(scatInfo);
			scat.setTable(scatTable);
			dao.createScat(scat);
			//scatTable.getScats().add(scat);
		}
		//dao.updateScatTable(scatTable);
		return 1;
	}


	@Override
	public ScatTableInfo createScatTable(ScatTableInfo scatTableInfo) {
		ScatTable scatTable = new ScatTable();
		scatTable.setTableDescription(scatTableInfo.getTableDescription());
		scatTable = dao.createScatTable(scatTable);
		scatTableInfo.setTableId(scatTable.getTableId());
		return scatTableInfo;
	}

	@Override
	public boolean deleteScatTable(String scatTableId) {
		dao.deleteScatTable(scatTableId);
		return true;
	}

	@Override
	public int removeScatCodesFromScatTable(String scatTableId,
			List<String> scatInfoIdList) {
		for(String scatId:scatInfoIdList){
			Scat scat = dao.fetchScat(scatId);
			dao.deleteScat(scat);
		}
		return 1;
	}

	@Override
	public ScatTableInfo updateScatTable(ScatTableInfo scatTableInfo) {
		ScatTable scatTable = new ScatTable();
		scatTable.setTableDescription(scatTableInfo.getTableDescription());
		scatTable = dao.updateScatTable(scatTable);
		scatTableInfo.setTableId(scatTable.getTableId());
		return scatTableInfo;
	}

	
	private List<ScatTableInfo> toScatTableInfoList(List<ScatTable> scatTables) {
		List<ScatTableInfo> scatTableInfoList = new ArrayList<ScatTableInfo>();
		for(ScatTable scatTable:scatTables){
			scatTableInfoList.add(toScatTableInfo(scatTable));
		}
		return scatTableInfoList;
	}
	private ScatTableInfo toScatTableInfo(ScatTable scatTable) {
		ScatTableInfo scatTableInfo = new ScatTableInfo();
		scatTableInfo.setTableId(scatTable.getTableId());
		scatTableInfo.setTableDescription(scatTable.getTableDescription());
		return scatTableInfo;
	}

	private List<ScatInfo> toScatInfoList(List<Scat> Scats) {
		List<ScatInfo> scatInfoList = new ArrayList<ScatInfo>();
		for(Scat scat:Scats){
			scatInfoList.add(toScatInfo(scat));
		}
		return scatInfoList;
	}
	
	private ScatInfo toScatInfo(Scat scat) {
		ScatInfo scatInfo = new ScatInfo();
		scatInfo.setId(scat.getId());
		scatInfo.setCode(scat.getCode());
		scatInfo.setLongDesc(scat.getLongDesc());
		scatInfo.setShortDesc(scat.getShortDesc());
		return scatInfo;
	}

	private Scat toScat(ScatInfo scatInfo) {
		Scat scat = new Scat();
		scat.setId(scatInfo.getId());
		scat.setCode(scatInfo.getCode());
		scat.setLongDesc(scatInfo.getLongDesc());
		scat.setShortDesc(scatInfo.getShortDesc());
		return scat;
	}
	
	/**
	 * @return the dao
	 */
	public ScatDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ScatDao dao) {
		this.dao = dao;
	}
	
	/**
	 * @return the externalScatService
	 */
	public ScatService getExternalScatService() {
		return externalScatService;
	}

	/**
	 * @param externalScatService the externalScatService to set
	 */
	public void setExternalScatService(ScatService externalScatService) {
		this.externalScatService = externalScatService;
	}
}
