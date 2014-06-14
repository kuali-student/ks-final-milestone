'use strict';

angular.module('kscrPocApp')
  .filter('days', function () {
    // These properties are expected from the service
    //var apiFormat = 'isSun,isMon,isTue,isWed,isThu,isFri,isSat'.split(',');
    var apiFormat = 'sun,mon,tue,wed,thu,fri,sat'.split(',');
    // This should probably always be 7.
    var dayCount = apiFormat.length;
    // Default formats
    var formats = {
      'short': {
        labels: 'Su,M,Tu,W,Th,F,Sa'.split(','),
        delimiter: ''
      },
      'medium': {
        labels: 'Sun,Mon,Tue,Wed,Thu,Fri,Sat'.split(','),
        delimiter: ', '
      },
      'long': {
        labels: 'Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday'.split(','),
        delimiter: ', '
      }
    };

    return function (input, formatType) {
      // Default format type
      formatType = angular.isDefined(formatType) ? formatType : 'short';
      // The intended format
      var format = formats[formatType];
      // List of all the day labels
      var output = [];
      // Loop through the days
      for( var i = 0; i < dayCount; i++ ) {
        // Add the appropriate day label as the boolean indicates
        if( input[ apiFormat[i] ] ) {
          output.push( format.labels[i] );
        }
      }
      // Combine the day labels with the appropriate delimiter
      return output.join( format.delimiter );
    };
  });
