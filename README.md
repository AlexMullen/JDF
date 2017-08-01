# Java Draughts Framework

A Draughts/Checkers framework written in Java.

## Variants implemented
*   English
*   International

## Notes about code
Some will notice I have performed some minor Java/OOP/Encapsulation sins by publically exposing some instance arrays in the `Board` class. Doing this was measurably faster than accessing them through a getter method on Android devices at the time - I am unsure whether this is still the case.
