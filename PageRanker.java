import java.util.*;

public class PageRanker {

    private int size;
    private int iteration;
    private double[] pageRank;
    private ArrayList<Node> nodes;
    private SparseMatrix adiacentMatrix;
    private HashMap<Integer, Integer> idToIndex;

    public PageRanker(ArrayList<Node> node, int iteration) {
        this.nodes = node;
        this.size = node.size();
        this.adiacentMatrix = new SparseMatrix(size);
        this.pageRank = new double[size];
        this.iteration = iteration;
        this.idToIndex = getIdToIndex();
        createNormalizedMatrix();
    }

    private void createNormalizedMatrix() {
        long current = System.currentTimeMillis();
        for (int i = 0; i < this.size; i++) {
            Node node = nodes.get(i);
            if (node.linksTo.isEmpty()) {
                for (int s = 0; s < size; s++) {
                    adiacentMatrix.setValue(s, i, this.adiacentMatrix.randomizer);
                }
            }
            int linksToSize = node.linksTo.size();
            double value = 1 / (double) linksToSize;
            int nodeIndex = idToIndex.get(node.id);
            for (int j = 0; j < linksToSize; j++) {
                adiacentMatrix.setValue(idToIndex.get(node.linksTo.get(j)), nodeIndex, value);
            }
        }
        System.out.println("Done Matrix Gen: " + (System.currentTimeMillis() - current));
        calculatePageRank();
    }

    private void calculatePageRank() {
        double defaultValue = 1 / (double) size;
        long current = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            pageRank[i] = defaultValue;
        }
        for (int i = 0; i < this.iteration; i++) {
            pageRank = adiacentMatrix.createVector(pageRank);
        }
        System.out.println("Done PageRank: " + (System.currentTimeMillis() - current));
        setPageRank();
    }

    public void setPageRank() {
        for (int i = 0; i < this.size; i++) {
            double valueWithDamp = pageRank[i];
            nodes.get(i).pageRank = (valueWithDamp);
        }
    }

    public void printAscendent() {
        Collections.sort(nodes,
                (o2, o1) -> o1.pageRank.compareTo(o2.pageRank));
        for (Node p : nodes) {
            String pgRank = String.format("%.17f", p.pageRank);
            System.out.println(p.title + " " + pgRank);
        }
    }

    private HashMap<Integer, Integer> getIdToIndex() {
        HashMap<Integer, Integer> result = new HashMap<>();
        int counter = 0;
        for (Node node : this.nodes) {
            result.put(node.id, counter);
            counter++;
        }
        return result;
    }
}
