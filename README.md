# Pennsylvania Real Estate Data Study

## Background
This java application uses a data set from OpenDataPhilly regarding street parking violations, e.g. parking in a no-stopping zone, not paying a meter, parking for too long, etc.

Each incident of a parking violation includes various pieces of data such as the date and time at which it occurred, the reason for the violation, and identifying information about the vehicle.

A parking violation also has an associated fine, i.e. the money that was charged as penalty to the vehicle, as well as information about the location at which it occurred. For our purposes, we are concerned with the violation’s ZIP Code, which is a numerical code used by the US Postal System to indicate a certain zone, e.g. a city or a neighborhood, as in the case of Philadelphia.

This program will also use a data set of property values of houses and other residences in Philadelphia. This data set includes details about each home including its ZIP Code and current market value, or estimated dollar value of the home, which is used by the city to calculate property taxes. It also includes the total livable area for the home, which measures the size of the home in square feet.

Information about the format of the input data files, the functional specification of the application, and the way in which should be designed follows below. 

## Public Data Files

### Parking Violations: Comma-Separated or JSON
The application is able to read parking violation data extracted from OpenDataPhilly extracted in either CSV or JSON format. The user will specify which input type is used.

For each file, the following data exists for the violations:
1. The timestamp of the parking violation, in YYYY-MM-DDThh:mm:ssZ format.
2. The fine assessed to the vehicle, in dollars.
3. The description of the violation.
4. An anonymous identifier for the vehicle; note that some vehicles have multiple violations.
5. The U.S. state of the vehicle’s license plate. For instance, “PA” = Pennsylvania, “NJ” = New Jersey, and so on.
6. A unique identifier for this violation.
7. The ZIP Code in which the violation occurred, if known.

### Property Values
The property values data set should be a CSV file. Each row of the CSV file represents data about one residence/home. The first row of the CSV file indicates the labels for the corresponding values in each row. For our purposes, we only care about the market_value, total_livable_area, and zip_code fields.

The application will determine what column these values exist in and only extract the appropriate fields from each row.

### Population Data
The population for each zip code is found in a whitespace-separated file in which each line contains the data for a single ZIP Code and contains the ZIP Code, then a single space, then the population.

## Functional Specifications
### Starting the Program
The runtime arguments to the program should be as follows, in this order:
- The format of the parking violations input file, either “csv” or “json”
- The name of the parking violations input file
- The name of the property values input file
- The name of the population input file
- The name of the log file (described below)
### Running the Program
If the number of runtime argumes correct, the input files exist and can be read, the program then prompts the user to specify the action to be performed:
- If the user enters the number 0, the program should exit.
- If the user enters the number 1, the program should show the total population for all ZIP Codes.
- If the user enters the number 2, the program should show the total parking fines per capita for each ZIP Code.
- If the user enters the number 3, the program should show the average market value for residences in a specified ZIP Code.
- If the user enters the number 4, the program should show the average total livable area for residences in a specified ZIP Code.
- If the user enters the number 5, the program should show the total residential market value per capita for a specified ZIP Code.
- If the user enters the number 6, the program should show a sorted list of zip codes and the
most common fine that occurred in that zip code. Values are sorted by the total livable area per
capita of that zip code.
### Logging
In addition to displaying the output as described above, the program also records the user inputs and activities by writing to the log file that was specified as a runtime argument to the program.

## Design Patterns Used
### Singleton Design Pattern
The singleton pattern is a creational design pattern that ensures that there is only one instance of a class, which can be easily accessed. The goal of the pattern is to ensure that all the code is using the same instance and allow code to use the single instance without having to create it.

The singleton pattern was used when creating the log file, this way only one log object would be created/referenced throughout the entire project.

### Factory Method Design Pattern
This is a creational pattern that uses inheritance to separate the code that uses another class from the code that creates it. E.g.:
- Class A has a dependency on Class B
- Subclasses of A override a factory method used to create an instance of the desired subclass of B

The factory method was used when reading the Parking Violations files which could be either JSON or CSV formats. This method allowed for the application to be able to read/process data from either file without caring about type.

### Strategy Design Pattern
This is a behavioral pattern in which we create classes to specify certain strategies that can be used as part of a larger algorithm. We use a class that represents the strategy and pass an instance to a single method that implements the rest of the algorithm.

This was used to assign similar methods for both the market value and livable area data sets from the input. Here, we were able to use a strategy of calculating average/per capita values and use them throughout the application.
