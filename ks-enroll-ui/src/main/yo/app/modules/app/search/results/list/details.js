'use strict';

angular.module('kscrPocApp')
  .controller('AppSearchResultsListDetailsCtrl', function ($scope, $state, $stateParams, pagingService, regGroupService, config) {

    // Initiates during normal app use.
    initiate();

    // Initiates during deep linking.
    $scope.$on('searchResultsChanged', function() {
      initiate();
    });

    function initiate() {

      //
      // Get this Course Offering and primary Activity Offering.
      // 

      var paging = pagingService.get('primaryActivityOffering');

      $scope.item = paging.item($stateParams.index);

      // If the item hasn't been found, then nothing can be done.
      if( $scope.item === null ) {
        return;
      }

      //
      // Update page title.
      //

      var state = $state.current;
      state.data.title = $scope.item.courseOfferingCode;
      $scope.$emit('updateStateTitle', state);

      //
      // Grading options.
      //

      $scope.additionalGradingOptions = [];

      if( $scope.item.auditCourse ) {
        $scope.additionalGradingOptions.push('Audit');
      }
      if( $scope.item.studentSelectablePassFail ) {
        $scope.additionalGradingOptions.push('Pass/Fail');
      }

      $scope.hasAdditionalGradingOptions = $scope.additionalGradingOptions.length > 0;

      //
      // Sibling navigation.
      //

      $scope.previousItem = paging.previous($stateParams.index);
      $scope.nextItem = paging.next($stateParams.index);
      $scope.hasPreviousItem = $scope.previousItem !== null;
      $scope.hasNextItem = $scope.nextItem !== null;

      //
      // Secondary Activity Offering selection.
      //

      var params = {
        termId: $scope.searchCriteria.termId,
        courseCode: $scope.item.courseOfferingCode
      };
      var aoId = $scope.item.activityOfferingId;

      // Since there's only one AO per Activity Type,
      // match to keys for easy, immediate access.
      $scope.selectedAOsByActivityType = {};

      // Gather first round of secondary Activity Offering data.
      regGroupService.get(params, aoId).then(function(result) {
        $scope.regGroups = result;
        $scope.updateSelections();
      });

      // A secondary Activity Offering was selected.
      $scope.updateSelections = function() {

        // Build a model for displaying a summary of the selections.
        var selectionSummary = [];
        angular.forEach($scope.regGroups, function(aoType) {
          var ao = $scope.selectedAOsByActivityType[aoType.name];
          selectionSummary.push({
            typeName: aoType.name,
            ao: ao,
            hasSelected: angular.isDefined(ao)
          });
        })
        $scope.selectionSummary = selectionSummary;

        // Transform selected Activity Offering Ids
        // from their Activity Type keys into an array.
        var selectedAOIds = [];
        angular.forEach($scope.selectedAOsByActivityType, function(ao) {
          selectedAOIds.push(ao.activityOfferingId);
        });
        selectedAOIds.push(aoId);

        // Recheck selections against the raw data.
        regGroupService.getMatchingRegGroupId(params, selectedAOIds).then(function(result) {
          $scope.hasSelectedRegGroupId = angular.isString(result);
          $scope.selectedRegGroupId = result;
          if( $scope.hasSelectedRegGroupId ) {
            console.log('selected reg group', result);
          }
        });
      };

    }

    //
    // Registration
    //

    $scope.register = function() {
      // Make sure there's a Reg Group Id to register.
      if( !$scope.hasSelectedRegGroupId ) {
        return;
      }
      regGroupService.register($scope.selectedRegGroupId, config.userId).success(function(data) {
        // Registration request successfully submitted.
        console.log('submitted registration for', $scope.selectedRegGroupId);
      });
    };

  });
