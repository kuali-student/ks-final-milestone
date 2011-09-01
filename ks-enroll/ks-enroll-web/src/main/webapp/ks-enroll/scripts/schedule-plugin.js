(function($) {

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

            var localVars = {
                genRed : 40,
                genGreen : 40,
                genBlue : 40,
                colorIndex : 0,
                charCode : 64, //A - 1 is start
                nameIndex : 0
            };

            $.data(this, "options", options);
            $.data(this, "vars", localVars);
            var cellWidth = $(this).find(".schedule td").css("width").replace("px", "");
            var borderWidth = 1;
            var tableWidth;
            if(options.omitWeekend){
               tableWidth  = (cellWidth * 6) + (borderWidth * 6);
            }
            else{
               tableWidth  = (cellWidth * 8) + (borderWidth * 8);
            }
            $(this).find(".schedule").width(tableWidth);
            if(options.data != null){
                $(this).initSchedule(options.data);
            }
        });

    };

    $.fn.initSchedule = function(data){
        $(this).find(".timeBlock").remove();
        $(this).find(".keyRow").remove();
        var schedule = this[0];
        var options = $.data(schedule, "options");
        options.data = data;
        if(data != null){
           $.each(data, function(index, value){
               $(schedule).addTimeAndKey(genBlockName(schedule), value.days, value.startTime, value.endTime, value.name, value.cssClass);
           });
        }
    }

    $.fn.addSchedulePreviewTimeHandler = function(scheduleId, days, startTime, endTime) {
        var name = genBlockName($("#" + scheduleId)[0]);
        $(this).hover(
            function () {
                $("#" + scheduleId).addTime(name, days, startTime, endTime, null, "previewTimeBlock", false);
            },
            function () {
                $("#" + scheduleId).removeTime(name);
            }
        );
    };

    $.fn.addTimeAndKey = function(name, days, startTime, endTime, timeName, cssColorClass) {
        $(this).addTime(name, days, startTime, endTime, timeName, cssColorClass, true);
        $(this).addKey(name, timeName, cssColorClass, true);
    };

    $.fn.addTime = function(name, days, startTime, endTime, timeName, cssColorClass, useLetter) {
        var options = $.data(this[0], "options");
        var vars = $.data(this[0], "vars");
        var schedule = this[0];
        //TODO check for time conflict here maybe?
        var startIndex = determineStartIndex(schedule, startTime.substring(0, 2));
        var startMinFraction = (startTime.substring(2, 4)) / 60;
        var minDiff = ((endTime.substring(0, 2) * 60) + parseInt(endTime.substring(2, 4))) - ((startTime.substring(0, 2) * 60) + parseInt(startTime.substring(2, 4)));
        var totalHours = minDiff / 60;

        //if totalHours outside of schedule range, cut off
        if (parseInt(startTime.substring(0, 2)) + startMinFraction + totalHours > (options.endTime+1)) {
            totalHours = (options.endTime+1) - (parseInt(startTime.substring(0, 2)) + startMinFraction);
        }

        var content;
        if (options.useLetterKeys && useLetter) {
            vars.charCode = vars.charCode + 1;
            content = String.fromCharCode(vars.charCode);
        }
        else {
            content = "&nbsp;"
        }

        if (!cssColorClass) {
            updateColor(schedule);
            var generatedColor = getGeneratedColor(schedule);
        }

        $.each(days, function(index, value) {
            var dayIndex = determineDayIndex(schedule, value);
            //-1 means an invalid date for this schedule so don't add it
            if(dayIndex != -1){
                var div = $('<div name="' + name + '" class="timeBlock">' + content + '</div>');
                if (cssColorClass) {
                    div.addClass(cssColorClass);
                }
                else {
                    //generate a shade of gray if no css class assigned
                    div.css("background-color", generatedColor);
                }
                var tdLoc = $(schedule).find(".timeRow:nth-child(" + startIndex + ")").find("td:nth-child(" + dayIndex + ")");

                var divHeight = tdLoc.outerHeight() * totalHours;
                div.height(divHeight);

                var divWidth = Math.floor(tdLoc.width() / 2);
                div.width(divWidth);

                var left = Math.floor(tdLoc.width() / 4);
                div.css("left", left + "px");

                var top = tdLoc.height() * startMinFraction;
                div.css("top", top + "px");

                if (timeName) {
                    div.attr('title', timeName);
                }

                $(tdLoc).find("div:not('.timeBlock')").append(div);
            }
        });
    };

    $.fn.removeTimeAndKey = function(name) {
        $(this).find("div[name='" + name + "']").remove();
        $(this).find("td[name='" + name + "']").remove();
    };

    $.fn.removeTime = function(name) {
        $(this).find("div[name='" + name + "']").remove();
    };

    $.fn.removeKey = function(name) {
        $(this).find("td[name='" + name + "']").remove();
    };

    $.fn.addKey = function(name, keyName, cssColorClass, useLetter) {
        var appendage;
        var options = $.data(this[0], "options");
        var vars = $.data(this[0], "vars");
        var content = "&nbsp;";
        if (options.useLetterKeys && useLetter) {
            content = String.fromCharCode(vars.charCode);
        }

        if (cssColorClass) {
            appendage = $("<tr class='keyRow'><td><div class='keyBlock " + cssColorClass + "'>" + content + "</div></td><td><div>" + keyName + "</div></td></tr>");
        }
        else {
            appendage = $("<tr class='keyRow'><td><div class='keyBlock' style='background-color: " + getGeneratedColor(this[0]) + "' >" + content + "</div></td><td><div>" + keyName + "</div></td></tr>");
        }

        var blocks = $(this).find(".timeBlock[name='" + name + "']");
        var height = blocks.height();
        var width = blocks.width();
        $(appendage).find("div").hover(
            function () {
                blocks.addClass("timeHighlight");
                if (!$.browser.msie) {
                    var borderWidth = blocks.css("borderBottomWidth").replace("px", "") * 2;
                    blocks.height(height - borderWidth);
                    blocks.width(width - borderWidth);
                }
            },
            function () {
                if (!$.browser.msie) {
                    blocks.height(height);
                    blocks.width(width);
                }
                blocks.removeClass("timeHighlight");
            }
        );

        $(this).find(".scheduleKey").append(appendage);
    }

    $.fn.clearTable =  function() {
        $(this).find(".timeBlock").remove();
    };

    $.fn.clearTableAndKeys = function() {
        $(this).find(".timeBlock").remove();
        $(this).find(".keyRow").remove();
    };

    //private functions
    function genBlockName(schedule) {
        var vars = $.data(schedule, "vars");
        vars.nameIndex++;
        return "timeBlock" + vars.nameIndex;
    }

    function getGeneratedColor(schedule) {
        var vars = $.data(schedule, "vars");
        return "rgb(" + vars.genRed + ", " + vars.genGreen + ", " + vars.genBlue + ")"
    }

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

    function determineStartIndex(schedule, hour) {
        var options = $.data(schedule, "options");
        if (hour < options.startTime) {
            hour = options.startTime;
        }
        var hourIndex = (hour - options.startTime) + 2;
        return hourIndex;
    }

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
                if(options.omitWeekend){
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
                if(options.omitWeekend){
                    dayIndex = -1;
                }
                break;
        }
        if (options.sundayFirst && !options.omitWeekend) {
            dayIndex = dayIndex + 2;
        }
        else {
            if(dayIndex != -1){
                dayIndex = dayIndex + 1;
            }
        }
        return dayIndex;
    }
})(jQuery);