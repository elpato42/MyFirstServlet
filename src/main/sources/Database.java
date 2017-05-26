import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

class Database {
    private Connection con;

    Database() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://194.87.187.238/semenova","semenova", "semenova");
    }

    Boolean login(String user, String pass) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
        stmt.setString(1, user);
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            String dbPassword = result.getString("password");

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(pass));
            String checkPass = String.format("%032x", new BigInteger(1, md5.digest()));

            return dbPassword.equals(checkPass);
        }

        return false;
    }

    Boolean createMessage(String message, String user) {
        PreparedStatement select = null;
        Integer userId;

        try {
            select = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            select.setString(1, user);
            ResultSet result = select.executeQuery();
            if (result.next()) {
                userId = result.getInt("id");
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try {
            PreparedStatement insert = con.prepareStatement(
                "INSERT INTO messages (user_id, date, message) VALUES (?, ?, ?)"
            );
            insert.setInt(1, userId);
            insert.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            insert.setString(3, message);
            insert.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    ArrayList<ArrayList<String>> getMessages() {
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();

        try {
            ResultSet result = con.prepareStatement(
                "SELECT m.*, u.login FROM messages m JOIN users u ON m.user_id = u.id ORDER BY date ASC"
            ).executeQuery();

            while (result.next()) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(result.getString("login"));
                row.add(result.getString("date"));
                row.add(result.getString("message"));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        }

        return results;
    }
}
