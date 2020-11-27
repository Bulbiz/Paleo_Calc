.PHONY: all clean

JFLEX_OUTPUT = paleo-lib/src/main/java/paleo/lib/parser/Lexer.java

all: lib

lib:
	if [ -f $(JFLEX_OUTPUT) ]; then rm $(JFLEX_OUTPUT)*; fi;
	cd paleo-lib && mvn package install

clean:
	cd paleo-lib && mvn clean

generate-doc:
	cd paleo-lib && mvn site
