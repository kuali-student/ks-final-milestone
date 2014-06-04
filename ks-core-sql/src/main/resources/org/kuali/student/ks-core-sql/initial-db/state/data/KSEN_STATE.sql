TRUNCATE TABLE KSEN_STATE DROP STORAGE
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'Activity Offering Cluster is active','Activity Offering Cluster is active','kuali.activity.offering.cluster.state.active','kuali.course.activity.offering.cluster.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Slot is Active','Slot is Active','kuali.appointment.slot.state.active','kuali.appointment.slot.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Appointment is Active','Appointment is Active','kuali.appointment.state.active','kuali.appointment.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Students have been assigned to slots','Students have been assigned to slots','kuali.appointment.window.state.assigned','kuali.appointment.window.lifecycle','Assigned',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'Window has been created','Window has been created','kuali.appointment.window.state.draft','kuali.appointment.window.lifecycle','Draft',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Atp-Atp relation is active','kuali.atp.atp.relation.state.active','kuali.atp.atp.relation.process','Active','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Atp-Atp relation is inactive','kuali.atp.atp.relation.state.inactive','kuali.atp.atp.relation.process','Inactive','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Atp-Milestone relation is active','kuali.atp.milestone.relation.state.active','kuali.atp.milestone.relation.process','Active','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Atp-Milestone relation is inactive','kuali.atp.milestone.relation.state.inactive','kuali.atp.milestone.relation.process','Inactive','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Time Period is just tentative','kuali.atp.state.Draft','kuali.atp.process','Draft','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Time Period has been established','kuali.atp.state.Official','kuali.atp.process','Official','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'This issue is active and can be attached to holds','kuali.hold.issue.state.active','kuali.hold.issue.process','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120717000000', 'YYYYMMDDHH24MISS' ),'The issue is inactive an cannot be attached to new holds','kuali.hold.issue.state.inactive','kuali.hold.issue.process','Inactive',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'The instructor is assigned to teach this course or section','The instructor is assigned to teach this course or section','kuali.lpr.state.assigned','kuali.lpr.lifecycle.instructor.course.assignment','Assigned','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'The instructor is proposed to teach this course or section but it has not yet been confirmed','The instructor is proposed to teach this course or section but it has not yet been confirmed','kuali.lpr.state.tentative','kuali.lpr.lifecycle.instructor.course.assignment','Tentative','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'The instructor had been assigned but then that assignment was removed','The instructor had been assigned but then that assignment was removed','kuali.lpr.state.unassigned','kuali.lpr.lifecycle.instructor.course.assignment','Unassigned','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Activity offering was not able to be scheduled, either due to  conflict or lack of data.','Activity offering was not able to be scheduled, either due to  conflict or lack of data.','kuali.lui.activity.offering.scheduling.state.error','kuali.activity.offering.scheduling.lifecycle','Error','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'TBA or Departmentally owned and need not be sent to the scheduler','TBA or Departmentally owned and need not be sent to the scheduler','kuali.lui.activity.offering.scheduling.state.exempt','kuali.activity.offering.scheduling.lifecycle','Exempt','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Scheduled and ready to be Offered','Scheduled and ready to be Offered','kuali.lui.activity.offering.scheduling.state.scheduled','kuali.activity.offering.scheduling.lifecycle','Scheduled','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Activity may or may not have Requested Delivery Logistics (but does NOT have any data set as TBA) but has not yet been sent to the Scheduler.','Activity may or may not have Requested Delivery Logistics (but does NOT have any data set as TBA) but has not yet been sent to the Scheduler.','kuali.lui.activity.offering.scheduling.state.unscheduled','kuali.activity.offering.scheduling.lifecycle','Unscheduled','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Approved','Approved','kuali.lui.activity.offering.state.approved','kuali.activity.offering.lifecycle','Approved','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Canceled','Canceled','kuali.lui.activity.offering.state.canceled','kuali.activity.offering.lifecycle','Canceled','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Draft','Draft','kuali.lui.activity.offering.state.draft','kuali.activity.offering.lifecycle','Draft','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Offered','Offered','kuali.lui.activity.offering.state.offered','kuali.activity.offering.lifecycle','Offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Submitted','Submitted','kuali.lui.activity.offering.state.submitted','kuali.activity.offering.lifecycle','Submitted','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Suspended','Suspended','kuali.lui.activity.offering.state.suspended','kuali.activity.offering.lifecycle','Suspended','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Canceled','Canceled','kuali.lui.course.offering.state.canceled','kuali.course.offering.lifecycle','Canceled','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120719000000', 'YYYYMMDDHH24MISS' ),'Draft','Draft','kuali.lui.course.offering.state.draft','kuali.course.offering.lifecycle','Draft','SYSTEMLOADER',TO_DATE( '20120719000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Offered','Offered','kuali.lui.course.offering.state.offered','kuali.course.offering.lifecycle','Offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Planned','Planned','kuali.lui.course.offering.state.planned','kuali.course.offering.lifecycle','Planned','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'Suspended','Suspended','kuali.lui.course.offering.state.suspended','kuali.course.offering.lifecycle','Suspended','SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Canceled','Canceled','kuali.lui.format.offering.state.canceled','kuali.format.offering.lifecycle','Canceled','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120719000000', 'YYYYMMDDHH24MISS' ),'Draft','Draft','kuali.lui.format.offering.state.draft','kuali.format.offering.lifecycle','Draft','SYSTEMLOADER',TO_DATE( '20120719000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Offered','Offered','kuali.lui.format.offering.state.offered','kuali.format.offering.lifecycle','Offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Planned','Planned','kuali.lui.format.offering.state.planned','kuali.format.offering.lifecycle','Planned','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'Suspended','Suspended','kuali.lui.format.offering.state.suspended','kuali.format.offering.lifecycle','Suspended','SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'Registration Group is canceled','Registration Group is canceled','kuali.lui.registration.group.state.canceled','kuali.course.registration.group.lifecycle','Canceled',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'Registration Group is invalid','Registration Group is invalid','kuali.lui.registration.group.state.invalid','kuali.course.registration.group.lifecycle','Invalid',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'Registration Group is offered','Registration Group is offered','kuali.lui.registration.group.state.offered','kuali.course.registration.group.lifecycle','Offered',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'Registration Group is valid, but not ready to be offered','Registration Group is valid, but not ready to be offered','kuali.lui.registration.group.state.pending','kuali.course.registration.group.lifecycle','Pending',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'Registration Group is suspended','Registration Group is suspended','kuali.lui.registration.group.state.suspended','kuali.course.registration.group.lifecycle','Suspended',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Indicates that this milestone is just tentative','kuali.milestone.state.Draft','kuali.milestone.process','Draft','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'Indicates that this milestone has been established','kuali.milestone.state.Official','kuali.milestone.process','Official','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'A default state for this Population Rule','A default state for this Population Rule','kuali.population.population.rule.state.active','kuali.population.population.rule.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'The inactive state for this Population Rule','The inactive state for this Population Rule','kuali.population.population.rule.state.inactive','kuali.population.population.rule.lifecycle','Inactive',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'A default state for this Population','A default state for this Population','kuali.population.population.state.active','kuali.population.population.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120716000000', 'YYYYMMDDHH24MISS' ),'The inactive state for this Population','The inactive state for this Population','kuali.population.population.state.inactive','kuali.population.population.lifecycle','Inactive',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120730000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Check is active and should be checked across all Processes.','kuali.process.check.state.active','kuali.process.check.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120730000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Check is disabled across all Processes and should be skipped with a success.','kuali.process.check.state.disabled','kuali.process.check.lifecycle','Disabled',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120730000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Check is inactive (out to pasture) across all Processes and should fail.','kuali.process.check.state.inactive','kuali.process.check.lifecycle','Inactive',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120730000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Instruction is active and enabled','kuali.process.instruction.state.active','kuali.process.instruction.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120730000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Instruction is disabled and should be skipped.','kuali.process.instruction.state.disabled','kuali.process.instruction.lifecycle','Disabled',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120730000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Process is active and enabled.','kuali.process.process.state.active','kuali.process.process.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120730000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Process is disabled and should be skipped resulting in success.','kuali.process.process.state.disabled','kuali.process.process.lifecycle','Disabled',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120730000000', 'YYYYMMDDHH24MISS' ),'Indicates that this Process is inactive because it was put out to pasture. Any checks for this process should fail.','kuali.process.process.state.inactive','kuali.process.process.lifecycle','Inactive',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'Building is offline and not available for use','Building is offline and not available for use','kuali.room.building.state.offline','kuali.room.building.lifecycle','Offline',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'Building is online and available for business processes','Building is online and available for business processes','kuali.room.building.state.online','kuali.room.building.lifecycle','Online',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'The facility is offline and not available for use','The facility is offline and not available for use','kuali.room.facility.state.offline','kuali.room.facility.lifecycle','Offline',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'The facility is online and consumable by business processes','The facility is online and consumable by business processes','kuali.room.facility.state.online','kuali.room.facility.lifecycle','Online',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121217000000', 'YYYYMMDDHH24MISS' ),'The schedule is active','The schedule is active','kuali.room.responsible.org.state.active','kuali.room.responsible.org.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121217000000', 'YYYYMMDDHH24MISS' ),'The schedule is inactive','The schedule is inactive','kuali.room.responsible.org.state.inactive','kuali.room.responsible.org.lifecycle','Inactive',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'The room is offline and unavailable for use','The room is offline and unavailable for use','kuali.room.room.state.offline','kuali.room.room.lifecycle','Offline',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121211000000', 'YYYYMMDDHH24MISS' ),'The room is online and available for business processes','The room is online and available for business processes','kuali.room.room.state.online','kuali.room.room.lifecycle','Online',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120813000000', 'YYYYMMDDHH24MISS' ),'The request has been created','The request has been created','kuali.scheduling.schedule.request.state.created','kuali.scheduling.schedule.request.lifecycle','Created','SYSTEMLOADER',TO_DATE( '20120813000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'An existing course offering was canceled','An existing course offering was canceled','kuali.soc.rollover.result.item.state.canceled','kuali.soc.rollover.result.process','Canceled','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'An error occurred so this course offering was not successfully processed','An error occurred so this course offering was not successfully processed','kuali.soc.rollover.result.item.state.error','kuali.soc.rollover.result.process','Error','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'An existing course offering has a new version','An existing course offering has a new version','kuali.soc.rollover.result.item.state.new.version','kuali.soc.rollover.result.process','New Version','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'A new course offering was created','A new course offering was created','kuali.soc.rollover.result.item.state.processed.created','kuali.soc.rollover.result.process','Created','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'An existing course offering was deleted','An existing course offering was deleted','kuali.soc.rollover.result.item.state.processed.deleted','kuali.soc.rollover.result.process','Deleted','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'An existing course offering was updated','An existing course offering was updated','kuali.soc.rollover.result.item.state.processed.updated','kuali.soc.rollover.result.process','Updated','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'An existing course offering was retired','An existing course offering was retired','kuali.soc.rollover.result.item.state.retired','kuali.soc.rollover.result.process','Retired','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120905000000', 'YYYYMMDDHH24MISS' ),'The SOC was submitted for mass scheduling, and the scheduling process completed','The SOC was submitted for mass scheduling, and the scheduling process completed','kuali.soc.scheduling.state.completed','kuali.soc.scheduling.process','Completed',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120905000000', 'YYYYMMDDHH24MISS' ),'The SOC was submitted for mass scheduling and has not yet completed','The SOC was submitted for mass scheduling and has not yet completed','kuali.soc.scheduling.state.inprogress','kuali.soc.scheduling.process','In Progress',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120905000000', 'YYYYMMDDHH24MISS' ),'The SOC has never been submitted to the scheduler','The SOC has never been submitted to the scheduler','kuali.soc.scheduling.state.notstarted','kuali.soc.scheduling.process','Not Started',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120918000000', 'YYYYMMDDHH24MISS' ),'Initial state upon creation.','Initial state upon creation.','kuali.soc.state.draft','kuali.soc.lifecycle','Draft',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120831000000', 'YYYYMMDDHH24MISS' ),'State denoting that the mass scheduling event has occurred and COs and AOs are now once again available for Departmental Admin staff to edit','State denoting that the mass scheduling event has occurred and COs and AOs are now once again available for Departmental Admin staff to edit','kuali.soc.state.finaledits','kuali.soc.lifecycle','Final Edits',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120831000000', 'YYYYMMDDHH24MISS' ),'State denoting that all course offerings for the term are going to be scheduled','State denoting that all course offerings for the term are going to be scheduled','kuali.soc.state.locked','kuali.soc.lifecycle','Locked',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120918000000', 'YYYYMMDDHH24MISS' ),'State applied when courses have been made available to departments.','State applied when courses have been made available to departments.','kuali.soc.state.open','kuali.soc.lifecycle','Open',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120831000000', 'YYYYMMDDHH24MISS' ),'State applied to courses when the final edits have been completed and courses are ready to be offered to students for enrollment','State applied to courses when the final edits have been completed and courses are ready to be offered to students for enrollment','kuali.soc.state.published','kuali.soc.lifecycle','Published',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120905000000', 'YYYYMMDDHH24MISS' ),'The system automatically sets this state when the state is set to Published. Then the system begins the verification and update of the AOs. When this process has completed, the system sets the SOC state to Published.','The system automatically sets this state when the state is set to Published. Then the system begins the verification and update of the AOs. When this process has completed, the system sets the SOC state to Published.','kuali.soc.state.publishing','kuali.soc.lifecycle','Publishing In Progress',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'State Change is active','State Change is active','kuali.state.change.state.active','kuali.state.change.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'State constraint is active','State constraint is active','kuali.state.constraint.state.active','kuali.state.constraint.lifecycle','Active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20121102000000', 'YYYYMMDDHH24MISS' ),'State Propagation is active','State Propagation is active','kuali.state.propagation.state.active','kuali.state.propagation.lifecycle','Active',0)
/
