#ifndef CHECKSTRING_H_INCLUDED__
#define CHECKSTRING_H_INCLUDED__

#include "error.h"

char *checkString(char *string, error_t *err);
char *withoutSpace(char *string, error_t *err);
#endif //CHECKSTRING_H_INCLUDED__