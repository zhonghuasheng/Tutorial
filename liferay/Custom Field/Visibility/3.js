<#include "../init.ftl">

<#assign multiple = false>
<#assign fieldType = "select-one">

<#if fieldStructure.multiple?? && (fieldStructure.multiple == "true")>
    <#assign multiple = true>
    <#assign fieldType = "select-multiple">
</#if>

<#if required>
    <#assign label = label + " (" + languageUtil.get(requestedLocale, "required") + ")">
</#if>

<#assign isPlaceholderNeeded = false>

<#if fieldStructure.placeholder?? && fieldStructure.placeholder?trim?length gt 0>
    <#assign isPlaceholderNeeded = true>
</#if>

<@aui["field-wrapper"] data=data>
    <@aui.select cssClass=cssClass helpMessage=escape(fieldStructure.tip) label=escape(label) multiple=multiple name=namespacedFieldName required=required showRequiredLabel=false>
        <#if isPlaceholderNeeded>
            <option cssClass=cssClass disabled selected value>${fieldStructure.placeholder}</option>
        </#if>

        ${fieldStructure.children}
    </@aui.select>
</@>

<#include "../decorate.ftl">
