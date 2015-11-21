-- Entities

CREATE TABLE Skill (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    SName VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE Company (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    CName VARCHAR(100) CHARACTER SET utf8 UNIQUE NOT NULL,
    Website VARCHAR(100),
    Industry VARCHAR(50),
    NumberOfRatings INT,
    Logo VARCHAR(150),
    OverallRating FLOAT,
    CultureAndValuesRating FLOAT,
    SeniorLeadershipRating FLOAT,
    CompensationAndBenefitsRating FLOAT,
    CareerOpportunitiesRating FLOAT,
    WorkLifeBalanceRating FLOAT,
    RecomendToFriend FLOAT,
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
    CompanyId INT NOT NULL
);

-- Offer demands a Skill
CREATE TABLE Demands (
    OfferId INT NOT NULL,
    SkillId INT NOT NULL,
    PRIMARY KEY (OfferId, SkillId),
    FOREIGN KEY (OfferId) REFERENCES Offer(Id),
    FOREIGN KEY (SkillId) REFERENCES Skill(Id)
);
