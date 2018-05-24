### Initial thoughts

I broke down this problem into steps. The first main one I thought to tackle would be creating methods to check for compatibility
based on distance, food categories, and time. The second would be to look through all of the pairs and determining which ones
were compatible. The last would be to take these pairs and write them to CSV files.

I decided to create multiple CSV files, one for each customer, that listed their possible recipients. I wanted to make checking pairs
based on customers easy, and while it would result in a lot of CSV files, they are relatively short and easy to read.

### Approach

- First I wrote the Parser class
   - I wanted to make sure it would work on any file of any size so that I could use it on both the recipient and customer file
- Then I worked on creating methods to check for compatibility
   - These methods would eventually become the Utils class
   - I did the distance one first and implemented the haversine formula that I found after some googling
   - I wrote two test cases for the distance checker (and all of the other compatibility methods)
      - The base test checks for edge cases (i.e. the two points are identical or wildly far apart)
      - The other one checks for points that are approximately 10 miles away from each other
      - I used online resources to determine correct distances, then made sure my answers were close to them
        - In the process, I learned that some of the provided locations were in the ocean, which I found amusing
   - I then wrote the category method
      - It converts the corresponding numbers into binary strings, then formats them so that they're 6 digits long with leading 0s
      - The base test case checks all possible combinations of true and false inputs
         - If the recipient has 1s, or if the customer has 1s, and when they match 1s
      - The other one checks for two examples of more expected cases
   - I wrote the time method last
      - It parses the given drop-off date to isolate the date and the time
      - The date is converted to get the day of the week, and the time is converted into an integer
      - The customer schedule corresponding to the day of the week is converted into a 16-digit binary string
      - The bit in the binary string corresponding to the time is then checked to see if it's 1 or 0
      - Like in the previous examples, the base test checks for cases that have obvious outputs, and the other one checks examples that the method is more likely to encounter
   - I then wrote one final method that calls the three
- Next I wrote a method to begin matching the recipients and customers
   - At first it looped through all possible pairs and immediately wrote the CSV file
   - It was unordered; recipients would be written in the order they were encountered
- I changed the structure of the Matcher class to include more features
  - It now counted popularities while looping through the pairs and stored recipients in arraylists instead of printing them immediately
  - Distances were also stored to improve sorting
  - I used hash maps to link customers and their arraylist of possible recipients, then looped through them afterwards to print the results
  - I then sorted the arraylist of recipients by popularity then by distance
- Afterwards I made some slight improvements to the file writing
  - I moved the file writing method into its own Printer class
  - I also slightly modified the writing to accomodate multiple customers with the same name (in case they had multiple entries)
  
### Results

The program I wrote successfully outputs CSV files for each of the customers. The files have all of the correct recipients in order
of popularity than distance. Having multiple CSV files makes it easier to check on individual customers and get an easy glimpse
into their possible options. Having one CSV file with every compatible match ranked might have also been feasible, and it would have
made certain actions like iterating through it and determining which pairs to actually use (since you could easily customers and recipients as
having been matched already, reducing the number of possible pairs later), but it's a tradeoff I expected.

If I had more time, I would have liked to implement this alternate formatting as well, then created a method to go through the
matches and actually figure out which pairs would work the best. I would have also liked to have made the matching algorithm more
sophisticated; for example, if a certain customer can only match with one recipient, then that recipient should have reduced
priority for everyone else.

I would have also liked to improve the time compatibility method to accomodate different timezones. Right now it assumes that
the recipient and customer are in the same timezone, which is certainly likely, but there might be cases where that's not 
safe to assume.
