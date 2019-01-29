Word Search
--

Your task is to produce a solution to a word search problem; this is the sort of thing printed in crossword magazines and so on. You’re given a grid of letters and have to find a word which appears horizontally or vertically in the grid. The grid consists only of the 26 letters in the range a…z and the words are not dictionary words, just random collections of up to 24 characters with an average length of 9 characters. There’s nothing special in those values, they’re just based on the corresponding values from `/usr/share/dict/words` on my computer.

In our case we’re only interested in words which appear vertically from top down and horizontally left to right. You are to flesh out the following implementation:
```
class WordSearch(object):
    def __init__(self, grid):
        pass
    
    def is_present(self, word):
        return True
```

In the `__init__` method, `grid` is a string representing the entire word grid, with each row concatenated; there is no intervening separator, instead you will have to rely on the fixed row length `ROW_LENGTH` to determine the end of a row.

The `is_present` method returns `True` if word is present in the grid according to our rules.
Your class will be used as follows
```
ws = WordSearch(grid)

for word in words_to_find:
    if ws.is_present(word):
        print "found {}".format(word)
```

Now the twist: the grid you will be given is a square grid with 10,000 (104) letters in each direction, i.e `ROW_LENGTH = 10000`. `words_to_find` is an array of 1,000,000 (106) words. And we’re looking for a solution which is optimised for run time efficiency. The sizes are not chosen for any specific magic reason, just to indicate that this is a ”big” problem.

We’re not looking for micro-optimisations in the code: if that were the case dispensing with Python
might be a better idea! Instead look at the algorithm side of things.

As a bonus question: how would you go about taking advantage of a multicore system?


## Comments on My Solution
My solution is to index the grid by characters. Currently, in the `Grid` class, there is a constant called `INDEX_LIMIT`. Editing this will change the number of characters that will be indexed right and down from each location in the grid. For example, in the grid
```
a b c
d e f
g h i
```
for position 0, 0 will be added to the list of `a`, `ab` and `ad` using `INDEX_LIMIT = 2`. When a word is less than or equal to `INDEX_LIMIT`, the existence of a list is checked. When a longer word is searched for, the begining of the word is considered to select the correct list. Then positions within that list are then searched for the word. If the word is found, `true` is returned, if the end of the list is reached without a match, `false` is returned.

I have written some unit tests for my code. This helped me to determine if parts of my code were working as intended. It also allowed me to test the speed of my solution.

I had to increase the maximum heap space of my JVM to run these solutions. This involved using the `-Xmx` parameter. Currently, I have concluded the following running times for the large grid on my pc:

| Index Length | Average time for 1 word |
| --- | ---|
| 1 | 75ms |
| 2 | 25ms |
| 3 | 5ms |

Beyond this, I started running into issues where my computers 16GB of RAM was being exceeded, and the run time was being affected by virtual memory usage (leading to huge increases in search times per word, reaching up to 7 seconds). Therefore, I chose to leave my final implementation as a 3 character index. 

Here are the results for running my final solution on my unit tests:

![Unit Test Results](https://i.imgur.com/bmOjQMt.png)

Please note: There was an additional ~3 minute time to setup the grid to run these tests on.

In conclusion, with my solution there is a tradeoff between memory usage, grid generation time and search time. A higher `index_limit` will result in a higher memory usage, and a longer generation time. However, for a large set of words, the longer generation time may be much shorter than the time spent to search for all of the words. In the example, you would ideally like to index 9 characters, as this is the average length of words, and therefore massively increase the search speeds. I could improve my solution by finding a way to reduce my memory usage.

## Extension
For my solution, you could use multithreading to increase the speed of the grid generation. For example, you could create a list of integers representing each character in the grid that still needs to be indexed. You could then create several threads that take a value from this list, and delete it from the list. The thread then goes to that position in the grid, and then index characters as normal.

To make this thread safe, the method to read and remove the position from the list should be `synchronised`, which stops 2 threads from indexing the same position. The read/write to the `HashSet` storing the indexes should also be locked. This stops any accidental overwriting of the indexes which could lead to words being missed.

The part of the code which runs through the list of words could also be multithreaded. To do this, threads should check single words each. The list should be locked whenever it is accessed/removed from, and the word being searched for should be removed from the list. This should increase the speed that words are searched for within the grid.