# Nim

Lars Wächter - 5280456

## Anleitung

### 1. Hauptmenü

Startet man das Spiel, wird man mit folgendem Dialog begrüßt:

```
Welcome, what do you want to do?
Play (p) or run tests (t): 
```

An dieser Stelle wählt man, ob man spielen oder die Testdurchläufe starten möchte.
Hierbei wählt man jeweils mit "p" oder "t" (ohne """)


### 2. Gegnerwahl

Hat man sich zum Spielen entscheidet, erscheint folgender Dialog:

````
Choose your opponent
Nim (1) or NimPerfect (2): 
````

Hier wählt man seinen Gegenspieler.


### 3. Board-Erstellung

Nachdem man seinen Gegner ausgewählt hat, erstellt man das Board

````
Enter sticks per row (Default: 3-4-5) or create a random board (r): 
````

Hierbei gibt es drei Eingabemöglichkeiten:

1. Leere Eingabe => Das default Board wird erstellt (3-4-5)
2. r => Ein zufälliges Board wird erstellt
3. X-X-X-X-X => Ein Board im gegebenen Format wird erstellt

Gibt man sein eigenes Board ein, also 3. Fall, sind analog den Anforderungen nur folgende Boards zulässig:

- Mind. 2 und max. 5 Reihen
- Mind. 1 und max. 7 Hölzer pro Reihe

Das vom Benutzer eingegebene Board wird auf diese Bedingungen überprüft.

### 4. Spielablauf

Nach der Board-Erstellung wird folgender Text angezeigt: (Board kann unterschiedlich sein)

````
Let's start!

 (1)	I 
 (2)	I I 
 (3)	I I I 
 (4)	I I I I 
 (5)	I I I I I 

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 
````

Der menschliche Spieler beginnt. Dabei hat er folgende Auswahlmöglichkeiten

1. Er spielt den Zug selbst (1)
2. Er lässt den NPC / KI den Zug für sich spielen (2)
3. Er macht eine Anzahl an Zügen rückgängig (3)

#### Fall 1 - Er spielt den Zug:

Möchte der menschliche Spieler den Zug selbst spielen, bekommt er folgenden Text angezeigt:

````
Enter your move in a format like row.amount: 
````

Hierbei gibt man ein, in welcher Reihe man wie viele Hölzchen weggenommen werden sollen. Das Format ist "Reihe.AnzahlHölzchen".

Beispiele:

- 3.5 - Entfernt 5 Hölzchen aus Reihe 3
- 1.1 - Entfernt 1 Hölzchen aus Reihe 1


#### Fall 2 - Er lässt den NPC / KI den Zug für sich spielen:

Lässt der Spieler den NPC bzw. die KI den Zug für sich spielen, spielt diese den bestmöglichen Zug.
Das kann wie folgt aussehen:

````
Okay, the NPC is playing for you: 3.1
````

#### Fall 3 - Er macht eine Anzahl an Zügen rückgängig

Möchte der Spieler einen Zug rückgängig machen, bekommt er folgenden Dialog angezeigt:

````
How many moves do you want do undo? (0-4): 
````

In Klammern steht die Anzahl an Zügen, die der Spieler momentan rückgängig machen kann.
Nachdem man sich entschieden hat, bekommt man folgende Bestätigung angezeigt:

````
Okay, I undo the last X moves.
````

**Achtung**: Macht man nur einen Spielzug rückgängig, ist sofort wieder der Gegner, also die KI, am Zug.
Diese wird anschließend sofort wieder ihren besten Zug spielen.

---

Nachdem man sich für seinen Zug entschieden hat, spielt die KI ihren bestmöglichen Zug.
Das kann wie folgt aussehen:

````
Your opponent is playing: 3.2
````


### 5. Spielende

Ist das Spiel zu Ende, erscheint folgender Dialog: (Gewinner kann unterschiedlich sein)

````
 (1)	
 (2)	
 (3)	
 (4)	
 (5)	

Game finished. You won :)
Leave empty and press <ENTER> to restart, undo a number of moves (u) or quit (q): 
````

Hierbei hat man folgende Eingabemöglichkeiten:

1. Leere Eingabe => Das Spiel startet neu
2. u => Er macht eine Anzahl an Zügen rückgängig
3. q => Das Anwendung wird beendet

## Besonderheiten

- Die Methode `bestMove` ist in den Klassen `Nim` und `NimPerfect` jeweils rekursiv
implementiert, um `unmutable` anstelle von `mustable` Lists verwenden zu können
- Teilweise kam es zu Überschneidungen zwischen dem NimGame und dem Minimax Interface

## Aufzeichnung eines Spiels gegen Nim

````
Welcome, what do you want to do?
Play (p) or run tests (t): p

Choose your opponent
Nim (1) or NimPerfect (2): 1

Enter sticks per row (Default: 3-4-5) or create a random board (r): 1-2-3-4-5

Let's start!

 (1)	I 
 (2)	I I 
 (3)	I I I 
 (4)	I I I I 
 (5)	I I I I I 

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 1

Enter your move in a format like row.amount: 2.2

 (1)	I 
 (2)	
 (3)	I I I 
 (4)	I I I I 
 (5)	I I I I I 

Your opponent is playing: 3.3

 (1)	I 
 (2)	
 (3)	
 (4)	I I I I 
 (5)	I I I I I 

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 1

Enter your move in a format like row.amount: 4.1

 (1)	I 
 (2)	
 (3)	
 (4)	I I I 
 (5)	I I I I I 

Your opponent is playing: 5.3

 (1)	I 
 (2)	
 (3)	
 (4)	I I I 
 (5)	I I 

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 1

Enter your move in a format like row.amount: 4.1

 (1)	I 
 (2)	
 (3)	
 (4)	I I 
 (5)	I I 

Your opponent is playing: 1.1

 (1)	
 (2)	
 (3)	
 (4)	I I 
 (5)	I I 

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 1

Enter your move in a format like row.amount: 4.1

 (1)	
 (2)	
 (3)	
 (4)	I 
 (5)	I I 

Your opponent is playing: 5.1

 (1)	
 (2)	
 (3)	
 (4)	I 
 (5)	I 

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 2

Okay, the NPC is playing for you: 4.1

 (1)	
 (2)	
 (3)	
 (4)	
 (5)	I 

Your opponent is playing: 5.1

 (1)	
 (2)	
 (3)	
 (4)	
 (5)	

Game finished. You lost :(
Leave empty and press <ENTER> to restart, undo a number of moves (u) or quit (q): 
````

## Aufzeichnung eines Spiels gegen NimPerfect

````
Welcome, what do you want to do?
Play (p) or run tests (t): p

Choose your opponent
Nim (1) or NimPerfect (2): 2

Enter sticks per row (Default: 3-4-5) or create a random board (r): r

Generating random board...

Let's start!

 (1)	I I I I I 
 (2)	I I I I I I 
 (3)	I I I I I I I 
 (4)	I 

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 1

Enter your move in a format like row.amount: 4.1

 (1)	I I I I I 
 (2)	I I I I I I 
 (3)	I I I I I I I 
 (4)	

Your opponent is playing: 3.4

 (1)	I I I I I 
 (2)	I I I I I I 
 (3)	I I I 
 (4)	

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 2

Okay, the NPC is playing for you: 2.2

 (1)	I I I I I 
 (2)	I I I I 
 (3)	I I I 
 (4)	

Your opponent is playing: 3.2

 (1)	I I I I I 
 (2)	I I I I 
 (3)	I 
 (4)	

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 1

Enter your move in a format like row.amount: 3.1

 (1)	I I I I I 
 (2)	I I I I 
 (3)	
 (4)	

Your opponent is playing: 1.1

 (1)	I I I I 
 (2)	I I I I 
 (3)	
 (4)	

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 3

How many moves do you want do undo? (0-6): 2

Okay, I undo the last 2 moves.

 (1)	I I I I I 
 (2)	I I I I 
 (3)	I 
 (4)	

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 1

Enter your move in a format like row.amount: 1.5

 (1)	
 (2)	I I I I 
 (3)	I 
 (4)	

Your opponent is playing: 2.3

 (1)	
 (2)	I 
 (3)	I 
 (4)	

It's your turn, who should play your move?
You (1) or NPC (2) or undo a move (3): 1

Enter your move in a format like row.amount: 2.1

 (1)	
 (2)	
 (3)	I 
 (4)	

Your opponent is playing: 3.1

 (1)	
 (2)	
 (3)	
 (4)	

Game finished. You lost :(
Leave empty and press <ENTER> to restart, undo a number of moves (u) or quit (q): 
````

## Aufzeichnung eines Testmodus-Durchlaufs

````
Simulating game #1
Board: 1, 1, 6, 6
Starting player: Player 1
Expected winner: Player -1
Player -1 won!

Simulating game #2
Board: 2, 2, 6
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #3
Board: 5, 4, 6, 5, 4
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #4
Board: 1, 3
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #5
Board: 4, 2, 2, 2, 3
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #6
Board: 7, 6
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #7
Board: 5, 7
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #8
Board: 6, 5, 1, 2
Starting player: Player 1
Expected winner: Player -1
Player -1 won!

Simulating game #9
Board: 1, 5
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #10
Board: 1, 5, 4
Starting player: Player 1
Expected winner: Player -1
Player -1 won!

Simulating game #11
Board: 7, 5
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #12
Board: 4, 3, 5, 2
Starting player: Player 1
Expected winner: Player -1
Player -1 won!

Simulating game #13
Board: 7, 6, 2, 5
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #14
Board: 3, 1
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #15
Board: 2, 3
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #16
Board: 2, 5
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #17
Board: 1, 5, 5, 5
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #18
Board: 3, 1, 1, 1
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #19
Board: 5, 7
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #20
Board: 6, 2, 7, 1, 6
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #21
Board: 6, 3, 3, 4
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #22
Board: 1, 4, 6, 4
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #23
Board: 7, 7, 1
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #24
Board: 1, 5
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #25
Board: 7, 2
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #26
Board: 3, 4
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #27
Board: 6, 2, 7
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #28
Board: 2, 6
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #29
Board: 5, 5
Starting player: Player -1
Expected winner: Player 1
Player 1 won!

Simulating game #30
Board: 6, 7, 7, 7, 6
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #31
Board: 4, 5
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #32
Board: 6, 4, 7, 3, 6
Starting player: Player 1
Expected winner: Player -1
Player -1 won!

Simulating game #33
Board: 2, 4, 2, 6, 2
Starting player: Player 1
Expected winner: Player -1
Player -1 won!

Simulating game #34
Board: 5, 4, 7, 6
Starting player: Player -1
Expected winner: Player 1
Player 1 won!

Simulating game #35
Board: 4, 3
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #36
Board: 7, 3, 7, 6, 3
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #37
Board: 4, 6, 1
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Simulating game #38
Board: 2, 1, 4
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #39
Board: 5, 6, 7
Starting player: Player -1
Expected winner: Player -1
Player -1 won!

Simulating game #40
Board: 3, 4, 5, 6, 3
Starting player: Player 1
Expected winner: Player 1
Player 1 won!

Test finished: Wins: Player 1 = 21 / Player -1 = 19
````