clean:
	mvn clean
	rm -rf jcstress.*
	rm -rf results/

run:
	mvn clean install
	java -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:-RestrictContended -Xbootclasspath/a:./lib/jcstress-core.jar:./lib/jopt-simple-4.9.jar:./target/jcstress-bootstrap.jar -jar target/jcstress-bootstrap.jar -v

.PHONY: clean run
