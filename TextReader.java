import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class TextReader {

    private String fileLocation;
    private ArrayList<Node> nodes;
    private HashMap<Integer, ArrayList<Integer>> rawData;
    private HashMap<Integer, Integer> idToIndex;


    public TextReader(String fileLocation) {
        this.idToIndex = new HashMap<>();
        this.fileLocation = fileLocation;
        this.nodes = new ArrayList<>();
        this.rawData = new HashMap<>();
    }

    public void readNodes() throws IOException {
        BufferedReader bufRead = Files.newBufferedReader(Paths.get(fileLocation));
        String myLine;
        Boolean start = false;

        while ((myLine = bufRead.readLine()) != null) {
            if (myLine.equals("]nodes")) {
                break;
            }
            if (start) {
                String[] value = myLine.split("\t");
                nodes.add(new Node(Integer.valueOf(value[0]), value[1]));
                idToIndex.put(Integer.valueOf(value[0]), nodes.size() - 1);
            }
            if (myLine.equals("nodes[")) {
                start = true;
            }
        }
    }

    public void readEdges() throws IOException {
        FileReader input = new FileReader(new File(fileLocation));
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
        Boolean start = false;

        while ((myLine = bufRead.readLine()) != null) {
            if (myLine.equals("]edges")) {
                break;
            }
            if (start) {
                String[] value = myLine.split("\t");
                if (idToIndex.containsKey(Integer.parseInt(value[0])) && idToIndex.containsKey(Integer.parseInt(value[1]))){
                    if (rawData.containsKey(Integer.parseInt(value[0]))) {
                        ArrayList<Integer> linksTo = rawData.get(Integer.parseInt(value[0]));
                        linksTo.add(Integer.parseInt(value[1]));
                    } else {
                        ArrayList<Integer> linksTo = new ArrayList<>();
                        linksTo.add(Integer.parseInt(value[1]));
                        rawData.put(Integer.valueOf(value[0]), linksTo);
                    }
                }
            }
            if (myLine.equals("edges[")) {
                start = true;
            }
        }
    }

    public ArrayList<Node> getCompleteNodes() {
        for (Node node : nodes) {
            if (rawData.containsKey(node.id)) {
                node.linksTo = (rawData.get(node.id));
            }
        }
        return nodes;
    }

    public HashMap<Integer, Integer> getIdToIndex() {
        return idToIndex;
    }
}
