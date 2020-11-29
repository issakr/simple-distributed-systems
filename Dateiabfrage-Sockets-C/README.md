#Ein Server soll einen Dateiabfrage-Dienst zur Verfügung stellen 
#Clients können nach Dateien suchen und die ersten n Bytes abfragen 
#Parameter für Anfrage:  
##Client liefert bis zu 5 Dateinamen  und einen Wert n<=10 
##Rückgabe: pro Datei die ersten  n Bytes oder die Meldung, dass  Datei nicht existiert 
#Implementierung in C;  Kommunikation über Sockets