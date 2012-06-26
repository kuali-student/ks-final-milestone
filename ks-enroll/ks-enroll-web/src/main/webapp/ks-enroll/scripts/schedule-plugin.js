(function($) {

    /**
     * Sets up the schedule calendar. This function should be used only on a div
     * which contains a table with the 'schedule' css class, formatted in the required fashion
     * (day columns, timeRows, inner div in cells with position: relative set, etc... see schedule.jsp),
     * and a 'scheduleKey' table in the appropriate format
     * @param options - contains various options as follows:
     * sundayFirst - true when sunday occurs first on the schedule
     * startTime - military time that the calendar starts at
     * endTime - military time that the calendar ends at (24 ends at midnight, last hour being the 23rd)
     * useLetterKeys - in addition to colors key by letter
     * omitWeekend - weekend is not included in this calender if true
     * data - the initial data to set this calendar with in the following array format:
     * [{days: ["MO", "TU"],
     *  startTime: "1500",
     *  endTime: "1600"},
     * <additional time objects>...]
     */
    $.fn.schedule = function(options) {

        return this.each(function() {
            options = options || {};
            //default setting
            options = $.extend({
                sundayFirst: true,
                startTime: 5,
                endTime: 20,
                useLetterKeys: true,
                omitWeekend: false,
                data: null
            }, options);

            $.data(this, "options", options);
            initScheduleVars(this);

            var cellWidth = $(this).find(".schedule td").css("width").replace("px", "");
            var borderWidth = 1;
            var tableWidth;
            if (options.omitWeekend) {
                tableWidth = (cellWidth * 6) + (borderWidth * 6);
            }
            else {
                tableWidth = (cellWidth * 8) + (borderWidth * 8);
            }
            $(this).find(".schedule").width(tableWidth);
            if (options.data != null) {
                $(this).initSchedule(options.data);
            }
        });

    };

    /**
     * initSchedule will clear all timeBlocks and keys on this schedule before adding the data to
     * both the schedule table and local keys.
     *
     * @param data - the initial data to set this calendar with in the following array format:
     * [{days: ["MO", "TU"],
     *  startTime: "1500",
     *  endTime: "1600"},
     * <additional time objects>...]
     */
    $.fn.initSchedule = function(data) {
        $(this).find(".timeBlock").remove();
        $(this).find(".keyRow").remove();
        var schedule = this[0];
        var options = $.data(schedule, "options");
        options.data = data;
        if (data != null) {
            $.each(data, function(index, value) {
                var name = value.timeId;
                if (name == null || name == "") {
                    name = genBlockName(schedule);
                }
                $(schedule).addTimeAndKey(name, value.days, value.startTime, value.endTime, value.name, value.timeType,
                    value.displayableTime, value.cssClass);
            });
        }
    }

    /**
     * Adds a handler on the selected element which shows timeBlocks on the schedule identified by id with the
     * time data specified when it is hovered over.
     * @param scheduleId
     * @param data
     */
    $.fn.addSchedulePreviewMultipleTimesHandler = function(scheduleId, data) {
        var name = genBlockName($("#" + scheduleId)[0]);
        $(this).hover(
            function () {
                $.each(data, function(index, value) {
                    $("#" + scheduleId).addTime(name, value.days, value.startTime, value.endTime, value.name, value.timeType, value.displayableTime, "previewTimeBlock", false);
                });
            },
            function () {
                $("#" + scheduleId).removeTime(name);
            }
        );
    };

    /**
     * Adds a handler on the selected element which shows timeBlocks on the schedule identified by id with the
     * time data specified when it is hovered over.  This is different from addSchedulePreviewMultipleTimesHandler
     * because this is for only time data instead of multiple.
     * @param scheduleId
     * @param data
     */
    $.fn.addSchedulePreviewSingleTimeHandler = function(scheduleId, days, startTime, endTime) {
        var name = genBlockName($("#" + scheduleId)[0]);
        $(this).hover(
            function () {
                $("#" + scheduleId).addTime(name, value.days, value.startTime, value.endTime, value.name, value.timeType, value.displayableTime, "previewTimeBlock", false);
            },
            function () {
                $("#" + scheduleId).removeTime(name);
            }
        );
    };

    /**
     * Adds multiple times and keys to the schedule selected with the time data supplied.  The keys are added
     * to the key 'scheduleKey' table in the div specified by keyDivId.  If keyDivId is not specified,
     * the key is added to this schedules default key table.  Used by scheduleCart.  This method does not clear
     * data on the schedule before adding.
     * @param data
     * @param keyDivId
     */
    $.fn.addBulkTimesAndKeys = function(data, keyDivId) {
        var schedule = this[0];
        if (data != null) {
            $.each(data, function(index, value) {
                var name = value.timeId;
                if (name == null || name == "") {
                    name = genBlockName(schedule);
                }
                $(schedule).addTime(name, value.days, value.startTime, value.endTime, value.name, value.timeType,
                    value.displayableTime, value.cssClass, true);
                if (keyDivId != null) {
                    $("#" + keyDivId).addKey(name, value.name, value.timeType,
                        value.displayableTime, value.cssClass, true, $(schedule).attr("id"));
                }
                else {
                    $(schedule).addKey(name, value.name, value.timeType,
                        value.displayableTime, value.cssClass, true, $(schedule).attr("id"));
                }
            });
        }

    };

    $.fn.addButton = function(name, jsFunction) {
        var keyTable = this[0];
        if (name != null && jsFunction != null) {
            $(keyTable).find("tr").each( function(){
                if(!($(this).find("th").length > 0)){
                    var button =  $("<a class='key-button'>" + name + "</a>").click(jsFunction);
                    $(this).append("<td>").append(button);
                }
                else{
                    $(this).append("<th>");
                }
            });
        }

    };

    /**
     * Adds a time and key to the schedule selected.
     * @param name - the html name attribute given to all timeBlocks for this
     * time, essentially working as its unique identifier
     * @param days - array of days for this time
     * @param startTime - startTime in military time
     * @param endTime - endTime in miliatary time
     * @param timeName - human readable name (in html) for this time to be used in key and on hover, will not be used if there is a match on name with
     * an existing block - it will be generated as a combination of this timeName and the block's timeName
     * @param typeName - type of this time ie lecture, lab, discussion - future matches on name and type will be grouped appropriately in
     * the same timeName
     * @param displayableTime - the times in human readable form (in html)
     * @param cssColorClass - css class that must define a background-color, if null, the background-color is generated per
     * timeBlock name
     */
    $.fn.addTimeAndKey = function(name, days, startTime, endTime, timeName, typeName, displayableTime, cssColorClass) {
        $(this).addTime(name, days, startTime, endTime, timeName, typeName, displayableTime, cssColorClass, true);
        $(this).addKey(name, timeName, typeName, displayableTime, cssColorClass, true, null);
    };

    /**
     * Adds a time to the schedule selected.  Times are timeBlock divs in the schedule, their width is determined
     * as function of the width of the table's cell width.  TimeBlock height is also determined dynamically based on
     * cell height and time length.  When a time block is in conflict when added, the current
     * time block is offset and the new block gets the 'timeBlock-conflict-new' class - this only properly works once - intended
     * use is for hover, as times should never conflict in a correct schedule (should never get to the point where that
     * conflicting time is permanent).
     * @param name - the html name attribute given to all timeBlocks for this
     * time, essentially working as its unique identifier
     * @param days - array of days for this time
     * @param startTime - startTime in military time
     * @param endTime - endTime in miliatary time
     * @param timeName - human readable name (in html) for this time to be used on hover, will not be used if there is a match on name with
     * an existing block - it will be generated as a combination of this timeName and the block's timeName
     * @param typeName - type of this time ie lecture, lab, discussion - future matches on name and type will be grouped appropriately in
     * the same timeName
     * @param displayableTime - the times in human readable form (in html)
     * @param cssColorClass - css class that must define a background-color, if null, the background-color is generated per
     * timeBlock name
     */
    $.fn.addTime = function(name, days, startTime, endTime, timeName, typeName, displayableTime, cssColorClass, useLetter) {
        var options = $.data(this[0], "options");
        var vars = $.data(this[0], "vars");
        var schedule = this[0];
        //TODO check for time conflict here maybe?
        var startIndex = determineStartIndex(schedule, startTime.substring(0, 2));
        var startMinFraction = (startTime.substring(2, 4)) / 60;
        var minDiff = ((endTime.substring(0, 2) * 60) + parseInt(endTime.substring(2, 4), 10)) - ((startTime.substring(0, 2) * 60) + parseInt(startTime.substring(2, 4), 10));
        var totalHours = minDiff / 60;

        //if totalHours outside of schedule range, cut off
        if (parseInt(startTime.substring(0, 2), 10) + startMinFraction + totalHours > (options.endTime + 1)) {
            totalHours = (options.endTime + 1) - (parseInt(startTime.substring(0, 2), 10) + startMinFraction);
        }

        var content;
        if (options.useLetterKeys && useLetter) {
            vars.charCode = vars.charCode + 1;
            content = String.fromCharCode(vars.charCode);
        }
        else {
            content = "&nbsp;"
        }
        var generatedColor = findTimeBlockColor(schedule, name);
        if (!cssColorClass && generatedColor == "") {
            updateColor(schedule);
            generatedColor = getGeneratedColor(schedule);
        }

        var generatedTimeName = "";
        if (timeName && typeName && displayableTime) {
            generatedTimeName = genTimeName(schedule, name, timeName, typeName, displayableTime);
        }

        $.each(days, function(index, value) {
            var dayIndex = determineDayIndex(schedule, value);
            //-1 means an invalid date for this schedule so don't add it
            if (dayIndex != -1) {

                //find td that matches start time and start day index in schedule table
                var tdLoc = $(schedule).find(".timeRow:nth-child(" + startIndex + ")").find("td:nth-child(" + dayIndex + ")");

                var div = $('<div name="' + name + '" class="timeBlock">' + content + '</div>');
                if (cssColorClass) {
                    div.addClass(cssColorClass);
                }
                else {
                    //generate a shade if no css class assigned
                    div.css("background-color", generatedColor);
                }

                var divHeight = tdLoc.outerHeight() * totalHours;
                div.height(divHeight);

                var divWidth = Math.floor(tdLoc.width() / 2);
                div.width(divWidth);

                var top = tdLoc.height() * startMinFraction;
                div.css("top", top + "px");

                var conflicts = checkForConflicts(schedule, dayIndex, parseInt(startTime, 10), parseInt(endTime, 10));
                if (conflicts.length) {
                    //has time conflict
                    var left = Math.floor(tdLoc.width() / 8);
                    div.css("left", left + "px");

                    div.addClass("timeBlock-conflict-new");
                    $(conflicts).each(function() {
                        //Only offset original block (overlay additional conflicts visually)
                        //if we want to make this fully work with multiple time conflicts (unlikely) must add conflict
                        //information to the timeBlock data, so removal can behave correctly
                        if(!($(this).hasClass("timeBlock-conflict-new")) && !($(this).hasClass("timeBlock-conflict-old"))){
                            $(this).addClass("timeBlock-conflict-old");
                            $(this).css("left", (left * 3) + "px");
                        }
                    });
                }
                else {
                    //does not have time conflict
                    var left = Math.floor(tdLoc.width() / 4);
                    div.css("left", left + "px");
                }

                $(tdLoc).find("div:not('.timeBlock')").append(div);

                //add time block data to its element internally
                $.data(div[0], "timeData", genBlockData(dayIndex, parseInt(startTime, 10), parseInt(endTime, 10)));
            }
        });

        $(schedule).find("[name='" + name + "']").each(function() {
            if (generatedTimeName) {
                $(this).attr('title', generatedTimeName);
                $.data(this, "timeName", generatedTimeName);
            }
        });
    };

    /**
     * Remove times and keys from the selected schedule that match the name specified
     * @param name
     */
    $.fn.removeTimeAndKey = function(name) {
        $(this).removeTime(name);
        $(this).find("tr[name='" + name + "']").remove();
    };

    /**
     * Remove times from the schedule and reposition original block if conflicts are resolved (does
     * not behave properly if multiple conflicts - not intended to)
     * @param name
     */
    $.fn.removeTime = function(name) {
        var removals = $(this).find("div[name='" + name + "']");
        var schedule = this[0];
        var oldData = [];
        $(removals).each(function() {
            var data = $.data(this, "timeData");
            oldData.push(data);
        });
        $(removals).remove();

        $(oldData).each(function(index, value) {
            var conflicts = checkForConflicts(schedule, value.dayIndex, value.start, value.end);
            if (conflicts.length) {
                //had a time conflict that resolved, now return to original position
                $(conflicts).each(function() {
                    var tdLoc = $(this).closest("td");
                    var left = Math.floor(tdLoc.width() / 4);
                    $(this).removeClass("timeBlock-conflict-old");
                    $(this).css("left", left + "px");
                });
            }
        });
    };

    /**
     * Removes a key from the selected element which contains a table with a row matching the name of the key
     * @param name
     */
    $.fn.removeKey = function(name) {
        $(this).find("tr[name='" + name + "']").remove();
    };

    /**
     * Adds a key to the specified area
     * @param name - the html name attribute given to the timeBlocks this function is keying
     * @param keyName - human readable name (in html) for this time to be used in the key, will not be used if there is a match on name with
     * an existing block - it will be generated as a combination of this timeName and the existing timeName
     * @param typeName - type of this time ie lecture, lab, discussion - future matches on name and type will be grouped appropriately in
     * the same keyName
     * @param displayableTime - the times in human readable form (in html)
     * @param cssColorClass - css class that must define a background-color, if null, the background-color used is the last
     * generated one (or if a key for name is already present that the keyName will just use that)
     * @param useLetter - true if letter should be used in the key
     * @param scheduleId - if null assumes that this selected element is the schedule, otherwise it uses
     * this id to identify where the schedule table div that this key refers to exists
     */
    $.fn.addKey = function(name, keyName, typeName, displayableTime, cssColorClass, useLetter, scheduleId) {
        var appendage;
        var schedule;
        if (scheduleId == null) {
            //assume this is the schedule block when no id supplied
            schedule = this[0];
        }
        else {
            //otherwise assume this is another block that contains a .scheduleKey class table
            schedule = $("#" + scheduleId)[0];
        }
        var generatedTimeName = getTimeName(schedule, name, keyName, typeName, displayableTime);
        var options = $.data(schedule, "options");
        var vars = $.data(schedule, "vars");
        var content = "&nbsp;";
        if (options.useLetterKeys && useLetter) {
            content = String.fromCharCode(vars.charCode);
        }

        if (cssColorClass) {
            appendage = $("<tr class='keyRow' name='" + name + "'><td><div class='keyBlock " + cssColorClass + "'>" + content + "</div></td><td><div class='timeKeyName'>" + generatedTimeName + "</div></td></tr>");
        }
        else {
            appendage = $("<tr class='keyRow' name='" + name + "'><td><div class='keyBlock' style='background-color: " + getGeneratedColor(schedule) + "' >" + content + "</div></td><td><div class='timeKeyName'>" + generatedTimeName + "</div></td></tr>");
        }


        $(appendage).find("div").hover(
            function () {
                var blocks = $(schedule).find(".timeBlock[name='" + name + "']");

                $(blocks).each(function() {
                    $(this).addClass("timeHighlight");
                    var height = $(this).height();
                    var width = $(this).width();
                    $.data(this, "origHeight", height);
                    $.data(this, "origWidth", width);
                    if (!$.browser.msie) {
                        var borderWidth = $(this).css("borderBottomWidth").replace("px", "") * 2;
                        $(this).height(height - borderWidth);
                        $(this).width(width - borderWidth);
                    }
                });

            },
            function () {
                var blocks = $(schedule).find(".timeBlock[name='" + name + "']");

                $(blocks).each(function() {
                    var height = $.data(this, "origHeight");
                    var width = $.data(this, "origWidth");
                    if (!$.browser.msie) {
                        $(this).height(height);
                        $(this).width(width);
                    }
                    $(this).removeClass("timeHighlight");
                });


            }
        );
        var existingElements = $(this).find(".scheduleKey").find("[name='" + name + "']");
        if (existingElements.length) {
            $(existingElements).find(".timeKeyName").html(generatedTimeName);
        }
        else {
            $(this).find(".scheduleKey").append(appendage);
        }
    }

    /**
     * Removes all timeBlocks from the schedule table of the selected element
     */
    $.fn.clearTable = function() {
        $(this).find(".timeBlock").remove();
    };

    /**
     * Removes all timeBlocks and keyRows from the schedule table and scheduleKey of the selected element
     */
    $.fn.clearTableAndKeys = function() {
        $(this).find(".timeBlock").remove();
        $(this).find(".keyRow").remove();
    };

    /**
     * Removes all keyRows from the scheduleKey of the selected element
     */
    $.fn.clearKeys = function() {
        $(this).find(".keyRow").remove();
    };

    //private functions
    function initScheduleVars(schedule) {
        var vars = $.data(schedule, "vars");
        if (vars == null) {
            var localVars = {
                genRed : 40,
                genGreen : 40,
                genBlue : 40,
                colorIndex : 0,
                charCode : 64, //A - 1 is start
                nameIndex : 0
            };
            $.data(schedule, "vars", localVars);
        }
    }

    /**
     * Generates a unique name for a timeBlock for the schedule passed in
     * @param schedule
     */
    function genBlockName(schedule) {
        initScheduleVars(schedule);
        var vars = $.data(schedule, "vars");

        vars.nameIndex++;
        return "timeBlock" + vars.nameIndex;
    }

    function genBlockData(dayIndex, startTime, endTime) {
        return  {dayIndex: dayIndex, start: startTime, end: endTime};
    }

    /**
     * Checks for conflicts against the dayIndex, startTime, and endTime specified with other
     * timeBlocks within the schedule specified
     * @param schedule
     * @param dayIndex
     * @param startTime
     * @param endTime
     */
    function checkForConflicts(schedule, dayIndex, startTime, endTime) {
        var conflicts = [];
        $(schedule).find(".timeBlock").each(function() {
            var data = $.data(this, "timeData");
            if (data.dayIndex == dayIndex) {
                if (startTime >= data.start && startTime < data.end) {
                    conflicts.push(this);
                }
                else if (startTime <= data.start && endTime > data.start) {
                    conflicts.push(this);
                }
            }
        });
        return conflicts;
    }

    /**
     * Gets the time name used by the time with name specified (used by key add)
     * @param schedule
     * @param name
     * @param timeName
     * @param typeName
     * @param displayableTime
     */
    function getTimeName(schedule, name, timeName, typeName, displayableTime) {
        var elements = $(schedule).find("[name='" + name + "']");
        var fullName;
        if (elements.length) {
            fullName = $.data(elements[0], "timeName");
        }
        if (fullName == null) {
            if (typeName != null && typeName != "") {
                fullName = timeName + " <span class='timeType'><b>" + typeName + ":</b> " + displayableTime + "</span>";
            }
            else {
                fullName = timeName + " " + displayableTime;
            }
        }
        return fullName;
    }

    /**
     * Generates an amalgamation of the current timeName (if name matches an existing timeBlock) with
     * the timeName, typeName, and displayableTime by grouping common type times together in one string
     * (used during addTime)
     * @param schedule
     * @param name
     * @param timeName
     * @param typeName
     * @param displayableTime
     */
    function genTimeName(schedule, name, timeName, typeName, displayableTime) {
        var elements = $(schedule).find("[name='" + name + "']");
        var fullName;
        if (elements.length) {
            var elementFullName = $.data(elements[0], "timeName");
            if (elementFullName != null) {
                if (typeName != null && typeName != "") {
                    var typeIndex = elementFullName.indexOf(typeName);
                    if (typeIndex > -1) {
                        var oldTypeString = elementFullName.slice(typeIndex);
                        var spanEndIndex = oldTypeString.indexOf("</span>");
                        var newTypeString = oldTypeString.substring(0, spanEndIndex)
                            + "; " + displayableTime;
                        newTypeString = newTypeString + oldTypeString.substring(spanEndIndex);
                        fullName = elementFullName.replace(oldTypeString, newTypeString);
                    }
                    else {
                        fullName = elementFullName + " <span class='timeType'><b>" + typeName + ":</b> " + displayableTime + "</span>";
                    }
                }
                else {
                    fullName = elementFullName + "; " + displayableTime;
                }
            }

        }
        if (fullName == null) {
            if (typeName != null && typeName != "") {
                fullName = timeName + " <span class='timeType'><b>" + typeName + ":</b> " + displayableTime + "</span>";
            }
            else {
                fullName = timeName + " " + displayableTime;
            }
        }
        return fullName;
    }

    function getGeneratedColor(schedule) {
        var vars = $.data(schedule, "vars");
        return "rgb(" + vars.genRed + ", " + vars.genGreen + ", " + vars.genBlue + ")";
    }

    /**
     * Finds the existing color if a timeBlock with the name specified already exists
     * @param schedule
     * @param name
     */
    function findTimeBlockColor(schedule, name) {
        var elements = $(schedule).find("[name='" + name + "']");
        var color = "";
        if (elements.length) {
            color = $(elements[0]).css("background-color");
        }
        return color;
    }

    /**
     * Iterates to the next color, up to 16 different colors
     * @param schedule
     */
    function updateColor(schedule) {
        var vars = $.data(schedule, "vars");
        if (vars.genRed + 60 > 255 || vars.genGreen + 60 > 255 || vars.genBlue + 60 > 255) {
            vars.genRed = 60;
            vars.genGreen = 60;
            vars.genBlue = 60;
            vars.colorIndex = vars.colorIndex + 1;
        }

        if (vars.colorIndex == 0 || vars.colorIndex > 3) {
            vars.genRed = vars.genRed + 60;
            vars.genGreen = vars.genGreen + 60;
            vars.genBlue = vars.genBlue + 60;
        }
        else if (vars.colorIndex == 1) {
            vars.genRed = vars.genRed + 60;
            vars.genGreen = 0;
            vars.genBlue = 0;
        }
        else if (vars.colorIndex == 2) {
            vars.genGreen = vars.genGreen + 60;
            vars.genRed = 0;
            vars.genBlue = 0;
        }
        else if (colorIndex == 3) {
            vars.genBlue = vars.genBlue + 60;
            vars.genRed = 0;
            vars.genGreen = 0;
        }
    }

    /**
     * Determines the row index to place the timeBlock based on the hour
     * @param schedule
     * @param hour
     */
    function determineStartIndex(schedule, hour) {
        var options = $.data(schedule, "options");
        if (hour < options.startTime) {
            hour = options.startTime;
        }
        var hourIndex = (hour - options.startTime) + 2;
        return hourIndex;
    }

    /**
     * Determines the dayIndex of the day passed in based on how many days exist in the represented
     * week
     * @param schedule
     * @param day
     */
    function determineDayIndex(schedule, day) {
        var dayIndex = -1;
        var options = $.data(schedule, "options");
        switch (day) {
            case "MO":
            case "M":
            case "Mon":
            case "Monday":
            case "1":
                dayIndex = 1;
                break;
            case "TU":
            case "T":
            case "Tue":
            case "Tuesday":
            case "2":
                dayIndex = 2;
                break;
            case "WE":
            case "W":
            case "Wed":
            case "Wednesday":
            case "3":
                dayIndex = 3;
                break;
            case "TH":
            case "Th":
            case "Thu":
            case "Thursday":
            case "4":
                dayIndex = 4;
                break;
            case "FR":
            case "F":
            case "Fri":
            case "Friday":
            case "5":
                dayIndex = 5;
                break;
            case "SA":
            case "S":
            case "Sat":
            case "Saturday":
            case "6":
                dayIndex = 6;
                if (options.omitWeekend) {
                    dayIndex = -1;
                }
                break;
            case "SU":
            case "Sun":
            case "Sunday":
            case "7":
                if (options.sundayFirst) {
                    dayIndex = 0;
                }
                else {
                    dayIndex = 7;
                }
                if (options.omitWeekend) {
                    dayIndex = -1;
                }
                break;
        }
        if (options.sundayFirst && !options.omitWeekend) {
            dayIndex = dayIndex + 2;
        }
        else {
            if (dayIndex != -1) {
                dayIndex = dayIndex + 1;
            }
        }
        return dayIndex;
    }
})(jQuery);