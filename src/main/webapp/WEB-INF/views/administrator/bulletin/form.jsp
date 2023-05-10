

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${_command == 'show'}">
		<acme:input-moment code="administrator.bulletin.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>	
	</jstl:if>
	<acme:input-textbox code="administrator.bulletin.form.label.title" path="title" />
	<acme:input-textbox code="administrator.bulletin.form.label.message" path="message" />
	<acme:input-checkbox code="administrator.bulletin.form.label.critical" path="critical" />
	<acme:input-url code="administrator.bulletin.form.label.furtherInformationLink" path="furtherInformationLink" />
	
	<jstl:choose> 
		<jstl:when test="${_command == 'create'}">
			<acme:input-checkbox code="administrator.bulletin.form.label.confirmation" path="confirmation"/>
			<acme:submit code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create" />
		</jstl:when>
	</jstl:choose>
</acme:form>
