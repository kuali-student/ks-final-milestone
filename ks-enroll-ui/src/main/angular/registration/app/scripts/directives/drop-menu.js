'use strict';

angular.module('regCartApp')

    .directive('dropMenu', function($window) {

        return {
            controller: function ($scope) {
                return angular.element($window).bind('resize', function() {
                    // if the menu is open while the window is resized, close it
                    if ($scope.dropMenu === true) {
                        $scope.dropMenu=false;
                    }
                });
            }
        };
    })

;