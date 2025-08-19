import java.util.*;
import java.util.stream.Collectors;

public class TestDriver {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("    TESTING AMAZON BESTSELLING BOOKS SYSTEM    ");
        System.out.println("=".repeat(60));
        
        // Load the dataset
        List<Book> books = DatasetReader.readDataset("data.csv");
        
        if (books.isEmpty()) {
            System.out.println("Error: No books loaded from dataset.");
            return;
        }
        
        System.out.println("Dataset loaded successfully! Total books: " + books.size());
        System.out.println();
        
        // Test 1: Count books by author
        System.out.println("TEST 1: Count books by George R. R. Martin");
        long count = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase("George R. R. Martin"))
                .count();
        System.out.println("Result: " + count + " books");
        System.out.println();
        
        // Test 2: List all authors
        System.out.println("TEST 2: List first 10 authors");
        Set<String> authors = books.stream()
                .map(Book::getAuthor)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("Total unique authors: " + authors.size());
        authors.stream().limit(10).forEach(author -> System.out.println("- " + author));
        System.out.println();
        
        // Test 3: List books by specific author
        System.out.println("TEST 3: Books by J.K. Rowling");
        List<Book> rowlingBooks = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase("J.K. Rowling"))
                .collect(Collectors.toList());
        System.out.println("Found " + rowlingBooks.size() + " books:");
        rowlingBooks.forEach(book -> System.out.println("- " + book.getTitle()));
        System.out.println();
        
        // Test 4: Classify by rating 4.7
        System.out.println("TEST 4: Books with rating 4.7");
        List<Book> ratedBooks = books.stream()
                .filter(book -> book.getUserRating() == 4.7)
                .collect(Collectors.toList());
        System.out.println("Found " + ratedBooks.size() + " books with rating 4.7:");
        ratedBooks.stream().limit(5).forEach(book -> 
            System.out.println("- " + book.getTitle() + " by " + book.getAuthor()));
        System.out.println();
        
        // Test 5: Price of books by Michelle Obama
        System.out.println("TEST 5: Price of books by Michelle Obama");
        List<Book> obamaBooks = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase("Michelle Obama"))
                .collect(Collectors.toList());
        if (!obamaBooks.isEmpty()) {
            System.out.println("Found " + obamaBooks.size() + " books:");
            double totalPrice = 0;
            for (Book book : obamaBooks) {
                System.out.println("- " + book.getTitle() + " - $" + book.getPrice());
                totalPrice += book.getPrice();
            }
            System.out.println("Total price: $" + totalPrice);
        } else {
            System.out.println("No books found for this author.");
        }
        System.out.println();
        
        // Dataset statistics
        System.out.println("DATASET STATISTICS:");
        System.out.println("-".repeat(30));
        
        IntSummaryStatistics yearStats = books.stream()
                .mapToInt(Book::getYear)
                .summaryStatistics();
        System.out.println("Year range: " + yearStats.getMin() + " - " + yearStats.getMax());
        
        DoubleSummaryStatistics ratingStats = books.stream()
                .mapToDouble(Book::getUserRating)
                .summaryStatistics();
        System.out.printf("Average rating: %.2f (%.1f - %.1f)%n", 
            ratingStats.getAverage(), ratingStats.getMin(), ratingStats.getMax());
        
        IntSummaryStatistics priceStats = books.stream()
                .mapToInt(Book::getPrice)
                .summaryStatistics();
        System.out.printf("Average price: $%.2f ($%d - $%d)%n", 
            priceStats.getAverage(), priceStats.getMin(), priceStats.getMax());
        
        Map<String, Long> genreCount = books.stream()
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.counting()));
        System.out.println("Genre distribution:");
        genreCount.forEach((genre, genreBooks) -> 
            System.out.printf("- %-15s: %d books (%.1f%%)%n", 
                genre, genreBooks, (genreBooks * 100.0) / books.size()));
                
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALL TESTS COMPLETED SUCCESSFULLY!");
        System.out.println("=".repeat(60));
    }
}