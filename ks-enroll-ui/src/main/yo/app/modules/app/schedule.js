'use strict';

angular.module('kscrPocApp')
  .controller('AppScheduleCtrl', function ($scope, scheduleService, calendarService, config) {
    $scope.schedule = scheduleService.query({ userId: config.userId, termCode: $scope.searchCriteria.termCode });
    $scope.events = calendarService.query({ userId: config.userId, termCode: $scope.searchCriteria.termCode });
  });
