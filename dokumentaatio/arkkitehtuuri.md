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

[PowerupService](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/powerup/PowerupService.java) luokka luo Powerup-olioita ja pitää kirjaa tällä hetkellä aktiivisista powerupeista. Varsinaiset poweruppien peliin tuovat muutokset käsitellään Game luokassa.

[LevelGenerator](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/src/main/java/brickbreaker/domain/LevelGenerator.java) luo satunnaisuutta hyödyntäen uusia tasoja tai tarkemmin sanottuna eri tiilien konfiguraatioita. Muu tason luontiin liittyvä työ, kuten pallojen ja mailan nollaaminen, tapahtuu Game luokassa. Tasojen luonti on hyvin yksinkertaista: LevelGenerator luo tasot riveittäin valiten satunnaisesti yhden kuudesta mahdollisesta rivityypistä. Riippuen valitusta tyypistä saatetaan vielä satunnaisesti valita tiilen tyyppi.

Ohjelman sovelluslogiikka kuvaava luokkakaavio:

<img src="https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkarakenne.png" width="700">

## Tietojen tallennus
