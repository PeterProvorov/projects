#include "checkString.h"
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
//#include <crtdbg.h>
//check: must be calculate this string?
char * checkString(char *string, error_t *err)
{
	int i;
	i = 0;
	//end of file
	if ((*string == '\0') && (*err == ERROR_END_OF_FILE))
		return NULL;
	//string contain only 'enter'
	if (*string == '\0')
	{
		printf("\n");
		return NULL;
	}
	//commentaries or empty string, if this is comments this function will print it in stdout
	for (i = 0; isspace((int)string[i]); ++i);
	if (((string[i] == '/') && (string[i + 1] == '/')) || (string[i] == '\0'))
	{
		printf("%s\n", string);
		return NULL;
	}
	//if this string must be calculate this function just return source string
			return string;
}

char * withoutSpace(char *string, error_t *err)
{
	int i,k;
	char *newString;
	i = 0;
	k = 0;
	newString = malloc(sizeof(char)*(strlen(string) + 1));
	if (newString == NULL)
	{
		*err = ERROR_STR_MALLOC_ERROR;
		return NULL;
	}
	while (string[i])
	{
		if (!isspace(string[i]))
		{
			newString[k] = string[i];
			k++;
		}
		i++;
	}
	newString[k] = 0;
	return newString;
}