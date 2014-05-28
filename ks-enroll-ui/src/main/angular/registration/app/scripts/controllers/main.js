'use strict';

angular.module('regCartApp')
    .controller('MainCtrl',
    function ($scope, $rootScope, $location, $state, TermsService, ScheduleService, GlobalVarsService, APP_URL, LoginService) {
        console.log('In Main Controller');

        $scope.appUrl = APP_URL.replace('/services/', '/');


        // update the term name if the termId changes
        $scope.$watch('termId', function (newValue) {
            if (newValue) {
                $scope.termName = TermsService.getTermNameForTermId($scope.terms, newValue);

                ScheduleService.getScheduleFromServer().query({termId: newValue }, function (result) {
                    console.log('called rest service to get schedule data - in main.js');
                    GlobalVarsService.updateScheduleCounts(result);
                    $scope.cartCredits = GlobalVarsService.getCartCredits; // notice that i didn't put the (). in the ui call: {{cartCredits()}}
                    $scope.cartCourseCount = GlobalVarsService.getCartCourseCount; // notice that i didn't put the (). in the ui call: {{cartCourseCount()}}
                    $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;   // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
                    $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount; // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
                    $scope.waitlistedCredits = GlobalVarsService.getWaitlistedCredits;   // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
                    $scope.waitlistedCourseCount = GlobalVarsService.getWaitlistedCourseCount; // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
                    $scope.showWaitlistedSection = GlobalVarsService.getShowWaitlistedSection;
                    $scope.userId = GlobalVarsService.getUserId;
                });
            }
        });

        $scope.terms = TermsService.getTermsFromServer().query({termCode: null, active: true}, function (result) {
            $scope.termId = 'kuali.atp.2012Fall';
            TermsService.setTermId($scope.termId);//FIXME Term service is just a service handle, don't put business logic in it!!!
            $scope.termName = TermsService.getTermNameForTermId(result, $scope.termId);
        });




        /**
        ScheduleService.getScheduleFromServer().query({termId: $scope.termId }, function (result) {
            console.log('called rest service to get schedule data - in main.js');
            GlobalVarsService.updateScheduleCounts(result);
            $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;   // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
            $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount; // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
        });
         **/
        $scope.logout = function(){
            LoginService.logout().query({}, function () {
                //After logging in, reload the page.
                console.log('Logging out');
                location.reload();
            });
        };

        $scope.goToPage = function (page) {
            console.log('Navigating to page: ' + page);
            $location.url(page);
        };

        //Update the UI routing state so it is available in the scope.
        $scope.$parent.uiState = $state.current.name;
        $scope.$on('$stateChangeStart',
            function(event, toState, toParams, fromState, fromParams){
                $scope.$parent.uiState = toState.name;
            });
    });
