package edu.umd.ks.poc.lum.web.scat.server.gwt;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;
import edu.umd.ks.poc.lum.web.spring.LumServicesContext;

public class ScatRpcServiceGwt extends RemoteServiceServlet implements ScatRpcService{

    private static final long serialVersionUID = 14089372485672195L;

    ScatRpcService serviceImpl = (ScatRpcService) LumServicesContext.getBean("scatService") ;

    /**
     * @return the serviceImpl
     */
    public ScatRpcService getServiceImpl() {
        return serviceImpl;
    }

    /**
     * @param serviceImpl the serviceImpl to set
     */
    public void setServiceImpl(ScatRpcService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public List<ScatInfo> findScats(String scatTableNum) {
        return serviceImpl.findScats(scatTableNum);
    }

    @Override
    public List<ScatTableInfo> searchScats(String searchString) {
        return serviceImpl.searchScats(searchString);
    }

	@Override
	public int addScatCodesToScatTable(String scatTableId,
			List<ScatInfo> scatInfoList) {
		return serviceImpl.addScatCodesToScatTable(scatTableId, scatInfoList);
	}

	@Override
	public ScatTableInfo createScatTable(ScatTableInfo scatTableInfo) {
		return serviceImpl.createScatTable(scatTableInfo);
	}

	@Override
	public boolean deleteScatTable(String scatTableId) {
		return serviceImpl.deleteScatTable(scatTableId);
	}

	@Override
	public int removeScatCodesFromScatTable(String scatTableId,
			List<String> scatInfoIdList) {
		return serviceImpl.removeScatCodesFromScatTable(scatTableId, scatInfoIdList);
	}

	@Override
	public ScatTableInfo updateScatTable(ScatTableInfo scatTableInfo) {
		return serviceImpl.updateScatTable(scatTableInfo);
	}


}
