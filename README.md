# Arena Legends — Simulateur de tournoi

## Présentation

**Arena Legends** est un simulateur de tournoi développé en **Java pur**, fonctionnant en console et sans framework.

Le projet permet de mettre en pratique plusieurs notions de programmation Java :

* conditions et boucles ;
* tableaux 1D et 2D ;
* `ArrayList` ;
* encapsulation ;
* héritage ;
* classes abstraites ;
* polymorphisme ;
* méthodes `static` ;
* copie défensive ;
* tri manuel ;
* gestion d'un tournoi à élimination directe.

Le tournoi final permet à **8 combattants** de s'affronter jusqu'à déterminer un champion. Le sujet demande également d'afficher le classement et les statistiques par classe.

---

# 1. Structure du projet

Le projet est composé principalement des classes suivantes :

```text
model/
├── Main.java
├── StatsArene.java
├── Combattant.java
├── Guerrier.java
├── Mage.java
├── Voleur.java
├── Paladin.java
└── Tournoi.java
```

### `Main`

Contient le menu principal et permet de tester les différentes fonctionnalités du projet.

### `StatsArene`

Contient les traitements sur les tableaux de scores et la grille de l'arène.

### `Combattant`

Classe abstraite contenant les caractéristiques communes aux différents combattants.

### `Guerrier`, `Mage`, `Voleur`, `Paladin`

Classes spécialisées héritant de `Combattant`.

### `Tournoi`

Gère les inscriptions, les désinscriptions, les duels, le tournoi à élimination directe, le classement et les statistiques par classe.

---

# 2. Question README — Pourquoi un `do...while` est-il plus adapté qu'un `while` pour un menu ?

Un `do...while` est particulièrement adapté pour un menu car le menu doit être affiché **au moins une fois**, avant de vérifier si l'utilisateur souhaite quitter.

Avec un `while`, il faudrait vérifier la condition avant la première exécution.

Avec un `do...while`, le fonctionnement est naturellement :

```text
Afficher le menu
      ↓
Demander le choix
      ↓
Exécuter l'action
      ↓
Le choix est-il différent de 0 ?
      ↓
Oui → recommencer
Non → quitter
```

Exemple :

```java
do {
    afficherMenu();
    choix = sc.nextInt();

    switch (choix) {
        // ...
    }

} while (choix != 0);
```

Le menu est donc toujours affiché au moins une fois.

---

# 3. Question README — Différence entre `int[] b = a` et recopier un tableau case par case

Lorsque l'on écrit :

```java
int[] a = {10, 20, 30};
int[] b = a;
```

`a` et `b` désignent **le même tableau en mémoire**.

Par conséquent :

```java
b[0] = 100;
```

modifie également `a` :

```text
a = {100, 20, 30}
b = {100, 20, 30}
```

On n'a pas créé une copie du tableau.

Pour créer un véritable nouveau tableau, il faut recopier les valeurs :

```java
int[] a = {10, 20, 30};
int[] b = new int[a.length];

for (int i = 0; i < a.length; i++) {
    b[i] = a[i];
}
```

Maintenant, `a` et `b` sont deux tableaux différents.

Si on fait :

```java
b[0] = 100;
```

on obtient :

```text
a = {10, 20, 30}
b = {100, 20, 30}
```

Cette notion est importante dans le projet, notamment pour éviter de modifier directement les données originales.

---

# 4. Question README — Pourquoi un setter `setPv(int pv)` casserait-il l'encapsulation ?

Un setter comme :

```java
public void setPv(int pv) {
    this.pv = pv;
}
```

permettrait à n'importe quel code extérieur de modifier directement les PV du combattant.

Même avec une vérification :

```java
if (pv >= 0 && pv <= pvMax) {
    this.pv = pv;
}
```

le problème reste présent : le code extérieur pourrait choisir arbitrairement les PV du combattant.

Par exemple :

