'use strict';

angular.module('kscrPocApp')
  .controller('AppSearchResultsListCtrl', function ($scope, $state, pluralizeFilter) {
    
    // BUG
    // This redirect shouldn't be needed, but for whatever reason,
    // if you return to a state (i.e. a previous query),
    // it may redirect to the `list` state without a prompt.
    // In that case, redirect back to the appropriate state.
    if( $scope.results.itemCount === 0 ) {
      $state.go('app.search.results.empty');
      return;
    }

    // Update the page title to reflect the number of results.
    $scope.$on('searchResultsChanged', function() {
      var state = $state.current;
      var count = $scope.results.itemCount;
      state.data.title = [count, pluralizeFilter(count, 'result')].join(' ');
      $scope.$emit('updateStateTitle', state);
    });
  });
