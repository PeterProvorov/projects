# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.9

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/peto/Downloads/tmp3stage

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/peto/Downloads/tmp3stage/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/tmp3stage.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/tmp3stage.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/tmp3stage.dir/flags.make

CMakeFiles/tmp3stage.dir/calculate.c.o: CMakeFiles/tmp3stage.dir/flags.make
CMakeFiles/tmp3stage.dir/calculate.c.o: ../calculate.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/peto/Downloads/tmp3stage/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/tmp3stage.dir/calculate.c.o"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/tmp3stage.dir/calculate.c.o   -c /Users/peto/Downloads/tmp3stage/calculate.c

CMakeFiles/tmp3stage.dir/calculate.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/tmp3stage.dir/calculate.c.i"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/peto/Downloads/tmp3stage/calculate.c > CMakeFiles/tmp3stage.dir/calculate.c.i

CMakeFiles/tmp3stage.dir/calculate.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/tmp3stage.dir/calculate.c.s"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/peto/Downloads/tmp3stage/calculate.c -o CMakeFiles/tmp3stage.dir/calculate.c.s

CMakeFiles/tmp3stage.dir/calculate.c.o.requires:

.PHONY : CMakeFiles/tmp3stage.dir/calculate.c.o.requires

CMakeFiles/tmp3stage.dir/calculate.c.o.provides: CMakeFiles/tmp3stage.dir/calculate.c.o.requires
	$(MAKE) -f CMakeFiles/tmp3stage.dir/build.make CMakeFiles/tmp3stage.dir/calculate.c.o.provides.build
.PHONY : CMakeFiles/tmp3stage.dir/calculate.c.o.provides

CMakeFiles/tmp3stage.dir/calculate.c.o.provides.build: CMakeFiles/tmp3stage.dir/calculate.c.o


CMakeFiles/tmp3stage.dir/checkString.c.o: CMakeFiles/tmp3stage.dir/flags.make
CMakeFiles/tmp3stage.dir/checkString.c.o: ../checkString.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/peto/Downloads/tmp3stage/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object CMakeFiles/tmp3stage.dir/checkString.c.o"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/tmp3stage.dir/checkString.c.o   -c /Users/peto/Downloads/tmp3stage/checkString.c

CMakeFiles/tmp3stage.dir/checkString.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/tmp3stage.dir/checkString.c.i"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/peto/Downloads/tmp3stage/checkString.c > CMakeFiles/tmp3stage.dir/checkString.c.i

CMakeFiles/tmp3stage.dir/checkString.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/tmp3stage.dir/checkString.c.s"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/peto/Downloads/tmp3stage/checkString.c -o CMakeFiles/tmp3stage.dir/checkString.c.s

CMakeFiles/tmp3stage.dir/checkString.c.o.requires:

.PHONY : CMakeFiles/tmp3stage.dir/checkString.c.o.requires

CMakeFiles/tmp3stage.dir/checkString.c.o.provides: CMakeFiles/tmp3stage.dir/checkString.c.o.requires
	$(MAKE) -f CMakeFiles/tmp3stage.dir/build.make CMakeFiles/tmp3stage.dir/checkString.c.o.provides.build
.PHONY : CMakeFiles/tmp3stage.dir/checkString.c.o.provides

CMakeFiles/tmp3stage.dir/checkString.c.o.provides.build: CMakeFiles/tmp3stage.dir/checkString.c.o


CMakeFiles/tmp3stage.dir/error.c.o: CMakeFiles/tmp3stage.dir/flags.make
CMakeFiles/tmp3stage.dir/error.c.o: ../error.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/peto/Downloads/tmp3stage/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building C object CMakeFiles/tmp3stage.dir/error.c.o"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/tmp3stage.dir/error.c.o   -c /Users/peto/Downloads/tmp3stage/error.c

CMakeFiles/tmp3stage.dir/error.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/tmp3stage.dir/error.c.i"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/peto/Downloads/tmp3stage/error.c > CMakeFiles/tmp3stage.dir/error.c.i

CMakeFiles/tmp3stage.dir/error.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/tmp3stage.dir/error.c.s"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/peto/Downloads/tmp3stage/error.c -o CMakeFiles/tmp3stage.dir/error.c.s

CMakeFiles/tmp3stage.dir/error.c.o.requires:

.PHONY : CMakeFiles/tmp3stage.dir/error.c.o.requires

CMakeFiles/tmp3stage.dir/error.c.o.provides: CMakeFiles/tmp3stage.dir/error.c.o.requires
	$(MAKE) -f CMakeFiles/tmp3stage.dir/build.make CMakeFiles/tmp3stage.dir/error.c.o.provides.build
.PHONY : CMakeFiles/tmp3stage.dir/error.c.o.provides

CMakeFiles/tmp3stage.dir/error.c.o.provides.build: CMakeFiles/tmp3stage.dir/error.c.o


CMakeFiles/tmp3stage.dir/getString.c.o: CMakeFiles/tmp3stage.dir/flags.make
CMakeFiles/tmp3stage.dir/getString.c.o: ../getString.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/peto/Downloads/tmp3stage/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building C object CMakeFiles/tmp3stage.dir/getString.c.o"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/tmp3stage.dir/getString.c.o   -c /Users/peto/Downloads/tmp3stage/getString.c

