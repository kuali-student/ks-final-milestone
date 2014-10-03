'use strict';

describe('Service: GlobalVarsService', function () {

    // load the service's module
    beforeEach(module('regCartApp'));

    var GlobalVarsService;

    // instantiate service
    beforeEach(inject(function(_GlobalVarsService_) {
        GlobalVarsService = _GlobalVarsService_;
    }));


    describe('getters & setters', function() {
        it('should initialize the variables', function() {
            expect(GlobalVarsService.getUserId()).toBeUndefined();
        });

        it('should set & get the correct values', function() {
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

});