```java
combattant.setPv(1);
```

ou :

```java
combattant.setPv(100);
```

Cela contourne les règles du jeu.

Dans Arena Legends, les PV doivent évoluer uniquement à travers :

```java
subirDegats(int d)
```

et :

```java
soigner(int s)
```

Ces méthodes permettent de contrôler les règles :

* les dégâts prennent en compte la défense ;
* les dégâts minimums sont respectés ;
* les PV ne descendent pas sous 0 ;
* les soins ne dépassent pas `pvMax` ;
* un combattant K.O. ne peut pas être soigné.

## L'absence de `setPv()` permet donc de mieux garantir l'état interne du combattant. Le sujet précise d'ailleurs qu'il ne doit pas être possible de mettre le combattant dans un état absurde depuis l'extérieur.

# 5. Pourquoi utiliser `protected` pour `subirDegatsBruts()` ?

La méthode :

```java
protected void subirDegatsBruts(int d)
```

est utilisée lorsqu'une sous-classe doit pouvoir infliger des dégâts en ignorant la défense.

Elle ne doit cependant pas être `public`, car cela permettrait à n'importe quelle classe extérieure d'appeler directement cette méthode.

Avec `protected`, elle reste accessible aux classes qui héritent de `Combattant`, notamment le `Mage`.

Cela permet donc de respecter l'encapsulation tout en donnant aux classes filles un accès contrôlé à certains comportements internes.

Le sujet demande précisément cette méthode `protected` pour le cas du Mage.

---

# 6. Question README — Polymorphisme avec `Combattant c = new Mage(...)`

Avec :

```java
Combattant c = new Mage(...);
c.attaquer(x);
```

le **type déclaré** de `c` est :

```java
Combattant
```

mais son **type réel** est :

```java
Mage
```

Comme `attaquer()` est une méthode abstraite redéfinie dans les classes filles, Java utilise la méthode correspondant au type réel de l'objet.

Ainsi :

```java
c.attaquer(x);
```

appelle :

```java
Mage.attaquer(x)
```

et non une méthode générique de `Combattant`.

C'est le principe du **polymorphisme** : une référence de type parent peut désigner un objet d'une classe fille, et la méthode redéfinie de l'objet réel est exécutée.

Cela permet notamment au `Tournoi` de travailler avec :

```java
Combattant
```

sans avoir besoin de savoir s'il s'agit d'un Guerrier, d'un Mage, d'un Voleur ou d'un Paladin.

Le sujet demande explicitement l'utilisation d'une classe `Combattant` abstraite avec `attaquer(Combattant cible)`.

---

# 7. Encapsulation et héritage

La classe `Combattant` contient les caractéristiques communes :

```java
private String nom;
private int pvMax;
private int pv;
private int attaque;
private int defense;
```

Ces attributs sont `private`.

Les classes filles utilisent les getters ou les méthodes `protected` pour accéder aux fonctionnalités nécessaires.

Cela évite qu'une classe extérieure puisse modifier directement les données internes.

Les règles principales sont :

| Attribut  | Règle                              |
| --------- | ---------------------------------- |
| `nom`     | entre 3 et 15 caractères, non null |
| `pvMax`   | entre 50 et 300                    |
| `pv`      | entre 0 et `pvMax`                 |
| `attaque` | entre 5 et 50                      |
| `defense` | entre 0 et 30                      |

## Ces contraintes correspondent aux règles du sujet.

# 8. Les différents combattants

## Guerrier

Le Guerrier possède une jauge de rage comprise entre 0 et 100.

Chaque attaque augmente la rage de 20.

Lorsque la rage atteint 100 :

* les dégâts sont doublés ;
* la rage revient à 0.

---

## Mage

Le Mage possède une réserve de mana.

Lorsqu'il possède suffisamment de mana, il peut lancer son sort :

```text
Dégâts = attaque × 2
```

Le sort ignore également la défense.

