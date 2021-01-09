.PHONY: all clean test

JFLEX_OUTPUT = paleo-lib/src/main/java/paleo/lib/parser/JFLexer.java
JAR_NAME = paleo-demo
DEMO_VERSION = 2.0

all: lib demo

lib:
	if [ -f $(JFLEX_OUTPUT) ]; then rm $(JFLEX_OUTPUT)*; fi;
	cd paleo-lib && mvn package install

demo:
	cd paleo-demo && mvn compile test assembly:single
	cp paleo-demo/target/demo-$(DEMO_VERSION)-jar-with-dependencies.jar \
	   $(JAR_NAME).jar

test:
	cd paleo-lib && mvn test

run:
	java -jar $(JAR_NAME).jar

doc:
	cd paleo-lib && mvn javadoc:javadoc
	cd paleo-demo && mvn javadoc:javadoc

clean:
	if [ -f $(JAR_NAME).jar ]; then rm $(JAR_NAME).jar; fi;
	if [ -f $(JFLEX_OUTPUT) ]; then rm $(JFLEX_OUTPUT)*; fi;
	cd paleo-lib && mvn clean
	cd paleo-demo && mvn clean
