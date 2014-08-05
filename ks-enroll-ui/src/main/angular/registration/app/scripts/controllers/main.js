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
        $scope.$watch('termId', function (newValue, oldValue) {
            if (newValue !== oldValue) {
                $scope.termName = TermsService.getTermNameForTermId($scope.terms, newValue);

                // Check to see if the term is open or closed for registration
                if (newValue === DEFAULT_TERM) {
                    console.log('checking term eligibility');
                    TermsService.checkStudentEligibilityForTerm().query({termId: newValue}, function (response) {
                        $scope.studentIsEligibleForTerm = (angular.isDefined(response.isEligible) && response.isEligible) || false;

                        // Broadcast a termIdChanged event notifying any listeners that the new termId is ready to go.
                        // Doing it this way prevents unnecessary loading & processing from the term change
                        // of things the user won't have access to or see.
                        if ($scope.studentIsEligibleForTerm) {
                            $scope.$broadcast('termIdChanged', newValue, oldValue);
                        } else {
                            GlobalVarsService.setUserId(response.userId);
                            $scope.userId = GlobalVarsService.getUserId;
                        }
                    }, function (error) {
                        console.log('Error while checking if term is open for registration', error);
                        $scope.studentIsEligibleForTerm = false;
                    });
                } else {
                    console.log('term eligibility check bypassed - term != default term');
                    $scope.studentIsEligibleForTerm = true;

                    // Broadcast a termIdChanged event notifying any listeners that the new termId is ready to go.
                    $scope.$broadcast('termIdChanged', newValue, oldValue);
                }
            }
        });

        // Listen for the termIdChanged event that is fired when a term has been changed & processed
        $scope.$on('termIdChanged', function(event, newValue) {
            // Go and get the schedule for the new term
            ScheduleService.getSchedule(newValue).then(function (result) {
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
        });

        // Load up the available terms
        TermsService.getTerms().then(function (terms) {
            $scope.terms = terms;
            $scope.termId = DEFAULT_TERM;
            $scope.termName = TermsService.getTermNameForTermId(terms, $scope.termId);
        });

        // Load up the messages
        MessageService.getMessages().then(function(messages) {
            $scope.messages = messages;
        });


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
                templateUrl: 'sessionExpired.html',
                controller: 'SessionCtrl',
                size: 'sm'
            });
        });

        //Update the UI routing state so it is available in the scope.
        $scope.$parent.uiState = $state.current.name;
        $scope.$on('$stateChangeStart', function(event, toState) {
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
