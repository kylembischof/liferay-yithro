/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.yithro.support.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.yithro.support.constants.SupportPortletKeys;
import com.liferay.yithro.support.constants.SupportWebKeys;
import com.liferay.yithro.support.service.SupportTeamLocalService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	property = {
		"javax.portlet.name=" + SupportPortletKeys.TICKET_SUPPORT,
		"mvc.command.name=/ticket_support/select_support_team_workers"
	},
	service = MVCRenderCommand.class
)
public class SelectSupportTeamWorkersMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long supportTeamId = ParamUtil.getLong(
				renderRequest, "supportTeamId");

			if (supportTeamId > 0) {
				renderRequest.setAttribute(
					SupportWebKeys.SUPPORT_TEAM,
					_supportTeamLocalService.getSupportTeam(supportTeamId));
			}

			return "/ticket_support/select_support_team_workers.jsp";
		}
		catch (Exception e) {
			SessionErrors.add(renderRequest, e.getClass());

			return "/ticket_support/error.jsp";
		}
	}

	@Reference
	private SupportTeamLocalService _supportTeamLocalService;

}