#include <stdio.h>
#include <rpc/rpc.h>

// les mêmes définitions que dans le serveur
#define PROGRAMME ((unsigned long)0x20000001)
#define VERSION ((unsigned long)2)
#define PROCEDURE ((unsigned long)2)

int main(int argc, char *argv[]) {
    char *server;
    char *message;
    char **result;
    enum clnt_stat stat;  

    if (argc != 3) {
        fprintf(stderr, "Utilisation: %s serveur message\n", argv[0]);
        exit(1);
    }
    server = argv[1];
    message = argv[2];

    // Allocate memory for result
    result = (char **)malloc(sizeof(char *));
    if(result == NULL) {
        fprintf(stderr, "Failed to allocate memory\n");
        exit(1);
    }
    *result = NULL; // ensure it's initialized to NULL

    // Call RPC
    stat = callrpc(server, PROGRAMME, VERSION, PROCEDURE, (xdrproc_t)xdr_wrapstring, &message, (xdrproc_t)xdr_wrapstring, result);
    if (stat != RPC_SUCCESS) {
        clnt_perrno(stat);  
        exit(1);
    }
    if (*result == NULL) {
        printf("Pas de réponse du serveur\n");
    } else {
        printf("Réponse du serveur: %s\n", *result);
    }
    free(result); 
    exit(0);
}
