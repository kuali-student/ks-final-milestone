'use strict';

angular.module('regCartApp')

    .directive('sticky', ['$timeout', '$window', '$document', function($timeout, $window, $document) {
        return {
            restrict: 'CA',
            scope: {
                offset: '@', // Offset from the top of page to stick the element
                minWidth: '@', // Min-width (px) of the page to make the element sticky
                maxWidth: '@' // Max-width (px) of the page to make the element sticky
            },
            link: function(scope, elem) {
                $timeout(function() {
                    var offset = scope.offset || 0,
                        minWidth = scope.minWidth || -1,
                        maxWidth = scope.maxWidth || -1,
                        w = angular.element($window),
                        d = angular.element($document),
                        stickyLine = elem.offset().top - offset, // Scroll position to stick at
                        initialOffset = elem.offset().top,
                        placeholder,
                        isStuck = false;

                    // Add the base class to the element
                    elem.addClass('util-sticky');



                    function check () {
                        // Enforce the width constraints
                        var width = w.outerWidth();
                        if ((minWidth > 0 && width < minWidth) || (maxWidth > 0 && width > maxWidth)) {
                            if (isStuck) {
                                console.log('Unsticking - width constraints met: ' + minWidth + ' < ' + width + ' < ' + maxWidth);
                                unstick();
                            }

                            return;
                        }


                        var scrollTop = (w.pageYOffset || d.scrollTop()) - (d.clientTop || 0),
                            shouldStick = (scrollTop >= stickyLine);

                        if (!isStuck && shouldStick) {
                            // console.log('Stick it!', d.scrollTop(), stickyLine, stick);
                            stick();
                        } else if (isStuck && !shouldStick) {
                            // console.log('Unstick it!', d.scrollTop(), stickyLine, stick);
                            unstick();
                        } else if (elem.isStuck && shouldStick) {
                            // console.log('Still stuck', scrollTop, stickyLine);
                        } else {
                            // console.log('Still unstuck', scrollTop, stickyLine);
                        }
                    }

                    function stick () {
                        // Insert a placeholder element before the sticky element.
                        // The placeholder prevents the page height from shifting when we pop it out of the DOM flow.
                        if (!placeholder) {
                            placeholder = angular.element('<div class="util-sticky-placeholder"></div>');
                        }

                        placeholder.css('height', elem.outerHeight() + 'px');
                        placeholder.insertBefore(elem);

                        // Add the stuck attributes
                        elem.css('top', offset + 'px');
                        elem.addClass('util-sticky--stuck');

                        isStuck = true;
                    }

                    function unstick () {
                        // The placeholder is no longer needed
                        if (placeholder) {
                            placeholder.remove();
                            placeholder = null;
                        }

                        // Remove the stuck attributes
                        elem.css('top', initialOffset + 'px');
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
