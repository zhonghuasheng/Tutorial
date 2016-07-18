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