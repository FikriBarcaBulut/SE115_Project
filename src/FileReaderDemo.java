import java.io.BufferedReader;
import java.io.FileReader;

public class FileReaderDemo {

    private int numCities;
    private String[] cities;
    private String[][] routes;
    private int numRoutes;
    private String startCity;
    private String endCity;

    public boolean readInputFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            numCities = Integer.parseInt(reader.readLine().trim());
            if (numCities <= 0) {
                throw new IllegalArgumentException("Number of Cities Must Be Positive.");
            }
            cities = reader.readLine().trim().split("\\s+");
            if (cities.length != numCities) {
                throw new IllegalArgumentException("City Count Doesn't Match The Number of City Labels Provided.");
            }
            numRoutes = Integer.parseInt(reader.readLine().trim());
            if (numRoutes < 0) {
                throw new IllegalArgumentException("Number of Routes Cannot Be Negative.");
            }

            routes = new String[numRoutes][3];
            for (int i = 0; i < numRoutes; i++) {
                String route = reader.readLine().trim();
                if (!route.matches("[A-Za-z]\\s+[A-Za-z]\\s+\\d+")) {
                    System.out.println("Error Line " + (i + 4) + ": Invalid Route Format");
                    return false;
                }
                String[] routeParts = route.split("\\s+");
                routes[i][0] = routeParts[0];
                routes[i][1] = routeParts[1];
                routes[i][2] = routeParts[2];
            }

            String[] startEnd = reader.readLine().trim().split("\\s+");
            if (startEnd.length != 2) {
                throw new IllegalArgumentException("Start and End Cities Must Be Specified Correctly");
            }
            startCity = startEnd[0];
            endCity = startEnd[1];

            System.out.println("File Read is Successful");
            return true;

        } catch (Exception e) {
            System.out.println("!!!ERROR!!!");
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

        String fileName = "C:\\Users\\barca\\OneDrive\\Masaüstü\\de12\\SE115_Project\\src\\map1.txt";
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
                System.out.printf("%s -> %s : %s\n", routes[i][0], routes[i][1], routes[i][2]);
            }
            System.out.println("Start City: " + fileReader.getStartCity());
            System.out.println("End City: " + fileReader.getEndCity());
        }
    }
}