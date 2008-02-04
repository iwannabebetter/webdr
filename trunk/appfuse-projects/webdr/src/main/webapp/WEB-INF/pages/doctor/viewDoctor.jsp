<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="doctorProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='doctorProfile.heading'/>"/>
</head>

<s:label key="doctor.username" cssClass="text large"/>
<s:label key="doctor.passwordHint" cssClass="text large"/>
<s:label key="doctor.firstName" cssClass="text medium"/>
<s:label key="doctor.lastName" cssClass="text medium"/>
<s:label key="doctor.registro" cssClass="text large"/>
<s:label key="doctor.fechaNacimiento" cssClass="text large"/>
<s:label key="doctor.email" cssClass="text medium"/>
<s:label key="doctor.phoneNumber" cssClass="text medium"/>
<s:label key="doctor.website" cssClass="text large"/>
<s:label key="doctor.address.address" cssClass="text large"/>
<s:label key="doctor.address.city" cssClass="text medium" />
<s:label key="doctor.address.province"  cssClass="text state" />
<s:label key="doctor.address.postalCode"cssClass="text medium" />
<s:label key="doctor.address.country" cssClass="text medium" />

<table class="pickList">
    <tr>
        <th class="pickLabel">
            <label class="required">Especialidades del Doctor</label>
        </th>
    </tr>
    <c:forEach var="list" items="${doctor.especialidadList}" varStatus="status">
        <tr><td align="left">-> <c:out value="${list.label}" escapeXml="false"/></td></tr>
    </c:forEach>
</table>