**LOGICKÉ MÍNY** / **GUESS-FREE MINESWEEPER**

[SLOVAK]

Tento Minesweeper sa snaží byť lepšou verziou klasického Minesweeperu pre Windows XP.


**Vylepšenia:**


1) Hlavnou kľúčovou vlastnosťou je, že existujú iba hracie dosky bez hádania.
Táto aplikácia **Míny** využíva sofistikované algoritmy na generovanie iba takých hracích dosiek, ktoré nevyžadujú hádanie.
Každú hru je možné vyriešiť pomocou logiky a dedukcie na základe čísel na hracej doske.

2) Na základe algoritmu si hráč môže vybrať obtiažnosť hracej dosky. Od jednoduchých, bez vzorov, až po viacnásobné výpočty s vzormi, ktoré treba nájsť počas levelu.
3) Ďalšou skvelou funkciou je veľmi potrebné tlačidlo **Späť**.
Po zásahu do míny si hráč môže vybrať, či chce vrátiť posledný ťah a pokračovať, alebo začať novú hru. Aby sme hráča motivovali ku hre bez chýb, dostáva 10 sekundový trest za každý zásah do míny.
4) Štvrtou dodatočnou funkciou, ktorá pomáha, ale je tiež vzdelávacia, sú **pravidlá** a **tipy**. Hráč nielenže môže študovať pravidlá, ktoré sú pomerne jednoduché, ale môže tiež objaviť vzory, ktoré mu môžu pomôcť stať sa celkovo lepším hráčom.
5) Okrem toho je tu aj tlačidlo **nápovedy**, ktoré pomáha hráčovi, keď nemôže nájsť ďalší ťah, a taktiež aj indikátor prebytočne označených mín. Počas hry je taktiež možné postupovať rýchlejšie a bezpečnejšie pomocou zlepšených funkcii ovládania.


**Ovládanie:**

**Ľavé kliknutie:** 
1.  Po kliknutí na ešte nepreskúmané políčko, sa políčko odhalí. Ak je políčko mína, hra sa končí. Ak nie, políčko odhalí svoje číslo reprezentujúce počet mín v susedných dlaždiciach.

2. Po kliknutí na už preskúmané políčko s číslom, odhalí okolité políčka ak počet vlajočiek zodpovedá číslu políčka na ktoré hráč klikol. Funguje aj pôvodná verzia ľavé + pravé kliknutie.

**Pravé kliknutie:** 
1.  Označí políčko vlajočkou, ak ešte nie je označené. Ak je políčko už označené vlajočkou, tak ho odznačí. Ak sú povolené aj otázniky, tak po vlajočke je políčko označené otáznikom a po ďalšom následnom kliknutí sa vráti do pôvodneho neoznačeného stavu.

2. Po kliknutí na už preskúmané políčko s číslom zistí, či sa v jeho okolí nachádza rovnaký počet nepreskúmaných políčok. Ak áno, znamená to že tieto políčka sú určite len míny a označí ich všetky naraz vlajočkou.

**Úrovne obtiažnosti:** Hra poskytuje rôzne úrovne obtiažnosti, ktoré sú začiatočník, pokročilý, expert a vlastné. Každá úroveň obtiažnosti určuje veľkosť plochy a počet mín.

**Podpora jazykov:** Hra podporuje viacero jazykov. Aktuálna implementácia zahŕňa angličtinu a slovenčinu a jazyk je možné kedykoľvek počas hry zmeniť. Možnosť jazyka zostáva vybratá aj po zatvorení aplikácie.

**Rebríček:** Hra udržiava rebríček, ktorý zaznamenáva najlepší čas pre úrovne obtiažnosti začiatočník, stredne pokročilý a expert. Rebríček tiež používa registre na ukladanie záznamov, takže zostávajú zachované.

**Nastavenia hry:** Hra umožňuje rôzne nastavenia, ako napríklad povolenie alebo zakázanie otáznikov.

**Reštart:** Hru je možné kedykoľvek reštartovať kliknutím na tlačidlo reštartovať.

**Pomoc a O aplikácii:** Hra obsahuje sekciu s pravidlami a tipmi a sekciu s informáciami o autorovi a hre.

**Ukončenie:** Hru je možné kedykoľvek ukončiť cez herné menu.

**Spätné kroky:** Hra má funkciu spätných krokov, ktorú je možné použiť po zásahu do míny.

**Prispôsobenie:** Hra umožňuje prispôsobenie veľkosti plochy a počtu mín, pričom minimálna veľkosť je 5x5 a maximálna 25x50. Počet mín je obmedzený na 20% celkového počtu políčok.

**Časovač a Počítadlo mín:** Hra obsahuje časovač, ktorý sa spustí okamžite po začatí hry, a počítadlo mín, ktoré zobrazuje počet zostávajúcich mín.

**Symboly:** Hra má dva symboly pre označenie políčok, vlajočky pre označené míny a voliteľné otázniky pre neznáme políčko.

Hra je implementovaná v jazyku Java a používa knižnicu Swing pre grafické užívateľské rozhranie.

Pre spustenie hry Minesweeper môžete použiť predkompilovaný JAR súbor, Minesweeper-final.jar.

Pre úspešné spustenie aplikácie postupujte podľa týchto krokov:

