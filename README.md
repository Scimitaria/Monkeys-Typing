# Monkeys-Typing
The Monkeys Typing algorithm is a custom variant of the genetic algorithm.

It's a bit rough, as I came up with the idea in my freshman year of college, but it does generally work.  
The backend framework for this algorithm was provided by the CS1 spacegame assignment - thanks Dr. Hibbs!

This program can be run manually with the standard gradle command:
```
./gradlew run -Pmain=cs2.AIP.<main file> --args="<population> <initActions> <addRate> <addNum>"
```

There is also a shell script for ease of use, which takes main function and parameters as argument:
```
./run <main> <population> <initActions> <addRate> <addNum> <numParents> <algToggle>
```

Main is the name of the file to run.  
Population is the starting number of agents.  
initActions is the starting number of actions.  
addRate is the number of iterations before new actions are added.  
addNum is the number of actions to add.  
numParents is the number of parents selected for the genetic algorithm.
algToggle switches between the Genetic Algorithm and Monkeys Typing. True selects GA; false is MT.

Default values:
```
Main file   = Playground
population  = 50
initActions = 100
addRate     = 1
addNum      = initActions
numParents  = 2
algToggle   = true
```

The main files are:

### Playground:
An R&D ground for the algorithms.   
Very barebones in terms of graphics, but a good way to test different parameters.  
This is the default if no name is specified in the script.

### Mario (UNDER DEVELOPMENT):
mario 
