[//]: # (Ten znak możesz traktować jak komentarz.)
[//]: # (W tym pliku będziemy symulować działania użytkownika.)

[//]: # (Poniżej znajdziesz wpisy reprezentujące działania użytkownika. Zastanów się nad schematem / wzorcem pojedynczego wpisu.)
[//]: # (Przy niektórych wpisach znajdziesz komentarz.)

[//]: # (Najpierw stwórzmy podstawy działania salonu samochodowego)

[//]: # (Inicjujemy sprzedawców)
[//]: # (name;surname;pesel)
INIT -> SALESMAN -> Stefan;Sprzedawca;67020499436
INIT -> SALESMAN -> Agnieszka;Samochodowa;73021314515
INIT -> SALESMAN -> Tomasz;Kombi;55091699846
INIT -> SALESMAN -> Rafał;Dach;62081825675

[//]: # (Inicjujemy mechaników)
[//]: # (name;surname;pesel)
INIT -> MECHANIC -> Robert;Śrubokręt;52070997836
INIT -> MECHANIC -> Zygmunt;Naprawa;83011863727
INIT -> MECHANIC -> Remigiusz;Alufelga;67111396321

[//]: # (Inicjujemy samochody na sprzedaż)
[//]: # (vin;brand;model;year;color;price)
INIT -> CAR -> 1FT7X2B60FEA74019;BMW;Series 1;2020;black;20000
INIT -> CAR -> 1N6BD06T45C416702;BMW;Series 3;2020;black;30000
INIT -> CAR -> 1G1PE5S97B7239380;BMW;Series 3;2020;black;30000
INIT -> CAR -> 1GCEC19X27Z109567;BMW;Series 5;2020;black;40000
INIT -> CAR -> 2C3CDYAG2DH731952;BMW;Series 5;2020;black;40000
INIT -> CAR -> 1GB6G5CG1C1105936;BMW;Series 7;2020;black;60000

[//]: # (Inicjujemy katalog usług serwisu)
[//]: # (serial_number;description;price)
INIT -> SERVICE -> 58394-014;Wymiana koła;240.20
INIT -> SERVICE -> 55319-866;Strojenie koła;50.20
INIT -> SERVICE -> 0008-0407;Wymiana oleju;140.15
INIT -> SERVICE -> 43063-180;Wymiana korka do oleju;17.19
INIT -> SERVICE -> 14222-2022;Wymiana filtra paliwa;14.98

[//]: # (Inicjujemy katalog części serwisu)
[//]: # (service_code;description;price)
INIT -> PART -> 11523-7310;Koło;320.11
INIT -> PART -> 54340-777;Olej;270.18
INIT -> PART -> 68180-556;Korek do oleju;140.15
INIT -> PART -> 0268-1264;Filtr powietrza;90.19

[//]: # (Teraz zasymulujmy sytuację, gdzie nowy klient kupuje samochód)
[//]: # (name;surname;telephone;email;country;city;postal_code;address)
BUY_FIRST_TIME -> CUSTOMER -> Alfred;Samochodowy;+48 754 552 234;alf.samoch@gmail.com;Polska;Wrocław;20-001;Bokserska 15 -> CAR -> 1G1PE5S97B7239380 -> SALESMAN -> 73021314515
BUY_FIRST_TIME -> CUSTOMER -> Monika;Cybulska;+48 458 774 125;mon.cyb@gmail.com;Polska;Wrocław;30-001;Wesoła 24 -> CAR -> 1FT7X2B60FEA74019 -> SALESMAN -> 55091699846
BUY_FIRST_TIME -> CUSTOMER -> Tomasz;Zimnoch;+48 486 324 125;tom.zim@gmail.com;Polska;Gdynia;50-001;Wrocławska 55 -> CAR -> 1GCEC19X27Z109567 -> SALESMAN -> 55091699846
BUY_FIRST_TIME -> CUSTOMER -> Robert;Wesoły;+48 184 239 489;rob.wes@gmail.com;Polska;Wrocław;10-001;Warszawska 321 -> CAR -> 1GB6G5CG1C1105936 -> SALESMAN -> 62081825675
BUY_FIRST_TIME -> CUSTOMER -> Marek;Marek;+48 587 566 234;mar.mar@gmail.com;Polska;Zakopane;40-001;Ciepła 22 -> CAR -> 1N6BD06T45C416702 -> SALESMAN -> 62081825675

[//]: # (Teraz samochód kupuje klient, który kupował już wcześniej samochód)
BUY_AGAIN -> CUSTOMER -> alf.samoch@gmail.com -> CAR -> 2C3CDYAG2DH731952 -> SALESMAN -> 55091699846

[//]: # (Następnie klient taki przyprowadza samochód do warsztatu i dokonuje naprawy)
SERVICE_REQUEST -> CUSTOMER -> alf.samoch@gmail.com -> CAR -> 2C3CDYAG2DH731952 -> WHAT -> Koło mi odpadło
SERVICE_REQUEST -> CUSTOMER -> tom.zim@gmail.com -> CAR -> 1GCEC19X27Z109567 -> WHAT -> Olej mi cieknie na stopy

[//]: # (Teraz do warsztatu przychodzą klienci, którzy nie kupili samochodu u tego dealera)
SERVICE_REQUEST -> CUSTOMER -> Adrian;Paczkomat;+48 991 221 123;adr.paczk@gmail.com;Polska;Szczecin;10-001;Górna 15 -> CAR -> 1N4BA41E18C806520;BMW;Series 1;2015 -> WHAT -> Coś nie działa prawidłowo

[//]: # (Później, każdy mechanik sprawdza, czy została do niego przydzielona jakaś praca.)
[//]: # (Oczywiście w takim przypadku, pisząc oprogramowanie musielibyśmy wprowadzić jakieś reguły biznesowe, żeby odzwierciedlić proces, w którym przydzielana jest praca mechanikom.)
[//]: # (Z racji, że jest to przykład edukacyjny, uprościmy sobie całość.)
[//]: # (service_code;quantity:product_code;quantity;comment;finished)

DO_THE_SERVICE -> MECHANIC -> 52070997836 -> CAR -> 2C3CDYAG2DH731952 -> WHAT -> 11523-7310;1;58394-014;3;OK;NOT_FINISHED
DO_THE_SERVICE -> MECHANIC -> 67111396321 -> CAR -> 2C3CDYAG2DH731952 -> WHAT -> ;;55319-866;2;OK;FINISHED
DO_THE_SERVICE -> MECHANIC -> 52070997836 -> CAR -> 1GCEC19X27Z109567 -> WHAT -> 54340-777;1;0008-0407;1;OK;NOT_FINISHED
DO_THE_SERVICE -> MECHANIC -> 83011863727 -> CAR -> 1GCEC19X27Z109567 -> WHAT -> 68180-556;2;43063-180;2;OK;NOT_FINISHED
DO_THE_SERVICE -> MECHANIC -> 67111396321 -> CAR -> 1GCEC19X27Z109567 -> WHAT -> 0268-1264;2;14222-2022;1;OK;FINISHED

[//]: # (Na koniec implementacji, dopisz jeszcze możliwość wyciągnięcia historii serwisowej auta na podstawie jego numeru VIN)