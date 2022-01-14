import java.util.ArrayList;

public class Node {

    public int id;
    public String title;
    public Double pageRank;
    public ArrayList<Integer> linksTo;

    public Node(int id, String title) {
        this.id = id;
        this.title = title;
        this.pageRank = null;
        this.linksTo = new ArrayList<>();
    }
}


