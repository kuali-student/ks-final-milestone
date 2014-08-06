'use strict';

angular.module('regCartApp')

    .directive('searchDetailsList', ['$timeout', '$animate', function($timeout, $animate) {
        return {
            restrict: 'E',
            templateUrl: 'partials/searchDetailsList.html',
            scope: {
                searchDetails: '='
            },
            link:function(scope) {
                $animate.enabled(false, angular.element(document.querySelector('.kscr-Search-details-grid')));

                scope.time = true;
                scope.instr = false;
                scope.seatsLoc = false;
                scope.tab = 'time';

                scope.select = function(tab) {
                    scope.tab = tab;
                    switch(tab) {
                        case 'time':
                            scope.time = true;
                            scope.instr = false;
                            scope.seatsLoc = false;
                            break;
                        case 'instr':
                            scope.time = false;
                            scope.instr = true;
                            scope.seatsLoc = false;
                            break;
                        case 'seatsLoc':
                            scope.time = false;
                            scope.instr = false;
                            scope.seatsLoc = true;
                            break;
                        case 'all':
                            scope.time = true;
                            scope.instr = true;
                            scope.seatsLoc = true;
                    }
                };

                scope.selectRow = function(searchDetail) {
                    scope.$emit("toggleAO", searchDetail);
                };

                // Displays the table in batches for performance
                var stagger = 20;
                scope.limit = 0;
                scope.$watch('limit', function() {
                    if (scope.limit < scope.searchDetails.length) {
                        $timeout(function() {
                            scope.limit += stagger;
                        })
                    }
                });
            }
        }
    }])

;