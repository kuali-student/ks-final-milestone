// Allow cross-domain scripting from wiki.kuali.org to work, hopefully:
if (window.self != window.top) { // must be in a frame
    var dd=String(document.domain).match(/^(?:[a-z0-9-]+\.)*kuali\.org$/);
    if(dd)document.domain="kuali.org";
    if(dd)document.write("<span style='color:#aaa;font-size:75%;font-weight:bold;'>document.domain = 'kuali.org'</span>");
}

/*
 * Rice's jquery.form.js - redefine "ajaxSubmit()" with option.iframe = false
 */
;(function($) {

    /**
     * ajaxSubmit() provides a mechanism for immediately submitting
     * an HTML form using AJAX.
     */
    $.fn.ajaxSubmit = function(options) {
        // fast fail if nothing selected (http://dev.jquery.com/ticket/2752)
        if (!this.length) {
            log('ajaxSubmit: skipping submit process - no element selected');
            return this;
        }

        if (typeof options == 'function') {
            options = { success: options };
        }

        var action = this.attr('action');
        var url = (typeof action === 'string') ? $.trim(action) : '';
        if (url) {
            // clean url (don't include hash vaue)
            url = (url.match(/^([^#]+)/)||[])[1];
        }
        url = url || window.location.href || '';

        options = $.extend(true, {
            iframe: false,//test - Bob Hurt
            url:  url,
            type: this[0].getAttribute('method') || 'GET', // IE7 massage (see issue 57)
            iframeSrc: /^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank'
        }, options);

        // hook for manipulating the form data before it is extracted;
        // convenient for use with rich editors like tinyMCE or FCKEditor
        var veto = {};
        this.trigger('form-pre-serialize', [this, options, veto]);
        if (veto.veto) {
            log('ajaxSubmit: submit vetoed via form-pre-serialize trigger');
            return this;
        }

        // provide opportunity to alter form data before it is serialized
        if (options.beforeSerialize && options.beforeSerialize(this, options) === false) {
            log('ajaxSubmit: submit aborted via beforeSerialize callback');
            return this;
        }

        var n,v,a = this.formToArray(options.semantic);
        if (options.data) {
            options.extraData = options.data;
            for (n in options.data) {
                if(options.data[n] instanceof Array) {
                    for (var k in options.data[n]) {
                        a.push( { name: n, value: options.data[n][k] } );
                    }
                }
                else {
                    v = options.data[n];
                    v = $.isFunction(v) ? v() : v; // if value is fn, invoke it
                    a.push( { name: n, value: v } );
                }
            }
        }

        // give pre-submit callback an opportunity to abort the submit
        if (options.beforeSubmit && options.beforeSubmit(a, this, options) === false) {
            log('ajaxSubmit: submit aborted via beforeSubmit callback');
            return this;
        }

        // fire vetoable 'validate' event
        this.trigger('form-submit-validate', [a, this, options, veto]);
        if (veto.veto) {
            log('ajaxSubmit: submit vetoed via form-submit-validate trigger');
            return this;
        }

        var q = $.param(a);

        if (options.type.toUpperCase() == 'GET') {
            options.url += (options.url.indexOf('?') >= 0 ? '&' : '?') + q;
            options.data = null;  // data is null for 'get'
        }
        else {
            options.data = q; // data is the query string for 'post'
        }

        var $form = this, callbacks = [];
        if (options.resetForm) {
            callbacks.push(function() { $form.resetForm(); });
        }
        if (options.clearForm) {
            callbacks.push(function() { $form.clearForm(); });
        }

        // perform a load on the target only if dataType is not provided
        if (!options.dataType && options.target) {
            var oldSuccess = options.success || function(){};
            callbacks.push(function(data) {
                var fn = options.replaceTarget ? 'replaceWith' : 'html';
                $(options.target)[fn](data).each(oldSuccess, arguments);
            });
        }
        else if (options.success) {
            callbacks.push(options.success);
        }

        options.success = function(data, status, xhr) { // jQuery 1.4+ passes xhr as 3rd arg
            var context = options.context || options;   // jQuery 1.4+ supports scope context
            for (var i=0, max=callbacks.length; i < max; i++) {
                callbacks[i].apply(context, [data, status, xhr || $form, $form]);
            }
        };

        // are there files to upload?
        var fileInputs = $('input:file', this).length > 0;
        var mp = 'multipart/form-data';
        var multipart = ($form.attr('enctype') == mp || $form.attr('encoding') == mp);

        // options.iframe allows user to force iframe mode
        // 06-NOV-09: now defaulting to iframe mode if file input is detected
        if (options.iframe !== false && (fileInputs || options.iframe || multipart)) {
            // hack to fix Safari hang (thanks to Tim Molendijk for this)
            // see:  http://groups.google.com/group/jquery-dev/browse_thread/thread/36395b7ab510dd5d
            if (options.closeKeepAlive) {
                $.get(options.closeKeepAlive, fileUpload);
            }
            else {
                fileUpload();
            }
        }
        else {
            $.ajax(options);
        }

        // fire 'notify' event
        this.trigger('form-submit-notify', [this, options]);
        return this;


        // private function for handling file uploads (hat tip to YAHOO!)
        function fileUpload() {
            var form = $form[0];

            if ($(':input[name=submit],:input[id=submit]', form).length) {
                // if there is an input with a name or id of 'submit' then we won't be
                // able to invoke the submit fn on the form (at least not x-browser)
                alert('Error: Form elements must not have name or id of "submit".');
                return;
            }

            var s = $.extend(true, {}, $.ajaxSettings, options);
            s.context = s.context || s;
            var id = 'jqFormIO' + (new Date().getTime()), fn = '_'+id;
            var $io = $('<iframe id="' + id + '" name="' + id + '" src="'+ s.iframeSrc +'" />');
            var io = $io[0];

            $io.css({ position: 'absolute', top: '-1000px', left: '-1000px' });

            var xhr = { // mock object
                aborted: 0,
                responseText: null,
                responseXML: null,
                status: 0,
                statusText: 'n/a',
                getAllResponseHeaders: function() {},
                getResponseHeader: function() {},
                setRequestHeader: function() {},
                abort: function() {
                    this.aborted = 1;
                    $io.attr('src', s.iframeSrc); // abort op in progress
                }
            };

            var g = s.global;
            // trigger ajax global events so that activity/block indicators work like normal
            if (g && ! $.active++) {
                $.event.trigger("ajaxStart");
            }
            if (g) {
                $.event.trigger("ajaxSend", [xhr, s]);
            }

            if (s.beforeSend && s.beforeSend.call(s.context, xhr, s) === false) {
                if (s.global) {
                    $.active--;
                }
                return;
            }
            if (xhr.aborted) {
                return;
            }

            var timedOut = 0;

            // add submitting element to data if we know it
            var sub = form.clk;
            if (sub) {
                var n = sub.name;
                if (n && !sub.disabled) {
                    s.extraData = s.extraData || {};
                    s.extraData[n] = sub.value;
                    if (sub.type == "image") {
                        s.extraData[n+'.x'] = form.clk_x;
                        s.extraData[n+'.y'] = form.clk_y;
                    }
                }
            }

            // take a breath so that pending repaints get some cpu time before the upload starts
            function doSubmit() {
                // make sure form attrs are set
                var t = $form.attr('target'), a = $form.attr('action');

                // update form attrs in IE friendly way
                form.setAttribute('target',id);
                if (form.getAttribute('method') != 'POST') {
                    form.setAttribute('method', 'POST');
                }
                if (form.getAttribute('action') != s.url) {
                    form.setAttribute('action', s.url);
                }

                // ie borks in some cases when setting encoding
                if (! s.skipEncodingOverride) {
                    $form.attr({
                        encoding: 'multipart/form-data',
                        enctype:  'multipart/form-data'
                    });
                }

                // support timout
                if (s.timeout) {
                    setTimeout(function() { timedOut = true; cb(); }, s.timeout);
                }

                // add "extra" data to form if provided in options
                var extraInputs = [];
                try {
                    if (s.extraData) {
                        for (var n in s.extraData) {
                            extraInputs.push(
                                $('<input type="hidden" name="'+n+'" value="'+s.extraData[n]+'" />')
                                    .appendTo(form)[0]);
                        }
                    }

                    // add iframe to doc and submit the form
                    $io.appendTo('body');
                    //test:
                    //alert('1. io.contentWindow.document.domain = '+io.contentWindow.document.domain);
                    //$io.contents().find('head').append("<script type='text/javascript'>document.domain='kuali.org';<" + "/script>");
                    //alert('2. io.contentWindow.document.domain = '+io.contentWindow.document.domain);
                    //var dd = io.contentWindow ? io.contentWindow.document : io.contentDocument ? io.contentDocument : io.document;
                    //alert('dd = '+dd);
                    //test end
                    io.attachEvent ? io.attachEvent('onload', cb) : io.addEventListener('load', cb, false);
                    form.submit();
                }
                finally {
                    // reset attrs and remove "extra" input elements
                    form.setAttribute('action',a);
                    if(t) {
                        form.setAttribute('target', t);
                    } else {
                        $form.removeAttr('target');
                    }
                    $(extraInputs).remove();
                }
            }

            if (s.forceSync) {
                doSubmit();
            }
            else {
                setTimeout(doSubmit, 10); // this lets dom updates render
            }

            var data, doc, domCheckCount = 50;

            function cb() {
                //test
                //alert('function cb()');
                //$(io).contents().find('head').append("<script type='text/javascript'>document.domain='kuali.org';<" + "/script>");
                //test end
                doc = io.contentWindow ? io.contentWindow.document : io.contentDocument ? io.contentDocument : io.document;
                if (!doc || doc.location.href == s.iframeSrc) {
                    // response not received yet
                    return;
                }
                io.detachEvent ? io.detachEvent('onload', cb) : io.removeEventListener('load', cb, false);

                var ok = true;
                try {
                    if (timedOut) {
                        throw 'timeout';
                    }

                    var isXml = s.dataType == 'xml' || doc.XMLDocument || $.isXMLDoc(doc);
                    log('isXml='+isXml);
                    if (!isXml && window.opera && (doc.body == null || doc.body.innerHTML == '')) {
                        if (--domCheckCount) {
                            // in some browsers (Opera) the iframe DOM is not always traversable when
                            // the onload callback fires, so we loop a bit to accommodate
                            log('requeing onLoad callback, DOM not available');
                            setTimeout(cb, 250);
                            return;
                        }
                        // let this fall through because server response could be an empty document
                        //log('Could not access iframe DOM after mutiple tries.');
                        //throw 'DOMException: not available';
                    }

                    //log('response detected');
                    xhr.responseText = doc.body ? doc.body.innerHTML : doc.documentElement ? doc.documentElement.innerHTML : null;
                    xhr.responseXML = doc.XMLDocument ? doc.XMLDocument : doc;
                    xhr.getResponseHeader = function(header){
                        var headers = {'content-type': s.dataType};
                        return headers[header];
                    };

                    var scr = /(json|script)/.test(s.dataType);
                    if (scr || s.textarea) {
                        // see if user embedded response in textarea
                        var ta = doc.getElementsByTagName('textarea')[0];
                        if (ta) {
                            xhr.responseText = ta.value;
                        }
                        else if (scr) {
                            // account for browsers injecting pre around json response
                            var pre = doc.getElementsByTagName('pre')[0];
                            var b = doc.getElementsByTagName('body')[0];
                            if (pre) {
                                xhr.responseText = pre.textContent;
                            }
                            else if (b) {
                                xhr.responseText = b.innerHTML;
                            }
                        }
                    }
                    else if (s.dataType == 'xml' && !xhr.responseXML && xhr.responseText != null) {
                        xhr.responseXML = toXml(xhr.responseText);
                    }

                    data = httpData(xhr, s.dataType, s);
                }
                catch(e){
                    log('error caught:',e);
                    ok = false;
                    xhr.error = e;
                    s.error && s.error.call(s.context, xhr, 'error', e);
                    g && $.event.trigger("ajaxError", [xhr, s, e]);
                }

                if (xhr.aborted) {
                    log('upload aborted');
                    ok = false;
                }

                // ordering of these callbacks/triggers is odd, but that's how $.ajax does it
                if (ok) {
                    s.success && s.success.call(s.context, data, 'success', xhr);
                    g && $.event.trigger("ajaxSuccess", [xhr, s]);
                }

                g && $.event.trigger("ajaxComplete", [xhr, s]);

                if (g && ! --$.active) {
                    $.event.trigger("ajaxStop");
                }

                s.complete && s.complete.call(s.context, xhr, ok ? 'success' : 'error');

                // clean up
                setTimeout(function() {
                    $io.removeData('form-plugin-onload');
                    $io.remove();
                    xhr.responseXML = null;
                }, 100);
            }

            var toXml = $.parseXML || function(s, doc) { // use parseXML if available (jQuery 1.5+)
                if (window.ActiveXObject) {
                    doc = new ActiveXObject('Microsoft.XMLDOM');
                    doc.async = 'false';
                    doc.loadXML(s);
                }
                else {
                    doc = (new DOMParser()).parseFromString(s, 'text/xml');
                }
                return (doc && doc.documentElement && doc.documentElement.nodeName != 'parsererror') ? doc : null;
            };
            var parseJSON = $.parseJSON || function(s) {
                return window['eval']('(' + s + ')');
            };

            var httpData = function( xhr, type, s ) { // mostly lifted from jq1.4.4
                var ct = xhr.getResponseHeader('content-type') || '',
                    xml = type === 'xml' || !type && ct.indexOf('xml') >= 0,
                    data = xml ? xhr.responseXML : xhr.responseText;

                if (xml && data.documentElement.nodeName === 'parsererror') {
                    $.error && $.error('parsererror');
                }
                if (s && s.dataFilter) {
                    data = s.dataFilter(data, type);
                }
                if (typeof data === 'string') {
                    if (type === 'json' || !type && ct.indexOf('json') >= 0) {
                        data = parseJSON(data);
                    } else if (type === "script" || !type && ct.indexOf("javascript") >= 0) {
                        $.globalEval(data);
                    }
                }
                return data;
            };
        }
    };

})(jQuery);


