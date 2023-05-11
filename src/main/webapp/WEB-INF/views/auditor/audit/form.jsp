

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-select code="auditor.audit.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="auditor.audit.form.label.code" path="code" placeholder="ABC123"/>	
	<acme:input-textbox code="auditor.audit.form.label.conclusion" path="conclusion"/>	
	<acme:input-textbox code="auditor.audit.form.label.strongPoints" path="strongPoints"/>	
	<acme:input-textbox code="auditor.audit.form.label.weakPoints" path="weakPoints"/>	
	<jstl:if test="${acme:anyOf(_command, 'show|update|publish')}">
			<acme:input-textbox code="auditor.audit.form.label.mark" path="mark" readonly="true"/>
	</jstl:if>
	
		
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="auditor.audit.form.button.auditing-records" action="/auditor/auditing-record/list?masterId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="auditor.audit.form.button.auditing-records" action="/auditor/auditing-record/list?masterId=${id}"/>
			<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
			<acme:submit code="auditor.audit.form.button.delete" action="/auditor/audit/delete"/>
			<acme:submit code="auditor.audit.form.button.publish" action="/auditor/audit/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>
