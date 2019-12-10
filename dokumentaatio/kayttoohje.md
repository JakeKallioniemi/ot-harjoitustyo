# Käyttöohje

## Ohjelman käynnistäminen

Ohjelma voidaan käynnistää esim. komennolla

```
mvn compile exec:java -Dexec.mainClass=brickbreaker.BrickBreaker
```

## Käyttäminen

Valikoissa liikutaan nuolinäppäimillä.
Valinta tehdään painamalla enteriä.  

Pelissä mailaa liikutetaan nuolinäppäimillä vasemmalle ja oikealla.  
Esc poistuu pelistä valikkoon.

## Pelaaminen

Pelin tavoitteena on pitää pallo ruudun sisällä mailaa liikuttamalla ja samalla rikkoa ruudun ylälaidassa olevia tiilejä.
Tiilien rikkominen antaa pisteitä, joiden määrä riippuu tiilen väristä. Kun tiili hajoaa saattaa sen tilalle ilmestyä powerup,
joka putoaa alaspäin. Powerupin kerääminen aktivoi efektin, joka riippuu powerupin tyypistä. Kun kaikki tiilet on rikottu, siirrytään seuraavalle tasolle. Jos ruudulla ei ole yhtään palloa, vähenee elämien määrä yhdellä. Kun elämien määrä on 0, peli loppuu.

Kun peli on loppunut näkyy lopullinen pistesaldo. Jos pisteet yltävät parhaan 10 joukkoon, voit antaa nimimerkin. Tämän jälkeen pisteesi näkyvät listalla.
