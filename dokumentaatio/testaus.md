# Testausdokumentti

Ohjelmalla on JUnit yksikkö- ja integraatiotestejä. Järjestelmätestejä on suoritettu manuaalisesti.

## Yksikkö- ja integraatiotestaus

Luokkia on testattu enimmäkseen yksikkötestein, mutta myös joitakin integraatiotestejä on mukana. Testiluokat on nimetty testattavan luokan mukaan. PaddleTest sisältää myös Entity-luokan testit, sillä abstraktia luokkaa ei voida suoraan testata. HighScoreService-luokan testeissä on hyödynnetty [MockHighScoreDao](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/test/java/brickbreaker/domain/mocks/MockHighScoreDao.java)-luokkaa, jotta testaus voidaan eristää. Satunnaisuuttaa käyttävien LevelGenerator ja PowerupService-luokkien testeissä käytetään [MockRandom](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/test/java/brickbreaker/domain/mocks/MockRandom.java)-luokkaa, jonka avulla luokille voidaan antaa haluttuja lukuja. Ohjelmassa käytetään laajasti riippuvuuksien injektointia, mikä mahdollistaa mock-luokkien käytön.

### Testauskattavuus

Käyttöliittymä ja alustukseen käytetty pääluokka BrickBreaker on jätetty pois raportista.
Rivikattavuus on 72% ja haarakattavuus 50%. Tiedostoon tallentamisesta vastaava luokka jäi kokonaan testaamatta.
Game-luokan testit eivät ole kovin kattavat, tämä johtuu osittain luokan hankalasta rakenteesta. Pelin logiikkaa on kuitenkin testattu manuaalisesti melko paljon.

<img src="https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/kattavuus.PNG" width="700">

## Järjestelmätestaus

Järjestelmätestaus on tehty manuaalisesti sekä Linux- että Windows 10-ympäristöissä. Satunnaisuus ja ohjelman eri tilojen määrä tekevät kattavasta testaamisesta haastavaa, mutta kaikki vaatimusmäärittelyssä määritellyt toiminnot on testattu useasti.

## Ongelmat/bugit

- Ohjelmassa on yksi checktyle-virhe, joka johtuu switch-lauseesta. Virheestä eroon pyrkiminen ei kuitenkaan tunnu järkevältä.
- Pallo ei aina pomppaa tiilistä odotetulla tavalla.
- Jotkut tekstit voivat osittain leikkautua pois joillakin koneilla.
- Ohjelma ei anna minkäänlaista virheilmoitusta, jos parhaiden pisteiden luku tai kirjoitus epäonnistuu, vaan jatkaa normaalia toimintaa ilman edellä mainittua toiminnallisuutta.
- Pallon kimpoaminen mailasta on toteutettu kehnosti: pallon aiempaa nopeutta ei oteta huomioon ja pallon suuntaa muutetaan ainoastaa x-suunnan nopeutta muuttamalla. Koska y-suunnan nopeus ei muutu samalla, pallon kokonaisnopeus muuttuu myös, eikä pelkästään suunta.
