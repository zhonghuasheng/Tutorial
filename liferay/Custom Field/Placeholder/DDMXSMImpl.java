package com.liferay.portlet.dynamicdatamapping.util;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Region;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.RegionServiceUtil;
import com.liferay.portlet.dynamicdatamapping.constant.Constants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

@DoPrivileged
public class DDMXSDImpl extends DDMXSDImplOrigin {
    private static final Log LOG = LogFactoryUtil.getLog(DDMXSDImpl.class);
    private Map<String, String> countries;

    @Override
    public JSONArray getJSONArray(Element element) throws PortalException {
        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
        Document document = element.getDocument();
        String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(document.asXML());
        List<Element> dynamicElementElements = element.elements(Constants.DYNAMIC_ELEMENT);

        for (Element dynamicElementElement : dynamicElementElements) {
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
            JSONObject localizationMapJSONObject = JSONFactoryUtil.createJSONObject();

            for (Attribute attribute : dynamicElementElement.attributes()) {
                jsonObject.put(attribute.getName(), attribute.getValue());
            }

            jsonObject.put(Constants.AUTO_GENERATED_NAME, false);
            jsonObject.put(Constants.ID, dynamicElementElement.attributeValue(Constants.NAME));
            String type = jsonObject.getString(Constants.TYPE);
            List<Element> metadataElements = dynamicElementElement.elements(Constants.META_DATA);

            for (Element metadataElement : metadataElements) {
                if (metadataElement == null) {
                    continue;
                }

                String locale = metadataElement.attributeValue(Constants.LOCALE);
                JSONObject localeMap = JSONFactoryUtil.createJSONObject();

                for (Element metadataEntryElement : metadataElement.elements()) {
                    String attributeName = metadataEntryElement.attributeValue(Constants.NAME);
                    String attributeValue = metadataEntryElement.getTextTrim();
                    putMetadataValue(localeMap, attributeName, attributeValue, type);

                    if (defaultLanguageId.equals(locale)) {
                        putMetadataValue(jsonObject, attributeName, attributeValue, type);
                    }
                }

                localizationMapJSONObject.put(locale, localeMap);
            }

            jsonObject.put(Constants.LOCALIZATION_MAP, localizationMapJSONObject);
            JSONArray hiddenAttributesJSONArray = JSONFactoryUtil.createJSONArray();

            if (type.equals(DDMImpl.TYPE_CHECKBOX) || type.equals(DDMImpl.TYPE_RADIO)) {
                hiddenAttributesJSONArray.put(Constants.REQUIRED);
            }

            hiddenAttributesJSONArray.put(Constants.READ_ONLY);
            jsonObject.put(Constants.HIDDEN_ATTRIBUTES, hiddenAttributesJSONArray);
            String key = Constants.FIELDS;

            if (type.equals(DDMImpl.TYPE_RADIO) || type.equals(DDMImpl.TYPE_SELECT)
                    || type.equals(Constants.TYPE_RADIO_SINGLE) || type.equals(Constants.TYPE_CHECKBOX_GROUP)
                    || type.equals(DDMImpl.TYPE_RECOMMENDER_IMAGE)) {
                key = Constants.OPTIONS;
            }

            jsonObject.put(key, getJSONArray(dynamicElementElement));
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    @Override
    protected String processFTL(PageContext pageContext, Element element, String mode, boolean readOnly,
            Map<String, Object> freeMarkerContext) throws Exception {
        String fieldNamespace = element.attributeValue(Constants.FIELD_NAMESPACE, _DEFAULT_NAMESPACE);
        TemplateResource templateResource = _defaultTemplateResource;
        Map<String, Object> fieldStructure = (Map<String, Object>) freeMarkerContext.get(Constants.FIELD_STRUCTURE);
        boolean fieldReadOnly = GetterUtil.getBoolean(fieldStructure.get(Constants.READ_ONLY));

        if ((fieldReadOnly && Validator.isNotNull(mode) && StringUtil.equalsIgnoreCase(mode,
                DDMTemplateConstants.TEMPLATE_MODE_EDIT)) || readOnly) {
            fieldNamespace = _DEFAULT_READ_ONLY_NAMESPACE;
            templateResource = _defaultReadOnlyTemplateResource;
        }

        String type = element.attributeValue(Constants.TYPE);
        String templateName = StringUtil.replaceFirst(type, fieldNamespace.concat(StringPool.DASH), StringPool.BLANK);
        StringBundler resourcePath = new StringBundler(5);
        resourcePath.append(_TPL_PATH);
        resourcePath.append(StringUtil.toLowerCase(fieldNamespace));
        resourcePath.append(CharPool.SLASH);
        resourcePath.append(templateName);
        resourcePath.append(_TPL_EXT);
        String resource = resourcePath.toString();
        URL url = getResource(resource);

        if (url != null) {
            templateResource = new URLTemplateResource(resource, url);
        }

        if (templateResource == null) {
            throw new Exception(Constants.UNABLE_LOAD_TEMPLATE_RESOURCE + resource);
        }

        Template template = TemplateManagerUtil.getTemplate(TemplateConstants.LANG_TYPE_FTL, templateResource, false);

        for (Map.Entry<String, Object> entry : freeMarkerContext.entrySet()) {
            template.put(entry.getKey(), entry.getValue());
        }

        Object parentField = template.get(Constants.PARENT_FIELD_STRUCTURE);
        Fields fields = (Fields) template.get(Constants.FIELDS);

        if (parentField != null && parentField instanceof Map) {
            Map<String, String> parentFieldMap = (Map<String, String>) parentField;

            if (Constants.TYPE_CHECKBOX_GROUP.equals(type)
                    || Constants.TYPE_ADDRESS.equals(type)
                    || Constants.TYPE_RADIO.equals(type)
                    || DDMImpl.TYPE_RECOMMENDER_IMAGE.equals(parentFieldMap.get(Constants.TYPE))
                    || (Constants.OPTION.equals(type) && Constants.TYPE_CHECKBOX_GROUP.equals(parentFieldMap
                            .get(Constants.TYPE)))) {
                template.put(Constants.TAG_RANDOM_ID, StringUtil.randomId());
            }

            if ((parentFieldMap.get(Constants.TYPE) != null)
                    && DDMImpl.TYPE_RECOMMENDER_IMAGE.equals(parentFieldMap.get(Constants.TYPE))) {
                template.put(Constants.DATA_SOURCE_SELECTION, parentFieldMap.get(Constants.DATA_SOURCE_SELECTION));
            }
        }

        if (fieldStructure != null && fieldStructure.get(Constants.TYPE) != null
                && Constants.TYPE_ADDRESS.equals(fieldStructure.get(Constants.TYPE))) {
            String fieldName = String.valueOf(fieldStructure.get(Constants.NAME));
            List<Country> countryList = CountryServiceUtil.getCountries();
            countries = new HashMap<String, String>();
            JSONObject regions = JSONFactoryUtil.createJSONObject();

            for (Country country : countryList) {
                long countryId = country.getCountryId();
                countries.put(String.valueOf(countryId), country.getNameCurrentValue());
                List<Region> regionList = RegionServiceUtil.getRegions(countryId);
                JSONArray regionJSON = JSONFactoryUtil.createJSONArray();

                if (regionList != null && regionList.size() > 0) {
                    JSONObject jsonObject = null;

                    for (Region region : regionList) {
                        jsonObject = JSONFactoryUtil.createJSONObject();
                        jsonObject.put(String.valueOf(region.getRegionId()), region.getName());
                        regionJSON.put(jsonObject);
                    }
                }

                regions.put(String.valueOf(countryId), regionJSON);
            }

            if (fields != null && fields.get(fieldName) != null) {
                Field field = fields.get(fieldName);
                Serializable fieldValue = field.getValue();
                JSONObject jsonObject = JSONFactoryUtil.createJSONObject(String.valueOf(fieldValue));
                template.put(Constants.COUNTRY_VALUE, jsonObject.getString(Constants.COUNTRY));
                template.put(Constants.REGION_VALUE, jsonObject.getString(Constants.REGION));
                template.put(Constants.CITY_VALUE, jsonObject.getString(Constants.CITY));
            } else {
                template.put(Constants.COUNTRY_VALUE, StringPool.BLANK);
                template.put(Constants.REGION_VALUE, StringPool.BLANK);
                template.put(Constants.CITY_VALUE, StringPool.BLANK);
            }

            template.put(Constants.COUNTRIES, countries);
            template.put(Constants.REGIONS, regions);
        }

        if (fieldStructure.get(Constants.VALUES) != null) {
            template.put(Constants.VALUES, fieldStructure.get(Constants.VALUES));
        }

        return processFTL(pageContext, template);
    }

    @Override
    public String getFieldHTML(PageContext pageContext, Element element, Fields fields, String portletNamespace,
            String namespace, String mode, boolean readOnly, Locale locale) throws Exception {
        Map<String, Object> freeMarkerContext = getFreeMarkerContext(pageContext, portletNamespace, namespace, element,
                locale);
        Map<String, Object> fieldStructure = (Map<String, Object>) freeMarkerContext.get(Constants.FIELD_STRUCTURE);

        if (fields != null) {
            freeMarkerContext.put(Constants.FIELDS, fields);
        }

        int fieldRepetition = 1;
        int offset = 0;
        DDMFieldsCounter ddmFieldsCounter = getFieldsCounter(pageContext, fields, portletNamespace, namespace);
        String name = element.attributeValue(Constants.NAME);
        String fieldDisplayValue = getFieldsDisplayValue(pageContext, fields);
        String[] fieldsDisplayValues = getFieldsDisplayValues(fieldDisplayValue);
        boolean fieldDisplayable = ArrayUtil.contains(fieldsDisplayValues, name);

        if (fieldDisplayable) {
            Map<String, Object> parentFieldStructure = (Map<String, Object>) freeMarkerContext
                    .get(Constants.PARENT_FIELD_STRUCTURE);
            String parentFieldName = (String) parentFieldStructure.get(Constants.NAME);
            offset = getFieldOffset(fieldsDisplayValues, name, ddmFieldsCounter.get(name));

            if (offset == fieldsDisplayValues.length) {
                return StringPool.BLANK;
            }

            fieldRepetition = countFieldRepetition(fieldsDisplayValues, parentFieldName, offset);
        }

        StringBundler sb = new StringBundler(fieldRepetition);

        while (fieldRepetition > 0) {
            offset = getFieldOffset(fieldsDisplayValues, name, ddmFieldsCounter.get(name));
            String fieldNamespace = StringUtil.randomId();

            if (fieldDisplayable) {
                fieldNamespace = getFieldNamespace(fieldDisplayValue, ddmFieldsCounter, offset);
            }

            fieldStructure.put(Constants.FIELD_NAMESPACE, fieldNamespace);
            fieldStructure.put(Constants.VALUE_INDEX, ddmFieldsCounter.get(name));

            if (fieldDisplayable) {
                ddmFieldsCounter.incrementKey(name);
            }

            String type = element.attributeValue(Constants.TYPE);
            String childrenHTML = null;

            if (Constants.TYPE_CHECKBOX_GROUP.equals(type) || Constants.TYPE_RADIO.equals(type)) {
                childrenHTML = getXHTML(pageContext, element, fields, portletNamespace, namespace, mode, readOnly,
                        locale);
            } else {
                childrenHTML = getHTML(pageContext, element, fields, portletNamespace, namespace, mode, readOnly,
                        locale);
            }

            fieldStructure.put(Constants.CHILDREN, childrenHTML);
            boolean disabled = GetterUtil.getBoolean(fieldStructure.get(Constants.DISABLED), false);

            if (disabled) {
                readOnly = true;
            }

            sb.append(processFTL(pageContext, element, mode, readOnly, freeMarkerContext));
            fieldRepetition--;
        }

        return sb.toString();
    }

    public String getXHTML(PageContext pageContext, Element element, Fields fields, String portletNamespace,
            String namespace, String mode, boolean readOnly, Locale locale) throws Exception {
        List<Element> dynamicElementElements = element.elements(Constants.DYNAMIC_ELEMENT);
        StringBundler sb = new StringBundler(dynamicElementElements.size());
        boolean hasOther = Boolean.parseBoolean(element.attributeValue(Constants.OTHER));
        String[] values = null;

        if (dynamicElementElements != null && dynamicElementElements.size() > 0) {
            values = new String[dynamicElementElements.size()];

            for (int i = 0; i < dynamicElementElements.size(); i++) {
                values[i] = dynamicElementElements.get(i).attributeValue(Constants.VALUE);
            }
        }

        for (Element dynamicElementElement : dynamicElementElements) {
            sb.append(getFieldHTML(pageContext, dynamicElementElement, fields, portletNamespace, namespace, mode,
                    readOnly, locale, values, hasOther));
        }

        return sb.toString();
    }

    public String getFieldHTML(PageContext pageContext, Element element, Fields fields, String portletNamespace,
            String namespace, String mode, boolean readOnly, Locale locale, String[] values, boolean hasOther)
            throws Exception {
        Map<String, Object> freeMarkerContext = getFreeMarkerContext(pageContext, portletNamespace, namespace, element,
                locale);
        Map<String, Object> fieldStructure = (Map<String, Object>) freeMarkerContext.get(Constants.FIELD_STRUCTURE);

        if (fields != null) {
            freeMarkerContext.put(Constants.FIELDS, fields);
        }

        int fieldRepetition = 1;
        int offset = 0;

        DDMFieldsCounter ddmFieldsCounter = getFieldsCounter(pageContext, fields, portletNamespace, namespace);
        String name = element.attributeValue(Constants.NAME);
        String fieldDisplayValue = getFieldsDisplayValue(pageContext, fields);
        String[] fieldsDisplayValues = getFieldsDisplayValues(fieldDisplayValue);
        boolean fieldDisplayable = ArrayUtil.contains(fieldsDisplayValues, name);

        if (fieldDisplayable) {
            Map<String, Object> parentFieldStructure = (Map<String, Object>) freeMarkerContext
                    .get(Constants.PARENT_FIELD_STRUCTURE);
            String parentFieldName = (String) parentFieldStructure.get(Constants.NAME);
            offset = getFieldOffset(fieldsDisplayValues, name, ddmFieldsCounter.get(name));

            if (offset == fieldsDisplayValues.length) {
                return StringPool.BLANK;
            }

            fieldRepetition = countFieldRepetition(fieldsDisplayValues, parentFieldName, offset);
        }

        StringBundler sb = new StringBundler(fieldRepetition);

        while (fieldRepetition > 0) {
            offset = getFieldOffset(fieldsDisplayValues, name, ddmFieldsCounter.get(name));
            String fieldNamespace = StringUtil.randomId();

            if (fieldDisplayable) {
                fieldNamespace = getFieldNamespace(fieldDisplayValue, ddmFieldsCounter, offset);
            }

            fieldStructure.put(Constants.FIELD_NAMESPACE, fieldNamespace);
            fieldStructure.put(Constants.VALUE_INDEX, ddmFieldsCounter.get(name));

            if (fieldDisplayable) {
                ddmFieldsCounter.incrementKey(name);
            }

            String type = element.attributeValue(Constants.TYPE);
            String childrenHTML = null;

            if (Constants.TYPE_CHECKBOX_GROUP.equals(type) || Constants.TYPE_RADIO.equals(type)) {
                childrenHTML = getXHTML(pageContext, element, fields, portletNamespace, namespace, mode, readOnly,
                        locale);
            } else {
                childrenHTML = getHTML(pageContext, element, fields, portletNamespace, namespace, mode, readOnly,
                        locale);
            }

            fieldStructure.put(Constants.CHILDREN, childrenHTML);
            fieldStructure.put(Constants.VALUES, values);
            fieldStructure.put(Constants.OTHER, hasOther);
            boolean disabled = GetterUtil.getBoolean(fieldStructure.get(Constants.DISABLED), false);

            if (disabled) {
                readOnly = true;
            }

            sb.append(processFTL(pageContext, element, mode, readOnly, freeMarkerContext));
            fieldRepetition--;
        }

        return sb.toString();
    }

    @Override
    protected void putMetadataValue(JSONObject jsonObject, String attributeName, String attributeValue, String type) {
        if (DDMImpl.TYPE_RADIO.equals(type) || DDMImpl.TYPE_SELECT.equals(type)
                || Constants.TYPE_CHECKBOX_GROUP.equals(type) || Constants.TYPE_RADIO_SINGLE.equals(type)) {
            if (Constants.PREDEFINDE_VALUE.equals(attributeName)) {
                try {
                    jsonObject.put(attributeName, JSONFactoryUtil.createJSONArray(attributeValue));
                } catch (JSONException e) {
                    LOG.error(Constants.ERROR_PREDEFINED_VALUE, e);
                }

                return;
            }
        }

        jsonObject.put(attributeName, attributeValue);
    }
}
