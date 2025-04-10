Pattern usati e ragionamento dietro


Partendo dal fatto che al prof piacciono molto i pattern ho cercato di implementarli dove possibile

1
Command Pattern
Usato per i Bottoni
Isola la richiesta di comando e la trasforma in un oggetto a se stante per esempio "BackButton" implementa la interfaccia GameButton che presenta il metodo execute che non presenta argomenti.
All'interno di ogni oggetto bottone è presente il metodo chiaramente implementato execute e in alcuni è presente anche un altro metodo setAttributes perché necessario passare dei parametri.
Il ragionamento dietro a questo pattern era di rendere più facile e strutturata la creazione di altri bottoni se necessari

2
Strategy
Sinceramente il prof la menzionato così tante volte in classe l'ho dovuta usare
Molto semplice 
c'è una interfaccia, gli altri la implementano e completano i metodi astratti

3
State Pattern
Il più strano e nuovo
crea una serie di "stati" in cui il gioco si può trovare, descritti dai nomi dei file
Tutti implementano una interfaccia che contiene tutti i possibili metodi usati dai file.
gli stati come ho detto sono descritti dai nomi dei file
animating state è usato solo per disabilitare i bottoni e capire se dopo è il turno del giocatore o del nemico.
Una volta finita un'animazione si passa allo stato di PlayerTurn o EnemyTurn usato per abilitare i bottoni e aspettare l'input o lasciare disabilitati i bottoni e iniziare un'altra animazione.
GameOver fa partire l'animazione della morte e disabilita i bottoni
InfoState fa partire l'animazione dell'info (fa ingrandire il nemico e scrive le informazioni) con l'unico bottone abilitato back per tornare alle opzioni di attacco
ItemSelectionState è usato per scegliere e gestire le pozioni con uno strategy per le pozioni
La maggior parte dei metodi non sono usate dalle singole classi ma tutti i metodi sono usati da almeno un metodo

4
MVC 
va beh lo sai cosa fa e a cosa serve ahahahaha