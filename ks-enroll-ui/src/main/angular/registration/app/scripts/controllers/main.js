'use strict';

angular.module('regCartApp')
    .controller('MainCtrl', ['$scope', '$location', '$state', 'TermsService', 'ScheduleService', 'GlobalVarsService', 'APP_URL', 'DEFAULT_TERM',
        'LoginService', 'MessageService', '$modal',
    function MainCtrl($scope, $location, $state, TermsService, ScheduleService, GlobalVarsService, APP_URL, DEFAULT_TERM, LoginService, MessageService, $modal) {
        console.log('In Main Controller');

        $scope.appUrl = APP_URL.replace('/services/', '/');

        $scope.termId = null;
        $scope.termName = '';
        $scope.studentIsEligibleForTerm = true; // Top-level check whether student is eligible to register for the selected term

        // update the term name if the termId changes
        $scope.$watch('termId', function (newValue) {
            if (newValue) {
                $scope.termName = TermsService.getTermNameForTermId($scope.terms, newValue);

                // Check to see if the term is open or closed for registration
                if (newValue === DEFAULT_TERM) {
                    console.log('checking term eligibility');
                    TermsService.checkStudentEligibilityForTerm().query({termId: newValue}, function (response) {
                        $scope.studentIsEligibleForTerm = (angular.isDefined(response.isEligible) && response.isEligible) || false;
                    }, function (error) {
                        console.log('Error while checking if term is open for registration', error);
                        $scope.studentIsEligibleForTerm = false;
                    });
                } else {
                    console.log('term eligibility check bypassed - term != default term');
                    $scope.studentIsEligibleForTerm = true;
                }

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

        // Load up the available terms
        $scope.terms = TermsService.getTermsFromServer().query({termCode: null, active: true}, function (result) {
            $scope.termId = DEFAULT_TERM;
            TermsService.setTermId($scope.termId);//FIXME Term service is just a service handle, don't put business logic in it!!!
            $scope.termName = TermsService.getTermNameForTermId(result, $scope.termId);
        });


        $scope.messages = MessageService.getMessages().query({messageKey: null});

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

        $scope.$on('sessionExpired', function () {
            console.log('Received event sessionExpired');
            $modal.open({
                backdrop: 'static',
                templateUrl: 'partials/sessionExpired.html',
                controller: 'SessionCtrl'
            });
        });

        //Update the UI routing state so it is available in the scope.
        $scope.$parent.uiState = $state.current.name;
        $scope.$on('$stateChangeStart',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.$parent.uiState = toState.name;
            });
    }])

    .controller('SessionCtrl', ['$scope', 'LoginService', function SessionCtrl($scope, LoginService) {
        $scope.logout = function() {
            LoginService.logout().query({}, function () {
                //After logging in, reload the page.
                console.log('Session expired...logging out');
                location.reload();
            });
        };
    }]);