Lorsqu'il n'a pas suffisamment de mana, son attaque est réduite et il récupère du mana.

---

## Voleur

Le Voleur possède une capacité d'esquive.

Il peut éviter complètement une attaque selon son pourcentage d'esquive.

Il possède également une probabilité de frapper deux fois.

---

## Paladin

Le Paladin hérite du Guerrier.

Il réutilise donc les mécanismes du Guerrier sans recopier son code.

Après une attaque, il récupère une partie des dégâts infligés.

Le sujet demande précisément d'utiliser l'héritage afin de réutiliser la logique du Guerrier sans copier-coller.

---

# 9. Historique des dégâts et copie défensive

Chaque combattant possède :

```java
private int[] historiqueDegats = new int[5];
```

Il contient les cinq derniers dégâts reçus.

Le getter ne retourne pas directement le tableau :

```java
public int[] getHistoriqueDegats() {
    return historiqueDegats.clone();
}
```

Cela constitue une **copie défensive**.

Ainsi, si une personne fait :

```java
int[] historique = combattant.getHistoriqueDegats();

historique[0] = 9999;
```

elle ne modifie pas le véritable historique du combattant.

Le sujet demande explicitement de démontrer cette protection dans le `main`.

---

# 10. Gestion du tournoi

La classe `Tournoi` contient :

```java
private ArrayList<Combattant> participants;
```

Les principales fonctionnalités sont :

```text
inscrire()
desinscrire()
duel()
lancer()
classement()
statsParClasse()
getParticipants()
```

## Inscription

Un tournoi accepte au maximum 8 combattants.

Deux combattants ne peuvent pas avoir le même nom, même si la casse est différente.

Ainsi :

```text
"Arya"
"arya"
"ARYA"
```

sont considérés comme le même nom.

---

# 11. Désinscription

La désinscription utilise un `Iterator`.

Il ne faut pas faire :

```java
for (Combattant c : participants) {
    if (...) {
        participants.remove(c);
    }
}
```

car modifier directement la liste pendant un `for-each` peut provoquer une :

```text
ConcurrentModificationException
```

L'utilisation de :

```java
Iterator<Combattant>
```

permet d'utiliser correctement :

```java
iterator.remove();
```

Le sujet demande explicitement cette précaution.

---

# 12. Duel

Deux combattants s'affrontent à tour de rôle.

Le combattant ayant la meilleure attaque commence.

Exemple :

```text
Guerrier ATK 20
Mage ATK 25
```

Le Mage commence.

Chaque tour est affiché dans la console.

Le duel continue jusqu'à ce qu'un combattant soit K.O.

Si le duel dépasse 50 tours, le vainqueur est déterminé selon le pourcentage de PV restants :

```text
PV actuels / PV maximum × 100
```

Le sujet demande cette règle afin d'éviter qu'un duel puisse durer indéfiniment.

---

# 13. Tournoi à élimination directe

Le tournoi utilise une liste temporaire de combattants.

Avec 8 participants :

```text
8 combattants
     ↓
4 duels
     ↓
4 vainqueurs
     ↓
2 duels
     ↓
2 vainqueurs
     ↓
1 duel
     ↓
Champion
```

Avant le début du tournoi, les participants sont mélangés.

Après chaque duel, les vainqueurs sont placés dans une nouvelle liste.

Les vainqueurs sont ensuite entièrement soignés avant le tour suivant.

Le processus continue jusqu'à ce qu'il ne reste qu'un seul combattant.

Cette organisation correspond au tournoi à élimination directe demandé dans le sujet.

---

# 14. Classement

Chaque combattant possède un compteur de victoires encapsulé :

```java
private int victoires;
```

Le compteur est augmenté lorsqu'il remporte un duel.

La méthode :

```java
classement()
```

retourne une `ArrayList<Combattant>` triée selon le nombre de victoires décroissant.

Le tri est réalisé manuellement et `Collections.sort()` n'est pas utilisé.

