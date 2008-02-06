
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="doctorProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='doctorProfile.heading'/>"/>
</head>

<br>
<s:label key="doctor.firstName" cssClass="text medium"/>
<s:label key="doctor.lastName" cssClass="text medium"/>
<br>
<s:label key="doctor.username" cssClass="text large"/>
<s:label key="doctor.passwordHint" cssClass="text large"/>
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

<br><b>Especialidades del Doctor</b>
<display:table name="doctor.especialidades" class="table" requestURI="" id="especialidadList" export="false"
               pagesize="25">

    <display:column property="nombre" sortable="true" titleKey="especialidad.nombre"/>
    <display:column property="descripcion" sortable="true" titleKey="especialidad.descripcion"/>
    
    <display:setProperty name="paging.banner.item_name" value="especialidades"/>
    <display:setProperty name="paging.banner.items_name" value="especialidades"/>
</display:table>
<br>

<b>Horarios del Doctor</b>
<display:table name="doctor.horarios" class="table" requestURI="" id="horarioAtencionesList" export="true" pagesize="25">        
    <display:column property="dia" sortable="true" titleKey="horarioAtencion.dia"/>
    <display:column property="horaInicioString" sortable="true" titleKey="horarioAtencion.horaInicio"/>
    <display:column property="horaFinString" sortable="true" titleKey="horarioAtencion.horaFin"/>
    <display:column property="doctor.username" sortable="true" titleKey="horarioAtencion.doctor.username"/>

    <display:setProperty name="paging.banner.item_name" value="horario del doctor"/>
    <display:setProperty name="paging.banner.items_name" value="horarios del doctor"/>

    <display:setProperty name="export.excel.filename" value="HorarioDr.xls"/>
    <display:setProperty name="export.csv.filename" value="HorarioDr.csv"/>
    <display:setProperty name="export.pdf.filename" value="HorarioDr.pdf"/>
</display:table>
