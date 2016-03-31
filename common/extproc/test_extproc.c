#include<stdio.h>
#include<stdlib.h>
#include<string.h>

void mailx(char *to, char *subject, char *message) {

  int num;
  char command[50000];

  strcpy(command, "echo \"");
  strcat(command, message);
  strcat(command, "\" | mailx -s \"");
  strcat(command, subject);
  strcat(command, "\" ");
  strcat(command, to);

  num = system(command);

}

void sh(char *command) {

  int num;

  num = system(command);

}

