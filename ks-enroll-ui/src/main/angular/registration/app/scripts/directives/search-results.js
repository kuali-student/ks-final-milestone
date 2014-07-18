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
            templateUrl: 'partials/searchList.html',
            controller: 'SearchResultsCtrl'
        };
    })

    .controller('SearchResultsCtrl', ['$scope', '$filter', function SearchResultsCtrl ($scope, $filter) {

        // the choices for limiting display of search results
        $scope.displayLimits = [20, 50, 100];
//        $scope.displayLimits = [2, 3, 5]; //uncomment this for testing pagination with small data sets

        // set the default display limit
        $scope.displayLimit = $scope.displayLimits[0];

        // set the default page #
        $scope.page = 1;

        // holds the last page
        $scope.lastPage = 1;

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
            console.log('page starts as: '+$scope.page);
            console.log('last page starts as: '+$scope.lastPage);
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
        }
    }])

;
