import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RulesControlsAndStrategies {
    
    private String rules;
    private String controls;
    private String[] strategies;


    public RulesControlsAndStrategies() {
               
    }

    public void setEnglishLang() {
        this.rules = "Minesweeper game is played on a grid of squares.\n"
                + "Safe squares have numbers telling you how many mines touch the square. Empty squares mean there is no mine nearby.\n"
                + "You can use the number clues to solve the game by opening all of the safe squares.\n"
                + "The goal is to avoid the mines. First click is always safe.\n"
                + "The game is lost if a mine is clicked.\n"
                + "The game is won if all the empty squares are revealed.\n"
                + "You can flag the squares they think contain mines.\n"
                + "You can also place a question mark on squares they are unsure about.\n"
                + "Left counter shows the number of mines without flags, and right counter is clock that shows your time in seconds.\n"
                + "Good luck!\n";


        this.controls = "Left click to reveal a square.\n"
                + "Left click on a revealed square to reveal all the adjacent squares if the number of flags around the square is equal to the number on the square.\n"
                + "Right click to flag a square.\n"
                + "Right click again to place a question mark, if question marks are enabled.\n"
                + "Right click once more to clear the square.\n"
                + "Right click on a revealed square, if its number equals the unrevealed adjacent squares, marks them all at once with a flag.\n";
                
        this.strategies = new String[]{
                "Simple situations where the number\n of unrevealed adjacent squares is equal\n to the number on the square.\n So all adjacent squares contain mines.",
                "After placing flags in the previous example,\n we can notice that even the adjacent square\n has the required number of flags, but some\n of its adjacent squares are still unrevealed.\n These squares are definitely safe.",
                "Pattern 1-1-X, vertical.\n The square marked in green cannot contain a mine\n because the yellow marked edge square\n would have no square to place a mine.",
                "Pattern 1-1-X, horizontal.\n The squares marked in green cannot contain a mine\n because the yellow marked edge square\n would have no square to place a mine.",
                "Pattern 1-1-1-X, horizontal.\n The squares marked in green cannot contain a mine\n because the yellow marked edge square\n would have no square to place a mine.",
                "Pattern 1-2-X.\n This pattern is based on the fact that the 1\nensures that 2 out of three squares, where the 2\n must contain 2 mines, only one mine can be placed.\n The third square is therefore definitely a mine.\n Subsequently, it is possible to use 1-1-X pattern.",
                "Pattern 1-2-1.\n This pattern is a variant of the 1-2-X pattern\n from both sides.",
                "Pattern 1-2-2-1.\n This pattern is a variant of the 1-2-X pattern\n from both sides.",
                "Pattern 1-3-1.\n This pattern is based on the fact that the 1s\n from both sides ensure that out of the four squares\n adjacent to the 3, only two mines be placed.\n They limit 4 squares out of 5 to two mines.\n The last fifth square must then be the third mine.\n Subsequently, it is possible to use 1-2-X pattern.",
                "Mine reduction:\n This pattern is based on the fact that the 1\n ensures that out of five squares, where the 4\n must contain 4 mines, only one mine can be placed.\nThe remaining three squares are then must be mines.\n Subsequently, it is possible to use 1-1-X pattern.",
                "Pattern reduction:\n On playing field, you need to pay\n attention to hidden patterns that can\n be found after subtracting already known mines.\n In this case, it is the 1-2-1 pattern."
            };
    }

    public void setSlovakLang() {
        this.rules = "Míny sa hrajú na štvorcovom poli, ktoré hráč postupne odokrýva.\n"
                + "Bezpečné políčka majú čísla, ktoré označujú koľko mín sa dotýka políčko.\n"
                + "Prázdne políčka indikujú, že v blízkosti nie je žiadna mína.\n"
                + "Cieľom je vyhnúť sa minám. Prvé kliknutie je vždy bezpečné.\n"
                + "Hra je prehratá, ak kliknete na mínu.\n"
                + "Hra je vyhratá, ak sú odhalené všetky políčka okrem mín.\n"
                + "Môžete označiť políčka, o ktorých si myslíte, že obsahujú miny.\n"
                + "Môžete tiež umiestniť otáznik na políčka, o ktorých si nie ste istí.\n"
                + "Ľavé počítadlo zobrazuje počet mín neoznačených vlajkou.\n"
                + "Pravé počítadlo, zobrazuje váš čas v sekundách.\n"
                + "Veľa šťastia!\n";

        this.controls = "Ľavým kliknutím odhalíte políčko.\n"
                + "Ľavým kliknutím na už preskúmané políčko odhalíte všetky susedné políčka, ak je počet vlajok okolo políčka rovnaký ako číslo na políčku.\n"
                + "Pravým kliknutím označíte políčko vlajkou.\n"
                + "Pravým kliknutím ešte raz umiestnite otáznik, ak sú otázniky povolené.\n"
                + "Pravým kliknutím ešte raz odstránite symbol zo políčka.\n"
                + "Pravým kliknutím na už preskúmané políčko, ak jeho číslo zodpovedá neodhaleným susedným políčkam, označíte ich všetky naraz vlajkou.\n";
        
        this.strategies = new String[] {
                "Jednoduché situácie kde počet nepreskúmaných\n susedných políčok je rovnaký ako číslo na políčku.\n Teda všetky susedné políčka obsahujú miny.",
                "Po umiestnení vlajok v predchádzajúcom príklade\n si môžeme všimnúť, že aj susedné políčko\n už má splnený počet mín, ale niektoré\n jeho susedné políčka sú ešte nepreskúmané.\n Tieto políčka sú určite bezpečné.",
                "Vzor 1-1-X, vertikálny.\n Políčko vyznačené zelenou nemôže obsahovať mínu\n pretože krajnej jednotke vyznačenej žltou\n by neostalo žiadne políčko kde by sa dala\n umiestniť mína.",
                "Vzor 1-1-X, horizontálny.\n Políčka vyznačené zelenou nemôžu obsahovať mínu\n pretože jednotke vľavo vyznačenej žltou\n by neostalo žiadne políčko kde by sa dala\n umiestniť mína.",
                "Vzor 1-1-1-X, horizontálny.\nPolíčka vyznačené zelenou nemôžu obsahovať mínu\n pretože jednotke vľavo vyznačenej žltou\n by neostalo žiadne políčko kde by sa dala\n umiestniť mína.",
                "Vzor 1-2-X.\n Tento vzor spočíva v tom, že jednotka zabezpečuje\n aby na dve políčka z troch, kde dvojka musí\n obsahovať 2 míny, mohla byť, umiestnená iba\n jedna mína. Tretie políčko je teda určite mína.\nNásledne je taktiež možné využiť vzor 1-1-X.",
                "Vzor 1-2-1.\n Tento vzor je variant vzoru 1-2-X z oboch strán.",
                "Vzor 1-2-2-1.\n Tento vzor je variant vzoru 1-2-X z oboch strán.",
                "Vzor 1-3-1.\n Tento vzor spočíva v tom, že jednotky z oboch\n strán zabezpečujú že na dvoch políčkach\nsusediach s číslom 3 môže byť iba jedna mína.\n Limitujú teda 4 políčka z piatich na dve míny.\n Posledné piate políčko teda musí byť tretia mína.\nNásledne je taktiež možné využiť vzor 1-2-X.",
                "Redukcia mín:\n Tento vzor spočíva v tom, že jednotka zabezpečuje\n aby na dve políčka z piatich, kde štvorka musí\n obsahovať 4 míny, mohla byť, umiestnená iba\n jedna mína. Zvyšné tri políčka sú teda určite míny.\nNásledne je taktiež možné využiť vzor 1-1-X.",
                "Redukcia vzorov:\n Na hracej ploche si treba všímať skryté vzory,\nktoré možno nájsť po odpočítaní už známych mín.\n V tomto prípade sa jedná o vzor 1-2-1."
                
        };

    }
}
