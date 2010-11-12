UPDATE KSST_REQ_COM_TYPE_NL_TMPL 
    SET TEMPLATE = 'Must have earned a minimum grade of #if($gradeType.getId().equals("kuali.resultComponent.grade.letter") || $gradeType.getId().equals("kuali.resultComponent.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade in $courseCluSet.getCluSetAsCode()' 
    WHERE ID = '13'
/
UPDATE KSST_REQ_COM_TYPE_NL_TMPL 
    SET TEMPLATE = 'Must not have earned a maximum grade of #if($gradeType.getId().equals("kuali.resultComponent.grade.letter") || $gradeType.getId().equals("kuali.resultComponent.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade or higher in $courseCluSet.getCluSetAsCode()'
    WHERE ID = '14'
/
UPDATE KSST_REQ_COM_TYPE_NL_TMPL 
    SET TEMPLATE = 'Must successfully complete a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") with a minimum grade of #if($gradeType.getId().equals("kuali.resultComponent.grade.letter") || $gradeType.getId().equals("kuali.resultComponent.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade from $courseCluSet.getCluSetAsCode()'
    WHERE ID = '19'
/
UPDATE KSST_REQ_COM_TYPE_NL_TMPL 
    SET TEMPLATE = 'Must have earned a minimum grade of #if($gradeType.getId().equals("kuali.resultComponent.grade.letter") || $gradeType.getId().equals("kuali.resultComponent.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade in'
    WHERE ID = '113'
/
UPDATE KSST_REQ_COM_TYPE_NL_TMPL 
    SET TEMPLATE = 'Must not have earned a maximum grade of #if($gradeType.getId().equals("kuali.resultComponent.grade.letter") || $gradeType.getId().equals("kuali.resultComponent.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade or higher in'
    WHERE ID = '114'
/
UPDATE KSST_REQ_COM_TYPE_NL_TMPL 
    SET TEMPLATE = 'Must successfully complete a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") with a minimum grade of #if($gradeType.getId().equals("kuali.resultComponent.grade.letter") || $gradeType.getId().equals("kuali.resultComponent.grade.percentage"))$gradeType.getName().toLowerCase() #{end}$grade from'
    WHERE ID = '119'
/
