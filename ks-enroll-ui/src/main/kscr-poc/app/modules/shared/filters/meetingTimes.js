'use strict';

angular.module('kscrPocApp')
  .filter('meetingTimes', function ($sce, daysFilter, timeRangeFilter) {
    return function (input) {
      // Input has to exist.
      if( !input ) {
        return;
      }
      return $sce.trustAsHtml([daysFilter(input), timeRangeFilter(input)].join(' '));
    };
  });
