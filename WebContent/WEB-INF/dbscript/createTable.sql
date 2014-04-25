create table OG_TestReport (
	id varchar(50) not null,
	project varchar(10),
	lotNo varchar(50),
	dateTime datetime,
	fairQualityCount int,
	faultCount int,
	firstSensorId varchar(20),
	reportFileName varchar(50)
)

create table OG_SensorReport (
	id varchar(50) not null,
	project varchar(10),
	testReportId varchar(50),
	sensorNo varchar(10),
	lotNo varchar(10),
	dateTime datetime,
	indoorTemperature varchar(10),
	glassTemperature varchar(10),
	indoorHumidity varchar(10),
	decisionCode varchar(10),
	serialNo varchar(20)
)

create table OG_CodeTable (
	code varchar(50) not null,
	description varchar(255)
)
insert into OG_CodeTable (code, description) values ('000','양품')
insert into OG_CodeTable (code, description) values ('001','유리 온도 불량')
insert into OG_CodeTable (code, description) values ('010','실내 온도 불량')
insert into OG_CodeTable (code, description) values ('100','실내 습도 불량')
insert into OG_CodeTable (code, description) values ('011','유리 온도, 실내 온도 불량')
insert into OG_CodeTable (code, description) values ('101','실내 습도, 유리 온도 불량')
insert into OG_CodeTable (code, description) values ('110','실내 습도, 실내 온도 불량')
insert into OG_CodeTable (code, description) values ('111','유리 온도, 실내 온도, 실내 습도 불량')
