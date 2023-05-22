

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete')}">
		<acme:input-moment code="administrator.offer.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>	
	</jstl:if>
	<acme:input-textbox code="administrator.offer.form.label.heading" path="heading" />
	<acme:input-textbox code="administrator.offer.form.label.summary" path="summary" />
	<acme:input-moment code="administrator.offer.form.label.startPeriod" path="startPeriod" />
	<acme:input-moment code="administrator.offer.form.label.endPeriod" path="endPeriod" />
	<acme:input-money code="administrator.offer.form.label.price" path="price"/>
	<acme:input-url code="administrator.offer.form.label.furtherInformationLink" path="furtherInformationLink"/>
	<jstl:if test="${_command !='create' }">
		<acme:input-money code="administrator.offer.form.label.money" path="money" readonly="true"/>
	</jstl:if>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')&&!boton}">
			<acme:submit code="administrator.offer.form.button.update" action="/administrator/offer/update"/>
			<acme:submit code="administrator.offer.form.button.delete" action="/administrator/offer/delete"/>
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')&& boton}">
		
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.offer.form.button.create" action="/administrator/offer/create"/>
		</jstl:when>
		
	</jstl:choose>
</acme:form>
