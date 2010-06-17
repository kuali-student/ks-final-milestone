/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.assembly;

import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.student.common.ws.beans.JaxWsClientFactory;
import org.kuali.student.common.ws.beans.JaxWsClientFactoryBean;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lu.assembly.CourseAssembler;
import org.kuali.student.lum.lu.assembly.CourseAssembler.RelationshipHierarchy;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

public class AssemblerTestMain {
	final static Logger LOG = Logger.getLogger(AssemblerTestMain.class);
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

		CourseAssembler courseAssembler = getAssembler();
		courseAssembler.setLuService(luService);

		Data result = courseAssembler.get(clu.getId());
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


	private static CourseAssembler getAssembler() {
	    CourseAssembler courseAssembler = new CourseAssembler();
		RelationshipHierarchy course = new RelationshipHierarchy();
		RelationshipHierarchy formats = new RelationshipHierarchy(CourseAssembler.FORMAT_RELATION_TYPE, "draft");
		RelationshipHierarchy activities = new RelationshipHierarchy(CourseAssembler.ACTIVITY_RELATION_TYPE, "draft");
		
		course.addChild(formats);
		formats.addChild(activities);
		
		courseAssembler = new CourseAssembler();
//		courseAssembler.setHierarchy(course);
		return courseAssembler;
	}
//	private static void dump(final Data e) {
//		dump(e, 0);
//		System.out
//				.println("***************************************************");
//	}

//	private static void dump(final Data e, final int indent) {
//		String pad = "";
//		for (int i = 0; i < indent; i++) {
//			pad += "----";
//		}
//		for (final Data.Property prop : e) {
//			if (prop.getValueType().equals(Data.class)) {
//				LOG.warn(pad + " Nested: ("
//						+ prop.getKeyType().getName() + ")"
//						+ prop.getKey().toString());
//				dump((Data) prop.getValue(), indent + 1);
//			} else {
//				LOG.warn(pad + " (" + prop.getKeyType().getName()
//						+ ")" + prop.getKey().toString() + " = ("
//						+ prop.getValueType().getName() + ")"
//						+ prop.getValue().toString());
//			}
//		}
//	}

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
