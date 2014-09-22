'use strict';

describe('Controller: CourseDetailsCtrl', function () {

    // load the controller's module
    beforeEach(module('regCartApp', 'mockData'));

    var CourseDetailsCtrl,
        $rootScope,
        scope,
        mockData,
        SEARCH_CRITERIA,
        selectedCourse,
        searchSpy,
        isCourseInCartSpy,
        isCourseInWaitlistSpy;


    // provide a mock SearchService
    beforeEach(function() {
        searchSpy = jasmine.createSpy('SearchService.getCourse()').andReturn({
            query: function(params, success) {
                success(selectedCourse);
            }
        });

        isCourseInCartSpy = jasmine.createSpy('CartService.isCourseInCart()');
        isCourseInWaitlistSpy = jasmine.createSpy('ScheduleService.isCourseWaitlisted()');

        module(function ($provide) {
            // Setup the mock SearchService.getCourse() query
            $provide.value('SearchService', {
                getCourse: searchSpy
            });

            $provide.value('CartService', {
                isCourseInCart: isCourseInCartSpy
            });

            $provide.value('ScheduleService', {
                isCourseWaitlisted: isCourseInWaitlistSpy
            });
        });
    });

    // Initialize the controller and a mock scope
    beforeEach(inject(function($controller, _$rootScope_, mockCourseDetails, _SEARCH_CRITERIA_) {
        mockData = mockCourseDetails;
        SEARCH_CRITERIA = _SEARCH_CRITERIA_;

        // Instantiate the controller instance
        $rootScope = _$rootScope_;
        scope = $rootScope.$new();
        CourseDetailsCtrl = $controller('CourseDetailsCtrl', {
            $scope: scope
        });
    }));


    // Helper method to load a given course into the details page
    function loadCourse(course, fromSchedule) {
        fromSchedule = fromSchedule || false;

        // Set the mock up to return the course from the query
        selectedCourse = course;

        // Broadcast the $stateChangeSuccess event which triggers the course to be loaded
        $rootScope.$broadcast('$stateChangeSuccess', 'root.search.details', {
            id: course.courseOfferingId,
            searchCriteria: (fromSchedule ? SEARCH_CRITERIA.fromSchedule : '')
        });

        expect(searchSpy).toHaveBeenCalled();
        expect(scope.course).toBe(selectedCourse);
    }



    describe('course loading', function() {
        it('should know whether it was from the schedule or not', function() {
            var course = mockData.singleRegGroup;

            loadCourse(course, true);
            expect(scope.fromSchedule).toBeTruthy();

            loadCourse(course, false);
            expect(scope.fromSchedule).toBeFalsy();
        });

        it('should detect when there is only a single reg group', function() {
            loadCourse(mockData.singleRegGroup);
            expect(scope.singleRegGroup).toBeTruthy();

            loadCourse(mockData.multiRegGroup);
            expect(scope.singleRegGroup).toBeFalsy();
        });
    });

    describe('course in cart || waitlist', function() {
        it('should know when the course is already in your cart', function() {
            // Load the multi rg course so no reg group is pre-selected
            loadCourse(mockData.multiRegGroup);
            isCourseInCartSpy.andReturn(false);

            var inCart = scope.isSelectedRegGroupInCart();
            expect(inCart).toBeFalsy();
            expect(isCourseInCartSpy).not.toHaveBeenCalled();


            // Load the single rg course so it's pre-selected
            loadCourse(mockData.singleRegGroup);
            isCourseInCartSpy.reset();

            inCart = scope.isSelectedRegGroupInCart();
            expect(inCart).toBeFalsy();
            expect(isCourseInCartSpy).toHaveBeenCalled();

            // Reset to return true
            isCourseInCartSpy.reset();
            isCourseInCartSpy.andReturn(true);

            inCart = scope.isSelectedRegGroupInCart();
            expect(inCart).toBeTruthy();
            expect(isCourseInCartSpy).toHaveBeenCalled();
        });

        it('should know when the course is already in your waitlist', function() {
            // Load the multi rg course so no reg group is pre-selected
            loadCourse(mockData.multiRegGroup);
            isCourseInWaitlistSpy.andReturn(false);

            isCourseInWaitlistSpy.andReturn(false);
            var inWaitlist = scope.isSelectedRegGroupInWaitlist();
            expect(inWaitlist).toBeFalsy();
            expect(isCourseInWaitlistSpy).not.toHaveBeenCalled();


            // Load the single rg course so it's pre-selected
            loadCourse(mockData.singleRegGroup);
            isCourseInWaitlistSpy.reset();

            inWaitlist = scope.isSelectedRegGroupInWaitlist();
            expect(inWaitlist).toBeFalsy();
            expect(isCourseInWaitlistSpy).toHaveBeenCalled();

            // Reset to return true
            isCourseInWaitlistSpy.reset();
            isCourseInWaitlistSpy.andReturn(true);

            inWaitlist = scope.isSelectedRegGroupInWaitlist();
            expect(inWaitlist).toBeTruthy();
            expect(isCourseInWaitlistSpy).toHaveBeenCalled();
        });
    });

    describe('activity offering selection', function() {
        function isAOCompatibleWithRGs(ao, rgs) {
            for (var id in rgs) {
                if (rgs[id].activityOfferingIds.indexOf(ao.activityOfferingId) !== -1) {
                    return true;
                }
            }

            return false;
        }

        function toggleAO(ao) {
            $rootScope.$broadcast('toggleAO', ao);
            return scope.aoMap[ao.aoId];
        }

        it('should know that the activity offerings of a single reg group course are selected by default', function() {
            var course = mockData.singleRegGroup;
            loadCourse(course);

            expect(scope.hasSelectedAOs()).toBeTruthy();

            angular.forEach(course.activityOfferingTypes, function(aoType) {
                expect(scope.isAOTypeSelected(aoType)).toBeTruthy();

                angular.forEach(aoType.formattedOfferings, function(ao) {
                    console.log(ao.flags);
                    expect(ao.flags.selected).toBeTruthy();
                });
            });
        });

        it('should be able to toggle an AO\'s selection with the toggleAO event', function() {
            var course = mockData.multiRegGroup;
            loadCourse(course);

            // Lets select the first AO
            var aoType = course.activityOfferingTypes[0],
                fao = aoType.formattedOfferings[0],
                ao;
            ao = toggleAO(fao);

            expect(scope.hasSelectedAOs()).toBeTruthy();
            expect(scope.isAOTypeSelected(aoType)).toBeTruthy();
            expect(ao.flags.selected).toBeTruthy();
            expect(scope.selectedRegGroup).toBeNull(); // No reg group yet

            // Deselect the AO
            toggleAO(fao);
            expect(scope.hasSelectedAOs()).toBeFalsy();
            expect(scope.isAOTypeSelected(aoType)).toBeFalsy();
            expect(ao.flags.selected).toBeFalsy();
        });

        it('should manage the AO eligibility throughout the selection process', function() {
            var course = mockData.multiRegGroup;
            loadCourse(course);

            // Make sure we don't have anything selected by default
            expect(scope.hasSelectedAOs()).toBeFalsy();
            angular.forEach(course.activityOfferingTypes, function(aoType) {
                expect(scope.isAOTypeSelected(aoType)).toBeFalsy();
            });

            // Lets select the first AO
            var selectedAoType = course.activityOfferingTypes[0],
                fao = selectedAoType.formattedOfferings[0],
                selectedAO;
            selectedAO = toggleAO(fao);

            var rgs = selectedAO.regGroupInfos;

            // Make sure the other AO states line up with what we'd expect given the one we just selected
            angular.forEach(course.activityOfferingTypes, function(aoType) {
                var aoTypeSelected = scope.isAOTypeSelected(aoType);
                expect(aoTypeSelected).toEqual(aoType.activityOfferingType === selectedAO.activityOfferingType);

                // First, not showing all
                angular.forEach(aoType.activityOfferings, function(ao) {
                    if (ao === selectedAO) {
                        expect(ao.flags.selected).toBeTruthy();
                        expect(ao.flags.disabled).toBeFalsy();
                        expect(ao.flags.hidden).toBeFalsy();
                    } else {
                        var isCompatible = isAOCompatibleWithRGs(ao, rgs);
                        expect(ao.flags.selected).toBeFalsy();
                        expect(ao.flags.hidden).toEqual(!isCompatible);
                        expect(ao.flags.disabled).toEqual(!isCompatible);
                    }
                });

                // Next, showing all
                aoType.showAll = true;
                scope.updateAOStates();
                angular.forEach(aoType.activityOfferings, function(ao) {
                    if (!aoTypeSelected) {
                        // Expect all AO's of an unselected type to be shown
                        expect(ao.flags.hidden).toBeFalsy();
                    }

                    if (ao === selectedAO) {
                        expect(ao.flags.selected).toBeTruthy();
                        expect(ao.flags.disabled).toBeFalsy();
                        expect(ao.flags.hidden).toBeFalsy();
                    } else {
                        var isCompatible = isAOCompatibleWithRGs(ao, rgs);
                        expect(ao.flags.selected).toBeFalsy();
                        expect(ao.flags.disabled).toEqual(!isCompatible);
                    }
                });
            });

            toggleAO(fao);

            // Deselecting the AO should reset the showAll flag on the AOType
            expect(selectedAoType.showAll).toBeFalsy();
        });

        it('should calculate the number of compatible AOs', function() {
            var course = mockData.multiRegGroup;
            loadCourse(course);

            // Lets select the first AO
            var selectedAoType = course.activityOfferingTypes[0],
                fao = selectedAoType.formattedOfferings[0],
                selectedAO;
            selectedAO = toggleAO(fao);

            var rgs = selectedAO.regGroupInfos;

            angular.forEach(course.activityOfferingTypes, function(aoType) {
                if (aoType !== selectedAoType) {
                    var compatibleAOs = 0;
                    angular.forEach(aoType.activityOfferings, function(ao) {
                        if (isAOCompatibleWithRGs(ao, rgs)) {
                            compatibleAOs++;
                        }
                    });

                    expect(scope.countEligibleAOs(aoType.activityOfferings)).toEqual(compatibleAOs);
                }
            });
        });

        it('should know when an RG is selected', function() {
            var course = mockData.multiRegGroup;
            loadCourse(course);

            // Lets grab the first RG out of the first AO in the first AOType
            var targetAO = course.activityOfferingTypes[0].activityOfferings[0],
                targetRG = targetAO.regGroupInfos[Object.keys(targetAO.regGroupInfos)[0]],
                faosToSelect = [];

            // Pick out all of the formatted activity offerings that comprise the target RG
            angular.forEach(course.activityOfferingTypes, function(aoType) {
                expect(scope.isAOTypeSelected(aoType)).toBeFalsy();

                angular.forEach(aoType.formattedOfferings, function(fao) {
                    if (targetRG.activityOfferingIds.indexOf(fao.aoId) !== -1) {
                        faosToSelect.push(fao);
                    }
                });
            });

            expect(scope.hasSelectedAOs()).toBeFalsy();
            expect(scope.selectedRegGroup).toBeNull();
            for (var i = 0; i < faosToSelect.length; i++) {
                toggleAO(faosToSelect[i]);

                expect(scope.hasSelectedAOs()).toBeFalsy();
                if (i < faosToSelect.length - 1) {
                    expect(scope.selectedRegGroup).toBeNull();
                } else {
                    // This is the last ao to select, it should have auto-selected the RG now
                    expect(scope.selectedRegGroup).toBe(targetRG);
                }
            }
        });
    });

    describe('actions', function() {

        var spy;
        function mockAction(isSuccess, errorReason) {
            // Intercept the event broadcast and return our mock event
            if (!spy) {
                spy = spyOn($rootScope, '$broadcast');
            } else {
                spy.reset();
            }

            return spy.andReturn({
                promise: {
                    then: function(success, error) {
                        if (isSuccess) {
                            success();
                        } else {
                            error(errorReason);
                        }
                    }
                }
            });
        }

        afterEach(function() {
            spy = null;
        });



        it('should be blocked when no reg group is selected', function() {
            // Load a multi reg group course
            loadCourse(mockData.multiRegGroup);

            var spy = mockAction(true);

            scope.addToCart(); // Add to cart should return out since no reg group is selected
            expect(spy).not.toHaveBeenCalled();

            scope.addToWaitlist();
            expect(spy).not.toHaveBeenCalled();

            scope.directRegister();
            expect(spy).not.toHaveBeenCalled();
        });

        it('should be blocked when the waitlist is unavailable for the selected reg group', function() {
            // Load up a copy so we don't monkey w/ the base mock data
            loadCourse(angular.copy(mockData.singleRegGroup));

            scope.selectedRegGroup.isWaitlistAvailable = false;

            var spy = mockAction(true);

            scope.addToWaitlist();
            expect(spy).not.toHaveBeenCalled();

            scope.selectedRegGroup.isWaitlistAvailable = true;

            scope.addToWaitlist();
            expect(spy).toHaveBeenCalled();
            expect(scope.course.allowWaitlist).toBeTruthy();
        });

        it('should be performed correctly based on their type', function() {
            // Load the single reg group course (w/ auto-selected reg group)
            loadCourse(mockData.singleRegGroup);

            var spy = mockAction(true);

            scope.addToCart();
            expect(spy).toHaveBeenCalled();
            expect(scope.actionType).toBe('cart');

            spy.reset();
            scope.removeActionMessage();
            scope.directRegister();
            expect(spy).toHaveBeenCalled();
            expect(scope.actionType).toBe('register');

            spy.reset();
            scope.removeActionMessage();
            scope.addToWaitlist();
            expect(spy).toHaveBeenCalled();
            expect(scope.actionType).toBe('waitlist');
        });

        it('should handle successes and failures', inject(function(STATUS) {
            // Load the single reg group course (w/ auto-selected reg group)
            loadCourse(mockData.singleRegGroup);

            // 1. Success
            var spy = mockAction(true);
            scope.addToCart();
            expect(spy).toHaveBeenCalled();
            expect(scope.actionStatus).toBe(STATUS.success);
            scope.removeActionMessage();

            // 2. Failure w/ no reason
            spy = mockAction(false);
            scope.addToCart();
            expect(spy).toHaveBeenCalled();
            expect(scope.actionStatus).toBeNull();

            // 3. Failure w/ unknown reason
            spy = mockAction(false, 'unknown');
            scope.addToCart();
            expect(spy).toHaveBeenCalled();
            expect(scope.actionStatus).toBe(STATUS.error);
            scope.removeActionMessage();

            // 4. Failure w/ 'cancel' reason
            spy = mockAction(false, 'cancel');
            scope.addToCart();
            expect(spy).toHaveBeenCalled();
            expect(scope.actionStatus).toBeNull();

            // 5. Failure w/ 'escape key press' reason
            spy = mockAction(false, 'escape key press');
            scope.addToCart();
            expect(spy).toHaveBeenCalled();
            expect(scope.actionStatus).toBeNull();
        }));

        it('should remove the action message appropriately', function() {
            scope.actionType = 'cart';
            scope.actionStatus = 'processing';

            scope.removeActionMessage();

            expect(scope.actionType).toBeNull();
            expect(scope.actionStatus).toBeNull();
        });
    });
});