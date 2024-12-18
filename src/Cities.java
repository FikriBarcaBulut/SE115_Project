public class Cities {
    private String name;
    private String[] neighbors;
    private int[] travelTimes;
    private int neighborCount;

    public Cities(String name, int maxNeighbors) {
        this.name = name;
        this.neighbors = new String[maxNeighbors];
        this.travelTimes = new int[maxNeighbors];
        this.neighborCount = 0;
    }

    public void addNeighbor(String neighbor, int time) {
        if (neighborCount < neighbors.length) {
            neighbors[neighborCount] = neighbor;
            travelTimes[neighborCount] = time;
            neighborCount++;
        }
    }

    public String getName() {
        return name;
    }
    public String[] getNeighbors() {
        return neighbors;
    }
    public int[] getTravelTimes() {
        return travelTimes;
    }
    public int getNeighborCount() {
        return neighborCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("City Info --> Name of The City = '" + name + "' --> Neighbor Cities = ");
        for (int i = 0; i < neighborCount; i++) {
            builder.append(neighbors[i]).append(" (").append(travelTimes[i]).append(" min) | ");
        }
        return builder.toString();
    }
}
