UPDATE KREW_DOC_TYP_T SET DOC_HDLR_URL = '${lum.application.url}/kr-krad/retireCourse?methodToCall=docHandler'  WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.retire'))
/
UPDATE KREW_DOC_TYP_T SET DOC_HDLR_URL = '${lum.application.url}/kr-krad/retireCourse?methodToCall=docHandler'  WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.retire.admin'))
/
UPDATE KREW_DOC_TYP_T SET DOC_HDLR_URL = '${lum.application.url}/kr-krad/courses?methodToCall=docHandler'  WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'CluCreditCourseParentDocument'))
/