to = $(USER)
mtp.robots:
	javac -classpath $(ROBOCODE)/libs/robocode.jar */*.java

clean:
	rm */*.class

deploy:
	mkdir -p $(ROBOCODE)/mtp.robots/$(to)
	cp */*.class $(ROBOCODE)/mtp.robots/$(to)

