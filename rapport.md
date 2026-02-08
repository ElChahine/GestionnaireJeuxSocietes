### Résumé du Refactoring

D'abord, le dossier data via le package fr.fges.data pour gérer la persistance. Il utilise le pattern Strategy avec une interface IGameRepository implémentée par JsonFileRepository et CsvFileRepository selon le fichier choisi au lancement.

Ensuite, l'ui via le package fr.fges.ui. La classe ConsoleController gère le flux de l'application et les entrées utilisateur, tandis que MenuPrinter se charge uniquement de l'affichage console, séparant ainsi l'interaction de la logique.

Puis, logic avec le package fr.fges.logic. Le cœur du système est GameService, qui orchestre les règles métier. Il est assisté par UndoManager pour l'historique et DuplicateValidator pour la vérification des données.

Finalement, ces trois couches respectent la Clean Architecture : le Main injecte les dépendances (Repository -> Service -> Controller), rendant le code modulaire et testable.

=============================================================

### FEATURE 1 : NoDuplicate
Implémentation de la fonctionnalité NoDuplicate via la classe dédiée DuplicateValidator. Elle empêche l'ajout d'un jeu si son titre existe déjà dans la collection (insensible à la casse).

### FEATURE 2 : Undo Last Action
Implémentation du système d'annulation via UndoManager. Utilise une structure de Pile (Stack) pour mémoriser l'historique. Permet d'annuler la dernière action d'ajout ou de suppression et met à jour le fichier de sauvegarde instantanément.

### FEATURE 3 : Games for X Players
Implémentation d'un filtre intelligent. L'utilisateur entre un nombre de joueurs, et le système affiche tous les jeux compatibles (où min <= nb <= max), triés par ordre alphabétique.

### FEATURE 4 : Recommend Game
Implémentation de la fonctionnalité Recommend Game, qui suggère un seul jeu de façon aléatoire à l'utilisateur, parmi ceux compatibles avec le nombre de joueurs saisi.

### FEATURE 5 : Weekend Summary
Implémentation de la fonctionnalité Weekend Summary. Le ConsoleController détecte automatiquement la date système. Si nous sommes samedi ou dimanche, une option supplémentaire apparaît pour afficher une sélection rapide de 3 jeux aléatoires.