var selector = "table.uif-kskrad-collection-actionLinksOnHover tbody tr:not(.uif-collectionAddItem)";
jQuery(document)
    .on("mouseenter", selector, function(){jQuery(this).find("div[data-label='Actions']").show();})
    .on("mouseleave", selector, function(){jQuery(this).find("div[data-label='Actions']").hide();});
