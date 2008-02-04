<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<s:form name="pacienteForm" action="savePaciente" method="post" validate="true">
    <li class="buttonBar right">
        <c:set var="buttons">     
            <s:submit key="button.cancel" method="cancel"/>
        </c:set>
    </li>

    <s:label key="paciente.username" cssClass="text large"/>
    <s:label key="paciente.passwordHint" cssClass="text large"/>

    <li>
        <div>
            <div class="left">
                <s:label key="paciente.firstName" theme="xhtml" cssClass="text medium"/>
            </div>
            <div>
                <s:label key="paciente.lastName" theme="xhtml" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <li>
        <label class="desc">Datos Propios del Paciente</label>
        <div class="group">
            <div>
                <s:label key="paciente.cedula" cssClass="text large"/>
            </div>
            <div>
                <s:label key="paciente.fechaNacimiento" cssClass="text large"/><br>
            </div>
            <div>
                <s:label key="paciente.fechaIngreso" cssClass="text large"/><br>
            </div>
            <div>
                <s:label key="paciente.tipoSangre.nombre" cssClass="text large"/>
            </div>            
        </div>
    </li>
    
    <li>
        <div>
            <div class="left">
                <s:label key="paciente.email" theme="xhtml" cssClass="text medium"/>
            </div>
            <div>
                <s:label key="paciente.phoneNumber" theme="xhtml" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <s:label key="paciente.website" cssClass="text large"/>

    <li>
        <label class="desc"><fmt:message key="paciente.address.address"/></label>
        <div class="group">
            <div>
                <s:label key="paciente.address.address" theme="xhtml" cssClass="text large" labelposition="bottom"/>
            </div>
            <div class="left">
                <s:label key="paciente.address.city" theme="xhtml" cssClass="text medium" labelposition="bottom"/>
            </div>
            <div>
                <s:label key="paciente.address.province" theme="xhtml" cssClass="text state" labelposition="bottom"/>
            </div>
            <div class="left">
                <s:label key="paciente.address.postalCode" theme="xhtml" cssClass="text medium" labelposition="bottom"/>
            </div>
            <div>
				<s:label key="paciente.address.country" theme="xhtml" cssClass="text large" labelposition="bottom"/>
            </div>
        </div>
    </li>
    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</s:form>
