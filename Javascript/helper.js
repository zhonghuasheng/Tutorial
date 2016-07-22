// 1. 判断是不是JSON格式
var isJson = function(obj){
    return (typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object array]" && obj.length>0);
}

// First find the publisher

if ('${fieldStructure.rule}' != '' && AUI().one('[name*=${fieldStructure.rule}]') != null) {
    var publisher = AUI().one('[name*=${fieldStructure.rule}]');
    if (!AUI().one('[name*=${fieldStructure.rule}]').hasOwnProperty('publish')) {
        AUI().Observer.switcher(publisher);
        var type = AUI().one('[name*=${fieldStructure.rule}]')._node.type;

        if (type === 'select-one') {
            publisher.on('change', function(val) {
                var selectedValue = this.one('option:selected').val();

                if (selectedValue === 'value 2') {
                    this.publish(selectedValue);
                }
            });.
        }
    }

    AUI().one('[name*=${namespacedFieldName}]').action = function(val) {
        if (AUI().one('[name*=${namespacedFieldName}]').hasOwnProperty('publish')) {
            AUI().one('[name*=${namespacedFieldName}]').publish(val);
        }

        AUI().one('[name*=${namespacedFieldName}]').setStyle('display', 'none');
    };
    publisher.addSubscriber(AUI().one('[name*=${namespacedFieldName}]').action);
}
B:[{"bindingTagName": "select_a","bindingTagEvent": "change","expectedValue": "value 2","actionName": "hidden"}]
C:[{"bindingTagName": "select_a","bindingTagEvent": "change","expectedValue": "value 3","actionName": "hidden"}]
D:[{"bindingTagName": "select_b","bindingTagEvent": "change","expectedValue": "value 3","actionName": "hidden"}]
E:[{"bindingTagName": "select_c","bindingTagEvent": "change","expectedValue": "value 3","actionName": "hidden"}]

b[{"bindingName": "select_a","on": "change","value": "value 2","action": "show"}]
c[{"bindingName": "select_a","on": "change","value": "value 3","action": "show"}]
d[{"bindingName": "select_b","on": "change","value": "value 1","action": "show"}]
e[{"bindingName": "select_c","on": "change","value": "value 2","action": "show"}]


            case 'text':
                currentTagNode.get('parentNode').setStyle('display', 'none');
                break;
            case 'textarea':
                currentTagNode.get('parentNode').setStyle('display', 'none');
                break;


AUI().use('event-custom', 'node', function(A) {
    var Observer = {
        addSubscriber: function(callback) {
            this.subscribers[this.subscribers.length] = callback;
        },

        publish: function(val) {
            for(var i = 0; i < this.subscribers.length; i++) {
                if (typeof this.subscribers[i] === 'function') {
                    this.subscribers[i](val);
                }
            }
        },

        switcher: function(obj) {
            obj.subscribers = [];

            for(var i in this) {
                obj[i] = this[i];
            }
        },

        test: function(val) {
            console.log(val);
        }
    };

    A.Observer = Observer;

    A.isJson = function(obj) {
        return (typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object array]" && obj.length>0);
    };

    A.isNullOrEmpty = function(obj) {
        return !(obj != null && obj.trim().length != 0);
    }
});


Condition Visibility Demo

