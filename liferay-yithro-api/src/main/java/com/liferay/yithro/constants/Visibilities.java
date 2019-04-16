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

package com.liferay.yithro.constants;

import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Amos Fong
 */
public class Visibilities {

	public static final int ADMIN = 3;

	public static final int[] ALL = {
		ADMIN, Visibilities.PUBLIC, Visibilities.WORKER
	};

	public static final int PUBLIC = 1;

	public static final int WORKER = 2;

	public static boolean hasVisibility(int visibility) {
		if (ArrayUtil.contains(ALL, visibility)) {
			return true;
		}
		else {
			return false;
		}
	}

}