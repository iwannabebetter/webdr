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
                <s:submit key="button.delete" method="delete" onclick="return confirmDelete('este paciente')"/>
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
        <label class="desc">Datos Propios del Paciente</label>
        <div class="group">
            <li>
                <div>
                    <s:textfield key="paciente.cedula" cssClass="text medium" required="true"/>
                </div>
            </li>
            <li>
                <div class="left">
                    <s:datetimepicker key="paciente.fechaNacimiento" theme="ajax" required="true"
                        dayWidth="wide" displayFormat="dd/MM/yyyy" toggleType="fade"/>
                </div>
                <div>
                    <s:datetimepicker key="paciente.fechaIngreso" theme="ajax" required="true"
                        dayWidth="wide" displayFormat="dd/MM/yyyy" toggleType="fade"/>
                </div>
            </li>
            <li>
                <div>
                    <s:set name="tipoSangres" value="tipoSangres" scope="request"/>
                    <s:select key="paciente.tipoSangre.id" list="tipoSangres" listKey="id" listValue="nombre" />
                </div>
            </li>
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
    
}
</script>
