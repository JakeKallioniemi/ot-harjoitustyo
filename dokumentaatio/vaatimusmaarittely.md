# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on Arkanoid-tyylinen peli, jonka tasot generoidaan satunnaisesti. Pelin tavoitteena on osua pallolla ruudulla oleviin palikoihin, jolloin ne tuhoutuvat. Pelaaja ohjaa mailaa, jonka avulla hän pitää pallon pelissä. 
Kun kaikki palikat on tuhottu, pelaaja siirtyy seuraavalle tasolle. Jos pallo osuu ruudun alalaitaan, pelaaja menettää elämän. Peli päättyy, kun elämät loppuvat.

## Perusversion tarjoama toiminnallisuus

- yllä kuvatun mukainen peli
	- maila, jota pelaaja ohjaa vasemmalle ja oikealle \[**tehty**\]
	- pallo, joka kimpoilee mailasta, seinistä ja palikoista \[**tehty**\]
	- palikat, jotka tuhoutuvat, kun pallo osuu niihin \[**tehty**\]

- satunnaisesti generoitavat tasot

- pisteet
	- eriväriset palikat antavat erimäärän pisteitä
	- näytetään kokoajan yläkulmassa

- highscore-systeemi
	- tallennetaan ja luetaan tiedostosta

- valikko, josta pääsee pelaamaan tai tarkastelemaan highscoreja (tai poistumaan pelistä). \[**tehty (osittain)**\]

## Jatkokehitysideoita

Lisätoiminnallisuudet suurinpiirtein todennäköisimmästä epätodennäköisimpään

- powerup-systeemi
	- enemmän palloja
	- lisäelämiä
	- suurempi maila
	- pallo, joka menee palikoiden läpi
	- jne.

- erilaisia palikoita
	- tuhoutumattomia
	- liikkuvia
	
- monimutkaisempi tasojen generointi
	- erikoistasoja esim. "boss fight"

- 2 pelaajaa

- mahdollisuus luoda tasoja
