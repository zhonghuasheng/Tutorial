<#if fieldStructure.rule??>
    <@aui.script>
        AUI().use('aui-core-extends', function(E) {
            if ('${fieldStructure.rule}' != '') {
                E.bindingRule(JSON.parse('${fieldStructure.rule}'), '${namespacedFieldName}', '${fieldStructure.type}');
            }
        });
        </@aui.script>
</#if>