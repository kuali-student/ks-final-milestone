/* used by ... */

jQuery(function(){
    // put selection checkbox into first header column if special class is set:
    jQuery('.uif-kskrad-collection-selection').find('label:first').prepend( _ksGetNewSelectionCheckbox() );
})

function ksButtonImageChanger(source, imageName) {
    var button = jQuery(source);
    if ( !button.prop('disabled') ) { // necessary because Opera 12 ignores disabled state on mouseover
        button.find('img').prop('src',imageName);
    }
}


function _ksGetNewSelectionCheckbox() {
    var checkbox = jQuery(document.createElement('input')).attr({type:'checkbox'});
    checkbox.click(function(){
        var checkbox = jQuery(this);
        var isChecked = checkbox.prop('checked');
        checkbox.closest('table')
            .find('tbody tr').find('td input[type="checkbox"]:first')
            .each(function(){ jQuery(this).prop('checked',isChecked); });
    });
    return checkbox;
}
