

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.offer.list.label.heading" path="heading"  width="40%"/>
	<acme:list-column code="administrator.offer.list.label.summary" path="summary" width="40%" />
	<acme:list-column code="administrator.offer.list.label.price" path="price" width="20%" />


</acme:list>

<acme:button code="administrator.offer.create" action="/administrator/offer/create"/>

