create table TestReport (
	id varchar(50) not null,
	project varchar(10),
	lotNo varchar(50),
	dateTime datetime,
	fairQualityCount int,
	faultCount int,
	sensorSerialNo varchar(20),
	reportFileName varchar(50)
)

create table SensorReport (
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