//client

#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>

#define MAX_STRING_SIZE 15
#define NUMBER_OF_STRING 5


void errorPrint(char string[]){
	fprintf(stderr,"%s",string);
}

int main(int argc, char *argv[])
{
	int sockfd,portno;
	struct sockaddr_in servaddr;
	struct hostent *server;
	char buffer[256];
	if (argc < 3) {
	   fprintf(stderr,"usage %s hostname port\n", argv[0]);
	   exit(0);
	}
	portno = atoi(argv[2]);
	// socket create and varification
	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd == -1) {
		errorPrint("socket creation failed...\n");
		exit(0);
	}
	else	printf("Socket successfully created..\n");
	 server = gethostbyname(argv[1]);
	if (server == NULL) {
		fprintf(stderr,"ERROR, no such host\n");
		exit(0);
	}
	bzero(&servaddr, sizeof(servaddr));

	// assign IP, PORT
	servaddr.sin_family = AF_INET;
	bcopy((char *)server->h_addr,
			(char *)&servaddr.sin_addr.s_addr,
			server->h_length);
	servaddr.sin_port = htons(portno);

	// connect the client socket to server socket
	if (connect(sockfd, (struct sockaddr*)&servaddr, sizeof(servaddr)) < 0) {
		errorPrint("connection with the server failed...\n");
		exit(0);
	}
	else	printf("connected to the server..\n");


	char feld[NUMBER_OF_STRING][MAX_STRING_SIZE];
	int files=0, bytes,n;
	do{
		fpurge(stdin);
		printf("Dateiname oder exit eingeben: ");
		fgets(feld[files],MAX_STRING_SIZE,stdin);
		if(strncmp(feld[files],"exit",4)==0)
			break;
		files++;
	}while(files < NUMBER_OF_STRING);
	printf("i: %d\n",files);
	n = write(sockfd,&files,MAX_STRING_SIZE);
	if (n < 0){
		errorPrint("ERROR writing to socket");
	}
	for(int j=0;j<files;j++){
		printf("Dateiname: %s",feld[j]);
		n=write(sockfd,feld[j],MAX_STRING_SIZE);
		if (n < 0) errorPrint("ERROR writing to socket");
	}
	printf("Bytes?(n<=10)-->n= ");
	scanf("%d",&bytes);
	n=write(sockfd,&bytes,MAX_STRING_SIZE);
	if (n < 0) errorPrint("ERROR writing to socket");
	printf("Sending: n=%d\n",bytes);

	char puffer[100];
	for(int j=0;j<files;j++){
		n = read(sockfd,puffer,bytes);
		if (n < 0) errorPrint("ERROR reading to socket");
		printf("Receiving from Server: %s\n",puffer);
	}



		// close the socket
		close(sockfd);

}
