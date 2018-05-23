# Overview

The parser takes in information on customers (who have surplus food to donate) and recipients (who are in need of such food) and returns, for each customer, a list of potential recipients. Recipients and customers are matched based on their distance from each other, whether the food needs of the recipient match the category of the food being donated, and whether the recipients are available when the food will be dropped off.

The customer and recipient information is passed in with CSV files. The customer format is:

```FirstName,LastName,Street,City,State,Postal,Country,Email,Phone,Latitude,Longitude,Categories,PickupAt,TimeZoneId```

And the recipient format is:

```FirstName,LastName,Street,City,State,Postal,Country,Email,Phone,Latitude,Longitude,Restrictions,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday```

The parser generates a CSV file for each customer, which has a list of potential recipients. The output file follows the same format as the recipient file.

---

# Methods

## CopiaMatcher

### main

Calls the Parser and Match classes to parse the CSV files and match recipients and customers Prints multiple CSV files, one for each customer, that list compatibile recipients. There may be zero to mulitple potential recipients for each customer. If there are multiple, the recipients are listed in order of overall popularity (how many customers they can match with) then on distance (how far thehy are from the customer).

This method requires two arguments. The first is the pathname to the folder where Customers.csv and Recipients.csv are located. The second is the pathname to the folder where the output CSV files are to be written.

## Parser

### parseCSV

Takes in a CSV file and returns an array list of lists of strings, each list corresponding to one row in the file.

## Utils

### checkCompatibility

Takes in two lists of strings corresponding to a customer and a recipient, then runs the three checking functions to see if the two are compatible. If the two do not fulfill the necessary requirements (e.g. they're over 10 miles apart), then -1 is returned. If they do fulfill the requirements, then the distance between them is returned.

## checkCategories

Takes in two strings of integers corresponding to the categories of food the customer is donating and the categories of food the recipient cannot accept. Returns true if these two values are compatibile, i.e. the customer is donating food that the recipient can accept. Returns false otherwise.

## checkDistance:

Takes in four values representing the latitude and longitude of the customer and the recipient. Uses the haversine formula to compute the distance between them in miles. Returns that distance.

## checkTime:

Takes in a string representing when the customer's food is first available for pickup and a list of strings representing the recipient's information. Determines the day of the week the customer is donating the food, then finds the corresponding schedule availability for the recipient. Returns true if the recipient is available during that time, and false otherwise.


## Matcher

### match

Takes in an arraylist of customer information and an arraylist of recipient information. Loops through both and sees if each pair of customers and recipients are compatible. Returns a map of recipients and the total number of customers they're compatible with (i.e. the recipients' popularities), and a map of customers and an arraylist of all of the recipients they're compatible with.

### sort

Takes in a customer ID string, a map of the recipient popularity, and a map of the customers and their compatible recipients. For the given ID, sorts that customer's arraylist of recipients first based on each recipient's popularity, then their distance to the customer.

## Printer

### print

Takes in a pathname to the folder where the output files should be written, the popularity map, and the recipient-customer map. For each customer, creates a CSV file with a list of their compatible recipients, sorted by popularity then distance. The column headers are the same as from the original Recipients.csv file, but with the popularity and distance appended. 
