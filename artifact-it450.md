# Algorithms and Data Structures Artifact

---

## Original Artifact

[IT 450 Original Artifact](https://github.com/EllisMiller-SNHU/eportfolio/blob/main/artifact-450/original/Checkers_v24.py)

---

## Enhanced Artifact

[IT 450 Enhanced Artifact](https://github.com/EllisMiller-SNHU/eportfolio/blob/main/artifact-450/enhanced/Checkers_v24.py)

---

## Enhancements Made
- Introduction of a knowledge base system for move evaluation
- Implementation of forward chaining inference logic
- Rule prioritization system for structured decision-making
- Move evaluation caching to improve performance
- Separation of AI reasoning from core game logic
- Improved move selection through prioritization and pruning
- Enhanced safety evaluation for board state analysis

---

## Skills Demonstrated
This enhancement demonstrates key algorithmic and data structure skills, including optimization of search and evaluation processes, implementation of caching strategies, and restructuring of decision-making logic to improve efficiency. I will apply principles of algorithm refinement to reduce redundant computation during move evaluation and improve the overall performance of the AI system.

Additionally, I demonstrate skills in improving computational efficiency through move ordering and pruning techniques, as well as organizing program logic to support faster decision-making. This includes restructuring the AI evaluation pipeline, reducing repeated calculations in the inference engine, and improving the overall flow of data used in move selection.

---

## Artifact Narrative – Artificial Intelligence / Software Engineering (IT450)
### Artifact Description
The selected artifact is an enhanced Python-based Checkers game developed in IT450. The application uses a custom GUI built with the graphics library to simulate a full playable checkers game with both human and computer opponents. The system includes standard checkers rules, custom board setup, save/load functionality, and an AI opponent. The enhanced version introduces a rule-based inference engine that significantly improves the decision-making capability of the computer player.
The artifact represents a full interactive software system combining game logic, user interface design, and artificial intelligence reasoning within a single integrated application.

### Justification for Inclusion
This artifact was selected because it demonstrates the application of artificial intelligence concepts, software architecture improvements, and rule-based decision systems in a functional game environment. It goes beyond basic game implementation by introducing an inference engine that evaluates board states and generates move decisions using structured rules and forward chaining logic.
The enhanced version of the project represents a major advancement from the original implementation. While the original AI relied on a simple heuristic scoring system, the improved version introduces a knowledge-based system that evaluates moves using explicit rules, contextual facts, and weighted reasoning. This makes the AI more interpretable, modular, and closer to real decision-making systems used in AI-driven applications.

### Enhancement Overview and Skills Demonstrated
The primary enhancement in this artifact is the implementation of a rule-based inference engine for the computer AI. This system introduces structured reasoning through production rules such as forced captures, safe advancement, king promotion prioritization, and danger avoidance. Each move is evaluated by gathering a knowledge base of facts and applying forward chaining to determine which rules fire for a given move.

Additional enhancements include:
- Introduction of a knowledge base system for move evaluation 
- Implementation of forward chaining inference logic 
- Rule prioritization system for structured decision-making 
- Move evaluation caching to improve performance 
- Separation of AI reasoning from core game logic 
- Improved move selection through prioritization and pruning 
- Enhanced safety evaluation for board state analysis

The AI system now evaluates not only immediate move outcomes but also strategic implications such as positional safety, piece advantage, promotion potential, and board control.
Through this enhancement, I demonstrated the following skills:
- Artificial intelligence design using rule-based systems 
- Implementation of forward chaining inference engines 
- Software optimization through caching and move pruning 
- Object-oriented design and system modularization 
- Game AI strategy development 
- Separation of concerns between game logic and AI reasoning 
- Performance optimization in decision-heavy systems

### Course Outcome Alignment
This enhancement aligns with the following course outcomes:
- Outcome 1: Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision making in the field of computer science.
The inference engine introduces structured, explainable decision-making logic, allowing AI behavior to be interpreted and evaluated, similar to collaborative expert systems.
- Outcome 3: Design and evaluate computing solutions using algorithmic principles and computer science practices.
The artifact demonstrates the design of a rule-based AI system that evaluates multiple possible moves using structured logic, heuristics, and optimization techniques.
- Outcome 4: Demonstrate the use of well-founded and innovative techniques in computing practices.
The implementation of forward chaining, knowledge bases, and rule prioritization reflects advanced AI methodologies commonly used in expert systems.
- Outcome 5: Develop a security mindset that anticipates vulnerabilities and ensures data integrity.
Although not security-focused, the structured separation of AI logic and game state reduces unintended side effects and improves system reliability by controlling how state changes are evaluated and executed.

### Summary of Improvement
Overall, the enhancement transforms the original Checkers AI from a simple heuristic-based decision system into a structured rule-based inference engine. The improved design introduces modular reasoning, clearer decision logic, and more strategic gameplay behavior.

The system is now more extensible, allowing additional rules to be added without modifying core game logic. It also improves transparency by allowing move decisions to be traced back to specific rules and facts. These improvements significantly elevate the quality of the AI system and demonstrate a stronger understanding of artificial intelligence principles and software engineering practices.
