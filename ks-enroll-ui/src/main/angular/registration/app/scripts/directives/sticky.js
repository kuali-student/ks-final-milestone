'use strict';

angular.module('regCartApp')

    /**
     * Sticky directive causes elements to stick at the top of the page when the user scrolls past it.
     *
     * @example <div sticky></div>
     * @example <div class="sticky">abcd</div>
     */
    .directive('sticky', ['$timeout', '$window', '$document', function($timeout, $window, $document) {
        return {
            restrict: 'CA',
            scope: {
            },
            link: function(scope, elem) {
                $timeout(function() {
                    var w = angular.element($window),
                        d = angular.element($document),
                        stickyLine = elem.offset().top, // Scroll position to stick at
                        placeholder,
                        isStuck = false;

                    // Add the base class to the element
                    elem.addClass('util-sticky');


                    /**
                     * Check and sticky/unstick the element depending on the element & scroll position.
                     */
                    function check () {
                        var scrollTop = ($window.pageYOffset || d.scrollTop()) - (d.clientTop || 0), // Calculate the top of the screen
                            shouldStick = (scrollTop >= stickyLine); // The element should be stuck if its top is below the 'stickyLine'.

                        if (!isStuck && shouldStick) {
                            // Element is not stuck but should be.
                            // console.log('Stick it!', d.scrollTop(), stickyLine, stick);
                            stick();
                        } else if (isStuck && !shouldStick) {
                            // Element is stuck but shouldn't be.
                            // console.log('Unstick it!', d.scrollTop(), stickyLine, stick);
                            unstick();
                        } else if (isStuck && shouldStick) {
                            // Element is stuck & should be, nothing to do.
                            // console.log('Still stuck', scrollTop, stickyLine);
                        } else {
                            // Element is not stuck & shouldn't be, nothing to do.
                            // console.log('Still unstuck', scrollTop, stickyLine);
                        }
                    }

                    /**
                     * Make the element sticky on the page.
                     */
                    function stick () {
                        // Insert a placeholder element before the sticky element.
                        // The placeholder prevents the page height from shifting when we pop it out of the DOM flow.
                        if (!placeholder) {
                            placeholder = angular.element('<div class="util-sticky-placeholder"/>');
                        }

                        placeholder.css('height', elem.outerHeight() + 'px');
                        placeholder.insertBefore(elem);

                        // Add the stuck class
                        elem.addClass('util-sticky--stuck');

                        isStuck = true;
                    }

                    /**
                     * Return the element to its pre-sticky state.
                     */
                    function unstick () {
                        // The placeholder is no longer needed
                        if (placeholder) {
                            placeholder.remove();
                            placeholder = null;
                        }

                        // Remove the stuck class
                        elem.removeClass('util-sticky--stuck');

                        isStuck = false;
                    }



                    // Recheck the stickiness when the user scrolls or resizes the window
                    w.on('scroll', check);
                    w.on('resize', function() {
                        $timeout(check);
                    });

                    check();


                    // Cleanup when the element is being destroyed
                    elem.bind('$destroy', function() {
                        if (placeholder) {
                            placeholder.remove();
                            placeholder = null;
                        }
                    });
                });
            }
        };
    }]);
