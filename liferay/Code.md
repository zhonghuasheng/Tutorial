1. window.location

    function editLicense() {
            window.location ='<portlet:renderURL ><portlet:param name="<%=PortalConstants.LICENSE_ID%>"
            value="${licenseDetail.id}"/><portlet:param name="<%=PortalConstants.PAGENAME%>"
            value="<%=PortalConstants.PAGENAME_EDIT%>"/></portlet:renderURL>'
    }

2. create url

    var baseUrl = '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(),
    themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>';
    var portletURL = Liferay.PortletURL.createURL( baseUrl )
    window.location = portletURL.toString();

