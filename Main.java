import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        playGame();
        scanner.close();
    }

    public static void playGame() {

        System.out.println("How many pencils would you like to use:");
        int pencilsOnTable = takePencilInput();

        System.out.println("Who will be the first (John, Jack):");
        boolean humansTurn = takePlayerName().equalsIgnoreCase("John");
        String playerName = "John";
        String botName = "Jack";

        printPencils(pencilsOnTable);

        while(pencilsOnTable > 0) {
            if(humansTurn) {
                System.out.println(playerName + "'s turn");
                pencilsOnTable = subtractPencilsAndCheckWinner(pencilsOnTable, humansTurn);
            } else {
                System.out.println(botName + "'s turn");
                pencilsOnTable = subtractPencilsAndCheckWinner(pencilsOnTable, humansTurn);
            }
            humansTurn = !humansTurn;
        }
    }

    public static int takePencilInput() {

        String startingPencils = scanner.nextLine();
        int pencilsOnTable;

        try {
            pencilsOnTable = Integer.parseInt(startingPencils);
            if(pencilsOnTable < 1) {
                System.out.println("The number of pencils should be positive");
                return takePencilInput();
            }
        } catch (NumberFormatException e) {
            System.out.println("The number of pencils should be numeric");
            return takePencilInput();
        }
        return pencilsOnTable;
    }

    public static String takePlayerName(){

        String playerName = scanner.nextLine();

        if(playerName.equalsIgnoreCase("John") || playerName.equalsIgnoreCase("Jack")) {
            return playerName;
        } else {
            System.out.println("Choose between 'John' and 'Jack'");
            return takePlayerName();
        }
    }

    public static void printPencils(int pencils) {

        String pencil = "|";
        if(pencils != 0) {
            System.out.println(pencil.repeat(pencils));
        }
    }

    public static int subtractPencilsAndCheckWinner (int pencilsOnTable, boolean isHuman) {

        int pencilsTaken;

        if(isHuman) {
            try{
                pencilsTaken = Integer.parseInt(scanner.nextLine());

                if(pencilsTaken < 1 || pencilsTaken > 3) {
                    System.out.println("Possible values: '1', '2', '3'");
                    return subtractPencilsAndCheckWinner(pencilsOnTable, true);
                } else if(pencilsTaken > pencilsOnTable) {
                    System.out.println("Too many pencils were taken");
                    return subtractPencilsAndCheckWinner(pencilsOnTable,true);
                }
            } catch (NumberFormatException e) {
                System.out.println("Possible values: '1', '2' or '3'");
                return subtractPencilsAndCheckWinner(pencilsOnTable, true);
            }

        } else {
            if(pencilsOnTable == 1) {
                pencilsTaken = 1;
            } else if(pencilsOnTable % 4 == 0) {
                pencilsTaken = 3;
            } else if(pencilsOnTable % 4 == 3) {
                pencilsTaken = 2;
            } else if (pencilsOnTable % 4 == 2) {
                pencilsTaken = 1;
            } else {
                Random random = new Random();
                pencilsTaken = random.nextInt(3) + 1;
                pencilsTaken = Math.min(pencilsTaken, pencilsOnTable);
            }
        }

        if(!isHuman) {
            System.out.println(pencilsTaken);
        }

        pencilsOnTable -= pencilsTaken;
        printPencils(pencilsOnTable);

        if(pencilsOnTable  == 0) {
            System.out.println((isHuman ? "Jack" : "John") + " won");
            return 0;
        }
        return pencilsOnTable;
    }
}
