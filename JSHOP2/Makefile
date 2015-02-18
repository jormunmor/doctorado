c: src/JSHOP2/JSHOP2.g src/JSHOP2/*.java
	cd src/JSHOP2; java antlr.Tool JSHOP2.g; javac *.java
	cd src; jar cvf JSHOP2.jar JSHOP2/*.class;
	rm src/JSHOP2/*.class
	mv src/JSHOP2.jar bin.build/

clean:
	rm src/JSHOP2/JSHOP2Lexer.*
	rm src/JSHOP2/JSHOP2Parser.*
	rm src/JSHOP2/JSHOP2TokenTypes.java
	rm src/JSHOP2/JSHOP2TokenTypes.txt


d: src/JSHOP2/*.java
	rm -rf doc
	cd src; javadoc -d ../doc -author -version -private JSHOP2

1: bin.build/JSHOP2.jar
	cd examples/quadrotor_arcas; java JSHOP2.InternalDomain quadrotor
	cd examples/quadrotor_arcas; java JSHOP2.InternalDomain -r problem
	cd examples/quadrotor_arcas; javac gui.java
	cd examples/quadrotor_arcas; java -Xmx256M gui
	cd examples/quadrotor_arcas; rm quadrotor.java; rm quadrotor.txt; rm problem.java; rm *.class
