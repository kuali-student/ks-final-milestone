'use strict';

angular.module('kscrPocApp')
  .controller('AppScheduleCtrl', function ($scope, scheduleService, config) {
    $scope.schedule = scheduleService.query({ person: config.userId, termCode: $scope.searchCriteria.termCode });
  });
