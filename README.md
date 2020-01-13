[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)

Java implementation of [lsm-tree](https://en.wikipedia.org/wiki/Log-structured_merge-tree)

This repository will contain three implementations of Trees (Binary,AVL,Red black) using [OOP principles](https://www.elegantobjects.org/#principles)

## TODO

 - ~~Implement Binary tree~~ (done)
 - ~~Implement AVL Tree~~(done)
 ~ Implement B-Tree
 - Implement Red black Tree
 - Implement simple and fast distributed key value storage
 
## Motivation

There are a lot of Java based Tree's implementations in Internet . Why another one ? 
All implementations that I saw were unreadable and hard to understand  
In this repository I want to implement all trees using Object Oriented Design so everyone will be able to look at code , check Unit tests and understand the logic

Finally I'm going to implement fast distributed key value storage because the same reason. I saw source code of [LevelDB](https://github.com/google/leveldb) and it was hard for me to understand how does it work  because source code looks like a mass of procedures 
## How to contribute?

Just fork the repo and send us a pull request.

Make sure your branch builds without any warnings/issues and it passes all our [checkstyles](https://github.com/strogiyotec/jminikeyvalue/blob/master/checkstyle.xml):

```
mvn checkstyle:check && mvn clean install
```


Some notes:
 - Root - Node without parent
 - Internal node - Any node with at least one child
 - Leaf node(External node) - Node without child
 - Predecessor - For an element X, its in-order predecessor is defined as the largest key smaller than X.
 - Successor - For an element X, its in-order successor is defined as the smallest key larger than X.