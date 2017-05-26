import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Database {
    Boolean login(String user, String pass) {
        return true;
    }

    Boolean createMessage(String message, String user) {
        return true;
    }

    ArrayList<ArrayList<String>> getMessages() {
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        ArrayList<String> row = new ArrayList<String>();

        row.add("Vasya");
        row.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        row.add("Fake message");
        results.add(row);

        return results;
    }
}
