<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<s:form name="pacienteForm" action="savePaciente" method="post" validate="true">
    <li style="display: none">
        <s:hidden key="paciente.id"/>
        <s:hidden key="paciente.version"/>
        <input type="hidden" name="from" value="${param.from}"/>

        <c:if test="${cookieLogin == 'true'}">
            <s:hidden key="paciente.password"/>
            <s:hidden key="paciente.confirmPassword"/>
        </c:if>

        <s:if test="paciente.version == null">
            <input type="hidden" name="encryptPass" value="true" />
        </s:if>
    </li>
    <li class="buttonBar right">
        <c:set var="buttons">
            <s:submit key="button.save" method="save" onclick="onFormSubmit(this.form)"/>
            
        <c:if test="${param.from == 'list' and not empty paciente.id}">
            <s:submit key="button.delete" method="delete" onclick="return confirmDelete('paciente')"/>
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

    <s:textfield key="paciente.username" cssClass="text large" required="true"/>

    <li>
        <label class="desc">Datos Propios del Paciente</label>
        <div class="group">
            <div>
                <s:textfield key="paciente.cedula" cssClass="text large" required="true"/>
            </div>
            <div>
                <s:textfield key="paciente.fechaNacimiento" required="true" cssClass="text large"/><br>
            </div>
            <div>
                <s:textfield key="paciente.fechaIngreso" required="true" cssClass="text large"/><br>
            </div>
            <div>
                <s:set name="tipoSangres" value="tipoSangres" scope="request"/>
                <s:select key="paciente.tipoSangre.id" list="tipoSangres" listKey="id" listValue="nombre" />
            </div>            
        </div>
    </li>


    <c:if test="${cookieLogin != 'true'}">
    <li>
        <div>
            <div class="left">
                <s:password key="paciente.password" showPassword="true" theme="xhtml" required="true" 
                    cssClass="text medium" onchange="passwordChanged(this)"/>
            </div>
            <div>
                <s:password key="paciente.confirmPassword" theme="xhtml" required="true" 
                    showPassword="true" cssClass="text medium" onchange="passwordChanged(this)"/>
            </div>
        </div>
    </li>
    </c:if>

    <s:textfield key="paciente.passwordHint" required="true" cssClass="text large"/>

    <li>
        <div>
            <div class="left">
                <s:textfield key="paciente.firstName" theme="xhtml" required="true" cssClass="text medium"/>
            </div>
            <div>
                <s:textfield key="paciente.lastName" theme="xhtml" required="true" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <li>
        <div>
            <div class="left">
                <s:textfield key="paciente.email" theme="xhtml" required="true" cssClass="text medium"/>
            </div>
            <div>
                <s:textfield key="paciente.phoneNumber" theme="xhtml" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <s:textfield key="paciente.website" required="true" cssClass="text large"/>

    <li>
        <label class="desc"><fmt:message key="paciente.address.address"/></label>
        <div class="group">
            <div>
                <s:textfield key="paciente.address.address" theme="xhtml" cssClass="text large" labelposition="bottom"/>
            </div>
            <div class="left">
                <s:textfield key="paciente.address.city" theme="xhtml" required="true" cssClass="text medium" 
                    labelposition="bottom"/>
            </div>
            <div>
                <s:textfield key="paciente.address.province" theme="xhtml" required="true" cssClass="text state" 
                    labelposition="bottom"/>
            </div>
            <div class="left">
                <s:textfield key="paciente.address.postalCode" theme="xhtml" required="true" cssClass="text medium" 
                    labelposition="bottom"/>
            </div>
            <div>
                <s:set name="country" value="paciente.address.country" scope="page"/>
                <appfuse:country name="paciente.address.country" prompt="" default="${country}"/>
                <p>
                    <label for="paciente.address.country">
                        <fmt:message key="paciente.address.country"/> <span class="req">*</span>
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
            <s:checkbox key="paciente.enabled" id="paciente.enabled" fieldValue="true" theme="simple"/>
            <label for="paciente.enabled" class="choice"><fmt:message key="paciente.enabled"/></label>

            <s:checkbox key="paciente.accountExpired" id="paciente.accountExpired" fieldValue="true" theme="simple"/>
            <label for="paciente.accountExpired" class="choice"><fmt:message key="paciente.accountExpired"/></label>

            <s:checkbox key="paciente.accountLocked" id="paciente.accountLocked" fieldValue="true" theme="simple"/>
            <label for="paciente.accountLocked" class="choice"><fmt:message key="paciente.accountLocked"/></label>

            <s:checkbox key="paciente.credentialsExpired" id="paciente.credentialsExpired" fieldValue="true" theme="simple"/>
            <label for="paciente.credentialsExpired" class="choice"><fmt:message key="paciente.credentialsExpired"/></label>
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
                        <label class="required"><fmt:message key="user.roles"/></label>
                    </th>
                </tr>
                <c:set var="leftList" value="${availableRoles}" scope="request"/>
                <s:set name="rightList" value="paciente.roleList" scope="request"/>
                <c:import url="/WEB-INF/pages/pickList.jsp">
                    <c:param name="listCount" value="1"/>
                    <c:param name="leftId" value="availableRoles"/>
                    <c:param name="rightId" value="pacienteRoles"/>
                </c:import>
            </table>
        </fieldset>
    </li>
    </c:when>
    <c:otherwise>
    <li>
        <strong><fmt:message key="user.roles"/>:</strong>
        <s:iterator value="user.roleList" status="status">
          <s:property value="label"/><s:if test="!#status.last">,</s:if>
          <input type="hidden" name="pacienteRoles" value="<s:property value="value"/>"/>
        </s:iterator>
        <s:hidden name="paciente.enabled" value="%{paciente.enabled}"/>
        <s:hidden name="paciente.accountExpired" value="%{paciente.accountExpired}"/>
        <s:hidden name="paciente.accountLocked" value="%{paciente.accountLocked}"/>
        <s:hidden name="paciente.credentialsExpired" value="%{paciente.credentialsExpired}"/>
    </li>
    </c:otherwise>
</c:choose>
    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement(document.forms["userForm"]);
    highlightFormElements();

    function passwordChanged(passwordField) {
        if (passwordField.name == "user.password") {
            var origPassword = "<s:property value="user.password"/>";
        } else if (passwordField.name == "user.confirmPassword") {
            var origPassword = "<s:property value="user.confirmPassword"/>";
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
