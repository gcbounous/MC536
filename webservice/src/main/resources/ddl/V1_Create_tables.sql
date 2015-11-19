-- Entities

CREATE TABLE Skill (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    SName VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE Company (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    CName VARCHAR(50) UNIQUE NOT NULL,
    Website VARCHAR(100),
    Industry VARCHAR(50),
    NumberOfRatings INT NOT NULL DEFAULT 0,
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
    Title VARCHAR(100) NOT NULL,
    Description VARCHAR(1000) NOT NULL,
    Location VARCHAR(50) NOT NULL,
    Url VARCHAR(200) UNIQUE NOT NULL,
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
