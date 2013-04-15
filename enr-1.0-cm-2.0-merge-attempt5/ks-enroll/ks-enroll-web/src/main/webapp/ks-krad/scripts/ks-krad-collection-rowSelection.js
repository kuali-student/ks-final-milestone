jQuery(function(){
    // put selection checkbox into first header column if special class is set:
    jQuery('.uif-kskrad-collection-rowSelection').find('label:first').prepend( _ksGetNewSelectionCheckbox() );
})

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
