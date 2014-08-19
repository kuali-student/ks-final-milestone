'use strict';

angular.module('regCartApp')
    .controller('MainCtrl', ['$scope', '$location', '$state', '$modal', 'APP_URL', 'DEFAULT_TERM',
        'GlobalVarsService', 'LoginService', 'TermsService', 'ScheduleService', 'CartService', 'MessageService',
    function MainCtrl($scope, $location, $state, $modal, APP_URL, DEFAULT_TERM, GlobalVarsService, LoginService, TermsService, ScheduleService, CartService, MessageService) {
        console.log('>> MainCtrl');

        $scope.appUrl = APP_URL.replace('/services/', '/');

        $scope.userId = GlobalVarsService.getUserId;

        $scope.terms = [];
        $scope.term = TermsService.getSelectedTerm(); // Globally used selected term
        $scope.studentIsEligibleForTerm = true; // Top-level check whether student is eligible to register for the selected term


        // Load up the available terms
        TermsService.getTerms().then(function(terms) {
            $scope.terms = terms;

            // Make sure the app is sync'd up with the state now that the terms are available
            syncWithState();
        });


        // Persist the new term value in the state when it is changed
        $scope.$watch('term', function (newValue, oldValue) {
            if (newValue && newValue !== oldValue) {
                console.log('Term Changed: ' + newValue.termId + ', was ' + (oldValue ? oldValue.termId : null));
                persistTermInRoute(newValue);
            }
        });

        // Listen for any state changes from ui-router. This is where the termId is persisted from.
        $scope.$on('$stateChangeSuccess', function() {
            syncWithState();
        });


        // Listen for the termIdChanged event that is fired when a term has been changed & processed
        $scope.$on('termIdChanged', function(event, newValue) {
            // Go and get the schedule for the new term
            ScheduleService.getSchedule(newValue).then(function (result) {
                console.log('called rest service to get schedule data - in main.js');
                ScheduleService.updateScheduleCounts(result);
                $scope.cartCredits = CartService.getCartCredits;
                $scope.cartCourseCount = CartService.getCartCourseCount;
                $scope.registeredCredits = ScheduleService.getRegisteredCredits;
                $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount;
                $scope.waitlistedCredits = ScheduleService.getWaitlistedCredits;
                $scope.waitlistedCourseCount = ScheduleService.getWaitlistedCourseCount;
                $scope.userId = GlobalVarsService.getUserId;
            });
        });

        // Load up the messages
        MessageService.getMessages();

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

        // Update the UI routing state so it is available in the scope.
        $scope.$parent.uiState = $state.current.name;
        $scope.$on('$stateChangeStart', function(event, toState) {
            $scope.$parent.uiState = toState.name;
        });

        // Determine whether the search form should be shown on this page
        $scope.searchForm = function() {
            var searchForm = true;
            if ($state.current.name === 'root.search.details') {
                searchForm = false; // hidden from mobile search details
            }
            return searchForm;
        };


        // Push the term value into the state parameters
        function persistTermInRoute(term, replace) {
            if (!term) {
                return;
            }

            if ((angular.isUndefined($state.params.term) || $state.params.term !== term.termCode)) {
                // The term needs to be persisted in the state.
                console.log('Persisting term in state: ' + term.termCode);

                // Put the new term in the state parameters
                var params = angular.copy($state.params); // Copy so it sees it as a new object
                params.term = term.termCode;

                var options = { notify: false };
                if (replace) {
                    options.location = 'replace';
                }

                // Push the new term into the route
                $state.go($state.current.name, params, options).then(function() {
                    // Sync up with the new term
                    syncWithTerm(term);
                });
            }
        }

        function syncWithState() {
            var term = null;

            if (angular.isDefined($state.params.term)) {
                // Try to load the term from the state
                term = TermsService.getTermByCode($state.params.term);
            }

            if (term === null) { // The state term is not a known term
                if ($scope.term) {
                    // Persist the already selected term (most likely from the user monkeying around with the URL || having an old term bookmarked)
                    term = $scope.term;
                } else {
                    // Persist the default term
                    term = TermsService.getTermById(DEFAULT_TERM);
                }

                persistTermInRoute(term, true);
            } else if ($scope.term !== term) {
                syncWithTerm(term);
            }
        }

        // Sync the app with a term
        function syncWithTerm(term) {
            if (term !== null) {
                var oldTermId = $scope.term ? $scope.term.termId : null; // Store off the previous termId

                console.log('Syncing with term: ' + term.termId);

                // Persist the term in the TermsService
                TermsService.setSelectedTerm(term);
                $scope.term = term;

                // Check to see if the term is open or closed for registration
                console.log('- Checking term eligibility');
                TermsService.isStudentEligibleForTerm(term).then(function(isEligible, response) {
                    $scope.studentIsEligibleForTerm = isEligible;

                    // Broadcast a termIdChanged event notifying any listeners that the new termId is ready to go.
                    // Doing it this way prevents unnecessary loading & processing from the term change
                    // of things the user won't have access to or see.
                    if ($scope.studentIsEligibleForTerm) {
                        $scope.$broadcast('termIdChanged', term.termId, oldTermId);
                    } else if (response) {
                        GlobalVarsService.setUserId(response.userId);
                    }
                }, function(error) {
                    console.log('Error while checking if term is open for registration', error);
                    $scope.studentIsEligibleForTerm = false;
                });
            }
        }
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
