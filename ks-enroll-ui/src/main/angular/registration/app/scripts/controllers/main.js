'use strict';

angular.module('regCartApp')
    .controller('MainCtrl',
    function ($scope, TermsService, ScheduleService, GlobalVarsService, APP_URL) {
        console.log('In Main Controller');

        $scope.appUrl = APP_URL.replace('/services/', '/');

        $scope.terms = TermsService.getTermsFromServer().query(({active: true}, function (result) {
            // default to the current term
            /* Commenting this out for now. In real world use below
             angular.forEach($scope.terms, function (term) {
             if (term.currentTerm) {
             $scope.termId = term.termId;
             }
             });
             */
        }));

        $scope.termId = 'kuali.atp.2012Spring';
        TermsService.setTermId($scope.termId);//FIXME Term service is just a service handle, don't put business logic in it!!!

        ScheduleService.getScheduleFromServer().query({termId: $scope.termId }, function (result) {
            console.log('called rest service to get schedule data - in main.js');
            GlobalVarsService.updateScheduleCounts(result);
            $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;   // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
            $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount; // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
        });

    });
