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


import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.infc.HoldsIdentityService;
import org.kuali.student.common.infc.HoldsLprService;
import org.kuali.student.common.infc.HoldsLuiService;
import org.kuali.student.common.infc.HoldsPermissionService;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationServiceAdapter;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.service.LuiService;

/**
 * A example of an adapter that might sit at the top of the stack and converts any
 * runtime exceptions into the formal OperationFailedException
 * <p/>
 * This could be genrated automatically from the contract definitions too.
 *
 * @Author Norm
 */
public class LuiPersonRelationAdapterStacker extends LuiPersonRelationServiceAdapter
		implements HoldsLprService,
		HoldsIdentityService,
		HoldsPermissionService {

	private LuiService luiService;
	private IdentityService identityService;
	private PermissionService permissionService;
	private List<LuiPersonRelationService> cleanupImpls;
	private List<LuiPersonRelationService> calculationImpls;
	private List<LuiPersonRelationService> authorizationImpls;
	private List<LuiPersonRelationService> validaitonImpls;
	private List<LuiPersonRelationService> sideEffectImpls;
	private List<LuiPersonRelationService> persistenceImpls;

	public void init() {

		List<LuiPersonRelationService> allImpls = new ArrayList<LuiPersonRelationService>();
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
		LuiPersonRelationService top = allImpls.get(i);
		setLprService(top);
		LuiPersonRelationService current = top;
		for (i = 1; i < allImpls.size(); i++) {
			LuiPersonRelationService next = allImpls.get(i);
			configure(current, next);
			current = allImpls.get(i);
		}
	    current = allImpls.get(allImpls.size() - 1);
		configure(current, null);
	}

	private void configure(LuiPersonRelationService current, LuiPersonRelationService next) {
		if (current instanceof HoldsLprService) {
			HoldsLprService holder = (HoldsLprService) current;
			if (holder.getLprService() == null) {
				holder.setLprService(next);
			}
		}
		if (current instanceof HoldsLuiService) {
			HoldsLuiService holder = (HoldsLuiService) current;
			if (holder.getLuiService() == null) {
				holder.setLuiService(this.getLuiService());
			}
		}
		if (current instanceof HoldsIdentityService) {
			HoldsIdentityService holder = (HoldsIdentityService) current;
			if (holder.getIdentityService() == null) {
				holder.setIdentityService(this.getIdentityService());
			}
		}
		if (current instanceof HoldsPermissionService) {
			HoldsPermissionService holder = (HoldsPermissionService) current;
			if (holder.getPermissionService() == null) {
				holder.setPermissionService(this.getPermissionService());
			}
		}
	}

	private void validateConfiguration(List<LuiPersonRelationService> allImpls) {
		if (this.persistenceImpls == null || this.persistenceImpls.isEmpty()) {
			throw new IllegalArgumentException("No persistence layers defined");
		}
		for (int i = 0; i < allImpls.size() - 1; i++) {
			if (!(allImpls.get(i) instanceof HoldsLprService)) {
				throw new IllegalArgumentException(allImpls.get(i).getClass().getName()
				  + " does not impelment HoldsLprService so it cannot call the next impl in the stack");
			}
		}
		// TODO: other validation
	}

	public List<LuiPersonRelationService> getAuthorizationImpls() {
		return authorizationImpls;
	}

	public void setAuthorizationImpls(List<LuiPersonRelationService> authorizationImpls) {
		this.authorizationImpls = authorizationImpls;
	}

	public List<LuiPersonRelationService> getCalculationImpls() {
		return calculationImpls;
	}

	public void setCalculationImpls(List<LuiPersonRelationService> calculationImpls) {
		this.calculationImpls = calculationImpls;
	}

	public List<LuiPersonRelationService> getCleanupImpls() {
		return cleanupImpls;
	}

	public void setCleanupImpls(List<LuiPersonRelationService> cleanupImpls) {
		this.cleanupImpls = cleanupImpls;
	}

	public List<LuiPersonRelationService> getPersistenceImpls() {
		return persistenceImpls;
	}

	public void setPersistenceImpls(List<LuiPersonRelationService> persistenceImpls) {
		this.persistenceImpls = persistenceImpls;
	}

	public List<LuiPersonRelationService> getSideEffectImpls() {
		return sideEffectImpls;
	}

	public void setSideEffectImpls(List<LuiPersonRelationService> sideEffectImpls) {
		this.sideEffectImpls = sideEffectImpls;
	}

	public List<LuiPersonRelationService> getValidaitonImpls() {
		return validaitonImpls;
	}

	public void setValidaitonImpls(List<LuiPersonRelationService> validaitonImpls) {
		this.validaitonImpls = validaitonImpls;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
}
