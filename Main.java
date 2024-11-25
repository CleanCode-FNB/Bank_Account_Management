import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter Credit Limit (in Rands): ");
        double creditLimit = scanner.nextDouble();

        System.out.print("Enter Interest Rate (in %): ");
        double interestRate = scanner.nextDouble();

        CreditAccount account = new CreditAccount(name, accountNumber, creditLimit, interestRate);
        System.out.println("\nAccount created successfully!\n");

        // Main menu
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Deposit (in Rands)");
            System.out.println("2. Withdraw (in Rands)");
            System.out.println("3. Apply Interest (in Rands)");
            System.out.println("4. Display Account Details (in Rands)");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount (in Rands): ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount (in Rands): ");
                    double withdrawalAmount = scanner.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;
                case 3:
                    account.applyInterest();
                    break;
                case 4:
                    account.displayAccountDetails();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for making use of our services. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
