package org.kuali.student.lum.lu.assembly;

import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.ws.beans.JaxWsClientFactory;
import org.kuali.student.common.ws.beans.JaxWsClientFactoryBean;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lu.assembly.CluInfoHierarchyAssembler.RelationshipHierarchy;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

public class AssemblerTestMain {

	public static void main(String[] args) throws Exception {
		LuService luService = aquireLuService();
//		AtpService atpService = aquireAtpService();
		ProposalService proposalService = aquireProposalService();
		
//		List<AtpDurationTypeInfo> atpTypes = atpService.getAtpDurationTypes();
		List<ProposalTypeInfo> proposalTypes = proposalService.getProposalTypes();
		
		// Create some cluInfo
		CluInfo clu = new CluInfo();
		clu.setType("kuali.lu.type.CreditCourse");
		clu.setState("draft");
		clu.setExpirationDate(new Date());
		clu.setEffectiveDate(new Date());

		clu = luService.createClu(clu.getType(), clu);

		CluInfo formatClu = new CluInfo();
		formatClu.setType("kuali.lu.type.CreditCourseFormatShell");
		formatClu.setState("draft");
		formatClu.setExpirationDate(new Date());
		formatClu.setEffectiveDate(new Date());

		formatClu = luService.createClu(formatClu.getType(), formatClu);

		CluCluRelationInfo relation = new CluCluRelationInfo();
		relation.setCluId(clu.getId());
		relation.setRelatedCluId(formatClu.getId());
		relation.setType("luLuRelationType.hasCourseFormat");

		luService.createCluCluRelation(clu.getId(), formatClu.getId(),
				"luLuRelationType.hasCourseFormat", relation);

		CluInfoHierarchyAssembler cluAssembler = getAssembler();
		cluAssembler.setLuService(luService);

		CluInfoHierarchy result = cluAssembler.get(clu.getId());
		// TODO finish updating test with new structures
		
//		CluInfoHierarchy course = new CluInfoHierarchy();
//		course.setCluInfo(clu);
//		course.setModificationState(ModificationState.CREATED);
//		
//		CluInfoHierarchy format = new CluInfoHierarchy();
//		format.setCluInfo(formatClu);
//		format.setModificationState(ModificationState.CREATED);
//		format.setParentRelationState("draft");
//		format.setParentRelationType(CreditCourseProposalAssembler.FORMAT_RELATION_TYPE);
//		course.getChildren().add(format);
		
		
		
//		Data cluData = cluAssembler.get(clu.getId());
//
//		dump(cluData);
	}


	private static CluInfoHierarchyAssembler getAssembler() {
		CluInfoHierarchyAssembler cluAssembler = new CluInfoHierarchyAssembler();
		RelationshipHierarchy course = new RelationshipHierarchy();
		RelationshipHierarchy formats = new RelationshipHierarchy(CreditCourseProposalAssembler.FORMAT_RELATION_TYPE, "draft");
		RelationshipHierarchy activities = new RelationshipHierarchy(CreditCourseProposalAssembler.ACTIVITY_RELATION_TYPE, "draft");
		
		course.addChild(formats);
		formats.addChild(activities);
		
		cluAssembler = new CluInfoHierarchyAssembler();
		cluAssembler.setHierarchy(course);
		return cluAssembler;
	}
	private static void dump(final Data e) {
		dump(e, 0);
		System.out
				.println("***************************************************");
	}

	private static void dump(final Data e, final int indent) {
		String pad = "";
		for (int i = 0; i < indent; i++) {
			pad += "----";
		}
		for (final Data.Property prop : e) {
			if (prop.getValueType().equals(Data.class)) {
				System.out.println(pad + " Nested: ("
						+ prop.getKeyType().getName() + ")"
						+ prop.getKey().toString());
				dump((Data) prop.getValue(), indent + 1);
			} else {
				System.out.println(pad + " (" + prop.getKeyType().getName()
						+ ")" + prop.getKey().toString() + " = ("
						+ prop.getValueType().getName() + ")"
						+ prop.getValue().toString());
			}
		}
	}

	private static LuService aquireLuService() throws Exception {
		// Get client
		JaxWsClientFactory client = new JaxWsClientFactoryBean();
		String urlString = "http://localhost:8081/ks-embedded-dev/services/LuService";
		client.setAddress(urlString);
		client.setServiceEndpointInterface(LuService.class);
		client.setServiceName(new QName("http://student.kuali.org/wsdl/lu",
				"LuService"));
		client
				.setWsdlLocation("http://localhost:8081/ks-embedded-dev/services/LuService?wsdl");
		LuService luService = (LuService) client.getObject();
		return luService;
	}
	
	private static ProposalService aquireProposalService() throws Exception {
		JaxWsClientFactory client = new JaxWsClientFactoryBean();
		String urlString = "http://localhost:8081/ks-embedded-dev/services/ProposalService";
		client.setAddress(urlString);
		client.setServiceEndpointInterface(ProposalService.class);
		client.setServiceName(new QName("http://student.kuali.org/wsdl/proposal",
				"ProposalService"));
		client
				.setWsdlLocation("http://localhost:8081/ks-embedded-dev/services/ProposalService?wsdl");
		ProposalService proposalService = (ProposalService) client.getObject();
		return proposalService;
	}

//	private static AtpService aquireAtpService() throws Exception{
//		JaxWsClientFactory client = new JaxWsClientFactoryBean();
//		String urlString = "http://localhost:8081/ks-embedded-dev/services/AtpService";
//		client.setAddress(urlString);
//		client.setServiceEndpointInterface(AtpService.class);
//		client.setServiceName(new QName("http://student.kuali.org/wsdl/atp",
//				"AtpService"));
//		client.setWsdlLocation("http://localhost:8081/ks-embedded-dev/services/AtpService?wsdl");
//		AtpService atpService = (AtpService) client.getObject();
//		return atpService;
//	}
	
}
