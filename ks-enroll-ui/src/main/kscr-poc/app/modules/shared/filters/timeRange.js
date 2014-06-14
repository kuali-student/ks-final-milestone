'use strict';

angular.module('kscrPocApp')
  .filter('timeRange', function ($sce) {
    return function (input) {
      // 13:27 => 1:27pm
      return $sce.trustAsHtml([input.startTime, input.endTime].join('&ndash;'));
    };
  });
