# Paleo

# Description

Une calculatrice améliorer écrit en Java. 

## Dependencies

Vous avez besoin de :

* `maven` >= 4.0
* `make`
 
## Build

Pour compiler le projet, faite :

```zsh
$ make
```

Pour générer la documentation, faite :

```zsh
$ make doc
```

Pour nettoyer le projet, exécutez : 

```zsh
$ make clean
```

## Execution

Pour executer la calculatrice, lancer :

```zsh
$ make run
```

or

```zsh
$ java -jar paleo-calculator.jar
```

## Utilisation basique de la calculatrice

La calculatrice utilise ici une syntaxe infix et non la syntaxe RPN (polonaise inversée)
Par exemple : 
` 5 + 5 ` est bon et s'évaluera mais
` 5 5 + ` n'est pas bon (il va montrer un message d'erreur qui dira qu'il ne comprend pas) 

Autres exemples qui fonctionne :
` (5 + 10) - 10 * 54 `
` 5 `
` -5 + 10 `

La calculatrice fonctionne en générale de cette manière :
	1/ L'application attend une entrée de l'utilisateur
	2/ Un Parseur va ensuite transformer cette entrée en une liste de Token 
	3/ Une Interpreteur va ensuite calculer la sortie à partir de la liste créer par le parseur.
	4/ L'application affiche enfin le résultat de l'interpreteur.

### Fonctionaliter/Extension 
## Extension 1 : La Calculatrice Multi-Type

La Calculatrice peut être utiliser avec plusieurs type.
Pour l'instant, les implémentés sont :

# Nombre Réel 

L'application reconnais un nombre réel si le mot n'est composer que de chiffre.
Exemple : `5` ; `78` ; `0` ; ...
Les opérations qui peuvent être utilisé sont : `+` | `-` | `/` | `*`
Exemple : ` 5 + 5` ; `(-5 + 8) * 6 ` ; ...

# Nombre décimaux

L'application reconnais un nombre decimale si le mot commence n'est composer que de chiffre et d'un seul et unique '.'
Exemple : `5.` ; `78.64` ; `0.0` ; ...
Les opérations qui peuvent être utilisé sont les mêmes que ceux dans les nombres réels (`+`| `-` | `/` | `*`)
On peut mélanger les nombres réels et décimaux dans une expression.
Exemple : ` 5 + 5.4 ` ; ` (4.0 + 8.0) * 6 ` ; ...


# Boolean 

L'application reconnait un boolean si le mot est soit "true" ou "false" 
Les opérations qui peuvent être utilisé sont : `and` | `or` | `not`
Exemple : ` true and not (true or false) ` ; ` true ` ; ` false ` ; ...

# Set

L'application reconnait un ensemble si le mot commence avec '{', puis quelques valeurs (cela peut être un réel, un décimal ou un boolean)
séparé avec ' ' ou ';' ou ',' et enfin fini avec '}'.
Exemple : ` { 1 ; 2.0 ; 3 ; true } ` ; `{}` ; `{ false }` ; ...
Les opérations qui peuvent être utilisé sont : `union` | `inter` | `diff`
Exemple : `{1} union {5;6}` ; `{false ; 1.0} inter {true ; false}` ; `{1;2;3;4;5;6} diff ({1} union {3;5})`

# Comment ça marche ?

La calculatrice utilise une map <clef;evaluateur>. L'interpreteur calcule en premier la clef à partir de l'opération
et des opérandes (que l'on peut voir comme la signature de l'opération) et chercher si il y a un evaluateur avec la même 
signature pour pouvoir l'évaluer.
Chaque nouveau type ajouté doit implémenter leurs evaluateurs et les ajouter dans le dictionnaire d'opération.
De cette manière, les opérandes peuvent être de différents types et les clients pourront ajoutés facilement
un nouveau type si ils le souhaitent.

## Extension 2 : Syntaxe Infix

Comme il a été dit plus haut dans la section "Utilisation basique de la calculatrice", la calculatrice utilise la 
syntaxe infix au lieu de la syntaxe RPN.

# Comment ça marche ?

L'interpreteur utilisé implémente tout simplement l'algorithme de l'évaluation des expressions infixes.
Vous pouvez trouver la documentation ici : https://algorithms.tutorialhorizon.com/evaluation-of-infix-expressions/ 
Grâce à l'architecture actuelle de la calculatrice, il devrait être possible de faire un interpréteur RPN qui peut 
remplacer l'interpreteur infixe pour pouvoir transformer notre calculatrice infixe en une calculatrice RPN.


## Extension 3 : Historique

La calculatrice contient un historique qui peut être utilisé pour pouvoir rappeler des anciennes valeurs et les réutilliser
pour de nouvelles expression.

A chaque fois qu'une evaluation a été calculer, la valeur est par la même occasion sauvegarder dans l'historique dans l'index
indiquer avant la sortie.
Exemple : `(2) : {5; 6; 7; 2; 1}` veut dire que `{5; 6; 7; 2; 1}` est stocker dans l'historique à l'index 2

Pour rappeler une valeur dans l'historique, utiliser `hist(` indexe_demander `)`
Exemple : `hist(2)` va rappeler la valeur de l'historique de l'index 2

Il est aussi possible d'utiliser une valeur rappeler dans une nouvelle expression :
Exemple : `hist(2) + 5.0` marche (si la valeur contenu dans hist(2) est un nombre bien sur ) 

Pour lister toutes les valeurs stocker dans l'historique, utiliser la commande `ls`
Pour afficher la documentation de l'historique, utiliser la commande `help`

# Comment ça marche ?

L'historique utilise une Arraylist. A chaque fois qu'une expression a été evalué dans l'application, il va aussi être ajouter dans
l'ArrayList de l'historique à l'index voulu. Quand on rapelle une valeur, cela créer un token historique qui va ensuite se substituer avec
la valeur retenu dans l'ArrayList à l'index qui lui correspond.

### Package 
paleo-lib : Ce package contiens toutes l'implémentation de la calculatrice ainsi que ces extensions (multi-type, l'historique, ...)
Son rôle en général est de pouvoir calculer une sortie (sous la forme d'une OperandToken ou d'un message d'erreur) à partir 
d'une entrée (String)

paleo-demo : Ce package est le point d'entrée du programme. C'est ici que l'on entre l'entrée utilisateur et que le résultat
du calcul de la calculatrice est afficher.

On a séparer ces deux modules pour par exemple si un client voudrait créer une calculatrice graphique avec comme base notre calculatrice,
il peut facilement enlever le package paleo-demo qui donne pour l'instant une interface textuelle avec un autre module graphique.
paleo-demo est entre autre un exemple d'utilisation de notre module de calculatrice








