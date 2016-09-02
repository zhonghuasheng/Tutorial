package com.liferay.portlet.dynamicdatamapping.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.constant.Constants;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;

@DoPrivileged
public class DDMImpl extends DDMImplOrigin {

    private static final String TYPE_CHECKBOX_GROUP = "ddm-checkbox-group";
    private static final String CHECKBOX = "Checkbox";
    public static final String TYPE_RECOMMENDER_IMAGE = "ddm-recommender-image";

    @Override
    public Serializable getDisplayFieldValue(ThemeDisplay themeDisplay, Serializable fieldValue, String type)
            throws Exception {
        if (fieldValue instanceof Date) {
            Date valueDate = (Date) fieldValue;
            DateFormat dateFormat = DateFormatFactoryUtil.getDate(themeDisplay.getLocale());
            fieldValue = dateFormat.format(valueDate);
        } else if (type.equals(DDMImpl.TYPE_CHECKBOX)) {
            Boolean valueBoolean = (Boolean) fieldValue;

            if (valueBoolean) {
                fieldValue = LanguageUtil.get(themeDisplay.getLocale(), Constants.YES);
            } else {
                fieldValue = LanguageUtil.get(themeDisplay.getLocale(), Constants.NO);
            }
        } else if (type.equals(DDMImpl.TYPE_DDM_DOCUMENTLIBRARY)) {
            if (Validator.isNull(fieldValue)) {
                return StringPool.BLANK;
            }

            String valueString = String.valueOf(fieldValue);
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject(valueString);
            String uuid = jsonObject.getString(Constants.UUID);
            long groupId = jsonObject.getLong(Constants.GROUP_ID);
            FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, groupId);
            fieldValue = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK, false,
                    true);
        } else if (type.equals(DDMImpl.TYPE_DDM_LINK_TO_PAGE)) {
            if (Validator.isNull(fieldValue)) {
                return StringPool.BLANK;
            }

            String valueString = String.valueOf(fieldValue);
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject(valueString);
            long groupId = jsonObject.getLong(Constants.GROUP_ID);
            boolean privateLayout = jsonObject.getBoolean(Constants.PRIVATE_LAYOUT);
            long layoutId = jsonObject.getLong(Constants.LAYOUT_ID);
            Layout layout = LayoutLocalServiceUtil.getLayout(groupId, privateLayout, layoutId);
            fieldValue = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
        } else if (type.equals(DDMImpl.TYPE_RADIO) || type.equals(DDMImpl.TYPE_SELECT)
                || type.equals(Constants.TYPE_RADIO_SINGLE) || type.equals(Constants.TYPE_CHECKBOX_GROUP)
                || type.equals(Constants.TYPE_ADDRESS) || type.equals(TYPE_RECOMMENDER_IMAGE)) {
            String valueString = String.valueOf(fieldValue);
            JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);
            String[] stringArray = ArrayUtil.toStringArray(jsonArray);
            fieldValue = stringArray[0];
        }

        return fieldValue;
    }

    @Override
    protected List<Serializable> getFieldValues(DDMStructure ddmStructure, String fieldName, String fieldNamespace,
            ServiceContext serviceContext) throws PortalException, SystemException {

        String fieldDataType = ddmStructure.getFieldDataType(fieldName);
        String fieldType = ddmStructure.getFieldType(fieldName);
        List<String> fieldNames = getFieldNames(fieldNamespace, fieldName, serviceContext);
        List<Serializable> fieldValues = new ArrayList<Serializable>(fieldNames.size());

        for (String fieldNameValue : fieldNames) {
            Serializable fieldValue = null;

            if (fieldType != null && fieldType.equals(Constants.TYPE_CHECKBOX_GROUP)) {
                fieldNameValue = fieldNameValue.concat(Constants.CHECKBOX);
            }

            if (fieldType != null && fieldType.equals(Constants.TYPE_ADDRESS)) {
                fieldValue = JSONFactoryUtil
                        .createJSONObject()
                        .put(Constants.COUNTRY,
                                String.valueOf(serviceContext.getAttribute(fieldNameValue + Constants._COUNTRY)))
                        .put(Constants.REGION,
                                String.valueOf(serviceContext.getAttribute(new StringBuilder(fieldNameValue).append(
                                        Constants._REGION).toString())))
                        .put(Constants.CITY,
                                String.valueOf(serviceContext.getAttribute(new StringBuilder(fieldNameValue).append(
                                        Constants._CITY).toString())));
            } else if (fieldType != null && fieldType.equals(Constants.TYPE_CHECKBOX_GROUP)) {
                fieldValue = serviceContext.getAttribute(fieldNameValue) == null ? StringPool.BLANK : serviceContext
                        .getAttribute(fieldNameValue);
            } else if (fieldType != null && fieldType.equals(TYPE_RECOMMENDER_IMAGE)) {
                fieldValue = serviceContext.getAttribute(fieldNameValue + CHECKBOX);
            } else if (fieldType != null && fieldType.equals(DDMImplOrigin.TYPE_SELECT)) {
                fieldValue = serviceContext.getAttribute(fieldNameValue) == null ?
                    StringPool.BLANK : serviceContext.getAttribute(fieldNameValue);
            } else {
                fieldValue = serviceContext.getAttribute(fieldNameValue);
            }

            if (fieldDataType.equals(FieldConstants.DATE)) {
                int fieldValueMonth = GetterUtil.getInteger(serviceContext.getAttribute(fieldNameValue + "Month"));
                int fieldValueDay = GetterUtil.getInteger(serviceContext.getAttribute(fieldNameValue + "Day"));
                int fieldValueYear = GetterUtil.getInteger(serviceContext.getAttribute(fieldNameValue + "Year"));
                String fieldValueDateString = GetterUtil.getString(serviceContext.getAttribute(fieldNameValue));

                if (Validator.isNull(fieldValueDateString)) {
                    fieldValue = StringPool.BLANK;
                } else {
                    Date fieldValueDate = PortalUtil.getDate(fieldValueMonth, fieldValueDay, fieldValueYear);

                    if (fieldValueDate != null) {
                        fieldValue = String.valueOf(fieldValueDate.getTime());
                    }
                }
            } else if (fieldDataType.equals(FieldConstants.IMAGE) && Validator.isNull(fieldValue)) {
                HttpServletRequest request = serviceContext.getRequest();

                if (!(request instanceof UploadRequest)) {
                    return null;
                }

                fieldValue = getImageFieldValue((UploadRequest) request, fieldNameValue);
            }

            if (fieldValue == null) {
                return null;
            }

            if (DDMImplOrigin.TYPE_RADIO.equals(fieldType) || DDMImplOrigin.TYPE_SELECT.equals(fieldType)
                    || Constants.TYPE_RADIO_SINGLE.equals(fieldType)
                    || DDMImpl.TYPE_RECOMMENDER_IMAGE.equals(fieldType)
                    || DDMImpl.TYPE_CHECKBOX_GROUP.equals(fieldType)) {

                if (fieldValue instanceof String) {
                    fieldValue = new String[] { String.valueOf(fieldValue) };
                }

                fieldValue = JSONFactoryUtil.serialize(fieldValue);
            }

            if (Constants.TYPE_ADDRESS.equals(fieldType)) {
                fieldValues.add(fieldValue);

            } else if (fieldValue != null && fieldValue.getClass().isArray()) {
                for (String string : (String[]) fieldValue) {
                    fieldValues.add(string);
                }

            } else {
                Serializable fieldValueSerializable = FieldConstants.getSerializable(fieldDataType,
                        GetterUtil.getString(fieldValue));
                fieldValues.add(fieldValueSerializable);
            }
        }

        return fieldValues;
    }
}
