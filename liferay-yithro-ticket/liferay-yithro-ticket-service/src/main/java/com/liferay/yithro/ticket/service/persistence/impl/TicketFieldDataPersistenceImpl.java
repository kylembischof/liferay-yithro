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

package com.liferay.yithro.ticket.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.yithro.ticket.exception.NoSuchTicketFieldDataException;
import com.liferay.yithro.ticket.model.TicketFieldData;
import com.liferay.yithro.ticket.model.impl.TicketFieldDataImpl;
import com.liferay.yithro.ticket.model.impl.TicketFieldDataModelImpl;
import com.liferay.yithro.ticket.service.persistence.TicketFieldDataPersistence;
import com.liferay.yithro.ticket.service.persistence.impl.constants.YithroPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ticket field data service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = TicketFieldDataPersistence.class)
public class TicketFieldDataPersistenceImpl
	extends BasePersistenceImpl<TicketFieldData>
	implements TicketFieldDataPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TicketFieldDataUtil</code> to access the ticket field data persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TicketFieldDataImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByTicketEntryId;
	private FinderPath _finderPathWithoutPaginationFindByTicketEntryId;
	private FinderPath _finderPathCountByTicketEntryId;

	/**
	 * Returns all the ticket field datas where ticketEntryId = &#63;.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @return the matching ticket field datas
	 */
	@Override
	public List<TicketFieldData> findByTicketEntryId(long ticketEntryId) {
		return findByTicketEntryId(
			ticketEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ticket field datas where ticketEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TicketFieldDataModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param start the lower bound of the range of ticket field datas
	 * @param end the upper bound of the range of ticket field datas (not inclusive)
	 * @return the range of matching ticket field datas
	 */
	@Override
	public List<TicketFieldData> findByTicketEntryId(
		long ticketEntryId, int start, int end) {

		return findByTicketEntryId(ticketEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ticket field datas where ticketEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TicketFieldDataModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param start the lower bound of the range of ticket field datas
	 * @param end the upper bound of the range of ticket field datas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ticket field datas
	 */
	@Override
	public List<TicketFieldData> findByTicketEntryId(
		long ticketEntryId, int start, int end,
		OrderByComparator<TicketFieldData> orderByComparator) {

		return findByTicketEntryId(
			ticketEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ticket field datas where ticketEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TicketFieldDataModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param start the lower bound of the range of ticket field datas
	 * @param end the upper bound of the range of ticket field datas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ticket field datas
	 */
	@Override
	public List<TicketFieldData> findByTicketEntryId(
		long ticketEntryId, int start, int end,
		OrderByComparator<TicketFieldData> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByTicketEntryId;
				finderArgs = new Object[] {ticketEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTicketEntryId;
			finderArgs = new Object[] {
				ticketEntryId, start, end, orderByComparator
			};
		}

		List<TicketFieldData> list = null;

		if (useFinderCache) {
			list = (List<TicketFieldData>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TicketFieldData ticketFieldData : list) {
					if ((ticketEntryId != ticketFieldData.getTicketEntryId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_TICKETFIELDDATA_WHERE);

			query.append(_FINDER_COLUMN_TICKETENTRYID_TICKETENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(TicketFieldDataModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ticketEntryId);

				if (!pagination) {
					list = (List<TicketFieldData>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<TicketFieldData>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ticket field data in the ordered set where ticketEntryId = &#63;.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket field data
	 * @throws NoSuchTicketFieldDataException if a matching ticket field data could not be found
	 */
	@Override
	public TicketFieldData findByTicketEntryId_First(
			long ticketEntryId,
			OrderByComparator<TicketFieldData> orderByComparator)
		throws NoSuchTicketFieldDataException {

		TicketFieldData ticketFieldData = fetchByTicketEntryId_First(
			ticketEntryId, orderByComparator);

		if (ticketFieldData != null) {
			return ticketFieldData;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ticketEntryId=");
		msg.append(ticketEntryId);

		msg.append("}");

		throw new NoSuchTicketFieldDataException(msg.toString());
	}

	/**
	 * Returns the first ticket field data in the ordered set where ticketEntryId = &#63;.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket field data, or <code>null</code> if a matching ticket field data could not be found
	 */
	@Override
	public TicketFieldData fetchByTicketEntryId_First(
		long ticketEntryId,
		OrderByComparator<TicketFieldData> orderByComparator) {

		List<TicketFieldData> list = findByTicketEntryId(
			ticketEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ticket field data in the ordered set where ticketEntryId = &#63;.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket field data
	 * @throws NoSuchTicketFieldDataException if a matching ticket field data could not be found
	 */
	@Override
	public TicketFieldData findByTicketEntryId_Last(
			long ticketEntryId,
			OrderByComparator<TicketFieldData> orderByComparator)
		throws NoSuchTicketFieldDataException {

		TicketFieldData ticketFieldData = fetchByTicketEntryId_Last(
			ticketEntryId, orderByComparator);

		if (ticketFieldData != null) {
			return ticketFieldData;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ticketEntryId=");
		msg.append(ticketEntryId);

		msg.append("}");

		throw new NoSuchTicketFieldDataException(msg.toString());
	}

	/**
	 * Returns the last ticket field data in the ordered set where ticketEntryId = &#63;.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket field data, or <code>null</code> if a matching ticket field data could not be found
	 */
	@Override
	public TicketFieldData fetchByTicketEntryId_Last(
		long ticketEntryId,
		OrderByComparator<TicketFieldData> orderByComparator) {

		int count = countByTicketEntryId(ticketEntryId);

		if (count == 0) {
			return null;
		}

		List<TicketFieldData> list = findByTicketEntryId(
			ticketEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ticket field datas before and after the current ticket field data in the ordered set where ticketEntryId = &#63;.
	 *
	 * @param ticketFieldDataId the primary key of the current ticket field data
	 * @param ticketEntryId the ticket entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ticket field data
	 * @throws NoSuchTicketFieldDataException if a ticket field data with the primary key could not be found
	 */
	@Override
	public TicketFieldData[] findByTicketEntryId_PrevAndNext(
			long ticketFieldDataId, long ticketEntryId,
			OrderByComparator<TicketFieldData> orderByComparator)
		throws NoSuchTicketFieldDataException {

		TicketFieldData ticketFieldData = findByPrimaryKey(ticketFieldDataId);

		Session session = null;

		try {
			session = openSession();

			TicketFieldData[] array = new TicketFieldDataImpl[3];

			array[0] = getByTicketEntryId_PrevAndNext(
				session, ticketFieldData, ticketEntryId, orderByComparator,
				true);

			array[1] = ticketFieldData;

			array[2] = getByTicketEntryId_PrevAndNext(
				session, ticketFieldData, ticketEntryId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected TicketFieldData getByTicketEntryId_PrevAndNext(
		Session session, TicketFieldData ticketFieldData, long ticketEntryId,
		OrderByComparator<TicketFieldData> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TICKETFIELDDATA_WHERE);

		query.append(_FINDER_COLUMN_TICKETENTRYID_TICKETENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(TicketFieldDataModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ticketEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ticketFieldData)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<TicketFieldData> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ticket field datas where ticketEntryId = &#63; from the database.
	 *
	 * @param ticketEntryId the ticket entry ID
	 */
	@Override
	public void removeByTicketEntryId(long ticketEntryId) {
		for (TicketFieldData ticketFieldData :
				findByTicketEntryId(
					ticketEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ticketFieldData);
		}
	}

	/**
	 * Returns the number of ticket field datas where ticketEntryId = &#63;.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @return the number of matching ticket field datas
	 */
	@Override
	public int countByTicketEntryId(long ticketEntryId) {
		FinderPath finderPath = _finderPathCountByTicketEntryId;

		Object[] finderArgs = new Object[] {ticketEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TICKETFIELDDATA_WHERE);

			query.append(_FINDER_COLUMN_TICKETENTRYID_TICKETENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ticketEntryId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_TICKETENTRYID_TICKETENTRYID_2 =
		"ticketFieldData.ticketEntryId = ?";

	private FinderPath _finderPathFetchByTEI_TFI;
	private FinderPath _finderPathCountByTEI_TFI;

	/**
	 * Returns the ticket field data where ticketEntryId = &#63; and ticketFieldId = &#63; or throws a <code>NoSuchTicketFieldDataException</code> if it could not be found.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param ticketFieldId the ticket field ID
	 * @return the matching ticket field data
	 * @throws NoSuchTicketFieldDataException if a matching ticket field data could not be found
	 */
	@Override
	public TicketFieldData findByTEI_TFI(long ticketEntryId, long ticketFieldId)
		throws NoSuchTicketFieldDataException {

		TicketFieldData ticketFieldData = fetchByTEI_TFI(
			ticketEntryId, ticketFieldId);

		if (ticketFieldData == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ticketEntryId=");
			msg.append(ticketEntryId);

			msg.append(", ticketFieldId=");
			msg.append(ticketFieldId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchTicketFieldDataException(msg.toString());
		}

		return ticketFieldData;
	}

	/**
	 * Returns the ticket field data where ticketEntryId = &#63; and ticketFieldId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param ticketFieldId the ticket field ID
	 * @return the matching ticket field data, or <code>null</code> if a matching ticket field data could not be found
	 */
	@Override
	public TicketFieldData fetchByTEI_TFI(
		long ticketEntryId, long ticketFieldId) {

		return fetchByTEI_TFI(ticketEntryId, ticketFieldId, true);
	}

	/**
	 * Returns the ticket field data where ticketEntryId = &#63; and ticketFieldId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param ticketFieldId the ticket field ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ticket field data, or <code>null</code> if a matching ticket field data could not be found
	 */
	@Override
	public TicketFieldData fetchByTEI_TFI(
		long ticketEntryId, long ticketFieldId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {ticketEntryId, ticketFieldId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByTEI_TFI, finderArgs, this);
		}

		if (result instanceof TicketFieldData) {
			TicketFieldData ticketFieldData = (TicketFieldData)result;

			if ((ticketEntryId != ticketFieldData.getTicketEntryId()) ||
				(ticketFieldId != ticketFieldData.getTicketFieldId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_TICKETFIELDDATA_WHERE);

			query.append(_FINDER_COLUMN_TEI_TFI_TICKETENTRYID_2);

			query.append(_FINDER_COLUMN_TEI_TFI_TICKETFIELDID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ticketEntryId);

				qPos.add(ticketFieldId);

				List<TicketFieldData> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByTEI_TFI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									ticketEntryId, ticketFieldId
								};
							}

							_log.warn(
								"TicketFieldDataPersistenceImpl.fetchByTEI_TFI(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					TicketFieldData ticketFieldData = list.get(0);

					result = ticketFieldData;

					cacheResult(ticketFieldData);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByTEI_TFI, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (TicketFieldData)result;
		}
	}

	/**
	 * Removes the ticket field data where ticketEntryId = &#63; and ticketFieldId = &#63; from the database.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param ticketFieldId the ticket field ID
	 * @return the ticket field data that was removed
	 */
	@Override
	public TicketFieldData removeByTEI_TFI(
			long ticketEntryId, long ticketFieldId)
		throws NoSuchTicketFieldDataException {

		TicketFieldData ticketFieldData = findByTEI_TFI(
			ticketEntryId, ticketFieldId);

		return remove(ticketFieldData);
	}

	/**
	 * Returns the number of ticket field datas where ticketEntryId = &#63; and ticketFieldId = &#63;.
	 *
	 * @param ticketEntryId the ticket entry ID
	 * @param ticketFieldId the ticket field ID
	 * @return the number of matching ticket field datas
	 */
	@Override
	public int countByTEI_TFI(long ticketEntryId, long ticketFieldId) {
		FinderPath finderPath = _finderPathCountByTEI_TFI;

		Object[] finderArgs = new Object[] {ticketEntryId, ticketFieldId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_TICKETFIELDDATA_WHERE);

			query.append(_FINDER_COLUMN_TEI_TFI_TICKETENTRYID_2);

			query.append(_FINDER_COLUMN_TEI_TFI_TICKETFIELDID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ticketEntryId);

				qPos.add(ticketFieldId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_TEI_TFI_TICKETENTRYID_2 =
		"ticketFieldData.ticketEntryId = ? AND ";

	private static final String _FINDER_COLUMN_TEI_TFI_TICKETFIELDID_2 =
		"ticketFieldData.ticketFieldId = ?";

	public TicketFieldDataPersistenceImpl() {
		setModelClass(TicketFieldData.class);

		setModelImplClass(TicketFieldDataImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("data", "data_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the ticket field data in the entity cache if it is enabled.
	 *
	 * @param ticketFieldData the ticket field data
	 */
	@Override
	public void cacheResult(TicketFieldData ticketFieldData) {
		entityCache.putResult(
			entityCacheEnabled, TicketFieldDataImpl.class,
			ticketFieldData.getPrimaryKey(), ticketFieldData);

		finderCache.putResult(
			_finderPathFetchByTEI_TFI,
			new Object[] {
				ticketFieldData.getTicketEntryId(),
				ticketFieldData.getTicketFieldId()
			},
			ticketFieldData);

		ticketFieldData.resetOriginalValues();
	}

	/**
	 * Caches the ticket field datas in the entity cache if it is enabled.
	 *
	 * @param ticketFieldDatas the ticket field datas
	 */
	@Override
	public void cacheResult(List<TicketFieldData> ticketFieldDatas) {
		for (TicketFieldData ticketFieldData : ticketFieldDatas) {
			if (entityCache.getResult(
					entityCacheEnabled, TicketFieldDataImpl.class,
					ticketFieldData.getPrimaryKey()) == null) {

				cacheResult(ticketFieldData);
			}
			else {
				ticketFieldData.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ticket field datas.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TicketFieldDataImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ticket field data.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TicketFieldData ticketFieldData) {
		entityCache.removeResult(
			entityCacheEnabled, TicketFieldDataImpl.class,
			ticketFieldData.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(TicketFieldDataModelImpl)ticketFieldData, true);
	}

	@Override
	public void clearCache(List<TicketFieldData> ticketFieldDatas) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TicketFieldData ticketFieldData : ticketFieldDatas) {
			entityCache.removeResult(
				entityCacheEnabled, TicketFieldDataImpl.class,
				ticketFieldData.getPrimaryKey());

			clearUniqueFindersCache(
				(TicketFieldDataModelImpl)ticketFieldData, true);
		}
	}

	protected void cacheUniqueFindersCache(
		TicketFieldDataModelImpl ticketFieldDataModelImpl) {

		Object[] args = new Object[] {
			ticketFieldDataModelImpl.getTicketEntryId(),
			ticketFieldDataModelImpl.getTicketFieldId()
		};

		finderCache.putResult(
			_finderPathCountByTEI_TFI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByTEI_TFI, args, ticketFieldDataModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TicketFieldDataModelImpl ticketFieldDataModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ticketFieldDataModelImpl.getTicketEntryId(),
				ticketFieldDataModelImpl.getTicketFieldId()
			};

			finderCache.removeResult(_finderPathCountByTEI_TFI, args);
			finderCache.removeResult(_finderPathFetchByTEI_TFI, args);
		}

		if ((ticketFieldDataModelImpl.getColumnBitmask() &
			 _finderPathFetchByTEI_TFI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ticketFieldDataModelImpl.getOriginalTicketEntryId(),
				ticketFieldDataModelImpl.getOriginalTicketFieldId()
			};

			finderCache.removeResult(_finderPathCountByTEI_TFI, args);
			finderCache.removeResult(_finderPathFetchByTEI_TFI, args);
		}
	}

	/**
	 * Creates a new ticket field data with the primary key. Does not add the ticket field data to the database.
	 *
	 * @param ticketFieldDataId the primary key for the new ticket field data
	 * @return the new ticket field data
	 */
	@Override
	public TicketFieldData create(long ticketFieldDataId) {
		TicketFieldData ticketFieldData = new TicketFieldDataImpl();

		ticketFieldData.setNew(true);
		ticketFieldData.setPrimaryKey(ticketFieldDataId);

		ticketFieldData.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ticketFieldData;
	}

	/**
	 * Removes the ticket field data with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ticketFieldDataId the primary key of the ticket field data
	 * @return the ticket field data that was removed
	 * @throws NoSuchTicketFieldDataException if a ticket field data with the primary key could not be found
	 */
	@Override
	public TicketFieldData remove(long ticketFieldDataId)
		throws NoSuchTicketFieldDataException {

		return remove((Serializable)ticketFieldDataId);
	}

	/**
	 * Removes the ticket field data with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ticket field data
	 * @return the ticket field data that was removed
	 * @throws NoSuchTicketFieldDataException if a ticket field data with the primary key could not be found
	 */
	@Override
	public TicketFieldData remove(Serializable primaryKey)
		throws NoSuchTicketFieldDataException {

		Session session = null;

		try {
			session = openSession();

			TicketFieldData ticketFieldData = (TicketFieldData)session.get(
				TicketFieldDataImpl.class, primaryKey);

			if (ticketFieldData == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTicketFieldDataException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ticketFieldData);
		}
		catch (NoSuchTicketFieldDataException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected TicketFieldData removeImpl(TicketFieldData ticketFieldData) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ticketFieldData)) {
				ticketFieldData = (TicketFieldData)session.get(
					TicketFieldDataImpl.class,
					ticketFieldData.getPrimaryKeyObj());
			}

			if (ticketFieldData != null) {
				session.delete(ticketFieldData);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ticketFieldData != null) {
			clearCache(ticketFieldData);
		}

		return ticketFieldData;
	}

	@Override
	public TicketFieldData updateImpl(TicketFieldData ticketFieldData) {
		boolean isNew = ticketFieldData.isNew();

		if (!(ticketFieldData instanceof TicketFieldDataModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ticketFieldData.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ticketFieldData);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ticketFieldData proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TicketFieldData implementation " +
					ticketFieldData.getClass());
		}

		TicketFieldDataModelImpl ticketFieldDataModelImpl =
			(TicketFieldDataModelImpl)ticketFieldData;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ticketFieldData.getCreateDate() == null)) {
			if (serviceContext == null) {
				ticketFieldData.setCreateDate(now);
			}
			else {
				ticketFieldData.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!ticketFieldDataModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ticketFieldData.setModifiedDate(now);
			}
			else {
				ticketFieldData.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ticketFieldData.isNew()) {
				session.save(ticketFieldData);

				ticketFieldData.setNew(false);
			}
			else {
				ticketFieldData = (TicketFieldData)session.merge(
					ticketFieldData);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				ticketFieldDataModelImpl.getTicketEntryId()
			};

			finderCache.removeResult(_finderPathCountByTicketEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByTicketEntryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ticketFieldDataModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByTicketEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ticketFieldDataModelImpl.getOriginalTicketEntryId()
				};

				finderCache.removeResult(_finderPathCountByTicketEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTicketEntryId, args);

				args = new Object[] {
					ticketFieldDataModelImpl.getTicketEntryId()
				};

				finderCache.removeResult(_finderPathCountByTicketEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByTicketEntryId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, TicketFieldDataImpl.class,
			ticketFieldData.getPrimaryKey(), ticketFieldData, false);

		clearUniqueFindersCache(ticketFieldDataModelImpl, false);
		cacheUniqueFindersCache(ticketFieldDataModelImpl);

		ticketFieldData.resetOriginalValues();

		return ticketFieldData;
	}

	/**
	 * Returns the ticket field data with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ticket field data
	 * @return the ticket field data
	 * @throws NoSuchTicketFieldDataException if a ticket field data with the primary key could not be found
	 */
	@Override
	public TicketFieldData findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTicketFieldDataException {

		TicketFieldData ticketFieldData = fetchByPrimaryKey(primaryKey);

		if (ticketFieldData == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTicketFieldDataException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ticketFieldData;
	}

	/**
	 * Returns the ticket field data with the primary key or throws a <code>NoSuchTicketFieldDataException</code> if it could not be found.
	 *
	 * @param ticketFieldDataId the primary key of the ticket field data
	 * @return the ticket field data
	 * @throws NoSuchTicketFieldDataException if a ticket field data with the primary key could not be found
	 */
	@Override
	public TicketFieldData findByPrimaryKey(long ticketFieldDataId)
		throws NoSuchTicketFieldDataException {

		return findByPrimaryKey((Serializable)ticketFieldDataId);
	}

	/**
	 * Returns the ticket field data with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ticketFieldDataId the primary key of the ticket field data
	 * @return the ticket field data, or <code>null</code> if a ticket field data with the primary key could not be found
	 */
	@Override
	public TicketFieldData fetchByPrimaryKey(long ticketFieldDataId) {
		return fetchByPrimaryKey((Serializable)ticketFieldDataId);
	}

	/**
	 * Returns all the ticket field datas.
	 *
	 * @return the ticket field datas
	 */
	@Override
	public List<TicketFieldData> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ticket field datas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TicketFieldDataModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ticket field datas
	 * @param end the upper bound of the range of ticket field datas (not inclusive)
	 * @return the range of ticket field datas
	 */
	@Override
	public List<TicketFieldData> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ticket field datas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TicketFieldDataModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ticket field datas
	 * @param end the upper bound of the range of ticket field datas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ticket field datas
	 */
	@Override
	public List<TicketFieldData> findAll(
		int start, int end,
		OrderByComparator<TicketFieldData> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ticket field datas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TicketFieldDataModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ticket field datas
	 * @param end the upper bound of the range of ticket field datas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ticket field datas
	 */
	@Override
	public List<TicketFieldData> findAll(
		int start, int end,
		OrderByComparator<TicketFieldData> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<TicketFieldData> list = null;

		if (useFinderCache) {
			list = (List<TicketFieldData>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_TICKETFIELDDATA);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_TICKETFIELDDATA;

				if (pagination) {
					sql = sql.concat(TicketFieldDataModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<TicketFieldData>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<TicketFieldData>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ticket field datas from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TicketFieldData ticketFieldData : findAll()) {
			remove(ticketFieldData);
		}
	}

	/**
	 * Returns the number of ticket field datas.
	 *
	 * @return the number of ticket field datas
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_TICKETFIELDDATA);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ticketFieldDataId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_TICKETFIELDDATA;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TicketFieldDataModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ticket field data persistence.
	 */
	@Activate
	public void activate() {
		TicketFieldDataModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		TicketFieldDataModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TicketFieldDataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TicketFieldDataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByTicketEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TicketFieldDataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTicketEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByTicketEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TicketFieldDataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTicketEntryId",
			new String[] {Long.class.getName()},
			TicketFieldDataModelImpl.TICKETENTRYID_COLUMN_BITMASK);

		_finderPathCountByTicketEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTicketEntryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByTEI_TFI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, TicketFieldDataImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByTEI_TFI",
			new String[] {Long.class.getName(), Long.class.getName()},
			TicketFieldDataModelImpl.TICKETENTRYID_COLUMN_BITMASK |
			TicketFieldDataModelImpl.TICKETFIELDID_COLUMN_BITMASK);

		_finderPathCountByTEI_TFI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTEI_TFI",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(TicketFieldDataImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = YithroPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.yithro.ticket.model.TicketFieldData"),
			true);
	}

	@Override
	@Reference(
		target = YithroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = YithroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_TICKETFIELDDATA =
		"SELECT ticketFieldData FROM TicketFieldData ticketFieldData";

	private static final String _SQL_SELECT_TICKETFIELDDATA_WHERE =
		"SELECT ticketFieldData FROM TicketFieldData ticketFieldData WHERE ";

	private static final String _SQL_COUNT_TICKETFIELDDATA =
		"SELECT COUNT(ticketFieldData) FROM TicketFieldData ticketFieldData";

	private static final String _SQL_COUNT_TICKETFIELDDATA_WHERE =
		"SELECT COUNT(ticketFieldData) FROM TicketFieldData ticketFieldData WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ticketFieldData.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TicketFieldData exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TicketFieldData exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TicketFieldDataPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"data"});

	static {
		try {
			Class.forName(YithroPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}