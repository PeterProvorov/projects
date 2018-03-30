#include "getString.h"
//#include <crtdbg.h>
#define STRING_SIZE 100

//������� ��� ��������� ������ �� ������.
char * GetString(FILE * stream, error_t *err)
{
	int ch, i, size;
	char *string, *tmp;
	*err = ERROR_OK;
	i = 0;
	size = STRING_SIZE;
	string = malloc(size*sizeof(char));
	if (string == NULL)
	{
		*err = ERROR_IO_MALLOC_ERROR;
		return NULL;
	}
	//��������� ������ �����������, ���� ��� ��� ����� �� ����������.
	ch = fgetc(stream);
	while (ch != EOF && ch != '\n')
	{
		string[i] = (char)ch;
		i++;
		//������������� ������ � ������, ���� ������ ������ STRING_SIZE
		if (i >= size)
		{
			size *= 2;
			if ((tmp = realloc(string, size*sizeof(char))) == NULL)
			{
				free(tmp);
				*err = ERROR_IO_REALLOC_ERROR;
				//���� ������ ������������ �� ������, �� �������� �� ������������ ���������� ��������� ������
				//while (ch != EOF && ch != '\n')
				//	ch = fgetc(stream);
				return NULL;
			}
			else
				string = tmp;
		}
		ch = fgetc(stream);
	}
	string[i] = 0;
	if (feof(stream))
	{
		*err = ERROR_END_OF_FILE;
		return string;
	}
	if (ferror(stream))
	{
		*err = ERROR_READING_ERROR;
		return NULL;
	}
	return string;
}