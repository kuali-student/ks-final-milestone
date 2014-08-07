'use strict';

angular.module('regCartApp')

    .directive('searchDetailsList', ['$timeout', '$animate', function($timeout, $animate) {
        return {
            restrict: 'E',
            templateUrl: 'partials/searchDetailsList.html',
            scope: {
                searchDetails: '=',
                singleRegGroup: '='
            },
            link:function(scope) {

                // reorganize the aos by type
                scope.aoTypes=[];
                for (var i=0; i<scope.searchDetails.length; i++) {
                    var activity=scope.searchDetails[i].activityOfferingTypeName;
                    var newAoType = true;
                    for (var j=0; j<scope.aoTypes.length; j++) {
                        if (scope.aoTypes[j].activity === activity) {
                            newAoType = false;
                            scope.aoTypes[j].aos.push(scope.searchDetails[i]);
                            break;
                        }
                    }
                    if (newAoType) {
                        var aoTypeContainer={activity: activity, aos: []};
                        aoTypeContainer.aos.push(scope.searchDetails[i]);
                        scope.aoTypes.push(aoTypeContainer);
                    }
                }

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
                    if (!scope.singleRegGroup) {
                        scope.$emit('toggleAO', searchDetail);
                    }
                };

                // Displays the table in batches for performance
                var stagger = 20;
                scope.limit = 0;
                scope.$watch('limit', function() {
                    if (scope.limit < scope.searchDetails.length) {
                        $timeout(function() {
                            scope.limit += stagger;
                        });
                    }
                });
            }
        };
    }])

;