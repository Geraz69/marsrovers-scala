# MarsRovers in Scala
<!-- If you'd like to use a logo instead uncomment this code and remove the text above this line

  ![Logo](URL to logo img file goes here)

-->

By [Gerardo Garcia Mendez](https://twitter.com/Geraz69).


## Prerequirements

You will need Java 1.7 and [SBT](http://www.scala-sbt.org/) up and running to run this codebase. Install it for [Windows](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Windows.html), [Mac](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Mac.html) or [Linux](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Linux.html).

After SBT is installed git clone this repo and go inside the repo root. Then run the compiler.

```bash
$ cd marsrovers-scala
$ sbt compile
```
NOTE: First time takes longer since it downloads Scala and SBT itself.

## Usage

To run it use the following command:

```bash
$ sbt "run-main MarsRovers data.txt"
```

The parameter after MarsRovers needs to be the location (absolute or relative to the repo root) of a file containing the positions about the rovers and instructions to move them.

The output of the program will be the final position of the rovers in the input or a stacktrace if there is an logical or IO error.

## Test Cases

You can run the unit tests with this command:

```bash
$ sbt test
```

