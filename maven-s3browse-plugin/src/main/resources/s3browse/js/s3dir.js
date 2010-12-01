      String.prototype.startsWith = function(str)
      {return (this.match("^"+str)==str)}

      String.prototype.endsWith = function(str)
      {return (this.match(str+"$")==str)}

      var defaultFiles = new Array();
      defaultFiles.push("index.html");
      defaultFiles.push("index.htm");

      var start = new Date().getTime();
      var debug = true;
      var localMode = false;
      var columnDecorators = new Array();
      var xml = '';
      var qs = GetQueryString();
      //var myPrefix = qs["prefix"];
      var myPrefix = jQuery.url.attr("path");
      if (myPrefix.startsWith("/")) {
		  myPrefix = myPrefix.substring(1);
	  }
      if (!myPrefix.endsWith("/")) {
		  myPrefix += "/";
	  }
      log("myPrefix from query string=" + myPrefix);
      var path = jQuery.url.attr("path");
      log("path=" + path);

      function GetQueryString() {
        return function(a)  {
          if (a == "") return {};
          var b = {};
            for (var i = 0; i < a.length; ++i)  {
              var p=a[i].split('=');
              b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
            }
            return b;
          } (window.location.search.substr(1).split('&'))
      }

      function log(msg) {
        if (!debug) {
          return;
        }
        console.log(msg);
      }

      function getDate(s) {
        var year = s.substring(0,4);
          var month = s.substring(5,7);
          var day = s.substring(8,10);
          var hour = s.substring(11,13);
          var minute = s.substring(14,16);
          var second = s.substring(17,19);
          var utcDate = new Date();
          utcDate.setUTCFullYear(year);
          utcDate.setUTCMonth(month-1);
          utcDate.setUTCDate(day);
          utcDate.setUTCHours(hour);
          utcDate.setUTCMinutes(minute);
          utcDate.setUTCSeconds(second);
          return utcDate.toUTCString();
      }

      function ColumnDecorator(tableDataClass,spanClass,columnTitle) {
          this.tableDataClass = tableDataClass;
          this.spanClass = spanClass;
          this.columnTitle = columnTitle;
      }

      function getHtmlTable(data,decorators) {
        if (data == null || data.length == 0) {
          return '';
        }
        var html = '<table id="mainTable">\n';
        html += '  <thead>\n';
        html += '    <tr>\n';
        for (var i=0;i<decorators.length;i++) {
          html += '      <th class="' + decorators[i].tableDataClass + '"><span class="' + decorators[i].spanClass + '">' + decorators[i].columnTitle + '</span></th>\n';
        }
        html += '    </tr>\n';
        html += '  </thead>\n';
        html += '  <tbody>\n';
        for (var row=0;row<data.length;row++) {
          var thisRow = data[row];
          if ((row % 2) == 0) {
            html += '    <tr>\n';
          } else {
            html += '    <tr class="table-tr-odd">\n';
          }
          for (var col=0;col<thisRow.length;col++) {
			log("row=" + row + " column=" + col + " data=" + thisRow[col]);
            html += '      <td class="' + decorators[col].tableDataClass + '">' + thisRow[col] + '</td>\n';
          }
          html += '    </tr>\n';
        }
        html += '  </tbody>\n';
        html += '</table>\n';
        return html;
      }

      function getHtmlHref(dest,show) {
        return '<a href="' + dest + '">' + show + '</a>';
      }

      function getHtmlImg(img) {
        return '<img src="' + img + '">';
      }

      function DisplayRow(image,ahref,date,size) {
        this.image = image;
        this.ahref = ahref;
        this.date = date;
        this.size = size;
      }

      function S3Object(key,lastModified,etag,size,storageClass) {
        this.key = key;
        this.lastModified = lastModified;
        this.etag = etag;
        this.size = size;
        this.storageClass = storageClass;
      }

      function S3Dir(s3Prefix) {
        this.prefix = s3Prefix;
      }

      function getS3Dir(s3json) {
        var s3Prefix = s3json.Prefix
        return new S3Dir(s3Prefix);
      }

      function getS3Object(s3json) {
        var key = s3json.Key;
        var lastModified = s3json.LastModified;
        var etag = s3json.ETag;
        var size = s3json.Size;
        var storageClass = s3json.StorageClass;
        return new S3Object(key,lastModified,etag,size,storageClass);
      }

      function getDestDir(newPrefix) {
		  return "/" + newPrefix;
	  }

      function getDirDisplayRow(s3Dir) {
        var image = getHtmlImg('/s3browse/images/folder.png');
        var dest = getDestDir(s3Dir.prefix);
        var show = getShow(s3Dir.prefix);
        var ahref = getHtmlHref(dest,show);
        var date = '-';
        var size = '-';
        return new DisplayRow(image,ahref,date,size);
      }

      function getShow(key) {
		  log("myPrefix=" + myPrefix + " key=" + key);
		  if (myPrefix == null) {
			  return key;
		  } else {
			  if (key.indexOf(myPrefix) == -1) {
				  log("error: key " + key + " does not contain prefix:" + myPrefix);
			  }
			  var index = myPrefix.length;
			  var s = key.substring(index);
			  return s;
		  }
	  }

      // Convert an S3Object into a DisplayRow object
      function getFileDisplayRow(s3Object) {
		log("s3Object.key=" + s3Object.key + " myPrefix=" + myPrefix);
		if (s3Object.key == myPrefix) {
			return null;
		}
		if ((s3Object.key + "/") == myPrefix) {
			return null;
		}
        var image = getHtmlImg('/s3browse/images/page_white.png');
        var show = getShow(s3Object.key);
        var dest = "/" + s3Object.key;
        var ahref = getHtmlHref(dest,show);
        var date = getDate(s3Object.lastModified);
        var size = ((s3Object.size) / 1024).toFixed(1) + " KiB";
        return new DisplayRow(image,ahref,date,size);
      }

      // Convert the JSON to S3 "directory" objects
      function getS3Dirs(listBucketResult) {
          var s3Dirs = new Array();
          var prefixes = listBucketResult.CommonPrefixes;
          if (prefixes == null) {
              return s3Dirs;
          }
          if ($.isArray(prefixes)) {
            for (var i=0;i<prefixes.length;i++) {
              var s3Dir = getS3Dir(prefixes[i]);
              s3Dirs.push(s3Dir);
            }
          } else {
              var s3Dir = getS3Dir(prefixes);
              s3Dirs.push(s3Dir);
          }
          return s3Dirs;
      }

      function isDirectory(s3Object,s3Dirs) {
		  var key = s3Object.key;
		  for (var i=0;i<s3Dirs.length;i++) {
			  var s3Dir = s3Dirs[i];
			  var prefix = s3Dir.prefix;
			  if ((key + "/") == prefix) {
				  return true;
			  }
			  log("key=" + key + " s3Dir.prefix=" + s3Dir.prefix);
		  }
		  return false;
	  }

      // Convert the JSON to S3 objects
      function getS3Objects(listBucketResult,s3Dirs) {
          var s3Objects = new Array();
          var contents = listBucketResult.Contents;
          if (contents == null) {
              return s3Objects;
          }
          if ($.isArray(contents)) {
            for (var i=0;i<contents.length;i++) {
              var s3Object = getS3Object(contents[i]);
              if (s3Object != null && !isDirectory(s3Object,s3Dirs)) {
                s3Objects.push(s3Object);
		      }
            }
          } else {
            var s3Object = getS3Object(contents);
            if (s3Object != null && !isDirectory(s3Object,s3Dirs)) {
              s3Objects.push(s3Object);
            }
          }
          return s3Objects;
      }

      // Convert the S3 "directory" objects to display row decorator objects for the UI
      function getDirDisplayRows(s3Dirs) {
          var displayRows = new Array();
          for (var i=0;i<s3Dirs.length;i++) {
            var displayRow = getDirDisplayRow(s3Dirs[i]);
            displayRows.push(displayRow);
		  }
          return displayRows;
      }

      function getFileDisplayRows(s3Objects) {
          var displayRows = new Array();
          for (var i=0;i<s3Objects.length;i++) {
            var displayRow = getFileDisplayRow(s3Objects[i]);
            if (displayRow != null) {
              displayRows.push(displayRow);
		    }
		  }
          return displayRows;
      }

      // Convert an array of display row objects into an array of arrays
      function getArrayData(displayRows) {
        var aa = new Array();
        for (var i=0;i<displayRows.length;i++) {
          var displayRow = displayRows[i];
          var a = new Array();
          a[0] = displayRow.image;
          a[1] = displayRow.ahref;
          a[2] = displayRow.date;
          a[3] = displayRow.size;
          aa.push(a);
        }
        return aa;
      }

      function getUpOneDirectory() {
		  log("upOne myPrefix=" + myPrefix);
		  if (myPrefix == null) {
			  return null;
		  }
		  var length = myPrefix.length;
		  var s = myPrefix.substring(0,length-1);
		  log("s=" + s);
		  var pos = s.lastIndexOf("/");
		  log("pos=" + pos);
		  var dest = "/"
		  if (pos != -1) {
			  var newPrefix = s.substring(0,pos+1);
			  dest += newPrefix;
		  }
		  var show = "../";
		  log("dest=" + dest);
		  if (dest == "/") {
			  dest = "/s3browse.html";
		  }
		  var image = "";
		  var ahref = getHtmlHref(dest,show);
		  var lastModified = "";
		  var size = "";
		  var myArray = new Array();
		  myArray.push(ahref);
		  myArray.push(image);
		  myArray.push(lastModified);
		  myArray.push(size);
		  return myArray;
	  }

	  function getDefaultS3Object(s3Objects) {
		  for (var i=0;i<s3Objects.length;i++) {
			  var s3Object = s3Objects[i];
			  var key = s3Object.key;
			  if (isDefaultFile(key)) {
				  return s3Object
			  }
		  }
		  return null;
	  }

	  function isDefaultFile(key) {
		  for (var i=0;i<defaultFiles.length;i++) {
			  var defaultFile = defaultFiles[i];
			  if (key.endsWith(defaultFile)) {
				  return true;
			  }
		  }
		  return false;
	  }

      function getData(listBucketResult) {
		var upOneDirectory = getUpOneDirectory();
		log("upOneDirectory=" + upOneDirectory);
        var s3Dirs = getS3Dirs(listBucketResult);
        var s3Objects = getS3Objects(listBucketResult,s3Dirs);
        var defaultS3Object = getDefaultS3Object(s3Objects);
        if (defaultS3Object != null) {
			var href = defaultS3Object.key;
			window.location.href = "/" + href;
		}
        var dirDisplayRows = getDirDisplayRows(s3Dirs);
        var fileDisplayRows = getFileDisplayRows(s3Objects);
        var dirData = getArrayData(dirDisplayRows);
        var fileData = getArrayData(fileDisplayRows);

        var data = new Array();
        if (upOneDirectory != null) {
			log("adding upOne");
			data.push(upOneDirectory);
			log("data.length=" + data.length);
		}
        for (var i=0;i<dirData.length;i++) {
          data.push(dirData[i]);
        }
        for (var i=0;i<fileData.length;i++) {
          data.push(fileData[i]);
        }
        return data;
      }

      function getTitle(listBucketResult) {
          var bucket = listBucketResult.Name;
          var directory = listBucketResult.Prefix;
          return 'Index of s3://' + listBucketResult.Name + '/' + directory;
      }

      function getHtmlTitle(title) {
          return '<span id="title">' + title + '</span>';
      }

      function obtainXML() {
        if (localMode) {
          xml = '<ListBucketResult><Name>cf.ks.kuali.org</Name><Prefix/><Marker/><MaxKeys>1000</MaxKeys><Delimiter>/</Delimiter><IsTruncated>false</IsTruncated><Contents><Key>dir.htm</Key><LastModified>2010-11-25T17:18:28.000Z</LastModified><ETag>"58bf1e8d52d59b1537f952ce17b4deb5"</ETag><Size>2598</Size><StorageClass>STANDARD</StorageClass></Contents><Contents><Key>dir2.htm</Key><LastModified>2010-11-25T20:17:09.000Z</LastModified><ETag>"87cc16493bf245a85261c6d1e579ccf2"</ETag><Size>1319</Size><StorageClass>STANDARD</StorageClass></Contents><Contents><Key>index.html</Key><LastModified>2010-11-25T14:11:01.000Z</LastModified><ETag>"e11229600102b8841fffc676213045be"</ETag><Size>730</Size><StorageClass>STANDARD</StorageClass></Contents><Contents><Key>jquery.url.js</Key><LastModified>2010-11-25T19:31:32.000Z</LastModified><ETag>"a3e6c3c12d4c6ed13eeec0e224f3fd06"</ETag><Size>4707</Size><StorageClass>STANDARD</StorageClass></Contents><Contents><Key>one.htm</Key><LastModified>2010-11-25T14:59:10.000Z</LastModified><ETag>"7977c80d484bd6f3882a37fff5fcb3b4"</ETag><Size>24</Size><StorageClass>STANDARD</StorageClass></Contents><CommonPrefixes><Prefix>ks/</Prefix></CommonPrefixes></ListBucketResult>';
          processXML();
        } else {
          $.get("/", { delimiter:"/", prefix:myPrefix }, successCallback);
        }
      }

      function successCallback(data,textStatus,request) {
        xml = request.responseText;
        processXML();
      }

      function processXML() {
        var listBucketResult = $.xml2json(xml);
        var data = getData(listBucketResult);
        var title = getTitle(listBucketResult);
        var htmlTable = getHtmlTable(data,columnDecorators);
        var htmlTitle = getHtmlTitle(title);
        var htmlFooter = getFooterLeft(start);
        log(title + " " + htmlTitle + " " + htmlTable + " " + htmlFooter);
        document.title = title;
        $("div.title").html(htmlTitle);
        $("div.data").html(htmlTable);
        $("div.footer").html(htmlFooter);
      }

      function execute() {
        columnDecorators[0] = new ColumnDecorator("image-column","sort-header","");
        columnDecorators[1] = new ColumnDecorator("name-column","sort-header","Name");
        columnDecorators[2] = new ColumnDecorator("last-modified-column","sort-header","Last Modified");
        columnDecorators[3] = new ColumnDecorator("size-column","sort-header","Size");

        obtainXML();

      }

      function getFooterLeft(start) {
        var stop = new Date().getTime();
        var millis = stop - start;
        return '<span id="footer-text">S3 index generated in ' + millis + ' milliseconds</span>';
      }

      $(document).ready(function() {
		try {
          execute();
	    } catch(err) {
		  alert(err);
		}
      });
