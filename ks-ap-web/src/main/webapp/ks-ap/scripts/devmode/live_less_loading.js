//Change ks-ap/css file extensions and set rel attribute for less
//var cssFiles = jQuery('link[href*="ks-ap/css"][href$=".css"]');
jQuery('link[href*="ks-ap/css"][href$=".css"]')
    .each(function(){
        var href = jQuery(this).attr("href");
        jQuery(this).attr("href", href.replace(/\.css$/i, ".less"));
        jQuery(this).attr("rel", "stylesheet/less");
    });