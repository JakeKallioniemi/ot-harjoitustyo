# BrickBreaker-peli

Yksinkertainen Arkanoid-tyylinen peli satunnaisesti generoitavilla tasoilla.

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

### Käynnistys

```
mvn compile exec:java -Dexec.mainClass=brickbreaker.BrickBreaker
```

Käynnistää ohjelman.

### Suoritettavan jar-tiedoston luonti

```
mvn package
```

Luo _BrickBreaker-1.0-SNAPSHOT.jar_ nimisen jar-tiedoston hakemistoon _target_.

### Testaus

```
mvn test
```

Suorittaa JUnit testit.


```
mvn jacoco:report
```

Luo HTML-muotoisen JaCoCo testikattavuusraportin, jota voi tarkastella avaamalla tiedoston _target/site/jacoco/index.html_ selaimella.

### JavaDoc

```
mvn javadoc:javadoc
```

Luo JavaDocin, jota voi tarkastella  avaamalla tiedoston _target/site/apidocs/index.html_ selaimella.

### Checkstyle

```
 mvn jxr:jxr checkstyle:checkstyle
```

Suorittaa tiedostoon [checkstyle.xml](https://github.com/JakeKallioniemi/ot-harjoitustyo/blob/master/BrickBreaker/checkstyle.xml) määritelly tarkastukset. Tuloksia voi tarkastella avaamalla tiedoston _target/site/checkstyle.html_ selaimella.
