# Käyttöohje

Aloita lataamalla [BrickBreaker.jar](https://github.com/JakeKallioniemi/ot-harjoitustyo/releases/tag/loppupalautus).

## Käynnistäminen

```
java -jar BrickBreaker.jar
```

Käynnistää ohjelman ja luo tarvittavan tiedoston.

## Sulkeminen

Ohjelman voi sulkea ruksista tai päävalikon kohdasta EXIT.

## Valikoissa liikkuminen

Valikoissa liikutaan nuolinäppäimillä.
Valinta tehdään painamalla enteriä.  

## Pelaaminen

Pelissä mailaa liikutetaan nuolinäppäimillä vasemmalle ja oikealla.  
Esc poistuu pelistä valikkoon.

## Pelin tavoite

Pelin tavoitteena on kerätä mahdollisimman paljon pisteitä pitämällä pallo pelissä mailaa liikuttamalla ja tuhoamalla ruudulla näkyvät tiilet.

## Pelin säännöt

Pelin alussa elämiä on kolme, kun viimeinen pallo osuu ruudun alalaitaan, menetät elämän. Peli loppuu kun elämät loppuvat.

Jos pelin lopussa pisteet yltävät kymmenen parhaan joukkoon voit antaa nimimerkin ja pisteesi tallennetaan.

### Tiilet

Tavallisia tiiliä on seitsemän:
- Valkoinen 50 p
- Oranssi 60 p
- Sinivihreä 70 p
- Vihreä 80 p
- Punainen 90 p
- Sininen 100 p
- Violetti 110 p

Pelissä on myös:
- Kultainen erikoistiili, joka vaatii kolme osumaa tuhoutuakseen. Se antaa 120 pistettä. 
- Harmaa erikoistiili, jota ei voi tuhota. Jos kuitenkin onnistut, antaa se 130 pistettä.

Kun tuhoat kaikki muut kuin harmaat tiilet, pääset seuraavalle tasolle.

### Powerupit

Välillä kun tuhoat tiilen, peliin ilmestyy powerup. Kun keräät ne mailalla, aktivoit niiden efektin.

Powerupit ovat:
- EXTRA LIFE (punainen), antaa yhden lisäelämän
- EXTRA BALLS (vaaleansininen), kaksi uutta palloa ilmestyy peliin
- WIDE (violetti), maila muuttuu leveämmäksi
- SUPER BALL (vaaleanpunainen), pallo muuttuu suuremmaksi ja menee kaikkien tiilien läpi, jopa harmaiden!

Jos menetät elämän tai siirryt seuraavalle tasolle powerupin efekti loppuu.
