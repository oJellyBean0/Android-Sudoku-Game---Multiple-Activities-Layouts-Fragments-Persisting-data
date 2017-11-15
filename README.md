# Android-Sudoku-Game---Multiple-Activities-Layouts-Fragments-Persisting-data

```
## Objectives:

```
 Multiple Activities, Layouts & Fragments
 Persisting data
 Styles & Themes
```
## Task 1: Sudoku (Fragments & Styles)

Sudoku is a number placement puzzle game played on a large 3x3 grid of smaller 3x3 regions. Each
3x3 region may only contain the numbers 1..9 once (rule 1). In the larger grid, the numbers 1..9 may
only appear once in each row of the grid (rule 2) and each column of the grid (rule 3). The puzzle is
solved once all the regions have been filled in and none of the rules have been violated.

For this particular task, you are to demonstrate that you understand how a number of different
topics covered in class work, specifically how fragments work. In order to do this, you must create a
_region_ **fragment** that contains the layout and logic necessary to handle a single region (the smaller
3x3 grid between the darker lines).

The region should be initialised with its position in the larger grid, as well as a number of starting
values in specific cells, which _cannot_ be changed by the user (bold face these). The user should be
able to specify a new number for an initially empty cell. After the user has changed a value, the
region logic should check whether rule 1 holds. Any cell violating this rule should be visually changed
to look different (e.g. draw both cells in a red font).

The region fragment can inform other interested parties of interesting things about itself including:

```
 when the region has been initialised,
 when the region has been completed (with no rules violated),
 when the user changed a particular cell’s value
 when the value of a cell in this region clashes with the value of another cell (in the same
region or a different region (which would be checked in response to the other region’s cell
value changing)), etc.
```
Carefully consider how the events described above control the flow of the game _before_
implementation.

As discussed in class, there are a number of ways of allowing different objects to communicate with
one another. Pick an appropriate method to allow the regions and the grid to communicate with one
another in an appropriate manner.

The Sudoku puzzle activity should make use of the region fragment by having 9 of them arranged in
a 3x3 grid and initialising each with appropriate starting values. Ensure that rules 1, 2 and 3 are
checked at appropriate times in response to user interaction.

Include a turn count that counts the number of turns the player has taken so far.

If the puzzle has been solved, then display a winning message, including the number of turns taken
to solve it.

## Task 2: Sudoku (Multiple Activities & Persistence)

Extend Task 1 so that the application has multiple activities.

There should be a main scene, on which the application starts, that displays a list of puzzles available
to be played (the puzzles should be loaded from a file on the device). The list should display, for each
puzzle, the minimum number of turns that have been taken to solve the puzzle. If a puzzle has not
been played before, then an “unsolved” message should be displayed instead. You need to include
at _least_ five different, solvable puzzles.

If the user clicks a puzzle in the list, then the main scene activity should pass the puzzle to be played
to the puzzle activity written in Task 1. The puzzle activity should _return_ the number of turns taken
to complete the puzzle if solved.

If the puzzle has been solved and the player has beaten the existing minimum number of turns, then
the list should be updated and the new score saved to the device.

If the player minimizes the application, or it is closed, the current screen and UI data should be
stored. In other words, if the player is currently playing a puzzle and the application is minimized and
closed, then the next time the application opens, that puzzle must be opened _and_ the values the
player has placed must still be the same.

It should be possible to quit a puzzle and return to the main scene activity at any time while playing
a puzzle. Be sure to update the best scores in this case.
