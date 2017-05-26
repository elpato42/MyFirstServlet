import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

class Database {
    private Connection con;

    Database() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:postgresql://194.87.187.238/semenova","semenova", "semenova");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Boolean login(String user, String pass) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
        stmt.setString(1, user);
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            String dbPassword = result.getString("password");
//http://www.md5.cz/
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

    ArrayList<ArrayList<String>> getMessages(String latestN) {
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        ResultSet result;

        try {
            if (latestN != null) {
                PreparedStatement stmt = con.prepareStatement(
                        "SELECT m.*, u.login FROM messages m JOIN users u ON m.user_id = u.id ORDER BY date DESC LIMIT ?"
                );
                stmt.setInt(1, Integer.parseInt(latestN));
                result = stmt.executeQuery();
            } else {
                PreparedStatement stmt = con.prepareStatement(
                        "SELECT m.*, u.login FROM messages m JOIN users u ON m.user_id = u.id WHERE m.date >= ? ORDER BY date DESC"
                );
                stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis() - 1200));
                result = stmt.executeQuery();
            }

            while (result.next()) {
                ArrayList<String> row = new ArrayList<String>();
                row.add(result.getString("login"));
                row.add(result.getString("date"));
                row.add(result.getString("message"));
                results.add(row);
            }

            if (latestN != null) {
                Collections.reverse(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        }

        return results;
    }

    void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable
    {
        try { con.close(); }
        catch (SQLException e) {
            e.printStackTrace();
        }
        super.finalize();
    }
}
