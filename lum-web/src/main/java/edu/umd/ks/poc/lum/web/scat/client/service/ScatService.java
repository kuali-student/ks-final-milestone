package edu.umd.ks.poc.lum.web.scat.client.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;

public interface ScatService {
	public List<ScatInfo> findScats(String scatTableNum);

	public List<ScatTableInfo> searchScats(String searchString);

	public ScatTableInfo createScatTable(ScatTableInfo scatTableInfo);

	public ScatTableInfo updateScatTable(ScatTableInfo scatTableInfo);

	public boolean deleteScatTable(String scatTableId);

	public int addScatCodesToScatTable(String scatTableId,
			List<ScatInfo> scatInfoList);

	public int removeScatCodesFromScatTable(String scatTableId,
			List<String> scatInfoIdList);


}
