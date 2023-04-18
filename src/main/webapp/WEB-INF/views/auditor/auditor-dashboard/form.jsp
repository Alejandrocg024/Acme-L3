<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="auditor.auditorDashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.average-num-auditing-records"/>
		</th>
		<td>
			<acme:print value="${numOfAuditingRecordsStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.max-num-auditing-records"/>
		</th>
		<td>
			<acme:print value="${numOfAuditingRecordsStats.getMax()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.min-num-auditing-records"/>
		</th>
		<td>
			<acme:print value="${numOfAuditingRecordsStats.getMin()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.lin-dev-num-auditing-records"/>
		</th>
		<td>
			<acme:print value="${numOfAuditingRecordsStats.getLinDev()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.average-time-period"/>
		</th>
		<td>
			<acme:print value="${periodOfAuditingRecordStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.max-time-period"/>
		</th>
		<td>
			<acme:print value="${periodOfAuditingRecordStats.getMax()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.min-time-period"/>
		</th>
		<td>
			<acme:print value="${periodOfAuditingRecordStats.getMin()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.lin-dev-time-period"/>
		</th>
		<td>
			<acme:print value="${periodOfAuditingRecordStats.getLinDev()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.hands-on-audits"/>
		</th>
		<td>
			<acme:print value="${numberOfHandsOnAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.theoretical-audits"/>
		</th>
		<td>
			<acme:print value="${numberOfTheoreticalAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.balanced-audits"/>
		</th>
		<td>
			<acme:print value="${numberOfBalancedAudits}"/>
		</td>
	</tr>	
</table>
 
<acme:return/>

