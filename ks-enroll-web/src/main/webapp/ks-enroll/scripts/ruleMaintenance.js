/*
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var ADD = '.kr-add-button';
var CUT='.kr-cut-button';
var PASTE='.kr-paste-button';
var DELETE='.kr-delete-button';
var REFRESH='.kr-refresh-button';
var TREE = '.tree-bar-button';
var MOVE='.kr-move-button';
var UP='.kr-up-button';
var DOWN='.kr-down-button';
var LEFT='.kr-left-button';
var RIGHT='.kr-right-button';
var COPY='.kr-copy-button';

var ENABLED = true;
var pasting = false;

function disableButton(id) {
    if (ENABLED && jq(id) != null) {
        jq(id).attr('disabled', true);
//        not using grayed out images yet..
//        jq(id + ' > img').attr('src', 'yourdisabledimg.jpg');
//        did the css stuff in a new css-class
//        jq(id).css('cursor', 'default');
//        jq(id).css('color', 'gray');
        jq(id).removeClass('kr-button-primary');
        jq(id).removeClass('kr-button-secondary1');
        jq(id).addClass('kr-button-primary-disabled');
    }
}

function enableButton(id) {
    if (ENABLED && jq(id) != null) {
        jq(id).removeAttr('disabled');
        jq(id).removeClass('kr-button-primary-disabled');
        jq(id).addClass('kr-button-primary');
    }
}

function enableAddButton() {
    enableButton(ADD);
}

function enablePasteButton() {
    pasting = true;
    enableButton(PASTE);
}

function enableDeleteButton() {
    enableButton(DELETE);
}

function enableRefreshButton() {
    enableButton(REFRESH);
}

function enableTreeButtons() {
    enableButton(TREE);
    if (!pasting) {
        disablePasteButton();
    }
}

function enableCopyButton() {
    enableButton(COPY);
}

function disablePasteButton() {
    pasting = false;
    disableButton(PASTE);
}

function disableTreeButtons() {
    disableButton(TREE);
}

function disableMoveButtons() {
    disableButton(MOVE);
}

function disableUpButton() {
    disableButton(UP);
}

function disableDownButton() {
    disableButton(DOWN);
}

function disableLeftButton() {
    disableButton(LEFT);
}

function disableRightButton() {
    disableButton(RIGHT);
}

function disableCutCopyButtons() {
    disableButton(CUT);
}

function cutPasteButtonInit() {
    // CUT
    if (jq('.kr-cut-button') != undefined && jq('.kr-cut-button') != null) {
        jq('.kr-cut-button').click(function() {
            enablePasteButton();
        });
    }
    // PASTE
    //if (jq('.kr-paste-button') != undefined && jq('.kr-paste-button') != null) {
    //    jq('.kr-paste-button').click(function() {
    //       disablePasteButton();
    //    });
    //}
}


function propButtonsInit() {
    disableTreeButtons();

    if (propositionAddInProgress()) {
        disablePasteButton();
        enableDeleteButton();
    } else {
        cutPasteButtonInit();
        enableAddButton();
        enableRefreshButton();
        selectedPropCheck();
    }
}

var onProp = false;
function enabledCheck(command) {
    if (onProp) return true;

    if (command == 'edit') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'add') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'addparent') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'left') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'right') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'up') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'down') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'cut') {
        onProp = true;
        propButtonsInit();
        enablePasteButton(); // clicks have not been inited yet, enable paste button
    } else if (command == 'copy') {
        onProp = true;
        propButtonsInit();
        enablePasteButton(); // clicks have not been inited yet, enable paste button
    } else if (command == 'paste') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'refresh') {
        onProp = true;
        propButtonsInit();
    } else if (command == 'delete') {
        onProp = true;
        propButtonsInit();
    }
    return onProp;
}

function selectedCheck() {
    var disableButtons = jq('input.disableButtons').val();
    if(disableButtons == 'true'){
        disableTreeButtons();
    }
}

function selectedPropCheck() {
    if (getSelectedPropositionInput() != null) {
        if (getSelectedPropositionInput().val() != "" && getSelectedPropositionInput().val() != undefined) {
            enableTreeButtons();
        }
    }
}

function loadControlsInit() {
    if (ENABLED) {
        disablePasteButton();
        propButtonsInit();
    }
}

function getSelectedPropositionInput() {
    return jq('input[id="proposition_selected_control"]');
}

function getCutPropositionInput() {
    return jq('input[id="proposition_cut_control"]');
}

function getCopyPropositionInput() {
    return jq('input[id="proposition_copy_control"]');
}

function getPropositionIdFromParentLi(parentLiNode) {
    return jq(parentLiNode).find('input.hiddenId').first().attr('value');
}

function ajaxCallPropositionTree(controllerMethod, collectionGroupId) {
    var selectedItemInput = getSelectedPropositionInput();
    var selectedItemId = selectedItemInput.val();
    var actionRevealCallBack = function (htmlContent) {};
    retrieveComponent(collectionGroupId, controllerMethod, actionRevealCallBack, {selectedItemInputName: selectedItemId});
}

function ajaxCallOnTabSelect(event, ui, editwithgroup, editwithlogic) {
    //Do client side validation before continueing to next tab.
    if (validateForm()) {

        if (ui.index == 0) {

            //Check if the logic expression has changed.
            var logicArea = jq('#KRMS-LogicArea-InputField_control');
            if (logicArea.hasClass('dirty')) {

                //Add an error message for the user.
                var data = jQuery("#KRMS-LogicArea-InputField").data(kradVariables.VALIDATION_MESSAGES);
                data.errors = [];
                var errorMessage = getMessage('error.krms.logic.preview');
                data.errors.push(errorMessage);
                jQuery("#KRMS-LogicArea-InputField").data(kradVariables.VALIDATION_MESSAGES, data);

                //Display error message
                handleMessagesAtField('KRMS-LogicArea-InputField');

                //Do not continue.
                event.preventDefault();
            } else {

                //Remove previous error message if any exist.
                var messagesDiv = jQuery("[data-messages_for='KRMS-LogicArea-InputField']");
                messagesDiv.hide();
                handleTabStyle('KRMS-LogicArea-InputField_control', false, false, false);

                //Refresh the edit tree.
                retrieveComponent(editwithgroup);
            }
        } else {

            //Check if any proposition is in editing mode.
            var updateButton = jq('button[id="update-button"]');
            if (updateButton.length) {

                //Force an updateProposition if any proposition is in edit mode.
                ajaxCallPropositionTree('updateProposition', editwithlogic);
            } else {

                //Refresh the preview tree.
                retrieveComponent(editwithlogic);
            }
        }
    } else {

        //Ignore the tab selection, user has to fix errors.
        event.preventDefault();
    }
}

function show(e){
    var nextElement = jq(e.currentTarget).next().next();
    nextElement.show();
}

function hide(e){
    var nextElement = jq(e.currentTarget).next('div.uif-subCollection');
    nextElement.hide();
}

function ajaxCutPropositionTree() {
    var selectedItemTracker = getSelectedPropositionInput();
    var selectedItemId = selectedItemTracker.val();
    var cutItemTracker = getCutPropositionInput();
    cutItemTracker.val(selectedItemId);
    var copyItemTracker = getCopyPropositionInput();
    copyItemTracker.val('');

    enablePasteButton();

    resetCutSelected(selectedItemId);
}

function ajaxCopyPropositionTree() {
    var selectedItemTracker = getSelectedPropositionInput();
    var selectedItemId = selectedItemTracker.val();
    var copyItemTracker = getCopyPropositionInput();
    copyItemTracker.val(selectedItemId);
    var cutItemTracker = getCutPropositionInput();
    cutItemTracker.val('');

    enablePasteButton();

    resetCutSelected(selectedItemId);
}

function resetCutSelected(selectedItemId) {
    jq('a.ruleTreeNode').each(function () {
        var propositionId = getPropositionIdFromParentLi(this.parentNode);
        if (selectedItemId == propositionId) {
            // simulate click, which will mark it
            jq(this.parentNode).addClass('ruleCutSelected');
            jq(this.parentNode).removeClass('ruleBlockSelected');
        } else {
            jq(this.parentNode).removeClass('ruleCutSelected');
        }
    });
}

function ajaxPastePropositionTree(controllerMethod, collectionGroupId) {
    var selectedItemInput = getSelectedPropositionInput();
    var selectedItemId = selectedItemInput.val();

    var collectionGroup = jq('#' + collectionGroupId);
    //Set parent to be refreshed
    var componentId = jq(collectionGroup).offsetParent().attr('id');
    var tabId = componentId.substring(0, componentId.indexOf('_'));

    var actionRevealCallBack = function (htmlContent) {

        resetControlKeys();

        jq('a.ruleTreeNode').each(function () {
            jq(this.parentNode).removeClass('ruleCutSelected');
        });

        disablePasteButton();
    };
    retrieveComponent(tabId, controllerMethod, actionRevealCallBack, {selectedItemInputName: selectedItemId});

}

function updateProposition(controllerMethod, collectionGroupId) {
    var selectedItemInput = getSelectedPropositionInput();
    if(controllerMethod != 'cancelEditProposition') {
        if (validateForm()) {
            var selectedItemInput = getSelectedPropositionInput();
            var selectedItemId = selectedItemInput.val();
            var actionRevealCallBack = function (htmlContent) {};

            retrieveComponent(collectionGroupId, controllerMethod, actionRevealCallBack, {selectedItemInputName: selectedItemId});
        }
    } else {
        var selectedItemInput = getSelectedPropositionInput();
        var selectedItemId = selectedItemInput.val();
        var actionRevealCallBack = function (htmlContent) {};

        enableAddButton();

        retrieveComponent(collectionGroupId, controllerMethod, actionRevealCallBack, {selectedItemInputName: selectedItemId});
    }
}

function resetControlKeys() {
    var selectedItemTracker = getSelectedPropositionInput();
    selectedItemTracker.val('');
    // also remove copy key
    var copyItemTracker = getCopyPropositionInput();
    copyItemTracker.val('');
    // and cut key.
    var cutItemTracker = getCutPropositionInput();
    cutItemTracker.val('');
}

function markNodeAsSelected(parentLiNode) {
    if (!jq(parentLiNode).hasClass('ruleCutSelected')) {
        jq(parentLiNode).addClass('ruleBlockSelected');
    }

    if (!propositionAddInProgress()) {
        enableTreeButtons();
    }

    if (jq(parentLiNode).hasClass('treeRoot')) {
        disableMoveButtons(); // disableButtons.js
        disableCutCopyButtons();
        if ((jq(parentLiNode).hasClass('simple'))) {
            enableCopyButton();
        }
    }

    if (jq(parentLiNode).hasClass('firstInGroup')) {
        disableUpButton();
    }

    if (jq(parentLiNode).hasClass('lastInGroup')) {
        disableDownButton();
    }

    if(jq(parentLiNode).hasClass('disableMoveRight')) {
        disableRightButton();
    }

    if(jq(parentLiNode).hasClass('disableMoveLeft')) {
        disableLeftButton();
    }
}


function handlePropositionNodeClick(parentLiNode) {
    var propositionId = getPropositionIdFromParentLi(parentLiNode);
    var selectedItemTracker = getSelectedPropositionInput();

    // Don't allow other propositions to be selected when the proposition description is blank
    if (propositionAddInProgress()) {
        return;
    }

    // make li show containment of children
    jq('li').each(function () {
        jq(this).removeClass('ruleBlockSelected');
        // hide edit image links
        jq(this.parentNode).find(".actionReveal").hide();
    });

    if (selectedItemTracker.val() == propositionId) {
        // if this item is already selected, deselect it
        resetControlKeys();

        if (!propositionAddInProgress()) {
            disableTreeButtons(); // disableButtons.js
            enableAddButton(); // disableButtons.js
            enableRefreshButton(); // disableButtons.js
        }
    } else {
        selectedItemTracker.val(propositionId);
        markNodeAsSelected(parentLiNode);

        if (jq(parentLiNode).hasClass('treeRoot')) {
            disableMoveButtons(); // disableButtons.js
            disableCutCopyButtons();
            if ((jq(parentLiNode).hasClass('simple'))) {
                enableCopyButton();
            }

        }
    }
}

function handleEditNodeClick(parentLiNode) {
    var propositionId = getPropositionIdFromParentLi(parentLiNode);
    var selectedItemTracker = getSelectedPropositionInput();

    // remove selection from other li's
    jq('li').each(function () {
        jq(this).removeClass('ruleBlockSelected');
    });

    selectedItemTracker.val(propositionId);
}

/**
 * Check if a proposition is missing a description.
 *
 * When a different proposition is selected and the added/edited proposition has a blank description then this method
 * returns true
 *
 * @return true if description is missing, false otherwise
 */
