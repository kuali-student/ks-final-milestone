'use strict';

angular.module('regCartApp')

    /*
    The search-column directive sets the columns to be displayed by search results with
    the following options:

    name (required) -- the user-friendly column name to be displayed
    field (required) -- the field name on the searchResult object to display
    filter (optional) -- a filter to be applied to the field
    order (optional) -- the order in which the column should display
    url (optional) -- converts the field into a link to the given url
     */
    .directive('searchColumn', ['$filter', function($filter) {
        return {
            restrict: 'E',
            require: '^searchResults',
            link:function(scope,element,attrs) {
                if (attrs.name || attrs.field || attrs.filter) {
                    var searchColumn = {name: attrs.name,
                                        field: attrs.field,
                                        filter: attrs.filter,
                                        order: attrs.order,
                                        url: attrs.url};
                    scope.searchColumns.push(searchColumn);
                    scope.searchColumns = $filter('orderBy')(scope.searchColumns, 'order');
                }
                if (attrs.field && attrs.default === 'true') {
                    scope.predicate = attrs.field;
                    scope.defaultField = attrs.field;
                }
            }
        };
    }])

    /*
     The search-list directive displays a generic list of search results based on the
     columns set by the search-column directive.
     */
    .directive('searchList', function() {
        return {
            restrict: 'E',
            require: '^searchResults',
            templateUrl: 'partials/searchList.html',
            link:function(scope,element,attrs) {
                if (attrs.detailsId) {
                    scope.detailsId = attrs.detailsId;
                }
            }
        };
    })

    /*
    The parent directive for search results. Can contain:

    -- search-column(s) -- defines the column(s) for display in the results
    -- search-list -- the actual list of results
     */
    .directive('searchResults', function() {
        return {
            restrict: 'E',
            controller: 'SearchResultsCtrl'
        };
    })

    .controller('SearchResultsCtrl', ['$scope', '$filter', function SearchResultsCtrl ($scope, $filter) {

        // the choices for limiting display of search results
        $scope.displayLimits = [20, 50, 100];

        // set the default display limit
        $scope.displayLimit = $scope.displayLimits[0];

        // set the default page #
        $scope.page = 1;

        // holds the last page
        $scope.lastPage = 1;

        // holds the customizable id for linking to search details
        $scope.detailsId = {};

        // holds the reverse for each column (true = reverse sort)
        $scope.reverseMap = {};

        // returns a the full range of pages
        $scope.pageRange = function() {
            var pageRange = [];
            for (var i = 1; i <= $scope.lastPage; i++) {
                pageRange.push(i);
            }
            return pageRange;
        };

        // if the display limit changes, reset the page to 1
        $scope.$watch('displayLimit', function() {
            $scope.page = 1;
        });

        // the range of search results being viewed
        $scope.displayRange = function(length) {
            var rangeMin = (($scope.page - 1) * $scope.displayLimit) + 1;
            var rangeMax = rangeMin + $scope.displayLimit - 1;
            if (rangeMax > length) {
                rangeMax = length;
            }
            // set the last page
            $scope.lastPage = Math.ceil (length / $scope.displayLimit);
            if ($scope.page > $scope.lastPage) {
                $scope.page = 1;
            }
            if (rangeMin === rangeMax) {
                return (rangeMax + ' of ' + length);
            } else {
                return (rangeMin + '-' + rangeMax + ' of ' + length);
            }
        };

        // apply the given filter name to the value (if it's an array)
        $scope.applyFilter = function(value, filterName) {
            if (angular.isArray(value) && filterName) {
                return $filter(filterName)(value);
            } else{
                return value;
            }
        };

        // returns the customizable id for the given search result
        $scope.getId = function(searchResult) {
            return searchResult[$scope.detailsId];
        };

        // returns the reverse order of the specified column
        $scope.getReverse = function(column) {
            initReverse(column); // initialize the column
            return $scope.reverseMap[column];
        };

        // switches the reverse order of the specified column
        $scope.switchReverse = function(column) {
            initReverse(column); // initialize the column
            $scope.reverseMap[column] = !$scope.reverseMap[column]; // switch it
            return $scope.reverseMap[column];
        };

        function initReverse(column) {
            if (angular.isUndefined($scope.reverseMap[column])) {
                $scope.reverseMap[column] = true; // set the default
            }
        }
    }])

;
