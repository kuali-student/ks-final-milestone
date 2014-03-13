/*
* Enable less dev mode in the browser
*
* */
//Change ks-ap/css file extensions and set rel attribute for less
var cssFiles = jQuery('link[href*="ks-ap/css"][href$=".css"]');
cssFiles.each(function(){
    var href = jQuery(this).attr("href");
    jQuery(this).attr("href", href.replace(/\.css$/i, ".less"));
    jQuery(this).attr("rel", "stylesheet/less");
});
//Global variable for less processing in the browser
var less = {
    env: "development",
    async: false,
    fileAsync: false,
    poll: 1000,
    functions: {},
    dumpLineNumbers: "all"
};
//Create and append to the body the link to less.js processor
var s = document.createElement("script");
s.type = "text/javascript";
s.src = "../ks-ap/scripts/vendor/less-1.7.0.min.js";
document.body.appendChild(s);