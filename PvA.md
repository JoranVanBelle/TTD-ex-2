# Plan van Aanpak

## Componenten: 

### Producer

- Scanner om de file binnen te lezen 
- Adapter om de inhoud van de file om te zetten naar objecten, gegenereert met een plugin die een schema omvormt naar een object 
- Producer factory, 1 klasse om zo veel producers te maken als je maar wilt 
- Een klasse om de files te verzenden

- Een main klasse om alle losstaande componenten samen te hangen als volgt:
  - De scanner om de file binnen te lezen
  - Een adapter die alles lijn voor lijn omzet naar een object gegenereert vanuit een schema
  - Een producer creëren met behulp van de factory
  - Het record verzenden met behulp van het laatste component
  
### API

- Een klasse die de api raadpleegt (infra)
- Een klasse die bovenstaande klasse aanspreekt en een api key meegeeft
- Een klasse die de juiste data uit de response haalt
- Een klasse die de data in een schema giet

- Een main klasse die alle componenten samenhangt:
  - Er wordt ook een producer aangemaakt om de verkregen data van de api op kafka te zetten
  
### Stream

- Een klasse met meerdere static functions
  - Een function voor de topology
  - Enkele functions voor de custom Serdes die nodig zijn om data van en naar de schema's te krijgen
  - Een function om naar het nieuwe object/schema te builden (aan te maken)

### Testen - Unit

#### Producer - csv
- Testen of alles van de inhoud aanwezig is en of object kan aangemaakt worden
- Testen of de parameters van de inhoud kloppen en of object kan aangemaakt worden
- Testen of Producer kan gemaakt worden
- Testen of verzenden van data lukt naar kafka

#### Producer - api
- Testen of object kan gecreëerd worden bij gebrek aan parameters/foutieve parameters
- Testen of Producer kan gemaakt worden
- Testen of verzenden van data lukt naar Kafka
- Testen of data uit response kan gehaald worden dmv response te mocken

#### Stream
- Testen wanneer weer niet in kafka is opgeslagen
- Testen of alles correct kan gejoined worden - met data ge-insert in verschillende volgordes
- Testen of content van resultaat klopt met wat er verwacht wordt

### Testen - integration
- Met behulp van testcontainers en een mock van de api kijken of alles correct wordt verwerkt volgens de verwachtingen en alles aanwezig is in de cluster