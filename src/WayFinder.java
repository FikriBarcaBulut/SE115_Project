public class WayFinder {
    private CountryMap map;

    public WayFinder(CountryMap map) {
        this.map = map;
    }

    public void findShortestPath(String startCity, String endCity) {
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
            System.out.println("Start City Not Found.");
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
        printShortestPath(cityNames, distances, previous, startCity, endCity);
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

    private void printShortestPath(String[] cityNames, int[] distances, String[] previous, String startCity, String endCity) {
        int endIndex = getCityIndex(cityNames, endCity);
        if (endIndex == -1 || distances[endIndex] == Integer.MAX_VALUE) {
            System.out.println("No Path Found From " + startCity + " to " + endCity);
            return;
        }

        String path = endCity;
        String currentCity = previous[endIndex];
        while (currentCity != null) {
            path = currentCity + " >>> " + path;
            currentCity = previous[getCityIndex(cityNames, currentCity)];
        }
        System.out.println("Fastest Way: " + path);
        System.out.println("Total Time: " + distances[endIndex] + " min");
    }
}
