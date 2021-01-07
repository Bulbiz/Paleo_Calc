.PHONY: all clean test

JFLEX_OUTPUT = paleo-lib/src/main/java/paleo/lib/parser/JFLexer.java
JAR_NAME = paleo-demo
DEMO_VERSION = 1.2

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

doc: clean
	if [ -f paleolib-doc.html ]; then rm paleolib-doc.html; fi;
	if [ -f paleodemo-doc.html ]; then rm paleocalc-doc.html; fi;
	cd paleo-lib && mvn javadoc:javadoc
	cd paleo-demo && mvn javadoc:javadoc
	ln -s paleo-lib/target/site/apidocs/index.html paleolib-doc.html
	ln -s paleo-demo/target/site/apidocs/index.html paleodemo-doc.html

clean:
	if [ -f $(JAR_NAME).jar ]; then rm $(JAR_NAME).jar; fi;
	if [ -f $(JFLEX_OUTPUT) ]; then rm $(JFLEX_OUTPUT)*; fi;
	if [ -f paleolib-doc.html ]; then rm paleolib-doc.html; fi;
	if [ -f paleodemo-doc.html ]; then rm paleocalc-doc.html; fi;
	cd paleo-lib && mvn clean
	cd paleo-demo && mvn clean
