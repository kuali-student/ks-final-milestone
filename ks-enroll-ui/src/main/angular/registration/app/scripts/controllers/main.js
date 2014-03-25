'use strict';

angular.module('regCartApp')
    .controller('MainCtrl',
    function ($scope, TermsService, ScheduleService, GlobalVarsService, APP_URL) {
        console.log('In Main Controller');

        $scope.appUrl = APP_URL.replace('/services/', '/');


        // update the term name if the termId changes
        $scope.$watch('termId', function (newValue) {
            if (newValue) {
                $scope.termName = TermsService.getTermNameForTermId($scope.terms, newValue);
            }
        });

        $scope.terms = TermsService.getTermsFromServer().query(({active: true}, function (result) {
            $scope.termId = 'kuali.atp.2012Spring';
            TermsService.setTermId($scope.termId);//FIXME Term service is just a service handle, don't put business logic in it!!!
            $scope.termName = TermsService.getTermNameForTermId(result, $scope.termId);
        }));





        ScheduleService.getScheduleFromServer().query({termId: $scope.termId }, function (result) {
            console.log('called rest service to get schedule data - in main.js');
            GlobalVarsService.updateScheduleCounts(result);
            $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;   // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
            $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount; // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
        });

    });
