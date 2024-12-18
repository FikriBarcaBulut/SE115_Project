public class CountryMap {
    private Cities[] allCities;
    private int cityCount;

    public CountryMap(int maxCities) {
        allCities = new Cities[maxCities];
        cityCount = 0;
    }

    public void addCity(String name, int maxNeighbors) {
        if (cityCount < allCities.length) {
            allCities[cityCount] = new Cities(name, maxNeighbors);
            cityCount++;
        }
    }

    public void addRoute(String city1, String city2, int time) {
        Cities c1 = getCity(city1);
        Cities c2 = getCity(city2);
        if (c1 != null && c2 != null) {
            c1.addNeighbor(city2, time);
            c2.addNeighbor(city1, time);
        }
    }

    public Cities getCity(String name) {
        for (int i = 0; i < cityCount; i++) {
            if (allCities[i].getName().equals(name)) {
                return allCities[i];
            }
        }
        return null;
    }

    public Cities[] getAllCities() {
        return allCities;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("CountryMap{allCities=");
        for (int i = 0; i < cityCount; i++) {
            builder.append(allCities[i].getName()).append(" ");
        }
        return builder.toString();
    }
}
