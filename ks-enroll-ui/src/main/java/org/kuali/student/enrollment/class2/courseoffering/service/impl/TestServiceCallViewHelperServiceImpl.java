/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 9/26/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.refdata.CluFixer;
import org.kuali.student.enrollment.class2.courseoffering.service.TestServiceCallViewHelperService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * This class //TODO Fill this in
 * //TODO Is this class still needed? can we move things like this to a separate package?
 *
 * @author Kuali Student Team
 */
public class TestServiceCallViewHelperServiceImpl extends ViewHelperServiceImpl implements TestServiceCallViewHelperService {
    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;
    private CourseOfferingSetService socService = null;
    private CourseService courseService = null;
    private PopulationService populationService = null;
    private LRCService lrcService = null;
    private CluFixer cluFixer;
    private ContextInfo contextInfo = new ContextInfo();
    private static final Logger LOG = Logger.getLogger(TestServiceCallViewHelperServiceImpl.class);

    @Override
    public List<String> getSocIdsByTerm(String termId) throws Exception {
        _initServices();
        List<String> socIds = socService.getSocIdsByTerm(termId, contextInfo);
        return socIds;
    }

    String[] seatpoolIds = { "0201b1ab-342c-4e3e-8ecb-9f2664840a6f",
           "02ba300c-fd90-4992-92fd-741bd6f0a2a9",
           "03deab32-5d50-420b-b8b1-b8d6988a39e6",
           "071906da-9948-46d5-84ab-09b3686f37da",
           "098280e8-6439-4cd4-8fc2-6c230b44dd19",
           "0b701ef2-3b62-4f3c-b946-c0168ac974b9",
           "0be56ad6-4073-4a28-9df8-8cab20f046ef",
           "0d31995b-49b3-44a2-af37-48dcc6d38a61",
           "0ff07b2d-fca4-4280-83b9-b61f75a54b5e",
           "17e5cd80-7660-4179-82f7-6431489badb1",
           "1c4ae57d-f65b-44fc-b03a-7d66207e1e16",
           "1df0d3c2-44f6-4faf-ac35-30be0c4ead9c",
           "1f6367e6-da7a-448d-beb6-e10cbbdd9437",
           "2125b83f-269a-49cc-9bcb-3130407c13c4",
           "219eba01-5f42-48b8-aecf-2e84fb631d07",
           "244ce4af-8efe-447c-a3bb-43029fe08678",
           "279c1d21-1296-470f-9d1e-62ef02568701",
           "27c10ec7-067d-4537-9f35-d508d3590b7d",
           "288c4b3f-ca52-43bf-9a2a-cccf591806e5",
           "2ccf4a39-c4ec-41dc-819e-a6769f94bc63",
           "2f2aa7d9-03ba-468b-b912-99f42e83dca7",
           "31895de0-1ad2-4443-9d63-de60e097cb45",
           "33eb196b-a7a9-425e-8347-fde8546c2fde",
           "34ef9e0d-4b0a-4257-92c9-79e194790077",
           "371174eb-1547-4c3d-b310-38220e80cc71",
           "37dcd6a0-94a3-4012-b66b-66863f32af34",
           "39a414fc-95b3-4f00-89b8-9d7aff89a63b",
           "3be90933-bafd-4f97-92cb-68328dbeb632",
           "3df57340-2d22-437e-9225-c171e2fdef98",
           "3e54fb84-b45d-49df-8bb2-f951b4dc6e71",
           "40729ced-9dda-4f56-97bc-cffcca2ed641",
           "49a4f7e2-ee6d-4938-bd6d-ae6d2f142529",
           "4a86ad3e-8026-405b-af52-85c16ebd2bd1",
           "4c30c92d-5c7f-4bb6-8e41-32dc78eaefff",
           "4e119624-8eeb-4c15-98ee-507fe259f942",
           "4e5542b6-65ea-45e3-9e06-4feb7d67b3b5",
           "5033d31b-3504-456d-aa19-ca2ddc086a58",
           "50b6cf91-033e-4b9e-af0b-03f05467d834",
           "510a701b-a221-4b74-a429-c8d5cf4a1d67",
           "51ad1361-391a-4a18-894f-2331838e73e8",
           "59c2e0a0-28a8-4345-abe2-dd9310452fc4",
           "5b2fb2ed-1867-417a-a9f6-2c74df64f512",
           "5eb01f9e-0058-42a6-b942-28aa6fd14bd6",
           "5f0f12c4-2b40-4544-8f1e-d937853b0f65",
           "5f2ab9d5-26a5-4dd1-a633-a44dd9f9cbdf",
           "5fda59aa-2bee-42f2-b36f-96fd291a9d7d",
           "60f47385-680a-4e9b-ae53-3490920a51eb",
           "61c6f740-93f8-471a-8df9-96a17e3e096c",
           "626cf4cd-914b-49a3-a2b4-8b836c6fc693",
           "6358adb8-60ed-4982-b091-0055eaa1a1a4",
           "6381face-4e20-4fc4-a98a-052c02b7dc29",
           "65a97bdb-2713-4129-a664-7a57b51f45db",
           "6661284f-e282-4ca5-99df-ee872586aa14",
           "6936ea76-d2ea-4e4d-9c11-03043a66cda7",
           "6b8f9c95-5e00-4317-a701-10fd459b19fc",
           "6c275d7b-d93c-4269-88eb-91b0b9e3548f",
           "6d3ea0e7-4ce4-430d-b839-c34043a40e02",
           "6d785ef9-9517-4ce3-a6cb-d39f9f810cbb",
           "7083fa70-82fd-4bdb-92ac-b1faa3cfd1da",
           "70c426ea-ea18-4eef-b20a-bcb92bc2d096",
           "73aa844f-3e8d-4d35-a0cf-6597bec3a4df",
           "7759a14c-be98-41e8-9e54-023732266bbb",
           "7b3d75a5-ae19-4a74-8a07-a880df9acc0b",
           "7d6cbe9b-7e9f-41fc-be14-c452921bb6c0",
           "7e9f1a53-a6c6-4245-bb7f-95c28bfa1967",
           "7fd407a4-f87a-47fd-aa54-68b862037f44",
           "834d1435-7684-429c-aafa-92fac236068a",
           "8764935e-e015-4faf-8272-c8f96f5bbc28",
           "87ca94d6-dd3f-412f-a26b-26b34032fe3b",
           "8a087933-565d-4748-b9f4-56fdeb6d4ffe",
           "8b57e639-1d19-4508-872f-3e5c6da07750",
           "8be5af6a-1d4e-4cc5-9827-75246efc7da7",
           "8befb71b-dace-4a09-a47c-766428892731",
           "8bfa7b87-5a48-4fc0-aac8-d0493d786f30",
           "8ecce06c-d5b7-41e9-9313-c8b43ab7a200",
           "906e0d98-6109-4b4d-b157-f7d8ff662bdb",
           "9337172f-21a6-4c50-b7a6-3d24f697a9f7",
           "94d9237b-af39-4211-901b-b88b41752d8d",
           "95234230-f1f1-4665-8569-8bce7637a485",
           "95bf9c62-6458-4a9d-be51-43904f822778",
           "96053b8c-7735-4009-9041-8f1a3a38badd",
           "97e84327-120b-4d65-9d90-008f64559a0c",
           "9e4bf5b3-2eb6-4728-9626-250c93c80f70",
           "9e4c5bad-2b3f-4aef-91ed-4d5b0fd74ded",
           "a01b9fe9-26f4-4cff-9c50-34300782e670",
           "a18b672c-79a3-45cd-a86b-03af1e77bb0c",
           "a2d3fe30-aa36-4e0c-8e80-5f388f13af6c",
           "a38ec610-5afa-408b-a6ba-6a43f2b3dee0",
           "a5f20784-2560-4810-86be-668261c53da3",
           "a6b10e50-bab2-4e29-99d3-7e0891138038",
           "a76acb6f-fc98-4973-bed3-fef6383319c5",
           "a7c14500-9709-42bf-9cdf-d61507d9f83a",
           "a933428a-4ff9-42c8-a196-e6f257702116",
           "ad533aeb-35d4-4386-9937-fceff5e937e9",
           "ae8d868d-e778-49c9-9505-e619e724a7ab",
           "afbc6ddb-1622-4d8c-b358-b62dffef5734",
           "b165aabd-8363-4c5f-8033-568ce60174e9",
           "b4258b7b-76d7-4662-8803-8c0384cb12bf",
           "b4494697-c9d4-4b69-982d-f2954519f0e3",
           "b4bfa7f8-f65d-4f48-9ba5-64abf548b854"};

