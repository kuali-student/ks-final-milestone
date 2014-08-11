'use strict';

describe('Service: ScheduleService', function () {

    // load the service's module
    beforeEach(module('regCartApp', 'mockPersonSchedule'));

    var ScheduleService;

    // instantiate service
    beforeEach(inject(function(_ScheduleService_) {
        ScheduleService = _ScheduleService_;
    }));


    describe('getters & setters', function() {
        it('should initialize the variables', function() {
            expect(ScheduleService.getRegisteredCredits()).toBeUndefined();
            expect(ScheduleService.getRegisteredCourseCount()).toBe(0);

            expect(ScheduleService.getWaitlistedCredits()).toBe(0);
            expect(ScheduleService.getWaitlistedCourseCount()).toBe(0);

            expect(ScheduleService.getSchedules()).toBeUndefined();
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


            var schedule = [{item: 'abcd'}];
            ScheduleService.setSchedules(schedule);
            expect(ScheduleService.getSchedules()).toEqual(schedule);
        });
    });

    describe('update schedule counts', function() {
        it('should correctly process the schedule response json', inject(function(_personSchedule_) {
            ScheduleService.updateScheduleCounts(_personSchedule_);

            expect(ScheduleService.getSchedules()).toBe(_personSchedule_.studentScheduleTermResults);

            expect(ScheduleService.getRegisteredCourseCount()).toBe(3);
            expect(ScheduleService.getRegisteredCredits()).toBe(7);

            expect(ScheduleService.getWaitlistedCourseCount()).toBe(1);
            expect(ScheduleService.getWaitlistedCredits()).toBe(3);
        }));
    });
});
