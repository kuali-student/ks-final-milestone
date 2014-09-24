-- Spring 2014 WMST298G, Canceled AO:E RG:1004
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.activity.offering.state.canceled' where ID='873d1b71-b3ab-4cfa-81ff-e7f22716d1d8' and LUI_TYPE like 'kuali.lui.type.activity.offering.%'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.registration.group.state.canceled' where ID='92021948-469b-45c5-a5a5-b10d13df1394' and LUI_TYPE = 'kuali.lui.type.registration.group'
/

-- Spring 2014 CHEM271, Suspended AO:N RG:1012
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.activity.offering.state.suspended' where ID='3a3d5c08-c720-4d97-9680-5b0d5fff4c42' and LUI_TYPE like 'kuali.lui.type.activity.offering.%'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.registration.group.state.suspended' where ID='c79b8da2-c05c-4d02-8798-d0f59a211529' and LUI_TYPE = 'kuali.lui.type.registration.group'
/

-- Summer1 2014 PHYS499, Suspended  AO:C RG:1003
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.activity.offering.state.suspended' where ID='201cf3e7-12fa-434a-b328-ff1d5b696a45' and LUI_TYPE like 'kuali.lui.type.activity.offering.%'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.registration.group.state.suspended' where ID='47f4e69d-6d07-4d94-8a2b-a913b9c6a5f0' and LUI_TYPE = 'kuali.lui.type.registration.group'
/

-- Summer1 2014 PHYS141, Suspended AO:B RG:1001
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.activity.offering.state.suspended' where ID='8e7ac9f4-4892-47b0-8ba1-678510ef8a65' and LUI_TYPE like 'kuali.lui.type.activity.offering.%'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.registration.group.state.suspended' where ID='961e330e-364f-4f32-8da5-d05a7376d86e' and LUI_TYPE = 'kuali.lui.type.registration.group'
/

-- Summer1 2014 BSCI379M Suspended AO: B, RG:1002
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.activity.offering.state.suspended' where ID='4d416ba0-5a97-4321-945d-52d0a28ac2a9' and LUI_TYPE like 'kuali.lui.type.activity.offering.%'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.registration.group.state.suspended' where ID='bef64dbd-9b81-4ca0-ad33-459ed643eca6' and LUI_TYPE = 'kuali.lui.type.registration.group'
/

-- Summer1 2014 ENGL393X Canceled AO:A RG:1001
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.activity.offering.state.canceled' where ID='af3f9ffc-a5b6-4bd8-b2db-9aea956a2aa4' and LUI_TYPE like 'kuali.lui.type.activity.offering.%'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.registration.group.state.canceled' where ID='71be0ad8-73e8-41a3-9e62-2cff4ecb622a' and LUI_TYPE = 'kuali.lui.type.registration.group'
/

-- Spring 2014 ENGL388A Canceled 1AO+1FO+1AO+1RG
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.course.offering.state.canceled' where ID='b61df554-f875-4de3-a5e2-f55899fd8192' and LUI_TYPE = 'kuali.lui.type.course.offering'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.format.offering.state.canceled' where ID='281d8a2c-446b-4955-a626-dbdfb24f7b9e' and LUI_TYPE = 'kuali.lui.type.course.format.offering'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.activity.offering.state.canceled' where ID='c410380d-d727-408c-881f-096d95ea3177' and LUI_TYPE like 'kuali.lui.type.activity.offering.%'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.registration.group.state.canceled' where ID='9d2d7f42-cc0d-4caf-93f6-03d55706bf4f' and LUI_TYPE = 'kuali.lui.type.registration.group'
/

-- Summer 1 2014 WMST269M Canceled 1AO+1FO+1AO+1RG
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.course.offering.state.canceled' where ID='c033996d-73cf-4153-93ca-37f50f87d123' and LUI_TYPE = 'kuali.lui.type.course.offering'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.format.offering.state.canceled' where ID='3f969cf9-c455-4c30-bd55-6db7743e6583' and LUI_TYPE = 'kuali.lui.type.course.format.offering'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.activity.offering.state.canceled' where ID='8a8a8152-e165-486e-8a8b-23900d61339c' and LUI_TYPE like 'kuali.lui.type.activity.offering.%'
/
UPDATE KSEN_LUI SET LUI_STATE='kuali.lui.registration.group.state.canceled' where ID='63ad67a8-d83d-4cde-86da-3e1bbf33de4c' and LUI_TYPE = 'kuali.lui.type.registration.group'
/

-- Spring 2014 HIST610 Suspended AO: A, RG:1001
update KSEN_LUI SET LUI_STATE = 'kuali.lui.activity.offering.state.suspended' where ID = '38b38d07-7496-493c-94b8-420e599107d7'
/
update KSEN_LUI SET LUI_STATE = 'kuali.lui.registration.group.state.suspended' where ID = 'fa151630-ca7b-45f1-b945-5ca994aabd5b'
/

-- Spring 2014 HIST811 Canceled AO: A, RG:1001
update KSEN_LUI SET LUI_STATE = 'kuali.lui.activity.offering.state.canceled' where ID = 'a43b0dc9-0067-4e98-aac4-4c69cc68f413'
/
update KSEN_LUI SET LUI_STATE = 'kuali.lui.registration.group.state.canceled' where ID = '70982fed-4354-4da4-a01a-323ef61dd066'
/