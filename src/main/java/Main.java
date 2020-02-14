import application.console.ConsoleQueue;
import helpers.ControllerIO;

import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world");
        try(Scanner scanner = new Scanner(System.in);
            PrintWriter printWriter = new PrintWriter(System.out)){
            ControllerIO controllerIO = new ControllerIO(scanner, printWriter);
            ConsoleQueue consoleQueue = new ConsoleQueue(controllerIO);
            consoleQueue.runQueueDemo();
        }
    }
}
