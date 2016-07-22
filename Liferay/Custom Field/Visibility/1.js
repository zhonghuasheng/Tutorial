/**
 * This file is used to extends AUI function. Following items had added in AUI:
 * 1. checkBoxGroupChecker : Each checkbox click event in CheckBoxGroup will call this function.
 * 2. bindingRule : This function is used to binding rule for condition visibility.
 */

AUI().use('event-custom', 'node', function(A) {
    var HIDDEN_ACTION = 'hidden';
    var SHOW_ACTION = 'show';

    var _isJson = function(obj) {
        return (typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object array]" && obj.length>0);
    };

    var _isNullOrEmpty = function(obj) {
        return !(obj != null && obj.length != 0);
    }

    // Extends checkBoxGroupChecker function in AUI
    var checkBoxGroupChecker = function(checkboxNode) {
        var isChecked = checkboxNode.get('checked');
        var checkboxGroupNode = checkboxNode.get('parentNode').get('parentNode');
        if (checkboxNode.hasClass('ddm-checkbox-group-select-all')) {
            checkboxGroupNode.all('input[type=checkbox]').set('checked', isChecked);
        } else {
            checkboxGroupNode = checkboxGroupNode.get('parentNode');
            var hasUnselectedCheckbox = checkboxGroupNode.all('input[type=checkbox]:not(.ddm-checkbox-group-select-all)')
                .filter(':not(:checked)').size() > 0;
            checkboxGroupNode.one('.ddm-checkbox-group-select-all').set('checked', !hasUnselectedCheckbox);
        }
    };

    A.checkBoxGroupChecker = checkBoxGroupChecker;

    // Extends bindingRule function in AUI
    var bindingRule = function (rules, currentDOMName, fieldType) {
        if (_isJson(rules) && rules.length > 0) {
            for (var i = 0; i < rules.length; i++) {
                var rule = rules[i];
                var bindingTagNode = A.one('[name*=' + rule.bindingTagName + ']');
                var currentTagNode = AUI().one('[name*=' + currentDOMName + ']');

                if (bindingTagNode != null && currentTagNode != null) {
                    rule['fieldType'] = fieldType;
                    _initDefaultBehaviorByFieldType(currentTagNode, rule.fieldType);

                    if (bindingTagNode.subscriberRules == null) {
                        bindingTagNode.subscriberRules = [];
                    }

                    if (_validateRule(rule) && bindingTagNode != null) {
                        rule['currentDOMName'] = currentDOMName;
                        bindingTagNode.subscriberRules.push(rule);

                        switch (rule.fieldType) {
                            case 'select':
                                _onSelectNodeChange(bindingTagNode, rule);
                                break;
                            case 'checkbox':
                                break;
                            case 'ddm-checkbox-group':
                                break;
                            case 'ddm-date':
                                break;
                            case 'ddm-decimal':
                                break;
                            case 'ddm-documentlibrary':
                                break;
                            case 'ddm-integer':
                                break;
                            case 'ddm-link-to-page':
                                break;
                            case 'ddm-number':
                                break;
                            case 'ddm-text-html':
                                break;
                            case 'radio':
                                break;
                            case 'text':
                                break;
                            case 'textarea':
                            default:
                                break;
                        }
                    }

                }


            }
        }
    };

    var _initDefaultBehaviorByFieldType = function (currentTagNode, fieldType) {
        switch (fieldType) {
            case 'select':
                currentTagNode.get('parentNode').setStyle('display', 'none');
                break;
            case 'checkbox':
                break;
            case 'ddm-checkbox-group':
                break;
            case 'ddm-date':
                break;
            case 'ddm-decimal':
                break;
            case 'ddm-documentlibrary':
                break;
            case 'ddm-integer':
                break;
            case 'ddm-link-to-page':
                break;
            case 'ddm-number':
                break;
            case 'ddm-text-html':
                break;
            case 'radio':
                break;
            case 'text':
                break;
            case 'textarea':
            default:
                break;
        }
    };

    var _validateRule = function (rule) {
        return !_isNullOrEmpty(rule.bindingTagName) && !_isNullOrEmpty(rule.bindingTagEvent)
            && !_isNullOrEmpty(rule.expectedValue) && !_isNullOrEmpty(rule.actionName)
    };

    var _onSelectNodeChange = function (bindingTagNode, rule) {
        Liferay.on(rule.bindingTagName, function(event) {
            var jsonArray = event.details[0];
            _distributeActionByNodeType(jsonArray);
        });

        bindingTagNode.on('change', function() {
            rule['currentValue'] = this.val();
            Liferay.fire(rule.bindingTagName, rule);
        });
    };

    var _distributeActionByNodeType = function (jsonArray) {
        var node = AUI().one('[name*=' + jsonArray.currentDOMName + ']');
        var fieldType = jsonArray.fieldType;

        if (fieldType == null) {
            return;
        } else if (fieldType === 'select') {
            var action = jsonArray.expectedValue === jsonArray.currentValue ? SHOW_ACTION : HIDDEN_ACTION;
            _action(node.get('parentNode'), action)

            if (action === SHOW_ACTION) {
                node.one('option:first-child').set('selected', true);
            } else {
                node.val('');
            }

            if (!_isNullOrEmpty(node.subscriberRules)) {
                node.subscriberRules.forEach(function (item) {
                    item.currentValue = node.val();
                    Liferay.fire(item.bindingTagName, item);
                });
            }
        }
    };

    var _action = function (node, actionName) {
        switch (actionName) {
            case HIDDEN_ACTION:
                node.setStyle('display', 'none');
                break;
            case SHOW_ACTION:
                node.setStyle('display', 'block');
                break;
            default:
                break;
        }
    }

    A.bindingRule = bindingRule;
});
