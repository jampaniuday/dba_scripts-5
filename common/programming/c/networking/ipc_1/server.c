/*
 ** FILE         : server.c
 ** AUTHOR       : Jeffrey Hunter
 ** DATE         : 11-OCT-2000
 ** PURPOSE      : Demonstrate Socket Programming Interface
 ** COMPILE        Solaris :  gcc -lsocket -lnsl server.c -o server
 **                Linux   :  gcc -lpisock -lnsl server.c -o server
 ** RUN          : $ server
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netdb.h>
#include <errno.h>

main () {
  int sockMain, sockClient, length, child;
  struct sockaddr_in servAddr;

  /*
   ** 1. CREATE MASTER SOCKET
   */

  if ( (sockMain = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
    perror("Server cannot open main socket.");
    exit(1);
  }

  /*
   ** 2. CREATE A DATA STRUCTURE TO HOLD THE LOCAL IP ADDRESS
   **    AND PORT THAT WE WILL USE. WE ARE WILLING TO ACCEPT
   **    CLIENTS CONNECTING TO ANY LOCAL IP ADDRESS (INADDR_ANY).
   **    SINCE THIS SERVER WILL NOT USE A WELL-KNOWN PORT,
   **    SET THE port=0. THE BIND CALL ASSIGN THE SERVER A PORT
   **    AND WRITE IT INTO THE "TCB".
   */

  bzero( (char *) &servAddr, sizeof(servAddr));
  servAddr.sin_family = AF_INET;
  servAddr.sin_addr.s_addr = htonl(INADDR_ANY);
  servAddr.sin_port = 0;

  /*
   ** 3. CALL BIND. BIND WILL PICK A PORT NUMBER AND 
   **    WRITE IT IN THE "TCB".
   */

  if ( bind (sockMain, &servAddr, sizeof(servAddr)) ) {
    perror("Server's bind failed.");
    exit(1);
  }

  /*
   ** 4. WE WANT TO LOOK AT THE PORT NUMBER. WE USE THE 
   **    getsockname() FUNCTION TO COPY THE PORT INTO
   **    servAddr.
   */

  length = sizeof(servAddr);
  if (getsockname(sockMain, &servAddr, &length)) {
    perror("getsockname call failed.");
    exit(1);
  }

  printf ("SERVER: Port number is %d\n", ntohs(servAddr.sin_port));

  /*
   ** 5. SET UP A QUEUE THAT CAN HOLD UP TO FIVE CLIENTS
   */

  listen(sockMain, 5);

  /*
   ** 6. WAIT FOR AN INCOMING CLIENT. ACCEPT WILL
   **    RETURN A NEW SOCKET DESCRIPTOR THAT WILL
   **    BE USED FOR THIS CLIENT.
   */

  for ( ; ; ) {

    if ( (sockClient = accept(sockMain, 0, 0)) < 0) {
      perror("Bad client socket.");
      exit(1);
    }

    /*
     ** 7. CREATE A CHILD PROCESS TO HANDLE THE CLIENT.
     */

    if ( (child = fork()) < 0) {
      perror("Failed to create child.");
      exit(1);
    } else if (child == 0) {  /* CODE FOR CHILD TO EXECUTE */
      close(sockMain);        /* CHILD NOT INTERESTED IN sockMain */
      childWork(sockClient);
      close(sockClient);
      exit(0);
    }

    /*
     ** 8. THIS IS THE PARENT. IT IS NO LONGER
     **    INTERESTED IN THE CLIENT SOCKET. THE
     **    THE PARENT CLOSES ITS ENTRY TO THE CLIENT
     **    SOCKET AND LOOPS BACK TO ISSUE A NEW
     **    ACCEPT.
     */
    close(sockClient);

  } /* FOR LOOP */

}

#define BUFLEN 81
int childWork (int sockClient) {

  char buf[BUFLEN];
  int msgLength;
  /*
   ** 9. ZERO OUT THE BUFFER. THEN ISSUE A RECV
   **    TO GET A MESSAGE FROM THE CLIENT.
   */

  bzero(buf, BUFLEN);
  if ( (msgLength = recv(sockClient, buf, BUFLEN, 0)) < 0) {
    perror("Bad receive by child.");
    exit(1);
  }
  printf ("SERVER: Socket used for this client is %d\n", sockClient);
  printf ("SERVER: Message length was %d\n", msgLength);
  printf ("SERVER: Message was: %s\n\n", buf);
}
