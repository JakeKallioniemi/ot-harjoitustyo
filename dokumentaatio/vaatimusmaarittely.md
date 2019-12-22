# Vaatimusmäärittely

Sovellus on Arkanoid-tyylinen peli, jonka tasot generoidaan satunnaisesti. Pelin tavoitteena on osua pallolla ruudulla oleviin tiiliin, jolloin ne tuhoutuvat. Pelaaja ohjaa mailaa, jonka avulla hän pitää pallon pelissä. 
Kun kaikki tiilet on tuhottu, pelaaja siirtyy seuraavalle tasolle. Jos pallo osuu ruudun alalaitaan, pelaaja menettää elämän. Peli päättyy, kun elämät loppuvat. Parhaat pisteet tallennetaa.

## Tarkempi toiminnallisuus

- Sovellus avautuu valikkoon, josta pääsee pelaamaan, tarkastelemaan pisteitä tai lopettamaan pelin.
	- Play siirtyy peliin.
	- Scores-valikko näyttää parhaat 10 tulosta parhaimmasta huonoimpaa, sekä pelaajien antamat nimimerkit.
	- Exit sammuttaa ohjelman.
- Pelaaja pääsee pelistä takaisin valikkoon Esc-näppäimellä.
- Taso luodaan satunnaisesti aina uuden tason alussa.
- Pelin alussa pelaajalle kerrotaan taso ja kuinka peli aloitetaan.
	- Mailan liikkuvuutta ei ole estetty.
	- Pallo seuraa mailaa.
- Pelaaja pystyy liikuttamaan mailaa nuolinäppäimillä vasemmalle ja oikealla.
- Pelaajan painaessa enteriä pallo lähtee liikkeelle satunnaiseen suuntaan ja esittely teksti poistuu.
- Pelin oikeassa yläkulmassa näytetään kokoajan pelaajan pisteet ja elämät.
	- Pisteitä on alussa 0, elämiä 3.
- Pallon osuessa mailaan se kimpoaa.
	- Suunta riippuu osumakohdasta.
- Pallon osuessa peliruudun sivuille tai yläosaan se kimpoaa.
- Pallon osuessa tiileen se kimpoaa.
- Tiiliä on kolmenlaisia.
	- Tavallisia tiiliä, jotka tuhoutuvat yhdestä osumasta. Tiilet on lisäksi jaettu 7 eri tyyppiin, jotka näytetään eri värein.
	- Kultaisia tiiliä, jotka tuhoutuvat kolmesta osumasta.
	- Harmaita tiiliä, jotka eivät tuhoudu.
- Tiilen tuhoutuessa pelaaja saa pisteitä.
	- Määrä riippuu tiilen tyypistä.
- Tiilen tuhoutuessa sen tillalle saattaa ilmestyä powerup.
	- Arvotaan satunnaisesti.
- Poweruppeja on neljää tyyppiä.
	- Vaaleansininen, joka luo kaksi uutta palloa, jotka lähtevät mailasta koiliseen ja kaakkoon.
	- Violetti, joka kasvattaa mailan kokoa.
	- Vaaleanpunainen, joka muuttaa pallon suuremmaksi ja tekee siitä pysäyttämättömän (ei kimpoa tiilistä, tuhoaa harmaat ja kultaiset tiilet yhdellä osumalla).
	- Punainen, joka antaa yhden lisäelämän.
- Powerup putoaa suoraan alaspäin.
	- Jos powerup osuu mailaan, sen efekti aktivoidaan.
	- Jos powerup osuu ruudun alalaitaan, se katoaa.
- Powerup ei voi ilmestyä, jos samanlainen on jo ruudulla.
- Violetti ja vaaleanpunainen powerup eivät ilmesty, jos sellainen on jo aktiivisena.
- Kun kaikki tiilet, lukuunottamatta harmaita tiiliä, on tuhottu pelaaja siirtyy seuraavalle tasolle.
- Kun pallo osuu ruudun alalaitaan, pelaaja menettää elämän.
	- Kun elämiä on nolla peli loppuu.
- Pelin lopussa pelaajalle näytetään Game Over-ruutu, jossa kerrotaan saatu pistemäärä.
- Jos pisteet eivät riittäneet parhaan kymmenen joukkoon, minkä tahansa näppäimen painaminen siirtyy päävalikkoon.
- Jos pisteet riittävät kymmenen parhaan joukkoon, minkä tahansa näppäimen painaminen siirtyy uuteen näkymään, jossa siitä kerrotaan pelaajalle ja pelaaja voi antaa kolmikirjaimisen nimimerkin nuolinäppäimillä.
	- Nimimerkki ja pisteet tallennetaan tiedostoon.
		- Tiedosto luodaan sovelluksen suorituksen alussa tarvittaessa.
		- Tiedoston nimi on _scores.txt_
		- Aluksi tiedostossa on placeholder-arvoja.
	- Pelaajalle näytetään top 10-pisteet.
		- Pelaajan uusi tulos on korostettu.
		- Minkä tahansa näppäimen painaminen siirtyy päävalikkoon.
