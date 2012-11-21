// Allow cross-domain scripting from wiki.kuali.org to work, hopefully:
if (window.self != window.top) { // must be in a frame
    var dd=String(document.domain).match(/^(?:[a-z0-9-]+\.)*kuali\.org$/);
    if(dd){document.domain="kuali.org";document.write("<span style='color:white;'>document.domain = 'kuali.org'</span>");}
}

