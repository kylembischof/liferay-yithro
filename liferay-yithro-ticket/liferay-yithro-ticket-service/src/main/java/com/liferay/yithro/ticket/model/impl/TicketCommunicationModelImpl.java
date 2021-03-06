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

package com.liferay.yithro.ticket.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.yithro.ticket.model.TicketCommunication;
import com.liferay.yithro.ticket.model.TicketCommunicationModel;
import com.liferay.yithro.ticket.model.TicketCommunicationSoap;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the TicketCommunication service. Represents a row in the &quot;Yithro_TicketCommunication&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>TicketCommunicationModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link TicketCommunicationImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TicketCommunicationImpl
 * @generated
 */
@JSON(strict = true)
public class TicketCommunicationModelImpl
	extends BaseModelImpl<TicketCommunication>
	implements TicketCommunicationModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ticket communication model instance should use the <code>TicketCommunication</code> interface instead.
	 */
	public static final String TABLE_NAME = "Yithro_TicketCommunication";

	public static final Object[][] TABLE_COLUMNS = {
		{"ticketCommunicationId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"ticketEntryId", Types.BIGINT},
		{"channel", Types.VARCHAR}, {"data_", Types.VARCHAR},
		{"visibility", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("ticketCommunicationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("ticketEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("channel", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("data_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("visibility", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Yithro_TicketCommunication (ticketCommunicationId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,ticketEntryId LONG,channel VARCHAR(75) null,data_ VARCHAR(75) null,visibility INTEGER)";

	public static final String TABLE_SQL_DROP =
		"drop table Yithro_TicketCommunication";

	public static final String ORDER_BY_JPQL =
		" ORDER BY ticketCommunication.createDate ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Yithro_TicketCommunication.createDate ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long TICKETENTRYID_COLUMN_BITMASK = 1L;

	public static final long VISIBILITY_COLUMN_BITMASK = 2L;

	public static final long CREATEDATE_COLUMN_BITMASK = 4L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static TicketCommunication toModel(
		TicketCommunicationSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		TicketCommunication model = new TicketCommunicationImpl();

		model.setTicketCommunicationId(soapModel.getTicketCommunicationId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setTicketEntryId(soapModel.getTicketEntryId());
		model.setChannel(soapModel.getChannel());
		model.setData(soapModel.getData());
		model.setVisibility(soapModel.getVisibility());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<TicketCommunication> toModels(
		TicketCommunicationSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<TicketCommunication> models = new ArrayList<TicketCommunication>(
			soapModels.length);

		for (TicketCommunicationSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public TicketCommunicationModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _ticketCommunicationId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setTicketCommunicationId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ticketCommunicationId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return TicketCommunication.class;
	}

	@Override
	public String getModelClassName() {
		return TicketCommunication.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<TicketCommunication, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<TicketCommunication, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<TicketCommunication, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((TicketCommunication)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<TicketCommunication, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<TicketCommunication, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(TicketCommunication)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<TicketCommunication, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<TicketCommunication, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, TicketCommunication>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			TicketCommunication.class.getClassLoader(),
			TicketCommunication.class, ModelWrapper.class);

		try {
			Constructor<TicketCommunication> constructor =
				(Constructor<TicketCommunication>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map<String, Function<TicketCommunication, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<TicketCommunication, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<TicketCommunication, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<TicketCommunication, Object>>();
		Map<String, BiConsumer<TicketCommunication, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<TicketCommunication, ?>>();

		attributeGetterFunctions.put(
			"ticketCommunicationId",
			TicketCommunication::getTicketCommunicationId);
		attributeSetterBiConsumers.put(
			"ticketCommunicationId",
			(BiConsumer<TicketCommunication, Long>)
				TicketCommunication::setTicketCommunicationId);
		attributeGetterFunctions.put(
			"companyId", TicketCommunication::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<TicketCommunication, Long>)
				TicketCommunication::setCompanyId);
		attributeGetterFunctions.put("userId", TicketCommunication::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<TicketCommunication, Long>)
				TicketCommunication::setUserId);
		attributeGetterFunctions.put(
			"createDate", TicketCommunication::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<TicketCommunication, Date>)
				TicketCommunication::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", TicketCommunication::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<TicketCommunication, Date>)
				TicketCommunication::setModifiedDate);
		attributeGetterFunctions.put(
			"ticketEntryId", TicketCommunication::getTicketEntryId);
		attributeSetterBiConsumers.put(
			"ticketEntryId",
			(BiConsumer<TicketCommunication, Long>)
				TicketCommunication::setTicketEntryId);
		attributeGetterFunctions.put(
			"channel", TicketCommunication::getChannel);
		attributeSetterBiConsumers.put(
			"channel",
			(BiConsumer<TicketCommunication, String>)
				TicketCommunication::setChannel);
		attributeGetterFunctions.put("data", TicketCommunication::getData);
		attributeSetterBiConsumers.put(
			"data",
			(BiConsumer<TicketCommunication, String>)
				TicketCommunication::setData);
		attributeGetterFunctions.put(
			"visibility", TicketCommunication::getVisibility);
		attributeSetterBiConsumers.put(
			"visibility",
			(BiConsumer<TicketCommunication, Integer>)
				TicketCommunication::setVisibility);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getTicketCommunicationId() {
		return _ticketCommunicationId;
	}

	@Override
	public void setTicketCommunicationId(long ticketCommunicationId) {
		_ticketCommunicationId = ticketCommunicationId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getTicketEntryId() {
		return _ticketEntryId;
	}

	@Override
	public void setTicketEntryId(long ticketEntryId) {
		_columnBitmask |= TICKETENTRYID_COLUMN_BITMASK;

		if (!_setOriginalTicketEntryId) {
			_setOriginalTicketEntryId = true;

			_originalTicketEntryId = _ticketEntryId;
		}

		_ticketEntryId = ticketEntryId;
	}

	public long getOriginalTicketEntryId() {
		return _originalTicketEntryId;
	}

	@JSON
	@Override
	public String getChannel() {
		if (_channel == null) {
			return "";
		}
		else {
			return _channel;
		}
	}

	@Override
	public void setChannel(String channel) {
		_channel = channel;
	}

	@JSON
	@Override
	public String getData() {
		if (_data == null) {
			return "";
		}
		else {
			return _data;
		}
	}

	@Override
	public void setData(String data) {
		_data = data;
	}

	@JSON
	@Override
	public int getVisibility() {
		return _visibility;
	}

	@Override
	public void setVisibility(int visibility) {
		_columnBitmask |= VISIBILITY_COLUMN_BITMASK;

		if (!_setOriginalVisibility) {
			_setOriginalVisibility = true;

			_originalVisibility = _visibility;
		}

		_visibility = visibility;
	}

	public int getOriginalVisibility() {
		return _originalVisibility;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), TicketCommunication.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public TicketCommunication toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, TicketCommunication>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		TicketCommunicationImpl ticketCommunicationImpl =
			new TicketCommunicationImpl();

		ticketCommunicationImpl.setTicketCommunicationId(
			getTicketCommunicationId());
		ticketCommunicationImpl.setCompanyId(getCompanyId());
		ticketCommunicationImpl.setUserId(getUserId());
		ticketCommunicationImpl.setCreateDate(getCreateDate());
		ticketCommunicationImpl.setModifiedDate(getModifiedDate());
		ticketCommunicationImpl.setTicketEntryId(getTicketEntryId());
		ticketCommunicationImpl.setChannel(getChannel());
		ticketCommunicationImpl.setData(getData());
		ticketCommunicationImpl.setVisibility(getVisibility());

		ticketCommunicationImpl.resetOriginalValues();

		return ticketCommunicationImpl;
	}

	@Override
	public int compareTo(TicketCommunication ticketCommunication) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), ticketCommunication.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TicketCommunication)) {
			return false;
		}

		TicketCommunication ticketCommunication = (TicketCommunication)obj;

		long primaryKey = ticketCommunication.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		TicketCommunicationModelImpl ticketCommunicationModelImpl = this;

		ticketCommunicationModelImpl._setModifiedDate = false;

		ticketCommunicationModelImpl._originalTicketEntryId =
			ticketCommunicationModelImpl._ticketEntryId;

		ticketCommunicationModelImpl._setOriginalTicketEntryId = false;

		ticketCommunicationModelImpl._originalVisibility =
			ticketCommunicationModelImpl._visibility;

		ticketCommunicationModelImpl._setOriginalVisibility = false;

		ticketCommunicationModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<TicketCommunication> toCacheModel() {
		TicketCommunicationCacheModel ticketCommunicationCacheModel =
			new TicketCommunicationCacheModel();

		ticketCommunicationCacheModel.ticketCommunicationId =
			getTicketCommunicationId();

		ticketCommunicationCacheModel.companyId = getCompanyId();

		ticketCommunicationCacheModel.userId = getUserId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			ticketCommunicationCacheModel.createDate = createDate.getTime();
		}
		else {
			ticketCommunicationCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ticketCommunicationCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			ticketCommunicationCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ticketCommunicationCacheModel.ticketEntryId = getTicketEntryId();

		ticketCommunicationCacheModel.channel = getChannel();

		String channel = ticketCommunicationCacheModel.channel;

		if ((channel != null) && (channel.length() == 0)) {
			ticketCommunicationCacheModel.channel = null;
		}

		ticketCommunicationCacheModel.data = getData();

		String data = ticketCommunicationCacheModel.data;

		if ((data != null) && (data.length() == 0)) {
			ticketCommunicationCacheModel.data = null;
		}

		ticketCommunicationCacheModel.visibility = getVisibility();

		return ticketCommunicationCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<TicketCommunication, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<TicketCommunication, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<TicketCommunication, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((TicketCommunication)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<TicketCommunication, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<TicketCommunication, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<TicketCommunication, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((TicketCommunication)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, TicketCommunication>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _ticketCommunicationId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _ticketEntryId;
	private long _originalTicketEntryId;
	private boolean _setOriginalTicketEntryId;
	private String _channel;
	private String _data;
	private int _visibility;
	private int _originalVisibility;
	private boolean _setOriginalVisibility;
	private long _columnBitmask;
	private TicketCommunication _escapedModel;

}