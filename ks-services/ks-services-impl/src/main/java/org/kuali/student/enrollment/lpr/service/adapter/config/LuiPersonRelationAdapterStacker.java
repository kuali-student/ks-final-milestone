/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.adapter.config;

import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;

import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.infc.HoldsIdentityServiceInfc;
import org.kuali.student.common.infc.HoldsLprServiceInfc;
import org.kuali.student.common.infc.HoldsLuiServiceInfc;
import org.kuali.student.common.infc.HoldsPermissionServiceInfc;
import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationAdapter;
import org.kuali.student.enrollment.lui.infc.LuiServiceInfc;

/**
 * A example of an adapter that might sit at the top of the stack and converts any
 * runtime exceptions into the formal OperationFailedException
 * <p/>
 * This could be genrated automatically from the contract definitions too.
 *
 * @Author Norm
 */
public class LuiPersonRelationAdapterStacker extends LuiPersonRelationAdapter
		implements LuiPersonRelationServiceInfc,
		HoldsLprServiceInfc,
		HoldsIdentityServiceInfc,
		HoldsPermissionServiceInfc {

	private LuiServiceInfc luiService;
	private IdentityService identityService;
	private PermissionService permissionService;
	private List<LuiPersonRelationServiceInfc> cleanupImpls;
	private List<LuiPersonRelationServiceInfc> calculationImpls;
	private List<LuiPersonRelationServiceInfc> authorizationImpls;
	private List<LuiPersonRelationServiceInfc> validaitonImpls;
	private List<LuiPersonRelationServiceInfc> sideEffectImpls;
	private List<LuiPersonRelationServiceInfc> persistenceImpls;

	public void init() {

		List<LuiPersonRelationServiceInfc> allImpls = new ArrayList<LuiPersonRelationServiceInfc>();
		if (cleanupImpls != null) {
			allImpls.addAll(cleanupImpls);
		}
		if (calculationImpls != null) {
			allImpls.addAll(calculationImpls);
		}
		if (authorizationImpls != null) {
			allImpls.addAll(authorizationImpls);
		}
		if (validaitonImpls != null) {
			allImpls.addAll(validaitonImpls);
		}
		if (sideEffectImpls != null) {
			allImpls.addAll(sideEffectImpls);
		}
		if (persistenceImpls != null) {
			allImpls.addAll(persistenceImpls);
		}
		validateConfiguration(allImpls);

		int i = 0;
		LuiPersonRelationServiceInfc top = allImpls.get(i);
		setLprService(top);
		LuiPersonRelationServiceInfc current = top;
		for (i = 1; i < allImpls.size(); i++) {
			LuiPersonRelationServiceInfc next = allImpls.get(i);
			configure(current, next);
			current = allImpls.get(i);
		}
	    current = allImpls.get(allImpls.size() - 1);
		configure(current, null);
	}

	private void configure(LuiPersonRelationServiceInfc current, LuiPersonRelationServiceInfc next) {
		if (current instanceof HoldsLprServiceInfc) {
			HoldsLprServiceInfc holder = (HoldsLprServiceInfc) current;
			if (holder.getLprService() == null) {
				holder.setLprService(next);
			}
		}
		if (current instanceof HoldsLuiServiceInfc) {
			HoldsLuiServiceInfc holder = (HoldsLuiServiceInfc) current;
			if (holder.getLuiService() == null) {
				holder.setLuiService(this.getLuiService());
			}
		}
		if (current instanceof HoldsIdentityServiceInfc) {
			HoldsIdentityServiceInfc holder = (HoldsIdentityServiceInfc) current;
			if (holder.getIdentityService() == null) {
				holder.setIdentityService(this.getIdentityService());
			}
		}
		if (current instanceof HoldsPermissionServiceInfc) {
			HoldsPermissionServiceInfc holder = (HoldsPermissionServiceInfc) current;
			if (holder.getPermissionService() == null) {
				holder.setPermissionService(this.getPermissionService());
			}
		}
	}

	private void validateConfiguration(List<LuiPersonRelationServiceInfc> allImpls) {
		if (this.persistenceImpls == null || this.persistenceImpls.isEmpty()) {
			throw new IllegalArgumentException("No persistence layers defined");
		}
		for (int i = 0; i < allImpls.size() - 1; i++) {
			if (!(allImpls.get(i) instanceof HoldsLprServiceInfc)) {
				throw new IllegalArgumentException(allImpls.get(i).getClass().getName()
				  + " does not impelment HoldsLprService so it cannot call the next impl in the stack");
			}
		}
		// TODO: other validation
	}

	public List<LuiPersonRelationServiceInfc> getAuthorizationImpls() {
		return authorizationImpls;
	}

	public void setAuthorizationImpls(List<LuiPersonRelationServiceInfc> authorizationImpls) {
		this.authorizationImpls = authorizationImpls;
	}

	public List<LuiPersonRelationServiceInfc> getCalculationImpls() {
		return calculationImpls;
	}

	public void setCalculationImpls(List<LuiPersonRelationServiceInfc> calculationImpls) {
		this.calculationImpls = calculationImpls;
	}

	public List<LuiPersonRelationServiceInfc> getCleanupImpls() {
		return cleanupImpls;
	}

	public void setCleanupImpls(List<LuiPersonRelationServiceInfc> cleanupImpls) {
		this.cleanupImpls = cleanupImpls;
	}

	public List<LuiPersonRelationServiceInfc> getPersistenceImpls() {
		return persistenceImpls;
	}

	public void setPersistenceImpls(List<LuiPersonRelationServiceInfc> persistenceImpls) {
		this.persistenceImpls = persistenceImpls;
	}

	public List<LuiPersonRelationServiceInfc> getSideEffectImpls() {
		return sideEffectImpls;
	}

	public void setSideEffectImpls(List<LuiPersonRelationServiceInfc> sideEffectImpls) {
		this.sideEffectImpls = sideEffectImpls;
	}

	public List<LuiPersonRelationServiceInfc> getValidaitonImpls() {
		return validaitonImpls;
	}

	public void setValidaitonImpls(List<LuiPersonRelationServiceInfc> validaitonImpls) {
		this.validaitonImpls = validaitonImpls;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public LuiServiceInfc getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiServiceInfc luiService) {
		this.luiService = luiService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
}