    String[] aoIds = {"34534989-dce6-4e54-80d2-2725e57d599f",
            "0bfe63c1-4a77-4d90-89bb-42942f3a04c1",
            "3a301fd0-bbc0-4763-9f1c-88631b1d209e",
            "e49e158b-9c95-4aab-8c67-6d0fc38041e9",
            "bb65fe98-964c-4dd5-b50c-92544d7f5ccf",
            "40e30b81-66ff-4c19-b194-27b204e8a75d",
            "34fc0741-0df6-489b-ad66-eb0b2507e8c6",
            "9ef1c8af-87cc-4722-af76-b97866d5a506",
            "0677a25f-5275-448c-bdd2-3cb0309f7a5a",
            "4c014238-c2ff-40e6-add9-5a8982d35870",
            "955579e4-2679-4f69-bcb9-9d194024b4c5",
            "619451a5-e0ca-49ef-abf8-4345f101565d",
            "4a4bb795-9671-44a8-93f2-b5455f311c10",
            "c45bf6fc-d694-4fa0-b2d4-2f7e19fc40c8",
            "34fc0741-0df6-489b-ad66-eb0b2507e8c6",
            "99d2e788-bc46-4242-a0a4-406b4b2b620f",
            "c76e3d55-0154-4da2-9f6d-1304a0c1528a",
            "9e06c246-6d4b-405d-a0f5-311bc95f6503",
            "9c8fd6bd-5fa2-4545-951e-de164a3abe5e",
            "bb65fe98-964c-4dd5-b50c-92544d7f5ccf",
            "045885e0-2527-459d-82cb-f4ebf59db3be",
            "c4c4dd73-95d7-41e4-aad2-f11c88c40237",
            "f651bd99-206c-447e-bc7b-c029f74aabaf",
            "736064c9-7f87-45bf-8af2-3155a61e0d33",
            "5805a0ca-dd9d-4559-a628-378b0f923db1",
            "0b4bbd1b-41a0-4127-87bd-e8e33415dca7",
            "9a81a6f7-d31a-4b49-a7f0-ff581be89fff",
            "e636edef-6d7a-422b-b1af-1905c9d22860",
            "736064c9-7f87-45bf-8af2-3155a61e0d33",
            "bdc264b4-0e94-4576-89c2-b62edf38ff2c",
            "276b19bd-3694-4c7b-9dfe-70fb468dca93",
            "0f072c7f-bd4b-4f30-afad-f3c48655d5d1",
            "f651bd99-206c-447e-bc7b-c029f74aabaf",
            "37f45d7a-e5f5-469f-9923-6e21339ffac7",
            "9e06c246-6d4b-405d-a0f5-311bc95f6503",
            "3be9d106-f201-445b-94b8-af34c430a830",
            "069d6479-0874-4839-ab06-a76107a2e2ed",
            "0f072c7f-bd4b-4f30-afad-f3c48655d5d1",
            "0f072c7f-bd4b-4f30-afad-f3c48655d5d1",
            "15d39449-795f-448e-b184-e5df3430d18e",
            "0bfe63c1-4a77-4d90-89bb-42942f3a04c1",
            "d084e676-d009-4e4c-bdda-ba4c2b21517c",
            "8c8dd5bf-d678-4be1-982f-1f074cf9fe1a",
            "e5fbb3cb-20d4-4885-ac6a-5e0a0aea4a5e",
            "8d136d8d-4bed-4048-95d9-6dfefd44c994",
            "b36f0037-bb56-49cd-b7de-7919572c8f92",
            "9c8fd6bd-5fa2-4545-951e-de164a3abe5e",
            "8d136d8d-4bed-4048-95d9-6dfefd44c994",
            "af6971b5-b2e3-4c33-8718-dafd184150d6",
            "46594cb4-9ac0-495b-b574-a5f5d83286a8",
            "3a301fd0-bbc0-4763-9f1c-88631b1d209e",
            "be68efa2-819d-4ecc-87bf-dc74d5dfaada",
            "506f8275-b474-47bd-8cdc-0385c74ab2eb",
            "a21d0070-d68f-4ff2-831b-9ab7880fc185",
            "e49e158b-9c95-4aab-8c67-6d0fc38041e9",
            "b36f0037-bb56-49cd-b7de-7919572c8f92",
            "a2df8009-4a92-4204-9e07-5b21499a463e",
            "d9440f0a-5c1f-44f4-ae49-27040da066ee",
            "99d2e788-bc46-4242-a0a4-406b4b2b620f",
            "455ba0f9-7089-413d-b18f-3fb1e796017c",
            "0118da0b-73bf-47f3-9ebd-3547efa174ce",
            "3343d76f-7eb0-403d-bb0e-236cffc7e7bb",
            "efb38ffc-85f7-43ab-8857-5a3a74679670",
            "15d39449-795f-448e-b184-e5df3430d18e",
            "0b4bbd1b-41a0-4127-87bd-e8e33415dca7",
            "48d97a2d-17f7-4216-be5f-68f1e8770b87",
            "f651bd99-206c-447e-bc7b-c029f74aabaf",
            "a7a950d8-dedc-40f5-b087-3acd1b4abec3",
            "65109be4-7345-426e-af50-f1f3dded9c2e",
            "5e106f60-21f4-403b-8d50-71b121b265cb",
            "4fbb6527-88e5-42fc-8e6d-3c8c3bd9ee53",
            "e49e158b-9c95-4aab-8c67-6d0fc38041e9",
            "06089a2b-5199-4aac-927f-c6b956113ed3",
            "0ceaede7-542b-4a36-a894-889a0eab4888",
            "b4a1fc65-c36c-4a58-9918-5cae14011697",
            "e636edef-6d7a-422b-b1af-1905c9d22860",
            "c45bf6fc-d694-4fa0-b2d4-2f7e19fc40c8",
            "0118da0b-73bf-47f3-9ebd-3547efa174ce",
            "b57726de-f7da-4d3c-bc53-52b0f5aa1b40",
            "15d39449-795f-448e-b184-e5df3430d18e",
            "9e06c246-6d4b-405d-a0f5-311bc95f6503",
            "276b19bd-3694-4c7b-9dfe-70fb468dca93",
            "a2df8009-4a92-4204-9e07-5b21499a463e",
            "9a81a6f7-d31a-4b49-a7f0-ff581be89fff",
            "128e4bf2-2fe9-44bd-96c1-d0f155049cda",
            "e5fbb3cb-20d4-4885-ac6a-5e0a0aea4a5e",
            "34fc0741-0df6-489b-ad66-eb0b2507e8c6",
            "5805a0ca-dd9d-4559-a628-378b0f923db1",
            "be68efa2-819d-4ecc-87bf-dc74d5dfaada",
            "e636edef-6d7a-422b-b1af-1905c9d22860",
            "99d2e788-bc46-4242-a0a4-406b4b2b620f",
            "0b4bbd1b-41a0-4127-87bd-e8e33415dca7",
            "0118da0b-73bf-47f3-9ebd-3547efa174ce",
            "b4a1fc65-c36c-4a58-9918-5cae14011697",
            "c6f0c211-619f-47fe-8470-343f246d3273",
            "276b19bd-3694-4c7b-9dfe-70fb468dca93",
            "bb65fe98-964c-4dd5-b50c-92544d7f5ccf",
            "4d9ea07b-82ec-4af6-9a6d-c6a5f977f727",
            "8d136d8d-4bed-4048-95d9-6dfefd44c994",
            "48d97a2d-17f7-4216-be5f-68f1e8770b87"};

    @Override
    public void verifyPopulations() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        _initServices();
        //Run in a new thread
        new Thread(new CluFixRunner(cluFixer)).start();
    }

    public class CluFixRunner implements Runnable {
        private CluFixer cluFixer;

        public CluFixRunner(CluFixer cluFixer) {
            this.cluFixer = cluFixer;
        }

        @Override
        public void run() {
            try {
                cluFixer.cleanClus("C:/Users/Charles/Desktop/Kuali/RefData/courseIds.txt");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void _initServices() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (courseService == null) {
            Object o = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "course",
                    "CourseService"));
            courseService = (CourseService) o;
        }

        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE,
                    PopulationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if (lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE,
                    LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        if(cluFixer == null){
            cluFixer = (CluFixer) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/cluFixer","CluFixer"));
        }
    }
}

