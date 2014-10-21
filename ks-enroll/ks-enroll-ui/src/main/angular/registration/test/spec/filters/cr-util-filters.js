'use strict';

describe('Filters: CR-Util-Filters -', function() {

    // load the module
    beforeEach(module('regCartApp'));

    var filter;


    describe('CourseSpace', function() {
        // instantiate the filter
        beforeEach(inject(function(courseSpaceFilter) {
            filter = courseSpaceFilter;
        }));

        it('should space out a course code', function() {
            // Default space ' '
            expect(filter('CHEM101')).toBe('CHEM 101');
            expect(filter('ENGL101A')).toBe('ENGL 101A');

            // Custom space
            expect(filter('CHEM101', '&#8203;')).toBe('CHEM&#8203;101');
            expect(filter('CHEM101', '-space-')).toBe('CHEM-space-101');
        });
    });


    describe('Cleanse', function() {
        // instantiate the filter
        beforeEach(inject(function(cleanseFilter) {
            filter = cleanseFilter;
        }));

        it('should cleanse out the spaces from a string', function() {
            expect(filter('abcd')).toBe('abcd');
            expect(filter('ab cd')).toBe('abcd');
            expect(filter(' a b c d ')).toBe('abcd');
            expect(filter(null)).toBeNull();
            expect(filter(true)).toBeTruthy();
            expect(filter(false)).toBeFalsy();
        });
    });


    describe('Ordinal', function() {
        // instantiate the filter
        beforeEach(inject(function(ordinalFilter) {
            filter = ordinalFilter;
        }));

        it('should handle non-numbers elegantly', function() {
            // This should be GIGO (Garbage-in, garbage-out)
            var data = 'junk';
            expect(filter(data)).toBe(data);

            data = {};
            expect(filter(data)).toBe(data);

            data = [];
            expect(filter(data)).toBe(data);
        });

        it('should properly format numbers', function() {
            var suffixes = ['th', 'st', 'nd', 'rd', 'th'];

            for (var i = 1; i < 10000; i++) {
                var suffix,
                    n = i % 100;

                if (n < 4) {
                    suffix = suffixes[n];
                } else if (n < 20) {
                    suffix = suffixes[0];
                } else {
                    n %= 10;
                    if (n < 4) {
                        suffix = suffixes[n];
                    } else {
                        suffix = suffixes[0];
                    }
                }

                expect(filter(i)).toBe(i + suffix);
            }
        });
    });


    describe('MultiplicativeAdverb', function() {
        // instantiate the filter
        beforeEach(inject(function(multiplicativeAdverbFilter) {
            filter = multiplicativeAdverbFilter;
        }));

        it('should handle non-numbers elegantly', function() {
            // This should be GIGO (Garbage-in, garbage-out)
            var data = 'junk';
            expect(filter(data)).toBe(data);

            data = {};
            expect(filter(data)).toBe(data);

            data = [];
            expect(filter(data)).toBe(data);
        });

        it('should properly format numbers and append the suffix', function() {
            var suffix = ' textSuffix';

            expect(filter(1)).toBe('once');
            expect(filter(1, suffix)).toBe('once');

            expect(filter(2)).toBe('twice');
            expect(filter(2, suffix)).toBe('twice');

            // Sadly... not using thrice
            // expect(filter(3)).toBe('thrice');
            // expect(filter(3, suffix)).toBe('thrice');

            for (var i = 3; i < 10; i++) {
                expect(filter(i)).toBe(i);
                expect(filter(i, suffix)).toBe(i + suffix);
            }
        });
    });


    describe('HumanizeRegGroupState', function() {
        var STATE;

        // instantiate the filter
        beforeEach(inject(function(humanizeRegGroupStateFilter, _STATE_) {
            filter = humanizeRegGroupStateFilter;
            STATE = _STATE_;
        }));

        it('should humanize the states', function() {
            // Base Cases - Various data types
            expect(filter()).toBe('not offered');
            expect(filter(null)).toBe('not offered');
            expect(filter(false)).toBe('not offered');
            expect(filter('abcdef')).toBe('not offered');
            expect(filter([])).toBe('not offered');
            expect(filter({})).toBe('not offered');
            expect(filter(STATE.lui)).toBe('not offered');

            expect(filter(STATE.lui.canceled)).toBe('cancelled');
            expect(filter(STATE.lui.invalid)).toBe('does not exist');
            expect(filter(STATE.lui.offered)).toBe('offered');
            expect(filter(STATE.lui.pending)).toBe('not offered');
            expect(filter(STATE.lui.suspended)).toBe('suspended');
        });
    });
});
