#ifndef GETSTRING_H_INCLUDED__
#define GETSTRING_H_INCLUDED__

#include <stdlib.h>
#include "error.h"

char * GetString(FILE * stream, error_t *err);

#endif // GETSTRING_H_INCLUDED__