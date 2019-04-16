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

package com.liferay.yithro.ticket.constants;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Amos Fong
 */
public class TicketFlagType {

	public static final int[] PENDING_ALL = {
		TicketFlagType.PENDING_CUSTOMER, TicketFlagType.PENDING_WORKER
	};

	public static final int PENDING_CUSTOMER = 1;

	public static final int PENDING_WORKER = 2;

	public static String getLabel(int type) {
		if (type == PENDING_CUSTOMER) {
			return "customer";
		}
		else if (type == PENDING_WORKER) {
			return "worker";
		}
		else {
			return StringPool.BLANK;
		}
	}

}