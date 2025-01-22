Voici un exemple de fichier README pour les itérations 2 et 3 de votre projet :


# Projet d'Analyse et de Conception d'Application : Combat Naval

## Description du Projet

Ce projet propose une version revisitée du jeu de Combat Naval, développée en plusieurs itérations. Après une première version console, les itérations 2 et 3 portent sur l'intégration de **JavaFX** pour une interface graphique interactive et l'ajout d'une phase de placement manuel des bateaux.

---

## Fonctionnalités (Itération 2)

### Portage vers JavaFX
- **Interface utilisateur graphique** :
  - Choix des dimensions de la mer.
  - Affichage graphique du plateau de jeu.
  - Interaction avec la souris pour sélectionner et déplacer les bateaux.
  - Visualisation des cases accessibles lors des déplacements.
- **Règles du jeu** : Identiques à celles de l'itération 1 (version console).
- **Architecture commune** :
  - Un **modèle unique** partagé entre les versions console et JavaFX.
  - Deux contrôleurs indépendants pour chaque version.
  - Utilisation du design pattern **MVC** et de l'interface **Observer/Observable**.

### Flexibilité de Conception
- Intégration possible d'éléments graphiques avancés (animations, sons, etc.).
- Exemple fourni dans le programme démo **MovingBoat** pour guider la mise en œuvre.

---

## Fonctionnalités (Itération 3)

### Placement Manuel des Bateaux
- **Choix du mode de placement** :
  - Placement aléatoire (comme dans l'itération 2).
  - Placement manuel : Les joueurs placent leurs bateaux sur la mer, tour à tour.
- **Interface améliorée** :
  - Les bateaux commencent dans un "port" à côté de la mer.
  - Le programme indique visuellement les cases disponibles pour le placement.
  - Les joueurs sont guidés à chaque étape du placement.
- **Gestion des règles de placement** :
  - Une seule unité par case.
  - Les emplacements choisis doivent être libres.
  - Une fois le dernier bateau placé, le jeu commence.

---

## Architecture Technique

### Utilisation du Pattern Builder
- **SeaBuilder** :
  - Responsable de la construction de la mer (avec ou sans placement manuel).
  - Gère l'état intermédiaire de la mer durant la phase de construction.
  - Empêche l'accès direct à la mer par le contrôleur jusqu'à la fin de la construction.

### Refactoring
- Le design du projet a été adapté pour intégrer le **SeaBuilder** tout en conservant les règles d'architecture initiales.
- Le code est structuré pour permettre une réutilisation et une extension faciles.

---

## Instructions d'Utilisation

### Installation
1. Clonez le repository depuis Bitbucket :
   ```bash
   git clone <repository_url>
   cd combat-naval
   ```
2. Assurez-vous d'avoir JavaFX configuré sur votre système.

### Lancement
1. Exécutez la version JavaFX :
   ```bash
   java -cp . MainJavaFX
   ```
2. Pour exécuter la version console :
   ```bash
   java -cp . MainConsole
   ```

---

## Agenda des Échéances

### Itération 2
- **Code source complet** : 19/03 (étudiants du jour), 20/03 (étudiants du soir).

### Itération 3
- **Code source avec placement manuel** : 23/04 (étudiants du jour), 24/04 (étudiants du soir).

---





