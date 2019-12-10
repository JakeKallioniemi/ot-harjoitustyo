# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on Arkanoid-tyylinen peli, jonka tasot generoidaan satunnaisesti. Pelin tavoitteena on osua pallolla ruudulla oleviin palikoihin, jolloin ne tuhoutuvat. Pelaaja ohjaa mailaa, jonka avulla hän pitää pallon pelissä. 
Kun kaikki palikat on tuhottu, pelaaja siirtyy seuraavalle tasolle. Jos pallo osuu ruudun alalaitaan, pelaaja menettää elämän. Peli päättyy, kun elämät loppuvat.

## Perusversion tarjoama toiminnallisuus

- yllä kuvatun mukainen peli \[**tehty**\]
	- maila, jota pelaaja ohjaa vasemmalle ja oikealle 
	- pallo, joka kimpoilee mailasta, seinistä ja palikoista 
	- palikat, jotka tuhoutuvat, kun pallo osuu niihin 

- satunnaisesti generoitavat tasot

- pisteet \[**tehty**\]
	- eriväriset palikat antavat erimäärän pisteitä 
	- näytetään kokoajan yläkulmassa 

- highscore-systeemi \[**tehty**\]
	- tallennetaan ja luetaan tiedostosta 

- valikko, josta pääsee pelaamaan tai tarkastelemaan highscoreja (tai poistumaan pelistä). \[**tehty**\]

## Jatkokehitysideoita

Lisätoiminnallisuudet suurinpiirtein todennäköisimmästä epätodennäköisimpään

- powerup-systeemi \[**tehty (uusi!)**\]
	- enemmän palloja
	- lisäelämiä
	- suurempi maila
	- pallo, joka menee palikoiden läpi
	- jne.

- erilaisia palikoita \[**tehty (uusi!)**\] tuki toiminnolle on tehty, mutta tasojen luonti ei vielä käytä sitä
	- tuhoutumattomia
	- liikkuvia
	
- monimutkaisempi tasojen generointi
	- erikoistasoja esim. "boss fight"

- 2 pelaajaa

- mahdollisuus luoda tasoja
