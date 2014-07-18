'use strict';

angular.module('kscrPocApp')
  .filter('instructors', function () {
    return function (input) {
      var output = [];
      for( var i = 0, l = input.length; i < l; i++ ) {
        var instructor = input[i];
        output.push( [instructor.firstName, instructor.lastName].join(' ') );
      }
      return output.join(', ');
    };
  });
