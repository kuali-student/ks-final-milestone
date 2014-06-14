'use strict';

// Inspired by:
// https://github.com/andreev-artem/angular_experiments/tree/master/ui-responsive-width
angular.module("uiGuage", [])
  .directive('uiGauge', function ($parse, $window) {

    // Manually get the width, since we're not requiring jQuery
    // and using the element.width() method.
    function getWidth(element) {
      return element[0].offsetWidth;
    }

    return {
      restrict: 'A',
      link: function (scope, element, attrs) {
        // Get the initial width.
        var width = getWidth(element);
        // Grab the assignable function for this directive attribute.
        var setWidth = $parse(attrs.uiGauge).assign;
        // Assign the width on load.
        setWidth(scope, width);

        // Bind to window resize.
        angular.element($window).bind('resize', function() {
          // Get the new width.
          var newWidth = getWidth(element);
          // Update the model only if the width changed.
          if(newWidth != width) {
            // Save the new width within this directive.
            width = newWidth;
            // Save and announce the width outside the directive.
            setWidth(scope, width);
            scope.$apply();
          }
        });
      }
    };
  });