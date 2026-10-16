# Quiz en Images - Application Android (TP Android)

Application Android développée en **Kotlin** avec une interface utilisateur déclarée en **XML** (sans Jetpack Compose et sans Fragment) dans le cadre du travail pratique *Quiz en images*.

---

## 📌 Présentation du Projet

L'application est un jeu de quiz composé de **cinq questions illustrées** sur le thème du **Sport** (Football, Basketball, Tennis, Cyclisme, Trophée).

### Fonctionnalités principales :
- **Questions illustrées** : Pour chaque question, l'utilisateur observe une image de sport et choisit parmi 3 propositions.
- **Gestion du score & progression** :
  - **Score** : +1 point par réponse correcte (score final sur 5).
  - **Progression** : Une `ProgressBar` avance de 0 à 5 selon le nombre de questions traitées.
- **Feedback dynamique** : Message explicite (vert/rouge) après chaque validation.
- **Écran de résultats & Rejouer** : Affichage du bilan final avec le pourcentage obtenu et option pour relancer le quiz.
- **Support de la rotation** : Sauvegarde complète de l'état du jeu lors des changements d'orientation de l'écran (`onSaveInstanceState`).

---

## 🛠️ Fiche Technique

- **Langage** : Kotlin
- **Architecture** : Single Activity (`MainActivity`) avec ViewBinding.
- **Interface UI** : Layout XML (`activity_main.xml`), `ScrollView`, `MaterialCardView`, `ProgressBar`, `MaterialButton`.
- **Images** : 5 Vector Drawables XML (`res/drawable/sport_*.xml`).
- **Tests** : Tests unitaires JUnit (`QuizUnitTest.kt`).

---

## 🚀 Comment Ouvrir et Lancer l'Application

### 1. Prérequis
- **Android Studio** (version Jellyfish / Ladybug ou supérieure).
- **JDK 11** ou plus récent.
- **Android SDK** (API minimum 24 - Android 7.0 / API Cible 34+).

### 2. Ouvrir le projet dans Android Studio
1. Lancez **Android Studio**.
2. Cliquez sur **File > Open...** (ou *Open An Existing Project*).
3. Naviguez jusqu'au dossier racine du projet `QuizzApp` et cliquez sur **OK**.
4. Patientez pendant que Gradle synchronise automatiquement les dépendances et le projet.

### 3. Exécuter l'application (Run)
1. Démarrez un **émulateur Android** (AVD) ou branchez un **appareil Android physique** en mode Débogage USB.
2. Dans la barre d'outils supérieure d'Android Studio, vérifiez que le module **`app`** et votre appareil sont sélectionnés.
3. Cliquez sur le bouton vert **Run 'app'** ▶️ (ou utilisez le raccourci `Shift + F10`).

### 4. Lancer les tests unitaires
Pour vérifier le bon fonctionnement de la logique du quiz :
- Ouvrez le terminal d'Android Studio et lancez :
  ```bash
  ./gradlew test
  ```
- Ou effectuez un clic droit sur le fichier [`QuizUnitTest.kt`](app/src/test/java/com/example/quizzapp/QuizUnitTest.kt) et choisissez **Run 'QuizUnitTest'**.

### 5. Générer / Récupérer le fichier APK
L'APK compilé se trouve dans le dossier de build :
```text
app/build/outputs/apk/debug/app-debug.apk
```
Pour le générer via la ligne de commande :
```bash
./gradlew assembleDebug
```

---

## 📁 Structure du Dépôt GitHub

```
QuizzApp/
├── app/                        # Module principal de l'application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/quizzapp/
│   │   │   │   ├── MainActivity.kt   # Activité unique & gestion du quiz
│   │   │   │   └── Question.kt       # Data class du modèle de question
│   │   │   └── res/
│   │   │       ├── drawable/         # Images vectorielles des sports (sport_*.xml)
│   │   │       ├── layout/           # Activity Layout (activity_main.xml)
│   │   │       └── values/           # Chaînes de caractères, couleurs et thèmes
│   │   └── test/                     # Tests unitaires JUnit (QuizUnitTest.kt)
│   └── build.gradle.kts        # Configuration du module App
├── captures/                   # Dossier contenant les captures d'écran de l'application
│   └── README.md
├── compte-rendu/               # Compte rendu détaillé du travail pratique
│   └── compte-rendu.md
├── .gitignore                  # Fichier d'exclusion Git (exclut build/ et local.properties)
├── build.gradle.kts            # Fichier de build racine
└── README.md                   # Ce fichier
```
