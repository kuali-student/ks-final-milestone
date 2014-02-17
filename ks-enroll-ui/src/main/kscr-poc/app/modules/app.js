'use strict';

angular.module('kscrPocApp')
  .controller('AppCtrl', function ($scope, $state, termsService, config) {
    // Default values
    $scope.searchCriteria = {
      termCode: config.termCode,
      query: config.query
    };
    // Toggle the visibility of the global search interface.
    $scope.showSearch = false;
    // Store the term object.
    $scope.selectedTerm = null;

    // Acquire all active terms.
    $scope.terms = termsService.query({ active: true }, function() {
      updateSelectedTerm($scope.searchCriteria.termCode);
    });

    // Because we want to select terms using a string model, instead
    // of an object, we need to watch for changes and store the full
    // object model for display.
    $scope.$watch('searchCriteria.termId', function(newValue) {
      updateSelectedTerm(newValue);
    });

    // `termCode` is just as much an authority as `termId` regarding context.
    // This is used most often in the URL, for deep linking.
    $scope.$watch('searchCriteria.termCode', function(newValue) {
      updateSelectedTerm(newValue);
    });

    $scope.query = function() {
      var params = {
        termCode: $scope.selectedTerm.termCode,
        query: $scope.searchCriteria.query
      };
      // Force a reload, even if the parameters haven't changed.
      $state.go('app.search.results.list', params, { reload: true });
    };

    function updateSelectedTerm(termIdOrCode) {
      // Ignore if the term data has yet to be returned,
      // or if the value is undefined.
      if( !angular.isArray($scope.terms) || angular.isUndefined(termIdOrCode) ) {
        return;
      }

      // Store the appropriate term data whenever a part of it changes.
      for( var i = 0, l = $scope.terms.length; i < l; i++ ) {
        var term = $scope.terms[i];
        if( term.termId === termIdOrCode || term.termCode === termIdOrCode ) {
          $scope.selectedTerm = term;
          $scope.searchCriteria.termId = term.termId;
          $scope.searchCriteria.termCode = term.termCode;
          return;
        }
      }
    }
  });
