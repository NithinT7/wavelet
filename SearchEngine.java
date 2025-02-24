import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> words = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                words.add(parameters[1]);
            }
            return String.format("Word added!");
        } 
        else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            String found ="";
            if (parameters[0].equals("s")) {
                for (int i = 0; i < words.size(); i++) {
                    if (words.get(i).contains(parameters[1])) {
                        found += words.get(i) + " ";
                    }
                }
                return found;
            }
        }
        return "404 Not Found!";
    }
}
public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
