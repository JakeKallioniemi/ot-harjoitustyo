# BrickBreaker-peli

Yksinkertainen Arkanoid-tyylinen peli satunnaisesti generoitavilla tasoilla.

## Dokumentaatio
[Vaatimusmäärittely](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)  
[Työaikakirjanpito](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)  
[Arkkitehtuurikuvaus](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

### Ohjelman käynnistys

Ohjelma suoritetaan komennolla

```
mvn compile exec:java -Dexec.mainClass=brickbreaker.BrickBreaker
```
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

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/checkstyle.xml) määritellyt tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedoston _target/site/checkstyle.html_
