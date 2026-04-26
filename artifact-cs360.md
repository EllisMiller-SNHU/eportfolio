# Databases Artifact

---

## Original Artifact

[CS 360 Original Artifact](https://github.com/EllisMiller-SNHU/eportfolio/tree/main/artifact-360/original)

---

## Enhanced Artifact

[CS 360 Enhanced Artifact](https://github.com/EllisMiller-SNHU/eportfolio/tree/main/artifact-360/enhanced)

---

## Enhancements Made
- Migrates from SQLiteOpenHelper to the Room persistence library
- Separates database structure into entities, DAOs, and a centralized database class
- Decouples database logic from UI and application code
- Improves maintainability and scalability through a modular database architecture
- Enhances code readability and aligns with modern Android development standards

---

## Skills Demonstrated
This enhancement demonstrates key software engineering and database development skills, including the implementation of structured data management using a local SQLite database and the transition toward a more modern and scalable persistence architecture. I will demonstrate the ability to refactor database interactions into a cleaner and more maintainable design by introducing a separation between data access logic and application logic.

Additionally, I demonstrate skills in improving database efficiency and data handling by replacing in-memory filtering with structured query-based retrieval. This improves both performance and scalability as the dataset grows.

This enhancement also reflects the ability to implement input validation and enforce data integrity within a mobile application context. By improving how data is stored, retrieved, and validated, the application becomes more reliable and better aligned with professional software development practices.

---

## Artifact Narrative – Software Design and Engineering (CS360)
### Artifact Description
The selected artifact is a Java-based Android Inventory Management application developed in CS360: Mobile Architecture and Programming. The application allows users to authenticate through a login system, create accounts, and manage a personal inventory of items stored in a local SQLite database. Users can add, edit, delete, and search inventory items, as well as receive SMS-based low-stock notifications. The artifact demonstrates a full mobile application workflow, including user authentication, persistent data storage, and dynamic UI interaction using RecyclerView components.

### Justification for Inclusion
This artifact was selected because it demonstrates a complete mobile application architecture that integrates user authentication, local data persistence, and interactive UI design. It showcases my ability to design and implement a functional Android application that manages structured data and responds to user input in real time.

The enhancement significantly improves the original design by migrating the application from a legacy SQLiteOpenHelper-based architecture to a modern Room persistence framework. Previously, database operations were tightly coupled with application logic inside the DatabaseHelper class. This made the system harder to maintain, scale, and extend. The enhanced version introduces a clear separation between database entities, data access objects (DAO), and the database instance itself.

### Enhancement Overview and Skills Demonstrated
The enhancement focused on redesigning the data persistence layer using the Room database architecture. This included defining an InventoryItem entity, creating a DAO interface for structured database operations, and implementing an AppDatabase class to manage database instantiation. The update replaces raw SQL queries with annotated methods, improving readability and reducing the likelihood of runtime errors.

Through this enhancement, I demonstrated the following skills:

- Migration from legacy SQLite architecture to modern Room persistence library  
- Design and implementation of Entity-DAO-database structure  
- Application of software design principles including separation of concerns  
- Refactoring tightly coupled database logic into a modular architecture  
- Improved maintainability and scalability of a mobile application  

### Course Outcome Alignment

This enhancement aligns with the following course outcomes:
- Outcome 3: Design and evaluate computing solutions using algorithmic principles and computer science practices.
The migration to Room demonstrates the ability to evaluate an existing database implementation and redesign it using improved architectural principles that enhance maintainability and reduce complexity.
- Outcome 4: Demonstrate the use of well-founded and innovative techniques in computing practices.
The implementation of the Room persistence library reflects modern Android development practices and introduces a structured approach to database management using industry-standard tools.
- Outcome 2: Design, develop, and deliver professional-quality technical communication.
The restructuring of the database layer improves code clarity and readability, making the system easier for other developers to understand and maintain.

### Summary of Improvement
Overall, the enhancement transforms the original application from a tightly coupled SQLite-based design into a modern, modular Room database architecture. By introducing entities, DAOs, and a centralized database class, the application now follows industry-standard Android development practices. These improvements increase scalability, reduce complexity, and significantly improve long-term maintainability of the system.
