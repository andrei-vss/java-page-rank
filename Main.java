import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        String fileLocation =System.getProperty("user.dir") + "\\src\\data.txt";

        TextReader reader = new TextReader(fileLocation);
        try {
            reader.readNodes();
            reader.readEdges();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PageRanker rankMe = new PageRanker(reader.getCompleteNodes(), 1);
        rankMe.printAscendent();

        PageRanker rankMe2 = new PageRanker(reader.getCompleteNodes(), 40);
        rankMe2.printAscendent();
    }
}
