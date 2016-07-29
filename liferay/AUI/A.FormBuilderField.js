/**
 * The Form Builder Component
 *
 * @module aui-form-builder
 * @submodule aui-form-builder-field
 */

var L = A.Lang,
    isArray = L.isArray,
    isObject = L.isObject,

    AArray = A.Array,
    AEscape = A.Escape,

    getCN = A.getClassName,

    CSS_CLEARFIX = getCN('clearfix'),
    CSS_COMPONENT = getCN('component'),
    CSS_FB_DROP_ZONE = getCN('form', 'builder', 'drop', 'zone'),
    CSS_FB_FIELD = getCN('form', 'builder', 'field'),
    CSS_FB_FIELD_NODE = getCN('form', 'builder', 'field', 'node'),
    CSS_FB_FIELD_SELECTED = getCN('form', 'builder', 'field', 'selected'),
    CSS_FB_UNIQUE = getCN('form', 'builder', 'unique'),
    CSS_ICON = getCN('glyphicon'),
    CSS_ICON_ASTERISK = getCN('glyphicon', 'asterisk'),
    CSS_ICON_PLUS = getCN('glyphicon', 'plus'),
    CSS_ICON_QUESTION_SIGN = getCN('glyphicon', 'question', 'sign'),
    CSS_ICON_TRASH = getCN('glyphicon', 'trash'),
    CSS_ICON_WRENCH = getCN('glyphicon', 'wrench'),
    CSS_WIDGET = getCN('widget'),

    TPL_BOUNDING_BOX = '<div class="' + [CSS_WIDGET, CSS_COMPONENT, CSS_FB_FIELD].join(' ') + '"></div>',
    TPL_DROP_ZONE = '<div class="' + CSS_FB_DROP_ZONE + '"></div>',
    TPL_FLAG_REQUIRED = '<span class="' + [CSS_ICON, CSS_ICON_ASTERISK].join(' ') + '"></span>',
    TPL_FLAG_TIP = '<span class="' + [CSS_ICON, CSS_ICON_QUESTION_SIGN].join(' ') + '"></span>',
    TPL_LABEL = '<label for="{id}">{label}</label>',

    INVALID_CLONE_ATTRS = ['id', 'name'];

/**
 * A base class for `A.FormBuilderField`.
 *
 * @class A.FormBuilderField
 * @param {Object} config Object literal specifying widget configuration
 *     properties.
 * @constructor
 */
