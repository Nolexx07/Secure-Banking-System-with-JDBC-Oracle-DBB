public class Bank {

    private String id;
    private String password;
    private String name;
    private long phno;
    private String dob;
    private String address;
    private String acno;
    private double balance;
    private String ifsc;
    private int pin;

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getPhno() { return phno; }
    public void setPhno(long phno) { this.phno = phno; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAcno() { return acno; }
    public void setAcno(String acno) { this.acno = acno; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getIfsc() { return ifsc; }
    public void setIfsc(String ifsc) { this.ifsc = ifsc; }

    public int getPin() { return pin; }
    public void setPin(int pin) { this.pin = pin; }

    public Bank(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Bank() {}
}