#include "error.h"
//#include <crtdbg.h>

//���� ����������� ������ ��������� �� �������������� ��������� ���������� BOOL_FALSE, ����� BOOL_TRUE
bool_t errorEvaluator(error_t err, char *str)
{
	switch (err)
	{
	case ERROR_OK:
		break;
	case ERROR_WRONG_CHAR:
		printf("%s == ERROR: Wrong characters in the string.\n", str);
		return BOOL_TRUE;
	case ERROR_BRACKETS:
		printf("%s == ERROR: Not correct brackets.\n", str);
		return BOOL_TRUE;
	case ERROR_MAP:
		printf("%s == ERROR: Not correct operations, probably.\n", str);
		return BOOL_TRUE;
	case ERROR_FUNC:
		printf("%s == ERROR: Not correct function name, probably.\n", str);
		return BOOL_TRUE;
	case ERROR_RANGE_OF_DEFINITION:
		printf("%s == ERROR: The output from range of definition.\n", str);
		return BOOL_TRUE;
	case ERROR_DIVIDE_BY_ZERO:
		printf("%s == ERROR: Division by zero.\n", str);
		return BOOL_TRUE;
	case ERROR_CODE_ERROR:
		printf("%s == ERROR: I really dont know why.\n", str);
		return BOOL_TRUE;
	case ERROR_IO_MALLOC_ERROR:
		printf("ERROR: Not enough memory.\n");
		return BOOL_TRUE;
	case ERROR_IO_REALLOC_ERROR:
		printf("ERROR: Not enough memory.\n");
		return BOOL_TRUE;
	case ERROR_STR_MALLOC_ERROR:
		printf("%s == ERROR: Not enough memory.\n", str);
		return BOOL_TRUE;
	case ERROR_STR_REALLOC_ERROR:
		printf("%s == ERROR: Not enough memory.\n", str);
		return BOOL_TRUE;
	case ERROR_READING_ERROR:
		printf("ERROR: Can not read file.\n");
		return BOOL_FALSE;
	case ERROR_END_OF_FILE:
		return BOOL_FALSE;
	}
	return BOOL_TRUE;
}