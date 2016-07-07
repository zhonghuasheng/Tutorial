AUI.add(
    'liferay-portlet-dynamic-data-mapping',
    function(A) {
        var AArray = A.Array;
        var Lang = A.Lang;
        var FormBuilderField = A.FormBuilderField;

        var BODY = A.getBody();

        var instanceOf = A.instanceOf;
        var isObject = Lang.isObject;

        var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

        var LOCALIZABLE_FIELD_ATTRS = ['label', 'predefinedValue', 'tip'];

        var MAP_HIDDEN_FIELD_ATTRS = {
            checkbox: ['readOnly', 'required'],

            separator: ['readOnly', 'required', 'predefinedValue', 'indexType'],

            DEFAULT: ['readOnly']
        };

        var STR_BLANK = '';

        var MAP_ELEMENT_DATA = {
            attributeList: STR_BLANK,
            nodeName: STR_BLANK
        };

        var STR_CDATA_CLOSE = ']]>';

        var STR_CDATA_OPEN = '<![CDATA[';

        var STR_SPACE = ' ';

        var TPL_ELEMENT = '<{nodeName}{attributeList}></{nodeName}>';

        var XML_ATTRIBUTES_FIELD_ATTRS = {
            dataType: 1,
            indexType: 1,
            localizable: 1,
            multiple: 1,
            name: 1,
            options: 1,
            readOnly: 1,
            repeatable: 1,
            required: 1,
            showLabel: 1,
            type: 1,
            width: 1,
            selectAll: 1,
            placeholder: 1
        };

        DEFAULTS_FORM_VALIDATOR.STRINGS.structureFieldName = Liferay.Language.get('please-enter-only-alphanumeric-characters');

        DEFAULTS_FORM_VALIDATOR.RULES.structureFieldName = function(value) {
            return (/^[\w\-]+$/).test(value);
        };

        var LiferayAvailableField = A.Component.create(
            {
                ATTRS: {
                    localizationMap: {
                        validator: isObject,
                        value: {}
                    }
                },

                NAME: 'availableField',

                EXTENDS: A.FormBuilderAvailableField
            }
        );

        A.LiferayAvailableField = LiferayAvailableField;

        var LiferayFormBuilder = A.Component.create(
            {
                ATTRS: {
                    availableFields: {
                        validator: isObject,
                        valueFn: function() {
                            return LiferayFormBuilder.AVAILABLE_FIELDS.DEFAULT;
                        }
                    },

                    portletNamespace: {
                        value: STR_BLANK
                    },

                    portletResourceNamespace: {
                        value: STR_BLANK
                    },

                    translationManager: {
                        validator: isObject,
                        value: {}
                    },

                    validator: {
                        setter: function(val) {
                            var instance = this;

                            var config = A.merge(
                                {
                                    rules: {
                                        name: {
                                            required: true,
                                            structureFieldName: true
                                        }
                                    },
                                    fieldStrings: {
                                        name: {
                                            required: Liferay.Language.get('this-field-is-required')
                                        }
                                    }
                                },
                                val
                            );

                            return config;
                        },
                        value: {}
                    },

                    strings: {
                        value: {
                            addNode: Liferay.Language.get('add-field'),
                            button: Liferay.Language.get('button'),
                            buttonType: Liferay.Language.get('button-type'),
                            close: Liferay.Language.get('close'),
                            deleteFieldsMessage: Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-entries'),
                            duplicateMessage: Liferay.Language.get('duplicate'),
                            editMessage: Liferay.Language.get('edit'),
                            label: Liferay.Language.get('field-label'),
                            large: Liferay.Language.get('large'),
                            localizable: Liferay.Language.get('localizable'),
                            medium: Liferay.Language.get('medium'),
                            multiple: Liferay.Language.get('multiple'),
                            name: Liferay.Language.get('name'),
                            no: Liferay.Language.get('no'),
                            options: Liferay.Language.get('options'),
                            predefinedValue: Liferay.Language.get('predefined-value'),
                            propertyName: Liferay.Language.get('property-name'),
                            required: Liferay.Language.get('required'),
                            reset: Liferay.Language.get('reset'),
                            save: Liferay.Language.get('save'),
                            settings: Liferay.Language.get('settings'),
                            showLabel: Liferay.Language.get('show-label'),
                            small: Liferay.Language.get('small'),
                            submit: Liferay.Language.get('submit'),
                            tip: Liferay.Language.get('tip'),
                            type: Liferay.Language.get('type'),
                            value: Liferay.Language.get('value'),
                            width: Liferay.Language.get('width'),
                            yes: Liferay.Language.get('yes')
                        }
                    }
                },

                EXTENDS: A.FormBuilder,

                NAME: 'liferayformbuilder',

                prototype: {
                    initializer: function() {
                        var instance = this;

                        instance.LOCALIZABLE_FIELD_ATTRS = A.Array(LOCALIZABLE_FIELD_ATTRS);
                        instance.MAP_HIDDEN_FIELD_ATTRS = A.clone(MAP_HIDDEN_FIELD_ATTRS);

                        var translationManager = instance.translationManager = new Liferay.TranslationManager(instance.get('translationManager'));

                        instance.after(
                            'render',
                            function(event) {
                                translationManager.render();
                            }
                        );

                        instance.addTarget(Liferay.Util.getOpener().Liferay);

                        instance._toggleInputDirection(translationManager.get('defaultLocale'));
                    },

                    bindUI: function() {
                        var instance = this;

                        LiferayFormBuilder.superclass.bindUI.apply(instance, arguments);

                        instance.translationManager.after('defaultLocaleChange', instance._onDefaultLocaleChange, instance);
                        instance.translationManager.after('editingLocaleChange', instance._afterEditingLocaleChange, instance);
                    },

                    createField: function() {
                        var instance = this;

                        var field = LiferayFormBuilder.superclass.createField.apply(instance, arguments);

                        field.set('readOnlyAttributes', instance._getReadOnlyFieldAttributes(field));
                        field.set('strings', instance.get('strings'));

                        return field;
                    },

                    getContentXSD: function() {
                        var instance = this;

                        return window[instance.get('portletNamespace') + 'getContentXSD']();
                    },

                    getFieldLocalizedValue: function(field, attribute, locale) {
                        var instance = this;

                        var localizationMap = field.get('localizationMap');

                        var value = A.Object.getValue(localizationMap, [locale, attribute]) || field.get(attribute);

                        return instance.normalizeValue(value);
                    },

                    getXSD: function() {
                        var instance = this;

                        var buffer = [];

                        var translationManager = instance.translationManager;

                        var editingLocale = translationManager.get('editingLocale');

                        instance._updateFieldsLocalizationMap(editingLocale);

                        var root = instance._createDynamicNode(
                            'root',
                            {
                                'available-locales': translationManager.get('availableLocales').join(),
                                'default-locale': translationManager.get('defaultLocale')
                            }
                        );

                        buffer.push(root.openTag);

                        instance.get('fields').each(
                            function(item, index, collection) {
                                instance._appendStructureTypeElementAndMetaData(item, buffer);
                            }
                        );

                        buffer.push(root.closeTag);

                        return buffer.join(STR_BLANK);
                    },

                    normalizeValue: function(value) {
                        var instance = this;

                        if (Lang.isUndefined(value)) {
                            value = STR_BLANK;
                        }

                        return value;
                    },

                    _afterEditingLocaleChange: function(event) {
                        var instance = this;

                        var editingField = instance.editingField;

                        if (editingField) {
                            editingField.set('readOnlyAttributes', instance._getReadOnlyFieldAttributes(editingField));
                        }

                        instance._updateFieldsLocalizationMap(event.prevVal);

                        instance._syncFieldsLocaleUI(event.newVal);

                        instance._toggleInputDirection(event.newVal);
                    },

                    _appendStructureChildren: function(field, buffer) {
                        var instance = this;

                        field.get('fields').each(
                            function(item, index, collection) {
                                instance._appendStructureTypeElementAndMetaData(item, buffer);
                            }
                        );
                    },

                    _appendStructureFieldOptionsBuffer: function(field, buffer) {
                        var instance = this;

                        var options = field.get('options');

                        if (options) {
                            AArray.each(
                                options,
                                function(item, index, collection) {
                                    var name = item.name;

                                    if (!name) {
                                        name = A.FormBuilderField.buildFieldName('option');
                                    }

                                    var typeElementOption = instance._createDynamicNode(
                                        'dynamic-element',
                                        {
                                            name: name,
                                            type: 'option',
                                            value: Liferay.Util.escapeHTML(item.value)
                                        }
                                    );

                                    buffer.push(typeElementOption.openTag);

                                    instance._appendStructureOptionMetaData(item, buffer);

                                    buffer.push(typeElementOption.closeTag);
                                }
                            );
                        }
                    },

                    _appendStructureOptionMetaData: function(option, buffer) {
                        var instance = this;

                        var localizationMap = option.localizationMap;

                        var labelTag = instance._createDynamicNode(
                            'entry',
                            {
                                name: 'label'
                            }
                        );

                        A.each(
                            localizationMap,
                            function(item, index, collection) {
                                if (isObject(item)) {
                                    var metadataTag = instance._createDynamicNode(
                                        'meta-data',
                                        {
                                            locale: index
                                        }
                                    );

                                    var labelVal = instance.normalizeValue(item.label);

                                    buffer.push(
                                        metadataTag.openTag,
                                        labelTag.openTag,
                                        STR_CDATA_OPEN + labelVal + STR_CDATA_CLOSE,
                                        labelTag.closeTag,
                                        metadataTag.closeTag
                                    );
                                }
                            }
                        );
                    },

                    _appendStructureTypeElementAndMetaData: function(field, buffer) {
                        var instance = this;

                        var typeElement = instance._createDynamicNode(
                            'dynamic-element',
                            {
                                dataType: field.get('dataType'),
                                fieldNamespace: field.get('fieldNamespace'),
                                indexType: field.get('indexType'),
                                localizable: field.get('localizable'),
                                multiple: field.get('multiple'),
                                name: field.get('name'),
                                readOnly: field.get('readOnly'),
                                repeatable: field.get('repeatable'),
                                required: field.get('required'),
                                showLabel: field.get('showLabel'),
                                type: field.get('type'),
                                width: field.get('width'),
                                selectAll: field.get('selectAll'),
                                placeholder: field.get('placeholder')
                            }
                        );

                        buffer.push(typeElement.openTag);

                        instance._appendStructureFieldOptionsBuffer(field, buffer);

                        instance._appendStructureChildren(field, buffer);

                        var availableLocales = instance.translationManager.get('availableLocales');

                        AArray.each(
                            availableLocales,
                            function(item1, index1, collection1) {
                                var metadata = instance._createDynamicNode(
                                    'meta-data',
                                    {
                                        locale: item1
                                    }
                                );

                                buffer.push(metadata.openTag);

                                AArray.each(
                                    field.getProperties(),
                                    function(item2, index2, collection2) {
                                        var attributeName = item2.attributeName;

                                        if (!XML_ATTRIBUTES_FIELD_ATTRS[attributeName]) {
                                            var attributeTag = instance._createDynamicNode(
                                                'entry',
                                                {
                                                    name: attributeName
                                                }
                                            );

                                            var attributeValue = instance.getFieldLocalizedValue(field, attributeName, item1);

                                            if ((attributeName === 'predefinedValue') && instanceOf(field, A.FormBuilderMultipleChoiceField)) {
                                                attributeValue = A.JSON.stringify(AArray(attributeValue));
                                            }

                                            buffer.push(
                                                attributeTag.openTag,
                                                STR_CDATA_OPEN + attributeValue + STR_CDATA_CLOSE,
                                                attributeTag.closeTag
                                            );
                                        }
                                    }
                                );

                                buffer.push(metadata.closeTag);
                            }
                        );

                        buffer.push(typeElement.closeTag);
                    },

                    _createDynamicNode: function(nodeName, attributeMap) {
                        var instance = this;

                        var attrs = [];
                        var typeElement = [];

                        if (!nodeName) {
                            nodeName = 'dynamic-element';
                        }

                        MAP_ELEMENT_DATA.attributeList = STR_BLANK;
                        MAP_ELEMENT_DATA.nodeName = nodeName;

                        if (attributeMap) {
                            A.each(
                                attributeMap,
                                function(item, index, collection) {
                                    if (item !== undefined) {
                                        attrs.push([index, '="', item, '" '].join(STR_BLANK));
                                    }
                                }
                            );

                            MAP_ELEMENT_DATA.attributeList = STR_SPACE + attrs.join(STR_BLANK);
                        }

                        typeElement = Lang.sub(TPL_ELEMENT, MAP_ELEMENT_DATA);
                        typeElement = typeElement.replace(/\s?(>)(<)/, '$1$1$2$2').split(/></);

                        return {
                            closeTag: typeElement[1],
                            openTag: typeElement[0]
                        };
                    },

                    _getReadOnlyFieldAttributes: function(field) {
                        var instance = this;

                        var translationManager = instance.translationManager;

                        var editingLocale = translationManager.get('editingLocale');

                        var readOnlyAttributes = field.get('readOnlyAttributes');

                        if (editingLocale === translationManager.get('defaultLocale')) {
                            AArray.removeItem(readOnlyAttributes, 'name');
                        }
                        else if (AArray.indexOf(readOnlyAttributes, 'name') === -1) {
                            readOnlyAttributes.push('name');
                        }

                        return readOnlyAttributes;
                    },

                    _onDefaultLocaleChange: function(event) {
                        var instance = this;

                        var fields = instance.get('fields');

                        var newVal = event.newVal;

                        var translationManager = instance.translationManager;

                        var availableLanguageIds = translationManager.get('availableLocales');

                        if (availableLanguageIds.indexOf(newVal) < 0) {
                            var config = {
                                fields: fields,
                                newVal: newVal,
                                prevVal: event.prevVal
                            };

                            translationManager.addAvailableLocale(newVal);

                            instance._updateLocalizationMaps(config);
                        }
                    },

                    _onPropertyModelChange: function(event) {
                        var instance = this;

                        var changed = event.changed;

                        var attributeName = event.target.get('attributeName');

                        var editingField = instance.editingField;

                        var readOnlyAttributes = editingField.get('readOnlyAttributes');

                        if (changed.hasOwnProperty('value') && (A.Array.indexOf(readOnlyAttributes, 'name') === -1)) {
                            if (attributeName === 'name') {
                                editingField.set('autoGeneratedName', event.autoGeneratedName === true);
                            }
                            else if ((attributeName === 'label') && editingField.get('autoGeneratedName')) {
                                var translationManager = instance.translationManager;

                                if (translationManager.get('editingLocale') === translationManager.get('defaultLocale')) {
                                    var label = changed.value.newVal;

                                    editingField.set('name', label);

                                    var modelList = instance.propertyList.get('data');

                                    var nameModel = modelList.filter(
                                        function(item, index, collection) {
                                            return (item.get('attributeName') === 'name');
                                        }
                                    );

                                    if (nameModel.length) {
                                        nameModel[0].set(
                                            'value',
                                            editingField.get('name'),
                                            {
                                                autoGeneratedName: true
                                            }
                                        );
                                    }
                                }
                            }
                        }
                    },

                    _renderSettings: function() {
                        var instance = this;

                        LiferayFormBuilder.superclass._renderSettings.apply(instance, arguments);

                        instance.propertyList.on('model:change', instance._onPropertyModelChange, instance);
                    },

                    _setAvailableFields: function(val) {
                        var instance = this;

                        var fields = AArray.map(
                            val,
                            function(item, index, collection) {
                                return A.instanceOf(item, A.AvailableField) ? item : new A.LiferayAvailableField(item);
                            }
                        );

                        fields.sort(
                            function(a, b) {
                                return A.ArraySort.compare(a.get('label'), b.get('label'));
                            }
                        );

                        return fields;
                    },

                    _syncFieldOptionsLocaleUI: function(field, locale) {
                        var instance = this;

                        var options = field.get('options');

                        AArray.each(
                            options,
                            function(item, index, collection) {
                                var localizationMap = item.localizationMap;

                                if (isObject(localizationMap)) {
                                    var localeMap = localizationMap[locale];

                                    if (isObject(localeMap)) {
                                        item.label = localeMap.label;
                                    }
                                }
                            }
                        );

                        field.set('options', options);
                    },

                    _syncFieldsLocaleUI: function(locale, fields) {
                        var instance = this;

                        fields = fields || instance.get('fields');

                        fields.each(
                            function(field, index, fields) {
                                if (instanceOf(field, A.FormBuilderMultipleChoiceField)) {
                                    instance._syncFieldOptionsLocaleUI(field, locale);
                                }

                                var localizationMap = field.get('localizationMap');
                                var localeMap = localizationMap[locale];

                                if (isObject(localizationMap) && isObject(localeMap)) {
                                    AArray.each(
                                        instance.LOCALIZABLE_FIELD_ATTRS,
                                        function(item, index, collection) {
                                            field.set(item, localeMap[item]);
                                        }
                                    );

                                    instance._syncUniqueField(field);
                                }

                                if (instance.editingField === field) {
                                    instance.propertyList.set('data', field.getProperties());
                                }

                                instance._syncFieldsLocaleUI(locale, field.get('fields'));
                            }
                        );
                    },

                    _toggleInputDirection: function(locale) {
                        var rtl = (Liferay.Language.direction[locale] === 'rtl');

                        BODY.toggleClass('form-builder-ltr-inputs', !rtl);
                        BODY.toggleClass('form-builder-rtl-inputs', rtl);
                    },

                    _updateFieldOptionsLocalizationMap: function(field, locale) {
                        var instance = this;

                        var options = field.get('options');

                        AArray.each(
                            options,
                            function(item, index, collection) {
                                var localizationMap = item.localizationMap;

                                if (!isObject(localizationMap)) {
                                    localizationMap = {};
                                }

                                localizationMap[locale] = {
                                    label: item.label
                                };

                                item.localizationMap = localizationMap;
                            }
                        );

                        field.set('options', options);
                    },

                    _updateFieldsLocalizationMap: function(locale, fields) {
                        var instance = this;

                        fields = fields || instance.get('fields');

                        fields.each(
                            function(item, index, collection) {
                                var localizationMap = {};

                                localizationMap[locale] = item.getAttrs(instance.LOCALIZABLE_FIELD_ATTRS);

                                item.set(
                                    'localizationMap',
                                    A.mix(
                                        localizationMap,
                                        item.get('localizationMap')
                                    )
                                );

                                if (instanceOf(item, A.FormBuilderMultipleChoiceField)) {
                                    instance._updateFieldOptionsLocalizationMap(item, locale);
                                }

                                instance._updateFieldsLocalizationMap(locale, item.get('fields'));
                            }
                        );
                    },

                    _updateLocalizationMaps: function(config) {
                        var instance = this;

                        var fields = config.fields;
                        var newVal = config.newVal;
                        var prevVal = config.prevVal;

                        AArray.each(
                            fields._items,
                            function(field) {
                                var childFields = field.get('fields');
                                var localizationMap = field.get('localizationMap');

                                var config = {
                                    fields: childFields,
                                    newVal: newVal,
                                    prevVal: prevVal
                                };

                                localizationMap[newVal] = localizationMap[prevVal];

                                instance._updateLocalizationMaps(config);
                            }
                        );
                    }
                },

                normalizeKey: function(str) {
                    A.each(
                        str,
                        function(item, index, collection) {
                            if (!A.Text.Unicode.test(item, 'L') && !A.Text.Unicode.test(item, 'N') && !A.Text.Unicode.test(item,'Pd')) {
                                str = str.replace(item, STR_SPACE);
                            }
                        }
                    );

                    return str.replace(/\s/g, '_');
                }
            }
        );

        LiferayFormBuilder.DEFAULT_ICON_CLASS = 'icon-fb-custom-field';

        var AVAILABLE_FIELDS = {
            DEFAULT: [
                {
                    fieldLabel: Liferay.Language.get('button'),
                    iconClass: 'form-builder-field-icon form-builder-field-icon-button',
                    label: Liferay.Language.get('button'),
                    type: 'button'
                },
                {
                    fieldLabel: Liferay.Language.get('checkbox'),
                    iconClass: 'icon-fb-boolean',
                    label: Liferay.Language.get('checkbox'),
                    type: 'checkbox'
                },
                {
                    fieldLabel: Liferay.Language.get('fieldset'),
                    iconClass: 'form-builder-field-icon form-builder-field-icon-fieldset',
                    label: Liferay.Language.get('fieldset'),
                    type: 'fieldset'
                },
                {
                    fieldLabel: Liferay.Language.get('text-box'),
                    iconClass: 'icon-fb-text',
                    label: Liferay.Language.get('text-box'),
                    type: 'text'
                },
                {
                    fieldLabel: Liferay.Language.get('text-area'),
                    iconClass: 'icon-fb-text-box',
                    label: Liferay.Language.get('text-area'),
                    type: 'textarea'
                },
                {
                    fieldLabel: Liferay.Language.get('radio-buttons'),
                    iconClass: 'icon-fb-radio',
                    label: Liferay.Language.get('radio-buttons'),
                    type: 'radio'
                },
                {
                    fieldLabel: Liferay.Language.get('select-option'),
                    iconClass: 'icon-fb-select',
                    label: Liferay.Language.get('select-option'),
                    type: 'select'
                }
            ],

            DDM_STRUCTURE: [
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.checkbox,
                    iconClass: 'icon-fb-boolean',
                    label: Liferay.Language.get('boolean'),
                    type: 'checkbox'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-calendar',
                    label: Liferay.Language.get('date'),
                    type: 'ddm-date'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-decimal',
                    label: Liferay.Language.get('decimal'),
                    type: 'ddm-decimal'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-file-text',
                    label: Liferay.Language.get('documents-and-media'),
                    type: 'ddm-documentlibrary'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-edit-sign',
                    label: Liferay.Language.get('html'),
                    type: 'ddm-text-html'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-integer',
                    label: Liferay.Language.get('integer'),
                    type: 'ddm-integer'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-link',
                    label: Liferay.Language.get('link-to-page'),
                    type: 'ddm-link-to-page'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-number',
                    label: Liferay.Language.get('number'),
                    type: 'ddm-number'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-radio',
                    label: Liferay.Language.get('radio'),
                    type: 'radio'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-select',
                    label: Liferay.Language.get('select'),
                    type: 'select'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-text',
                    label: Liferay.Language.get('text'),
                    type: 'text'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-text-box',
                    label: Liferay.Language.get('text-box'),
                    type: 'textarea'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-boolean',
                    label: Liferay.Language.get('Checkbox Group'),
                    type: 'ddm-checkbox-group'
                }
            ],

            DDM_TEMPLATE: [
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-paragraph',
                    label: Liferay.Language.get('paragraph'),
                    type: 'ddm-paragraph'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-separator',
                    label: Liferay.Language.get('separator'),
                    type: 'ddm-separator'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-fb-fieldset',
                    label: Liferay.Language.get('fieldset'),
                    type: 'fieldset'
                }
            ],

            WCM_STRUCTURE: [
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
                    iconClass: 'icon-picture',
                    label: Liferay.Language.get('image'),
                    type: 'wcm-image'
                },
                {
                    hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.separator,
                    iconClass: 'icon-fb-separator',
                    label: Liferay.Language.get('separator'),
                    type: 'ddm-separator'
                }
            ]
        };

        AVAILABLE_FIELDS.WCM_STRUCTURE = AVAILABLE_FIELDS.WCM_STRUCTURE.concat(AVAILABLE_FIELDS.DDM_STRUCTURE);

        LiferayFormBuilder.AVAILABLE_FIELDS = AVAILABLE_FIELDS;

        Liferay.FormBuilder = LiferayFormBuilder;
    },
    '',
    {
        requires: ['arraysort', 'aui-form-builder', 'aui-form-validator', 'aui-text-unicode', 'json', 'liferay-menu', 'liferay-translation-manager', 'liferay-util-window', 'text']
    }
);