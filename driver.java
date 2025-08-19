import java.util.*;
import java.util.stream.Collectors;

public class driver {
    private static List<Book> books;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("    AMAZON BESTSELLING BOOKS ANALYSIS SYSTEM    ");
        System.out.println("=".repeat(60));
        
        // Load the dataset
        books = DatasetReader.readDataset("data.csv");
        
        if (books.isEmpty()) {
            System.out.println("Error: No books loaded from dataset. Please check the data.csv file.");
            return;
        }
        
        System.out.println("Dataset loaded successfully! Total books: " + books.size());
        System.out.println();
        
        // Main menu loop
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    countBooksByAuthor();
                    break;
                case 2:
                    listAllAuthors();
                    break;
                case 3:
                    listBooksByAuthor();
                    break;
                case 4:
                    classifyByUserRating();
                    break;
                case 5:
                    priceOfBooksByAuthor();
                    break;
                case 6:
                    displayDatasetStatistics();
                    break;
                case 7:
                    System.out.println("Thank you for using Amazon Bestselling Books Analysis System!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number from 1-7.");
            }
            
            System.out.println("\nPress Enter to continue...");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    MAIN MENU                    ");
        System.out.println("=".repeat(60));
        System.out.println("1. Count total books by an author");
        System.out.println("2. List all authors in the dataset");
        System.out.println("3. List all books by a specific author");
        System.out.println("4. Classify books by user rating");
        System.out.println("5. Get prices of all books by an author");
        System.out.println("6. Display dataset statistics");
        System.out.println("7. Exit");
        System.out.println("=".repeat(60));
        System.out.print("Enter your choice (1-7): ");
    }
    
    private static int getUserChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // Task 1: Total number of books by an author
    private static void countBooksByAuthor() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        COUNT BOOKS BY AUTHOR        ");
        System.out.println("=".repeat(50));
        
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine().trim();
        
        long count = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(authorName))
                .count();
        
        System.out.println("\nResult:");
        System.out.println("Author: " + authorName);
        System.out.println("Total books: " + count);
        
        if (count == 0) {
            System.out.println("No books found for this author. Please check the spelling.");
        }
    }
    
    // Task 2: All the authors in the dataset
    private static void listAllAuthors() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        ALL AUTHORS IN DATASET        ");
        System.out.println("=".repeat(50));
        
        Set<String> authors = books.stream()
                .map(Book::getAuthor)
                .collect(Collectors.toCollection(TreeSet::new));
        
        System.out.println("\nTotal unique authors: " + authors.size());
        System.out.println("\nAuthors list:");
        System.out.println("-".repeat(40));
        
        int count = 1;
        for (String author : authors) {
            System.out.printf("%3d. %s%n", count++, author);
        }
    }
    
    // Task 3: Names of all books by an author
    private static void listBooksByAuthor() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        BOOKS BY AUTHOR        ");
        System.out.println("=".repeat(50));
        
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine().trim();
        
        List<Book> authorBooks = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
        
        System.out.println("\nResult:");
        System.out.println("Author: " + authorName);
        System.out.println("Total books: " + authorBooks.size());
        
        if (authorBooks.isEmpty()) {
            System.out.println("No books found for this author. Please check the spelling.");
        } else {
            System.out.println("\nBooks:");
            System.out.println("-".repeat(40));
            for (int i = 0; i < authorBooks.size(); i++) {
                System.out.printf("%3d. %s (%d)%n", 
                    i + 1, 
                    authorBooks.get(i).getTitle(), 
                    authorBooks.get(i).getYear());
            }
        }
    }
    
    // Task 4: Classify with a user rating
    private static void classifyByUserRating() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        CLASSIFY BY USER RATING        ");
        System.out.println("=".repeat(50));
        
        System.out.print("Enter user rating (e.g., 4.5): ");
        try {
            double rating = Double.parseDouble(scanner.nextLine().trim());
            
            List<Book> ratedBooks = books.stream()
                    .filter(book -> book.getUserRating() == rating)
                    .collect(Collectors.toList());
            
            System.out.println("\nResult:");
            System.out.println("Rating: " + rating);
            System.out.println("Total books with this rating: " + ratedBooks.size());
            
            if (ratedBooks.isEmpty()) {
                System.out.println("No books found with rating " + rating);
            } else {
                System.out.println("\nBooks with rating " + rating + ":");
                System.out.println("-".repeat(60));
                for (int i = 0; i < ratedBooks.size(); i++) {
                    System.out.printf("%3d. %s by %s (%d)%n", 
                        i + 1, 
                        ratedBooks.get(i).getTitle(),
                        ratedBooks.get(i).getAuthor(),
                        ratedBooks.get(i).getYear());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating format. Please enter a decimal number (e.g., 4.5).");
        }
    }
    
    // Task 5: Price of all books by an author
    private static void priceOfBooksByAuthor() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        BOOK PRICES BY AUTHOR        ");
        System.out.println("=".repeat(50));
        
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine().trim();
        
        List<Book> authorBooks = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
        
        System.out.println("\nResult:");
        System.out.println("Author: " + authorName);
        System.out.println("Total books: " + authorBooks.size());
        
        if (authorBooks.isEmpty()) {
            System.out.println("No books found for this author. Please check the spelling.");
        } else {
            double totalPrice = 0;
            System.out.println("\nBooks and Prices:");
            System.out.println("-".repeat(60));
            System.out.printf("%-40s %s%n", "Book Title", "Price");
            System.out.println("-".repeat(60));
            
            for (Book book : authorBooks) {
                System.out.printf("%-40s $%d%n", 
                    truncateString(book.getTitle(), 40), 
                    book.getPrice());
                totalPrice += book.getPrice();
            }
            
            System.out.println("-".repeat(60));
            System.out.printf("%-40s $%.2f%n", "TOTAL PRICE:", totalPrice);
            System.out.printf("%-40s $%.2f%n", "AVERAGE PRICE:", totalPrice / authorBooks.size());
        }
    }
    
    // Additional feature: Dataset statistics
    private static void displayDatasetStatistics() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        DATASET STATISTICS        ");
        System.out.println("=".repeat(50));
        
        // Basic stats
        System.out.println("Dataset Overview:");
        System.out.println("-".repeat(30));
        System.out.println("Total books: " + books.size());
        
        Set<String> uniqueAuthors = books.stream()
                .map(Book::getAuthor)
                .collect(Collectors.toSet());
        System.out.println("Unique authors: " + uniqueAuthors.size());
        
        // Year range
        IntSummaryStatistics yearStats = books.stream()
                .mapToInt(Book::getYear)
                .summaryStatistics();
        System.out.println("Year range: " + yearStats.getMin() + " - " + yearStats.getMax());
        
        // Rating statistics
        DoubleSummaryStatistics ratingStats = books.stream()
                .mapToDouble(Book::getUserRating)
                .summaryStatistics();
        System.out.printf("Average rating: %.2f%n", ratingStats.getAverage());
        System.out.printf("Rating range: %.1f - %.1f%n", ratingStats.getMin(), ratingStats.getMax());
        
        // Price statistics
        IntSummaryStatistics priceStats = books.stream()
                .mapToInt(Book::getPrice)
                .summaryStatistics();
        System.out.printf("Average price: $%.2f%n", priceStats.getAverage());
        System.out.println("Price range: $" + priceStats.getMin() + " - $" + priceStats.getMax());
        
        // Genre distribution
        Map<String, Long> genreCount = books.stream()
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.counting()));
        
        System.out.println("\nGenre Distribution:");
        System.out.println("-".repeat(30));
        genreCount.forEach((genre, count) -> 
            System.out.printf("%-15s: %d books (%.1f%%)%n", 
                genre, count, (count * 100.0) / books.size()));
    }
    
    // Helper method to truncate strings for better formatting
    private static String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}