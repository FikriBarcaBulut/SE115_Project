import java.io.FileWriter;
import java.io.IOException;

public class WayFinder {
    private CountryMap map;

    public WayFinder(CountryMap map) {
        this.map = map;
    }

    public void findShortestPath(String startCity, String endCity, String outputFile) {
        int numCities = map.getAllCities().length;
        String[] cityNames = new String[numCities];
        int[] distances = new int[numCities];
        boolean[] visited = new boolean[numCities];
        String[] previous = new String[numCities];

        for (int i = 0; i < numCities; i++) {
            Cities city = map.getAllCities()[i];
            cityNames[i] = city.getName();
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
            previous[i] = null;
        }

        int startIndex = getCityIndex(cityNames, startCity);
        if (startIndex == -1) {
            System.out.println("Start city not found.");
            return;
        }
        distances[startIndex] = 0;

        for (int i = 0; i < numCities; i++) {
            int currentCityIndex = getClosestCityIndex(distances, visited);
            if (currentCityIndex == -1) break;
            visited[currentCityIndex] = true;
            Cities currentCity = map.getAllCities()[currentCityIndex];
            for (int j = 0; j < currentCity.getNeighborCount(); j++) {
                String neighborName = currentCity.getNeighbors()[j];
                int travelTime = currentCity.getTravelTimes()[j];
                int neighborIndex = getCityIndex(cityNames, neighborName);

                if (!visited[neighborIndex]) {
                    int newDist = distances[currentCityIndex] + travelTime;
                    if (newDist < distances[neighborIndex]) {
                        distances[neighborIndex] = newDist;
                        previous[neighborIndex] = currentCity.getName();
                    }
                }
            }
        }
        writeEverythingToFile(cityNames, distances, previous, startCity, endCity, outputFile);
    }

    private int getCityIndex(String[] cityNames, String cityName) {
        for (int i = 0; i < cityNames.length; i++) {
            if (cityNames[i].equals(cityName)) {
                return i;
            }
        }
        return -1;
    }

    private int getClosestCityIndex(int[] distances, boolean[] visited) {
        int minDist = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDist) {
                minDist = distances[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private void writeEverythingToFile(String[] cityNames, int[] distances, String[] previous, String startCity, String endCity, String outputFile) {
        int endIndex = getCityIndex(cityNames, endCity);
        StringBuilder outputBuilder = new StringBuilder();

        outputBuilder.append("========================================\n");
        outputBuilder.append("              MAP STRUCTURE             \n");
        outputBuilder.append("========================================\n");

        for (Cities city : map.getAllCities()) {
            if (city != null) {
                outputBuilder.append(city).append("\n");
            }
        }

        outputBuilder.append("========================================\n");

        if (endIndex == -1 || distances[endIndex] == Integer.MAX_VALUE) {
            outputBuilder.append("No Path Found From ").append(startCity).append(" to ").append(endCity).append("\n");
        } else {
            StringBuilder pathBuilder = new StringBuilder(endCity);
            String currentCity = previous[endIndex];
            while (currentCity != null) {
                pathBuilder.insert(0, currentCity + " -> ");
                currentCity = previous[getCityIndex(cityNames, currentCity)];
            }

            String path = pathBuilder.toString();
            int totalTime = distances[endIndex];

            outputBuilder.append("========================================\n");
            outputBuilder.append("              FASTEST ROUTE             \n");
            outputBuilder.append("========================================\n");
            outputBuilder.append("Start City       : ").append(startCity).append("\n");
            outputBuilder.append("End City         : ").append(endCity).append("\n");
            outputBuilder.append("Fastest Route    : ").append(path).append("\n");
            outputBuilder.append("Total Travel Time: ").append(totalTime).append(" min\n");
            outputBuilder.append("========================================\n");
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(outputBuilder.toString());
            System.out.println("Result Written to " + outputFile);
        } catch (IOException e) {
            System.out.println("Error Writing to File: " + e.getMessage());
        }
    }
}
