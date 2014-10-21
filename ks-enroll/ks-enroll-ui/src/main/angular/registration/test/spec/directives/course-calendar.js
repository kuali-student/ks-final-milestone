'use strict';

describe('Directive: CourseCalendar', function () {

    // load the service's module & template
    beforeEach(module('regCartApp', 'partials/courseCalendar.html'));

    var $compile,
        $timeout,
        rootScope,
        scope,
        el,
        isolateScope;


    beforeEach(inject(function(_$compile_, _$rootScope_, _$timeout_) {
        $compile = _$compile_;
        rootScope = _$rootScope_;
        scope = _$rootScope_.$new();
        $timeout = _$timeout_;
    }));


    function compileDirective(size) {
        var tpl = '<course-calendar size="' + (size || '') + '"></course-calendar>';

        // Compile the template and apply the scope
        el = $compile(tpl)(scope);
        scope.$digest();

        $timeout.flush();

        isolateScope = el.isolateScope();
    }


    describe('init', function() {
        it('should init the correct scope values', function() {
            compileDirective();

            // Visibility toggles
            expect(isolateScope.hideRegistered).toBeFalsy();
            expect(isolateScope.hideWaitlisted).toBeFalsy();
            expect(isolateScope.hideCart).toBeFalsy();

            // Time range (full array of hours from Midnight (0) - 11PM).
            expect(isolateScope.times.length).toBe(24);
            for (var i = 0; i < isolateScope.times.length; i++) {
                expect(isolateScope.times[i].getHours()).toBe(i);
            }

            // Visible Time range (expect 7am - 7pm by default)
            expect(isolateScope.visibleTimeRange).toEqual([ (7 * 60), (19 * 60) ]);

            expect(isolateScope.selectedCourse).toBeNull();
            expect(isolateScope.calendarSize).toBe('large');
        });

        it('should set the size', function() {
            compileDirective('small');
            expect(isolateScope.calendarSize).toBe('small');

            compileDirective('large');
            expect(isolateScope.calendarSize).toBe('large');

            compileDirective();
            expect(isolateScope.calendarSize).toBe('large');

            compileDirective('abcd');
            expect(isolateScope.calendarSize).toBe('large');
        });
    });

    describe('filters', function() {
        beforeEach(function() {
            compileDirective();
        });

        it('should apply the course filter', function() {
            isolateScope.hideRegistered = false;
            isolateScope.hideWaitlisted = false;
            isolateScope.hideCart = false;

            var reg = { type: 'REG' },
                wl = { type: 'WAIT' },
                cart = { type: 'CART' };

            // Base Case
            expect(isolateScope.courseFilter(reg)).toBeTruthy();
            expect(isolateScope.courseFilter(wl)).toBeTruthy();
            expect(isolateScope.courseFilter(cart)).toBeTruthy();

            // Reg Off
            isolateScope.hideRegistered = true;
            expect(isolateScope.courseFilter(reg)).toBeFalsy();
            expect(isolateScope.courseFilter(wl)).toBeTruthy();
            expect(isolateScope.courseFilter(cart)).toBeTruthy();

            // WL Off
            isolateScope.hideRegistered = false;
            isolateScope.hideWaitlisted = true;
            expect(isolateScope.courseFilter(reg)).toBeTruthy();
            expect(isolateScope.courseFilter(wl)).toBeFalsy();
            expect(isolateScope.courseFilter(cart)).toBeTruthy();

            // Cart Off
            isolateScope.hideWaitlisted = false;
            isolateScope.hideCart = true;
            expect(isolateScope.courseFilter(reg)).toBeTruthy();
            expect(isolateScope.courseFilter(wl)).toBeTruthy();
            expect(isolateScope.courseFilter(cart)).toBeFalsy();
        });

        it('should apply the time filter', function() {
            // Visible time range should default to 7am - 7pm
            for (var i = 0; i < isolateScope.times.length; i++) {
                var time = isolateScope.times[i],
                    hour = time.getHours();
                if (7 <= hour && hour <= 19) {
                    expect(isolateScope.timeFilter(time)).toBeTruthy();
                } else {
                    expect(isolateScope.timeFilter(time)).toBeFalsy();
                }
            }
        });
    });

    describe('course selection', function() {
        var course;

        function selectCourse(c) {
            scope.$broadcast('course-clicked', c || course);
        }

        beforeEach(function() {
            compileDirective();
            course = { id: '123', index: 1 };
        });

        it('should catch the course-clicked event and toggle the selected course', function() {
            expect(isolateScope.selectedCourse).toBeNull();

            // Select a course
            selectCourse();
            expect(isolateScope.selectedCourse).toBe(course);

            // Select a different course
            var course2 = { id: '456', index: 2 };
            selectCourse(course2);
            expect(isolateScope.selectedCourse).toBe(course2);

            // Desect the course
            selectCourse(course2);
            expect(isolateScope.selectedCourse).toBeNull();
        });

        it('should clear the selected course when the clearSelectedCourse method is called', function() {
            expect(isolateScope.selectedCourse).toBeNull();

            // Select a course
            selectCourse();
            expect(isolateScope.selectedCourse).toBe(course);

            // Clear using clear method
            isolateScope.clearSelectedCourse();
            expect(isolateScope.selectedCourse).toBeNull();
        });

        it('should clear the selected course when the visibility toggles are changed', function() {
            selectCourse();
            expect(isolateScope.selectedCourse).toBe(course);
            isolateScope.hideRegistered = true;
            isolateScope.$digest();
            expect(isolateScope.selectedCourse).toBeNull();

            selectCourse();
            expect(isolateScope.selectedCourse).toBe(course);
            isolateScope.hideWaitlisted = true;
            isolateScope.$digest();
            expect(isolateScope.selectedCourse).toBeNull();

            selectCourse();
            expect(isolateScope.selectedCourse).toBe(course);
            isolateScope.hideCart = true;
            isolateScope.$digest();
            expect(isolateScope.selectedCourse).toBeNull();
        });
    });
});


