'use strict';

angular.module('kscrPocApp')
  .controller('AppSearchResultsCtrl', function ($scope, $state, $stateParams, primaryActivityOfferingService) {

    // Override any defaults with those provided in the url.
    // This allows deep linking.
    $scope.searchCriteria.query = $stateParams.query;
    $scope.searchCriteria.termCode = $stateParams.termCode;

    // Build the params for the search.
    var params = {
      termCode: $stateParams.termCode,
      courseCode: $stateParams.query
    };

    // Search.
    $scope.results = primaryActivityOfferingService.query(params, function(results) {
      // Redirect appropriately.
      var hasResults = results.itemCount > 0;
      if( hasResults ) {
        $scope.$broadcast('searchResultsChanged');
      }
      else {
        $state.go('app.search.results.empty');
      }
    });

  });
