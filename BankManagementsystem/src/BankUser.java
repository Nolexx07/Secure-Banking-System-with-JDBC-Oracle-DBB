public class BankUser {

    public static void main(String[] args) {

        boolean check = true;

        while (check) {
            System.out.println("1.Register\n2.Login\n3.Exit");
            int ch = Controller.sc.nextInt();

            switch (ch) {

                case 1 -> Controller.doRegister();

                case 2 -> {
                    if (Controller.isLoginSuccess()) {

                        boolean log = true;

                        while (log) {
                            System.out.println("""
                            1.Open Account
                            2.Passbook
                            3.Check Balance
                            4.Transfer
                            5.Generate Pin
                            6.Change Pin
                            7.Logout
                            8.Transaction History
                            """);

                            int c = Controller.sc.nextInt();

                            switch (c) {
                                case 1 -> Controller.createAccount();
                                case 2 -> Controller.passbook();
                                case 3 -> Controller.CheckBalance();
                                case 4 -> Controller.Moneytransfer();
                                case 5 -> Controller.GeneratePin();
                                case 6 -> Controller.ChangePin();
                                case 7 -> Controller.viewTransactions();
                                case 8 -> log = false;
                            }
                        }

                    } else {
                        System.out.println("Invalid Login");
                    }
                }

                case 3 -> {
                    check = false;
                    System.out.println("Thank You");
                }
            }
        }
    }
}