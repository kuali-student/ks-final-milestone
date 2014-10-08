'use strict';

describe('Controller: LearningPlanCtrl', function() {

    // load the controller's module
    beforeEach(module('regCartApp', 'mockData'));

    var ctrl,
        scope,
        $rootScope,
        mockLearningPlan,
        termId = 'kuali.atp.2012Fall',
        stateGoSpy,
        getLearningPlanSpy,
        getTermIdSpy,
        isCourseInCartSpy,
        isCourseRegisteredSpy,
        isCourseInWaitlistSpy;


    // provide a mock SearchService
    beforeEach(function() {
        getLearningPlanSpy = jasmine.createSpy('LearningPlanService.getLearningPlan')
            .andReturn({
                then: function(success) {
                    success(mockLearningPlan.plan);
                }
            });

        stateGoSpy = jasmine.createSpy('$state.go');

        getTermIdSpy = jasmine.createSpy('TermsService')
            .andReturn(termId);

        isCourseInCartSpy = jasmine.createSpy('CartService.isCourseInCart()');
        isCourseRegisteredSpy = jasmine.createSpy('ScheduleService.isCourseRegistered()');
        isCourseInWaitlistSpy = jasmine.createSpy('ScheduleService.isCourseWaitlisted()');

        module(function ($provide) {
            $provide.value('$state', {
                go: stateGoSpy
            });

            $provide.value('LearningPlanService', {
                getLearningPlan: getLearningPlanSpy
            });

            // Setup the mock TermsService.getTermId() method
            $provide.value('TermsService', {
                getTermId: getTermIdSpy
            });

            $provide.value('CartService', {
                isCourseInCart: isCourseInCartSpy
            });

            $provide.value('ScheduleService', {
                isCourseRegistered: isCourseRegisteredSpy,
                isCourseWaitlisted: isCourseInWaitlistSpy
            });
        });
    });


    // Initialize the controller, mock scope, and the mock data
    beforeEach(inject(function ($controller, _$rootScope_, _mockLearningPlan_) {
        mockLearningPlan = angular.copy(_mockLearningPlan_);

        scope = _$rootScope_.$new();
        scope.featureToggles = {
            learningPlan: true // Default the featureToggle to on
        };

        ctrl = $controller('LearningPlanCtrl', {
            $scope: scope
        });

        $rootScope = _$rootScope_;
    }));


    function loadLearningPlan() {
        getLearningPlanSpy.reset();
        $rootScope.$broadcast('termIdChanged', termId);
    }



    describe('Loading', function() {
        it('should be able to load the plan for a given term', function() {
            loadLearningPlan();
            expect(getLearningPlanSpy).toHaveBeenCalledWith(termId);
            expect(scope.plan).toBe(mockLearningPlan.plan);
        });

        it('should handle a failure with a message', function() {
            var errorResponse = {
                messageKey: 'kuali.cr.learningplan.message.course.code.not.found'
            };

            getLearningPlanSpy.andReturn({
                then: function(success, error) {
                    error(errorResponse);
                }
            });

            loadLearningPlan();
            expect(getLearningPlanSpy).toHaveBeenCalledWith(termId);
            expect(scope.plan).toBeNull();
            expect(scope.message).toBe(errorResponse);
        });

        it('should handle a failure without a message', function() {
            var errorResponse = {};

            getLearningPlanSpy.andReturn({
                then: function(success, error) {
                    error(errorResponse);
                }
            });

            loadLearningPlan();
            expect(getLearningPlanSpy).toHaveBeenCalledWith(termId);
            expect(scope.plan).toBeNull();
            expect(scope.message).toBeNull();
        });

        it('should manage the feature toggle', function() {
            var errorResponse = {
                messageKey: 'kuali.cr.learningplan.message.learningplan.not.configured'
            };

            getLearningPlanSpy.andReturn({
                then: function(success, error) {
                    error(errorResponse);
                }
            });

            scope.featureToggles.learningPlan = false;
            loadLearningPlan();
            expect(getLearningPlanSpy).not.toHaveBeenCalled();
            expect(scope.plan).toBeNull();
            expect(scope.message).toBeNull();

            scope.featureToggles.learningPlan = true;
            loadLearningPlan();
            expect(getLearningPlanSpy).toHaveBeenCalledWith(termId);
            expect(scope.plan).toBeNull();
            expect(scope.message).toBeNull();
            expect(scope.featureToggles.learningPlan).toBeFalsy();
        });
    });

    describe('Reset', function() {
        it('should clear out the scope variables', function() {
            loadLearningPlan();

            scope.reset();

            expect(scope.isLoaded).toBeFalsy();
            expect(scope.plan).toBeNull();
            expect(scope.message).toBeNull();
            expect(scope.selectedCategory).toBe(scope.categories[Object.keys(scope.categories)[0]]);

            for (var key in scope.categories) {
                expect(scope.categories[key].count).toBe(0);
            }
        });
    });

    describe('Count in Category', function() {
        it('should tally the number of items per category', function() {
            scope.reset();

            loadLearningPlan();
            expect(scope.categories.planned.count).toBe(6);
            expect(scope.categories.backup.count).toBe(2);
        });
    });

    describe('Selecting a category', function() {
        it('should set the selected category', function() {
            var keys = Object.keys(scope.categories);

            expect(scope.selectedCategory).toBe(scope.categories[keys[0]]);

            scope.selectCategory(scope.categories[keys[1]]);
            expect(scope.selectedCategory).toBe(scope.categories[keys[1]]);
        });
    });

    describe('Is item in Cart or Schedule', function() {
        it('should know when an item is in the cart', function() {
            isCourseInCartSpy.andReturn(false);
            expect(scope.isItemInCart(mockLearningPlan.regGroupOffered)).toBeFalsy();
            expect(isCourseInCartSpy).toHaveBeenCalled();

            isCourseInCartSpy.reset();
            isCourseInCartSpy.andReturn(true);
            expect(scope.isItemInCart(mockLearningPlan.regGroupOffered)).toBeTruthy();
            expect(isCourseInCartSpy).toHaveBeenCalled();
        });

        it('should know when an item is in the schedule - registered', function() {
            isCourseRegisteredSpy.andReturn(false);
            isCourseInWaitlistSpy.andReturn(false);
            expect(scope.isItemInSchedule(mockLearningPlan.regGroupOffered)).toBeFalsy();
            expect(isCourseRegisteredSpy).toHaveBeenCalled();

            isCourseRegisteredSpy.reset();
            isCourseRegisteredSpy.andReturn(true);
            expect(scope.isItemInSchedule(mockLearningPlan.regGroupOffered)).toBeTruthy();
            expect(isCourseRegisteredSpy).toHaveBeenCalled();
        });

        it('should know when an item is in the schedule - waitlisted', function() {
            isCourseRegisteredSpy.andReturn(false);
            isCourseInWaitlistSpy.andReturn(false);
            expect(scope.isItemInSchedule(mockLearningPlan.regGroupOffered)).toBeFalsy();
            expect(isCourseInWaitlistSpy).toHaveBeenCalled();

            isCourseInWaitlistSpy.reset();
            isCourseInWaitlistSpy.andReturn(true);
            expect(scope.isItemInSchedule(mockLearningPlan.regGroupOffered)).toBeTruthy();
            expect(isCourseInWaitlistSpy).toHaveBeenCalled();
        });
    });

    describe('Actionable Items', function() {
        it('should know which items are actionable', function() {
            loadLearningPlan();

            // Baseline
            var items = scope.getActionableItems();
            expect(items.length).toBe(2);

            // Block with in cart
            isCourseInCartSpy.andReturn(true);
            items = scope.getActionableItems();
            expect(items.length).toBe(0);

            // Block with registered
            isCourseInCartSpy.andReturn(false);
            isCourseRegisteredSpy.andReturn(true);
            items = scope.getActionableItems();
            expect(items.length).toBe(0);

            // Block with waitlisted
            isCourseRegisteredSpy.andReturn(false);
            isCourseInWaitlistSpy.andReturn(true);
            items = scope.getActionableItems();
            expect(items.length).toBe(0);

            // Retest the baseline
            isCourseInWaitlistSpy.andReturn(false);
            items = scope.getActionableItems();
            expect(items.length).toBe(2);
        });
    });

    describe('Checking items for issues', function() {
        var LEARNING_PLAN_ERRORS;

        beforeEach(inject(function(_LEARNING_PLAN_ERRORS_) {
            LEARNING_PLAN_ERRORS = _LEARNING_PLAN_ERRORS_;
        }));

        it('should know when there are no issues with a course or reg group', function() {
            expect(scope.checkPlanItemForIssues(mockLearningPlan.courseOffered)).toBeFalsy();
            expect(scope.checkPlanItemForIssues(mockLearningPlan.regGroupOffered)).toBeFalsy();
        });

        it('should detect when a reg group is not offered', function() {
            var item = mockLearningPlan.regGroupNotOffered,
                hasIssues = scope.checkPlanItemForIssues(item);

            expect(hasIssues).toBeTruthy();
            expect(item.warnings[0]).toEqual({
                messageKey: LEARNING_PLAN_ERRORS.regGroupNotOffered,
                state: item.state
            });
        });

        it('should detect when a course is not active', function() {
            var item = mockLearningPlan.courseNotActive,
                hasIssues = scope.checkPlanItemForIssues(item);

            expect(hasIssues).toBeTruthy();
            expect(item.warnings[0]).toEqual({
                messageKey: LEARNING_PLAN_ERRORS.courseNotActive,
                state: item.state
            });
        });

        it('should detect when a course is not offered', function() {
            var item = mockLearningPlan.courseNotOffered,
                hasIssues = scope.checkPlanItemForIssues(item);

            expect(hasIssues).toBeTruthy();
            expect(item.warnings[0]).toEqual({
                messageKey: LEARNING_PLAN_ERRORS.courseNotOffered,
                state: item.state
            });
        });
    });

    describe('Add to Cart', function() {
        it('should fire the addCourseToCart event for each actionable item', function() {
            loadLearningPlan();

            var spy = jasmine.createSpy('addCoursesToCart');
            scope.$on('addCoursesToCart', spy);

            scope.addAllToCart();
            expect(spy).toHaveBeenCalled();
            expect(spy.callCount).toBe(1);
        });
    });

    describe('View Details', function() {
        it('should push to the correct state when attempting to view details', inject(function(SEARCH_ORIGINS) {
            var course = mockLearningPlan.regGroupOffered;

            // Course w/ regGroupId should push to the details
            scope.viewDetails(course);
            expect(stateGoSpy).toHaveBeenCalledWith('root.search.details', {
                origin: SEARCH_ORIGINS.schedule,
                searchCriteria: course.courseCode,
                id: course.courseId,
                code: course.courseCode,
                regGroupId: course.regGroupId
            });

            stateGoSpy.reset();
            course = mockLearningPlan.courseNotOffered;
            scope.viewDetails(course);
            expect(stateGoSpy).not.toHaveBeenCalled();

            course = mockLearningPlan.courseOffered;
            scope.viewDetails(course);
            expect(stateGoSpy).toHaveBeenCalledWith('root.search.results', {
                origin: SEARCH_ORIGINS.schedule,
                searchCriteria: course.courseCode,
                cluId: course.cluId
            });
        }));
    });
});



