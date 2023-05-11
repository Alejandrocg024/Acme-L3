<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<div style="text-align: center;">
		<h2>
			<acme:message code="student.payment.form.label.payment"/>
		</h2>
	</div>
	<acme:input-textbox code="student.payment.form.label.holderName" path="holderName"/>	
	<acme:input-textbox code="student.payment.form.label.creditCardNumer" path="creditCardNumber" placeholder="4002516849403089"/>	
	<acme:input-textbox code="student.payment.form.label.expirationDate" path="expirationDate" placeholder="student.payment.form.date"/>	
	<acme:input-textbox code="student.payment.form.label.securityCode" path="securityCode" placeholder="123"/>
	<jstl:choose>	 
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.payment.form.button.payment" action="/student/payment/create?enrolmentId=${enrolmentId}"/>			
		</jstl:when>	
	</jstl:choose>
</acme:form>
