# Khan Academy Project Interview
### Revan Sopher

Implements "Infection" model for gradual feature roll-out, where groups of users interconnected by
"coaching"/"coached by" relationships receive the feature together.

## Total Infection
`TotalInfection.java` sequentially selects a random user, and infects all related users by searching the graph with a
depth first search.
This repeats until all users are selected.

## Partial Infection
`PartialInfection.java` attempts to infect exactly the given number of users.
It first searches the entire tree to calculate the sizes of the distinct groups, then continually infects the largest
group possible until the goal is reached, over- or undershooting if necessary.

## Visualizations
`InfectionUtils.java` prints a `UserMap` into an HTML file, using the `viz.js`([GraphViz](http://graphviz.org/) library -- not mine) to visualize.
This renders the graph in the browser, which is slow for big graphs but acceptable here.
The test code creates two files: `userMap.html` is the entire graph after generation, and `infection.html` is the progress of the infection updated
at each step.
Refresh the files to update.

## Generation
`UserMap.java` represents and generates sample graphs, with a bunch of configurable probabilities.
The generation starts by creating 30 users who have no teachers.
Every user has a 0.3 chance of being a teacher: teachers have N students, where N is a Gaussian random variable centered at 5.
Each disconnected group has at most 15 users, which limits the size of the test data.
Each student has a 0.01 chance of having a randomly selected second teacher, which can join together disconnected groups.

## Further work
Visualizing in Javascript lends itself nicely to building a Client/Server model, making a web control panel for
visualizing and controlling a feature roll-out.
