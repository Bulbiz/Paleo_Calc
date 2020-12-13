.PHONY: all clean test

JFLEX_OUTPUT = paleo-lib/src/main/java/paleo/lib/parser/JFLexer.java
JAR_NAME = paleo-calculator

all: lib calc

lib:
	if [ -f $(JFLEX_OUTPUT) ]; then rm $(JFLEX_OUTPUT)*; fi;
	cd paleo-lib && mvn package install

calc:
	cd paleo-calc && mvn compile test assembly:single
	cp paleo-calc/target/calc-1.2-jar-with-dependencies.jar \
	   $(JAR_NAME).jar

test:
	cd paleo-lib && mvn test

run:
	java -jar $(JAR_NAME).jar

doc:
	if [ -f paleolib-doc.html ]; then rm paleolib-doc.html; fi;
	if [ -f paleocalc-doc.html ]; then rm paleocalc-doc.html; fi;
	cd paleo-lib && mvn javadoc:javadoc
	cd paleo-calc && mvn javadoc:javadoc
	ln -s paleo-lib/target/site/apidocs/index.html paleolib-doc.html
	ln -s paleo-calc/target/site/apidocs/index.html paleocalc-doc.html

clean:
	if [ -f $(JAR_NAME).jar ]; then rm $(JAR_NAME).jar; fi;
	if [ -f $(JFLEX_OUTPUT) ]; then rm $(JFLEX_OUTPUT)*; fi;
	if [ -f paleolib-doc.html ]; then rm paleolib-doc.html; fi;
	if [ -f paleocalc-doc.html ]; then rm paleocalc-doc.html; fi;
	cd paleo-lib && mvn clean
	cd paleo-calc && mvn clean
