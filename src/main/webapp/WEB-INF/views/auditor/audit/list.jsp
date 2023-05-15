

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.code" path="code"  width="20%"/>
	<acme:list-column code="auditor.audit.list.label.courseCode" path="courseCode"  width="40%"/>
	<acme:list-column code="auditor.audit.list.label.courseTitle" path="courseTitle"  width="40%"/>


</acme:list>
<acme:button code="auditor.audit.create" action="/auditor/audit/create"/>


