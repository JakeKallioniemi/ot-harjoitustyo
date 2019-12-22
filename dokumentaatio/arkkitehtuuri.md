# Arkkitehtuurikuvaus

## Pakkausrakenne

Ohjelmassa on kolme pakkausta:
- _brickbreaker.ui_, joka sisältää käyttöliittymän
- _brickbreaker.domain_, joka sisältää sovelluslogiikan
- _brickbreaker.dao_, joka sisältää dao-luokat

_brickbreaker.domain_ pakkauksen laajuuden takia osa luokista on jaettu vielä kahteen alipakkaukseen.  

Ohjelma noudattaa kerrosarkkitehtuuria.  
Pakkausten väliset riippuvuudet:

brickbreaker.ui -> brickbreaker.domain -> brickbreaker.dao

## Käyttöliittymä

Käyttöliittymän näkymät ovat:
- Päävalikko ([MenuView](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/ui/MenuView.java))
- Varsinainen peli ([GameView](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/ui/GameView.java))
- Pisteiden tarkastelu ([ScoresView](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/ui/ScoresView.java))
- Game Over-ruutu ([GameOverView](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/ui/GameOverView.java))
- Uuden pistetuloksen lisääminen ([EnterScoreView](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/ui/EnterScoreView.java))

Kaikki näkymät toteuttavat rajapinnan [View](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/ui/View.java) ja siirtyminen niiden välillä tapahtuu luokan [ViewManager](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/ui/ViewManager.java) avulla. ViewManager mahdollistaa myös argumenttien välityksen näkymien välillä.

