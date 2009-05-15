package org.kuali.student.lum.lu.naturallanguage.contexts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.naturallanguage.util.CustomCluSet;

public abstract class AbstractContext implements Context {
    private LuDao luDao;

    /**
     * Sets the LU DAO.
     * 
     * @param luDao LU DAO
     */
    public void setLuDao(LuDao luDao) {
        this.luDao = luDao;
    }

    /**
     * Gets the CLU set.
     * 
     * @param cluSetId
     *            CLU set id
     * @return CLU set
     * @throws DoesNotExistException If CLU set does not exist
     */
    public CustomCluSet getCluSet(String cluSetId) throws DoesNotExistException {
        CluSet cluSet = this.luDao.fetch(CluSet.class, cluSetId);
        return new CustomCluSet(cluSet);
    }

    /**
     * Gets a new CLU set from comma separated list of CLU ids.
     * 
     * @param cluIds Comma separated list of CLU ids
     * @return A new CLU set
     * @throws DoesNotExistException If CLU does not exist
     */
    public CustomCluSet getClusAsCluSet(String cluIds) throws DoesNotExistException {
    	String[] cluIdArray = cluIds.split("\\s*,\\s*");
    	CluSet cluSet = new CluSet();
    	List<Clu> list = new ArrayList<Clu>();
    	for(String cluId : cluIdArray) {
    		Clu clu = this.luDao.fetch(Clu.class, cluId.trim());
    		list.add(clu);
    	}
    	cluSet.setClus(list);
        return new CustomCluSet(cluSet);
    }

    /**
     * Gets requirement component fields as a map.
     * 
     * @param reqComponent Requirement Component
     * @return Map of requirement component fields
     */
    public Map<String, String> getReqCompField(ReqComponent reqComponent) {
        List<ReqComponentField> fields = reqComponent.getReqCompField();
        Map<String, String> map = new HashMap<String, String>();
        for (ReqComponentField field : fields) {
            String key = field.getKey();
            String value = field.getValue();
            map.put(key, value);
        }
        return map;
    }

	public abstract Map<String, Object> createContextMap(ReqComponent reqComponent) throws DoesNotExistException;
}
