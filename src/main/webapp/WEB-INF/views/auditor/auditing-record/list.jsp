
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditing-record.list.label.subject" path="subject"  width="40%"/>
	<acme:list-column code="auditor.auditing-record.list.label.assessment" path="assessment"  width="60%"/>

</acme:list>
<acme:button test = "${createButton && draftMode}" code="auditor.auditing-record.list.button.create" action="/auditor/auditing-record/create?masterId=${masterId}"/>
<acme:button test = "${createButton && !draftMode}" code="auditor.auditing-record.list.button.create-exceptional" action="/auditor/auditing-record/create?masterId=${masterId}"/>