CMakeFiles/tmp3stage.dir/getString.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/tmp3stage.dir/getString.c.i"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/peto/Downloads/tmp3stage/getString.c > CMakeFiles/tmp3stage.dir/getString.c.i

CMakeFiles/tmp3stage.dir/getString.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/tmp3stage.dir/getString.c.s"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/peto/Downloads/tmp3stage/getString.c -o CMakeFiles/tmp3stage.dir/getString.c.s

CMakeFiles/tmp3stage.dir/getString.c.o.requires:

.PHONY : CMakeFiles/tmp3stage.dir/getString.c.o.requires

CMakeFiles/tmp3stage.dir/getString.c.o.provides: CMakeFiles/tmp3stage.dir/getString.c.o.requires
	$(MAKE) -f CMakeFiles/tmp3stage.dir/build.make CMakeFiles/tmp3stage.dir/getString.c.o.provides.build
.PHONY : CMakeFiles/tmp3stage.dir/getString.c.o.provides

CMakeFiles/tmp3stage.dir/getString.c.o.provides.build: CMakeFiles/tmp3stage.dir/getString.c.o


CMakeFiles/tmp3stage.dir/main.c.o: CMakeFiles/tmp3stage.dir/flags.make
CMakeFiles/tmp3stage.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/peto/Downloads/tmp3stage/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Building C object CMakeFiles/tmp3stage.dir/main.c.o"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/tmp3stage.dir/main.c.o   -c /Users/peto/Downloads/tmp3stage/main.c

CMakeFiles/tmp3stage.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/tmp3stage.dir/main.c.i"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/peto/Downloads/tmp3stage/main.c > CMakeFiles/tmp3stage.dir/main.c.i

CMakeFiles/tmp3stage.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/tmp3stage.dir/main.c.s"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/peto/Downloads/tmp3stage/main.c -o CMakeFiles/tmp3stage.dir/main.c.s

CMakeFiles/tmp3stage.dir/main.c.o.requires:

.PHONY : CMakeFiles/tmp3stage.dir/main.c.o.requires

CMakeFiles/tmp3stage.dir/main.c.o.provides: CMakeFiles/tmp3stage.dir/main.c.o.requires
	$(MAKE) -f CMakeFiles/tmp3stage.dir/build.make CMakeFiles/tmp3stage.dir/main.c.o.provides.build
.PHONY : CMakeFiles/tmp3stage.dir/main.c.o.provides

CMakeFiles/tmp3stage.dir/main.c.o.provides.build: CMakeFiles/tmp3stage.dir/main.c.o


# Object files for target tmp3stage
tmp3stage_OBJECTS = \
"CMakeFiles/tmp3stage.dir/calculate.c.o" \
"CMakeFiles/tmp3stage.dir/checkString.c.o" \
"CMakeFiles/tmp3stage.dir/error.c.o" \
"CMakeFiles/tmp3stage.dir/getString.c.o" \
"CMakeFiles/tmp3stage.dir/main.c.o"

# External object files for target tmp3stage
tmp3stage_EXTERNAL_OBJECTS =

tmp3stage: CMakeFiles/tmp3stage.dir/calculate.c.o
tmp3stage: CMakeFiles/tmp3stage.dir/checkString.c.o
tmp3stage: CMakeFiles/tmp3stage.dir/error.c.o
tmp3stage: CMakeFiles/tmp3stage.dir/getString.c.o
tmp3stage: CMakeFiles/tmp3stage.dir/main.c.o
tmp3stage: CMakeFiles/tmp3stage.dir/build.make
tmp3stage: CMakeFiles/tmp3stage.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/peto/Downloads/tmp3stage/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_6) "Linking C executable tmp3stage"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/tmp3stage.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/tmp3stage.dir/build: tmp3stage

.PHONY : CMakeFiles/tmp3stage.dir/build

CMakeFiles/tmp3stage.dir/requires: CMakeFiles/tmp3stage.dir/calculate.c.o.requires
CMakeFiles/tmp3stage.dir/requires: CMakeFiles/tmp3stage.dir/checkString.c.o.requires
CMakeFiles/tmp3stage.dir/requires: CMakeFiles/tmp3stage.dir/error.c.o.requires
CMakeFiles/tmp3stage.dir/requires: CMakeFiles/tmp3stage.dir/getString.c.o.requires
CMakeFiles/tmp3stage.dir/requires: CMakeFiles/tmp3stage.dir/main.c.o.requires

.PHONY : CMakeFiles/tmp3stage.dir/requires

CMakeFiles/tmp3stage.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/tmp3stage.dir/cmake_clean.cmake
.PHONY : CMakeFiles/tmp3stage.dir/clean

CMakeFiles/tmp3stage.dir/depend:
	cd /Users/peto/Downloads/tmp3stage/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/peto/Downloads/tmp3stage /Users/peto/Downloads/tmp3stage /Users/peto/Downloads/tmp3stage/cmake-build-debug /Users/peto/Downloads/tmp3stage/cmake-build-debug /Users/peto/Downloads/tmp3stage/cmake-build-debug/CMakeFiles/tmp3stage.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/tmp3stage.dir/depend
