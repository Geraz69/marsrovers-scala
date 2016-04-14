# MarsRovers in Scala
<!-- If you'd like to use a logo instead uncomment this code and remove the text above this line

  ![Logo](URL to logo img file goes here)

-->

By [Gerardo Garcia Mendez](author URL goes here).


## Installation

Git clone this repo. Scala and all the needed libraries are shiped with it for convenience

After you have it go inside the repo root and compile the source code:

```bash
$ cd marsrovers-scala
$ mkdir classes
$ lib/scala-2.11.7/bin/scalac -d classes -cp classes:lib/scalatest_2.11-2.2.6.jar src/*.scala
```

* you need to have Java 1.7 installed

## Usage

To run it use the following command:

```bash
$ lib/scala-2.11.7/bin/scala -classpath classes MarsRovers data.txt
```

The first parameter needs to be the location (absolute or relative to the repo root) of a file containing the positions about the rovers and instructions to move them.

The output of the program will be the final position of the rovers in the input or a stacktrace id there is an logical or IO error.


## Test Cases

You can run the unit tests with this command:

```bash
$ lib/scala-2.11.7/bin/scala -classpath classes:lib/scalatest_2.11-2.2.6.jar org.scalatest.run MarsRoversSpec
```

## License

GNU GPL lincence applies.