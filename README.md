# Tema POO  - GwentStone

<div align="center"><img src="https://tenor.com/view/witcher3-gif-9340436.gif" width="500px"></div>

## Homework Structure

Tema se foloseste de doua clase principale pentru a executa si rula jocurile:

**AppManager** -> clasa care pregateste fiecare test prin a lua toate deckurile
jucatorilor si toate meciurile.

**GameManager** -> clasa care pregateste fiecare joc si ruleaza actiunile date
la input. GameManager se ocupa si de retinerea elementelor dintr-un joc:
cartile de pe masa, cartile din mana jucatorilor, eroii, playerul care isi
joaca tura intr-un moment dat, mana care trebuie data fiecarui jucator etc.

In plus, am creat alte clase pentru a modulariza afisarea si tratarea cazurilor
invalide:

**DebugCommands** -> are o singura functie statica in care tratez comenzile de
debug. Pentru cateva din cazuri a trebuit sa clonez outputul deoarece aveam
nevoie de o copie a referintei pentru a afisa statutul unui obiect la un
moment dat.

**Conditions** -> este o clasa care contine functii de test pentru anumite
conditii din joc (verifica daca un player are un tank, daca cartea HeartHound
poate fi folosita etc.)

**PrintOutput** -> clasa ajutatoare pe care o folosesc sa afisez outputurile
destul de usor. Are multi constructori care sunt folositi in fuctie de ce vreau
sa afisez la output.

**PrintErrors** -> contine functii care se apeleaza atunci cand avem cazuri
invalide.

Clasa Card contine informatii universale pentru fiecare clasa ce o mosteneste:
Clasele minion, environment si hero mostenesc clasa Card plus faptul ca fiecare
contine functii pentru rularea comenzilor din input specifice lor (clasa minion
are implementata functiile placeCard, attack si useAbility).


