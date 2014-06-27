'use strict';

/*
This is a collection of smaller directives used for course registration
 */
angular.module('regCartApp')

    /*
    This directive puts focus on an element, and refocuses if there is blur.
    It should be used with care -- primarily for allowing ENTER to target a particular button
     */
    .directive('focusMe', function($timeout, $parse) {
        return {
            link: function(scope, element, attrs) {
                var model = $parse(attrs.focusMe);
                scope.$watch(model, function(value) {
                    if(value === true) {
                        $timeout(function() {
                            element[0].focus();
                        });
                    }
                });
                element.bind('blur', function() {
                    $timeout(function() {
                        element[0].focus();
                    });
                });
            }
        };
    })

    /*
     This directive puts focus on an element, but allows for blur.
     */
    .directive('focusOnce', function($timeout, $parse) {
        return {
            link: function(scope, element, attrs) {
                var model = $parse(attrs.focusOnce);
                scope.$watch(model, function(value) {
                    if(value === true) {
                        $timeout(function() {
                            element[0].focus();
                        });
                    }
                });
            }
        };
    })

    /*
    This directive puts the drop menu on any page. The menu will hide itself if
    resized (based on the dropMenu scope variable)
     */
    .directive('dropMenu', function($window) {

        return {
            controller: function ($scope) {
                return angular.element($window).bind('resize', function() {
                    // if the menu is open while the window is resized, close it
                    if ($scope.dropMenu === true) {
                        $scope.dropMenu=false;
                    }
                });
            },
            templateUrl: 'dropMenu.html'
        };
    })

;