Exemple :

```text
1. Veigar — 3 victoires
2. Garen — 2 victoires
3. Pyke — 1 victoire
4. Darius — 0 victoire
```

Le sujet impose ce tri manuel.

---

# 15. Statistiques par classe

La méthode :

```java
statsParClasse()
```

additionne le nombre de victoires de tous les combattants appartenant à chaque classe.

Elle utilise :

```java
getClasse()
```

et non :

```java
instanceof
```

Exemple :

```text
===== STATISTIQUES PAR CLASSE =====

Guerrier : 5 victoires
Mage     : 4 victoires
Voleur   : 2 victoires
Paladin  : 3 victoires
```

Cette approche permet au `Tournoi` de ne pas dépendre directement des classes concrètes.

---

# 16. Question sur `getParticipants()`

La méthode :

```java
public ArrayList<Combattant> getParticipants() {
    return new ArrayList<>(participants);
}
```

retourne une copie de la liste.

Ainsi :

```java
tournoi.getParticipants().clear();
```

ne vide pas la véritable liste du tournoi.

Exemple :

```text
Avant clear : 8 participants
Après clear : 8 participants
```

La liste originale reste donc protégée.

C'est une nouvelle utilisation de la **copie défensive**. Le sujet demande explicitement ce test.

---

# 17. Test final

Le test final doit utiliser :

* 8 combattants ;
* au moins 2 Guerriers ;
* au moins 2 Mages ;
* au moins 2 Voleurs ;
* au moins 2 Paladins.

Le programme doit afficher :

===== ARENA LEGENDS : TOURNOI =====

===== PARTICIPANTS =====
Guerrier Tryndamere [100/100 PV] ATK 25 DEF 10
Mage Veigar [80/80 PV] ATK 30 DEF 5
Voleur Pyke [90/90 PV] ATK 20 DEF 8
Paladin Leona [120/120 PV] ATK 18 DEF 15
Guerrier Garen [110/110 PV] ATK 22 DEF 12
Mage Ahri [75/75 PV] ATK 28 DEF 4
Voleur Talon [85/85 PV] ATK 24 DEF 6
Paladin Taric [130/130 PV] ATK 15 DEF 18

===== TEST COPIE DEFENSIVE =====
Participants avant clear : 8
Participants après clear : 8

===== DEBUT DU TOURNOI =====

===== NOUVEAU TOUR =====

Duel : Ahri VS Veigar

===== DUEL =====
Veigar commence !

--- Tour 1 ---
Veigar attaque Ahri
Ahri reçoit 60 dégâts.
Ahri : 15/75 PV

--- Tour 2 ---
Ahri attaque Veigar
Veigar reçoit 56 dégâts.
Veigar : 24/80 PV

--- Tour 3 ---
Veigar attaque Ahri
Ahri reçoit 60 dégâts.
Ahri : 0/75 PV

K.O. ! Ahri est vaincu.
Vainqueur : Veigar
Vainqueur du duel : Veigar
Veigar est entièrement soigné : 80/80 PV

Duel : Taric VS Talon

===== DUEL =====
Talon commence !

--- Tour 1 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 2 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 3 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 4 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 5 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 6 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 7 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 8 ---
Taric attaque Talon
Talon esquive complètement l'attaque !
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 9 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 10 ---
Taric attaque Talon
Talon reçoit 30 dégâts.
Talon : 85/85 PV

--- Tour 11 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 12 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 13 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 14 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 15 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 16 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 17 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 18 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 19 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 20 ---
Taric attaque Talon
Talon esquive complètement l'attaque !
Talon reçoit 30 dégâts.
Talon : 85/85 PV

--- Tour 21 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 22 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 23 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 24 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 25 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 26 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 27 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 28 ---
Taric attaque Talon
Talon esquive complètement l'attaque !
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 29 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 30 ---
Taric attaque Talon
Talon reçoit 30 dégâts.
Talon : 85/85 PV

