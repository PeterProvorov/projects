#ifndef CALCULATE_H_INCLUDED__
#define CALCULATE_H_INCLUDED__


#include "bool.h"
#include "error.h"
//универсальный тип для разбиения выражения на лексемы
typedef enum
{
	ID_BRACKETS,
	ID_NUMBER,
	ID_OPERATION,
	ID_FUNC,
	ID_END_OF_ARRAY
}lexical_token_id_t;

typedef enum
{
	MINUS_UNARY = 5,
	MINUS_BINARY
}minus_id_t;


typedef struct
{
	char name;
	minus_id_t minus_id;
	int priority;
	char associativity;
}operation_t;

typedef struct
{
	char *funcName;
	double(*pf)(double);
}function_t;

typedef struct
{
	lexical_token_id_t id;
	union{
		char brackets;
		double number;
		operation_t operation;
		function_t function;
	}lexical_token_t;
}expression_t;

//structure for temporary array, for translating string to RPN
typedef struct
{
	int size, top;
	expression_t **bottom;
}arrayRPN_t;

double Calculate(char *string, error_t *err);

#endif // CALCULATE_H_INCLUDED__