# AIML-core

This project is a Java 1.8 library that implements [AIML 1.0](https://pandorabots.com/docs/aiml/aiml-basics.html) and its handler.

## Getting Started

To run this project you will need the following:

- Gradle
- Java 1.8

Simply checkout this repository to get started.

### Installing

Currently there is no public compiled version. If you want to use this library in your projects, run the gradle build task 

```
gradle build
```

This task will compile the sources, run the tests and publish the compiled version into the <i>build/libs</i> folder. For now, simply use this Jar as a dependency in your project.


## Usage
You can use the AIMLFileReader to parse and load AIML Files which mathces the AIML 1.0 definition.
```java
List<AIML> aimlFiles = new ArrayList<>();

try (AIMLFileReader reader = new AIMLFileReader(file))
{
    aimlFiles = reader
        .withBotMemory(botMem)
        .stream()
        .collect(Collectors.toList());
}
catch(Exception e)
{
    // TODO: handle exception
}
```
This files can be used to feed the AIMLHandlerBuilder to create an AIMLHandler.
```java
AIMLHandler handler = new AIMLHandlerBuilder()
    .nonStaticMemory(new HasMap<>())
    .withBotMemory(botMem)
    .withAiml(aimlFiles)
    .build();                      
```
 After creation it is possible to request answers.
```java
String response = handler.getAnswer(input);
```

## Running the tests

The JUnit tests are callable via the gradle machanisms.
```
gradle test
```

## Contributing

Please read [The Elements of AIML Style](https://files.ifi.uzh.ch/cl/hess/classes/seminare/chatbots/style.pdf) for details on AIML.
 
## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details