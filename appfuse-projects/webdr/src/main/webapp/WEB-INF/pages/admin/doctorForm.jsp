<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<s:form name="doctorForm" action="saveDoctor" method="post" validate="true">
    <li style="display: none">
        <s:hidden key="doctor.id"/>
        <s:hidden key="doctor.version"/>
        <input type="hidden" name="from" value="${param.from}"/>

        <c:if test="${cookieLogin == 'true'}">
            <s:hidden key="doctor.password"/>
            <s:hidden key="doctor.confirmPassword"/>
        </c:if>

        <s:if test="doctor.version == null">
            <input type="hidden" name="encryptPass" value="true" />
        </s:if>
    </li>
    <li class="buttonBar right">
        <c:set var="buttons">
            <s:submit key="button.save" method="save" onclick="onFormSubmit(this.form)"/>
            
        <c:if test="${param.from == 'list' and not empty doctor.id}">
            <s:submit key="button.delete" method="delete" onclick="return confirmDelete('doctor')"/>
        </c:if>
        
            <s:submit key="button.cancel" method="cancel"/>
        </c:set>
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
    <li class="info">
        <c:choose>
            <c:when test="${param.from == 'list'}">
                <p><fmt:message key="userProfile.admin.message"/></p>
            </c:when>
            <c:otherwise>
                <p><fmt:message key="userProfile.message"/></p>
            </c:otherwise>
        </c:choose>
    </li>

    <s:textfield key="doctor.username" cssClass="text large" required="true"/>

    <c:if test="${cookieLogin != 'true'}">
    <li>
        <div>
            <div class="left">
                <s:password key="doctor.password" showPassword="true" theme="xhtml" required="true" 
                    cssClass="text medium" onchange="passwordChanged(this)"/>
            </div>
            <div>
                <s:password key="doctor.confirmPassword" theme="xhtml" required="true" 
                    showPassword="true" cssClass="text medium" onchange="passwordChanged(this)"/>
            </div>
        </div>
    </li>
    </c:if>

    <s:textfield key="doctor.passwordHint" required="true" cssClass="text large"/>

    <li>
        <div>
            <div class="left">
                <s:textfield key="doctor.firstName" theme="xhtml" required="true" cssClass="text medium"/>
            </div>
            <div>
                <s:textfield key="doctor.lastName" theme="xhtml" required="true" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <li>
        <div>
            <div class="left">
                <s:textfield key="doctor.email" theme="xhtml" required="true" cssClass="text medium"/>
            </div>
            <div>
                <s:textfield key="doctor.phoneNumber" theme="xhtml" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <s:textfield key="doctor.website" required="true" cssClass="text large"/>
    
    <s:textfield key="doctor.fechaNacimiento" required="true" cssClass="text large"/>
    
    <s:textfield key="doctor.registro" required="true" cssClass="text large"/>

    <li>
        <label class="desc"><fmt:message key="doctor.address.address"/></label>
        <div class="group">
            <div>
                <s:textfield key="doctor.address.address" theme="xhtml" cssClass="text large" labelposition="bottom"/>
            </div>
            <div class="left">
                <s:textfield key="doctor.address.city" theme="xhtml" required="true" cssClass="text medium" 
                    labelposition="bottom"/>
            </div>
            <div>
                <s:textfield key="doctor.address.province" theme="xhtml" required="true" cssClass="text state" 
                    labelposition="bottom"/>
            </div>
            <div class="left">
                <s:textfield key="doctor.address.postalCode" theme="xhtml" required="true" cssClass="text medium" 
                    labelposition="bottom"/>
            </div>
            <div>
                <s:set name="country" value="doctor.address.country" scope="page"/>
                <appfuse:country name="doctor.address.country" prompt="" default="${country}"/>
                <p>
                    <label for="doctor.address.country">
                        <fmt:message key="doctor.address.country"/> <span class="req">*</span>
                    </label>
                </p>
            </div>
        </div>
    </li>
<c:choose>
    <c:when test="${param.from == 'list'}">
    <li>
        <fieldset>
            <legend><fmt:message key="userProfile.accountSettings"/></legend>
            <s:checkbox key="doctor.enabled" id="doctor.enabled" fieldValue="true" theme="simple"/>
            <label for="doctor.enabled" class="choice"><fmt:message key="doctor.enabled"/></label>

            <s:checkbox key="doctor.accountExpired" id="doctor.accountExpired" fieldValue="true" theme="simple"/>
            <label for="doctor.accountExpired" class="choice"><fmt:message key="doctor.accountExpired"/></label>

            <s:checkbox key="doctor.accountLocked" id="doctor.accountLocked" fieldValue="true" theme="simple"/>
            <label for="doctor.accountLocked" class="choice"><fmt:message key="doctor.accountLocked"/></label>

            <s:checkbox key="doctor.credentialsExpired" id="doctor.credentialsExpired" fieldValue="true" theme="simple"/>
            <label for="doctor.credentialsExpired" class="choice"><fmt:message key="doctor.credentialsExpired"/></label>
        </fieldset>
    </li>
    <li>
        <fieldset>
            <legend><fmt:message key="userProfile.assignRoles"/></legend>
            <table class="pickList">
                <tr>
                    <th class="pickLabel">
                        <label class="required"><fmt:message key="user.availableRoles"/></label>
                    </th>
                    <td></td>
                    <th class="pickLabel">
                        <label class="required"><fmt:message key="doctor.roles"/></label>
                    </th>
                </tr>
                <c:set var="leftList" value="${availableRoles}" scope="request"/>
                <s:set name="rightList" value="doctor.roleList" scope="request"/>
                <c:import url="/WEB-INF/pages/pickList.jsp">
                    <c:param name="listCount" value="1"/>
                    <c:param name="leftId" value="availableRoles"/>
                    <c:param name="rightId" value="userRoles"/>
                </c:import>
            </table>
        </fieldset>
    </li>
    </c:when>
    <c:otherwise>
    <li>
        <strong><fmt:message key="doctor.roles"/>:</strong>
        <s:iterator value="doctor.roleList" status="status">
          <s:property value="label"/><s:if test="!#status.last">,</s:if>
          <input type="hidden" name="doctorRoles" value="<s:property value="value"/>"/>
        </s:iterator>
        <s:hidden name="doctor.enabled" value="%{doctor.enabled}"/>
        <s:hidden name="doctor.accountExpired" value="%{doctor.accountExpired}"/>
        <s:hidden name="doctor.accountLocked" value="%{doctor.accountLocked}"/>
        <s:hidden name="doctor.credentialsExpired" value="%{doctor.credentialsExpired}"/>
    </li>
    </c:otherwise>
</c:choose>
    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement(document.forms["doctorForm"]);
    highlightFormElements();

    function passwordChanged(passwordField) {
        if (passwordField.name == "doctor.password") {
            var origPassword = "<s:property value="doctor.password"/>";
        } else if (passwordField.name == "doctor.confirmPassword") {
            var origPassword = "<s:property value="doctor.confirmPassword"/>";
        }
        
        if (passwordField.value != origPassword) {
            createFormElement("input", "hidden",  "encryptPass", "encryptPass",
                              "true", passwordField.form);
        }
    }

<!-- This is here so we can exclude the selectAll call when roles is hidden -->
function onFormSubmit(theForm) {
<c:if test="${param.from == 'list'}">
    selectAll('userRoles');
</c:if>
}
</script>
