# AIML-core

This project is a Java 11 library that implements [AIML 2.0](https://callmom.pandorabots.com/static/reference/) and its handler.

## Getting Started

To run this project you will need the following:

- Java 11

Simply checkout this repository to get started.

### Installing

Currently there is no public compiled version. If you want to use this library in your projects, run the gradle build task 

```
gradle build
```

This task will compile the sources, run the tests and publish the compiled version into the <i>build/libs</i> folder. For now, simply use this Jar as a dependency in your project.


## Usage
You can use the AIMLHandlerBuilder to parse handle AIMLs. See 
<i>src/examples/java/examples/BasicExample.java</i>

If you just want to play around with the libary there is a command line client in <i>src/examples/java/examples/InteractiveExample.java</i>.
Just Enter a line and get an answer. To stop the Example, send `q` as a request.

## Running the tests

The JUnit tests are callable via the gradle machanisms.
```
gradle test
```

## Contributing

Please read [The Elements of AIML Style](https://files.ifi.uzh.ch/cl/hess/classes/seminare/chatbots/style.pdf) for details on AIML.
 
## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details