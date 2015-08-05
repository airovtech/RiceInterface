create table OG_TLE_TestReport (
	id varchar(50) not null,
	project varchar(10),
	lotNo varchar(50),
	dateTime datetime,
	fairQualityCount int,
	faultCount int,
	firstSensorId varchar(20),
	reportFileName varchar(50)
)

create table OG_TLE_SensorReport (
	date varchar(30),
	dateTime datetime,
	customerName varchar(10),
	productName varchar(20),
	modelName varchar(10),
	tester varchar(30),
	qrCode varchar(30),
	dataMatrix varchar(20),
	lotNo varchar(50),
	humidity varchar(10),
	decisionCode varchar(10),
	id varchar(50),
	idx int,
	testReportId varchar(50),
)

create table OG_TLE_FtpHistory(
	id varchar(50),
	folderName varchar(50),
	fileName varchar(50),
	fileSerial varchar(20),
	createDate datetime,
	lastId varchar(50),
)

create table OG_TLE_CodeTable (
	code varchar(50) not null,
	description varchar(255)
)
insert into OG_TLE_CodeTable (code, description) values ('000','양품')
insert into OG_TLE_CodeTable (code, description) values ('001','실내 습도 불량')
