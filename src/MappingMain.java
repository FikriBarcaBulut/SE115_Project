public class MappingMain {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You Must Enter a File Name");
            return;
        }

        FileReaderDemo fileReader = new FileReaderDemo();
        if (!fileReader.readInputFile(args[0])) {
            System.out.println("Error Reading The Input File");
            return;
        }

        int numCities = fileReader.getNumCities();
        CountryMap map = new CountryMap(numCities);
        String[] cities = fileReader.getCities();
        for (int i = 0; i < numCities; i++) {
            map.addCity(cities[i], 10);
        }

        String[][] routes = fileReader.getRoutes();
        for (int i = 0; i < routes.length; i++) {
            String city1 = routes[i][0];
            String city2 = routes[i][1];
            int time = Integer.parseInt(routes[i][2]);
            map.addRoute(city1, city2, time);
        }

        WayFinder wayFinder = new WayFinder(map);
        wayFinder.findShortestPath(fileReader.getStartCity(), fileReader.getEndCity());
    }
}
