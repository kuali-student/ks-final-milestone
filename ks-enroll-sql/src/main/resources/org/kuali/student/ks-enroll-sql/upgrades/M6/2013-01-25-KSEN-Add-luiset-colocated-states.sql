INSERT INTO ksen_state_lifecycle(ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, VER_NBR, CREATETIME, CREATEID)
  VALUES( 'kuali.luiset.lifecycle', 'kuali.luiset.lifecycle', 'kuali.luiset.lifecycle state lifecycle', 'kuali.luiset.lifecycle state lifecycle', 0, '25-JAN-13 09.36.00 AM', 01 )
/
INSERT INTO ksen_state(ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, VER_NBR, CREATETIME, CREATEID)
  VALUES( 'kuali.luiset.state.active', 'Active', 'The identifier is active', 'The identifier is active', 'kuali.luiset.lifecycle', 0, '25-JAN-13 08.39.00 AM', 01 )
/
INSERT INTO ksen_state(ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, VER_NBR, CREATETIME, CREATEID)
  VALUES( 'kuali.luiset.state.inactive', 'Inactive', 'The identifier is inactive', 'The identifier is inactive', 'kuali.luiset.lifecycle', 0, '25-JAN-13 08.39.00 AM', 01 )
/