--- Tour 31 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 32 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 33 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 34 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 35 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 36 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 37 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 38 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 39 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 40 ---
Taric attaque Talon
Talon esquive complètement l'attaque !
Talon reçoit 30 dégâts.
Talon : 85/85 PV

--- Tour 41 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 42 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 43 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 44 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 45 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 46 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 47 ---
Talon attaque Taric
Taric reçoit 48 dégâts.
Taric : 130/130 PV

--- Tour 48 ---
Taric attaque Talon
Talon reçoit 15 dégâts.
Talon : 85/85 PV

--- Tour 49 ---
Talon attaque Taric
Taric reçoit 24 dégâts.
Taric : 130/130 PV

--- Tour 50 ---
Taric attaque Talon
Talon reçoit 30 dégâts.
Talon : 85/85 PV

===== LIMITE DE 50 TOURS =====
Taric : 100,00% de PV
Talon : 100,00% de PV
Égalité !
Vainqueur du duel : Taric
Taric est entièrement soigné : 130/130 PV

Duel : Leona VS Garen

===== DUEL =====
Garen commence !

--- Tour 1 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 2 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 3 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 4 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 5 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 6 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 7 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 8 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 9 ---
Garen attaque Leona
Leona reçoit 44 dégâts.
Leona : 120/120 PV

--- Tour 10 ---
Leona attaque Garen
Garen reçoit 36 dégâts.
Garen : 110/110 PV

--- Tour 11 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 12 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 13 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 14 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 15 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 16 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 17 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 18 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 19 ---
Garen attaque Leona
Leona reçoit 44 dégâts.
Leona : 120/120 PV

--- Tour 20 ---
Leona attaque Garen
Garen reçoit 36 dégâts.
Garen : 110/110 PV

--- Tour 21 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 22 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 23 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 24 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 25 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 26 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 27 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 28 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 29 ---
Garen attaque Leona
Leona reçoit 44 dégâts.
Leona : 120/120 PV

--- Tour 30 ---
Leona attaque Garen
Garen reçoit 36 dégâts.
Garen : 110/110 PV

--- Tour 31 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 32 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 33 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 34 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 35 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 36 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 37 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 38 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 39 ---
Garen attaque Leona
Leona reçoit 44 dégâts.
Leona : 120/120 PV

--- Tour 40 ---
Leona attaque Garen
Garen reçoit 36 dégâts.
Garen : 110/110 PV

--- Tour 41 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 42 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 43 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 44 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 45 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 46 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 47 ---
Garen attaque Leona
Leona reçoit 22 dégâts.
Leona : 120/120 PV

--- Tour 48 ---
Leona attaque Garen
Garen reçoit 18 dégâts.
Garen : 110/110 PV

--- Tour 49 ---
Garen attaque Leona
Leona reçoit 44 dégâts.
Leona : 120/120 PV

--- Tour 50 ---
Leona attaque Garen
Garen reçoit 36 dégâts.
Garen : 110/110 PV

===== LIMITE DE 50 TOURS =====
Leona : 100,00% de PV
Garen : 100,00% de PV
Égalité !
Vainqueur du duel : Leona
Leona est entièrement soigné : 120/120 PV

Duel : Pyke VS Tryndamere

===== DUEL =====
Tryndamere commence !

--- Tour 1 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 2 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 3 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 4 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 5 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 6 ---
Pyke attaque Tryndamere
Tryndamere reçoit 40 dégâts.
Tryndamere : 100/100 PV

--- Tour 7 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 8 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 9 ---
Tryndamere attaque Pyke
Pyke reçoit 50 dégâts.
Pyke : 90/90 PV

--- Tour 10 ---
Pyke attaque Tryndamere
Tryndamere reçoit 40 dégâts.
Tryndamere : 100/100 PV

