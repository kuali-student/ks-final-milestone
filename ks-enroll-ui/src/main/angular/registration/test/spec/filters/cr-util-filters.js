'use strict';

describe('Filters: CR-Util-Filters -', function() {

    // load the module
    beforeEach(module('regCartApp'));

    var filter;


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
});
