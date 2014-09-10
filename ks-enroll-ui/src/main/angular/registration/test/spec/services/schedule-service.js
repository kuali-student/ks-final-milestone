'use strict';

describe('Service: ScheduleService', function () {

    // load the service's module
    beforeEach(module('regCartApp', 'mockStudentScheduleTermResult'));

    var ScheduleService;

    // instantiate service
    beforeEach(inject(function(_ScheduleService_) {
        ScheduleService = _ScheduleService_;
    }));


    describe('getters & setters', function() {
        it('should initialize the variables', function() {
            expect(ScheduleService.getRegisteredCredits()).toBe(0);
            expect(ScheduleService.getRegisteredCourseCount()).toBe(0);

            expect(ScheduleService.getWaitlistedCredits()).toBe(0);
            expect(ScheduleService.getWaitlistedCourseCount()).toBe(0);
        });

        it('should set & get the correct values', function() {
            ScheduleService.setRegisteredCredits(8);
            expect(ScheduleService.getRegisteredCredits()).toBe(8);

            ScheduleService.setRegisteredCourseCount(2);
            expect(ScheduleService.getRegisteredCourseCount()).toBe(2);

            ScheduleService.setWaitlistedCredits(4);
            expect(ScheduleService.getWaitlistedCredits()).toBe(4);

            ScheduleService.setWaitlistedCourseCount(1);
            expect(ScheduleService.getWaitlistedCourseCount()).toBe(1);
        });
    });

    describe('update schedule counts', function() {
        it('should correctly process the schedule response json', inject(function(_studentScheduleTermResult_) {
            var mockData = angular.copy(_studentScheduleTermResult_);

            ScheduleService.updateScheduleCounts(_studentScheduleTermResult_);

            expect(ScheduleService.getRegisteredCourses()[0].createTime).toEqual(mockData.registeredCourseOfferings[1].createTime);
            expect(ScheduleService.getRegisteredCourses()[1].createTime).toEqual(mockData.registeredCourseOfferings[0].createTime);
            expect(ScheduleService.getRegisteredCourses()[2].createTime).toEqual(mockData.registeredCourseOfferings[2].createTime);

            expect(ScheduleService.getRegisteredCourseCount()).toBe(3);
            expect(ScheduleService.getRegisteredCredits()).toBe(7);

            expect(ScheduleService.getWaitlistedCourseCount()).toBe(1);
            expect(ScheduleService.getWaitlistedCredits()).toBe(3);
        }));
    });
});
