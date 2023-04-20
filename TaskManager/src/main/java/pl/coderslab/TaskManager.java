package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args){

            Path path = Paths.get("tasks.csv");
            String[] linesOf_CSV_Files = read_CSV_File(path);

             String[][] splitArray = splitLinesOf_CSV_File(path);
             String[][] removeOfArray;
             //removeOfArray = new String[][];
        //String[][] splitArray = removeOfArray[][];


            //printTasks(splitArray);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "exit":
                    saveTabToFile(String.valueOf(path), splitArray);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                case "add":
                    splitArray = addTask(splitArray);

                    break;
                case "remove":
                    splitArray = removeTask(splitArray, getTheNumber());
                    System.out.println("Value was successfully deleted.");
                    break;
                case "list":
                    printTasks(splitArray);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            printOptions();
        }

    }

    public static void printOptions(){

        String[] Options = {"Please select an option:", "add", "remove", "list", "exit"};
        for (int i = 0; i < Options.length; i++) {
            if(i ==0){
              System.out.println(ConsoleColors.BLUE + Options[i]);

            } else {
                System.out.println(ConsoleColors.WHITE + Options[i]);
            }
        }

    }

    public static  String[] read_CSV_File(Path path){

        try {
            String[] linesOf_CSV_File = Files.readAllLines(path).toArray(new String[0]);
            return linesOf_CSV_File;

        } catch (IOException e) {
            e.printStackTrace();
            String[] errorOfReading = {"błąd czytania z pliku"};
            String[] errorOfReading1 = errorOfReading;
            return errorOfReading1;

        }

    }

    public static String[][] splitLinesOf_CSV_File(Path path){

        String[] linesOf_CSV_File = read_CSV_File(path);
        String[][] tasks = new String[linesOf_CSV_File.length][linesOf_CSV_File[linesOf_CSV_File.length-1].split(", ").length];
        for (int i = 0; i < linesOf_CSV_File.length ; i++) {

              String[] split =linesOf_CSV_File[i].toString().split(", ");

            for (int j = 0; j < linesOf_CSV_File[i].split(", ").length; j++) {

                    tasks[i][j] = split[j];
            }

        }

        return tasks;
    }

    public static void printTasks(String[][] tasks){

        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");

            for (int j = 0; j < tasks[i].length; j++) {

                System.out.print((tasks[i][j] + " "));
            }
            System.out.println();
        }

    }


    private static String[][] addTask(String[][] splitArray){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scanner.nextLine();
        //String[][] tasks = new String[0][0];
        splitArray =  Arrays.copyOf(splitArray, splitArray.length + 1);
        splitArray[splitArray.length-1] = new String[3];
        splitArray[splitArray.length-1][0] = description;
        splitArray[splitArray.length-1][1] = dueDate;
        splitArray[splitArray.length-1][2] = isImportant;

        //System.out.println("splitArray = " + splitArray);

        return splitArray;

    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }


    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        String n = scanner.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            scanner.nextLine();
        }
        return Integer.parseInt(n);
    }
    private static String[][] removeTask(String[][] tasks, int index) {

        try {
            if (index < tasks.length) {
                tasks = ArrayUtils.remove(tasks, index);

                //return tasks;
            }

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");
        }

        return tasks;
    }

    public static void saveTabToFile(String fileName, String[][] tasks) {
        Path dir = Paths.get(fileName);
        String[] lines = new String[tasks.length];

        for (int i = 0; i < tasks.length; i++) {
            lines[i] = String.join(",", tasks[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }


}


