package universityitemverificationsystem2;
import java.util.Scanner;

public class UniversityItemVerificationSystem2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SystemManager systemManager = new SystemManager();

        while (true) {
            System.out.println("--- University Item Verification System ---");
            System.out.println("Select Role:");
            System.out.println("1. Student");
            System.out.println("2. Security Personnel");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            switch (roleChoice) {
                case 1:
                    studentPortal(scanner, systemManager);
                    break;

                case 2:
                    securityPortal(scanner, systemManager);
                    break;

                case 3:
                    System.out.println("Exiting system...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void studentPortal(Scanner scanner, SystemManager systemManager) {
        while (true) {
            System.out.println("\n--- Student Portal ---");
            System.out.println("1. Register User");
            System.out.println("2. Register Item");
            System.out.println("3. Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter father's name: ");
                    String fatherName = scanner.nextLine();
                    System.out.print("Enter department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();  
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();

                    systemManager.registerUser(name, fatherName, department, year, userId);
                    break;

                case 2:
                    System.out.print("Enter item type/category: ");
                    String typeCategory = scanner.nextLine();
                    System.out.print("Enter item model/brand: ");
                    String modelBrand = scanner.nextLine();
                    System.out.print("Enter user ID for the item: ");
                    int itemUserId = scanner.nextInt();
                    scanner.nextLine();

                    systemManager.registerItem(typeCategory, modelBrand, itemUserId);
                    break;

                case 3:
                    return; 
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void securityPortal(Scanner scanner, SystemManager systemManager) {
        while (true) {
            System.out.println("\n--- Security Personnel Portal ---");
            System.out.println("1. Verify Item");
            System.out.println("2. Generate Report");
            System.out.println("3. Display Flagged Items");
            System.out.println("4. Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter user ID: ");
                    int verifyUserId = scanner.nextInt();
                    System.out.print("Enter item ID: ");
                    int itemId = scanner.nextInt();
                    scanner.nextLine();

                    systemManager.verifyItem(verifyUserId, itemId);
                    break;

                case 2:
                    systemManager.generateReport();
                    break;

                case 3:
                    systemManager.displayFlaggedItems();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
