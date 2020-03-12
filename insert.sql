INSERT INTO Employee(Name, BirthYear, OriginCountry)
VALUES ("Tom Hanks", "1959", "USA");

INSERT INTO Company(URL, Country, Address)
VALUES("HTTPS://MYCOMPANY", "USA", "1600 Pennsylvanie Ave.");

INSERT INTO Media(Title, Length, PublicationYear, LaunchDate, Description, CompanyID)
VALUES ("Forrest Gump", 123, 1990, "1990-08-09", "Fin film", 1);

INSERT INTO EmployeeActs(EmployeeID, MediaID, Role)
VALUES(1, 1, "Forrest");