--- Tour 11 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 12 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 13 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 14 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 15 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 16 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 17 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 18 ---
Pyke attaque Tryndamere
Tryndamere reçoit 40 dégâts.
Tryndamere : 100/100 PV

--- Tour 19 ---
Tryndamere attaque Pyke
Pyke reçoit 50 dégâts.
Pyke : 90/90 PV

--- Tour 20 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 21 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 22 ---
Pyke attaque Tryndamere
Tryndamere reçoit 40 dégâts.
Tryndamere : 100/100 PV

--- Tour 23 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 24 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 25 ---
Tryndamere attaque Pyke
Pyke esquive complètement l'attaque !
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 26 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 27 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 28 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 29 ---
Tryndamere attaque Pyke
Pyke reçoit 50 dégâts.
Pyke : 90/90 PV

--- Tour 30 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 31 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 32 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 33 ---
Tryndamere attaque Pyke
Pyke esquive complètement l'attaque !
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 34 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 35 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 36 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 37 ---
Tryndamere attaque Pyke
Pyke esquive complètement l'attaque !
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 38 ---
Pyke attaque Tryndamere
Tryndamere reçoit 40 dégâts.
Tryndamere : 100/100 PV

--- Tour 39 ---
Tryndamere attaque Pyke
Pyke reçoit 50 dégâts.
Pyke : 90/90 PV

--- Tour 40 ---
Pyke attaque Tryndamere
Tryndamere reçoit 40 dégâts.
Tryndamere : 100/100 PV

--- Tour 41 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 42 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 43 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 44 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 45 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 46 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 47 ---
Tryndamere attaque Pyke
Pyke reçoit 25 dégâts.
Pyke : 90/90 PV

--- Tour 48 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

--- Tour 49 ---
Tryndamere attaque Pyke
Pyke reçoit 50 dégâts.
Pyke : 90/90 PV

--- Tour 50 ---
Pyke attaque Tryndamere
Tryndamere reçoit 20 dégâts.
Tryndamere : 100/100 PV

===== LIMITE DE 50 TOURS =====
Pyke : 100,00% de PV
Tryndamere : 100,00% de PV
Égalité !
Vainqueur du duel : Pyke
Pyke est entièrement soigné : 90/90 PV

===== NOUVEAU TOUR =====

Duel : Veigar VS Taric

===== DUEL =====
Veigar commence !

--- Tour 1 ---
Veigar attaque Taric
Taric reçoit 60 dégâts.
Taric : 70/130 PV

--- Tour 2 ---
Taric attaque Veigar
Veigar reçoit 15 dégâts.
Veigar : 80/80 PV

--- Tour 3 ---
Veigar attaque Taric
Taric reçoit 15 dégâts.
Taric : 71/130 PV

--- Tour 4 ---
Taric attaque Veigar
Veigar reçoit 15 dégâts.
Veigar : 80/80 PV

--- Tour 5 ---
Veigar attaque Taric
Taric reçoit 15 dégâts.
Taric : 72/130 PV

--- Tour 6 ---
Taric attaque Veigar
Veigar reçoit 15 dégâts.
Veigar : 80/80 PV

--- Tour 7 ---
Veigar attaque Taric
Taric reçoit 60 dégâts.
Taric : 13/130 PV

--- Tour 8 ---
Taric attaque Veigar
Veigar reçoit 15 dégâts.
Veigar : 80/80 PV

--- Tour 9 ---
Veigar attaque Taric
Taric reçoit 15 dégâts.
Taric : 14/130 PV

--- Tour 10 ---
Taric attaque Veigar
Veigar reçoit 30 dégâts.
Veigar : 80/80 PV

--- Tour 11 ---
Veigar attaque Taric
Taric reçoit 15 dégâts.
Taric : 17/130 PV

--- Tour 12 ---
Taric attaque Veigar
Veigar reçoit 15 dégâts.
Veigar : 80/80 PV

