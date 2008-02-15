<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="doctorProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='doctorProfile.heading'/>"/>
    <meta name="menu" content="DoctorMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<s:form name="doctorForm" action="saveDoctor" method="post" validate="true" namespace="/doctor">
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
            <c:if test="${deleteAccess and not empty doctor.id}">
                <s:submit key="button.delete" method="delete" onclick="return confirmDelete('este doctor')"/>
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
        <label class="desc">Datos Propios del Doctor</label>
        <div class="group">
            <li>
                <div>
                    <s:textfield key="doctor.registro" theme="xhtml" required="true" cssClass="text small"/>
                </div>
            </li>
            <li>
                <div>
                    <s:datetimepicker key="doctor.fechaNacimiento" theme="ajax" required="true"
                                      dayWidth="wide" displayFormat="dd/MM/yyyy" toggleType="fade"/>
                </div>
            </li>
            <li>
                <fieldset>
                    <legend><fmt:message key="doctorProfile.asignarEspecialidades"/></legend>
                    <table class="pickList">
                        <tr>
                            <th class="pickLabel">
                                <label class="required">
                                    <fmt:message key="doctor.especialidadesPermitidas"/>
                                </label>
                            </th>
                            <td></td>
                            <th class="pickLabel">
                                <label class="required">
                                    <fmt:message key="doctor.especialidades"/>
                                </label>
                            </th>
                        </tr>
                        <c:set var="leftList" value="${availableEspecialidades}" scope="request"/>
                        <s:set name="rightList" value="doctor.especialidadList" scope="request"/>
                        <c:import url="/WEB-INF/pages/pickList.jsp">
                            <c:param name="listCount" value="1"/>
                            <c:param name="leftId" value="availableEspecialidades"/>
                            <c:param name="rightId" value="doctorEspecialidad"/>
                        </c:import>
                    </table>
                </fieldset>
            </li>
            <li>
                <div>
                    <s:url id="horarioUrl" action="doctorHorarios">
                        <s:param name="doctorUsername" value="%{doctor.username}" />
                    </s:url>
                    <s:a href="%{horarioUrl}">
                        <b><fmt:message key="doctor.horarioAtencion"/></b>
                    </s:a>
                </div>
            </li>
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
    selectAll('doctorEspecialidad');
    </c:if>
}
</script>
