'use strict';

angular.module('regCartApp')

    /*
     *
     * COURSE_DETAILS is an example of a configuration setting for search-details-list-directive.js, the
     * directive which displays search details in the mobile view. It is currently in use for
     * course search details.
     *
     * Additional configurations can be built using this template, and dynamically injected
     * by name using the DetailsFactory (details-factory.js).
     *
     * See below for details on each configuration item.
     *
     */
    .constant('COURSE_DETAILS', {
        /*
         *
         * Defines each tab, and the fields that go into it. Additionally, the directive will
         * draw an "ALL" tab, which can be selected to display all tabs at once. The all tab
         * has an id of 'all'.
         *
         * Options:
         *      id: the id of the tab
         *      selected: boolean indicating whether the tab is selected by default. Only one should be set to true.
         *      name: the name of the tab to be displayed to the user
         *      fields: an array of fields that will be shown if the tab is selected
         *
         */
        tabs: [
            {
                id: 'time',
                selected: true,
                name: 'Time',
                fields: ['dateTime'],
                conflict: false
            },
            {
                id: 'instr',
                selected: false,
                name: 'Instr',
                fields: ['instructor'],
                conflict: false
            },
            {
                id: 'loc',
                selected: false,
                name: 'Loc',
                fields: ['location'],
                conflict: false
            },
            {
                id: 'seats',
                selected: false,
                name: 'Seats',
                fields: ['seatsOpen'],
                conflict: false
            }
        ],
        // selectedTab defines which tab is selected by default (by id).
        selectedTab: 'time',
        /*
         * selectedEvent: if provided, the directive will emit this event by name when a row is selected,
         * along with the data for the row.
         */
        selectEvent: 'toggleAO',
        /*
         *
         * fieldOptions allows for advanced field-specific configuration
         *
         * Options:
         *      right: if true, the field will align to the right
         *      block: if true, the field will be displayed as a block if the 'all' tab is selected
         *      append: if set, the text will be appended to the end of the field when displayed
         *      highlight: if set, the text will be highlighted
         *
         */
        fieldOptions: {
            dateTime: {
                highlight: true
            },
            instructor: {
                block: true
            },
            location: {
                block: true
            },
            seatsOpen: {
                append: 'available',
                conflict: true
            }
        },
        /*
         * icons is an optional configuration. if set, the field name given will be displayed as
         * an icon (always tappable)
         */
        icons: 'additionalInfo',
        /*
         * getSections() is a method that takes the entire array of search details and breaks it
         * into sections.
         *
         * Each section object has two variables:
         *      header: the header to be displayed at the top of the section
         *      details: an array of search detail objects to be displayed in the section
         *
         * This can be coded to split things up however the data needs it. To display everything
         * as one section simply return an array with all the search details included e.g.
         *      return [{header: 'My Header', details: searchDetails}];
         */
        getSections: function(searchDetails) {
            var sections = [];
            for (var i=0; i<searchDetails.length; i++) {
                var header=searchDetails[i].activity;
                var newAoType = true;
                for (var j=0; j<sections.length; j++) {
                    if (sections[j].header === header) {
                        newAoType = false;
                        sections[j].details.push(searchDetails[i]);
                        break;
                    }
                }
                if (newAoType) {
                    var detailContainer={header: header, details: []};
                    detailContainer.details.push(searchDetails[i]);
                    sections.push(detailContainer);
                }
            }
            return sections;
        },
        /*
         *
         * Helper method to set the "default field" on each row. This is used to
         * set the id of checkboxes (primarily used for AFTs).
         *
         */
        setDefaultField: function(searchDetails) {
            for (var i=0; i<searchDetails.length; i++) {
                searchDetails[i].defaultField = searchDetails[i].aoId;
            }
        }
    })
;
