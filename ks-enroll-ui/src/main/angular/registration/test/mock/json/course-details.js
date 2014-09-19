'use strict';

/**
 * Mock Course Details Data
 *
 * Courses are under:
 * .singleRegGroup = CHEM105
 * .multiRegGroup = CHEM131
 */
angular.module('mockData').value('mockCourseDetails', {
    singleRegGroup: {
        "courseOfferingId": "9e89ed85-66ba-4a9c-9765-c36e78929051",
        "courseOfferingCode": "CHEM105",
        "courseOfferingDesc": "The chemistry of carbon: aliphatic compounds, aromatic compounds, stere chemistry, halides, amines, and amides, acids, esters, carbohydrates, and natural products. This course is intended for students in curricula requiring only one semester of organic chemistry. Students requiring two or more years of chemistry should register for CHEM231/232 or CHEM237.",
        "courseOfferingNumber": "105",
        "courseOfferingSubjectArea": "CHEM",
        "courseOfferingLongName": "Fundamental of Organic and Biochemistry",
        "cluId": "31831b51-5be1-4f61-961f-a23d60ad2c53",
        "regGroupsOffered": true,
        "creditOptions": [
            "3.0"
        ],
        "gradingOptions": {
            "kuali.resultComponent.grade.passFail": "Pass/Fail",
            "kuali.resultComponent.grade.letter": "Letter",
            "kuali.resultComponent.grade.audit": "Audit"
        },
        "crossListedCourses": null,
        "prerequisites": [],
        "activityOfferingTypes": [
            {
                "activityOfferingCode": "Lec",
                "activityOfferingTypeName": "Lecture",
                "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                "activityOfferings": [
                    {
                        "activityOfferingId": "a2596117-c310-4d3d-9ab0-0363715df307",
                        "activityOfferingTypeName": "Lecture",
                        "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                        "activityOfferingCode": "A",
                        "seatsAvailable": 46,
                        "seatsOpen": 46,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": true,
                                "tue": false,
                                "wed": true,
                                "thu": false,
                                "fri": true,
                                "sat": false,
                                "startTime": "8:00 am",
                                "endTime": "8:50 am",
                                "displayTime": "8:00-8:50am",
                                "days": "MWF",
                                "buildingCode": "CHM",
                                "roomCode": "1228",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "NIEVES, BENOIT",
                                "firstName": "BENOIT",
                                "lastName": "NIEVES",
                                "activityOfferingId": "a2596117-c310-4d3d-9ab0-0363715df307",
                                "personId": "KS-6326",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "ea3ac9da-38aa-41f0-8d13-6cccfa6bae75": {
                                "regGroupId": "ea3ac9da-38aa-41f0-8d13-6cccfa6bae75",
                                "regGroupCode": "1001",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": {
                            "startDate": 1346198400000,
                            "endDate": 1350777600000,
                            "name": "Fall 2012 Sub Term 1",
                            "subTermId": "kuali.atp.2012HalfFall1"
                        },
                        "honors": false
                    }
                ]
            },
            {
                "activityOfferingCode": "Dis",
                "activityOfferingTypeName": "Discussion",
                "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                "activityOfferings": [
                    {
                        "activityOfferingId": "a03df821-38bc-4fc4-b34a-77a92405b87f",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "B",
                        "seatsAvailable": 46,
                        "seatsOpen": 46,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": true,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "1:00 pm",
                                "endTime": "1:50 pm",
                                "displayTime": "1:00-1:50pm",
                                "days": "W",
                                "buildingCode": "CHM",
                                "roomCode": "1228",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "DANIELS, RAMONA",
                                "firstName": "RAMONA",
                                "lastName": "DANIELS",
                                "activityOfferingId": "a03df821-38bc-4fc4-b34a-77a92405b87f",
                                "personId": "KS-5534",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "ea3ac9da-38aa-41f0-8d13-6cccfa6bae75": {
                                "regGroupId": "ea3ac9da-38aa-41f0-8d13-6cccfa6bae75",
                                "regGroupCode": "1001",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": {
                            "startDate": 1346198400000,
                            "endDate": 1350777600000,
                            "name": "Fall 2012 Sub Term 1",
                            "subTermId": "kuali.atp.2012HalfFall1"
                        },
                        "honors": false
                    }
                ]
            }
        ]
    },
    multiRegGroup: {
        "courseOfferingId": "25a7e006-e71a-4d5d-806c-04675f365222",
        "courseOfferingCode": "CHEM131",
        "courseOfferingDesc": "An overview of the Periodic Table, inorganic substances, ionic and covalent bonding, bulk properties of materials, chemical equilibrium, and quantitative chemistry. CHEM131 is the first course in a four-semester sequence for students majoring in the sciences, other than Chemistry and Biochemistry majors.",
        "courseOfferingNumber": "131",
        "courseOfferingSubjectArea": "CHEM",
        "courseOfferingLongName": "Chemistry I - Fundamentals of General Chemistry",
        "cluId": "CLUID-CHEM131-200508000000",
        "regGroupsOffered": true,
        "creditOptions": [
            "3.0"
        ],
        "gradingOptions": {
            "kuali.resultComponent.grade.passFail": "Pass/Fail",
            "kuali.resultComponent.grade.letter": "Letter",
            "kuali.resultComponent.grade.audit": "Audit"
        },
        "crossListedCourses": null,
        "prerequisites": [],
        "activityOfferingTypes": [
            {
                "activityOfferingCode": "Lec",
                "activityOfferingTypeName": "Lecture",
                "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                "activityOfferings": [
                    {
                        "activityOfferingId": "a00dd7ea-5f42-4076-b17c-d60d6fc7b38a",
                        "activityOfferingTypeName": "Lecture",
                        "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                        "activityOfferingCode": "O",
                        "seatsAvailable": 200,
                        "seatsOpen": 200,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": true,
                                "tue": false,
                                "wed": true,
                                "thu": false,
                                "fri": true,
                                "sat": false,
                                "startTime": "11:00 am",
                                "endTime": "11:50 am",
                                "displayTime": "11:00-11:50am",
                                "days": "MWF",
                                "buildingCode": "CHM",
                                "roomCode": "1407",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "CANTU, CARL",
                                "firstName": "CARL",
                                "lastName": "CANTU",
                                "activityOfferingId": "a00dd7ea-5f42-4076-b17c-d60d6fc7b38a",
                                "personId": "KS-9402",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "1584f79a-d91e-4c3c-be03-7964362f5dc7": {
                                "regGroupId": "1584f79a-d91e-4c3c-be03-7964362f5dc7",
                                "regGroupCode": "1018",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "ac729767-065a-43eb-868d-4c1e44c92705": {
                                "regGroupId": "ac729767-065a-43eb-868d-4c1e44c92705",
                                "regGroupCode": "1014",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "96e77b6b-ad45-4431-8eee-613b1fd69de6": {
                                "regGroupId": "96e77b6b-ad45-4431-8eee-613b1fd69de6",
                                "regGroupCode": "1017",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "1814db45-3017-4b3d-9423-1f461d240c9a": {
                                "regGroupId": "1814db45-3017-4b3d-9423-1f461d240c9a",
                                "regGroupCode": "1015",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "cb077c6a-132a-4b64-870e-32a29f906844": {
                                "regGroupId": "cb077c6a-132a-4b64-870e-32a29f906844",
                                "regGroupCode": "1013",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "5aaf7bb5-383e-4951-8c94-02ba4cd2bca9": {
                                "regGroupId": "5aaf7bb5-383e-4951-8c94-02ba4cd2bca9",
                                "regGroupCode": "1016",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "1dd9caa4-70a9-4b0a-9f68-87f1a20a9495",
                        "activityOfferingTypeName": "Lecture",
                        "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                        "activityOfferingCode": "A",
                        "seatsAvailable": 185,
                        "seatsOpen": 185,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": true,
                                "tue": false,
                                "wed": true,
                                "thu": false,
                                "fri": true,
                                "sat": false,
                                "startTime": "8:00 am",
                                "endTime": "8:50 am",
                                "displayTime": "8:00-8:50am",
                                "days": "MWF",
                                "buildingCode": "CHM",
                                "roomCode": "1407",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "WALLS, MARTINA",
                                "firstName": "MARTINA",
                                "lastName": "WALLS",
                                "activityOfferingId": "1dd9caa4-70a9-4b0a-9f68-87f1a20a9495",
                                "personId": "KS-8275",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "a982ea11-a16d-49ed-a45e-5c18f4a2a7ee": {
                                "regGroupId": "a982ea11-a16d-49ed-a45e-5c18f4a2a7ee",
                                "regGroupCode": "1001",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "08bcf038-440b-4f7f-9954-b337618c98f1": {
                                "regGroupId": "08bcf038-440b-4f7f-9954-b337618c98f1",
                                "regGroupCode": "1005",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "b5ed652e-da53-4f6a-8729-ff6b85313d2a": {
                                "regGroupId": "b5ed652e-da53-4f6a-8729-ff6b85313d2a",
                                "regGroupCode": "1004",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "d42d8baf-6f2b-4b42-80d6-af9b3153b286": {
                                "regGroupId": "d42d8baf-6f2b-4b42-80d6-af9b3153b286",
                                "regGroupCode": "1002",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "ffd32efd-8309-40b8-81a4-975c849ae9f5": {
                                "regGroupId": "ffd32efd-8309-40b8-81a4-975c849ae9f5",
                                "regGroupCode": "1003",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "d6ff7666-e2dd-476f-a18c-d441e3cff85f": {
                                "regGroupId": "d6ff7666-e2dd-476f-a18c-d441e3cff85f",
                                "regGroupCode": "1006",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "1959ab09-8146-49a2-a6dd-bad7ada602c9",
                        "activityOfferingTypeName": "Lecture",
                        "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                        "activityOfferingCode": "AG",
                        "seatsAvailable": 52,
                        "seatsOpen": 52,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": true,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "6:30 pm",
                                "endTime": "9:30 pm",
                                "displayTime": "6:30-9:30pm",
                                "days": "M",
                                "buildingCode": "CHM",
                                "roomCode": "1402",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "KRAMER, MATS",
                                "firstName": "MATS",
                                "lastName": "KRAMER",
                                "activityOfferingId": "1959ab09-8146-49a2-a6dd-bad7ada602c9",
                                "personId": "KS-11606",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "e4e4a06c-5245-4a38-970c-7fdba0d1825c": {
                                "regGroupId": "e4e4a06c-5245-4a38-970c-7fdba0d1825c",
                                "regGroupCode": "1028",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "63e979f5-50d5-4f15-a182-56428c427947": {
                                "regGroupId": "63e979f5-50d5-4f15-a182-56428c427947",
                                "regGroupCode": "1025",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "a6af216a-67f1-4091-972b-40c784a36dec": {
                                "regGroupId": "a6af216a-67f1-4091-972b-40c784a36dec",
                                "regGroupCode": "1030",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "927d2b89-3af1-415d-853d-32b6687ef0bf": {
                                "regGroupId": "927d2b89-3af1-415d-853d-32b6687ef0bf",
                                "regGroupCode": "1026",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "fa6daf84-3a37-40b6-a6eb-697b2841e362",
                        "activityOfferingTypeName": "Lecture",
                        "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                        "activityOfferingCode": "H",
                        "seatsAvailable": 185,
                        "seatsOpen": 185,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": true,
                                "tue": false,
                                "wed": true,
                                "thu": false,
                                "fri": true,
                                "sat": false,
                                "startTime": "10:00 am",
                                "endTime": "10:50 am",
                                "displayTime": "10:00-10:50am",
                                "days": "MWF",
                                "buildingCode": "CHM",
                                "roomCode": "1407",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "JARVIS, MATTHEW",
                                "firstName": "MATTHEW",
                                "lastName": "JARVIS",
                                "activityOfferingId": "fa6daf84-3a37-40b6-a6eb-697b2841e362",
                                "personId": "KS-5193",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "c37e4aa3-53ff-45d1-8901-d2212f6f4e20": {
                                "regGroupId": "c37e4aa3-53ff-45d1-8901-d2212f6f4e20",
                                "regGroupCode": "1012",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "ad1d8e2e-3d3c-49e3-a247-0a7afb220519": {
                                "regGroupId": "ad1d8e2e-3d3c-49e3-a247-0a7afb220519",
                                "regGroupCode": "1008",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "74b5baac-8a07-414e-ad4f-e9a673bb3fd5": {
                                "regGroupId": "74b5baac-8a07-414e-ad4f-e9a673bb3fd5",
                                "regGroupCode": "1010",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "ee5e5fc5-ade2-4be4-9eea-456f0b7b83f0": {
                                "regGroupId": "ee5e5fc5-ade2-4be4-9eea-456f0b7b83f0",
                                "regGroupCode": "1009",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "792e2c2e-9ce2-46f6-a6c9-442e1e78e814": {
                                "regGroupId": "792e2c2e-9ce2-46f6-a6c9-442e1e78e814",
                                "regGroupCode": "1007",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "e92dbaeb-625b-4183-b0dd-b32382d700fc": {
                                "regGroupId": "e92dbaeb-625b-4183-b0dd-b32382d700fc",
                                "regGroupCode": "1011",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "5857b2bd-a872-4409-a4ad-9ef6341d4ee3",
                        "activityOfferingTypeName": "Lecture",
                        "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                        "activityOfferingCode": "V",
                        "seatsAvailable": 185,
                        "seatsOpen": 185,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": true,
                                "tue": false,
                                "wed": true,
                                "thu": false,
                                "fri": true,
                                "sat": false,
                                "startTime": "1:00 pm",
                                "endTime": "1:50 pm",
                                "displayTime": "1:00-1:50pm",
                                "days": "MWF",
                                "buildingCode": "CHM",
                                "roomCode": "1407",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "BOYER, TRUMAN",
                                "firstName": "TRUMAN",
                                "lastName": "BOYER",
                                "activityOfferingId": "5857b2bd-a872-4409-a4ad-9ef6341d4ee3",
                                "personId": "KS-7322",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "3161f942-1ebc-4c57-8bb2-004941642245": {
                                "regGroupId": "3161f942-1ebc-4c57-8bb2-004941642245",
                                "regGroupCode": "1021",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "19bb3435-e72f-4c56-a12c-d866a8ad2a44": {
                                "regGroupId": "19bb3435-e72f-4c56-a12c-d866a8ad2a44",
                                "regGroupCode": "1019",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "cf2418ad-6996-4e30-8178-6d41866baa0d": {
                                "regGroupId": "cf2418ad-6996-4e30-8178-6d41866baa0d",
                                "regGroupCode": "1020",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "1746db8c-7a06-49ad-827e-c038af58d083": {
                                "regGroupId": "1746db8c-7a06-49ad-827e-c038af58d083",
                                "regGroupCode": "1022",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "42c7c5a7-f94c-4c74-9b5f-2721a352e18f": {
                                "regGroupId": "42c7c5a7-f94c-4c74-9b5f-2721a352e18f",
                                "regGroupCode": "1024",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "700e878a-9bc3-44d7-bfc0-89d55966a60c": {
                                "regGroupId": "700e878a-9bc3-44d7-bfc0-89d55966a60c",
                                "regGroupCode": "1023",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "d0e20eb1-f4b5-4ad6-831c-4a1f06c8f0fa",
                        "activityOfferingTypeName": "Lecture",
                        "activityOfferingType": "kuali.lui.type.activity.offering.lecture",
                        "activityOfferingCode": "AC",
                        "seatsAvailable": 96,
                        "seatsOpen": 96,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "5:00 pm",
                                "endTime": "6:15 pm",
                                "displayTime": "5:00-6:15pm",
                                "days": "TuTh",
                                "buildingCode": "CHM",
                                "roomCode": "1407",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "GRAY, GAIL",
                                "firstName": "GAIL",
                                "lastName": "GRAY",
                                "activityOfferingId": "d0e20eb1-f4b5-4ad6-831c-4a1f06c8f0fa",
                                "personId": "KS-7223",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "3b75a159-6846-4fd3-8df2-474d1fdcc90e": {
                                "regGroupId": "3b75a159-6846-4fd3-8df2-474d1fdcc90e",
                                "regGroupCode": "1033",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "5af65ba0-221e-4019-9413-39d0d2602fcc": {
                                "regGroupId": "5af65ba0-221e-4019-9413-39d0d2602fcc",
                                "regGroupCode": "1034",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "eeaa4013-8032-48a4-9492-7de49cd6dde5": {
                                "regGroupId": "eeaa4013-8032-48a4-9492-7de49cd6dde5",
                                "regGroupCode": "1029",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "399abffa-c66f-40b2-8cac-440d17915dbb": {
                                "regGroupId": "399abffa-c66f-40b2-8cac-440d17915dbb",
                                "regGroupCode": "1031",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    }
                ]
            },
            {
                "activityOfferingCode": "Dis",
                "activityOfferingTypeName": "Discussion",
                "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                "activityOfferings": [
                    {
                        "activityOfferingId": "605c7f9b-6757-40b4-850f-36ed8501e097",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "W",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
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
                                "endTime": "1:20 pm",
                                "displayTime": "12:30-1:20pm",
                                "days": "Tu",
                                "buildingCode": "CSS",
                                "roomCode": "1113",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "PRUITT, KEVIN",
                                "firstName": "KEVIN",
                                "lastName": "PRUITT",
                                "activityOfferingId": "605c7f9b-6757-40b4-850f-36ed8501e097",
                                "personId": "KS-9337",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "1746db8c-7a06-49ad-827e-c038af58d083": {
                                "regGroupId": "1746db8c-7a06-49ad-827e-c038af58d083",
                                "regGroupCode": "1022",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "fc986edd-0c41-4f4f-a49c-2ace05ae7a2c",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "C",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "9:30 am",
                                "endTime": "10:20 am",
                                "displayTime": "9:30-10:20am",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "2201",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "HAWKINS, KENDALL",
                                "firstName": "KENDALL",
                                "lastName": "HAWKINS",
                                "activityOfferingId": "fc986edd-0c41-4f4f-a49c-2ace05ae7a2c",
                                "personId": "KS-2953",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "d6ff7666-e2dd-476f-a18c-d441e3cff85f": {
                                "regGroupId": "d6ff7666-e2dd-476f-a18c-d441e3cff85f",
                                "regGroupCode": "1006",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "a3132fda-f07a-4ab7-b954-29f411e0dd8c",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "D",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
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
                                "endTime": "1:20 pm",
                                "displayTime": "12:30-1:20pm",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "2201",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "HOFFMAN, SHANNON",
                                "firstName": "SHANNON",
                                "lastName": "HOFFMAN",
                                "activityOfferingId": "a3132fda-f07a-4ab7-b954-29f411e0dd8c",
                                "personId": "KS-10762",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "08bcf038-440b-4f7f-9954-b337618c98f1": {
                                "regGroupId": "08bcf038-440b-4f7f-9954-b337618c98f1",
                                "regGroupCode": "1005",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "77f3d52d-5ee5-4470-9f00-05dfec7e00b8",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "B",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "8:00 am",
                                "endTime": "8:50 am",
                                "displayTime": "8:00-8:50am",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "2201",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "CLINE, AARON",
                                "firstName": "AARON",
                                "lastName": "CLINE",
                                "activityOfferingId": "77f3d52d-5ee5-4470-9f00-05dfec7e00b8",
                                "personId": "KS-2522",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "d42d8baf-6f2b-4b42-80d6-af9b3153b286": {
                                "regGroupId": "d42d8baf-6f2b-4b42-80d6-af9b3153b286",
                                "regGroupCode": "1002",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "2516452f-e76e-494a-a0fa-25ea443e4ad2",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "AE",
                        "seatsAvailable": 32,
                        "seatsOpen": 32,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": true,
                                "sat": false,
                                "startTime": "12:00 pm",
                                "endTime": "12:50 pm",
                                "displayTime": "12:00-12:50pm",
                                "days": "F",
                                "buildingCode": "CHM",
                                "roomCode": "0127",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "LOVE, KRISZTINA",
                                "firstName": "KRISZTINA",
                                "lastName": "LOVE",
                                "activityOfferingId": "2516452f-e76e-494a-a0fa-25ea443e4ad2",
                                "personId": "KS-2228",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "927d2b89-3af1-415d-853d-32b6687ef0bf": {
                                "regGroupId": "927d2b89-3af1-415d-853d-32b6687ef0bf",
                                "regGroupCode": "1026",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "399abffa-c66f-40b2-8cac-440d17915dbb": {
                                "regGroupId": "399abffa-c66f-40b2-8cac-440d17915dbb",
                                "regGroupCode": "1031",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "81748a8c-8065-441d-ad01-1a48903aea3a",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "E",
                        "seatsAvailable": 20,
                        "seatsOpen": 20,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "8:00 am",
                                "endTime": "8:50 am",
                                "displayTime": "8:00-8:50am",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "2201",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "STEVENSON, RUSSELL",
                                "firstName": "RUSSELL",
                                "lastName": "STEVENSON",
                                "activityOfferingId": "81748a8c-8065-441d-ad01-1a48903aea3a",
                                "personId": "KS-11583",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "ffd32efd-8309-40b8-81a4-975c849ae9f5": {
                                "regGroupId": "ffd32efd-8309-40b8-81a4-975c849ae9f5",
                                "regGroupCode": "1003",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "6237a03e-ae32-43ce-9f81-e83131c88f72",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "Y",
                        "seatsAvailable": 20,
                        "seatsOpen": 20,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "3:30 pm",
                                "endTime": "4:20 pm",
                                "displayTime": "3:30-4:20pm",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "0122",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "RICHMOND, BROOKS",
                                "firstName": "BROOKS",
                                "lastName": "RICHMOND",
                                "activityOfferingId": "6237a03e-ae32-43ce-9f81-e83131c88f72",
                                "personId": "KS-7151",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "42c7c5a7-f94c-4c74-9b5f-2721a352e18f": {
                                "regGroupId": "42c7c5a7-f94c-4c74-9b5f-2721a352e18f",
                                "regGroupCode": "1024",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "3dac6a67-115a-4842-a601-b4046bbd75a7",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "Z",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
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
                                "endTime": "1:20 pm",
                                "displayTime": "12:30-1:20pm",
                                "days": "Th",
                                "buildingCode": "PHY",
                                "roomCode": "0405",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "LANDRY, MORGAN",
                                "firstName": "MORGAN",
                                "lastName": "LANDRY",
                                "activityOfferingId": "3dac6a67-115a-4842-a601-b4046bbd75a7",
                                "personId": "KS-9059",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "3161f942-1ebc-4c57-8bb2-004941642245": {
                                "regGroupId": "3161f942-1ebc-4c57-8bb2-004941642245",
                                "regGroupCode": "1021",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "0440756b-33f7-43a6-b0d6-81fc6c19b3e5",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "AH",
                        "seatsAvailable": 28,
                        "seatsOpen": 28,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": true,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "6:30 pm",
                                "endTime": "7:20 pm",
                                "displayTime": "6:30-7:20pm",
                                "days": "W",
                                "buildingCode": "CHM",
                                "roomCode": "0122",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "WEISS, WEI",
                                "firstName": "WEI",
                                "lastName": "WEISS",
                                "activityOfferingId": "0440756b-33f7-43a6-b0d6-81fc6c19b3e5",
                                "personId": "KS-11711",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "e4e4a06c-5245-4a38-970c-7fdba0d1825c": {
                                "regGroupId": "e4e4a06c-5245-4a38-970c-7fdba0d1825c",
                                "regGroupCode": "1028",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "5af65ba0-221e-4019-9413-39d0d2602fcc": {
                                "regGroupId": "5af65ba0-221e-4019-9413-39d0d2602fcc",
                                "regGroupCode": "1034",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "555a7879-440a-4b76-9775-88b400005d82",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "S",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
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
                                "endTime": "1:20 pm",
                                "displayTime": "12:30-1:20pm",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "0128",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "WORKMAN, BRIAN",
                                "firstName": "BRIAN",
                                "lastName": "WORKMAN",
                                "activityOfferingId": "555a7879-440a-4b76-9775-88b400005d82",
                                "personId": "KS-6727",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "ac729767-065a-43eb-868d-4c1e44c92705": {
                                "regGroupId": "ac729767-065a-43eb-868d-4c1e44c92705",
                                "regGroupCode": "1014",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "a31b210f-d610-4dca-90cc-e6bdcc573417",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "K",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "11:00 am",
                                "endTime": "11:50 am",
                                "displayTime": "11:00-11:50am",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "0119",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "WILLIAMS, IDO",
                                "firstName": "IDO",
                                "lastName": "WILLIAMS",
                                "activityOfferingId": "a31b210f-d610-4dca-90cc-e6bdcc573417",
                                "personId": "KS-3364",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "c37e4aa3-53ff-45d1-8901-d2212f6f4e20": {
                                "regGroupId": "c37e4aa3-53ff-45d1-8901-d2212f6f4e20",
                                "regGroupCode": "1012",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "d7bcc977-9a3a-45be-a384-cdca52751e99",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "X",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "2:00 pm",
                                "endTime": "2:50 pm",
                                "displayTime": "2:00-2:50pm",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "0119",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "TRUJILLO, KATHERINE",
                                "firstName": "KATHERINE",
                                "lastName": "TRUJILLO",
                                "activityOfferingId": "d7bcc977-9a3a-45be-a384-cdca52751e99",
                                "personId": "KS-7468",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "700e878a-9bc3-44d7-bfc0-89d55966a60c": {
                                "regGroupId": "700e878a-9bc3-44d7-bfc0-89d55966a60c",
                                "regGroupCode": "1023",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "288d0530-2843-44e1-b4dc-caf58f05b4c9",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "L",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "9:30 am",
                                "endTime": "10:20 am",
                                "displayTime": "9:30-10:20am",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "0119",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "BRIGHT, SUBHADEEP",
                                "firstName": "SUBHADEEP",
                                "lastName": "BRIGHT",
                                "activityOfferingId": "288d0530-2843-44e1-b4dc-caf58f05b4c9",
                                "personId": "KS-6316",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "ad1d8e2e-3d3c-49e3-a247-0a7afb220519": {
                                "regGroupId": "ad1d8e2e-3d3c-49e3-a247-0a7afb220519",
                                "regGroupCode": "1008",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "79ac9218-fd27-497a-9db1-76e65780a92d",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "AA",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "2:00 pm",
                                "endTime": "2:50 pm",
                                "displayTime": "2:00-2:50pm",
                                "days": "Th",
                                "buildingCode": "PHY",
                                "roomCode": "0405",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "CLEMENTS, SUSAN",
                                "firstName": "SUSAN",
                                "lastName": "CLEMENTS",
                                "activityOfferingId": "79ac9218-fd27-497a-9db1-76e65780a92d",
                                "personId": "KS-4761",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "19bb3435-e72f-4c56-a12c-d866a8ad2a44": {
                                "regGroupId": "19bb3435-e72f-4c56-a12c-d866a8ad2a44",
                                "regGroupCode": "1019",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "6dd9e8e1-1f18-4851-86c9-f57b119c43fc",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "P",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
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
                                "endTime": "1:20 pm",
                                "displayTime": "12:30-1:20pm",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "0128",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "SNYDER, PAMELA",
                                "firstName": "PAMELA",
                                "lastName": "SNYDER",
                                "activityOfferingId": "6dd9e8e1-1f18-4851-86c9-f57b119c43fc",
                                "personId": "KS-8302",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "1814db45-3017-4b3d-9423-1f461d240c9a": {
                                "regGroupId": "1814db45-3017-4b3d-9423-1f461d240c9a",
                                "regGroupCode": "1015",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "21324d18-7629-4e35-b6fb-b99d5eaf068a",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "M",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
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
                                "endTime": "1:20 pm",
                                "displayTime": "12:30-1:20pm",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "0119",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "BENSON, LOUIS",
                                "firstName": "LOUIS",
                                "lastName": "BENSON",
                                "activityOfferingId": "21324d18-7629-4e35-b6fb-b99d5eaf068a",
                                "personId": "KS-12157",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "74b5baac-8a07-414e-ad4f-e9a673bb3fd5": {
                                "regGroupId": "74b5baac-8a07-414e-ad4f-e9a673bb3fd5",
                                "regGroupCode": "1010",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "80a49e72-0ec0-4c6a-9b9a-cbf4754df8e0",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "G",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
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
                                "endTime": "1:20 pm",
                                "displayTime": "12:30-1:20pm",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "1224",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "CANNON, TIMOTHY",
                                "firstName": "TIMOTHY",
                                "lastName": "CANNON",
                                "activityOfferingId": "80a49e72-0ec0-4c6a-9b9a-cbf4754df8e0",
                                "personId": "KS-2468",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "a982ea11-a16d-49ed-a45e-5c18f4a2a7ee": {
                                "regGroupId": "a982ea11-a16d-49ed-a45e-5c18f4a2a7ee",
                                "regGroupCode": "1001",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "958fa3f6-67e8-42e8-8000-4521b1ed4aa2",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "AD",
                        "seatsAvailable": 32,
                        "seatsOpen": 32,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": true,
                                "sat": false,
                                "startTime": "11:00 am",
                                "endTime": "11:50 am",
                                "displayTime": "11:00-11:50am",
                                "days": "F",
                                "buildingCode": "CHM",
                                "roomCode": "0127",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "DODSON, KATHERINE",
                                "firstName": "KATHERINE",
                                "lastName": "DODSON",
                                "activityOfferingId": "958fa3f6-67e8-42e8-8000-4521b1ed4aa2",
                                "personId": "KS-4543",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "63e979f5-50d5-4f15-a182-56428c427947": {
                                "regGroupId": "63e979f5-50d5-4f15-a182-56428c427947",
                                "regGroupCode": "1025",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "eeaa4013-8032-48a4-9492-7de49cd6dde5": {
                                "regGroupId": "eeaa4013-8032-48a4-9492-7de49cd6dde5",
                                "regGroupCode": "1029",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "a82efe3c-6a70-45d8-8d02-7f306d429422",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "U",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "3:30 pm",
                                "endTime": "4:20 pm",
                                "displayTime": "3:30-4:20pm",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "0128",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "KEY, ALAN",
                                "firstName": "ALAN",
                                "lastName": "KEY",
                                "activityOfferingId": "a82efe3c-6a70-45d8-8d02-7f306d429422",
                                "personId": "KS-4611",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "5aaf7bb5-383e-4951-8c94-02ba4cd2bca9": {
                                "regGroupId": "5aaf7bb5-383e-4951-8c94-02ba4cd2bca9",
                                "regGroupCode": "1016",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "247b52bd-655d-42e8-853d-b7f983b61b9c",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "J",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "9:30 am",
                                "endTime": "10:20 am",
                                "displayTime": "9:30-10:20am",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "0119",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "MICHAEL, BARBARA",
                                "firstName": "BARBARA",
                                "lastName": "MICHAEL",
                                "activityOfferingId": "247b52bd-655d-42e8-853d-b7f983b61b9c",
                                "personId": "KS-6844",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "ee5e5fc5-ade2-4be4-9eea-456f0b7b83f0": {
                                "regGroupId": "ee5e5fc5-ade2-4be4-9eea-456f0b7b83f0",
                                "regGroupCode": "1009",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "39f581f3-dd55-404e-a813-5946d2cafa1e",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "AB",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "3:30 pm",
                                "endTime": "4:20 pm",
                                "displayTime": "3:30-4:20pm",
                                "days": "Th",
                                "buildingCode": "PHY",
                                "roomCode": "0405",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "GOULD, RICHARD",
                                "firstName": "RICHARD",
                                "lastName": "GOULD",
                                "activityOfferingId": "39f581f3-dd55-404e-a813-5946d2cafa1e",
                                "personId": "KS-2586",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "cf2418ad-6996-4e30-8178-6d41866baa0d": {
                                "regGroupId": "cf2418ad-6996-4e30-8178-6d41866baa0d",
                                "regGroupCode": "1020",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "97d5d33c-c7f8-48d0-91e7-c713d20a75b5",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "F",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "9:30 am",
                                "endTime": "10:20 am",
                                "displayTime": "9:30-10:20am",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "2201",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "DUFFY, JIASHAN",
                                "firstName": "JIASHAN",
                                "lastName": "DUFFY",
                                "activityOfferingId": "97d5d33c-c7f8-48d0-91e7-c713d20a75b5",
                                "personId": "KS-2467",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "b5ed652e-da53-4f6a-8729-ff6b85313d2a": {
                                "regGroupId": "b5ed652e-da53-4f6a-8729-ff6b85313d2a",
                                "regGroupCode": "1004",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "df72ce4b-9508-48b6-acd9-3b66a3fdbffc",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "N",
                        "seatsAvailable": 20,
                        "seatsOpen": 20,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "2:00 pm",
                                "endTime": "2:50 pm",
                                "displayTime": "2:00-2:50pm",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "0119",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "HENDRIX, DONALD",
                                "firstName": "DONALD",
                                "lastName": "HENDRIX",
                                "activityOfferingId": "df72ce4b-9508-48b6-acd9-3b66a3fdbffc",
                                "personId": "KS-8371",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "792e2c2e-9ce2-46f6-a6c9-442e1e78e814": {
                                "regGroupId": "792e2c2e-9ce2-46f6-a6c9-442e1e78e814",
                                "regGroupCode": "1007",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "71ba801d-aea5-4def-9bcc-d4b6dde19cc7",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "AF",
                        "seatsAvailable": 32,
                        "seatsOpen": 32,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": true,
                                "sat": false,
                                "startTime": "1:00 pm",
                                "endTime": "1:50 pm",
                                "displayTime": "1:00-1:50pm",
                                "days": "F",
                                "buildingCode": "CHM",
                                "roomCode": "0127",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "BENJAMIN, AKIO",
                                "firstName": "AKIO",
                                "lastName": "BENJAMIN",
                                "activityOfferingId": "71ba801d-aea5-4def-9bcc-d4b6dde19cc7",
                                "personId": "KS-12180",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "a6af216a-67f1-4091-972b-40c784a36dec": {
                                "regGroupId": "a6af216a-67f1-4091-972b-40c784a36dec",
                                "regGroupCode": "1030",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            },
                            "3b75a159-6846-4fd3-8df2-474d1fdcc90e": {
                                "regGroupId": "3b75a159-6846-4fd3-8df2-474d1fdcc90e",
                                "regGroupCode": "1033",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "04798fdf-c118-4dff-be88-1dbee18bb657",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "R",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "3:30 pm",
                                "endTime": "4:20 pm",
                                "displayTime": "3:30-4:20pm",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "0128",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "CHRISTIAN, CORNELIS",
                                "firstName": "CORNELIS",
                                "lastName": "CHRISTIAN",
                                "activityOfferingId": "04798fdf-c118-4dff-be88-1dbee18bb657",
                                "personId": "KS-9165",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "96e77b6b-ad45-4431-8eee-613b1fd69de6": {
                                "regGroupId": "96e77b6b-ad45-4431-8eee-613b1fd69de6",
                                "regGroupCode": "1017",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "614eaec7-7644-4ff7-82c0-1a6aed120db2",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "T",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "2:00 pm",
                                "endTime": "2:50 pm",
                                "displayTime": "2:00-2:50pm",
                                "days": "Th",
                                "buildingCode": "CHM",
                                "roomCode": "0128",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "HATFIELD, LORNA",
                                "firstName": "LORNA",
                                "lastName": "HATFIELD",
                                "activityOfferingId": "614eaec7-7644-4ff7-82c0-1a6aed120db2",
                                "personId": "KS-11744",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "cb077c6a-132a-4b64-870e-32a29f906844": {
                                "regGroupId": "cb077c6a-132a-4b64-870e-32a29f906844",
                                "regGroupCode": "1013",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "1e43d997-981f-4293-9817-a73a154de841",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "I",
                        "seatsAvailable": 33,
                        "seatsOpen": 33,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "8:00 am",
                                "endTime": "8:50 am",
                                "displayTime": "8:00-8:50am",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "0119",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "GIBSON, ALYN",
                                "firstName": "ALYN",
                                "lastName": "GIBSON",
                                "activityOfferingId": "1e43d997-981f-4293-9817-a73a154de841",
                                "personId": "KS-8591",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "e92dbaeb-625b-4183-b0dd-b32382d700fc": {
                                "regGroupId": "e92dbaeb-625b-4183-b0dd-b32382d700fc",
                                "regGroupCode": "1011",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    },
                    {
                        "activityOfferingId": "db107fd2-a643-48a5-886c-619fa1ee09f0",
                        "activityOfferingTypeName": "Discussion",
                        "activityOfferingType": "kuali.lui.type.activity.offering.discussion",
                        "activityOfferingCode": "Q",
                        "seatsAvailable": 35,
                        "seatsOpen": 35,
                        "maxWaitListSize": null,
                        "waitListSize": null,
                        "scheduleComponents": [
                            {
                                "sun": false,
                                "mon": false,
                                "tue": false,
                                "wed": false,
                                "thu": false,
                                "fri": false,
                                "sat": false,
                                "startTime": "2:00 pm",
                                "endTime": "2:50 pm",
                                "displayTime": "2:00-2:50pm",
                                "days": "Tu",
                                "buildingCode": "CHM",
                                "roomCode": "0128",
                                "isTBA": false
                            }
                        ],
                        "instructors": [
                            {
                                "displayName": "SYKES, JULIA",
                                "firstName": "JULIA",
                                "lastName": "SYKES",
                                "activityOfferingId": "db107fd2-a643-48a5-886c-619fa1ee09f0",
                                "personId": "KS-11468",
                                "primary": true
                            }
                        ],
                        "regGroupInfos": {
                            "1584f79a-d91e-4c3c-be03-7964362f5dc7": {
                                "regGroupId": "1584f79a-d91e-4c3c-be03-7964362f5dc7",
                                "regGroupCode": "1018",
                                "waitListOffered": true,
                                "maxWaitListSize": null,
                                "waitListSize": 0
                            }
                        },
                        "requisites": [],
                        "subterm": null,
                        "honors": false
                    }
                ]
            }
        ]
    }});