function propositionWithoutDescription(parentLiNode) {
    var description = propositionAddInProgress();
    // check if edit is in progress
    if (description) {
        // check if edited proposition is the selected proposition
        if (parentLiNode.find && parentLiNode.find('.editDescription').attr('id') == description.attr('id')) {
            return false;
        } else {
            return true;
        }
    } else {
        return false;
    }
}

/**
 * Check if a proposition is currently being added.
 *
 * As long as the proposition does not have a description the proposition is considered as being
 * in an add status.
 *
 * @return description jQuery object of the proposition that is being added, null if none is currently being added
 */
function propositionAddInProgress() {
    var flag = jQuery(".simpleEditNode");
    return flag.length > 0 ? true : false;
}

function initRuleTree(componentId) {

    // binding to tree loaded event
    jq('#' + componentId).bind('loaded.jstree', function (event, data) {
        /* make the tree load with all nodes expanded */
        jq('#' + componentId).jstree('open_all');

        //Reset the tree buttons.
        loadControlsInit();

        //Display error message
        jq('#' + componentId).find("div[data-role='InputField']").andSelf().filter("div[data-role='InputField']").each(function () {
            var data = jQuery(this).data(kradVariables.VALIDATION_MESSAGES);
            handleMessagesAtField(jQuery(this).attr('id'));
        });

        // hide quick action icons (edit and add parent) on proposition tree nodes
        jq(this).find(".actionReveal").hide();

        // selecting the description on an edit node should set it to be selected
        jq('input.editDescription').click(function () {

            var parentLiNode = jq(this).closest('li');
            handlePropositionNodeClick(parentLiNode);
        });

        // rule node clicks should set the selected item
        jq('a.ruleTreeNode').click(function () {
            var parentLiNode = this.parentNode;
            handlePropositionNodeClick(parentLiNode);
        });

        // rule node clicks should set the selected item
        jq('li.simpleEditNode').click(function () {
            var parentLiNode = jq(this).closest('li');
            handleEditNodeClick(parentLiNode);
        });

        // set type to 'logic' on logic nodes -- this prevents them from being selected
        jq('a.compoundOpCodeNode').each(function () {
            jq('#' + componentId).jstree('set_type', 'logic', this.parentNode);
        });

        /* mark the selected node */
        jq('a.ruleTreeNode').each(function () {
            var propositionId = getPropositionIdFromParentLi(this.parentNode);
            var selectedItemTracker = getSelectedPropositionInput();
            var selectedItemId = selectedItemTracker.val();

            if ((typeof selectedItemId !== "undefined") && (selectedItemId == propositionId)) {
                markNodeAsSelected(this.parentNode);
            }

            var cutItemTracker = getCutPropositionInput();
            var cutItemId = cutItemTracker.val();
            if ((typeof cutItemId !== "undefined") && (cutItemId == propositionId)) {
                jq(this.parentNode).addClass('ruleCutSelected');
                cutItemTracker.val(cutItemId);
            } else {
                jq(this.parentNode).removeClass('ruleCutSelected');
            }

            var copyItemTracker = getCopyPropositionInput();
            var copyItemId = copyItemTracker.val();
            if ((typeof copyItemId !== "undefined") && (copyItemId == propositionId)) {
                jq(this.parentNode).addClass('ruleCutSelected');
                copyItemTracker.val(copyItemId);
            } else {
                jq(this.parentNode).removeClass('ruleCutSelected');
            }
        });

        /* update sister compound operators and update proposition summary */
        jq('li.compoundOpCodeNode').change(function () {
            //Set the selected id
            var parentLiNode = jq(this).closest('li');
            //Set parent to be refreshed
            var componentId = jq(parentLiNode).offsetParent().attr('id');
            var tabId = componentId.substring(0, componentId.indexOf('_'));

            var propositionId = getPropositionIdFromParentLi(parentLiNode);
            var selectedItemTracker = getSelectedPropositionInput();
            selectedItemTracker.val(propositionId);

            ajaxCallPropositionTree('updateCompoundOperator', tabId);
        })

    });


    /* create the tree */
    createTree(componentId, {
        'plugins': ['themes', 'html_data', 'ui', 'crrm', 'types' /*, 'dnd' */ ], // disabled drag and drop plugin
        'ui': { 'select_limit': 1 },
        'themes': { 'theme': 'editrule', 'dots': true, 'icons': false },
        'crrm': {
            /* This is where you can control what is draggable onto what within the tree: */
            'move': {
                /*
                 * m.o - the node being dragged
                 * m.r - the target node
                 */
                'check_move': function (m) {
                    var p = this._get_parent(m.o);
                    if (!p) return false;
                    p = p == -1 ? this.get_container() : p;

                    if (m.o.hasClass('logicNode')) return false;

                    if (p === m.np) return true;
                    if (p[0] && m.np[0] && p[0] === m.np[0]) return true;
                    return false;
                }
            }
        },
        'types': {
            'types': {
                /* nodes set to type 'logic' will not be selectable */
                'logic': { 'select_node': false }
            }
        },
        'dnd': { 'drag_target': false, 'drop_target': false }
    });

}

