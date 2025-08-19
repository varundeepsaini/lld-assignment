import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatasetReader {
    
    public static List<Book> readDataset(String filename) {
        List<Book> books = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header line
            String line = br.readLine();
            
            while ((line = br.readLine()) != null) {
                try {
                    // Handle CSV parsing with potential commas in quotes
                    List<String> values = parseCSVLine(line);
                    
                    if (values.size() >= 7) {
                        String title = values.get(0).trim();
                        String author = values.get(1).trim();
                        double userRating = Double.parseDouble(values.get(2).trim());
                        int reviews = Integer.parseInt(values.get(3).trim());
                        int price = Integer.parseInt(values.get(4).trim());
                        int year = Integer.parseInt(values.get(5).trim());
                        String genre = values.get(6).trim();
                        
                        Book book = new Book(title, author, userRating, reviews, price, year, genre);
                        books.add(book);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        return books;
    }
    
    // Helper method to properly parse CSV lines with potential commas in quotes
    private static List<String> parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentValue = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString());
                currentValue = new StringBuilder();
            } else {
                currentValue.append(c);
            }
        }
        
        // Add the last value
        values.add(currentValue.toString());
        
        return values;
    }
}