--Commenting this out, KSENROLL-2683, causes integrity problems in CM.
--update KSLU_CLU set ver_ind_id='REFERENCECOURSEGEOG100' where id='REFERENCECOURSEGEOG100'
--/

update KSLU_CLU set ver_ind_id='REFERENCECOURSEGEOG123', curr_ver_end= to_timestamp('19-Dec-12 4.42.26.431 PM','DD-MON-RR HH.MI.SS.FF AM') where id='REFERENCECOURSEGEOG123'
/
