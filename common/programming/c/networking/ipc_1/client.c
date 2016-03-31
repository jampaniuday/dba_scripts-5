/*
 ** FILE         : client.c
 ** AUTHOR       : Jeffrey Hunter
 ** DATE         : 11-OCT-2000
 ** PURPOSE      : Demonstrate Socket Programming Interface
 ** COMPILE        Solaris :  gcc -lsocket -lnsl client.c -o client
 **                Linux   :  gcc -lpisock -lnsl client.c -o client
 ** RUN          : $ client <hostname> <port> <message>
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netdb.h>
#include <errno.h>

main (int argc, char ** argv) {
  int sock;
  struct sockaddr_in servAddr;
  struct hostent *hp, *gethostbyname();

  if (argc < 4) {
    printf ("ENTER  client hostname port message\n");
    exit(1);
  }

  if ( (sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
    perror("Could not get a socket.");
    exit(1);
  }

  bzero( (char *) &servAddr, sizeof(servAddr));
  servAddr.sin_family = AF_INET;
  hp = gethostbyname(argv[1]);
  bcopy(hp->h_addr, &servAddr.sin_addr, hp->h_length);
  servAddr.sin_port = htons(atoi(argv[2]));

  if (connect(sock, &servAddr, sizeof(servAddr)) < 0) {
    perror("Client could not connect.");
    exit(1);
  }

  printf("CLIENT: Ready to send\n");

  if (send(sock, argv[3], strlen(argv[3]), 0) < 0) {
    perror("Problem with send.");
    exit(1);
  }

  printf("CLIENT: Completed send.\n");
  close(sock);
  exit(0);
}
