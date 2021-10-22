# Optimisation du traitement dans BatchProcessService.computeIdOccurrencesFromLogDirectory
* Ne pas multiplier à l'infini les sous-méthodes de traitement
	-> réintégrer addOrUpdateURLStatsOccurrence dans la méthode process ?
* Boucles for sur URLStat
	-> dans addOrUpdateList (maj liste globale urlStat à partir des urlStats de chaque fichiers)
	-> dans findHighestOccurrences on boucle à nouveau pour obtenir la liste des + grdes occurrences
	-> dans getHighestUrlsOccurrences on reboucle sur urlStats pour ne conserver que celles ayant les + grdes occurrences et construire la liste des URLOccurrence
		-> dans la sous-méthode findUoccCorrepondingToUrlStats, on boucle sur la liste des URLOccurrence pour incrémenter ou ajouter une nouvelle URLOccurrence
=> voir si ce nombre de boucles est réductible

# Augmenter la couverture de tests
* Tester plus de méthodes de BatchProcessService
* Créer des tests pour les méthodes génériques
* Pousser plus loin la recherche pour mocker "System.in" afin de simuler des entrées utilisateur dans les tests (test UserInputServiceTest est désactivé)

# Simplification du code - A regarder
* Pousser plus loin l'utilisation des Collectors
Liens :
 -> https://blog.jooq.org/tag/joo%CE%BB/
 -> https://www.baeldung.com/streamex Collectors groupBy
* Itérer sur des streams en utilisant des indices :
  -> https://www.baeldung.com/java-stream-indices
  
# Monitorer l'application
-> Spring Boot Actuator (and micrometer) ?

# Intégration continue

# Evolution de l'application avec des traitements plus lourds
-> Refonte vers Spring Integration ?
-> urls stockés dans une base de données en mémoire ou sur disque ? (filtrage d'un plus grand volume de données, limitation de la taille mémoire)


 
