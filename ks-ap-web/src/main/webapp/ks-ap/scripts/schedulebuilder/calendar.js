var KsapSbCalendar = {

	minTime : 5,
	maxTime : 24,
	weekends : true,

	// see http://arshaw.com/fullcalendar/ for documentation
	options : function() {
		return {
			year : this.termStartDate.getFullYear(),
			month : this.termStartDate.getMonth(),
			date : this.termStartDate.getDate(),
			minTime : this.minTime,
			maxTime : this.maxTime,
			weekends : this.weekends,
			contentHeight : 1068,
			allDaySlot : true,
			allDayText : 'to be arranged',
			defaultView : 'agendaWeek',
			timeFormat : {
				agenda : 'h(:mm)t{ - h(:mm)t}',
			},
			columnFormat : {
				week : 'dddd'
			},
			firstHour : 8,
			slotMinutes : 30,
			eventTextColor : 'black',
			header : false,
			lazyFetching : false,
			editable : false,
			selectable : false,
			selectHelper : false,

			// TODO: detailed dialog
			//			eventClick : function(event) {
			//				showGrowl("TODO: details " + event.id);
			//			},
	
			eventAfterRender : function(event, element) {
				element.attr("title", event.hoverText);
			}
		};
	},

	sources : [],

	initialize : function(selector, termStartDate) {
		this.selector = selector;
		this.termStartDate = termStartDate;
		this.widget = jQuery(selector).fullCalendar(this.options());
	},
	
	add : function(source) {
		this.widget.fullCalendar('addEventSource', source);
	},

	remove : function(source) {
		this.widget.fullCalendar('removeEventSource', source);
	},
	
	clear : function() {
		this.widget.fullCalendar('removeEvents');
	},
	
	gotoDate : function(year, month, date) {
		this.widget.fullCalendar('gotoDate', year, month-1, date);
	}
	
};
