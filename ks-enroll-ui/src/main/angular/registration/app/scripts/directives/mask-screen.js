'use strict';

angular.module('regCartApp')

    /**
     * Mask screen directive that masks the screen when the mask-screen value is true.
     * The parameter should be a boolean value.
     *
     * @example <div mask-screen="maskWhenTrue"></div>
     */
    .directive('maskScreen', ['$timeout', '$document', function($timeout, $document) {
        return {
            restrict: 'A',
            scope: {
                maskScreen: '='
            },
            link: function(scope, elem) {
                var isMasked = false,
                    maskElement,
                    initialZIndex;

                /**
                 * Mask the screen
                 */
                function mask() {
                    if (isMasked) {
                        // Already masked
                        return;
                    }

                    // Make sure the mask element has been created
                    if (!maskElement) {
                        maskElement = angular.element('<div class="util-Mask"/>');

                        // Bind up to unmask when the mask is clicked
                        maskElement.bind('click', function() {
                            unmask();
                        });
                    }

                    // Append the maskElement to the body
                    $document.find('body').append(maskElement);

                    // Update the current element's z-index to be higher than the mask. This assumes that the mask has a set zIndex.
                    var zIndex = maskElement.css('zIndex');
                    initialZIndex = elem.css('zIndex');
                    elem.css('zIndex', zIndex + 1);

                    isMasked = true;
                }

                /**
                 * Unmask the screen
                 */
                function unmask() {
                    // Destroy the mask element
                    if (maskElement) {
                        maskElement.remove();
                        maskElement = null;
                    }

                    // Reset the zIndex on the element
                    elem.css('zIndex', initialZIndex);

                    isMasked = false;

                    // Update the scope.maskScreen value to be accurate.
                    $timeout(function() {
                        // This needs to be in a timeout in order for the parent scope to see the change.
                        scope.maskScreen = isMasked;
                    });
                }


                // Listen for the maskScreen value to change and handle it accordingly
                scope.$watch('maskScreen', function(shouldMask) {
                    if (shouldMask && !isMasked) {
                        mask();
                    } else if (!shouldMask && isMasked) {
                        unmask();
                    }
                });

                // Mask the screen if it's not already
                if (scope.mask && !isMasked) {
                    mask();
                }


                // Cleanup when the element is being destroyed
                elem.bind('$destroy', unmask);
            }
        };
    }]);
