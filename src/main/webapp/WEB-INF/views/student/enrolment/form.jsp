<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<div style="text-align: center;">
		<jstl:choose>	 
			<jstl:when test="${draftMode == false}">
				<h2>
					<acme:message code="student.enrolment.form.label.draftModeFalse"/>
				</h2>
			</jstl:when>	
			<jstl:when test="${draftMode == true}">
				<h2>
					<acme:message code="student.enrolment.form.label.draftModeTrue"/>
				</h2>
			</jstl:when>			
		</jstl:choose>
	</div>
	<acme:input-textbox code="student.enrolment.form.label.code" path="code" placeholder="ABC123"/>	
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>	
	<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>	
	<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
	<acme:input-double code="student.enrolment.form.label.workTime" path="workTime" readonly="true"/>
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<div style="text-align: center;">
				<h2>
					<acme:message code="student.enrolment.form.label.paymentInfo"/>
				</h2>
			</div>
			<acme:input-textbox code="student.enrolment.form.label.holderName" path="holderName" readonly="true"/>	
			<acme:input-textbox code="student.enrolment.form.label.lowerNibble" path="lowerNibble" readonly="true"/>	
			<acme:button code="student.enrolment.form.button.activities" action="/student/activity/list?enrolmentId=${id}"/>			
		</jstl:when>	
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			<acme:button code="student.enrolment.form.button.publish" action="/student/payment/create?enrolmentId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.register" action="/student/enrolment/create"/>
		</jstl:when>			
	</jstl:choose>
</acme:form>
