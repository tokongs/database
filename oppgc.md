# Oppgave C



## i) Oversikt over klasser og oppgaver de løser  

- ActiveDomainObject

- Actor Henter data fra actor entiteen
- DBConnection Sørger for database tilkobling
- Employee Henter data fra employee entiteen
- Genre Henter data fra genre entiteen
- Main Entrypoint
- Media Henter data fra media entiteen
- MenuController Styrer alt av menyer og logikk
- Review Henter data fra review entiteen

klasse som holder kontroll på alle verdier til et review. Har også muligheten til å inserte et nytt review ved hjelp av insert funksjonen

## ii) usecase realsiert
Vi har løst usecasene som beskrevet i oppage 1 med en CLI. Man navigerer seg gjennom denne med nummere som vises i menyen. MenuController holder styr på det meste og bruker de andre klassene for å hente ut data. 