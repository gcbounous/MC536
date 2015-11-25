-- Entidades
CREATE TABLE Company (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    CName VARCHAR(100) CHARACTER SET utf8 UNIQUE NOT NULL,
    Website VARCHAR(100),
    Industry VARCHAR(50),
    NumberOfRatings INT NOT NULL DEFAULT 0,
    Logo VARCHAR(150),
    OverallRating FLOAT NOT NULL DEFAULT 0,
    CultureAndValuesRating FLOAT NOT NULL DEFAULT 0,
    SeniorLeadershipRating FLOAT NOT NULL DEFAULT 0,
    CompensationAndBenefitsRating FLOAT NOT NULL DEFAULT 0,
    CareerOpportunitiesRating FLOAT NOT NULL DEFAULT 0,
    WorkLifeBalanceRating FLOAT NOT NULL DEFAULT 0,
    RecomendToFriend FLOAT NOT NULL DEFAULT 0,
    CEOAproval INT
);

CREATE TABLE Offer (
    Id INT PRIMARY KEY,
    Title VARCHAR(200) NOT NULL,
    Description TEXT CHARACTER SET utf8 NOT NULL,
    Location VARCHAR(50) CHARACTER SET utf8 NOT NULL,
    Url VARCHAR(500) UNIQUE NOT NULL,
    PubDate TIMESTAMP NOT NULL,
    Updated TIMESTAMP,
    CompanyId INT NOT NULL,
    FOREIGN KEY (CompanyId) REFERENCES Company(Id)
);

CREATE TABLE Skill (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    SName VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE `User` (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL
);

-- Offer demands a Skill
CREATE TABLE Demands (
    OfferId INT NOT NULL,
    SkillId INT NOT NULL,
    PRIMARY KEY (OfferId, SkillId),
    FOREIGN KEY (OfferId) REFERENCES Offer(Id),
    FOREIGN KEY (SkillId) REFERENCES Skill(Id)
);

-- Relação entre usuários e skills
CREATE TABLE User_Skill (
    UserId INT NOT NULL,
    SkillId INT NOT NULL,
    PRIMARY KEY (UserId, SkillId),
    FOREIGN KEY (UserId) REFERENCES `User`(Id),
    FOREIGN KEY (SkillId) REFERENCES Skill(Id)
);