<root available-locales="en_US" default-locale="en_US">
	<dynamic-element dataType="string" indexType="keyword" localizable="true" multiple="false" name="select_a" readOnly="false" repeatable="false" required="false" showLabel="true" type="select" width="" placeholder="">
		<dynamic-element name="option11956" type="option" value="value 1">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 1]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option11957" type="option" value="value 2">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 2]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option11958" type="option" value="value 3">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 3]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<meta-data locale="en_US">
			<entry name="label">
				<![CDATA[Select A(No rule)]]>
			</entry>
			<entry name="predefinedValue">
				<![CDATA[[""]]]>
			</entry>
			<entry name="tip">
				<![CDATA[]]>
			</entry>
			<entry name="rule">
				<![CDATA[]]>
			</entry>
		</meta-data>
	</dynamic-element>
	<dynamic-element dataType="string" indexType="keyword" localizable="true" multiple="false" name="select_b" readOnly="false" repeatable="false" required="false" showLabel="true" type="select" width="" placeholder="">
		<dynamic-element name="option12282" type="option" value="value 1">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 1]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option12283" type="option" value="value 2">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 2]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option12284" type="option" value="value 3">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 3]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<meta-data locale="en_US">
			<entry name="label">
				<![CDATA[Select B(See Tip)]]>
			</entry>
			<entry name="predefinedValue">
				<![CDATA[[""]]]>
			</entry>
			<entry name="tip">
				<![CDATA[[{"bindingName": "select_a","on": "change","value": "value 2","action": "show"}]]]>
			</entry>
			<entry name="rule">
				<![CDATA[[{"bindingName": "select_a","on": "change","value": "value 2","action": "show"}]]]>
			</entry>
		</meta-data>
	</dynamic-element>
	<dynamic-element dataType="string" indexType="keyword" localizable="true" multiple="false" name="select_c" readOnly="false" repeatable="false" required="false" showLabel="true" type="select" width="" placeholder="">
		<dynamic-element name="option12608" type="option" value="value 1">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 1]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option12609" type="option" value="value 2">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 2]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option12610" type="option" value="value 3">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 3]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<meta-data locale="en_US">
			<entry name="label">
				<![CDATA[Select C(See Tip)]]>
			</entry>
			<entry name="predefinedValue">
				<![CDATA[[""]]]>
			</entry>
			<entry name="tip">
				<![CDATA[[{"bindingName": "select_a","on": "change","value": "value 3","action": "show"}]]]>
			</entry>
			<entry name="rule">
				<![CDATA[[{"bindingName": "select_a","on": "change","value": "value 3","action": "show"}]]]>
			</entry>
		</meta-data>
	</dynamic-element>
	<dynamic-element dataType="string" indexType="keyword" localizable="true" multiple="false" name="Select_D_See_Tip_" readOnly="false" repeatable="false" required="false" showLabel="true" type="select" width="" placeholder="">
		<dynamic-element name="option12934" type="option" value="value 1">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 1]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option12935" type="option" value="value 2">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 2]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option12936" type="option" value="value 3">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 3]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<meta-data locale="en_US">
			<entry name="label">
				<![CDATA[Select D(See Tip)]]>
			</entry>
			<entry name="predefinedValue">
				<![CDATA[[""]]]>
			</entry>
			<entry name="tip">
				<![CDATA[[{"bindingName": "select_b","on": "change","value": "value 1","action": "show"}]]]>
			</entry>
			<entry name="rule">
				<![CDATA[[{"bindingName": "select_b","on": "change","value": "value 1","action": "show"}]]]>
			</entry>
		</meta-data>
	</dynamic-element>
	<dynamic-element dataType="string" indexType="keyword" localizable="true" multiple="false" name="Select_E_See_Tip_" readOnly="false" repeatable="false" required="false" showLabel="true" type="select" width="" placeholder="">
		<dynamic-element name="option13260" type="option" value="value 1">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 1]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option13261" type="option" value="value 2">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 2]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<dynamic-element name="option13262" type="option" value="value 3">
			<meta-data locale="en_US">
				<entry name="label">
					<![CDATA[option 3]]>
				</entry>
			</meta-data>
		</dynamic-element>
		<meta-data locale="en_US">
			<entry name="label">
				<![CDATA[Select E(See Tip)]]>
			</entry>
			<entry name="predefinedValue">
				<![CDATA[[""]]]>
			</entry>
			<entry name="tip">
				<![CDATA[[{"bindingName": "select_c","on": "change","value": "value 2","action": "show"}]]]>
			</entry>
			<entry name="rule">
				<![CDATA[[{"bindingName": "select_c","on": "change","value": "value 2","action": "show"}]]]>
			</entry>
		</meta-data>
	</dynamic-element>
</root>