//Change ks-ap/css file extensions and set rel attribute for less
jQuery('link[href*="themes/ksapboot/stylesheets"][href$=".css"]')
    .each(function(){
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

/*
 * When in dev mode, this enables live in-browser less-recompiling
 * To have live updates, type this in the js console:
 *   less.watch();
 * To turn off:
 *   less.unwatch();
 * */