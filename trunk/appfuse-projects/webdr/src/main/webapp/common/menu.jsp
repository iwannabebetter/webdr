<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="primary-nav" class="menuList">
    <li class="pad">&nbsp;</li>
    <c:if test="${empty pageContext.request.remoteUser}">
        <li>
            <a href="<c:url value="/login.jsp"/>" class="current">
                <fmt:message key="login.title"/>
            </a>
        </li>
    </c:if>
    <!-- Menus Generales para todos los roles -->
    <menu:displayMenu name="MainMenu"/>
    <!-- Menus exclusivos del Admin -->
    <menu:displayMenu name="AdminDoctorMenu"/>
    <menu:displayMenu name="AdminPacienteMenu"/>
    <menu:displayMenu name="PropiedadMenu"/>
    <!--menu:displayMenu name="AdminUsuarioMenu"/-->
    <!-- Menus exclusivos del Doctor -->
    <menu:displayMenu name="ReservasRealizadas"/>
    <menu:displayMenu name="AdminConsultaMenu"/>
    <menu:displayMenu name="AdminPacienteMenu2"/>
    <!-- Menus exclusivos del Paciente -->
    <menu:displayMenu name="UserMenu2"/>
    <menu:displayMenu name="VerDoctores"/>
    <menu:displayMenu name="ReservaMenu"/>
    <!-- Menu general para logout -->
    <menu:displayMenu name="Logout"/>
</ul>
</menu:useMenuDisplayer>