--- Tour 13 ---
Veigar attaque Taric
Taric reçoit 60 dégâts.
Taric : 0/130 PV

K.O. ! Taric est vaincu.
Vainqueur : Veigar
Vainqueur du duel : Veigar
Veigar est entièrement soigné : 80/80 PV

Duel : Leona VS Pyke

===== DUEL =====
Pyke commence !

--- Tour 1 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 2 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 3 ---
Pyke attaque Leona
Leona reçoit 40 dégâts.
Leona : 120/120 PV

--- Tour 4 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 5 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 6 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 7 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 8 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 9 ---
Pyke attaque Leona
Leona reçoit 40 dégâts.
Leona : 120/120 PV

--- Tour 10 ---
Leona attaque Pyke
Pyke esquive complètement l'attaque !
Pyke reçoit 36 dégâts.
Pyke : 90/90 PV

--- Tour 11 ---
Pyke attaque Leona
Leona reçoit 40 dégâts.
Leona : 120/120 PV

--- Tour 12 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 13 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 14 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 15 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 16 ---
Leona attaque Pyke
Pyke esquive complètement l'attaque !
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 17 ---
Pyke attaque Leona
Leona reçoit 40 dégâts.
Leona : 120/120 PV

--- Tour 18 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 19 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 20 ---
Leona attaque Pyke
Pyke reçoit 36 dégâts.
Pyke : 90/90 PV

--- Tour 21 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 22 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 23 ---
Pyke attaque Leona
Leona reçoit 40 dégâts.
Leona : 120/120 PV

--- Tour 24 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 25 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 26 ---
Leona attaque Pyke
Pyke esquive complètement l'attaque !
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 27 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 28 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 29 ---
Pyke attaque Leona
Leona reçoit 40 dégâts.
Leona : 120/120 PV

--- Tour 30 ---
Leona attaque Pyke
Pyke reçoit 36 dégâts.
Pyke : 90/90 PV

--- Tour 31 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 32 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 33 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 34 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 35 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 36 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 37 ---
Pyke attaque Leona
Leona reçoit 40 dégâts.
Leona : 120/120 PV

--- Tour 38 ---
Leona attaque Pyke
Pyke esquive complètement l'attaque !
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 39 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 40 ---
Leona attaque Pyke
Pyke reçoit 36 dégâts.
Pyke : 90/90 PV

--- Tour 41 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 42 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 43 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 44 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 45 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 46 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 47 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 48 ---
Leona attaque Pyke
Pyke reçoit 18 dégâts.
Pyke : 90/90 PV

--- Tour 49 ---
Pyke attaque Leona
Leona reçoit 20 dégâts.
Leona : 120/120 PV

--- Tour 50 ---
Leona attaque Pyke
Pyke reçoit 36 dégâts.
Pyke : 90/90 PV

===== LIMITE DE 50 TOURS =====
Leona : 100,00% de PV
Pyke : 100,00% de PV
Égalité !
Vainqueur du duel : Leona
Leona est entièrement soigné : 120/120 PV

===== NOUVEAU TOUR =====

Duel : Veigar VS Leona

===== DUEL =====
Veigar commence !

--- Tour 1 ---
Veigar attaque Leona
Leona reçoit 15 dégâts.
Leona : 120/120 PV

--- Tour 2 ---
Leona attaque Veigar
Veigar reçoit 18 dégâts.
Veigar : 80/80 PV

--- Tour 3 ---
Veigar attaque Leona
Leona reçoit 15 dégâts.
Leona : 120/120 PV

--- Tour 4 ---
Leona attaque Veigar
Veigar reçoit 18 dégâts.
Veigar : 80/80 PV

--- Tour 5 ---
Veigar attaque Leona
Leona reçoit 60 dégâts.
Leona : 60/120 PV

--- Tour 6 ---
Leona attaque Veigar
Veigar reçoit 18 dégâts.
Veigar : 80/80 PV

