package Engine;
import chessboard.Board;
import chessboard.tools.Position;

import java.awt.*;
import java.util.Scanner;
/**
 * @author Ryszard Kopyciñski
 * zarz¹dza rozgrywk¹, akcjami od graczy
 * komunikuje siê z plansz¹ w cenu weryfikacji sytuacji w grze
 * poœredniczy miêdzy graczami a szachownic¹
 */
public class Game {
    private final Board board;
    private int turnNumber;
    public Game() {
        board = new Board();
        turnNumber = 1;
    }
    public void start() {
        board.print();
        while (finishedTurn()) {
            nextTurn();
            board.print();
        }
    }
    private String currentColor() {
        if (board.getCurrentColor() == Color.BLACK) {
            return "Czarny";
        }
        return "Bia³y";
    }
    private boolean isCorrectCommand(String command) {
        command = command.trim();
        if (command.length() >= 2) {//minimum 2 znaki
            Position selected = Position.getInstance(command.substring(0, 2));
            if (selected != null) {//pocz¹tek poprawny
                command = command.substring(2).trim();
                if (command.length() == 0) {//brak drugiej czêœci, zapytanie o ruchy
                    if (board.checkPossibleAvtions(selected)) {
                        board.print();
                        return false;
                    }
                } else if (command.length() >= 2) {//druga czêœæ ma ponad dwa znaki
                    Position target = Position.getInstance(command.substring(0, 2));
                    if (target != null) {//koniec poprawny
                        if (board.makeAction(selected, target)) {
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("Bledna komenda!");
        return false;
    }
    private boolean finishedTurn() {
        Scanner in = new Scanner(System.in);
        boolean repeat;
        do {
            System.out.print("Tura: " + turnNumber + " [" + currentColor() + "]:");
            String input = in.nextLine().trim();
            repeat = !input.trim().equalsIgnoreCase("Q");
            if (repeat) {
                if (isCorrectCommand(input)) {
                    return true;
                }
            } else {
                return false;
            }
        } while (repeat);
        return true;
    }
    private void nextTurn() {
        if (board.getCurrentColor() == Color.WHITE) {
            turnNumber++;
        }
    }
}