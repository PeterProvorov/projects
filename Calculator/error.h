#ifndef ERROR_H_INCLUDED_
#define ERROR_H_INCLUDED_

#include <stdio.h>
#include "bool.h"

typedef enum
{
	ERROR_WRONG_CHAR = -8,		//недопустимые символы
	ERROR_MAP,								//неправильно расставленные операторы
	ERROR_BRACKETS,						//несбалансированное количество скобок
	ERROR_FUNC,								//неправильно названная функция
	ERROR_IO_REALLOC_ERROR,
	ERROR_STR_REALLOC_ERROR,
	ERROR_IO_MALLOC_ERROR,
	ERROR_STR_MALLOC_ERROR,
	ERROR_OK,
	ERROR_READING_ERROR,
	ERROR_END_OF_FILE,
	ERROR_CODE_ERROR,
	ERROR_RANGE_OF_DEFINITION,
	ERROR_DIVIDE_BY_ZERO
}error_t;

bool_t errorEvaluator(error_t err, char* str);
#endif // ERROR_H_INCLUDED_