describe('Service: LearningPlanService', function() {

    beforeEach(module('regCartApp', 'mockData'));

    var LearningPlanService,
        mockLearningPlan,
        termId = 'kuali.atp.2012Fall';


    beforeEach(inject(function(_mockLearningPlan_) {
        mockLearningPlan = _mockLearningPlan_.plan;
    }));

    describe('getLearningPlan', function() {
        var getFromServerSpy;

        beforeEach(inject(function(_LearningPlanService_) {
            LearningPlanService = _LearningPlanService_;

            getFromServerSpy = spyOn(LearningPlanService, 'getLearningPlanFromServer')
                .andReturn({
                    then: function(success) {
                        success(mockLearningPlan);
                    }
                });
        }));

        it('should know when to hold em, know when to fold em, know when to walk away, know when to run', function() {
            // Test the caching
            LearningPlanService.getLearningPlan(termId).then(function(plan) {
                expect(plan).toEqual(mockLearningPlan);
                expect(getFromServerSpy).toHaveBeenCalled();
            });

            getFromServerSpy.reset();
            LearningPlanService.getLearningPlan(termId).then(function(plan) {
                expect(plan).toEqual(mockLearningPlan);
                expect(getFromServerSpy).not.toHaveBeenCalled();
            });
        });
    });
});
