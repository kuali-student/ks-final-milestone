function ajaxCallActivityOfferings(controllerMethod, courseOfferingId) {

//    alert("testing the method code = " + courseOfferingId);

    ajaxSubmitForm(controllerMethod, null,
        {courseOfferingId: courseOfferingId},
        null, null, null);
}
