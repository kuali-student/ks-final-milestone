'use strict';

angular.module('regCartApp')

    // Server service URLs
    .constant('URLS', {
        scheduleOfClasses: 'ScheduleOfClassesClientService',
        courseRegistration: 'CourseRegistrationClientService',
        courseRegistrationCart: 'CourseRegistrationCartClientService',
        developmentLogin: 'DevelopmentLoginClientService'
    })


    // State identifiers
    .constant('STATE', (function() {
        // Assigned as a var so they can be used in the groupings
        var lprStates = {
            failed:                         'kuali.lpr.trans.state.failed',
            processing:                     'kuali.lpr.trans.state.processing',
            succeeded:                      'kuali.lpr.trans.state.succeeded',

            item: {
                failed:                     'kuali.lpr.trans.item.state.failed',
                processing:                 'kuali.lpr.trans.item.state.processing',
                succeeded:                  'kuali.lpr.trans.item.state.succeeded',
                waitlist:                   'kuali.lpr.trans.item.state.waitlist',
                waitlistActionAvailable:    'kuali.lpr.trans.item.state.waitlistActionAvailable'
            }
        };

        return {
            // DTO states (generic but used for CLUs/COs)
            dto: {
                draft: 'Draft',
                submitted: 'Submitted',
                withdrawn: 'Withdrawn',
                approved: 'Approved',
                notApproved: 'NotApproved',
                active: 'Active',
                superseded: 'Superseded',
                suspended: 'Suspended',
                retired: 'Retired'
            },

            // LUI states (RegGroups)
            lui: {
                canceled:   'kuali.lui.registration.group.state.canceled',
                invalid:    'kuali.lui.registration.group.state.invalid',
                offered:    'kuali.lui.registration.group.state.offered',
                pending:    'kuali.lui.registration.group.state.pending',
                suspended:  'kuali.lui.registration.group.state.suspended'
            },

            // LPR transaction states
            lpr: lprStates,

            // Aggregated states - used to map states to statuses in GlobalVarsService
            action: [
                lprStates.item.waitlistActionAvailable
            ],
            error: [
                lprStates.failed,
                lprStates.item.failed
            ],
            processing: [
                lprStates.processing,
                lprStates.item.processing
            ],
            success: [
                lprStates.succeeded,
                lprStates.item.succeeded
            ],
            waitlist: [
                lprStates.item.waitlist
            ]
        };
    })())


    // Status identifiers are used to indicate status within the client.
    // Often, they are a client-friendly translation of the longer STATE identifiers.
    .constant('STATUS', {
        action:     'action',
        editing:    'editing',
        error:      'error',
        info:       'info',
        new:        'new',
        processing: 'processing',
        success:    'success',
        waitlist:   'waitlist'
    })


    // Course Types
    .constant('COURSE_TYPES', {
        registered: 'registered',
        waitlisted: 'waitlist',
        cart: 'cart'
    })


    // Grading option identifiers
    .constant('GRADING_OPTION', {
        audit:      'kuali.resultComponent.grade.audit',
        letter:     'kuali.resultComponent.grade.letter',
        passFail:   'kuali.resultComponent.grade.passFail',
        percentageGrade:   'kuali.resultComponent.grade.percentage',
        satisfactory:   'kuali.resultComponent.grade.satisfactory',
        notation:   'kuali.resultComponent.grade.completedNotation',
        plusMinusLetter:   'kuali.result.group.grade.letter.plus.minus.standard'
    })


    // Action link identifiers that get returned from the server
    .constant('ACTION_LINK', {
        removeItemFromCart: 'removeItemFromCart',
        undoDeleteCourse:   'undoDeleteCourse'
    })


    // Validation error type identifiers that get returned from the server
    .constant('VALIDATION_ERROR_TYPE', {
        maxCredits: 'kuali.lpr.trans.message.credit.load.exceeded',
        timeConflict: 'kuali.lpr.trans.message.time.conflict',
        regGroupNotOffered: 'kuali.lpr.trans.message.reggroup.notoffered',
        waitlistAvailable: 'kuali.lpr.trans.message.waitlist.available',        //"No seats available."
        waitlistWaitlisted: 'kuali.lpr.trans.message.waitlist.waitlisted',      //"Waitlisted" ??? Is it error or success? Status 'waitlist'
        waitlistFull: 'kuali.lpr.trans.message.waitlist.full',                  //"No seats available.<br/>(Waitlist full)"
        waitlistNotOffered: 'kuali.lpr.trans.message.waitlist.not.offered',      //"No seats available.<br/>(Waitlist not offered)"
        transactionException: 'kuali.lpr.trans.message.exception',
        transactionItemException: 'kuali.lpr.trans.item.message.exception',
        courseNotOpen: 'kuali.lpr.trans.message.course.not.open',
        courseNotOpenEarly: 'kuali.lpr.trans.message.course.not.open.early',
        courseNotOpenLate: 'kuali.lpr.trans.message.course.not.open.late',
        dropPeriodClosed: 'kuali.lpr.trans.message.drop.period.closed',          // The current date is outside of the drop period
        editPeriodClosed: 'kuali.lpr.trans.message.edit.period.closed',          // The current date is outside of the edit period
        courseGradeIncomplete: 'kuali.lpr.trans.message.course.grade.incomplete',// Student has an incomplete already in the course
        courseAlreadyTaken: 'kuali.lpr.trans.message.course.already.taken',      // Student has already taken the course and has hit the repeatability limit
        repeatabilityWarning: 'kuali.lpr.trans.message.repeatability.warning'    // Repeatability warning that is returned when the student is repeating a course
    })

    .constant('GENERAL_ERROR_TYPE', {
        noRegGroup: 'noRegGroup',
        courseNotFound: 'kuali.cr.cart.message.course.code.not.found'
    })

    .constant('VALIDATION_SUCCESS_TYPE', {
        waitlistStudentRemoved: 'kuali.lpr.trans.message.waitlist.student.removed',    //"Student removed from waitlist."
        waitlistUpdated: 'kuali.lpr.trans.message.waitlist.options.updated',    //"Waitlist options were updated."
        courseUpdated: 'kuali.lpr.trans.message.course.updated',                //"Course was updated."
        courseDropped: 'kuali.lpr.trans.message.course.dropped',                //"Course was dropped."
        personRegistered: 'kuali.lpr.trans.message.person.registered'           //"Registered"
    })

    /*
    This constant determines how days are displayed, and the order that they
    are displayed in the calendar
     */
    .constant('DAY_CONSTANTS', {
        monday: 'M',
        tuesday: 'Tu',
        wednesday: 'W',
        thursday: 'Th',
        friday: 'F',
        saturday: 'Sa',
        sunday: 'Su',
        dayArray: ['M', 'Tu', 'W', 'Th', 'F', 'Sa', 'Su']
    })

    /*
    Constants for the schedule grid
     */
    .constant('GRID_CONSTANTS', {
        defaultStart: 7*60, // 7am
        defaultEnd: 19*60,  // 7pm
        defaultBuffer: 15
    })

    // Search origins constants
    .constant('SEARCH_ORIGINS', {
        schedule: 'schedule'
    })

;
