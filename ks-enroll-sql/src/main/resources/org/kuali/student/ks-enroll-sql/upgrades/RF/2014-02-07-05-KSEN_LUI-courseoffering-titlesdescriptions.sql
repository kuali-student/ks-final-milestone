UPDATE KSEN_LUI SET DESCR_PLAIN='A Indepth look into fantasy authors of the late 90s and 2000s inluding JK Rawlings, Tracy Hickman and Robert Jordan.', DESCR_FORMATTED='A Indepth look into fantasy authors of the late 90s and 2000s inluding JK Rawlings, Tracy Hickman and Robert Jordan.'
  WHERE name = 'ENGL278T CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)')
/
UPDATE KSEN_LUI SET DESCR_PLAIN='A deeper look into the myths and legends of ancient Greece including The Oydessy, Battle of Tory, numerous others. ', DESCR_FORMATTED='A deeper look into the myths and legends of ancient Greece including The Oydessy, Battle of Tory, numerous others. '
  WHERE name = 'ENGL278R CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)')
/
UPDATE KSEN_LUI SET DESCR_PLAIN='A look in to the development and evalution of the action hero from its earlier conceptions to modern day Rambos.', DESCR_FORMATTED='A look in to the development and evalution of the action hero from its earlier conceptions to modern day Rambos.'
  WHERE name = 'ENGL329S CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)')
/
UPDATE KSEN_LUI SET DESCR_PLAIN='Student Group exchange with Tokyo University Literature Department.', DESCR_FORMATTED='Student Group exchange with Tokyo University Literature Department.'
 WHERE name = 'ENGL369W CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)')
/
UPDATE KSEN_LUI SET DESCR_PLAIN='Comparison of historical works and their modern day interpretations including both written reworks, films and satirers.', DESCR_FORMATTED='Comparison of historical works and their modern day interpretations including both written reworks, films and satirers.'
 WHERE name = 'ENGL379T CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)')
/
UPDATE KSEN_LUI SET DESCR_PLAIN='Group Intership with popular fantasy publisher Tor. Experience literature publishing process from beginning to end.', DESCR_FORMATTED='Group Intership with popular fantasy publisher Tor. Experience literature publishing process from beginning to end.'
 WHERE name = 'ENGL388A CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)')
/
UPDATE KSEN_LUI_IDENT SET LNG_NAME='Special Topics in Literature: Modern Day Fantasy', SHRT_NAME='Special Topics in Literature: Modern Day Fantasy'
 WHERE LUI_ID in (
  select id from ksen_lui
    where name = 'ENGL278T CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)'))
/
UPDATE KSEN_LUI_IDENT SET LNG_NAME='Special Topics in Literature: Greek Mythology', SHRT_NAME='Special Topics in Literature: Greek Mythology'
 WHERE LUI_ID in (
  select id from ksen_lui
    where name = 'ENGL278R CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)'))
/
UPDATE KSEN_LUI_IDENT SET LNG_NAME='Special Topics in Film Studies: Action Heros', SHRT_NAME='Special Topics in Film Studies: Action Heros'
 WHERE LUI_ID in (
  select id from ksen_lui
    where name = 'ENGL329S CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)'))
/
UPDATE KSEN_LUI_IDENT SET LNG_NAME='Special Topics in Study Abroad III: Japan', SHRT_NAME='Special Topics in Study Abroad III: Japan'
 WHERE LUI_ID in (
  select id from ksen_lui
    where name = 'ENGL369W CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)'))
/
UPDATE KSEN_LUI_IDENT SET LNG_NAME='Special Topics in Literature: Historical Remakes by Modern Day Writers', SHRT_NAME='Special Topics in Literature: Historical Remakes by Modern Day Writers'
 WHERE LUI_ID in (
  select id from ksen_lui
    where name = 'ENGL379T CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)'))
/
UPDATE KSEN_LUI_IDENT SET LNG_NAME='Writing Intership: Tor Books', SHRT_NAME='Writing Intership: Tor Books'
 WHERE LUI_ID in (
  select id from ksen_lui
    where name = 'ENGL388A CO' and atp_id='kuali.atp.2014Spring'
    and lui_type='kuali.lui.type.course.offering'
    and  regexp_like (lui_state,'kuali.lui.course.offering.state.(draft|planned|offered)'))
/