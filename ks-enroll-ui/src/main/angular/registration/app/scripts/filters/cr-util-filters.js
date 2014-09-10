'use strict';

/**
 * Utility filters for course registration
 */
angular.module('regCartApp')

/**
 * Take a course code in the format of ENGL101A and put a space in it to be ENGL 101A.
 *
 * Usage: {{ courseCode | courseSpace }}
 *        {{ courseCode | courseSpace:'&#8203;' }} // Non-breaking space
 */
    .filter('courseSpace', function() {
        return function (courseCode, space) {
            var prefix = courseCode.match(/^[a-zA-Z]+/),
                level = courseCode.replace(prefix, '');

            return prefix + (space || ' ') + level;
        };
    })

/**
 * Cleanses a string by removing spaces
 *
 * Usage: {{ 'a b c d' | cleanse }} = 'abcd'
 */
    .filter('cleanse', function() {
        return function(value) {
            var cleansed;
            if (angular.isString(value)) {
                cleansed = value.replace(/ /g, '');
            } else {
                cleansed = value;
            }
            return cleansed;
        };
    })

/**
 * Transform a number to an ordinal number (e.g. 1st, 2nd, 3rd)
 *
 * Usage: {{ 5 | ordinal }} = '5th'
 */
    .filter('ordinal', function() {
        return function(num) {
            if (!angular.isNumber(num)) {
                return num;
            }

            var n = num % 100,
                suffixArr = ['th', 'st', 'nd', 'rd', 'th'],
                ord = n < 21 ? (n < 4 ? suffixArr[n] : suffixArr[0]) : (n % 10 > 4 ? suffixArr[0] : suffixArr[n % 10]);

            return num + ord;
        };
    })

/**
 * Transform a number to a multiplicative adverb (once, twice, thrice)
 *
 * Usage:
 *  {{ 1 | multiplicativeAdverb }} = 'once'
 *  {{ 2 | multiplicativeAdverb }} = 'twice'
 *  {{ 5 | multiplicativeAdverb : ' times' }} = '5 times'
 *  $filter('multiplicativeAdverb')(5, ' times') = '5 times'
 *
 * @param num Number to humanize
 * @param nonMultiplicativeSuffix Suffix to append to the number if it has no multiplicative format
 */
    .filter('multiplicativeAdverb', function() {
        return function(num, nonMultiplicativeSuffix) {
            if (!angular.isNumber(num)) {
                return num;
            }

            var h = num;
            switch (num) {
                case 1:
                    h = 'once';
                    break;
                case 2:
                    h = 'twice';
                    break;
                // case 3: h = 'thrice'; break; // Don't use thrice

                default:
                    if (nonMultiplicativeSuffix) {
                        h = num + nonMultiplicativeSuffix;
                    }
                    break;
            }


            return h;
        };
    })
;
