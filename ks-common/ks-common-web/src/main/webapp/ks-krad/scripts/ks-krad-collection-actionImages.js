function ksButtonImageChanger(source, imageName) {
    var button = jQuery(source);
    if ( !button.prop('disabled') ) { // necessary because Opera 12 ignores disabled state on mouseover
        button.find('img').prop('src',imageName);
    }
}
