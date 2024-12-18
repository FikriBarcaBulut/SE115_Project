import java.io.*;
import java.util.Scanner;

public class FileReaderDemo {

    private int numCities;
    private String[] cities;
    private String[][] routes;
    private int numRoutes;
    private String startCity;
    private String endCity;

    public boolean readInputFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {

            numCities = Integer.parseInt(scanner.nextLine().trim());
            if (numCities <= 0) {
                throw new IllegalArgumentException("Number of Cities Must Be Positive.");
            }

            cities = scanner.nextLine().trim().split("\\s+");
            if (cities.length != numCities) {
                throw new IllegalArgumentException("City Count Doesn't Match The Number of City Labels Provided.");
            }

            numRoutes = Integer.parseInt(scanner.nextLine().trim());
            if (numRoutes < 0) {
                throw new IllegalArgumentException("Number of Routes Cannot Be Negative.");
            }

            routes = new String[numRoutes][3];
            for (int i = 0; i < numRoutes; i++) {
                if (!scanner.hasNextLine()) {
                    throw new IllegalArgumentException("Insufficient Route Data.");
                }
                String route = scanner.nextLine().trim();
                if (!route.matches("[A-Za-z]\\s+[A-Za-z]\\s+\\d+")) {
                    System.out.println("Error Line " + (i + 4) + ": Invalid Route Format");
                    return false;
                }
                String[] routeParts = route.split("\\s+");
                routes[i][0] = routeParts[0];
                routes[i][1] = routeParts[1];
                routes[i][2] = routeParts[2];
            }

            if (!scanner.hasNextLine()) {
                throw new IllegalArgumentException("Missing Start and End Cities.");
            }
            String[] startEnd = scanner.nextLine().trim().split("\\s+");
            if (startEnd.length != 2) {
                throw new IllegalArgumentException("Start and End Cities Must Be Specified Correctly");
            }
            startCity = startEnd[0];
            endCity = startEnd[1];

            System.out.println("File Read is Successful");
            return true;

        } catch (Exception e) {
            System.out.println("!!!ERROR!!! " + e.getMessage());
        }
        return false;
    }

    public int getNumCities() {
        return numCities;
    }
    public String[] getCities() {
        return cities;
    }
    public String[][] getRoutes() {
        return routes;
    }
    public int getNumRoutes() {
        return numRoutes;
    }
    public String getStartCity() {
        return startCity;
    }
    public String getEndCity() {
        return endCity;
    }

    public static void main(String[] args) {
        Scanner consoleScanner = new Scanner(System.in);

        System.out.print("Enter The File Path: ");
        String fileName = consoleScanner.nextLine();

        FileReaderDemo fileReader = new FileReaderDemo();
        boolean success = fileReader.readInputFile(fileName);

        if (success) {
            System.out.println("Number of Cities: " + fileReader.getNumCities());
            System.out.print("All Cities: ");
            for (String city : fileReader.getCities()) {
                System.out.print(city + " ");
            }
            System.out.println("\nRoutes:");
            String[][] routes = fileReader.getRoutes();
            for (int i = 0; i < fileReader.getNumRoutes(); i++) {
                System.out.printf("%s >>> %s : %s\n", routes[i][0], routes[i][1], routes[i][2]);
            }
            System.out.println("Start City: " + fileReader.getStartCity());
            System.out.println("End City: " + fileReader.getEndCity());
        }

    }
}
