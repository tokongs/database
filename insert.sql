INSERT INTO Employee(Name, BirthYear, OriginCountry)
VALUES ("Tom Hanks", "1959", "USA");

INSERT INTO Company(Name, URL, Country, Address)
VALUES("20th Centry Fox", "HTTPS://MYCOMPANY", "USA", "1600 Pennsylvanie Ave.");

INSERT INTO Company(Name, URL, Country, Address)
VALUES("Disney", "HTTPS://MYCOMPANY", "USA", "1600 Pennsylvanie Ave.");

INSERT INTO Media(Title, Length, PublicationYear, LaunchDate, Description, CompanyID)
VALUES ("Forrest Gump", 123, 1990, "1990-08-09", "Fin film", 1);

INSERT INTO Media(Title, Length, PublicationYear, LaunchDate, Description, CompanyID)
VALUES ("Inferno", 123, 1990, "1990-08-09", "Fin film", 2);

INSERT INTO EmployeeActs(EmployeeID, MediaID, Role)
VALUES(1, 1, "Forrest");

INSERT INTO EmployeeActs(EmployeeID, MediaID, Role)
VALUES(1, 2, "Robert Langdon");

INSERT INTO Genre(Name)
VALUES("Drama");

INSERT INTO MediaIsGenre(MediaID, GenreID)
VALUES(1, 1);

INSERT INTO Genre(Name)
VALUES("Action");

INSERT INTO MediaIsGenre(MediaID, GenreID)
VALUES(2, 2);