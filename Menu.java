import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        // ==== 1. File Reading ====
        String[] books = new String[100];
        int count = 0;
        try {
            File file = new File("books.txt");
            Scanner fileScanner = new Scanner(file);
            // ==== 2. Array Storage ====
            while (fileScanner.hasNextLine() && count < books.length) {
                books[count] = fileScanner.nextLine();
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: books.txt file not found.");
        }
        // ==== Required Menu Options ====
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("=======================");
            System.out.print("==== LIBRARY MENU ====\r\n" + //
                    "1. Display all books.\r\n" + //
                    "2. Add a new book.\r\n" + //
                    "3. Search for a book.\r\n" + //
                    "4. Remove a book.\r\n" + //
                    "5. Count total books.\r\n" + //
                    "6. Save and Exit.\n" + //
                    "Choose an option (1-6) : ");
            choice = input.nextInt();
            switch (choice) {
                // ====== Option 1 ======
                // Display all books with numbers
                case 1:
                    System.out.println("=======================");
                    for (int i = 0; i < books.length; i++) {
                        if (books[i] == null) {
                            break;
                        } else {
                            System.out.println((i + 1) + ". " + books[i]);
                        }

                    }
                    break;
                // ====== Option 2 ======
                // Ask user for book title, add to array
                case 2:
                    System.out.println("=======================");
                    System.out.print("Enter name new book : ");
                    input.nextLine();
                    String newbook = input.nextLine();
                    books[count] = newbook;
                    System.out.println("Your book (" + books[count] + ") was added");
                    count++;
                    break;
                // ====== Option 3 ======
                // Search if a book exists
                case 3:
                    boolean found = false;
                    System.out.println("=======================");
                    System.out.print("Enter name book : ");
                    input.nextLine();
                    String searchbook = input.nextLine();
                    for (int u = 0; u < books.length; u++) {
                        if (books[u] != null && books[u].equalsIgnoreCase(searchbook)) {
                            System.out.println("The Book exist at position " + (u + 1));
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("The Book not exist");
                    }
                    break;

                // ====== Option 4 ======
                // Remove a book
                case 4:
                    System.out.println("=======================");
                    for (int i = 0; i < books.length; i++) {
                        if (books[i] == null) {
                            break;
                        } else {
                            System.out.println((i + 1) + ". " + books[i]);
                        }
                    }
                    System.out.print("Enter position book : ");
                    int removebook = input.nextInt();
                    int w;
                    for (w = 0; w < books.length; w++) {
                        if (w == (removebook - 1)) {
                            for (int y = 0; y < (books.length - removebook); y++) {
                                if (books[y] == null) {
                                    break;
                                } else {
                                    books[removebook - 1 + y] = books[removebook + y];
                                }

                            }

                        }
                    }
                    count--;
                    break;
                // ====== Option 5 ======
                // Show total number of books
                case 5:
                    System.out.println("=======================");
                    System.out.println("Total of number books = " + count);
                    break;
                // ====== Option 6 ======
                // Save current books back to file and exit
                case 6:
                    try {
                        PrintWriter writer = new PrintWriter("books.txt");
                        for (int i = 0; i < count; i++) {
                            writer.println(books[i]);
                        }
                        writer.close();

                    } catch (IOException e) {
                        System.out.println("Error saving file.");
                    }
                    System.out.println("Good bye!");
                    break;
                default:
                    System.out.println("Invalid choose");    
            }
        } while (choice != 6);

    }
}