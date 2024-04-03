package Engine;
import chessboard.Board;
import chessboard.tools.Position;

import java.awt.*;
import java.util.Scanner;
/**
 * @author Ryszard Kopyci�ski
 * zarz�dza rozgrywk�, akcjami od graczy
 * komunikuje si� z plansz� w cenu weryfikacji sytuacji w grze
 * po�redniczy mi�dzy graczami a szachownic�
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
        return "Bia�y";
    }
    private boolean isCorrectCommand(String command) {
        command = command.trim();
        if (command.length() >= 2) {//minimum 2 znaki
            Position selected = Position.getInstance(command.substring(0, 2));
            if (selected != null) {//pocz�tek poprawny
                command = command.substring(2).trim();
                if (command.length() == 0) {//brak drugiej cz�ci, zapytanie o ruchy
                    if (board.checkPossibleAvtions(selected)) {
                        board.print();
                        return false;
                    }
                } else if (command.length() >= 2) {//druga cz�� ma ponad dwa znaki
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