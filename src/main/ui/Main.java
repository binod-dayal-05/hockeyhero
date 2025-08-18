package ui;

import java.util.Scanner;

import javax.swing.SwingUtilities;

//represents Object that starts the program by creating new Manage object for consolue ui, or by creating
//new HockeyHeroGUI object for graphical ui
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Console UI");
        System.out.println("2 - GUI");
        String choice = scanner.nextLine();
        //String choice = "2";
        if (choice.equals("1")) {
            new Manage();
        } else if (choice.equals("2")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new HockeyHeroGUI();
                }
            });
        }
    }
}