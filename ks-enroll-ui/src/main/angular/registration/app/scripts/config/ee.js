'use strict';

/**
 * Yarr, super-secret easter eggs be here.
 */


/**
 * These easter eggs are baked into the search form, but can easily be removed.
 * They have been added to illustrate how an implementing institution could customize / override default behavior.
 * This was a critical concern so we felt it necessitated adding it to our reference code. to remove,
 * just remove ee.js line in index.html
 */
angular.module('regCartApp')
    .config(['$provide', function($provide) {
        $provide.decorator('searchFormDirective', ['$delegate', '$animate', function($delegate, $animate) {

            function barrelRoll() {
                var target = angular.element('body'),
                    cls = 'util-transitions-barrelRoll',
                    wobble = 'util-transitions-wobble';

                // Make the body do a barrel roll
                $animate.addClass(target, cls, function() {
                    $animate.removeClass(target, cls); // Remove the class once it's done so it can be reapplied

                    // Make any course calendar items wobble after the barrel roll. They're probably a little motion sick.
                    target.find('course-calendar-item').each(function() {
                        var el = angular.element(this);
                        $animate.addClass(el, wobble, function() {
                            $animate.removeClass(el, wobble);
                        });
                    });
                });
            }

            function doctorWho() {
                var body = angular.element('body'),
                    target = angular.element('<div/>'),
                    cls = 'util-transitions-doctorWho';

                target.append(
                    '<div class="' + cls + '-tardis"/>' +
                    '<div class="' + cls + '-dalek"/>');

                body.append(target);

                $animate.addClass(target, cls, function() {
                    target.remove();
                });
            }




            var directive = $delegate[0];

            var link = directive.link;
            directive.compile = function() {
                return function(scope) {
                    if (angular.isFunction(link)) {
                        link.apply(this, arguments);
                    }

                    scope.$watch('courseSearchCriteria', function(criteria) {
                        if (angular.isString(criteria)) {
                            criteria = criteria.toLowerCase();

                            if (criteria.indexOf('do a barrel') !== -1) {
                                barrelRoll();
                            } else if (criteria.indexOf('exterminate') !== -1) {
                                doctorWho();
                            }
                        }
                    });
                };
            };

            return $delegate;
        }]);
    }]);
