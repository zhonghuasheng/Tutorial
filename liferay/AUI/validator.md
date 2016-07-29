Step 1: Reference the AUI Taglib.

<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
Step 2: Configure the Form in the View JSP.

    <aui:form name="myForm" action="" method="post">
        <aui:input name="Name" value="" label="Name">

        </aui:input>
    </aui:form>

Step 3: Insert and Configure the AUI validator Tag in the View JSP.

    <aui:form name="myForm" action="" method="post">
        <aui:input name="Name" value="" label="Name">
            <aui:validator name="required" />
        </aui:input>
    </aui:form>

alpha: Allows only alphabetic characters.

alphanum: Allows only alphanumeric characters.

date: Allows only a date.

digits: Allows only digits.

email: Allows only an email address.

equalTo: Allows only contents equal some other field that has the specified field ID. The ID is declared in the opening and closing validator tags. For example <aui:validator name="equalTo">'#<portlet:namespace />password'</aui:validator>

max: Allows only an integer value less than the specified value. For example, a max value of 20 is specified here <aui:validator name="max">20</aui:validator>

maxLength: Allows a maximum field length of the specified size. The syntax is the same as max.

min: Allows only an integer value greater than the specified value. The syntax is the same as max.

minLength: Allows a field length longer than the specified size. The syntax is the same as max.

number: Allows only numerical values.

range: Allows only a number between the specified range. For example, a range between 1.23 and 10 is specified here <aui:validator name="range">[1.23,10]</aui:validator>

rangeLength: Allows a field length between the specified range. For example, a range between 3 and 8 characters long is specified here <aui:validator name="rangeLength">[3,8]</aui:validator>

required: Prevents a blank field.

url: Allows only a URL value.