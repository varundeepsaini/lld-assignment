# Amazon Bestselling Books Analysis System

A Java-based data analysis system for Amazon's Top 50 bestselling books from 2009-2019. This project analyzes a dataset containing information about 550+ bestselling books including their titles, authors, ratings, reviews, prices, publication years, and genres.

## Dataset Information

The dataset contains the following columns:
- **Name**: Book title
- **Author**: Author name
- **User Rating**: Average Amazon user rating (3.3 to 4.9)
- **Reviews**: Number of user reviews (37 to 87,800)
- **Price**: Book price ($0 to $105)
- **Year**: Year appeared on bestseller list (2009-2019)
- **Genre**: Fiction or Non-fiction

## Features

The system provides 5 main analysis tasks:

1. **Count Books by Author**: Returns the total number of books written by a specific author
2. **List All Authors**: Displays all unique authors in the dataset
3. **Books by Author**: Shows all books written by a specific author
4. **Classify by Rating**: Finds all books with a specific user rating
5. **Price Analysis**: Shows book names and prices for a specific author
6. **Dataset Statistics**: Comprehensive overview of the dataset

## Files Description

- `Book.java` - Data model class representing a book with all attributes
- `DatasetReader.java` - CSV file reader that creates Book objects from the dataset
- `driver.java` - Original main class with menu-driven interface
- `InteractiveDriver.java` - Improved version with better input handling
- `TestDriver.java` - Automated test program to verify all functionality
- `data.csv` - Dataset file containing book information

## How to Run

### Prerequisites
- Java 17 or higher installed
- All Java files compiled (`.class` files present)

### Option 1: Interactive Menu (Recommended for Manual Testing)
```bash
java InteractiveDriver
```

### Option 2: Automated Test (Shows All Functionality)
```bash
java TestDriver
```

### Option 3: Original Driver (Basic Menu)
```bash
java driver
```

### Option 4: Run Demo Script
```bash
./demo.sh
```

## Compilation Instructions

```bash
# Compile all Java files
javac *.java

# Run the interactive system
java InteractiveDriver
```

## Sample Usage

### Example 1: Count Books by Author
```
Enter your choice: 1
Enter author name: George R. R. Martin
Result: Author: George R. R. Martin, Total books: 2
```

### Example 2: Books with Specific Rating
```
Enter your choice: 4
Enter user rating: 4.7
Result: Found 10 books with rating 4.7
```

### Example 3: Price Analysis
```
Enter your choice: 5
Enter author name: Michelle Obama
Books and Prices:
Becoming                                $11
TOTAL PRICE:                            $11.00
AVERAGE PRICE:                          $11.00
```

## Key Implementation Features

- **Robust CSV Parsing**: Handles quoted fields and comma-separated values correctly
- **Case-Insensitive Search**: Author searches work regardless of case
- **Error Handling**: Graceful handling of malformed data and user input errors
- **Flexible Matching**: Partial name matching for author searches
- **Statistical Analysis**: Comprehensive dataset statistics including averages and distributions
- **Professional Output**: Well-formatted console output with clear separators

## Technical Details

- Written in Java 17
- Uses Java Streams for efficient data processing
- Object-oriented design with proper encapsulation
- Input validation and error handling
- Memory-efficient processing of large datasets

## Dataset Statistics (Current)

- **Total Books**: 58 books
- **Unique Authors**: 56 authors
- **Year Range**: 2009-2020
- **Average Rating**: 4.49 (4.0-4.8)
- **Average Price**: $9.95 ($5-$18)
- **Genre Distribution**: 53.4% Fiction, 46.6% Non-fiction

## Future Enhancements

- GUI interface using JavaFX or Swing
- Database integration for larger datasets
- Advanced search filters (price range, year range, genre)
- Export results to CSV/PDF
- Visualization charts and graphs
- RESTful API for web integration

---

*This project demonstrates core Java programming concepts including file I/O, object-oriented design, collections framework, and stream processing.*
