# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne:

brickbreaker.ui -> brickbreaker.domain -> brickbreaker.dao

Pakkaus _brickbreaker.ui_ sisältää JavaFX:llä toteutetun käyttöliittymän _brickbreaker.domain_ sovelluslogiikan ja _brickbreaker.dao_ tietojen tallentamisen.

## Sovelluslogiikka

Luokka Game pitää sisällään pelin tämänhetkisen tilanteet sekä metodeja tilan päivittämiseen. Törmäysten tarkistus on sovelluksessa tärkeässä asemassa (esim. pallo osuu mailaan tai pallo osuu tiileen). Varsinainen tarkistus delegoidaan eteenpäin, mutta törmäysten seuraukset käsitellään myös enimmäkseen luokassa Game. Suurin osa käyttöliittymän kommunikoinnista sovelluslogiikan kanssa tapahtuu Game luokan kautta.

Luokat Ball, Brick, Paddle ja Powerup kuvaavat pelissä näkyvia oliotia. Kaikki nämä perivät luokan Entity, jossa on määritelty kahden Entityn välisen törmäyksen tarkistus. Törmäyksen tarkistus on toteutettu käyttämällä JavaFX:än oliota Shape, mikä yksinkertaistaa tarkistusta huomattavasti. Shape oliota käytetään myös osana käyttöliittymää.

HighScoreService on ainoa luokka, jolla on yhteys dao-pakkaukseen. Sen avulla käyttöliittymäluokat voivat hakea pisteitä ja tallentaa niitä. Varsinainen tallennus ja haku tehdään injektoidun HighScoreDao-rajapinnan toteuttavan luokan avulla.

PowerupService luokka luo poweruppeja ja pitää kirjaa tällä hetkellä aktiivisista powerupeista. Varsinaiset poweruppien peliin tuovat muutokset käsitellään Game luokassa.

LevelGenerator luo satunnaisuutta hyödyntäen (tällä hetkellä tämä on vielä toteuttamatta) uusia tasoja tai tarkemmin sanottuna eri palikoiden konfiguraatioita. Muu tason luontiin liittyvä työ tapahtuu Game luokassa.

## Luokkarakenne

**HUOM!** kuvasta puuttuu osa luokista

<img src="https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkarakenne.png" width="700">

## Toiminnallisuus

**HUOM!** kuva vastaa sovelluksen viikon 5 releasea.

Tarkastetaan pallon osumat palikoihin tilanteessa, jossa pallo on osunut pelin ainoaan palikkaan vasemmalta.

<img src="https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssi.png" width="700">
