--KSNROLL-14908 : Update the Applied Hold data with the term info where Hold Issue is based on Term
update KSEN_HOLD AP
   set AP.APP_EFF_TERM_ID =
       (select I.FIRST_APP_TERM_ID
          from KSEN_HOLD_ISSUE I
         where AP.ISSUE_ID = I.ID
           and I.HOLD_ISSUE_TERM_BASED_IND = 1)
 where AP.ISSUE_ID = (select I.ID
                        from KSEN_HOLD_ISSUE I
                       where AP.ISSUE_ID = I.ID
                         and I.HOLD_ISSUE_TERM_BASED_IND = 1)
/