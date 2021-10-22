 Introduction

Dans le dossier «logsToScan» présent à la racine du projet, se trouvent des fichiers avec URLs. La plupart ont un paramètre «id».

Ce programme permet de déterminer quels sont les 5 «id» que l’on retrouve dans le plus d’URLs (en utilisant le langage de votre choix).

Avec le jeu de tests "logsToScan" présent à la racine du projet, le programme batch affiche les résultats suivants :

Ids qui ont le plus grand nombre d'occurences


La commande shell " sed -E 's@.*id=([-_a-zA-Z0-9]*).*@\1@' logsToScan/* | grep bid.org | grep 535 | wc -l " (sous Linux), permet de vérfier que le nombre d'occurrences en résultat du batch correspond à celui de la commande shell (ex. avec "id=535"

Suite à un problème technique, cette version ne comporte pas de tests JUnit fonctionnels - le lancement de ces tests entraîne soit un problème d'injection de dépendances, soit un lancement du prompt utilisateur qui bloque leur exécution. Ces tests sont par conséquents désactivés dans cette version.

# Configuration

Le fichier "application.properties" situé dans le dossier "src/main/resources" permet de paramétrer :

* le nombre de lignes traitées dans un cycle du batch ( clé batch.process.size)
* le protocole des urls contenues dans les logs à validef ( clé urls.types.to.validate)
* le nombre de clés ayant le plus d'occurrences ( clé value.nb.with.highest.occur.to.find, définie à la valeur 5 pour respecter l'énoncé)
# Environnement système

Le programme a été testé sous Win. 10 et sous Linux Ubuntu (wsl 2) avec les versions de Java et Maven suivantes :
mvn --version
Apache Maven 3.6.3
Java version: 11.0.10, vendor: Azul Systems

# Compilation et packaging

Dans un terminal à la racine du projet, entrer la commande :
mvn clean package

# Lancement du programme

Dans un terminal à la racine du projet, entrer la commande :
java -jar target/urlsbatch-0.0.1-SNAPSHOT.jar --log.dir.path=logsToScan --spring.profiles.active=prod

# Déroulement du programme

Après le lancement, un prompt s'affiche permettant à l'utilisateur d'indiquer le chemin du répertoire où sont situés les fichiers de logs si aucun Path n'a été renseigné en paramètre

> .\logsToScan

Il est possible d'entrer un chemin relatif ou absolu. Le dossier de jeu de tests à la racine du projet est nommé "logsToScan".

