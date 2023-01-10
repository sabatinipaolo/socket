esercizio svolto ..


quesiti : perché try catch ?



### esercizio 02_intro_TCP_Echo_Server_ese01:

abbiamo un server che grazie ai thread gestisce le comunicazioni con i client ..

L'esercizio consiste nel 
-moidficare il client in modo che :
cicla indefinitivamente 
 legge un messaggio in input e lo invia al server
 se il messaggio inviato inizia con "@server bye" chiude la connessione, il cilco e il programma;
 
 una volta inviato legge il messaggio di rispota del server e lo stampa a video
 continua a ciclare
 
 
 
-modificare il server in un "echo server" 
nel senso che il thread che gestisce la connessisone con il client 
se riceve un messaggio che inizia con "@server bye" chiude la connessione
altrmimenti stampa a video il messaggio ricevuto (con ip e e porta del client) lo reinvia al client premettendo la stringa "il server dice : ho ricevuto", e cicla finché ci sono dati da leggere.



