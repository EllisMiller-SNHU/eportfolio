# Code Review
## CS 340 Artifact Before Enhancement

<!-- Link to codebase -->

---

### Review
The artifact selected for enhancement is a Python-based web application built using Dash and Plotly, which connects to a MongoDB database through a custom CRUD class. The application displays animal shelter data in an interactive dashboard, allowing users to filter records, view tabular data, visualize geographic locations on a map, and analyze breed distributions using a pie chart.

The backend of the application is handled by the AnimalShelter class, which provides basic Create, Read, Update, and Delete operations for interacting with the MongoDB database. The read method is primarily used in this application to retrieve animal records based on user-defined filter criteria. The frontend is built using Dash and includes a data table, radio-button filters, a geolocation map using Dash Leaflet, and a Plotly pie chart for breed distribution.

When the application runs, it initially loads all records from the database and displays them in a DataTable. The user can then filter the dataset using predefined rescue categories such as water rescue, mountain rescue, and disaster response. These filters trigger database queries that update the displayed dataset dynamically.

The application also includes interactive components such as row selection in the data table, which updates a map visualization to show the selected animal’s location. Additionally, a pie chart dynamically updates based on the filtered dataset to show breed distribution.

From a structural perspective, the application is functional but tightly coupled. The database queries, data processing logic, and user interface components are all contained within a single file. This creates challenges in terms of scalability, maintainability, and code reuse. Additionally, there is repeated logic for building queries and processing DataFrame objects, which could be refactored into reusable functions.

Areas identified for improvement include:
- Lack of modular architecture
- Repeated data transformation logic 
- Coupling between UI and database logic 
- Limited separation between data access and business logic layers

The planned enhancement focuses on restructuring the application into a more modular design. This will include separating query-building logic into dedicated functions, isolating data processing into a service layer, and improving overall code organization. These changes will improve maintainability, scalability, and alignment with industry-standard software design principles.

---

## IT 450 Artifact Before Enhancement

<!-- Link to codebase -->

---

### Review
The artifact selected for enhancement is a Python-based Checkers game built using the graphics library. The application provides an interactive board game experience with standard checkers rules, including piece movement, capturing, king promotion, and turn-based gameplay. The system also includes a custom setup mode, save/load functionality, and a basic computer opponent.

The core functionality of the application is implemented within the Checkers class, which handles the overall game state, board initialization, player turns, move validation, and game progression. The Tile class represents individual board spaces and pieces, storing information such as piece type, color, and whether a piece is a king or pawn.

The user interface is built directly into the same program using the graphics library. The board, buttons, and game elements are drawn manually within the same execution loop. User input is handled through mouse clicks, which are converted into board coordinates and processed by the game logic to determine valid actions.

The game supports both two-player mode and a single-player mode with a computer opponent. The computer AI in the original version uses a heuristic-based decision system that evaluates available moves based on simple scoring rules such as captures, safety, and potential promotions. Move selection is based on comparing scores across all valid moves and selecting the highest-rated option.

When the game runs, it continuously processes either user input or AI turns depending on the current game state. In single-player mode, the AI automatically selects and executes a move during its turn. The game enforces standard rules such as forced captures, valid diagonal movement, and piece promotion when reaching the opposite end of the board.

From a structural perspective, the application is functional but heavily monolithic. The game logic, user interface, and AI decision-making are all tightly integrated within the same class structure. This results in a lack of clear separation between concerns, making the code more difficult to maintain and extend.

There is also significant repetition in movement validation and capture logic, where similar checks are performed across multiple methods. Additionally, the AI logic is relatively simple and does not consider deeper strategic reasoning beyond immediate move scoring.

Areas identified for improvement include:
- Monolithic class structure with limited separation of concerns 
- Repeated logic in move validation and capture detection 
- Basic heuristic-based AI with limited strategic depth 
- Lack of modular design for AI decision-making components 

The planned enhancement focuses on improving software design by introducing better separation between game logic, user interface, and AI components. The AI system will be redesigned to use a more structured decision-making approach, and repeated logic will be refactored into reusable methods. These improvements aim to increase maintainability, readability, and extensibility of the system while aligning it more closely with standard software engineering practices.

---

## CS 360 Artifact Before Enhancement

<!-- Link to codebase -->

---

### Review
The selected artifact is a Java-based Android Inventory Management application developed in CS360: Mobile Architecture and Programming. The application allows users to log in, create accounts, and manage a personal inventory of items stored in a local SQLite database. Users are able to add, edit, delete, and search inventory items, as well as receive low-stock alerts via SMS notifications.

The application consists of multiple activities and UI components, including a LoginActivity, InventoryActivity, and a dialog-based ItemDialogFragment used for creating and editing inventory items. The application uses a DatabaseHelper class to manage SQLite database operations, including user authentication and CRUD operations for inventory items.

When the application launches, the user is prompted to log in or create a new account. Upon successful authentication, the application navigates to the main inventory screen, where a RecyclerView displays all inventory items associated with the logged-in user. The user can add new items using a floating action button, edit items by selecting them, and delete items through a confirmation dialog. A search function allows filtering of inventory items by name or description.

The DatabaseHelper class directly manages database creation, schema definition, and all SQL queries for both user and inventory tables. This includes methods for inserting users, validating login credentials, and performing CRUD operations on inventory items. As a result, both application logic and database logic are tightly coupled within a single helper class.

From a structural standpoint, the application is functional but relies heavily on SQLiteOpenHelper and manual SQL query management. This creates limitations in terms of scalability, maintainability, and alignment with modern Android development practices. Additionally, business logic, database logic, and UI logic are partially intertwined within activity classes, which reduces modularity.

Areas identified for improvement include:
- Use of legacy SQLiteOpenHelper instead of modern Room persistence library
- Limited separation of concerns between UI, data access, and business logic
- Manual SQL queries that increase risk of errors and reduce maintainability
- Lack of abstraction layer for data operations  

The planned enhancement focuses on migrating the application architecture from SQLiteOpenHelper to the Room persistence library. This includes introducing a structured database layer using Entity, DAO, and AppDatabase classes. These improvements will modernize the application architecture, improve maintainability, and align the project with industry-standard Android development practices.

---
