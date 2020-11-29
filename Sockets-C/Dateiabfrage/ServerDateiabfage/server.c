//server

#include <unistd.h>
#include <stdio.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>

#include <stdlib.h>
#include "dateiManager.h"

//#define MAX 80
#define MAX_STRING_SIZE 15
//
//char *inDateiSuchen(char wort[],int maxbytes);
void errorPrint(char string[]);
//// Function designed for chat between client and server.
//
//int  readArgnumber(int sockfd){
//	int buff;
//		// read wieviel Datein
//		int a=read(sockfd, &buff, sizeof(buff));
//		int argNum = buff ;
//		if (a < 0) {
//			errorPrint("ERROR reading from socket");
//		}
//	return argNum;
//}
//
//void readDaten(int sockfd){
//	char buff[MAX];
//
//		//read argc vom Client
//		//-2 wegen dateiname und byteanzahl
//		int dateinzahl = readArgnumber(sockfd) - 2;
////		printf("dateinanzahl: %d\n-------------------\n", dateinzahl);
//
//		//read argv[argc-1] vom Client
//		int bytes = readArgnumber(sockfd);
//
//		//read argv aber nur Namen
//		for(int i=1;i<=dateinzahl;i++){
//			int a=read(sockfd, &buff, sizeof(buff));
//			if (a < 0) {
//				errorPrint("ERROR reading from socket");
//			}else{
//				char * c = inDateiSuchen(buff,bytes);
//	//			printf("aus dem Datein: %s\n-------------------\n", c);
//				write(sockfd,c,sizeof(c));
//			}
//		}
//}
// Driver function
int main(int argc, char *argv[] )
{
	int sockfd, connfd,portno,n;
	socklen_t len;
	struct sockaddr_in servaddr, cliaddr;
	if (argc < 2) {
		fprintf(stderr,"ERROR, no port provided\n");
		exit(1);
	     }
	// socket create and verification
	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd < 0) {
		errorPrint("socket creation failed...\n");
		exit(0);
	}
	else
		printf("Socket successfully created..\n");
	bzero(&servaddr, sizeof(servaddr));

	// assign IP, PORT
	portno = atoi(argv[1]);
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(portno);

	// Binding newly created socket to given IP and verification
	if ((bind(sockfd, (struct sockaddr*)&servaddr, sizeof(servaddr))) < 0) {
		errorPrint("socket bind failed...\n");
		exit(0);
	}
	else
		printf("Socket successfully binded..\n");

	// Now server is ready to listen and verification
	if ((listen(sockfd, 5)) != 0) {
		errorPrint("Listen failed...\n");
		exit(0);
	}
	else
		printf("Server listening..\n");
	len = sizeof(cliaddr);

	// Accept the data packet from client and verification
	connfd = accept(sockfd, (struct sockaddr*)&cliaddr, &len);
	if (connfd < 0) {
		errorPrint("server acccept failed...\n");
		exit(0);
	}
	else
		printf("server acccept the client...\n");
	int fileNr;
	 n = read(connfd,&fileNr,MAX_STRING_SIZE);
	 if (n < 0) errorPrint("ERROR reading from socket");
	 printf("FileNr: %d\n", fileNr);


	char receiv_feld[fileNr][MAX_STRING_SIZE];
	int bytes;

	printf("Dateinames: ");
	for(int j=0;j<fileNr;j++){
		n = read(connfd,receiv_feld[j],MAX_STRING_SIZE);
		if (n < 0) errorPrint("ERROR reading from socket");
		printf("%s, ",receiv_feld[j]);
	}
	printf("\n");
	n = read(connfd,&bytes,MAX_STRING_SIZE);
	if (n < 0) errorPrint("ERROR reading from socket");
	printf("Receive from Client: %d\n", bytes);

	FILE *datei;
	for(int j=0;j<fileNr;j++){
		char *puffer;
		printf("Opening: %s\n",receiv_feld[j]);
		datei = fopen(receiv_feld[j],"r");
		if (datei == NULL)	errorPrint("Open failed\n");
		else{
			printf("Opened: %s\n",receiv_feld[j]);
			int g = fread(&puffer,sizeof(char),bytes,datei);
			printf("Anzahl gelesene objekte %d\n", g);
			printf("Das wurde gelesen: ->%s<-, ", puffer);
			n = write(connfd,puffer,sizeof(puffer));
			if (n < 0) errorPrint("ERROR writing to socket");
		}
		if(fclose(datei) != 0)	errorPrint("Close failed\n");
		else	printf("closed\n");
	}

	// After chatting close the socket
	close(sockfd);
}
