package edu.umd.ks.poc.lum.web.scat.server.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.scat.service.ScatService;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;

public class ScatRpcServiceImpl implements ScatRpcService {

    private ScatService service = null;

    /**
     * @param service the service to set
     */
    public void setService(ScatService service) {
        this.service = service;
    }

    @Override
    public List<ScatInfo> findScats(String scatTableNum) {
        return service.findScats(scatTableNum);
    }

    @Override
    public List<ScatTableInfo> searchScats(String searchString) {
        return service.searchScats(searchString);
    }

	@Override
	public int addScatCodesToScatTable(String scatTableId,
			List<ScatInfo> scatInfoList) {
		return service.addScatCodesToScatTable(scatTableId, scatInfoList);
	}

	@Override
	public ScatTableInfo createScatTable(ScatTableInfo scatTableInfo) {
		return service.createScatTable(scatTableInfo);
	}

	@Override
	public boolean deleteScatTable(String scatTableId) {
		return service.deleteScatTable(scatTableId);
	}

	@Override
	public int removeScatCodesFromScatTable(String scatTableId,
			List<String> scatInfoIdList) {
		return service.removeScatCodesFromScatTable(scatTableId, scatInfoIdList);
	}

	@Override
	public ScatTableInfo updateScatTable(ScatTableInfo scatTableInfo) {
		return service.updateScatTable(scatTableInfo);
	}



}
