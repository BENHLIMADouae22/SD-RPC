#include <stdio.h>
#include <rpc/rpc.h> // contient toutes les fonctions RPC

// définir un programme, une version et une procédure
#define PROGRAMME ((unsigned long)0x20000001)
#define VERSION ((unsigned long)2)
#define PROCEDURE ((unsigned long)2)

// la fonction à enregistrer
char *echo(char *arg) {
    return arg;
}

int main(void) {
    // enregistrer la fonction echo
    if (registerrpc(PROGRAMME, VERSION, PROCEDURE, echo, (xdrproc_t)xdr_wrapstring, (xdrproc_t)xdr_wrapstring) == -1) {
        fprintf(stderr, "Erreur d'enregistrement de la RPC\n");
        exit(1);
    }

    svc_run(); // commencer à écouter les requêtes RPC
    fprintf(stderr, "Erreur: svc_run a retourné!\n");
    exit(1);
}

