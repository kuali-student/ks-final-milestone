package org.kuali.student.lum.lu.assembly;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.Data;
import org.kuali.student.common.assembly.Metadata;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.CluInfoData;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

public class CluInfoAssembler implements Assembler {
	private LuService luService;
	private AtpService atpService;

	@Override
	public void chain(Assembler assembler) {
		// TODO Auto-generated method stub

	}

	@Override
	public Data createNew(String type, String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data get(String id) {
		return get(id, new HashSet<String>());
	}
	public Data get(String id, Set<String> fetched) {
		CluInfoData result = null;
		// TODO Auto-generated method stub
		try {

			CluInfo cluInfo = luService.getClu(id);
			result = copyFromCluInfo(cluInfo);
			List<CluCluRelationInfo> relations = luService
					.getCluCluRelationsByClu(id);
			;
			result.set(CluInfoData.CHILD_CLUS, new Data());
			for (CluCluRelationInfo relation : relations) {
				String formatId = relation.getCluId();
				if (!fetched.contains(formatId)) {
					fetched.add(formatId);
					result.getChildren().add(get(formatId, fetched));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private CluInfoData copyFromCluInfo(CluInfo clu) {
		CluInfoData result = new CluInfoData();
		if (null != result) {
			result.set("id", clu.getId());
			result.set("state", clu.getState());
			result.set("type", clu.getType());
			result.set("effectiveDate", clu.getEffectiveDate());
			result.set("expirationDate", clu.getExpirationDate());
			Data campusLocations = new Data();
			result.set("campusLocations", campusLocations);
			for (String s : clu.getCampusLocationList()) {
				campusLocations.add(s);
			}
			if(null!=clu.getMarketingDesc()){
				result.set("marketingDesc", convertRichTextInfo(clu
					.getMarketingDesc()));
			}
			if(null!=clu.getDesc()){
				result.set("desc", convertRichTextInfo(clu.getDesc()));
			}
			return result;
		}
		return null;
	}

	private Data convertRichTextInfo(RichTextInfo rti) {
		if (null != rti) {
			Data result = new Data(RichTextInfo.class.getName());
			result.set("formatted", rti.getFormatted());
			result.set("plain", rti.getPlain());
			return result;
		}
		return null;
	}

	@Override
	public Metadata getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metadata getMetadata(String type, String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Data, List<ValidationResultInfo>> save(Data data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data transform(Data data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validate(Data data) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}
}
