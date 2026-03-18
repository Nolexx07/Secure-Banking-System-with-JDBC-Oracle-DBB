import java.sql.*;
import java.util.*;

public class Controller {

    public static Scanner sc = new Scanner(System.in);

    // ✅ Multiple users
    public static HashMap<String, Bank> users = new HashMap<>();
    public static Bank currentUser = null;

    static int c = 1;

    // DB Connection
    static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    static final String USER = "orcl";
    static final String PASS = "1234";

    public static void doRegister() {
        System.out.println("Enter ID: ");
        String id = sc.next();

        if (users.containsKey(id)) {
            System.out.println("User already exists!");
            return;
        }

        System.out.println("Enter Password: ");
        String pwd = sc.next();

        Bank b = new Bank(id, pwd);
        users.put(id, b);

        System.out.println("Registration Successful...");
    }

    public static boolean isLoginSuccess() {
        System.out.print("Enter login ID: ");
        String id = sc.next();
        System.out.print("Enter Password: ");
        String pwd = sc.next();

        if (users.containsKey(id)) {
            Bank b = users.get(id);

            if (b.getPassword().equals(pwd)) {
                currentUser = b;
                return true;
            }
        }
        return false;
    }

    public static void createAccount() {
        Bank b = currentUser;

        System.out.print("Name: ");
        b.setName(sc.next());

        System.out.print("Phone: ");
        b.setPhno(sc.nextLong());

        System.out.print("DOB: ");
        b.setDob(sc.next());

        System.out.print("Address: ");
        sc.nextLine();
        b.setAddress(sc.nextLine());

        b.setAcno(generateAccount());
        b.setBalance(14500);
        b.setIfsc("JSP0010082");

        System.out.println("Account Created Successfully...");
    }

    // ✅ FIXED METHOD
    public static String generateAccount() {
        String acc = "18425";
        long no = 867654 * ++c;

        int i = 0;
        while (i < 6) {
            acc += no % 10;
            no /= 10;
            i++;
        }
        return acc;
    }

    public static void passbook() {
        Bank b = currentUser;

        System.out.println("-----PASSBOOK-----");
        System.out.println("Name: " + b.getName());
        System.out.println("Acc No: " + b.getAcno());
        System.out.println("Balance: " + b.getBalance());
        System.out.println("IFSC: " + b.getIfsc());
    }

    public static void CheckBalance() {
        Bank b = currentUser;

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        if (pin == b.getPin()) {
            System.out.println("Balance: " + b.getBalance());
        } else {
            System.out.println("Invalid PIN");
        }
    }

    // ✅ JDBC Transaction Save
    public static void saveTransaction(String type, double amount) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String sql = "INSERT INTO transactions(username, type, amount) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, currentUser.getId());
            ps.setString(2, type);
            ps.setDouble(3, amount);

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }

    public static void Moneytransfer() {
        Bank b = currentUser;

        System.out.println("1. Send\n2. Receive");
        int ch = sc.nextInt();

        switch (ch) {

            case 1 -> {
                System.out.print("Amount: ");
                double amt = sc.nextDouble();

                System.out.print("PIN: ");
                int pin = sc.nextInt();

                if (pin == b.getPin()) {
                    if (amt <= b.getBalance()) {
                        b.setBalance(b.getBalance() - amt);

                        saveTransaction("DEBIT", amt);

                        System.out.println("Transfer Successful");
                    } else {
                        System.out.println("Insufficient Balance");
                    }
                }
            }

            case 2 -> {
                System.out.print("Enter Acc No: ");
                String acc = sc.next();

                if (acc.equals(b.getAcno())) {
                    System.out.print("Amount: ");
                    double amt = sc.nextDouble();

                    b.setBalance(b.getBalance() + amt);

                    saveTransaction("CREDIT", amt);

                    System.out.println("Money Received");
                }
            }
        }
    }

    public static void GeneratePin() {
        Bank b = currentUser;

        System.out.print("Acc No: ");
        String acc = sc.next();

        System.out.print("Phone: ");
        long ph = sc.nextLong();

        if (acc.equals(b.getAcno()) && ph == b.getPhno()) {

            System.out.print("New PIN: ");
            int p1 = sc.nextInt();

            System.out.print("Confirm PIN: ");
            int p2 = sc.nextInt();

            if (p1 == p2) {
                b.setPin(p1);
                System.out.println("PIN Generated");
            }
        }
    }

    public static void ChangePin() {
        Bank b = currentUser;

        System.out.print("Current PIN: ");
        int pin = sc.nextInt();

        if (pin == b.getPin()) {
            System.out.print("New PIN: ");
            b.setPin(sc.nextInt());
            System.out.println("PIN Changed");
        }
    }

    public static void viewTransactions() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String sql = "SELECT * FROM transactions WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, currentUser.getId());

            ResultSet rs = ps.executeQuery();

            System.out.println("----Transaction History----");

            while (rs.next()) {
                System.out.println(
                        rs.getString("type") + " | " +
                                rs.getDouble("amount") + " | " +
                                rs.getTimestamp("trans_date")
                );
            }

            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}