1. Uistite sa, že máte nainštalovanú Javu vo vašom systéme. Najnovšiu verziu si môžete stiahnuť zo stránky Oracle:

    [https://www.java.com/download/ie_manual.jsp](https://www.java.com/download/ie_manual.jsp)

Ak dvojklik na súbor JAR neotvorí aplikáciu, môže byť potrebné zmeniť program na otváranie súborov JAR vo vašom systéme.


Na zostavenie hry Minesweeper zo zdrojového kódu postupujte podľa týchto krokov:

1. Uistite sa, že máte nainštalovaný Java Development Kit (JDK) vo vašom systéme. Najnovšiu verziu JDK si môžete stiahnuť zo stránky Oracle.
    Najnovšiu verziu JDK si môžete stiahnuť zo stránky Oracle na tejto URL:

    [https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe](https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe)

2. Nainštalujte Visual Studio Code. Môžete si ho stiahnuť z oficiálnej webovej stránky:

    [https://code.visualstudio.com/download](https://code.visualstudio.com/download)

3. Nainštalujte "Java Extension Pack" z trhu VS Code. Tento balík rozšírení obsahuje podporu pre projekty Maven.

4. V zobrazení Maven nájdite projekt Minesweeper a rozbaľte ho. Mali by ste vidieť zoznam fáz životného cyklu Maven.

5. Dvakrát kliknite na fázu `clean`, aby ste vyčistili váš projekt, a potom dvakrát kliknite na fázu `package`, aby ste zostavili váš projekt.

Tým sa vytvorí JAR súbor v priečinku `target`, ktorý môžete spustiť a hrať hru.

Kód je možné aj ladiť priamo vo VSCode, spustením hlavnej triedy Minesweeper.java.


------------------------
[ENGLISH]


This Minesweeper aims to be a better version of the classic Windows XP Minesweeper.

**Improvements:**

1) The main key feature is that there are only guess-free boards.
This Minesweeper application uses sophisticated algorithms to generate only guess free boards.
Every game can be solved using logic and deduction based on the numbers on the board at any given time.
2) Based on the algorithm, player can pick a difficulty of the board. Ranging from simple, no patterns involved to multiple calculations with patterns to spot throughout the level.
3) Another awesome feature is a very much needed undo button.
After hitting a mine, user can choose to undo last move and continue or start a new game. To motivate player to not hit mines too much, he recieves 10 second penalty for hitting a mine each time.
4) The fourth additional feature that is helping but also educational is rules & hints. Not only can player study rules, which are fairly simple but can also discover patterns that can help him become an overall better player. 
5) In addition, there is also a hint button that helps the player when they cannot find the next move, and also an indicator for excessively flagged mines. During the game, it is also possible to proceed faster and safer using improved control functions.


**Controls:**

**Left click**: 
1.  When clicking on an unexplored tile, the tile is revealed. If the tile is a mine, the game ends. If not, the tile reveals its number representing the number of mines in the neighboring tiles.

2. When clicking on an already explored tile with a number, it reveals the surrounding tiles if the number of flagged tiles matches the number on the tile the player clicked. The original version of left + right click also works.

**Right click**: 
1.  Marks the tile with a flag if it is not already marked. If the tile is already marked with a flag, it unmarks it. If question marks are allowed, the tile is marked with a question mark after the flag, and after the next subsequent click, it returns to its original unmarked state.

2. When clicking on an already explored tile with a number, it checks if there is the same number of unexplored tiles in its vicinity. If yes, it means that these tiles are definitely mines and marks them all at once with a flag.


**Difficulty Levels**: The game provides different difficulty levels, which are beginner, intermediate, expert, and custom. Each difficulty level determines the size of the grid and the number of mines.

**Language Support**: The game supports multiple languages. The current implementation includes English and Slovak, and the language can be switched at any time during the game. Language option stays selected upon closing the application as well.

**Leaderboard**: The game maintains a leaderboard that records the best time for beginner, intermediate and expert difficulty level. The leaderboard also uses registry to save records, so they 

**Game Settings**: The game allows various settings to be adjusted, such as enabling or disabling question marks.

**Restart**: The game can be restarted at any time by clicking the restart button.

**Help and About**: The game includes a help section with rules and hints, and an about section with information about the author and the game.

**Exit**: The game can be exited at any time through the game menu.

**Undo**: The game has an undo feature that can be used after hitting a mine.

**Customization**: The game allows for customization of the board size and mine count, minimum size being 5x5 and maximum 25x50. The mine count is limited to 20% of the total board tiles.

**Timer and Mine Counter**: The game includes a timer that starts immediately when the game starts and a mine counter that shows the number of mines left.

**Symbols**: The game has two symbols for marking tiles, flags for marked mines and optional question marks for unknown tiles.

The game is implemented in Java and uses the Swing library for the graphical user interface.

To run the Minesweeper game, you can use the pre-compiled JAR file, Minesweeper-final.jar.

To succesfully run the application, follow these steps:

1. Make sure you have the Java Runtime Environment (JRE) installed on your system. You can download the latest version from Oracle's website:

    [https://www.java.com/download/ie_manual.jsp](https://www.java.com/download/ie_manual.jsp)

If double-clicking the JAR file doesn't run the application, you might need to change the file association for JAR files on your system.


To build the Minesweeper game from the source code, follow these steps:

1. Make sure you have Java Development Kit (JDK) installed on your system. You can download the latest version of JDK from the Oracle website.
You can download the latest version of JDK from the Oracle website at this URL:


[https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe](https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe)

2. Install Visual Studio Code. You can download it from the official website:

   [https://code.visualstudio.com/download](https://code.visualstudio.com/download)

3. Install the "Java Extension Pack" from the VS Code marketplace. This extension pack includes support for Maven projects.

4. In the Maven view, find Minesweeper project and expand it. You should see a list of Maven lifecycle phases.

5. Double-click on the `clean` phase to clean your project, and then double-click on the `package` phase to build your project.

This will create a JAR file in the `target` directory that you can run to play the game.

You can also debug the code by running the main class Minesweeper.java directly in VSCode.

