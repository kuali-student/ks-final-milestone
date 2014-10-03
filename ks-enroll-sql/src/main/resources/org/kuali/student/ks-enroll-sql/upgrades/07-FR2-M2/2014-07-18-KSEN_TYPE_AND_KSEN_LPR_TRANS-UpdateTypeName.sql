-- KSENROLL-13677 Change register to registration
UPDATE KSEN_TYPE SET TYPE_KEY='kuali.lpr.trans.type.registration' WHERE TYPE_KEY='kuali.lpr.trans.type.register'
/
UPDATE KSEN_LPR_TRANS SET LPR_TRANS_TYPE='kuali.lpr.trans.type.registration' WHERE LPR_TRANS_TYPE='kuali.lpr.trans.type.register'
/
