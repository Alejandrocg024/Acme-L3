

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.lecturer.form.label.almaMater" path="almaMater"/>
	<acme:input-textbox code="authenticated.lecturer.form.label.resume" path="resume"/>
	<acme:input-textbox code="authenticated.lecturer.form.label.listOfQualifications" path="listOfQualifications"/>
	<acme:input-url code="authenticated.lecturer.form.label.furtherInformation" path="furtherInformation"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.lecturer.form.button.create" action="/authenticated/lecturer/create"/>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.lecturer.form.button.update" action="/authenticated/lecturer/update"/>
	</jstl:if>	
</acme:form>
