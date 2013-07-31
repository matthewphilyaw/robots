MilkShake is an attempt to add analytics to the mix.

Main idea here is to use a graph to store the heading of the scanned robots normalized to some fraction of an angle. For example, say we normalize by rounding to the nearest 5th of an angle; if the scanned robot's heading is something like 30.35 degrees, then it would be rounded to 30.40, then stored in the graph. The idea being that the faction chosen will represent a margin of acceptable error between each step. That way given several headings close in range that get rounded correctly to the corresponding fractiong and this can be used as a key in dictionary to index in the to graph. Also keeps noise out of the data to some degree and avoids needless duplication of data that for all intents and purposes are close enough that they could be one value instead.

The graph should keep a collection of incoming and outgoing edges, and those edges will contain prior headings that led to the scaned angle (incoming for the node with the current scanned heading), and a set of edges that represent possibly nodes to navigate too (outgoing). The outgoing edges will have weights attached to them, and multiple weights representing the different paths that led to there based on historical data. For example a given edge could have three weights, one for each known way of getting to it; if given the current heading scanned (call it c), and two prior headings (p1, p2) then say the edge that contains the next node n (n) would contain a weight for the path (p1, p2), where as next node n2, may only have a weight for (p1) because of a different path. 

With this structure in place given a current scanned heading, and two to three prior headings you can start to build up weights based on how frequently that pattern occurs. Then given a current heading you can predict or find the strongest edge to follow. The structure also minimizes the amount of duplication you need.