--- Tour 7 ---
Veigar attaque Leona
Leona reçoit 15 dégâts.
Leona : 61/120 PV

--- Tour 8 ---
Leona attaque Veigar
Veigar reçoit 18 dégâts.
Veigar : 80/80 PV

--- Tour 9 ---
Veigar attaque Leona
Leona reçoit 15 dégâts.
Leona : 62/120 PV

--- Tour 10 ---
Leona attaque Veigar
Veigar reçoit 36 dégâts.
Veigar : 80/80 PV

--- Tour 11 ---
Veigar attaque Leona
Leona reçoit 60 dégâts.
Leona : 5/120 PV

--- Tour 12 ---
Leona attaque Veigar
Veigar reçoit 18 dégâts.
Veigar : 80/80 PV

--- Tour 13 ---
Veigar attaque Leona
Leona reçoit 15 dégâts.
Leona : 6/120 PV

--- Tour 14 ---
Leona attaque Veigar
Veigar reçoit 18 dégâts.
Veigar : 80/80 PV

--- Tour 15 ---
Veigar attaque Leona
Leona reçoit 15 dégâts.
Leona : 7/120 PV

--- Tour 16 ---
Leona attaque Veigar
Veigar reçoit 18 dégâts.
Veigar : 80/80 PV

--- Tour 17 ---
Veigar attaque Leona
Leona reçoit 60 dégâts.
Leona : 0/120 PV

K.O. ! Leona est vaincu.
Vainqueur : Veigar
Vainqueur du duel : Veigar
Veigar est entièrement soigné : 80/80 PV

===== CHAMPION DU TOURNOI =====
Mage Veigar [80/80 PV] ATK 30 DEF 5

===== CLASSEMENT FINAL =====
1. Veigar - 3 victoire(s)
2. Tryndamere - 0 victoire(s)
3. Pyke - 0 victoire(s)
4. Leona - 0 victoire(s)
5. Garen - 0 victoire(s)
6. Ahri - 0 victoire(s)
7. Talon - 0 victoire(s)
8. Taric - 0 victoire(s)

===== STATISTIQUES PAR CLASSE =====

===== STATISTIQUES PAR CLASSE =====
Guerrier : 0 victoire(s)
Mage     : 3 victoire(s)
Voleur   : 0 victoire(s)
Paladin  : 0 victoire(s)

===== FIN DU TOURNOI =====



Le sujet demande également de lancer le tournoi 100 fois sans affichage et d'indiquer quelle classe gagne le plus souvent afin de discuter de l'équilibrage.

---

# 18. Résultat des 100 simulations

Pour cette partie, il faut effectuer réellement les 100 simulations avec la version finale du programme.

Résultats obtenus :


Guerrier : 0 victoires
Mage     : 100 victoires
Voleur   : 0 victoires
Paladin  : 0 victoires


Classe ayant obtenu le plus de victoires : Mage


________________________
```

## Le tournoi est-il équilibré ?

Les résultats des 100 simulations permettent d'observer si une classe remporte beaucoup plus de tournois que les autres.

La classe Mage obtient une proportion très supérieure aux autres, cela indique un déséquilibre dans les règles de combat.

---

# Conclusion

Le projet **Arena Legends** permet de réunir plusieurs notions fondamentales de la programmation orientée objet en Java.

Le projet met notamment en œuvre :

```text
Conditions
   ↓
Boucles
   ↓
Tableaux
   ↓
ArrayList
   ↓
Encapsulation
   ↓
Héritage
   ↓
Polymorphisme
   ↓
Tournoi
```

La classe `Combattant` protège les données internes et définit le comportement commun des héros.

Les classes `Guerrier`, `Mage`, `Voleur` et `Paladin` spécialisent ce comportement.

Enfin, la classe `Tournoi` rassemble toutes ces fonctionnalités afin de simuler un tournoi complet à élimination directe et de déterminer un champion.
