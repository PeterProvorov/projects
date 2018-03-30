#include "calculate.h"
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>
#include <assert.h>
//#include <crtdbg.h>

static expression_t *newString;
static expression_t *RPNString;
static arrayRPN_t array;
//===== �� ��� ����� ========
typedef struct
{
	int size, top;
	double *bottom;
}my_stack_t;

static my_stack_t stack;

double *createStack(int size)
{
	stack.size = size;
	stack.bottom = malloc(sizeof(double)*size);
	stack.top = 0;
	return stack.bottom;
}

bool_t push(double num)
{
	if (stack.top < stack.size)
	{
		stack.bottom[stack.top] = num;
		stack.top++;
		return BOOL_TRUE;
	}
	return BOOL_FALSE;
}

double pop(void)
{
	//����� �� ������� �������
	if (stack.top > 0)
		stack.top--;
	return stack.bottom[stack.top];
}

void deleteStack(void)
{
	free(stack.bottom);
}
//=========================

//===========�������������� �������============
double ctg(double num)
{
	return 1 / tan(num);
}
//=============================================

//============= ������� � ��� =================
bool_t splitString(char *string, error_t *err)
{
	char *pString, *endPtr;
	int i, k, size;
	*err = ERROR_OK;
	i = 0;
	k = 0;
	pString = string;
	size = strlen(string) + 1;
	//������ "������" �������������� ����, ���������� �������� ����� ������� ������ ������
	newString = malloc(size*sizeof(expression_t));
	if (newString == NULL)
	{
		*err = ERROR_STR_MALLOC_ERROR;
		return BOOL_FALSE;
	}
	//� ���� ����� ���������� ��������� ������ �� ������� � ������ ������ � ������.
	while (pString[i])
	{

		//    �������� ������� �� �������� ��������.
		//   ��������� ��������� ������� ��� ����� � 2 �������:
		//1. ������� ������ ����� ����������� ������.
		//2. ������� ������ �������� ������� +\-, �����, �����������, ���������� ������� �� ������ ���� ������ ��� ')'
		//   � ��������� ������ +\- ����� �������� ���������, � ����������� ������� ������ ���� ������.
		if (isdigit(pString[i]))
		{
			newString[k].id = ID_NUMBER;
			newString[k].lexical_token_t.number = strtod(&pString[i], &endPtr);
			i += sizeof(char)*(endPtr - &pString[i]);
			k++;
			continue;
		}
		// Is function
		//��� ������������ ��������� 'pi', 'e'
		if (isalpha(pString[i]))
			switch (pString[i])
		{
			case 's':
				if (&pString[i] == strstr(&pString[i], "sin("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "sin";
					newString[k].lexical_token_t.function.pf = sin;
					k++;
					i += 3;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
					continue;
				}
				if (&pString[i] == strstr(&pString[i], "sqrt("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "sqrt";
					newString[k].lexical_token_t.function.pf = sqrt;
					k++;
					i += 4;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
				}
				else
				{
					free(newString);
					*err = ERROR_FUNC;
					return BOOL_FALSE;
				}
				continue;
			case 'c':
				if (&pString[i] == strstr(&pString[i], "cos("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "cos";
					newString[k].lexical_token_t.function.pf = cos;
					k++;
					i += 3;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
					continue;
				}
				if (&pString[i] == strstr(&pString[i], "ceil("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "ceil";
					newString[k].lexical_token_t.function.pf = ceil;
					k++;
					i += 4;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
					continue;
				}
				if (&pString[i] == strstr(&pString[i], "ctg("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "ctg";
					newString[k].lexical_token_t.function.pf = ctg;
					k++;
					i += 3;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
				}
				else
				{
					free(newString);
					*err = ERROR_FUNC;
					return BOOL_FALSE;
				}
				continue;
			case 't':
				if (&pString[i] == strstr(&pString[i], "tg("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "tg";
					newString[k].lexical_token_t.function.pf = tan;
					k++;
					i += 2;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
				}
				else
				{
					free(newString);
					*err = ERROR_FUNC;
					return BOOL_FALSE;
				}
				continue;
			case 'l':
				if (&pString[i] == strstr(&pString[i], "ln("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "ln";
					newString[k].lexical_token_t.function.pf = log;
					k++;
					i += 2;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
				}
				else
				{
					free(newString);
					*err = ERROR_FUNC;
					return BOOL_FALSE;
				}
				continue;
			case 'f':
				if (&pString[i] == strstr(&pString[i], "floor("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "floor";
					newString[k].lexical_token_t.function.pf = floor;
					k++;
					i += 5;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
				}
				else
				{
					free(newString);
					*err = ERROR_FUNC;
					return BOOL_FALSE;
				}
				continue;
				//���������
			case 'p':
				if (&pString[i] == strstr(&pString[i], "pi"))
				{
					newString[k].id = ID_NUMBER;
					newString[k].lexical_token_t.number = 3.1415926535;
					i += sizeof(char) * 2;
					k++;
				}
				else
				{
					free(newString);
					*err = ERROR_WRONG_CHAR;
					return BOOL_FALSE;
				}
				continue;
				//������ ����� ����� ��������, ���� ������ ���-�� ������������, ������������ � ��
			case 'e':
				newString[k].id = ID_NUMBER;
				newString[k].lexical_token_t.number = 2.718281828458945;
				i += sizeof(char) * 1;
				k++;
				continue;
			case 'a':
				if (&pString[i] == strstr(&pString[i], "arcsin("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "arcsin";
					newString[k].lexical_token_t.function.pf = asin;
					k++;
					i += 6;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
					continue;
				}
				if (&pString[i] == strstr(&pString[i], "arccos("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "arccos";
					newString[k].lexical_token_t.function.pf = acos;
					k++;
					i += 6;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
					continue;
				}
				if (&pString[i] == strstr(&pString[i], "arctg("))
				{
					newString[k].id = ID_FUNC;
					newString[k].lexical_token_t.function.funcName = "arctg";
					newString[k].lexical_token_t.function.pf = atan;
					k++;
					i += 5;
					newString[k].id = ID_BRACKETS;
					newString[k].lexical_token_t.brackets = '(';
					k++;
					i++;
				}
				else
				{
					free(newString);
					*err = ERROR_FUNC;
					return BOOL_FALSE;
				}
				continue;
			default:
				free(newString);
				*err = ERROR_WRONG_CHAR;
				return BOOL_FALSE;
		}
		// �������� ������� �� ������
		if (pString[i] == '(')
		{
			newString[k].id = ID_BRACKETS;
			newString[k].lexical_token_t.brackets = '(';
			i++;
			k++;
			continue;
		}
		if (pString[i] == ')')
		{
			newString[k].id = ID_BRACKETS;
			newString[k].lexical_token_t.brackets = ')';
			i++;
			k++;
			continue;
		}
		// �������� ������� �� ��������
		if (pString[i] == '-')
		{
			if (!((newString[k - 1].id == ID_NUMBER || newString[k - 1].id == ID_BRACKETS) && (isdigit(pString[i + 1]) || isalpha(pString[i + 1]) || (pString[i + 1] == '('))))
			{
				free(newString);
				*err = ERROR_MAP;
				return BOOL_FALSE;
			}
			newString[k].id = ID_OPERATION;
			newString[k].lexical_token_t.operation.name = '-';
			newString[k].lexical_token_t.operation.priority = 0;
			newString[k].lexical_token_t.operation.associativity = 'l';
			i++;
			k++;
			continue;
		}
		if (pString[i] == '+')
		{
			if (!((newString[k - 1].id == ID_NUMBER || newString[k - 1].id == ID_BRACKETS) && (isdigit(pString[i + 1]) || isalpha(pString[i + 1]) || (pString[i + 1] == '('))))
			{
				free(newString);
				*err = ERROR_MAP;
				return BOOL_FALSE;
			}
			newString[k].id = ID_OPERATION;
			newString[k].lexical_token_t.operation.name = '+';
			newString[k].lexical_token_t.operation.priority = 0;
			newString[k].lexical_token_t.operation.associativity = 'l';
			i++;
			k++;
			continue;
		}
		if (pString[i] == '/')
		{
			if (!((newString[k - 1].id == ID_NUMBER || newString[k - 1].id == ID_BRACKETS) && (isdigit(pString[i + 1]) || isalpha(pString[i + 1]) || (pString[i + 1] == '('))))
			{
				free(newString);
				*err = ERROR_MAP;
				return BOOL_FALSE;
			}
			newString[k].id = ID_OPERATION;
			newString[k].lexical_token_t.operation.name = '/';
			newString[k].lexical_token_t.operation.priority = 1;
			newString[k].lexical_token_t.operation.associativity = 'l';
			i++;
			k++;
			continue;
		}
		if (pString[i] == '*')
		{
			if (!((newString[k - 1].id == ID_NUMBER || newString[k - 1].id == ID_BRACKETS) && (isdigit(pString[i + 1]) || isalpha(pString[i + 1]) || (pString[i + 1] == '('))))
			{
				free(newString);
				*err = ERROR_MAP;
				return BOOL_FALSE;
			}
			newString[k].id = ID_OPERATION;
			newString[k].lexical_token_t.operation.name = '*';
			newString[k].lexical_token_t.operation.priority = 1;
			newString[k].lexical_token_t.operation.associativity = 'l';
			i++;
			k++;
			continue;
		}
		if (pString[i] == '^')
		{
			if (!((newString[k - 1].id == ID_NUMBER || newString[k - 1].id == ID_BRACKETS) && (isdigit(pString[i + 1]) || isalpha(pString[i + 1]) || (pString[i + 1] == '('))))
			{
				free(newString);
				*err = ERROR_MAP;
				return BOOL_FALSE;
			}
			newString[k].id = ID_OPERATION;
			newString[k].lexical_token_t.operation.name = '^';
			newString[k].lexical_token_t.operation.priority = 2;
			newString[k].lexical_token_t.operation.associativity = 'r';
			i++;
			k++;
			continue;
		}
		free(newString);
		*err = ERROR_WRONG_CHAR;
		return BOOL_FALSE;
	}
	newString[k].id = ID_END_OF_ARRAY;
	return BOOL_TRUE;
}

void minusChecker(void)
{
	int i = 0;
	while (newString[i].id != ID_END_OF_ARRAY)
	{
		if (newString[i].id == ID_OPERATION && newString[i].lexical_token_t.operation.name == '-')
			if ((i == 0 || (newString[i - 1].id == ID_BRACKETS && newString[i - 1].lexical_token_t.brackets == '(')) && (newString[i + 1].id == ID_NUMBER || newString[i + 1].id == ID_FUNC || (newString[i + 1].id == ID_BRACKETS && newString[i + 1].lexical_token_t.brackets == '(')))
				newString[i].lexical_token_t.operation.minus_id = MINUS_UNARY;
			else
				newString[i].lexical_token_t.operation.minus_id = MINUS_BINARY;
		i++;
	}
}

expression_t **createRPNArray(int size)
{
	array.size = size;
	array.bottom = malloc(sizeof(expression_t *)*size);
	array.top = 0;
	return array.bottom;
}

void deleteRPNArray(void)
{
	free(array.bottom);
}

bool_t putInArray(expression_t *token)
{
	if (array.top == array.size)
	{
		//deleteStackRPN();
		//array is overflow
		return BOOL_FALSE;
	}
	array.bottom[array.top] = token;
	array.top++;
	//�������� �� ����� �� ������� �������
	return BOOL_TRUE;
}

bool_t rewrite(expression_t *el)
{
	//����� �� ������� �������
	if (array.top < 0)
		return BOOL_FALSE;
	//�������� ����� ����������� ��-�� ������� ������� �� ���������
	array.top--;
	el->id = array.bottom[array.top]->id;
	el->lexical_token_t.brackets = array.bottom[array.top]->lexical_token_t.brackets;
	el->lexical_token_t.function.funcName = array.bottom[array.top]->lexical_token_t.function.funcName;
	el->lexical_token_t.function.pf = array.bottom[array.top]->lexical_token_t.function.pf;
	el->lexical_token_t.number = array.bottom[array.top]->lexical_token_t.number;
	el->lexical_token_t.operation.name = array.bottom[array.top]->lexical_token_t.operation.name;
	el->lexical_token_t.operation.priority = array.bottom[array.top]->lexical_token_t.operation.priority;
	el->lexical_token_t.operation.associativity = array.bottom[array.top]->lexical_token_t.operation.associativity;
	el->lexical_token_t.operation.minus_id = array.bottom[array.top]->lexical_token_t.operation.minus_id;
	return BOOL_TRUE;
}

expression_t *checkLastEl(void)
{
	if (array.top > 0)
		return array.bottom[array.top - 1];
	else
		return NULL;
}

int getArraySize(expression_t *array)
{
	int size;
	size = 0;
	while (array[size].id != ID_END_OF_ARRAY)
		size++;
	return size;
}

bool_t toRPN(error_t *err)
{
	int size, i, k;
	expression_t *lastToken;
	i = 0;
	k = 0;
	*err = ERROR_OK;
	//create array for translating to RPN
	size = getArraySize(newString);
	if (createRPNArray(size) == NULL)
	{
		free(newString);
		*err = ERROR_STR_MALLOC_ERROR;
		return BOOL_FALSE;
	}

	//create array for lexical tokens in RPN
	RPNString = malloc((size + 1)*sizeof(expression_t));
	if (RPNString == NULL)
	{
		free(newString);
		deleteRPNArray();
		*err = ERROR_STR_MALLOC_ERROR;
		return BOOL_FALSE;
	}
	//translating to RPN
	while (newString[i].id != ID_END_OF_ARRAY)
	{
		//����� ����� ����� � ����� ������
		if (newString[i].id == ID_NUMBER)
		{
			RPNString[k].id = ID_NUMBER;
			RPNString[k].lexical_token_t.number = newString[i].lexical_token_t.number;
			i++;
			k++;
			continue;
		}
		//������� ����� �� ��������� ������
		if (newString[i].id == ID_FUNC)
		{
			if (putInArray(&newString[i]) == BOOL_FALSE)
			{
				deleteRPNArray();
				free(newString);
				free(RPNString);
				*err = ERROR_CODE_ERROR;
				return BOOL_FALSE;
			}
			i++;
			continue;
		}
		//��������� ������
		if (newString[i].id == ID_BRACKETS)
		{
			if (newString[i].lexical_token_t.brackets == '(')
			{
				if (putInArray(&newString[i]) == BOOL_FALSE)
				{
					deleteRPNArray();
					free(newString);
					free(RPNString);
					*err = ERROR_CODE_ERROR;
					return BOOL_FALSE;
				}
				i++;
				continue;
			}
			else
			{
				if (array.top > 0)
				{
					lastToken = checkLastEl();
					while ((lastToken->lexical_token_t.brackets != '(') && (array.top > 0))
					{
						if (BOOL_FALSE == rewrite(&RPNString[k]))
						{
							deleteRPNArray();
							free(newString);
							free(RPNString);
							*err = ERROR_CODE_ERROR;
							return BOOL_FALSE;
						}
						k++;
						if (array.top > 0)
							lastToken = checkLastEl();
					}
					if (lastToken->lexical_token_t.brackets == '(')
					{
						assert(array.top > 0);
						array.top--; //��� ������ �������?
						lastToken = checkLastEl();
						if (lastToken != NULL)
							if (array.bottom[array.top - 1]->id == ID_FUNC)
							{
								if (BOOL_FALSE == rewrite(&RPNString[k]))
								{
									deleteRPNArray();
									free(newString);
									free(RPNString);
									*err = ERROR_CODE_ERROR;
									return BOOL_FALSE;
								}
								k++;
								i++;
								continue;
							}
						i++;
					}
					else
					{
						assert(array.top >= 0);
						//������� ����������� ������ ��� ��������� �����������
						deleteRPNArray();
						free(newString);
						free(RPNString);
						*err = ERROR_BRACKETS;
						return BOOL_FALSE;
					}
				}
				else
				{
					assert(array.top >= 0);
					//������� ����������� ������ ��� ��������� �����������
					free(newString);
					deleteRPNArray();
					free(RPNString);
					*err = ERROR_BRACKETS;
					return BOOL_FALSE;
				}
			}
			continue;
		}
		if (newString[i].id == ID_OPERATION)
		{
			if (array.top > 0)
			{
				lastToken = checkLastEl();
				if (newString[i].lexical_token_t.operation.associativity == 'l')
					while ((lastToken != NULL) && (lastToken->id == ID_OPERATION) && (newString[i].lexical_token_t.operation.priority <= lastToken->lexical_token_t.operation.priority))
					{
						assert(lastToken->id == ID_OPERATION);
						if (BOOL_FALSE == rewrite(&RPNString[k]))
						{
							free(newString);
							deleteRPNArray();
							free(RPNString);
							*err = ERROR_CODE_ERROR;
							return BOOL_FALSE;
						}
						k++;
						lastToken = checkLastEl();
					}
				else
					while ((lastToken != NULL) && (lastToken->id == ID_OPERATION) && (newString[i].lexical_token_t.operation.priority < lastToken->lexical_token_t.operation.priority))
					{
						assert(lastToken->id == ID_OPERATION);
						if (BOOL_FALSE == rewrite(&RPNString[k]))
						{
							free(newString);
							deleteRPNArray();
							free(RPNString);
							*err = ERROR_CODE_ERROR;
							return BOOL_FALSE;
						}
						k++;
						lastToken = checkLastEl();
					}
			}
			putInArray(&newString[i]);
			i++;
		}
	}
	while (array.top > 0)
	{
		lastToken = checkLastEl();
		if (lastToken->id == ID_OPERATION)
		{
			rewrite(&RPNString[k]);
			k++;
		}
		else
		{
			//����������� ����������� ������
			assert(array.top >= 0);
			free(newString);
			deleteRPNArray();
			free(RPNString);
			*err = ERROR_BRACKETS;
			return BOOL_FALSE;
		}
	}
	RPNString[k].id = ID_END_OF_ARRAY;
	deleteRPNArray();
	return BOOL_TRUE;
}

//======= ���������� ===
double calc(error_t *err)
{
	int i, count, size;
	double res, tmp1, tmp2;
	i = 0;
	count = 0;
	size = getArraySize(RPNString);
	createStack(size);
	while (RPNString[i].id != ID_END_OF_ARRAY)
	{
		if (RPNString[i].id == ID_NUMBER)
		{
			if (push(RPNString[i].lexical_token_t.number) == BOOL_FALSE);//����� ����, ���� ����������
			i++;
			continue;
		}
		if (RPNString[i].id == ID_FUNC)
		{
			tmp1 = pop();
			res = (*RPNString[i].lexical_token_t.function.pf) (tmp1);
			if (isfinite(res) == 0)
			{
				*err = ERROR_RANGE_OF_DEFINITION;
				deleteStack();
				free(newString);
				free(RPNString);
				return BOOL_FALSE;
			}
			push(res);
			i++;
			continue;
		}
		if (RPNString[i].id == ID_OPERATION)
		{
			switch (RPNString[i].lexical_token_t.operation.name)
			{
			case '+':
				tmp1 = pop();
				tmp2 = pop();
				res = tmp1 + tmp2;
				push(res);
				i++;
				break;
			case '-':
				if (RPNString[i].lexical_token_t.operation.minus_id == MINUS_BINARY)
				{
					tmp1 = pop();
					tmp2 = pop();
					res = tmp2 - tmp1;
				}
				else
				{
					tmp1 = pop();
					res = 0 - tmp1;
				}
				push(res);
				i++;
				break;
			case '*':
				tmp1 = pop();
				tmp2 = pop();
				res = tmp2 * tmp1;
				push(res);
				i++;
				break;
			case '/':
				tmp1 = pop();
				tmp2 = pop();
				if (tmp1 != 0)
					res = tmp2 / tmp1;
				else
				{
					*err = ERROR_DIVIDE_BY_ZERO;
					deleteStack();
					free(newString);
					free(RPNString);
					return BOOL_FALSE;
				}
				push(res);
				i++;
				break;
			case '^':
				tmp1 = pop();
				tmp2 = pop();
				res = pow(tmp2, tmp1);
				push(res);
				i++;
				break;
			}
			continue;
		}
	}
	if (stack.top == 1)
		res = pop();
	else
	{
		*err = ERROR_MAP;
		deleteStack();
		free(newString);
		free(RPNString);
		return BOOL_FALSE;
	}
	deleteStack();
	free(newString);
	free(RPNString);
	return res;
}

//======= ������� ������� =================
double Calculate(char *string, error_t *err)
{
	double answer;
	if (splitString(string, err) == BOOL_FALSE)
		return 666;
	minusChecker();
	if (toRPN(err) == BOOL_FALSE)
		return 666;
	answer = calc(err);	
	return answer;
}