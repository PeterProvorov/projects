#include "getString.h"
#include "calculate.h"
#include "checkString.h"
#include "bool.h"
#include "error.h"
//#include <crtdbg.h>
#include <zconf.h>

//This function open FILE stream or standart input stream.
errno_t chooseStream(int argc, char *argv[], FILE **stream)
{
	if (2 == argc)
	{
        *stream = fopen(argv[1], "r");
        return 1;
	}
	else
	{
		*stream = stdin;
		return 0;
	}
}

int main(int argc, char *argv[])
{
	FILE *stream = NULL;
	char *expression, *string;
	double answer;
	errno_t error;
	error_t err;
	string = NULL;
	expression = NULL;
	error = (errno_t)chooseStream(argc, argv, &stream);
	if (error != 0) // ��������� ������ ��� �������� ������
	{
		err = ERROR_READING_ERROR;
		errorEvaluator(err, string);
		return 3;
	}
	string = GetString(stream, &err);
	while (string == NULL) //������� ��������� ������ �� ������ �� ��� ��� ���� �� ������� ��� ����� �� ����������
	{
		if (errorEvaluator(err, string) != BOOL_FALSE)
		{
			string = GetString(stream, &err);
			continue;
		}
		else
		{
			fclose(stream);
			return -1;
		}
	}
	//reading and calculating if all is good.
	while (errorEvaluator(err, string) != BOOL_FALSE)
	{
		if (checkString(string, &err) == NULL)//���� ������ - ����������� ��� ���� ���������� �������, ���� ���������
		{
			if (err == ERROR_END_OF_FILE)
				break;
			free(string);
			string = GetString(stream, &err);
			while (string == NULL) //������� ��������� ������ �� ������ �� ��� ��� ���� �� ������� ��� ����� �� ����������
			{
				if (errorEvaluator(err, string) != BOOL_FALSE)
				{
					string = GetString(stream, &err);
					continue;
				}
				else
				{
					fclose(stream);
					free(string);
					return -1;
				}
			}
			continue;
		}
		expression = withoutSpace(string, &err);
		if (expression == NULL)
		{
			errorEvaluator(err, string);
			free(string);
			string = GetString(stream, &err);
			while (string == NULL) //������� ��������� ������ �� ������ �� ��� ��� ���� �� ������� ��� ����� �� ����������
			{
				if (errorEvaluator(err, string) != BOOL_FALSE)
				{
					string = GetString(stream, &err);
					continue;
				}
				else
				{
					fclose(stream);
					free(string);
					return -1;
				}
			}
			continue;
		}
		answer = Calculate(expression, &err);
		if (err == ERROR_OK)
			printf("%s == %g\n", string, answer);
		else
			if (errorEvaluator(err, string) == BOOL_FALSE)
			{
				free(string);
				free(expression);
				fclose(stream);
				return -1;
			}
		free(string);
		free(expression);
		string = GetString(stream, &err);
		while (string == NULL) //������� ��������� ������ �� ������ �� ��� ��� ���� �� ������� ��� ����� �� ����������
		{
			if (errorEvaluator(err, string) != BOOL_FALSE)
			{
				string = GetString(stream, &err);
				continue;
			}
			else
			{
				fclose(stream);
				free(string);
				return -1;
			}
		}
	}
	//Try to read last string
	if (checkString(string, &err) != NULL)
	{
		expression = withoutSpace(string, &err);
		if (expression == NULL)
		{
			errorEvaluator(err, string);
			fclose(stream);
			free(string);
			return -1;
		}
		answer = Calculate(expression, &err);
		if (err == ERROR_OK)
			printf("%s == %g\n", string, answer);
		else
			if (errorEvaluator(err, string) == BOOL_FALSE)
			{
				free(string);
				free(expression);
				fclose(stream);
				return -1;
			}
		free(string);
		free(expression);
		string = NULL;
		fclose(stream);
		return 0;
	}
	free(string);
	string = NULL;
	fclose(stream);
	//_CrtDumpMemoryLeaks();
	return 0;
}