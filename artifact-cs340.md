# Software Design & Engineering Artifact

---

## Original Artifact

[CS 340 Original Artifact](https://github.com/EllisMiller-SNHU/eportfolio/blob/main/artifact-340/original/ProjectTwoDashboard.ipynb)

---

## Enhanced Artifact

[CS 340 Enhanced Artifact]([artifact-340/enhanced/ProjectTwoDashboard.ipynb](https://github.com/EllisMiller-SNHU/eportfolio/blob/main/artifact-340/enhanced/ProjectTwoDashboard.ipynb))

---

## Enhancements Made
- Code modularity
- Separation of concerns
- Maintainability
- Data pipeline clarity
- UI responsiveness and stability

---

## Skills Demonstrated
This enhancement demonstrates key software engineering and design skills, including modularization, separation of concerns, and refactoring of tightly coupled code into a more maintainable architecture. I will apply principles of layered design to separate database access, data processing, and user interface logic.

Additionally, I will demonstrate skills in improving code readability, eliminating redundant logic, and organizing application components in a way that supports scalability and future development. This includes restructuring callback logic and standardizing data handling procedures across the application.

---

## Artifact Narrative – Software Design and Engineering (CS340)
### Artifact Description
The selected artifact is a Python-based web application developed in CS340: Advanced Programming Concepts. The application uses the Dash framework to create an interactive dashboard for animal shelter data stored in a MongoDB database. It includes functionality for filtering animal records, displaying data in a table, visualizing geographic locations on a map, and presenting breed distribution using a pie chart. The artifact was originally created as a full-stack-style dashboard integrating database access, backend logic, and frontend visualization.

### Justification for Inclusion
This artifact was selected because it demonstrates a combination of database interaction, backend processing, and frontend user interface design within a single application. It showcases my ability to work with MongoDB through a custom CRUD class, process structured data using Python and Pandas, and build interactive visualizations using Dash and Plotly.
The enhancements made to this artifact significantly improve its software design by introducing a more modular and maintainable structure. Previously, the application contained tightly coupled logic, where database queries, data processing, and UI components were all handled within a single file. The enhancement refactors this structure by separating concerns into distinct functional components, improving readability and scalability.

### Enhancement Overview and Skills Demonstrated
The enhancement focused on improving software design through modularization and separation of concerns. Specifically, query-building logic was extracted into a dedicated function, and data processing was isolated into a reusable service layer. This reduces redundancy and improves maintainability by centralizing logic that was previously duplicated across multiple sections of the application.

Through this enhancement, I demonstrated the following skills:
- Software refactoring and code restructuring 
- Application of modular design principles 
- Separation of concerns in full-stack applications 
- Improved code readability and maintainability 
- Use of functional decomposition to organize complex systems

### Course Outcome Alignment
This enhancement aligns with the following course outcomes:
- Outcome 3: Design and evaluate computing solutions using algorithmic principles and computer science practices.
The refactoring of the application demonstrates the ability to evaluate an existing solution and redesign it using improved structural principles that enhance scalability and maintainability.
- Outcome 4: Demonstrate the use of well-founded and innovative techniques in computing practices.
The implementation of modular architecture and separation of concerns reflects industry-standard software engineering practices used to improve system design.
- Outcome 5: Develop a security mindset that anticipates vulnerabilities and ensures data integrity.
Although not security-focused at its core, the improved structure reduces risk of logic errors and supports safer handling of database queries by centralizing and controlling data access logic.

### Summary of Improvement
Overall, the enhancement transformed the original application from a monolithic structure into a more organized and maintainable system. By separating data access, processing logic, and user interface components, the application now better reflects professional software engineering practices. These improvements make the system easier to extend, debug, and scale in future development scenarios.
