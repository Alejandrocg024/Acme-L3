

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete')}">
		<acme:input-moment code="administrator.banner.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>	
	</jstl:if>
	<acme:input-textbox code="administrator.banner.form.label.slogan" path="slogan" />
	<acme:input-moment code="administrator.banner.form.label.displayPeriodBegin" path="displayPeriodBegin" />
	<acme:input-moment code="administrator.banner.form.label.displayPeriodFinish" path="displayPeriodFinish" />
	<acme:input-url code="administrator.banner.form.label.pictureLink" path="pictureLink" />
	<acme:input-url code="administrator.banner.form.label.webLink" path="webLink" />
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && !readonly}">
			<acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
