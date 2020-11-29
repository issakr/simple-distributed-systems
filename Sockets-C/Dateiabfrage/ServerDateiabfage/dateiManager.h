#include <stdio.h>
void errorPrint(char string[]){
	fprintf(stderr,"%s",string);
}

//char *inDateiSuchen(char wort[],int maxbytes){
//
//	FILE *datei;
//	char puffer[100];
//		printf("Opening: %s\n",wort);
//		datei = fopen(wort,"r");
//		if (datei == NULL){
//			errorPrint("Open failed\n");
//		}else{
//			printf("Opened: %s\n",wort);
//			int g = fread(&puffer,sizeof(char),maxbytes,datei);
//			printf("Anzahl gelesene objekte %d\n", g);
//			printf("Das wurde gelesen: ->%s<-, ", puffer);
//		}
//		if(fclose(datei) != 0)	errorPrint("Close failed");
//		else	printf("closed\n");
//
//	return puffer;
//}
//
