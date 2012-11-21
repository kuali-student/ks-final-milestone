// Allow cross-domain scripting from wiki.kuali.org to work, hopefully:
var dd=String(document.domain).match(/^(?:[a-z0-9-]+\.)*kuali\.org$/);
if(dd)document.domain="kuali.org";

