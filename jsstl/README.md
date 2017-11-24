### jSSTL : java Signal Spatio Temporal Logic  ###
jSSTL is a Java library for the specification and verification of spatio-temporal properties of dynamical systems described as spatio-temporal traces over a weighted graph.
Given a trace x(t,l), a weighted graph and a formula Phi, it returns the boolean and quantitative spatio-temporal signals of the satisfaction at each time and in each location. 

It consists of three main packages:  `core`, `util` and `io`.

* `core.formula` contains the classes used to specify an SSTL formulas. They mimic the abstract syntax tree of formulas. 
`core.monitor` provides the implementation of the monitoring algorithms to verify an SSTL formulas. The monitoring is performed via a visit of a formula that implements a bottom-up evaluation.  
`core.space` provides the classes for the representation of the graph. It relies on JGraphT, a free Java graph library that provides mathematical graph-theory objects and algorithms.
The methods `booleanCheck(graphModel, inputSignal)` and `quantitativeCheck(graphModel, inputSignal)` of a formula produce the boolean and quantitative signals for the trace inputSignal with a topology of the considered locations as a graphModel.

* `util` contains the classes used to represent and manage the signals.

* `io` provides a set of classes that can be used to read graph models and input signals from an input stream and to write monitoring results to an output stream. Currently,  CSV and tabular based ascii files are supported for both input and 
output of signals.   


An Eclipse plug-in providing front-end for SSTL monitoring is currently under development.

### How to run ###

A running example is provided within the library.

### Who do I talk to? ###

* laura.nenzi-at-imtlucca.it
* michele.loreti-at-unifi.it