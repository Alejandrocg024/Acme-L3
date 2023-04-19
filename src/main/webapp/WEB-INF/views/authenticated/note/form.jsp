

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${_command == 'show'}">
		<acme:input-moment code="authenticated.note.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	</jstl:if>
	<acme:input-textbox code="authenticated.note.form.label.author" path="author" readonly="true"/>
	<acme:input-textbox code="authenticated.note.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.note.form.label.message" path="message"/>
	<acme:input-email code="authenticated.note.form.label.email" path="email"/>
	<acme:input-url code="authenticated.note.form.label.furtherInformationLink" path="furtherInformationLink"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:input-checkbox code="authenticated.note.form.label.confirmation" path="confirmation"/>
		<acme:submit code="authenticated.note.form.button.create" action="/authenticated/note/create" />
	</jstl:if>
</acme:form>