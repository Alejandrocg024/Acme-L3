<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${_command == 'show'}">
		<acme:input-moment code="any.peep.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	</jstl:if>
	<acme:input-textbox code="any.peep.form.label.title" path="title"/>	
	<acme:input-textbox code="any.peep.form.label.nick" path="nick"/>	
	<acme:input-textbox code="any.peep.form.label.message" path="message"/>	
	<acme:input-email code="any.peep.form.label.email" path="email"/>
	<acme:input-url code="any.peep.form.label.link" path="link"/>
	
	<jstl:if test="${_command == 'publish'}">
		<acme:submit code="any.peep.form.button.publish" action="/any/peep/publish" />
	</jstl:if>	
</acme:form>
