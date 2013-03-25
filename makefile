TO = $(USER)
robots: 
	javac -classpath $(ROBOCODE)/libs/robocode.jar */*.java
	
clean:
	rm */*.class

deploy:
	rm -rf $(ROBOCODE)/robots/$(TO)
	mkdir -p $(ROBOCODE)/robots/$(TO)
	cp */*.class $(ROBOCODE)/robots/$(TO)

