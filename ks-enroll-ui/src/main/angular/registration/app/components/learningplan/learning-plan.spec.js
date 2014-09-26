'use strict';


describe('Controller: LearningPlanCtrl', function () {

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
        isCourseInWaitlistSpy;


    // provide a mock SearchService
    beforeEach(function() {
        getLearningPlanSpy = jasmine.createSpy('LearningPlanService.getLearningPlan')
            .andReturn({
                then: function(success) {
                    success(mockLearningPlan);
                }
            });

        stateGoSpy = jasmine.createSpy('$state.go');

        getTermIdSpy = jasmine.createSpy('TermsService')
            .andReturn(termId);

        isCourseInCartSpy = jasmine.createSpy('CartService.isCourseInCart()');
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
                isCourseWaitlisted: isCourseInWaitlistSpy
            });
        });
    });


    // Initialize the controller, mock scope, and the mock data
    beforeEach(inject(function ($controller, _$rootScope_, _mockLearningPlan_) {
        mockLearningPlan = _mockLearningPlan_;

        scope = _$rootScope_.$new();
        ctrl = $controller('LearningPlanCtrl', {
            $scope: scope
        });

        $rootScope = _$rootScope_;
    }));


    function loadLearningPlan() {
        $rootScope.$broadcast('termIdChanged', termId);
    }



    describe('Loading', function() {
        it('should be able to load the plan for a given term', function() {
            loadLearningPlan();
            expect(getLearningPlanSpy).toHaveBeenCalledWith(termId);
            expect(scope.plan).toBe(mockLearningPlan);
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
    });

    describe('View Details', function() {
        it('should push to the correct state when attempting to view details', function() {
            var course = {
                courseId: '9e89ed85-66ba-4a9c-9765-c36e78929051',
                courseCode: 'ENGL101',
                regGroupId: 'ea3ac9da-38aa-41f0-8d13-6cccfa6bae75'
            };

            scope.viewDetails(course);
            // THIS NEEDS TO HANDLE THE OTHER CASES
            expect(stateGoSpy).toHaveBeenCalledWith('root.search.details', {
                searchCriteria: 'fromschedule',
                id: course.courseId,
                code: course.courseCode,
                regGroupId: course.regGroupId
            });
        });
    });
});