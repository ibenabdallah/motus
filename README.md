# Motus

Motus est un [jeu télévisé français](https://fr.wikipedia.org/wiki/Motus_(jeu_t%C3%A9l%C3%A9vis%C3%A9)) d'origine américaine, diffusé sur Antenne 2 puis sur France 2 du 25 juin 1990 au 31 août 2019 et présenté par Thierry Beccaro.

API de mots open source :  https://pastes.io/raw/iys4katchh

# Screenshots :

<p float="left">
  <img width="30%" height="60%" src="docs/screen-1.jpg" />
  <img width="30%" height="60%" src="docs/screen-2.jpg" />
  <img width="30%" height="60%" src="docs/screen-3.jpg" />
</p>

# Architecture & Choix techniques

### Clean Architecture :
Elle réduire les dépendances de logique métier avec les services que l'app consomme (API, Base de
données, Framework, Librairies tierces), et pour maintenir une application stable au cours de ses
évolutions, de ses tests mais également lors de changements ou mises à jour des ressources externes.

<img src="docs/clean-architecture.png" />

### Coroutines Kotlin :
Elle fournissent une API qui vous permez d'écrire du code asynchrone. Avec les coroutines Kotlin,
vous pouvez définir unCoroutineScope qui vous aide à gérer quand vos coroutines doivent s'exécuter.
Chaque opération asynchrone s'exécute dans une thread particulière.

### Hilt :
Hilt est une bibliothèque d'injection de dépendances pour Android qui réduit le passe-partout de l'
injection manuelle de dépendances dans votre projet. L'injection manuelle de dépendances vous oblige
à construire chaque classe et ses dépendances à la main, et à utiliser des conteneurs pour
réutiliser et gérer les dépendances.

### Retrofit :
C'st un client HTTP de type sécurisé pour Android. Retrofit facilite la connexion à un service Web
REST en traduisant l'API en interfaces. Elle est puissante facilite la consommation de données JSON
ou XML, qui sont ensuite analysées en objets. Les requêtes GET, POST, PUT, PATCH et DELETE peuvent
toutes être exécutées. Retrofit utilise #OkHttp (du même développeur) pour gérer les requêtes
réseau. De plus, Retrofit utile le convertisseur #Gson pour analyser les objets JSON.

### UI Compose :
Créez de meilleures applications plus rapidement avec Composition du Jetpack Jetpack Compose est la
boîte à outils moderne d'Android pour créer une interface utilisateur native. Il simplifie et
accélère le développement de l'interface utilisateur sur Android. Donnez rapidement vie à votre
application avec moins de code, des outils puissants et des API Kotlin intuitives.

# Android, Architecture Components & jetpack

https://developer.android.com/jetpack/docs/guide

# Open Source Libraries

Kotlin      : https://kotlinlang.org

Retrofit    : https://square.github.io/retrofit

OkHttp      : https://square.github.io/okhttp

hilt        : https://dagger.dev/hilt/

Coroutines  : https://github.com/Kotlin/kotlinx.coroutines

Compose     : https://developer.android.com/jetpack/compose


## Contribuant :writing_hand:

Les contributions sont les bienvenues ! Si vous rencontrez des problèmes ou avez des suggestions d'améliorations, veuillez ouvrir un problème ou soumettre une pull request. Veuillez vous assurer que vos contributions respectent le style de codage et les directives du projet.

## Contributors 📢

<a href="https://github.com/ibenabdallah/motus/graphs/contributors">
    <img src="https://contrib.rocks/image?repo=ibenabdallah/motus"/>
</a>

## License :spiral_notepad:

Ce projet est sous licence [MIT License](LICENSE).

## Connectez-vous avec moi :man_shrugging:
[![](https://img.shields.io/badge/gmail-%23DD0031.svg?&style=for-the-badge&logo=gmail&logoColor=white)](mailto:benabdallahismail121@gmail.com?subject=[GitHub]%20Source%20Motus)
[![](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/baismail/)
[![](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/Ingbaismail)