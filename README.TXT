SURLY 1
Jacob Coffland and Michael Albert
Febuary 1st, 2019

Installation:
1. Download and extract the zip file from the git repository.
2. Compile the java files using javac from the terminal.
3. Run the main method using the input file as a command line argument

Demo: Follow the instructions above with an input file taken from the TestInputSurly2 folder, or a file made to the following specifications
- Commands are issued with the following keywords: 
    - RELATION, INSERT, PRINT, DELETE, DESTROY, SELECT, PROJECT, JOIN, INTEGRITY_CONSTRAINT.
- Commands and relation names are case insensitive
- Where can be used as a secondary keyword following the relation name for DELETE, SELECT INTEGRITY_CONSTRAINT.
- Parameters are given immediately after commands with no parentheses except for RELATION. Arguments can include spaces when surrounded by single quotes.
- End each line with a semicolon
- Lines with errors will generate an inline error message, but will not stop the remaining commands
- Comments are allowed, using /* this is a comment */ format
