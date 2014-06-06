'use strict';

angular.module('mockJson', []).value('personSchedule', {
    "userId": "admin",
    "studentScheduleTermResults": [
        {
            "term": {
                "termName": "Fall 2012",
                "termId": "kuali.atp.2012Fall",
                "termCode": "201208",
                "startDate": null,
                "endDate": null,
                "currentTerm": false
            },
            "registeredCourseOfferings": [
                {
                    "courseCode": "ENGL101",
                    "description": "An introductory course in expository writing.",
                    "credits": "3.0",
                    "gradingOptionId": "kuali.resultComponent.grade.letter",
                    "longName": "Academic Writing",
                    "regGroupCode": "1001",
                    "masterLprId": "0a9367a4-7627-4f91-b2e3-8526db033439",
                    "creditOptions": [
                        "3.0"
                    ],
                    "gradingOptions": {
                        "kuali.resultComponent.grade.letter": "Letter"
                    },
                    "activityOfferings": [
                        {
                            "activityOfferingId": "35828d6a-8adb-491a-9861-c06f12de963e",
                            "activityOfferingTypeName": "Lecture",
                            "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                            "scheduleComponents": [
                                {
                                    "sun": false,
                                    "mon": false,
                                    "tue": false,
                                    "wed": false,
                                    "thu": false,
                                    "fri": false,
                                    "sat": false,
                                    "startTime": "12:30 pm",
                                    "endTime": "1:45 pm",
                                    "displayTime": "12:30-1:45pm",
                                    "days": "Tu",
                                    "buildingCode": "WEB",
                                    "roomCode": "ONLINE",
                                    "isTBA": false
                                }
                            ],
                            "instructors": [
                                {
                                    "displayName": "STRICKLAND, PHIL",
                                    "firstName": "PHIL",
                                    "lastName": "STRICKLAND",
                                    "activityOfferingId": "35828d6a-8adb-491a-9861-c06f12de963e",
                                    "principalId": "S.PHILS",
                                    "primary": true
                                }
                            ]
                        }
                    ],
                    "waitlisted": false
                },
                {
                    "courseCode": "CHEM232",
                    "description": "Provides experience in developing some basic laboratory techniques, recrystallizaton, distillation, extraction, chromatography.",
                    "credits": "1.0",
                    "gradingOptionId": "kuali.resultComponent.grade.letter",
                    "longName": "Organic Chemistry Laboratory I",
                    "regGroupCode": "1001",
                    "masterLprId": "e69fbfe6-853d-4be5-a193-ac072dc99dba",
                    "creditOptions": [
                        "1.0"
                    ],
                    "gradingOptions": {
                        "kuali.resultComponent.grade.letter": "Letter"
                    },
                    "activityOfferings": [
                        {
                            "activityOfferingId": "81b9d2ef-b25e-40fe-8085-13394b95a848",
                            "activityOfferingTypeName": "Lab",
                            "activityOfferingType": "kuali.lui.type.activity.offering.lab",
                            "scheduleComponents": [
                                {
                                    "sun": false,
                                    "mon": true,
                                    "tue": false,
                                    "wed": false,
                                    "thu": false,
                                    "fri": false,
                                    "sat": false,
                                    "startTime": "12:00 pm",
                                    "endTime": "2:50 pm",
                                    "displayTime": "12:00-2:50pm",
                                    "days": "M",
                                    "buildingCode": "CHM",
                                    "roomCode": "1360",
                                    "isTBA": false
                                }
                            ],
                            "instructors": [
                                {
                                    "displayName": "PICKETT, ALAN",
                                    "firstName": "ALAN",
                                    "lastName": "PICKETT",
                                    "activityOfferingId": "81b9d2ef-b25e-40fe-8085-13394b95a848",
                                    "principalId": "P.ALANW",
                                    "primary": true
                                }
                            ]
                        }
                    ],
                    "waitlisted": false
                },
                {
                    "courseCode": "ENGL201",
                    "description": "Wide range of texts, genres, and themes from ancient and medieval Western traditions. Study of cultural, historical, and artistic forces shaping traditions, and the influence and relevance of those traditions to life in twenty-first century.",
                    "credits": "3.0",
                    "gradingOptionId": "kuali.resultComponent.grade.audit",
                    "longName": "Inventing Western Literature: Ancient and Medieval Traditions",
                    "regGroupCode": "1001",
                    "masterLprId": "e259c124-ac80-441f-900b-74e3c66ec549",
                    "creditOptions": [
                        "3.0"
                    ],
                    "gradingOptions": {
                        "kuali.resultComponent.grade.passFail": "Pass/Fail",
                        "kuali.resultComponent.grade.letter": "Letter",
                        "kuali.resultComponent.grade.audit": "Audit"
                    },
                    "activityOfferings": [
                        {
                            "activityOfferingId": "72a99038-cc3f-426f-973c-f85daf1fa24d",
                            "activityOfferingTypeName": "Lecture",
                            "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                            "scheduleComponents": [
                                {
                                    "sun": false,
                                    "mon": false,
                                    "tue": false,
                                    "wed": false,
                                    "thu": false,
                                    "fri": false,
                                    "sat": false,
                                    "startTime": "",
                                    "endTime": "",
                                    "displayTime": null,
                                    "days": "Tu",
                                    "buildingCode": null,
                                    "roomCode": null,
                                    "isTBA": true
                                }
                            ],
                            "instructors": [
                                {
                                    "displayName": "MCINTYRE, ERIK",
                                    "firstName": "ERIK",
                                    "lastName": "MCINTYRE",
                                    "activityOfferingId": "72a99038-cc3f-426f-973c-f85daf1fa24d",
                                    "principalId": "M.ERIKF",
                                    "primary": true
                                }
                            ]
                        }
                    ],
                    "waitlisted": false
                }
            ],
            "waitlistCourseOfferings": [
                {
                    "courseCode": "CHEM399A",
                    "description": "Basic (chemical) research conducted under the supervision of a faculty member.",
                    "credits": "3.0",
                    "gradingOptionId": "kuali.resultComponent.grade.letter",
                    "longName": "Introduction to Chemical Research",
                    "regGroupCode": "1001",
                    "masterLprId": "b98e971f-ec54-40b9-83ed-badb079d567d",
                    "creditOptions": [
                        "1.0",
                        "1.5",
                        "2.0",
                        "2.5",
                        "3.0"
                    ],
                    "gradingOptions": {
                        "kuali.resultComponent.grade.passFail": "Pass/Fail",
                        "kuali.resultComponent.grade.letter": "Letter",
                        "kuali.resultComponent.grade.audit": "Audit"
                    },
                    "activityOfferings": [
                        {
                            "activityOfferingId": "fdf860cc-0310-45d8-876f-7a053dcd2f82",
                            "activityOfferingTypeName": "Lecture",
                            "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                            "scheduleComponents": [
                                {
                                    "sun": false,
                                    "mon": false,
                                    "tue": false,
                                    "wed": false,
                                    "thu": false,
                                    "fri": false,
                                    "sat": false,
                                    "startTime": "",
                                    "endTime": "",
                                    "displayTime": null,
                                    "days": "Tu",
                                    "buildingCode": null,
                                    "roomCode": null,
                                    "isTBA": true
                                }
                            ],
                            "instructors": [
                                {
                                    "displayName": "LOWERY, DANIEL",
                                    "firstName": "DANIEL",
                                    "lastName": "LOWERY",
                                    "activityOfferingId": "fdf860cc-0310-45d8-876f-7a053dcd2f82",
                                    "principalId": "L.DANIELR",
                                    "primary": true
                                }
                            ]
                        }
                    ],
                    "waitlisted": true
                }
            ]
        }
    ]
});