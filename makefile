to = $(USER)
robots: 
	javac -classpath $(ROBOCODE)/libs/robocode.jar */*.java
	
clean:
	rm */*.class

deploy:
	mkdir -p $(ROBOCODE)/robots/$(to)
	cp */*.class $(ROBOCODE)/robots/$(to)

