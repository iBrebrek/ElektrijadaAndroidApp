CREATE TABLE Competitor 
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	Name NVARCHAR (255) NOT NULL,
	Surname	NVARCHAR (255) NULL,
	Section NVARCHAR (255) NULL,
	IsPerson BOOLEAN NOT NULL,
	GroupCompetitorId INTEGER NULL,
	FacultyId INTEGER NOT NULL,
	CompetitionId INTEGER NULL,
	OrdinalNum INTEGER NULL,
	IsDisqualified BOOLEAN NOT NULL,
	FOREIGN KEY(CompetitionId) REFERENCES Competition(Id),
	FOREIGN KEY(GroupCompetitorId) REFERENCES Competitor(Id),
	FOREIGN KEY(FacultyId) REFERENCES Faculty(Id)
	
	);

CREATE TABLE Duel
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	TimeFrom DATETIME NOT NULL,
	TimeTo DATETIME	NULL,
	CategoryId INTEGER NOT NULL,
	Competitor1Id INTEGER NOT NULL,
	Competitor2Id INTEGER NOT NULL,
	StageId INTEGER NULL,
	Location NVARCHAR (255) NOT NULL,
	IsAssumption BOOLEAN NOT NULL,
	FOREIGN KEY(Competitor1Id) REFERENCES Competitor(Id),
	FOREIGN KEY(Competitor2Id) REFERENCES Competitor(Id),
	FOREIGN KEY(StageId) REFERENCES Stage(Id),
	FOREIGN KEY(CategoryId) REFERENCES Category(Id)
	);

CREATE TABLE CompetitionScore
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	Result DECIMAL (9,4) NOT NULL,
	Note NVARCHAR (255) NULL,
	CompetitionId INTEGER NOT NULL,
	UserId INTEGER NOT NULL,
	CompetitorId INTEGER NOT NULL,
	IsAssumption BOOLEAN NOT NULL,
	IsOfficial BOOLEAN NOT NULL,
	FOREIGN KEY(CompetitionId) REFERENCES Competition(Id),
	FOREIGN KEY(CompetitorId) REFERENCES Competitor(Id),
	FOREIGN KEY(UserId) REFERENCES User(Id)
	);

CREATE TABLE Competition
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	TimeFrom DATETIME NOT NULL,
	TimeTo DATETIME	NULL,
	CategoryId INTEGER NOT NULL,
	Location NVARCHAR (255) NOT NULL,
	IsAssumption BOOLEAN NOT NULL,
	FOREIGN KEY(CategoryId) REFERENCES Category(Id)
	);

CREATE TABLE Category
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	Name NVARCHAR (255) NOT NULL,
	Nick NVARCHAR (255) NULL,
	IsSport BOOLEAN	NOT NULL
	);

CREATE TABLE DuelScore
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	Score1 DECIMAL (9,4) NOT NULL,
	Score2 DECIMAL (9,4) NOT NULL,
	DuelId INTEGER NOT NULL,
	UserId INTEGER NOT NULL,
	IsAssumption BOOLEAN NOT NULL,
	Note NVARCHAR (255) NULL,
	IsOfficial BOOLEAN NOT NULL,
	FOREIGN KEY(DuelId) REFERENCES Duel(Id),
	FOREIGN KEY(UserId) REFERENCES User(Id)
	);

CREATE TABLE Faculty
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	Name NVARCHAR (255) NOT NULL
	);

CREATE TABLE News
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	Time DATETIME NOT NULL,
	Description NVARCHAR (MAX) NOT NULL,
	Title NVARCHAR (255) NOT NULL,
	UserId INTEGER NOT NULL,
	FOREIGN KEY(UserId) REFERENCES User(Id)
	);

CREATE TABLE Stage
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	Name NVARCHAR (255) NOT NULL
	);

CREATE TABLE User
	(
	Id INTEGER PRIMARY KEY NOT NULL,
	Name NVARCHAR (255) NOT NULL,
	Surname NVARCHAR (255) NOT NULL,
	LoginType NVARCHAR (255) NOT NULL
	);
