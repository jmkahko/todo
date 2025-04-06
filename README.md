# Todo sovellus
Todo sovelluksella demonstroidaan miten sovellus on haavoittuvainen SQL-injektioille. Sovellusta parannetaan eriversiossa, ettei SQL-injektiot olisi enään mahdollisia.

## Ensimmäinen versio
Ensimmäisessä [versiossa](https://github.com/jmkahko/todo/tree/versio1) sovellus on SQL-injektoituva. Sovelluksen DAO-luokissa käytetään Javan [Statement](https://docs.oracle.com/javase/8/docs/api/java/sql/Statement.html)-rajapintaa. Tietokantaan tallennetuissa procedureissa käytetään EXECUTE-komentoa ja parametrit asetetaan SQL-lauseeseen suoraan
```
k.tunnus = ’’’ || input_tunnus || ’’’
```


## Toinen versio
Toisessa [versiossa](https://github.com/jmkahko/todo/tree/versio2) sovelluksen Java DAO-luokkiin on Javan [Statement](https://docs.oracle.com/javase/8/docs/api/java/sql/Statement.html)-rajapinnan tilalle vaihdettu [PreparedStatement](https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html)-rajapinta tilanteissa, joissa asetetaan muuttujia SQL-lauseeseen.


### Muutetut DAO-luokat:
- [CustomerDao](https://github.com/jmkahko/todo/blob/versio2/src/main/java/fi/gradu/todo/dao/CustomerDao.java)
- [TodoDao](https://github.com/jmkahko/todo/blob/versio2/src/main/java/fi/gradu/todo/dao/ToDoDao.java)


## Kolmas versio
Kolmannessa [versiossa](https://github.com/jmkahko/todo/tree/versio3) on muutettu [DefaultController](https://github.com/jmkahko/todo/blob/versio3/src/main/java/fi/gradu/todo/controller/DefaultController.java) luokan createNewUser(..) metodia, johon lisätty tulevian tietojen tarkistusta. Lisätty myös [ApiController](https://github.com/jmkahko/todo/blob/versio3/src/main/java/fi/gradu/todo/controller/ApiController.java) luokan todoSearchById(..) metodiin try catch lohko tulevan parametrin merkkijonon muunnos long muotoon, ettei virhettä lokiteta.


## Neljäs versio
Neljännessä [versiossa](https://github.com/jmkahko/todo/tree/versio4) on muutettu tietokannan luo_kayttaja(..) [procedure](https://github.com/jmkahko/todo/blob/versio4/Tietokanta/init-db.sql) metodia, ettei ajeta EXECUTE-komentoa ja parametrejä ei asetata suoraan SQL-lauseeseen.


## Viides versio
Viidennessä [versiossa](https://github.com/jmkahko/todo/tree/versio5) on palautettu takaisin kolmennessa [versiossa](https://github.com/jmkahko/todo/tree/versio3) tehty muutos DefaultController luokkaan.