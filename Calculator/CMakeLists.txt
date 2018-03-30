cmake_minimum_required(VERSION 3.9)
project(tmp3stage)

set(CMAKE_CXX_STANDARD 11)

add_executable(tmp3stage
        bool.h
        calculate.c
        calculate.h
        checkString.c
        checkString.h
        error.c
        error.h
        getString.c
        getString.h
        main.c)
