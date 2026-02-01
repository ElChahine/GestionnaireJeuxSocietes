# Rapport design Pattern 

## Résumé du Refactoring

D'abord, le dossier **data** via le package `fr.fges.data` pour gérer les fichiers JSON et CSV (`JsonFileRepository`, `CsvFileRepository`). 
Ensuite, **l'ui** via le package `fr.fges.ui` avec les classes `ConsoleController` et `MenuPrinter`, séparant ainsi l'UI de la logique métier. 
Puis, **logic** avec le package `fr.fges.logic`, contenant les classes `Game`, `GameService`, et `GameRepository`, qui encapsulent la logique métier et les opérations sur les données.
Finalement, ces trois couches (UI, Logic, Data) ont été intégrées et orchestrées par `GameService`.

=============================================================

## FEATURE 1 : NoDuplicate

implémenttion de la fonctionnalité NoDuplicate, qui empêche l'ajout de jeux en double dans la collection

## FEATURE 2 : Recommend Game

implémentation de la fonctionnalité Recommend Game, qui suggère des jeux de facon aléatoire à l'utilisateur en fonction du nombre de joueurs.

## FEATURE 3 : Weekend Summary

implémentation de la fonctionnalité Weekend Summary, qui affiche 3 jeux aleatoires de la colectin uniquement le weekend. 