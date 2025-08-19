#!/bin/bash

echo "======================================================================"
echo "          AMAZON BESTSELLING BOOKS ANALYSIS - DEMO                  "
echo "======================================================================"
echo

# Demo 1: Count books by George R. R. Martin
echo "DEMO 1: Counting books by George R. R. Martin..."
echo -e "1\nGeorge R. R. Martin\n7" | java InteractiveDriver

echo
echo "======================================================================"
echo

# Demo 2: List first few authors
echo "DEMO 2: Listing all authors in the dataset..."
echo -e "2\n7" | java InteractiveDriver | head -20

echo
echo "======================================================================"
echo

# Demo 3: Books by J.K. Rowling
echo "DEMO 3: Finding books by J.K. Rowling..."
echo -e "3\nJ.K. Rowling\n7" | java InteractiveDriver

echo
echo "======================================================================"
echo

# Demo 4: Books with rating 4.7
echo "DEMO 4: Finding books with rating 4.7..."
echo -e "4\n4.7\n7" | java InteractiveDriver

echo
echo "======================================================================"
echo

# Demo 5: Price of books by Michelle Obama
echo "DEMO 5: Prices of books by Michelle Obama..."
echo -e "5\nMichelle Obama\n7" | java InteractiveDriver

echo
echo "======================================================================"
echo

# Demo 6: Dataset statistics
echo "DEMO 6: Dataset statistics..."
echo -e "6\n7" | java InteractiveDriver

echo
echo "======================================================================"
echo "                         DEMO COMPLETED                            "
echo "======================================================================"