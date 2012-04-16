/**
* SCRIPT METHODS USED BY MULTIPLE .JS FILES
*/


/**
* Enter key causes next action button to fire, so this routine turns the Enter
* keypress into a Tab keypress, essentially
*/
function tabToNextInput(e) {
    if ( e.keyCode === 13 ) {
        e.preventDefault();
        var inputs = jq("input:visible,select:visible");
        var next = inputs.get(inputs.index(this) + 1);
        if (next) {
            next.focus();
        }
    }
}

function confirmDeletion(msg){
    var response = confirm(msg);

    if( response == true ){
      submitForm();
    }
}

function stepBrowserBack(){
    window.history.back(-2);
}

