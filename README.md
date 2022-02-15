# Merchant's guide to the galaxy

To build the project run the ```mvn clean package``` command (unit testing is performed at build time).

To run the project run 

```java -jar .target/MerchantsGuideToTheGalaxy-1.0-SNAPSHOT.jar <absolute path to input .txt file>``` or

```java -jar .target/MerchantsGuideToTheGalaxy-1.0-SNAPSHOT.jar``` and type the input manually.

-----------------------------------------------------------------------------------------------
## Assumptions

The program can process four different types of Notes:
- ### Definition of an intergalactic unit
  This Note type follows the pattern:  
  *INTERGALACTIC_UNIT* is *ROMAN_NUMERAL*  
  Intergalactic units and their mapping to roman numerals are case-insensitive.  
  Intergalactic units can only be mapped to a single roman numeral.  
  Reserved words (**credits**, **how**, **is**, **many**, **much**) can not be used as intergalactic units.
- ### Price for an item, for which the amount of the item is expressed in intergalactic units
  This Note type follows the pattern:  
  *INTERGALACTIC_UNITS* *ITEM* is *PRICE* Credits  
  Also, this Note type should contain at least one intergalactic unit that specifies the item quantity. 
- ### Query for converting intergalactic units to decimal notation
  This Note type follows the pattern:  
  how much is *INTERGALACTIC_UNITS* ?  
  If the intergalactic units do not conform to the convention for roman numerals the program will inform 
  the user that the input is not valid.
- ### Query for getting the total price for a given quantity(expressed in intergalactic units) of an item
  This Note type follows the pattern:  
  how many Credits is *INTERGALACTIC_UNITS* *ITEM* ?  
  If the intergalactic units do not conform to the convention for roman numerals the program will inform
  the user that the input is not valid.  
  If the price for the item is not previously defined the program will inform the user that there is no 
  information about the price for that item. 
