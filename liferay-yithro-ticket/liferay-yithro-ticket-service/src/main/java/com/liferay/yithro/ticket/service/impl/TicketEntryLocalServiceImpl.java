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

package com.liferay.yithro.ticket.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.yithro.audit.constants.Actions;
import com.liferay.yithro.audit.constants.Fields;
import com.liferay.yithro.audit.service.AuditEntryLocalService;
import com.liferay.yithro.constants.Visibilities;
import com.liferay.yithro.ticket.constants.TicketAttachmentConstants;
import com.liferay.yithro.ticket.constants.TicketFlagType;
import com.liferay.yithro.ticket.constants.TicketFlagValue;
import com.liferay.yithro.ticket.exception.TicketEntryDescriptionException;
import com.liferay.yithro.ticket.exception.TicketEntrySubjectException;
import com.liferay.yithro.ticket.model.TicketAttachment;
import com.liferay.yithro.ticket.model.TicketComment;
import com.liferay.yithro.ticket.model.TicketEntry;
import com.liferay.yithro.ticket.model.TicketFlag;
import com.liferay.yithro.ticket.service.TicketAttachmentLocalService;
import com.liferay.yithro.ticket.service.TicketFieldDataLocalService;
import com.liferay.yithro.ticket.service.TicketFlagLocalService;
import com.liferay.yithro.ticket.service.base.TicketEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
public class TicketEntryLocalServiceImpl
	extends TicketEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public TicketEntry addTicketEntry(
			long userId, String languageId, String subject, String description,
			int status, int weight, Map<Long, String> ticketFieldsMap,
			List<TicketAttachment> ticketAttachments)
		throws PortalException {

		// Ticket entry

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(subject, description);

		long ticketEntryId = counterLocalService.increment();

		TicketEntry ticketEntry = ticketEntryPersistence.create(ticketEntryId);

		ticketEntry.setUserId(user.getUserId());
		ticketEntry.setUserName(user.getFullName());
		ticketEntry.setCreateDate(now);
		ticketEntry.setModifiedDate(now);
		ticketEntry.setLanguageId(languageId);
		ticketEntry.setSubject(subject);
		ticketEntry.setDescription(description);
		ticketEntry.setStatus(status);
		ticketEntry.setWeight(weight);

		ticketEntryPersistence.update(ticketEntry);

		// Ticket information

		ticketFieldDataLocalService.addTicketFieldData(
			ticketEntryId, ticketFieldsMap);

		// Ticket attachments

		for (TicketAttachment ticketAttachment : ticketAttachments) {
			if (ticketAttachment.getTicketAttachmentId() > 0) {
				ticketAttachmentLocalService.updateTicketAttachment(
					ticketAttachment.getTicketAttachmentId(),
					ticketEntry.getTicketEntryId());

				continue;
			}

			ticketAttachmentLocalService.addTicketAttachment(
				userId, ticketEntry.getTicketEntryId(),
				TicketAttachmentConstants.TICKET_SOLUTION_DEFAULT_ID,
				ticketAttachment.getFileName(), ticketAttachment.getFile(),
				Visibilities.PUBLIC, WorkflowConstants.STATUS_APPROVED,
				new ServiceContext());
		}

		return ticketEntry;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public TicketEntry deleteTicketEntry(TicketEntry ticketEntry)
		throws PortalException {

		// Ticket entry

		ticketEntryPersistence.remove(ticketEntry);

		// Ticket attachments

		List<TicketAttachment> ticketAttachments =
			ticketAttachmentPersistence.findByTicketEntryId(
				ticketEntry.getTicketEntryId());

		for (TicketAttachment ticketAttachment : ticketAttachments) {
			ticketAttachmentLocalService.deleteTicketAttachment(
				ticketAttachment);
		}

		// Ticket comments

		ticketCommentPersistence.removeByTicketEntryId(
			ticketEntry.getTicketEntryId());

		// Ticket field data

		ticketFieldDataPersistence.removeByTicketEntryId(
			ticketEntry.getTicketEntryId());

		// Ticket links

		ticketLinkPersistence.removeByTicketEntryId(
			ticketEntry.getTicketEntryId());

		// Ticket workers

		ticketWorkerPersistence.removeByTicketEntryId(
			ticketEntry.getTicketEntryId());

		return ticketEntry;
	}

	public List<TicketEntry> getTicketEntries(
		Date modifiedDate, int start, int end) {

		return ticketEntryPersistence.findByGtModifiedDate(
			modifiedDate, start, end);
	}

	public int getTicketEntriesCount(Date modifiedDate) {
		return ticketEntryPersistence.countByGtModifiedDate(modifiedDate);
	}

	@Indexable(type = IndexableType.REINDEX)
	public TicketEntry reindexTicketEntry(long ticketEntryId)
		throws PortalException {

		return ticketEntryPersistence.findByPrimaryKey(ticketEntryId);
	}

	public void sendEmail(
			long userId, TicketEntry ticketEntry, TicketComment ticketComment,
			String action)
		throws PortalException {
	}

	public void updatePendingTypes(
			long userId, long ticketEntryId, int[] pendingTypes)
		throws PortalException {

		TicketEntry ticketEntry = ticketEntryPersistence.findByPrimaryKey(
			ticketEntryId);

		if (ticketEntry.isClosed()) {
			return;
		}

		updatePendingTypes(userId, ticketEntry, pendingTypes);
	}

	public TicketEntry updateTicketEntry(
			long userId, long ticketEntryId, long reportedByUserId,
			String languageId, String subject, String description, int status,
			int weight, Date dueDate, Map<Long, String> ticketFieldsMap,
			ServiceContext serviceContext)
		throws PortalException {

		// Ticket entry

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(subject, description);

		TicketEntry ticketEntry = ticketEntryPersistence.findByPrimaryKey(
			ticketEntryId);

		TicketEntry oldTicketEntry = (TicketEntry)ticketEntry.clone();

		if (ticketEntry.getUserId() != reportedByUserId) {
			User reportedByUser = userLocalService.getUser(reportedByUserId);

			ticketEntry.setUserId(reportedByUserId);
			ticketEntry.setUserName(reportedByUser.getFullName());
		}

		ticketEntry.setModifiedDate(now);
		ticketEntry.setLanguageId(languageId);
		ticketEntry.setSubject(subject);
		ticketEntry.setDescription(description);
		ticketEntry.setStatus(status);
		ticketEntry.setWeight(weight);

		if (ticketEntry.isClosed() && (ticketEntry.getClosedDate() == null)) {
			ticketEntry.setClosedDate(new Date());
		}

		if (dueDate != null) {
			ticketEntry.setDueDate(dueDate);
		}

		ticketEntryPersistence.update(ticketEntry);

		long auditSetId = auditEntryLocalService.getNextAuditSetId(
			TicketEntry.class.getName(), ticketEntryId);

		serviceContext.setAttribute("auditSetId", auditSetId);

		serviceContext.setCreateDate(now);

		// Ticket information

		ticketFieldDataLocalService.updateTicketFieldData(
			userId, ticketEntryId, ticketFieldsMap, serviceContext);

		// Audit entry

		updateAuditEntry(
			user.getUserId(), now, auditSetId, oldTicketEntry, ticketEntry);

		return ticketEntry;
	}

	protected void updateAuditEntry(
			long userId, Date createDate, long auditSetId,
			TicketEntry oldTicketEntry, TicketEntry ticketEntry)
		throws PortalException {

		long ticketEntryId = ticketEntry.getTicketEntryId();

		if (oldTicketEntry.getUserId() != ticketEntry.getUserId()) {
			auditEntryLocalService.addAuditEntry(
				userId, createDate, TicketEntry.class, ticketEntryId,
				auditSetId, TicketEntry.class, ticketEntryId, Actions.UPDATE,
				Fields.REPORTED_BY, Visibilities.PUBLIC,
				oldTicketEntry.getUserName(), oldTicketEntry.getUserId(),
				ticketEntry.getUserName(), ticketEntry.getUserId(),
				StringPool.BLANK, false);
		}

		String oldSubject = oldTicketEntry.getSubject();

		if (!oldSubject.equals(ticketEntry.getSubject())) {
			auditEntryLocalService.addAuditEntry(
				userId, createDate, TicketEntry.class, ticketEntryId,
				auditSetId, TicketEntry.class, ticketEntryId, Actions.UPDATE,
				Fields.SUBJECT, Visibilities.PUBLIC, StringPool.BLANK,
				oldSubject, StringPool.BLANK, ticketEntry.getSubject(),
				StringPool.BLANK, false);
		}

		String oldDescription = oldTicketEntry.getDescription();

		if (!oldDescription.equals(ticketEntry.getDescription())) {
			auditEntryLocalService.addAuditEntry(
				userId, createDate, TicketEntry.class, ticketEntryId,
				auditSetId, TicketEntry.class, ticketEntryId, Actions.UPDATE,
				Fields.DESCRIPTION, Visibilities.PUBLIC, StringPool.BLANK,
				oldDescription, StringPool.BLANK, ticketEntry.getDescription(),
				StringPool.BLANK, false);
		}

		if (oldTicketEntry.getStatus() != ticketEntry.getStatus()) {
			auditEntryLocalService.addAuditEntry(
				userId, createDate, TicketEntry.class, ticketEntryId,
				auditSetId, TicketEntry.class, ticketEntryId, Actions.UPDATE,
				Fields.STATUS, Visibilities.PUBLIC,
				oldTicketEntry.getStatusLabel(), oldTicketEntry.getStatus(),
				ticketEntry.getStatusLabel(), ticketEntry.getStatus(),
				StringPool.BLANK, false);
		}

		if (oldTicketEntry.getWeight() != ticketEntry.getWeight()) {
			auditEntryLocalService.addAuditEntry(
				userId, createDate, TicketEntry.class, ticketEntryId,
				auditSetId, TicketEntry.class, ticketEntryId, Actions.UPDATE,
				Fields.WEIGHT, Visibilities.ADMIN, StringPool.BLANK,
				oldTicketEntry.getWeight(), StringPool.BLANK,
				ticketEntry.getWeight(), StringPool.BLANK, false);
		}

		Date oldDueDate = oldTicketEntry.getDueDate();
		Date dueDate = ticketEntry.getDueDate();

		if (DateUtil.compareTo(oldDueDate, dueDate) != 0) {
			auditEntryLocalService.addAuditEntry(
				userId, createDate, TicketEntry.class, ticketEntryId,
				auditSetId, TicketEntry.class, ticketEntryId, Actions.UPDATE,
				Fields.DUE_DATE, Visibilities.WORKER, oldDueDate.toString(),
				oldDueDate.getTime(), dueDate.toString(), dueDate.getTime(),
				StringPool.BLANK, false);
		}
	}

	protected void updatePendingTypes(
			long userId, TicketEntry ticketEntry, int[] pendingTypes)
		throws PortalException {

		if (pendingTypes == null) {
			return;
		}

		Date now = new Date();

		long auditSetId = auditEntryLocalService.getNextAuditSetId(
			TicketEntry.class.getName(), ticketEntry.getTicketEntryId());

		Set<Integer> previousPendingTypes = new HashSet<>();

		List<TicketFlag> ticketFlags = ticketFlagPersistence.findByTEI_T_V(
			ticketEntry.getTicketEntryId(), TicketFlagType.PENDING_ALL,
			TicketFlagValue.PENDING_TRUE);

		for (TicketFlag ticketFlag : ticketFlags) {
			previousPendingTypes.add(ticketFlag.getType());

			if (!ArrayUtil.contains(pendingTypes, ticketFlag.getType())) {
				ticketFlagPersistence.remove(ticketFlag.getTicketFlagId());

				auditEntryLocalService.addAuditEntry(
					userId, now, TicketEntry.class,
					ticketEntry.getTicketEntryId(), auditSetId,
					TicketFlag.class, ticketFlag.getTicketFlagId(),
					Actions.DELETE, ticketFlag.getTypeLabel(),
					Visibilities.PUBLIC, ticketFlag.getTypeLabel(),
					ticketFlag.getType(), StringPool.BLANK, StringPool.BLANK,
					StringPool.BLANK, true);
			}
		}

		for (int pendingType : pendingTypes) {
			if (!previousPendingTypes.contains(pendingType)) {
				TicketFlag ticketFlag = ticketFlagLocalService.updateTicketFlag(
					userId, ticketEntry.getTicketEntryId(), pendingType,
					TicketFlagValue.PENDING_TRUE);

				auditEntryLocalService.addAuditEntry(
					userId, now, TicketEntry.class,
					ticketEntry.getTicketEntryId(), auditSetId,
					TicketFlag.class, ticketFlag.getTicketFlagId(), Actions.ADD,
					ticketFlag.getTypeLabel(), Visibilities.PUBLIC,
					StringPool.BLANK, StringPool.BLANK,
					ticketFlag.getTypeLabel(), ticketFlag.getType(),
					StringPool.BLANK, false);
			}
		}

		ticketEntry.setModifiedDate(now);

		ticketEntryPersistence.update(ticketEntry);

		reindexTicketEntry(ticketEntry.getTicketEntryId());
	}

	protected void validate(String subject, String description)
		throws PortalException {

		if (Validator.isNull(subject)) {
			throw new TicketEntrySubjectException();
		}

		if (Validator.isNull(description)) {
			throw new TicketEntryDescriptionException();
		}
	}

	@Reference
	protected AuditEntryLocalService auditEntryLocalService;

	@Reference
	protected TicketAttachmentLocalService ticketAttachmentLocalService;

	@Reference
	protected TicketFieldDataLocalService ticketFieldDataLocalService;

	@Reference
	protected TicketFlagLocalService ticketFlagLocalService;

}