'use strict';

describe('Directive: Sticky', function () {

    // Initialize the module & configure the provider to provide our mock $window object
    beforeEach(module('regCartApp', function($provide) {
        $provide.value('$window', {
            document: $(window.document),
            pageYOffset: 0,
            scrollTo: function(x, y) {
                this.pageYOffset = y;
            }
        });
    }));


    var $compile,
        $timeout,
        $window,
        $document,
        scope,
        el,
        sandbox,
        stickyLine;

    beforeEach(inject(function(_$compile_, _$timeout_, _$window_, _$document_, $rootScope) {
        $compile = _$compile_;
        $timeout = _$timeout_;
        $window = _$window_;
        $document = _$document_;
        scope = $rootScope.$new();
    }));

    // Compile the directive prior to each test case
    beforeEach(function() {
        compileDirective();
    });

    // Clean up after each test case runs
    afterEach(function() {
        if (sandbox) {
            // Remove the sandbox if it was added to the body
            sandbox.remove();
            sandbox = null;
        }

        scope.$destroy();
    });


    function compileDirective() {
        el = angular.element('<div sticky><div>...</div></div>');
        sandbox = angular.element('<div/>');
        sandbox.append(el);

        // Add the sandbox element to the document body so it is available to the DOM in the directive
        angular.element($document[0].body).append(sandbox);

        // Compile the template & apply the scope
        $compile(sandbox)(scope);
        scope.$digest();

        $timeout.flush();

        stickyLine = el.offset().top;
    }

    function setScrollPosition(top) {
        $window.scrollTo(0, top);
        angular.element($window).triggerHandler('scroll'); // Fire the scroll event
    }

    function getPlaceholderElement() {
        return angular.element(sandbox[0].querySelector('.util-sticky-placeholder'))[0];
    }

    function isStuck() {
        return el.hasClass('util-sticky--stuck');
    }



    it('should correctly initialize', function() {
        expect(el.hasClass('util-sticky')).toBeTruthy();
        expect(isStuck()).toBeFalsy();
        expect(getPlaceholderElement()).toBeUndefined();
    });

    it('should make the element sticky when scrolled below its top', function() {
        expect(isStuck()).toBeFalsy();

        // Increment around the line and make sure it behaves as expected
        for (var i = stickyLine - 2; i < stickyLine + 2; i++) {
            setScrollPosition(i);

            if (i < stickyLine) {
                expect(isStuck()).toBeFalsy();
                expect(getPlaceholderElement()).toBeUndefined();
            } else {
                expect(isStuck()).toBeTruthy();
                expect(getPlaceholderElement()).toBeDefined();
            }
        }

        for (i = stickyLine + 2; i < stickyLine - 2; i--) {
            setScrollPosition(i);

            if (i < stickyLine) {
                expect(isStuck()).toBeFalsy();
                expect(getPlaceholderElement()).toBeUndefined();
            } else {
                expect(isStuck()).toBeTruthy();
                expect(getPlaceholderElement()).toBeDefined();
            }
        }
    });
});