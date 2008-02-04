<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<s:form name="doctorView" action="saveDoctor" method="post" validate="true">
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

    <s:label key="doctor.username" cssClass="text large"/>
    <s:textfield key="doctor.passwordHint" required="true" cssClass="text large"/>

    <li>
        <div>
            <div class="left">
                <s:label key="doctor.firstName" theme="xhtml" cssClass="text medium"/>
            </div>
            <div>
                <s:label key="doctor.lastName" theme="xhtml" cssClass="text medium"/>
            </div>
        </div>
    </li>
    <li>
        <label class="desc"><u>Datos Propios del Doctor</u></label>
        <div class="group">
            <div>
                <s:label key="doctor.registro" cssClass="text large"/>
            </div>
            <div>
                <s:label key="doctor.fechaNacimiento" cssClass="text large"/>
            </div>
            <div>
                <table class="pickList">
                    <tr>
                        <th class="pickLabel">
                            <label class="required">Especialidades del Doctor</label>
                        </th>
                    </tr>
                    <s:set name="rightList" value="doctor.especialidadList" scope="request"/>
                    <c:import url="/WEB-INF/pages/pickList.jsp">
                        <c:param name="listCount" value="1"/>
                        <c:param name="rightId" value="doctorEspecialidad"/>
                    </c:import>
                </table>
            </div>
            <div>
                Horarios
            </div>            
        </div>
    </li>
    <li>
        <div>
            <div class="left">
                <s:label key="doctor.email" theme="xhtml" cssClass="text medium"/>
            </div>
            <div>
                <s:label key="doctor.phoneNumber" theme="xhtml" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <s:label key="doctor.website" cssClass="text large"/>

    <li>
        <label class="desc"><fmt:message key="doctor.address.address"/></label>
        <div class="group">
            <div>
                <s:label key="doctor.address.address" theme="xhtml" cssClass="text large" labelposition="bottom"/>
            </div>
            <div class="left">
                <s:label key="doctor.address.city" theme="xhtml" cssClass="text medium" 
                    labelposition="bottom"/>
            </div>
            <div>
                <s:label key="doctor.address.province" theme="xhtml" cssClass="text state" 
                    labelposition="bottom"/>
            </div>
            <div class="left">
                <s:label key="doctor.address.postalCode" theme="xhtml" cssClass="text medium" 
                    labelposition="bottom"/>
            </div>
            <div>
                <s:label key="doctor.address.country" theme="xhtml" cssClass="text medium" 
                    labelposition="bottom"/>
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
