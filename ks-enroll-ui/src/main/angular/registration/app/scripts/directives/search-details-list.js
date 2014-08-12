'use strict';

angular.module('regCartApp')

    /*
     * This directive is used to display a mobile view of search details. Details are shown
     * in a tabular format, which allows users to view the details that are relevant to
     * them. An "all" tab is also available for viewing all the results together.
     *
     * Event Handling
     * -- Emits: "toggleAO" -- this alerts the search details controller to select/deselect the ao
     * -- Broadcasts: none
     * -- Receives: none
     *
     */
    .directive('searchDetailsList', ['$timeout', '$animate', function($timeout, $animate) {
        return {
            restrict: 'E',
            templateUrl: 'partials/searchDetailsList.html',
            scope: {
                searchDetails: '=', // an array of search details to show
                singleRegGroup: '=' // a boolean determining if this is a single reg group or not
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

                // turn off ng-repeat animations for better performance
                $animate.enabled(false, angular.element(document.querySelector('.kscr-Search-details-grid')));

                // initialize the tabs
                scope.time = true;
                scope.instr = false;
                scope.seatsLoc = false;
                scope.tab = 'time';

                // this method selects a tab and sets the display flags accordingly
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

                // if a row is selected, emit a "toggleAO" event to the search details controller
                scope.selectRow = function(searchDetail) {
                    if (!scope.singleRegGroup) {
                        scope.$emit('toggleAO', searchDetail);
                    }
                };

                // Displays the table in batches for performance
                var stagger = 20;
                scope.limit = stagger;
                scope.$watch('limit', function() {
                    if (scope.limit < scope.searchDetails.length) {
                        $timeout(function() {
                            scope.limit += stagger;
                        }, 250);
                    }
                });
            }
        };
    }])

;