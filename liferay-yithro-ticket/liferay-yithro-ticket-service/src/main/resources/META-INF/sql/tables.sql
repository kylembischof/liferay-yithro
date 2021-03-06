create table Yithro_TicketAttachment (
	ticketAttachmentId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	ticketEntryId LONG,
	ticketFieldId LONG,
	ticketCommunicationId LONG,
	fileName VARCHAR(255) null,
	fileSize LONG,
	visibility INTEGER,
	status INTEGER
);

create table Yithro_TicketComment (
	ticketCommentId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ticketEntryId LONG,
	ticketCommunicationId LONG,
	body TEXT null,
	type_ INTEGER,
	format VARCHAR(75) null,
	visibility INTEGER,
	settings_ VARCHAR(75) null,
	status INTEGER
);

create table Yithro_TicketCommentTemplate (
	ticketCommentTemplateId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	content STRING null
);

create table Yithro_TicketCommunication (
	ticketCommunicationId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	ticketEntryId LONG,
	channel VARCHAR(75) null,
	data_ VARCHAR(75) null,
	visibility INTEGER
);

create table Yithro_TicketEntry (
	ticketEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ticketStructureId LONG,
	ticketStatusId LONG,
	languageId VARCHAR(75) null,
	ticketNumber LONG,
	summary VARCHAR(255) null,
	description STRING null,
	weight INTEGER,
	holdDate DATE null,
	closedDate DATE null,
	dueDate DATE null
);

create table Yithro_TicketField (
	ticketFieldId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	type_ INTEGER,
	visibility INTEGER,
	systemKey VARCHAR(75) null,
	status INTEGER
);

create table Yithro_TicketFieldData (
	ticketFieldDataId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	ticketEntryId LONG,
	ticketFieldId LONG,
	data_ STRING null
);

create table Yithro_TicketFieldOption (
	ticketFieldOptionId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	ticketFieldId LONG,
	name STRING null,
	visibility INTEGER,
	order_ INTEGER,
	status INTEGER
);

create table Yithro_TicketFlag (
	ticketFlagId LONG not null primary key,
	companyId LONG,
	userId LONG,
	modifiedDate DATE null,
	ticketEntryId LONG,
	type_ INTEGER,
	value INTEGER
);

create table Yithro_TicketLink (
	ticketLinkId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	ticketEntryId LONG,
	ticketCommunicationId LONG,
	url STRING null,
	type_ INTEGER,
	visibility INTEGER
);

create table Yithro_TicketSolution (
	ticketSolutionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	ticketEntryId LONG,
	solution STRING null,
	type_ INTEGER,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	statusMessage TEXT null,
	statusReason INTEGER
);

create table Yithro_TicketStatus (
	ticketStatusId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	order_ INTEGER,
	terminal BOOLEAN,
	status INTEGER
);

create table Yithro_TicketStructure (
	ticketStructureId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description TEXT null,
	structure TEXT null
);

create table Yithro_TicketWorker (
	ticketWorkerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	ticketEntryId LONG,
	sourceClassNameId LONG,
	sourceClassPK LONG,
	role_ INTEGER,
	primary_ BOOLEAN
);