# BrickBreaker-peli

Yksinkertainen Arkanoid-tyylinen peli satunnaisesti generoitavilla tasoilla.  
  
**HUOM!** Peli on vielä aika buginen ja pallo voi esimerkiksi pomppia oudosti tai jäädä seiniin kiinni varsinkin jos fps on alhainen.

## Dokumentaatio
[Käyttöohje](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)  
[Vaatimusmäärittely](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)  
[Työaikakirjanpito](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)  
[Arkkitehtuurikuvaus](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)  

## Releaset

[Viikko 5](https://github.com/JakeKallioniemi/ot-harjoitustyo/releases/tag/viikko5)  
[Viikko 6](https://github.com/JakeKallioniemi/ot-harjoitustyo/releases/tag/viikko6)

## Komentorivitoiminnot

**HUOM!** Kaikki listatut komennot tulee suorittaa BrickBreaker-kansion sisällä eikä juuri kansiossa.

### Ohjelman käynnistys

Ohjelma suoritetaan komennolla

```
mvn compile exec:java -Dexec.mainClass=brickbreaker.BrickBreaker
```
### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _BrickBreaker-1.0-SNAPSHOT.jar_

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedoston _target/site/jacoco/index.html_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedoston _target/site/apidocs/index.html_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/checkstyle.xml) määritellyt tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedoston _target/site/checkstyle.html_
