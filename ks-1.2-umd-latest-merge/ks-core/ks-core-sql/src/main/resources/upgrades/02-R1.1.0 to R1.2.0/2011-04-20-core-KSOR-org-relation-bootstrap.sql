update KSOR_ORG_PERS_RELTN set PERS_ID = 'testuser1' where PERS_ID = 'KIM-1'
/
update KSOR_ORG_PERS_RELTN set PERS_ID = 'testuser3' where PERS_ID = 'KIM-3'
/
update KSOR_ORG_PERS_RELTN set PERS_ID = 'testuser4' where PERS_ID = 'KIM-4'
/
update KSOR_ORG_PERS_RELTN set ORG_PERS_RELTN_TYPE = 'kuali.org.PersonRelation.Chair' where ID = '3'  -- He was of the type kuali.org.PersonRelation.Coordinator
/
update KSOR_ORG_PERS_RELTN set ORG_PERS_RELTN_TYPE = 'kuali.org.PersonRelation.Chair' where ID = '4'  -- He was of the type kuali.org.PersonRelation.Professor
/
update KSOR_ORG_PERS_RELTN set ORG_PERS_RELTN_TYPE = 'kuali.org.PersonRelation.Chair' where ID = '6'  -- He was of the type kuali.org.PersonRelation.Professor
/
update KSOR_ORG_PERS_RELTN set ORG_PERS_RELTN_TYPE = 'kuali.org.PersonRelation.Chair', PERS_ID = 'testuser2'  where ID = '1'  -- He was of the type kuali.org.PersonRelation.Head
/
