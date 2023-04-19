
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${exceptional}">
		<acme:message code="auditor.auditing-record.message.exception"/>
	</jstl:if>
	<acme:input-textbox code="auditor.auditing-record.form.label.subject" path="subject"/>	
	<acme:input-textbox code="auditor.auditing-record.form.label.assessment" path="assessment"/>	
	<acme:input-moment code="auditor.auditing-record.form.label.startPeriod" path="startPeriod" />
	<acme:input-moment code="auditor.auditing-record.form.label.endPeriod" path="endPeriod" />
	<acme:input-select code="auditor.auditing-record.form.label.mark" path="mark" choices="${marks}"/>
	<acme:input-url code="auditor.auditing-record.form.label.furtherInformationLink" path="furtherInformationLink"/>
		
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditing-record.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditing-record.form.button.delete" action="/auditor/auditing-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == true}">
			<acme:submit code="auditor.auditing-record.form.button.create" action="/auditor/auditing-record/create?masterId=${masterId}"/>
		</jstl:when>	
		<jstl:when test="${_command == 'create'&& draftMode == false}">
			<acme:input-checkbox code="auditor.auditing-record.form.button.confirmation" path="confirmation"/>
			<acme:submit code="auditor.auditing-record.form.button.create-exceptional" action="/auditor/auditing-record/create?masterId=${masterId}"/>
		</jstl:when>	
	</jstl:choose>
	
</acme:form>
