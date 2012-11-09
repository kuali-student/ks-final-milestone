--
-- Copyright 2005-2012 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

--
--     KSENROLL-3697 - clean up ENR workflow docs
--     Cleaning AcademicCalendarWrapperMaintenanceDocument,MilestoneTypeMaintenanceDocument,
--              CourseOfferingInfoMaintenanceDocument,FormatOfferingInfoMaintenanceDocument,
--              TermMaintenanceDocument
--
delete from KREW_RTE_NODE_CFG_PARM_T where RTE_NODE_ID in (select RTE_NODE_ID from KREW_RTE_NODE_T where DOC_TYP_ID in ('3020','3018','3019','3023','3034'))
/

delete from KREW_DOC_TYP_PROC_T where DOC_TYP_ID in ('3020','3018','3019','3023','3034')
/

delete from KREW_RTE_NODE_T where DOC_TYP_ID in ('3020','3018','3019','3023','3034')
/

delete from KREW_DOC_TYP_T where DOC_TYP_ID in ('3020','3018','3019','3023','3034')
/