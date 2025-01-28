CREATE TABLE `Utilisateur` (
  `Utilisateur ID` SERIAL PRIMARY KEY
);

CREATE TABLE `reservation` (
  `date de depart` DATE,
  `date de retour` DATE,
  `vehicule ID` INTEGER,
  `heure de depart` TIME,
  `heure de retour` TIME,
  `tarif` DECIMAL,
  `Utilisateur ID` INTEGER,
  `ville de depart` VARCHAR,
  `ville de retour` VARCHAR,
  `agence ID` INTEGER,
  `reservation ID` SERIAL PRIMARY KEY,
  FOREIGN KEY (`Utilisateur ID`) REFERENCES `Utilisateur`(`Utilisateur ID`)
);

CREATE TABLE `Agence` (
  `Nom` VARCHAR,
  `agence ID` SERIAL PRIMARY KEY,
  FOREIGN KEY (`agence ID`) REFERENCES `reservation`(`agence ID`)
);

CREATE TABLE `Informations` (
  `Nom` VARCHAR,
  `email` VARCHAR,
  `mot de passe` VARCHAR,
  `date de naissance` DATE,
  `adresse` TEXT,
  `Utilisateur ID` INTEGER PRIMARY KEY,
  FOREIGN KEY (`Utilisateur ID`) REFERENCES `Utilisateur`(`Utilisateur ID`)
);

CREATE TABLE `Service utilisateur` (
  `requetes` Type,
  `messages` Type,
  `suivi` Type,
  `Utilisateur ID` Type,
  FOREIGN KEY (`Utilisateur ID`) REFERENCES `Utilisateur`(`Utilisateur ID`)
);

CREATE TABLE `Vehicule` (
  `categorie` VARCHAR,
  `disponibilite` BOOLEAN,
  `tarif` DECIMAL,
  `vehicule ID` SERIAL PRIMARY KEY,
  FOREIGN KEY (`vehicule ID`) REFERENCES `reservation`(`vehicule ID`)
);

CREATE TABLE `Categorie` (
  `categorie` VARCHAR,
  `type` VARCHAR,
  `transmission` VARCHAR,
  `fuel` VARCHAR,
  `vehicule ID` INTEGER,
  FOREIGN KEY (`vehicule ID`) REFERENCES `Vehicule`(`vehicule ID`)
);