describe('Service: CourseCalendarDataParser', function() {

    // load the service's module
    beforeEach(module('regCartApp', 'mockData'));

    var service,
        schedule,
        days;

    var cart,
        registered,
        waitlisted;

    // instantiate service
    beforeEach(inject(function(_CourseCalendarDataParser_, _studentScheduleTermResult_, DAY_CONSTANTS) {
        service = _CourseCalendarDataParser_;
        schedule = angular.copy(_studentScheduleTermResult_);
        days = DAY_CONSTANTS;
    }));

    describe('change detection', function() {

        beforeEach(function() {
            cart = [];
            registered = [];
            waitlisted = [];
        });

        it('should handle empty lists', function() {
            service.getCalendar(); // Clear the dirty

            expect(service.setCart(cart)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setCart(cart)).toBeFalsy();

            expect(service.setRegistered(registered)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setRegistered(registered)).toBeFalsy();

            expect(service.setWaitlisted(waitlisted)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setWaitlisted(waitlisted)).toBeFalsy();
        });

        it('should handle populated lists', function() {
            service.getCalendar(); // Clear the dirty

            var course = schedule.registeredCourseOfferings[0];
            cart.push(course);
            registered.push(course);
            waitlisted.push(course);

            expect(service.setCart(cart)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setCart(cart)).toBeFalsy();

            expect(service.setRegistered(registered)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setRegistered(registered)).toBeFalsy();

            expect(service.setWaitlisted(waitlisted)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setWaitlisted(waitlisted)).toBeFalsy();
        });

        it('should detect dropped courses', function() {
            var course = schedule.registeredCourseOfferings[0];
            cart.push(course);
            registered.push(course);
            waitlisted.push(course);

            service.setCart(cart);
            service.setRegistered(registered);
            service.setWaitlisted(waitlisted);
            service.getCalendar(); // Clear the dirty

            var droppedCourse = angular.copy(course);
            droppedCourse.dropped = true;

            var newCart = [droppedCourse],
                newRegistered = [droppedCourse],
                newWaitlist = [droppedCourse];

            expect(service.setCart(newCart)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setCart(newCart)).toBeFalsy();

            expect(service.setRegistered(newRegistered)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setRegistered(newRegistered)).toBeFalsy();

            expect(service.setWaitlisted(newWaitlist)).toBeTruthy();
            service.getCalendar(); // Clear the dirty
            expect(service.setWaitlisted(newWaitlist)).toBeFalsy();
        });
    });

    describe('calendar construction', function() {
        beforeEach(function() {
            cart = [angular.copy(schedule.registeredCourseOfferings[0])];
            registered = schedule.registeredCourseOfferings;
            waitlisted = schedule.waitlistCourseOfferings;

            service.setCart(cart);
            service.setRegistered(registered);
            service.setWaitlisted(waitlisted);
        });

        it('should build the calendar once and cache it until it changes', function() {
            var calendar = service.getCalendar();

            expect(calendar.timeRange).toEqual([420, 1140]);
            expect(calendar.days.length).toBe(5);

            var mon = calendar.days[0],
                tue = calendar.days[1],
                wed = calendar.days[2],
                thu = calendar.days[3],
                fri = calendar.days[4];

            // Check labels
            expect(mon.day).toBe(days.monday);
            expect(tue.day).toBe(days.tuesday);
            expect(wed.day).toBe(days.wednesday);
            expect(thu.day).toBe(days.thursday);
            expect(fri.day).toBe(days.friday);

            // Based on the mock data, expect monday & tuesday have a course (+1 on tues for the cart course)
            expect(mon.courses.length).toBe(1);
            expect(tue.courses.length).toBe(2);
            expect(wed.courses.length).toBe(0);
            expect(thu.courses.length).toBe(0);
            expect(fri.courses.length).toBe(0);
        });

        it('should properly detect conflicts and put the course in the correct lane', function() {
            var calendar = service.getCalendar(),
                courses = calendar.days[1].courses; // Tuesday should be what has the conflicts

            for (var i = 0; i < courses.length; i++) {
                expect(courses[i].conflictCount).toBe(1);
                expect(courses[i].lane).toBe(i);
            }

            var course1 = courses[0],
                course2 = courses[1];

            expect(course1.conflicts).toContain(course2.key);
            expect(course2.conflicts).toContain(course1.key);
        });

        it('should build the truncated label out based on the number of conflicts detected', function() {
            var calendar1 = service.getCalendar();

            // Add another instance of the conflict
            waitlisted.push(angular.copy(schedule.registeredCourseOfferings[0]));
            service.setWaitlisted(waitlisted);

            var calendar2 = service.getCalendar();

            var noConflict = calendar1.days[0].courses,
                singleConflict = calendar1.days[1].courses, // Tuesday should be what has the conflicts
                doubleConflict = calendar2.days[1].courses;


            for (var i = 0; i < noConflict.length; i++) {
                expect(noConflict[i].truncated).toBe(noConflict[i].courseCode); // No change w/ no conflict
            }

            for (i = 0; i < singleConflict.length; i++) {
                expect(singleConflict[i].truncated).toBe(singleConflict[i].courseCode.substring(0, 1) + '...'); // Truncated label should have the '...'
            }

            for (i = 0; i < doubleConflict.length; i++) {
                expect(doubleConflict[i].truncated).toBe(doubleConflict[i].courseCode.substring(0, 1)); // Truncated label only be the first letter
            }
        });
    });
});