Käyttöliittymä on toteutettu [JavaFX](https://docs.oracle.com/javase/8/javafx/api/)-kirjastolla. Jokainen näkymä on erillinen [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html)-olio, jotka ViewManager aina asettaa [Stage](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html)-oliolle. Näkymät on rakennettu käyttäen JavaFX:n tarjoamia komponentteja.

GameView käyttää [AnimationTimer](https://docs.oracle.com/javase/8/javafx/api/javafx/animation/AnimationTimer.html)-oliota pelin reaaliaikaiseen simulointiin. GameView:llä on yhteys sovelluslogiikkaan [Game](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/Game.java)-luokan kautta. Game säilyttää pelin oliota JavaFX:n [ObservableList](https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html)-olioissa, jotka injektoidaan Game-luokkaan GameView:stä. Gamen kannalta mikä tahansa [List](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)-rajapinnan toteuttava luokka toimisi. Kun listoihin lisätään tai niistä poistetaan olioita, päivittyvät muutokset automaattisesti käyttöliittymän näkymään.

GameView:n lisäksi ScoresView:llä, GameOverView:llä ja EnterScoreView:llä on yhteys sovelluslogiikkaan. Yhteys ei kuitenkaan ole Game-luokkaan vaan [HighScoreService](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/highscore/HighScoreService.java)-luokkaan, jonka avulla näkymät saavat tietoa parhaista pisteistä.

## Sovelluslogiikka

Luokka Game pitää sisällään pelin tämänhetkisen tilanteet sekä metodeja tilan muuttamiseen. Törmäysten tarkistus on sovelluksessa tärkeässä asemassa (esim. pallo osuu mailaan tai pallo osuu tiileen). Varsinainen tarkistus delegoidaan eteenpäin, mutta törmäysten seuraukset käsitellään myös enimmäkseen luokassa Game.

Luokat [Ball](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/Ball.java), [Brick](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/Brick.java), [Paddle](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/Paddle.java) ja [Powerup](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/powerup/Powerup.java) kuvaavat pelissä näkyvia olioita. Kaikki nämä perivät abstraktin luokan [Entity](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/Entity.java), jossa on määritelty kahden Entityn välisen törmäyksen tarkistus. Törmäyksen tarkistus on toteutettu käyttämällä JavaFX:än oliota [Shape](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Shape.html), mikä yksinkertaistaa tarkistusta huomattavasti. Shape olioita käytetään myös osana käyttöliittymää.

HighScoreService on ainoa luokka, jolla on yhteys dao-pakkaukseen. Sen avulla käyttöliittymäluokat voivat hakea parhaita pisteitä ja tallentaa niitä. Varsinainen tallennus ja haku tehdään injektoidun [HighScoreDao](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/dao/HighScoreDao.java)-rajapinnan toteuttavan luokan avulla. Nimen ja pisteiden yhdistämiseen käytetään apuna [HighScoreEntry](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/highscore/HighScoreEntry.java)-olioita.

[PowerupService](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/powerup/PowerupService.java) luokka luo Powerup-olioita ja pitää kirjaa tällä hetkellä aktiivisista powerupeista. Powerupin tyyppi esitetään [PowerupType](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/powerup/PowerupType.java)-Enum-olion avulla. Varsinaiset poweruppien aiheuttamat muutokset käsitellään Game luokassa.

[LevelGenerator](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/LevelGenerator.java) luo satunnaisuutta hyödyntäen uusia tasoja tai tarkemmin sanottuna eri tiilien konfiguraatioita. Muu tason luontiin liittyvä työ, kuten pallojen ja mailan nollaaminen, tapahtuu Game luokassa. Tasojen luonti on hyvin yksinkertaista: LevelGenerator luo tasot riveittäin valiten satunnaisesti yhden kuudesta mahdollisesta rivityypistä. Riippuen valitusta tyypistä saatetaan vielä satunnaisesti valita tiilen tyyppi.

Ohjelman sovelluslogiikka kuvaava luokkakaavio:

<img src="https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkarakenne.png" width="700">

## Tietojen tallennus

Sovellus tallentaa parhaat pisteet _scores.txt_ tiedostoon. Tallenus tehdään [FileHighScoreDao](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/dao/FileHighScoreDao.java)-luokalla, joka toteuttaa [HighScoreDao](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/dao/HighScoreDao.java)-rajapinnan. Tiedostoon tallennus tehdään aina rajapinnan kautta eli toteutuksen voisi vaihtaa esimerkiksi tietokantaan.

### Tiedosto

Tiedostossa pisteet on kirjoitettu riveittäin. Nimimerkki on erotettu pisteistä tabilla. Jos tiedostoa ei ole olemassa tai sen muoto on väärä (rivejä liian vähän, ei nimimerkkiä tai pisteitä, pisteet eivät ole kokonaisluku), luodaan uusi tiedosto oletusarvoilla. Käyttöliittymän kautta syötettynä kaikki nimimerkit ovat kolme merkkisiä, mutta nimimerkin pituutta tiedostoa luettaessa ei valvota.

## Esimerkki toiminnallisuuksia

### Näkymän vaihto

Kun halutaan vaihtaa näkymää kutsutaan ViewManagerin changeView-metodia. ChangeView ottaa parametrina merkkijonon, joka kuvaa haluttua näkymää ja listan argumentteja tai null, jos argumentteja ei tarvita. Näkymä etsitään merkkijonon perusteella hajautustaulusta, jonka jälkeen kutsutaan uuden näkymän metodia enter, joka luo näkymää vastaavan Scene-olion ja tekee muun tarvittavan alustuksen. Mahdolliset argumentit annetaan myös enter-metodin kautta. Kun alustus on tehty, enter palauttaa Scene-olion ViewManagerille, joka asettaa sen Stage-olioon ja näkymän vaihto on valmis.

### Mailan liikuttaminen

Kun ollaan pelinäkymässä (GameView), painetut näppäimet tarkistetaan joka päivityskerralla. Jos mukana on nuolinäppäin vasemmalle tai oikealle kutsutaan Gamen metodia movePaddle, jolle annetaan luku joka kertoo liikkeen suunnan (negatiivinen vasemmalle, positiivinen oikealle), sekä dt eli delta time, joka kertoo kuluneen ajan edellisestä päivityksestä sekunteina. Game kutsuu edelleen Paddle-luokan metodia move, jolle annetaan mailan siirtymä vaakatasossa, sekä dt. Lopulta move siirtää mailaa annetulla määrällä, dt:llä kerrottuna. Näin ollen liikkeen määrä pysyy samana päivitynopeudesta riippumatta. Tarvittaessa move siirtää mailaa vielä uudelleen, jos maila joutui pelikentän ulkopuolelle.

### Pisteiden tallennus

Kun käyttäjä on antanut nimimerkin, kutsutaan HighScoreServicen metodia addScore, jolle annetaan nimimerkki ja pisteet. HighScoreService luo HighScoreEntry olion, joka kuvaa yhtä riviä highscore-listassa. Huonoimmat pisteet poistetaan ja uusimman pisteiden indeksi listassa tallennetaa. Tämän jälkeen HighScoreService kutsuu HighScoreDao rajapinnan kautta FileHighScoreDaon metodia add, joka ylikirjoittaa koko tiedoston uudelleen. Lopuksi näkymä vaihdetaan pisteiden listaukseen (ScoresView), joka kutsuu HighScoreServicen metodia getNewestScoreIndex, jonka avulla se korostaa juuri lisätyt pisteet.

## Ohjelman rakenteeseen liittyvät ongelmat

Game hallitsee suurta osaa ohjelman logiikasta ja sen metodien toiminta riippuu hyvin paljon pelin sen hetkisestä tilasta, jonka takia sen testaaminen on aika hankalaa. 
