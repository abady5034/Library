import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class LibraryGUI extends Application {

    private String[] books = new String[100];
    private int count = 0;

    @Override
    public void start(Stage primaryStage) {
        // ==== Load books from file ====
        try {
            File file = new File("books.txt");
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine() && count < books.length) {
                books[count] = fileScanner.nextLine();
                count++;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("books.txt not found, starting with empty library.");
        }

        primaryStage.setTitle("Library Menu");

        VBox root = new VBox(15);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e); -fx-padding: 20;");

        Label title = new Label("==== LIBRARY MENU ====");
        title.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 24px; -fx-font-weight: bold;");

        Button btnDisplay = new Button("1. Display all books");
        Button btnAdd = new Button("2. Add a new book");
        Button btnSearch = new Button("3. Search for a book");
        Button btnRemove = new Button("4. Remove a book");
        Button btnCount = new Button("5. Count total books");
        Button btnSaveExit = new Button("6. Save and Exit");

        Button[] buttons = {btnDisplay, btnAdd, btnSearch, btnRemove, btnCount, btnSaveExit};
        for (Button b : buttons) {
            b.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
            b.setMaxWidth(Double.MAX_VALUE);
        }

        root.getChildren().addAll(title, btnDisplay, btnAdd, btnSearch, btnRemove, btnCount, btnSaveExit);

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // ===== Button Actions =====
        btnDisplay.setOnAction(e -> showAlert("All Books", getBooksList()));

        btnAdd.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Book");
            dialog.setHeaderText("Enter new book name:");
            dialog.setContentText("Book:");
            dialog.showAndWait().ifPresent(name -> {
                if (count < books.length) {
                    String before = getBooksList(); // الكتب قبل الإضافة
                    books[count] = name;
                    count++;
                    String after = getBooksList(); // الكتب بعد الإضافة
                    showAlert("Book Added", "=== Books Before Adding ===\n" + before + "\n=== Books After Adding ===\n" + after);
                } else {
                    showAlert("Error", "Library is full.");
                }
            });
        });

        btnSearch.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Search Book");
            dialog.setHeaderText("Enter book name to search:");
            dialog.setContentText("Book:");
            dialog.showAndWait().ifPresent(name -> {
                boolean found = false;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < count; i++) {
                    if (books[i].equalsIgnoreCase(name)) {
                        sb.append("Book found at position " + (i + 1));
                        found = true;
                        break;
                    }
                }
                if (!found) sb.append("Book not found.");
                showAlert("Search Result", sb.toString());
            });
        });

        btnRemove.setOnAction(e -> {
            if (count == 0) {
                showAlert("No Books", "لا يوجد كتب للحذف.");
                return;
            }

            // عرض جميع الكتب قبل الحذف
            showAlert("All Books", getBooksList());

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Remove Book");
            dialog.setHeaderText("Enter book position to remove:");
            dialog.setContentText("Position:");
            dialog.showAndWait().ifPresent(posStr -> {
                try {
                    int pos = Integer.parseInt(posStr) - 1;
                    if (pos >= 0 && pos < count) {
                        String before = getBooksList(); // الكتب قبل الحذف
                        String removed = books[pos];
                        for (int i = pos; i < count - 1; i++) {
                            books[i] = books[i + 1];
                        }
                        books[count - 1] = null;
                        count--;
                        String after = getBooksList(); // الكتب بعد الحذف
                        showAlert("Book Removed", "Removed: " + removed + "\n\n=== Books Before Removing ===\n" + before + "\n=== Books After Removing ===\n" + after);
                    } else {
                        showAlert("Error", "Invalid position.");
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Invalid input.");
                }
            });
        });

        btnCount.setOnAction(e -> showAlert("Total Books", "Total number of books: " + count));

        btnSaveExit.setOnAction(e -> {
            try {
                PrintWriter writer = new PrintWriter("books.txt");
                for (int i = 0; i < count; i++) {
                    writer.println(books[i]);
                }
                writer.close();
            } catch (IOException ex) {
                showAlert("Error", "Failed to save books to file.");
            }
            primaryStage.close();
        });
    }

    private String getBooksList() {
        if (count == 0) return "No books available.";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append((i + 1) + ". " + books[i] + "\n");
        }
        return sb.toString();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
