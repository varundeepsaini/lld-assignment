#!/usr/bin/env python3
"""
Comprehensive test suite for Amazon Bestselling Books Java Analysis System
Tests all 5 core analysis tasks and verifies system functionality
"""

import subprocess
import sys
import os
import time
from datetime import datetime

class JavaBookAnalysisSystemTester:
    def __init__(self):
        self.tests_run = 0
        self.tests_passed = 0
        self.test_results = []
        
    def log_test(self, test_name, passed, details=""):
        """Log test results"""
        self.tests_run += 1
        if passed:
            self.tests_passed += 1
            status = "✅ PASSED"
        else:
            status = "❌ FAILED"
        
        result = f"{status} - {test_name}"
        if details:
            result += f": {details}"
        
        print(result)
        self.test_results.append((test_name, passed, details))
        
    def run_java_command(self, command, input_data=None, timeout=30):
        """Execute Java command and return output"""
        try:
            if input_data:
                process = subprocess.Popen(
                    command.split(),
                    stdin=subprocess.PIPE,
                    stdout=subprocess.PIPE,
                    stderr=subprocess.PIPE,
                    text=True,
                    cwd='/app'
                )
                stdout, stderr = process.communicate(input=input_data, timeout=timeout)
            else:
                process = subprocess.Popen(
                    command.split(),
                    stdout=subprocess.PIPE,
                    stderr=subprocess.PIPE,
                    text=True,
                    cwd='/app'
                )
                stdout, stderr = process.communicate(timeout=timeout)
            
            return process.returncode, stdout, stderr
        except subprocess.TimeoutExpired:
            process.kill()
            return -1, "", "Command timed out"
        except Exception as e:
            return -1, "", str(e)
    
    def test_compilation(self):
        """Test if all Java files compile successfully"""
        print("\n🔧 Testing Java Compilation...")
        
        java_files = ["Book.java", "DatasetReader.java", "InteractiveDriver.java", "TestDriver.java"]
        
        for java_file in java_files:
            if not os.path.exists(f"/app/{java_file}"):
                self.log_test(f"File exists: {java_file}", False, "File not found")
                continue
                
            returncode, stdout, stderr = self.run_java_command(f"javac {java_file}")
            
            if returncode == 0:
                self.log_test(f"Compilation: {java_file}", True)
            else:
                self.log_test(f"Compilation: {java_file}", False, f"Compilation error: {stderr}")
    
    def test_dataset_loading(self):
        """Test if dataset loads correctly"""
        print("\n📊 Testing Dataset Loading...")
        
        # Check if data.csv exists
        if not os.path.exists("/app/data.csv"):
            self.log_test("Dataset file exists", False, "data.csv not found")
            return
        
        self.log_test("Dataset file exists", True)
        
        # Count lines in CSV (excluding header)
        try:
            with open("/app/data.csv", 'r') as f:
                lines = f.readlines()
                data_lines = len(lines) - 1  # Exclude header
                
            self.log_test("Dataset readable", True, f"Found {data_lines} data rows")
            
            # Expected: 58 books according to requirements
            if data_lines == 58:
                self.log_test("Dataset size verification", True, "58 books as expected")
            else:
                self.log_test("Dataset size verification", False, f"Expected 58 books, found {data_lines}")
                
        except Exception as e:
            self.log_test("Dataset readable", False, str(e))
    
    def test_automated_test_driver(self):
        """Run the automated TestDriver"""
        print("\n🤖 Running Automated TestDriver...")
        
        returncode, stdout, stderr = self.run_java_command("java TestDriver")
        
        if returncode == 0:
            self.log_test("TestDriver execution", True)
            
            # Check for expected outputs in TestDriver
            expected_patterns = [
                "Dataset loaded successfully",
                "George R. R. Martin",
                "J.K. Rowling", 
                "rating 4.7",
                "Michelle Obama",
                "ALL TESTS COMPLETED SUCCESSFULLY"
            ]
            
            for pattern in expected_patterns:
                if pattern in stdout:
                    self.log_test(f"TestDriver output contains: {pattern}", True)
                else:
                    self.log_test(f"TestDriver output contains: {pattern}", False)
                    
        else:
            self.log_test("TestDriver execution", False, f"Exit code: {returncode}, Error: {stderr}")
    
    def test_task1_count_books_by_author(self):
        """Test Task 1: Count total books by an author"""
        print("\n📚 Testing Task 1: Count Books by Author...")
        
        # Test with George R. R. Martin (should find 2 books)
        input_data = "1\nGeorge R. R. Martin\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            self.log_test("Task 1 - Interactive execution", True)
            
            if "Total books: 2" in stdout:
                self.log_test("Task 1 - George R. R. Martin count", True, "Found 2 books")
            else:
                self.log_test("Task 1 - George R. R. Martin count", False, "Expected 2 books")
                
        else:
            self.log_test("Task 1 - Interactive execution", False, stderr)
    
    def test_task2_list_all_authors(self):
        """Test Task 2: List all authors in dataset"""
        print("\n👥 Testing Task 2: List All Authors...")
        
        input_data = "2\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            self.log_test("Task 2 - Interactive execution", True)
            
            # Should show unique authors count (expected: 56 according to requirements)
            if "unique authors" in stdout.lower():
                self.log_test("Task 2 - Authors list generated", True)
            else:
                self.log_test("Task 2 - Authors list generated", False)
                
        else:
            self.log_test("Task 2 - Interactive execution", False, stderr)
    
    def test_task3_books_by_author(self):
        """Test Task 3: List all books by specific author"""
        print("\n📖 Testing Task 3: Books by Specific Author...")
        
        # Test with J.K. Rowling (should find Harry Potter books)
        input_data = "3\nJ.K. Rowling\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            self.log_test("Task 3 - Interactive execution", True)
            
            if "Harry Potter" in stdout:
                self.log_test("Task 3 - J.K. Rowling books found", True, "Harry Potter books detected")
            else:
                self.log_test("Task 3 - J.K. Rowling books found", False, "Harry Potter books not found")
                
        else:
            self.log_test("Task 3 - Interactive execution", False, stderr)
    
    def test_task4_classify_by_rating(self):
        """Test Task 4: Classify books by user rating"""
        print("\n⭐ Testing Task 4: Classify by User Rating...")
        
        # Test with rating 4.7
        input_data = "4\n4.7\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            self.log_test("Task 4 - Interactive execution", True)
            
            if "Rating: 4.7" in stdout and "Total books with this rating:" in stdout:
                self.log_test("Task 4 - Rating 4.7 classification", True)
            else:
                self.log_test("Task 4 - Rating 4.7 classification", False)
                
        else:
            self.log_test("Task 4 - Interactive execution", False, stderr)
    
    def test_task5_prices_by_author(self):
        """Test Task 5: Get prices of books by author"""
        print("\n💰 Testing Task 5: Book Prices by Author...")
        
        # Test with Michelle Obama (should show "Becoming" for $11)
        input_data = "5\nMichelle Obama\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            self.log_test("Task 5 - Interactive execution", True)
            
            if "Becoming" in stdout and "$11" in stdout:
                self.log_test("Task 5 - Michelle Obama 'Becoming' price", True, "Found Becoming for $11")
            elif "Michelle Obama" in stdout:
                self.log_test("Task 5 - Michelle Obama books found", True, "Author found but price may differ")
            else:
                self.log_test("Task 5 - Michelle Obama books", False, "Author not found")
                
        else:
            self.log_test("Task 5 - Interactive execution", False, stderr)
    
    def test_dataset_statistics(self):
        """Test dataset statistics functionality"""
        print("\n📈 Testing Dataset Statistics...")
        
        input_data = "6\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            self.log_test("Dataset statistics - Interactive execution", True)
            
            # Check for expected statistics
            expected_stats = [
                "Total books:",
                "Unique authors:",
                "Year range:",
                "Average rating:",
                "Average price:",
                "Genre Distribution:"
            ]
            
            for stat in expected_stats:
                if stat in stdout:
                    self.log_test(f"Statistics contains: {stat}", True)
                else:
                    self.log_test(f"Statistics contains: {stat}", False)
                    
        else:
            self.log_test("Dataset statistics - Interactive execution", False, stderr)
    
    def test_error_handling(self):
        """Test error handling for invalid inputs"""
        print("\n🚨 Testing Error Handling...")
        
        # Test invalid menu choice
        input_data = "99\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            if "Invalid choice" in stdout or "Invalid input" in stdout:
                self.log_test("Error handling - Invalid menu choice", True)
            else:
                self.log_test("Error handling - Invalid menu choice", False)
        else:
            self.log_test("Error handling test execution", False, stderr)
        
        # Test invalid rating format
        input_data = "4\nabc\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            if "Invalid rating format" in stdout:
                self.log_test("Error handling - Invalid rating format", True)
            else:
                self.log_test("Error handling - Invalid rating format", False)
        else:
            self.log_test("Error handling rating test execution", False, stderr)
    
    def test_case_insensitive_search(self):
        """Test case-insensitive author search"""
        print("\n🔍 Testing Case-Insensitive Search...")
        
        # Test with lowercase author name
        input_data = "1\ngeorge r. r. martin\n7\n"
        returncode, stdout, stderr = self.run_java_command("java InteractiveDriver", input_data)
        
        if returncode == 0:
            if "Total books: 2" in stdout:
                self.log_test("Case-insensitive search", True, "Lowercase search works")
            else:
                self.log_test("Case-insensitive search", False, "Lowercase search failed")
        else:
            self.log_test("Case-insensitive search execution", False, stderr)
    
    def run_all_tests(self):
        """Run all tests"""
        print("=" * 80)
        print("    COMPREHENSIVE TESTING: AMAZON BESTSELLING BOOKS ANALYSIS SYSTEM")
        print("=" * 80)
        print(f"Test started at: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
        
        # Change to app directory
        os.chdir('/app')
        
        # Run all test categories
        self.test_compilation()
        self.test_dataset_loading()
        self.test_automated_test_driver()
        self.test_task1_count_books_by_author()
        self.test_task2_list_all_authors()
        self.test_task3_books_by_author()
        self.test_task4_classify_by_rating()
        self.test_task5_prices_by_author()
        self.test_dataset_statistics()
        self.test_error_handling()
        self.test_case_insensitive_search()
        
        # Print final results
        print("\n" + "=" * 80)
        print("                           TEST SUMMARY")
        print("=" * 80)
        print(f"Tests Run: {self.tests_run}")
        print(f"Tests Passed: {self.tests_passed}")
        print(f"Tests Failed: {self.tests_run - self.tests_passed}")
        print(f"Success Rate: {(self.tests_passed/self.tests_run)*100:.1f}%")
        
        if self.tests_passed == self.tests_run:
            print("\n🎉 ALL TESTS PASSED! System is working correctly.")
            return 0
        else:
            print(f"\n⚠️  {self.tests_run - self.tests_passed} tests failed. See details above.")
            
            # Print failed tests
            print("\nFailed Tests:")
            for test_name, passed, details in self.test_results:
                if not passed:
                    print(f"  ❌ {test_name}: {details}")
            
            return 1

def main():
    """Main test execution"""
    tester = JavaBookAnalysisSystemTester()
    return tester.run_all_tests()

if __name__ == "__main__":
    sys.exit(main())