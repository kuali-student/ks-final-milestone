'use strict';

describe('Service: GlobalVarsService', function () {

    // load the service's module
    beforeEach(module('regCartApp', 'mockPersonSchedule'));

    var GlobalVarsService;

    // instantiate service
    beforeEach(inject(function(_GlobalVarsService_) {
        GlobalVarsService = _GlobalVarsService_;
    }));


    describe('getters & setters', function() {
        it('should initialize the variables', function() {
            expect(GlobalVarsService.getCartCredits()).toBe(0);
            expect(GlobalVarsService.getCartCourseCount()).toBe(0);

            expect(GlobalVarsService.getRegisteredCredits()).toBeUndefined();
            expect(GlobalVarsService.getRegisteredCourseCount()).toBe(0);

            expect(GlobalVarsService.getWaitlistedCredits()).toBe(0);
            expect(GlobalVarsService.getWaitlistedCourseCount()).toBe(0);

            expect(GlobalVarsService.getSchedule()).toBeUndefined();
            expect(GlobalVarsService.getUserId()).toBeUndefined();
        });

        it('should set & get the correct values', function() {
            GlobalVarsService.setCartCredits(5);
            expect(GlobalVarsService.getCartCredits()).toBe(5);

            GlobalVarsService.setCartCourseCount(3);
            expect(GlobalVarsService.getCartCourseCount()).toBe(3);

            GlobalVarsService.setRegisteredCredits(8);
            expect(GlobalVarsService.getRegisteredCredits()).toBe(8);

            GlobalVarsService.setRegisteredCourseCount(2);
            expect(GlobalVarsService.getRegisteredCourseCount()).toBe(2);

            GlobalVarsService.setWaitlistedCredits(4);
            expect(GlobalVarsService.getWaitlistedCredits()).toBe(4);

            GlobalVarsService.setWaitlistedCourseCount(1);
            expect(GlobalVarsService.getWaitlistedCourseCount()).toBe(1);


            var schedule = [{item: 'abcd'}];
            GlobalVarsService.setSchedule(schedule);
            expect(GlobalVarsService.getSchedule()).toEqual(schedule);

            var userId = 'defghijklmnop123';
            GlobalVarsService.setUserId(userId);
            expect(GlobalVarsService.getUserId()).toBe(userId);
        });
    });

    describe('status > state mapping', function() {
        it('should map the status to the correct state', inject(function(STATE, STATUS) {
            // Default
            expect(GlobalVarsService.getCorrespondingStatusFromState('')).toBe(STATUS.new);

            // Lpr trans states
            expect(GlobalVarsService.getCorrespondingStatusFromState(STATE.lpr.failed)).toBe(STATUS.error);
            expect(GlobalVarsService.getCorrespondingStatusFromState(STATE.lpr.processing)).toBe(STATUS.processing);
            expect(GlobalVarsService.getCorrespondingStatusFromState(STATE.lpr.succeeded)).toBe(STATUS.success);

            // Lpr item trans states
            expect(GlobalVarsService.getCorrespondingStatusFromState(STATE.lpr.item.failed)).toBe(STATUS.error);
            expect(GlobalVarsService.getCorrespondingStatusFromState(STATE.lpr.item.processing)).toBe(STATUS.processing);
            expect(GlobalVarsService.getCorrespondingStatusFromState(STATE.lpr.item.succeeded)).toBe(STATUS.success);
            expect(GlobalVarsService.getCorrespondingStatusFromState(STATE.lpr.item.waitlist)).toBe(STATUS.waitlist);
            expect(GlobalVarsService.getCorrespondingStatusFromState(STATE.lpr.item.waitlistActionAvailable)).toBe(STATUS.action);
        }));
    });

    it('should get the correct message matching the status', inject(function(STATUS) {
        expect(GlobalVarsService.getCorrespondingMessageFromStatus(STATUS.action)).toEqual('');
        expect(GlobalVarsService.getCorrespondingMessageFromStatus(STATUS.editing)).toEqual('');
        expect(GlobalVarsService.getCorrespondingMessageFromStatus(STATUS.error)).toEqual('');
        expect(GlobalVarsService.getCorrespondingMessageFromStatus(STATUS.new)).toEqual('');
        expect(GlobalVarsService.getCorrespondingMessageFromStatus(STATUS.processing)).toEqual('');
        expect(GlobalVarsService.getCorrespondingMessageFromStatus(STATUS.success)).toEqual('');
        expect(GlobalVarsService.getCorrespondingMessageFromStatus(STATUS.waitlist)).toEqual('If a seat becomes available you will be registered automatically');
    }));



    describe('update schedule counts', function() {
        it('should correctly process the schedule response json', inject(function(_personSchedule_) {
            var json = _personSchedule_;

            GlobalVarsService.updateScheduleCounts(json);

            expect(GlobalVarsService.getSchedule()).toBe(json.studentScheduleTermResults);
            expect(GlobalVarsService.getUserId()).toBe(json.userId);

            expect(GlobalVarsService.getRegisteredCourseCount()).toBe(3);
            expect(GlobalVarsService.getRegisteredCredits()).toBe(7);

            expect(GlobalVarsService.getWaitlistedCourseCount()).toBe(1);
            expect(GlobalVarsService.getWaitlistedCredits()).toBe(3);
        }));
    });
});
