'use strict';

angular.module('kscrPocApp')
  .controller('AppScheduleCtrl', function ($scope, scheduleService, calendarService, config) {
    $scope.schedules = scheduleService.query({ userId: config.userId });
    $scope.events = calendarService.query({ userId: config.userId, termCode: $scope.searchCriteria.termCode });
  });