var FormBuilderField = A.Component.create({

    /**
     * Static property provides a string to identify the class.
     *
     * @property NAME
     * @type String
     * @static
     */
    NAME: 'form-builder-field',

    /**
     * Static property used to define the default attribute
     * configuration for the `A.FormBuilderField`.
     *
     * @property ATTRS
     * @type Object
     * @static
     */
    ATTRS: {

        /**
         * If `true` children are accepted.
         *
         * @attribute acceptChildren
         * @default true
         * @type Boolean
         */
        acceptChildren: {
            value: true
        },

        /**
         * The `A.FormBuilder` instance.
         *
         * @attribute builder
         * @default null
         */
        builder: {
            value: null
        },

        /**
         * Collection of toolbar controls.
         *
         * @attribute controlsToolbar
         * @type Object
         */
        controlsToolbar: {
            validator: isObject,
            valueFn: function() {
                var instance = this;

                return {
                    children: instance._getToolbarItems(instance.get('required'), instance.get('unique')),
                    visible: A.UA.touchEnabled && A.UA.mobile
                };
            }
        },

        /**
         * Indicates which is the type of data for the input field.
         *
         * @attribute dataType
         * @default 'string'
         * @type String
         */
        dataType: {
            value: 'string'
        },

        /**
         * Checks if the input field is disabled or not.
         *
         * @attribute disabled
         * @default false
         * @type Boolean
         */
        disabled: {
            value: false
        },

        /**
         * Checks if the input field is selected or not.
         *
         * @attribute selected
         * @default false
         * @type Boolean
         */
        selected: {
            value: false
        },

        /**
         * List of hidden attributes.
         *
         * @attribute hiddenAttributes
         * @default []
         * @type Array
         */
        hiddenAttributes: {
            validator: isArray,
            value: []
        },

        /**
         * The id of the input field.
         *
         * @attribute id
         */
        id: {
            setter: '_setId'
        },

        /**
         * The label of the input field.
         *
         * @attribute label
         * @default ''
         * @type String
         */
        label: {
            value: ''
        },

        /**
         * Collection for content localization.
         *
         * @attribute localizationMap
         * @default {}
         * @type Object
         */
        localizationMap: {
            value: {}
        },

        /**
         * The name of the input field.
         *
         * @attribute name
         */
        name: {
            valueFn: function() {
                var instance = this,
                    type = instance.get('type');

                return A.FormBuilderField.buildFieldName(type);
            }
        },

        /**
         * Container for the field parent.
         *
         * @attribute parent
         * @default null
         */
        parent: {
            value: null
        },

        /**
         * Specifies a predefined value for the input field.
         *
         * @attribute predefinedValue
         * @default ''
         * @type String
         */
        predefinedValue: {
            value: ''
        },

        /**
         * Checks if an input field is read-only.
         * In other words, it cannot be modified.
         *
         * @attribute readOnly
         * @default false
         * @type Boolean
         */
        readOnly: {
            setter: A.DataType.Boolean.parse,
            value: false
        },

        /**
         * List of read-only input fields.
         *
         * @attribute readOnlyAttributes
         * @default []
         * @type Array
         */
        readOnlyAttributes: {
            validator: isArray,
            value: []
        },

        /**
         * Checks if an input field is required.
         * In other words, it needs content to be valid.
         *
         * @attribute required
         * @default false
         * @type Boolean
         */
        required: {
            setter: A.DataType.Boolean.parse,
            value: false
        },

        /**
         * If `true` the label is showed.
         *
         * @attribute showLabel
         * @default true
         * @type Boolean
         */
        showLabel: {
            setter: A.DataType.Boolean.parse,
            value: true
        },

        /**
         * Collection of strings used to label elements of the UI.
         *
         * @attribute strings
         * @type Object
         */
        strings: {
            value: {
                button: 'Button',
                buttonType: 'Button Type',
                deleteFieldsMessage: 'Are you sure you want to delete the selected field(s)?',
                duplicateMessage: 'Duplicate',
                editMessage: 'Edit',
                label: 'Label',
                large: 'Large',
                medium: 'Medium',
                multiple: 'Multiple',
                name: 'Name',
                no: 'No',
                options: 'Options',
                predefinedValue: 'Predefined Value',
                readOnly: 'Read Only',
                required: 'Required',
                reset: 'Reset',
                showLabel: 'Show Label',
                small: 'Small',
                submit: 'Submit',
                tip: 'Tip',
                type: 'Type',
                width: 'Width',
                yes: 'Yes'
            }
        },

        /**
         * Specify the tab order.
         *
         * @attribute tabIndex
         * @default 0
         * @type Number
         */
        tabIndex: {
            value: 0
        },

        /**
         * Reusable block of markup used to generate the field.
         *
         * @attribute template
         * @default ''
         * @type String
         */
        template: {
            value: ''
        },

        /**
         * Hint to help the user to fill the input field.
         *
         * @attribute tip
         * @default ''
         * @type String
         */
        tip: {
            value: ''
        },

        /**
         * Defines the type of field.
         *
         * @attribute type
         * @default ''
         * @type String
         */
        type: {
            value: ''
        },

        /**
         * Checks if the input field is unique or not.
         *
         * @attribute unique
         * @default false
         * @type Boolean
         */
        unique: {
            setter: A.DataType.Boolean.parse,
            value: false
        },

        /**
         * Stack order of the field. An element with greater stack order is
         * always in front of an element with a lower stack order.
         *
         * @attribute zIndex
         * @default 100
         * @type Number
         */
        zIndex: {
            value: 100
        },

        /**
         * Node used to generate the drop zone.
         *
         * @attribute dropZoneNode
         */
        dropZoneNode: {
            valueFn: function() {
                return A.Node.create(TPL_DROP_ZONE);
            }
        },

        /**
         * Node used to generate a label.
         *
         * @attribute labelNode
         */
        labelNode: {
            valueFn: function() {
                var instance = this;

                return A.Node.create(
                    L.sub(
                        TPL_LABEL, {
                            id: AEscape.html(instance.get('id')),
                            label: AEscape.html(instance.get('label'))
                        }
                    )
                );
            }
        },

        /**
         * Node used to generate the required flag.
         *
         * @attribute requiredFlagNode
         */
        requiredFlagNode: {
            valueFn: function() {
                return A.Node.create(TPL_FLAG_REQUIRED);
            }
        },

        /**
         * Node used to generate a template.
         *
         * @attribute templateNode
         */
        templateNode: {
            valueFn: 'getNode'
        },

        /**
         * Node used to generate a tip.
         *
         * @attribute tipFlagNode
         */
        tipFlagNode: {
            valueFn: function() {
                return A.Node.create(TPL_FLAG_TIP);
            }
        }

    },

    /**
     * Static property used to define the UI attributes.
     *
     * @property UI_ATTRS
     * @type Array
     * @static
     */
    UI_ATTRS: [
        'acceptChildren', 'disabled', 'fields', 'label', 'name',
        'predefinedValue', 'required', 'selected', 'showLabel', 'tip', 'unique'
    ],

    /**
     * Static property used to define the augmented classes.
     *
     * @property AUGMENTS
     * @type Array
     * @static
     */
    AUGMENTS: [A.PropertyBuilderFieldSupport],

    /**
     * Creates the field id.
     *
     * @method buildFieldId
     * @param id
     * @private
     * @return {String}
     */
    buildFieldId: function(id) {
        return 'fields' + '_' + 'field' + '_' + id;
    },

    /**
     * Creates the field name.
     *
     * @method buildFieldName
     * @param type
     * @private
     * @return {String}
     */
    buildFieldName: function(type) {
        return type + (++A.Env._uidx);
    },

    /**
     * Object hash, defining how attribute values have to be parsed from markup.
     *
     * @property HTML_PARSER
     * @type Object
     * @static
     */
    HTML_PARSER: {
        dropZoneNode: '.' + CSS_FB_DROP_ZONE,
        labelNode: 'label',
        requiredFlagNode: '.' + CSS_ICON + ' .' + CSS_ICON_ASTERISK,
        tipFlagNode: '.' + CSS_ICON + ' .' + CSS_ICON_QUESTION_SIGN
    },

    prototype: {
        BOUNDING_TEMPLATE: TPL_BOUNDING_BOX,

        /**
         * Construction logic executed during `A.FormBuilderField` instantiation.
         * Lifecycle.
         *
         * @method initializer
         * @protected
         */
        initializer: function() {
            var instance = this;

            instance.controlsToolbar = new A.Toolbar(
                instance.get('controlsToolbar')
            );
        },

        /**
         * Render the `A.FormBuilderField` component instance. Lifecycle.
         *
         * @method renderUI
         * @protected
         */
        renderUI: function() {
            var instance = this,
                boundingBox = instance.get('boundingBox'),
                contentBox = instance.get('contentBox'),
                labelNode = instance.get('labelNode'),
                requiredFlagNode = instance.get('requiredFlagNode'),
                templateNode = instance.get('templateNode'),
                tipFlagNode = instance.get('tipFlagNode');

            contentBox.addClass(CSS_CLEARFIX);

            contentBox.append(labelNode);
            contentBox.append(requiredFlagNode);
            contentBox.append(tipFlagNode);
            contentBox.append(templateNode);

            instance.controlsToolbar.render(boundingBox);
        },

        /**
         * Destructor lifecycle implementation for the `A.FormBuilderField`
         * class.
         *
         * @method destructor
         * @protected
         */
        destructor: function() {
            var instance = this,
                builder = instance.get('builder'),
                fieldId = builder._getFieldId(instance);

            instance.get('fields').each(function(field) {
                field.destroy();
            });

            if (builder.editingField === instance) {
                delete builder.editingField;

                builder.closeEditProperties();
            }

            if (instance.controlsToolbar) {
                instance.controlsToolbar.destroy();
            }

            // destroy manually because NestedList doesn`t
            // use delegate
            instance.get('boundingBox').dd.destroy();

            instance.tooltip.destroy();

            instance.get('parent').removeField(instance);

            builder.uniqueFieldsMap.remove(fieldId);
        },

        /**
         * Creates the field using the `createField` method from
         * `A.FormBuilder`.
         *
         * @method createField
         * @param val
         * @return {Object}
         */
        createField: function(val) {
            var instance = this,
                builder = instance.get('builder');

            val = builder.createField(val);

            val.set('parent', instance);

            return val;
        },

        /**
         * Gets the field markup.
         *
         * To developer: Implement this
         *
         * @method getHTML
         * @return {String}
         */
        getHTML: function() {
            return '';
        },

        /**
         * Creates a `Node` from the HTML string.
         *
         * @method getNode
         * @return {Node}
         */
        getNode: function() {
            var instance = this;

            return A.Node.create(instance.getHTML());
        },

        /**
         * Gets all necessary attributes for cloning this field.
         *
         * @method getAttributesForCloning
         * @return {Object}
         */
        getAttributesForCloning: function() {
            // List of all non-property attributes that need to be cloned.
            var attributes = {
                hiddenAttributes: this.get('hiddenAttributes'),
                readOnlyAttributes: this.get('readOnlyAttributes'),
                localizationMap: this.get('localizationMap')
            };

            // All field properties should be cloned as well.
            AArray.each(this.getProperties(), function(property) {
                var name = property.attributeName;

                if (AArray.indexOf(INVALID_CLONE_ATTRS, name) === -1) {
                    attributes[name] = property.value;
                }
            });

            return attributes;
        },

        /**
         * Gets properties from the property model.
         *
         * @method getProperties
         * @return {Array}
         */
        getProperties: function(excludeHidden) {
            var instance = this,
                propertyModel = instance.getPropertyModel(),
                hiddenAttributes = instance.get('hiddenAttributes'),
                readOnlyAttributes = instance.get('readOnlyAttributes'),
                properties = [];

            AArray.each(propertyModel, function(property) {
                var attribute = property.attributeName;

                // TODO - Change checking to use hashes O(1) instead of indexOf
                // arrays O(N)
                if (excludeHidden && AArray.indexOf(hiddenAttributes, attribute) > -1) {
                    return;
                }

                var value = instance.get(attribute),
                    type = L.type(value);

                if (type === 'boolean') {
                    value = String(value);
                }

                property.value = value;

                // TODO - Change checking to use hashes O(1) instead of indexOf
                // arrays O(N)
                if (AArray.indexOf(readOnlyAttributes, attribute) > -1) {
                    property.readOnly = true;
                }

                properties.push(property);
            });

            return properties;
        },

        /**
         * Returns a list of property models. Each property model is made of a
         * name, attribute, editor, and formatter.
         *
         * @method getPropertyModel
         * @return {Array}
         */
        getPropertyModel: function() {
            var instance = this,
                strings = instance.getStrings();

            return [{
                attributeName: 'type',
                editor: false,
                name: strings.type
            }, {
                attributeName: 'label',
                editor: new A.TextCellEditor(),
                name: strings.label
            }, {
                attributeName: 'showLabel',
                editor: new A.RadioCellEditor({
                    options: {
                        'true': strings.yes,
                        'false': strings.no
                    }
                }),
                formatter: A.bind(instance._booleanFormatter, instance),
                name: strings.showLabel
            }, {
                attributeName: 'readOnly',
                editor: new A.RadioCellEditor({
                    options: {
                        'true': strings.yes,
                        'false': strings.no
                    }
                }),
                formatter: A.bind(instance._booleanFormatter, instance),
                name: strings.readOnly
            }, {
                attributeName: 'required',
                editor: new A.RadioCellEditor({
                    options: {
                        'true': strings.yes,
                        'false': strings.no
                    }
                }),
                formatter: A.bind(instance._booleanFormatter, instance),
                name: strings.required
            }, {
                attributeName: 'name',
                editor: new A.TextCellEditor({
                    validator: {
                        rules: {
                            value: {
                                required: true
                            }
                        }
                    }
                }),
                name: strings.name
            }, {
                attributeName: 'predefinedValue',
                editor: new A.TextCellEditor(),
                name: strings.predefinedValue
            }, {
                attributeName: 'tip',
                editor: new A.TextAreaCellEditor(),
                name: strings.tip
            }];
        },

        /**
         * Transforms a `Boolean` value into "yes" or "no" string.
         *
         * @method _booleanFormatter
         * @param o
         * @protected
         */
        _booleanFormatter: function(o) {
            var instance = this,
                strings = instance.getStrings();

            return A.DataType.Boolean.parse(o.data.value) ? strings.yes : strings.no;
        },

        /**
         * Returns the node for the current field.
         *
         * @method _getFieldNode
         * @protected
         */
        _getFieldNode: function() {
            var templateNode = this.get('templateNode'),
                fieldNode = templateNode.one('.' + CSS_FB_FIELD_NODE);

            if (!fieldNode) {
                fieldNode = templateNode;
            }

            return fieldNode;
        },

        /**
         * Gets a list of toolbar items.
         *
         * @method _getToolbarItems
         * @return {Array}
         */
        _getToolbarItems: function() {
            var instance = this,
                builder = instance.get('builder'),
                items = [
                    {
                        icon: [CSS_ICON, CSS_ICON_WRENCH].join(' '),
                        on: {
                            click: A.bind(instance._handleEditEvent, instance)
                        }
                    }
                ];

            if (!instance.get('unique')) {
                items.push({
                    icon: [CSS_ICON, CSS_ICON_PLUS].join(' '),
                    on: {
                        click: A.bind(instance._handleDuplicateEvent, instance)
                    }
                });
            }

            if ((builder && builder.get('allowRemoveRequiredFields')) || !instance.get('required')) {
                items.push({
                    icon: [CSS_ICON, CSS_ICON_TRASH].join(' '),
                    on: {
                        click: A.bind(instance._handleDeleteEvent, instance)
                    }
                });
            }

            return [items];
        },

        /**
         * Checks if the field isn't unique. If not, duplicates the instance.
         *
         * @method _handleDuplicateEvent
         * @param event
         * @protected
         */
        _handleDuplicateEvent: function(event) {
            var instance = this;

            if (!instance.get('unique')) {
                instance.get('builder').duplicateField(instance);
            }

            event.stopPropagation();
        },

        /**
         * Handles the edit event.
         *
         * @method _handleEditEvent
         * @param event
         * @protected
         */
        _handleEditEvent: function(event) {
            var instance = this;

            instance.get('builder').editField(instance);

            event.stopPropagation();
        },

        /**
         * Popups a dialog to confirm deletion. If "yes", destroys the instance.
         *
         * @method _handleDeleteEvent
         * @param event
         * @protected
         */
        _handleDeleteEvent: function(event) {
            var instance = this,
                strings = instance.getStrings();

            if (window.confirm(strings.deleteFieldsMessage)) {
                instance.destroy();
            }

            event.stopPropagation();
        },

        /**
         * Set the `id` attribute on the UI.
         *
         * @method _setId
         * @param val
         * @protected
         */
        _setId: function(val) {
            return A.FormBuilderField.buildFieldId(val);
        },

        /**
         * Set the `acceptChildren` attribute on the UI.
         *
         * @method _uiSetAcceptChildren
         * @param val
         * @protected
         */
        _uiSetAcceptChildren: function(val) {
            var instance = this,
                boundingBox = instance.get('boundingBox'),
                dropZone = instance.get('dropZoneNode'),
                markupDropZone = boundingBox.one('.' + CSS_FB_DROP_ZONE);

            if (val && !markupDropZone) {
                boundingBox.append(dropZone);
            }
            else if (!val && markupDropZone) {
                markupDropZone.remove();
            }
            else if (val && markupDropZone) {
                instance.set('dropZoneNode', markupDropZone);
            }
        },

        /**
         * Set the `selected` attribute on the UI.
         *
         * @method _uiSetSelected
         * @param val
         * @protected
         */
        _uiSetSelected: function(val) {
            var instance = this;

            instance.get('boundingBox').toggleClass(CSS_FB_FIELD_SELECTED, val);
        },

        /**
         * Set the `disabled` attribute on the UI.
         *
         * @method _uiSetDisabled
         * @param val
         * @protected
         */
        _uiSetDisabled: function(val) {
            var fieldNode = this._getFieldNode();

            if (val) {
                fieldNode.setAttribute('disabled', val);
            }
            else {
                fieldNode.removeAttribute('disabled');
            }
        },

        /**
         * Set the fields on the UI using the `plotFields` method from
         * `A.FormBuilder`.
         *
         * @method _uiSetFields
         * @param val
         * @protected
         */
        _uiSetFields: function(val) {
            var instance = this,
                builder = instance.get('builder');

            builder.plotFields(val, instance.get('dropZoneNode'));
        },

        /**
         * Set the label content on the UI.
         *
         * @method _uiSetLabel
         * @param val
         * @protected
         */
        _uiSetLabel: function(val) {
            var instance = this,
                labelNode = instance.get('labelNode');

            labelNode.setContent(AEscape.html(val));
        },

        /**
         * Set the `name` attribute on the UI.
         *
         * @method _uiSetName
         * @param val
         * @protected
         */
        _uiSetName: function(val) {
            var fieldNode = this._getFieldNode();

            fieldNode.set('name', val);
        },

        /**
         * Set the `predefinedValue` attribute on the UI.
         *
         * @method _uiSetPredefinedValue
         * @param val
         * @protected
         */
        _uiSetPredefinedValue: function(val) {
            var fieldNode = this._getFieldNode();

            fieldNode.val(val);
        },

        /**
         * Set the `required` attribute on the UI.
         *
         * @method _uiSetRequired
         * @param val
         * @protected
         */
        _uiSetRequired: function(val) {
            var instance = this,
                controlsToolbar = instance.controlsToolbar,
                requiredNode = instance.get('requiredFlagNode');

            requiredNode.toggle(val);

            controlsToolbar.set('children', instance._getToolbarItems());
        },

        /**
         * Set the `showLabel` attribute on the UI.
         *
         * @method _uiSetShowLabel
         * @param val
         * @protected
         */
        _uiSetShowLabel: function(val) {
            var instance = this,
                labelNode = instance.get('labelNode');

            labelNode.toggle(val);
        },

        /**
         * Set the `tip` attribute on the UI.
         *
         * @method _uiSetTip
         * @param val
         * @protected
         */
        _uiSetTip: function(val) {
            var tipFlagNode = this.get('tipFlagNode');

            tipFlagNode.toggle(val.length !== 0);

            if (this.tooltip) {
                this.tooltip.set('bodyContent', val);
                return;
            }

            this.tooltip = new A.Tooltip({
                bodyContent: val,
                trigger: tipFlagNode,
                position: 'right',
                visible: false
            }).render();
        },

        /**
         * Set the `unique` attribute on the UI.
         *
         * @method _uiSetUnique
         * @param val
         * @protected
         */
        _uiSetUnique: function(val) {
            var instance = this,
                boundingBox = instance.get('boundingBox'),
                controlsToolbar = instance.controlsToolbar;

            boundingBox.toggleClass(CSS_FB_UNIQUE, val);

            controlsToolbar.set('children', instance._getToolbarItems());
        }

    }

});

A.FormBuilderField = FormBuilderField;
A.namespace('FormBuilderField.types').field = FormBuilderField;