-- Entities --
CREATE TABLE COMPANY
       ( Id				INT		NOT NULL,
       	 CName				VARCHAR(50) 	NOT NULL,
	 Website			VARCHAR(100),
	 Industry			VARCHAR(50),
	 NumberOfRatings		INT		NOT NULL DEFAULT 0,
	 Logo				VARCHAR(150),
	 OverallRating			FLOAT,
	 CultureAndValuesRating		FLOAT,
	 SeniorLeadershipRating		FLOAT,
	 CompensationAndBenefitsRating	FLOAT,
	 CareerOpportunitiesRating	FLOAT,
	 WorkLifeBalanceRating		FLOAT,
	 RecomendToFriend		FLOAT,
	 CEOAproval			INT,
	 PRIMARY KEY (Id),
	 UNIQUE (CName) );

CREATE TABLE OFFER
       ( Id		INT				NOT NULL,
       	 Title		VARCHAR(100)			NOT NULL,
	 Description	VARCHAR(1000)			NOT NULL,
	 Location	VARCHAR(50)			NOT NULL,
	 Url		VARCHAR(200)			NOT NULL,
	 PubDate	TIMESTAMP			NOT NULL,
	 Updated	TIMESTAMP,
	 CompanyId	INT				NOT NULL,
	 PRIMARY KEY (Id),
	 UNIQUE (Url),
	 FOREIGN KEY (CompanyId) REFERENCES COMPANY(Id)
	 ON DELETE RESTRICT ON UPDATE CASCADE );

CREATE TABLE SKILL
       ( Id		INT		NOT NULL,
       	 SName		VARCHAR(20)	NOT NULL,
	 PRIMARY KEY (Id)
	 UNIQUE (Sname) );


-- Relationships --

-- Company proposes an Offer
CREATE TABLE PROPOSES
       ( CompanyId	INT	NOT NULL,
       	 OfferId	INT	NOT NULL,
	 PRIMARY KEY (CompanyId, OfferId),
	 FOREIGN KEY (CompanyId) REFERENCES COMPANY(Id)
	 	 ON DELETE CASCADE ON UPDATE CASCADE,
	 FOREIGN KEY (OfferId) REFERENCES OFFER(Id)
	 	 ON DELETE CASCADE ON UPDATE CASCADE );

-- Offer demands a Skill
CREATE TABLE DEMANDS
       ( OfferId	INT	NOT NULL,
       	 SkillId	INT	NOT NULL,
	 PRIMARY KEY (OfferId, SkillId),
	 FOREIGN KEY (OfferId) REFERENCES OFFER(Id)
	 	 ON DELETE CASCADE ON UPDATE CASCADE,
	 FOREIGN KEY (SkillId) REFERENCES SKILL(Id)
	 	 ON DELETE CASCADE ON UPDATE CASCADE );