function initPreviewTree(componentId) {

    // binding to tree loaded event
    jq('#' + componentId).bind('loaded.jstree', function (event, data) {
        /* make the tree load with all nodes expanded */
        jq('#' + componentId).jstree('open_all');

        // set type to 'logic' on logic nodes -- this prevents them from being selected
        jq('a.compoundOpCodeNode').each(function () {
            jq('#' + componentId).jstree('set_type', 'logic', this.parentNode);
        });

    });


    /* create the tree */
    createTree(componentId, {
        'plugins': ['themes', 'html_data', 'ui', 'crrm', 'types' /*, 'dnd' */ ], // disabled drag and drop plugin
        'ui': { 'select_limit': 1 },
        'themes': { 'theme': 'viewrule', 'dots': true, 'icons': false },
        'crrm': {
            /* This is where you can control what is draggable onto what within the tree: */
            'move': {
                /*
                 * m.o - the node being dragged
                 * m.r - the target node
                 */
                'check_move': function (m) {
                    var p = this._get_parent(m.o);
                    if (!p) return false;
                    p = p == -1 ? this.get_container() : p;

                    if (m.o.hasClass('logicNode')) return false;

                    if (p === m.np) return true;
                    if (p[0] && m.np[0] && p[0] === m.np[0]) return true;
                    return false;
                }
            }
        },
        'types': {
            'types': {
                /* nodes set to type 'logic' will not be selectable */
                'logic': { 'select_node': false }
            }
        },
        'dnd': { 'drag_target': false, 'drop_target': false }
    });

}




