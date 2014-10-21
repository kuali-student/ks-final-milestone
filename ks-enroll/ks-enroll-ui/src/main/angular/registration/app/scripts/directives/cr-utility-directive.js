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
    });

/*
This directive binds and compiles an HTML element. It can be used as a
replacement for ng-bind-html when you need to include a custom element
(e.g. ng-click).
 */
angular.module('regCartApp')
    .directive('compile', ['$compile', function ($compile) {
        return function(scope, element, attrs) {
            scope.$watch(
                function(scope) {
                    return scope.$eval(attrs.compile);
                },
                function(value) {
                    element.html(value);
                    $compile(element.contents())(scope);
                }
            );
        };
    }]);



// Common mask functionality
var maskUtils = {
    mask: function(targetElement, maskElement) {
        if (maskElement) {
            // Remove the mask element from its current parent
            maskElement.remove();
        } else {
            // Make sure the mask element has been created
            maskElement = angular.element('<div class="util-Mask"/>');
        }

        // Append the maskElement to the body
        targetElement.append(maskElement);
        targetElement.addClass('util-Mask--masked');

        return maskElement;
    },
    unmask: function(targetElement, maskElement) {
        if (!maskElement) {
            // Not masked
            return;
        }

        maskElement.remove();
        maskElement = null;

        targetElement.removeClass('util-Mask--masked');
    },
    resetZIndex: function(element, zIndex) {
        element.css('zIndex', zIndex);
    },
    updateZIndex: function(element, maskElement) {
        // Update the element's z-index to be higher than the mask. This assumes that the mask has a set zIndex.
        var zIndex = maskElement.css('zIndex'),
            initialZIndex = element.css('zIndex');

        element.css('zIndex', zIndex + 1);

        return initialZIndex;
    }
};


/**
 * Mask screen directive that masks the screen when the mask-screen value is true.
 * The parameter should be a boolean value.
 *
 * @example <div mask-screen="maskWhenTrue"></div>
 */
angular.module('regCartApp')
    .directive('maskScreen', ['$timeout', '$document', function($timeout, $document) {
        return {
            restrict: 'A',
            scope: {
                maskScreen: '='
            },
            link: function(scope, elem) {
                var targetElement = $document.find('body'),
                    maskElement = null,
                    isMasked = false,
                    initialZIndex;

                // Mask the screen
                function mask() {
                    maskElement = maskUtils.mask(targetElement);

                    // Bind up to unmask when the mask is clicked
                    maskElement.bind('click', function() {
                        unmask();
                    });

                    initialZIndex = maskUtils.updateZIndex(elem, maskElement);
                    isMasked = true;
                }

                // Unmask the screen
                function unmask() {
                    maskUtils.unmask(targetElement, maskElement);
                    maskUtils.resetZIndex(elem, initialZIndex);
                    isMasked = false;

                    // Update the scope.maskScreen value to be accurate.
                    $timeout(function() {
                        // This needs to be in a timeout in order for the parent scope to see the change.
                        scope.maskScreen = false;
                    });
                }

                // Toggle the mask
                function toggleMask(shouldMask) {
                    if (shouldMask && !isMasked) {
                        mask();
                    } else if (!shouldMask && isMasked) {
                        unmask();
                    }
                }

                // Listen for the maskScreen value to change and handle it accordingly
                scope.$watch('maskScreen', toggleMask);
                toggleMask(scope.maskScreen);

                // Cleanup when the element is being destroyed
                elem.bind('$destroy', unmask);
            }
        };
    }]);

/**
 * Mask when directive that masks an element when the mask-when value is true.
 * The parameter should be a boolean value.
 *
 * @example <div mask-when="maskWhenTrue"></div>
 */
angular.module('regCartApp')
    .directive('maskWhen', ['$timeout', function($timeout) {
        return {
            restrict: 'A',
            scope: {
                maskWhen: '='
            },
            link: function(scope, elem) {
                var maskElement = null,
                    isMasked = false;

                // Mask the screen
                function mask() {
                    maskElement = maskUtils.mask(elem);

                    // Bind up to unmask when the mask is clicked
                    maskElement.bind('click', function() {
                        unmask();
                    });

                    isMasked = true;
                }

                // Unmask the screen
                function unmask() {
                    maskUtils.unmask(elem, maskElement);
                    isMasked = false;

                    // Update the scope.maskWhen value to be accurate.
                    $timeout(function() {
                        // This needs to be in a timeout in order for the parent scope to see the change.
                        scope.maskWhen = false;
                    });
                }

                // Toggle the mask
                function toggleMask(shouldMask) {
                    if (shouldMask && !isMasked) {
                        mask();
                    } else if (!shouldMask && isMasked) {
                        unmask();
                    }
                }

                // Listen for the maskScreen value to change and handle it accordingly
                scope.$watch('maskWhen', toggleMask);
                toggleMask(scope.maskWhen);

                // Cleanup when the element is being destroyed
                elem.bind('$destroy', unmask);
            }
        };
    }]);

