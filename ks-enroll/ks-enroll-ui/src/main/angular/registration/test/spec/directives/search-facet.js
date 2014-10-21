'use strict';

describe('Directive: SearchFacet', function () {

    // load the service's module, mock results, & template
    beforeEach(module('regCartApp', 'mockData', 'partials/searchFacet.html'));

    var $compile,
        scope,
        mockSearchResults,
        facet,
        el;


    beforeEach(inject(function(_$compile_, _$rootScope_, searchResults) {
        $compile = _$compile_;
        scope = _$rootScope_.$new();
        mockSearchResults = searchResults;

        facet = {
            label: 'DEFAULT_LABEL',
            id: 'DEFAULT_ID',
            optionsKey: 'coursePrefix'
        };
    }));


    function compileDirective(facetConfig, results) {
        if (!facetConfig) {
            facetConfig = facet;
        }

        if (!results) {
            results = mockSearchResults;
        }

        scope.facet = facetConfig;
        scope.results = results;

        var tpl = '<search-facet facet="facet" results="results"></search-facets>';

        // Compile the template and apply the scope
        el = $compile(tpl)(scope);
        scope.$digest();
    }


    describe('initialization', function() {
        it('should inject the appropriate html contents', function() {
            compileDirective();
            expect(el.html()).toContain('class="kscr-SearchFacet"');
            expect(el.html()).toContain('search_facet_DEFAULT_ID');
        });

        it('should cleanup and build out the facet configuration', function() {
            compileDirective();

            expect(facet.optionsProvider).toBeDefined();
            expect(angular.isFunction(facet.optionsProvider)).toBeTruthy();

            expect(facet.filter).toBeDefined();
            expect(angular.isFunction(facet.filter)).toBeTruthy();

            expect(facet.selectedOptions).toEqual([]);
        });

        it('should call the prepare() method on the facet if it is defined', function() {
            var fn = jasmine.createSpy();
            facet.prepare = fn;

            compileDirective();

            expect(fn).toHaveBeenCalledWith(el.isolateScope().results);
        });
    });

    describe('parsing & building options', function() {
        it('should handle simple options', function() {
            compileDirective();

            var options = el.isolateScope().options;
            expect(options.length).toBe(3);

            // Expect them to be in alphabetical order
            expect(options[0].value).toBe('BSCI');
            expect(options[0].label).toBe('BSCI');
            expect(options[0].count).toBe(1);

            expect(options[1].value).toBe('CHEM');
            expect(options[1].label).toBe('CHEM');
            expect(options[1].count).toBe(3);

            expect(options[2].value).toBe('ENGL');
            expect(options[2].label).toBe('ENGL');
            expect(options[2].count).toBe(3);
        });

        it('should handle array options', function() {
            facet.optionsKey = 'creditOptions';

            compileDirective();

            var options = el.isolateScope().options;
            expect(options.length).toBe(5);

            expect(options[0].value).toBe('1.0');
            expect(options[0].label).toBe('1.0');
            expect(options[0].count).toBe(2);

            expect(options[1].value).toBe('1.5');
            expect(options[1].label).toBe('1.5');
            expect(options[1].count).toBe(1);

            expect(options[2].value).toBe('2.0');
            expect(options[2].label).toBe('2.0');
            expect(options[2].count).toBe(1);

            expect(options[3].value).toBe('2.5');
            expect(options[3].label).toBe('2.5');
            expect(options[3].count).toBe(1);

            expect(options[4].value).toBe('3.0');
            expect(options[4].label).toBe('3.0');
            expect(options[4].count).toBe(6);
        });

        it('should handle a custom optionsProvider', function() {
            // This is pulled directly from the SeatsAvailable optionsProvider
            facet.optionsProvider = function(results) {
                var itemsWithSeats = 0;
                angular.forEach(results, function(item) {
                    if (item.seatsAvailable > 0) {
                        itemsWithSeats++;
                    }
                });

                var options = [];
                if (itemsWithSeats > 0) {
                    options.push({
                        label: 'Seats available',
                        value: 'seatsAvailable',
                        count: itemsWithSeats
                    });
                }

                return options;
            };

            compileDirective();

            var options = el.isolateScope().options;
            expect(options.length).toBe(1);

            expect(options[0].value).toBe('seatsAvailable');
            expect(options[0].label).toBe('Seats available');
            expect(options[0].count).toBe(5);
        });
    });

    describe('manipulating selected options', function() {
        it('should work', function() {
            compileDirective();

            var s = el.isolateScope(),
                option = { value: 'BSCI' };


            expect(s.hasSelected()).toBeFalsy();
            expect(s.isSelected(option)).toBeFalsy();

            s.toggleOption(option);
            expect(s.hasSelected()).toBeTruthy();
            expect(s.isSelected(option)).toBeTruthy();

            s.clearSelectedOptions();
            expect(s.hasSelected()).toBeFalsy();
            expect(s.isSelected(option)).toBeFalsy();
        });
    });

    describe('filtering', function() {
        function verifyFilter(option) {
            var s = el.isolateScope();
            s.toggleOption(option);
            s.$digest();

            angular.forEach(s.results, function(item) {
                var value = item[facet.optionsKey],
                    check;

                if (angular.isArray(value)) {
                    check = value.indexOf(option.value) !== -1;
                } else {
                    check = value === option.value;
                }

                expect(facet.filter(item, s.facet.selectedOptions)).toEqual(check);
            });
        }

        it('should handle a simple option', function() {
            compileDirective();

            // Select the filter
            var option = { value: 'BSCI' };
            verifyFilter(option);
        });

        it('should handle array options', function() {
            facet.optionsKey = 'creditOptions';

            compileDirective();

            // Select the filter
            var option = { value: '1.0' };
            verifyFilter(option);
        });

        it('should handle a custom filter', function() {
            // Basic courseCode match
            facet.filter = function(item) {
                return (item.courseCode === 'ENGL101');
            };

            compileDirective();

            angular.forEach(el.isolateScope().results, function(item) {
                expect(facet.filter(item, facet.selectedOptions)).toEqual(item.courseCode === 'ENGL101');
            });



            // Seats available filter
            facet.filter = function(item) {
                // Since there is only 1 option possible, there is no need to check against any selected facets.
                return item.seatsAvailable > 0;
            };

            compileDirective();

            angular.forEach(el.isolateScope().results, function(item) {
                expect(facet.filter(item, facet.selectedOptions)).toEqual(item.seatsAvailable > 0);
            });
        });
    });
});