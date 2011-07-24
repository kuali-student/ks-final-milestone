--  INSERTING into KSEN_COMM_STATE
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.state.Draft',null,0,null,null,null,null,'Indicates that this Time Period is just tentative',null,null,'Draft','kuali.atp.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.state.Official',null,0,null,null,null,null,'Indicates that this Time Period has been established',null,null,'Official','kuali.atp.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.milestone.state.Draft',null,0,null,null,null,null,'Indicates that this milestone is just tentative',null,null,'Draft','kuali.milestone.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.milestone.state.Official',null,0,null,null,null,null,'Indicates that this milestone has been established',null,null,'Official','kuali.milestone.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.atp.relation.state.active',null,0,null,null,null,null,'Indicates that this Atp-Atp relation is active',null,null,'Active','kuali.atp.atp.relation.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.atp.relation.state.inactive',null,0,null,null,null,null,'Indicates that this Atp-Atp relation is inactive',null,null,'Inactive','kuali.atp.atp.relation.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.milestone.relation.state.active',null,0,null,null,null,null,'Indicates that this Atp-Milestone relation is active',null,null,'Active','kuali.atp.milestone.relation.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.milestone.relation.state.inactive',null,0,null,null,null,null,'Indicates that this Atp-Milestone relation is inactive',null,null,'Inactive','kuali.atp.milestone.relation.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.restriction.state.active',null,0,null,null,null,null,'This restriction is active and should be enforced',null,null,'Active','kuali.hold.restriction.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.restriction.state.inactive',null,0,null,null,null,null,'The restriction is inactive and should not be enforced',null,null,'Inactive','kuali.hold.restriction.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.issue.state.active',null,0,null,null,null,null,'This issue is active and can be attached to holds ',null,null,'Active','kuali.hold.issue.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.issue.state.inactive',null,0,null,null,null,null,'The hold is inactive an cannot be attached to new holds ',null,null,'Inactive','kuali.hold.issue.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.state.active',null,0,null,null,null,null,'This hold is active and should be enforced',null,null,'Active','kuali.hold.process.student')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.state.released',null,0,null,null,null,null,'The hold was active and the issue has been resolved',null,null,'Released','kuali.hold.process.student')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.state.canceled',null,0,null,null,null,null,'The hold was canceled or removed because it was originally put on in error',null,null,'Canceled','kuali.hold.process.student')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.draft',null,0,null,null,null,null,'Proposed for Departmental Planning purposed',null,null,'Draft','kuali.course.offering.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.submitted',null,0,null,null,null,null,'Submitted to central admin for approval (or scheduling)',null,null,'Submitted','kuali.course.offering.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.approved',null,0,null,null,null,null,'Approved and ready to be scheduled',null,null,'Approved','kuali.course.offering.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.lui.relation.state.active',null,0,null,null,null,null,'The relationship between the two LUIs is active ',null,null,'Active','kuali.lui.lui.relationship.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.lui.relation.state.inactive',null,0,null,null,null,null,'The relationship between the two LUIs is in-active ',null,null,'Inactive','kuali.lui.lui.relationship.process')
/

