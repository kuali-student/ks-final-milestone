<#macro sb_cart_radio element>

	<@krad.div component=element>

		<#if element.selected>
			<input id="${element.id}_radio" type="radio" name="${element.propertyName}" value="${element.optionValue}" checked />
		<#else>
			<input id="${element.id}_radio" type="radio" name="${element.propertyName}" value="${element.optionValue}" />
		</#if>

		<label for="${element.id}_radio">${element.optionLabel}</label>

	</@krad.div>

</#macro>
