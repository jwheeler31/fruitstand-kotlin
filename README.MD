#### Comments to Reviewer
* The pub-sub implementation was heavily borrowed from "Head First Design Patterns" by Eric Freeman and Elizabeth Bates, 
  as well as an [example on stackabuse.com](https://stackabuse.com/the-observer-design-pattern-in-java/)
* The inventory aspect would have been much cleaner using a repository pattern, but I opted for a singleton in the 
  interest of time.
* In retrospect, an order builder would have been very useful.

##Coding Task for Software Engineer Candidates
The following task will allow you to demonstrate your ability to deliver readable,
maintainable and testable code. As an agile organization, we are applying equal
emphasis on assessing both your problem solving and testing abilities.
Be prepared to present your solution over conversation that will lead to
questions around design decisions, rationale and future expansion.
The exercise should take around 60-90 minutes; please don’t spend much
longer than this.
* Please complete the following task in Kotlin
* Use git to version control the code and once complete, send us a link to
the Github (or similar) repository via the recruitment agent

Instructions: _Complete the steps in order. Don’t read ahead._ At each step build
the simplest possible solution which meets the requirement. *Tag* and git *commit*
after each step so that your approach is clear.

## Step 1: Build an Orders Service
Build a service that’s able to receive simple orders of shopping goods from the
command line
* `Apple`s cost `60 cents` and `orange`s cost `25 cents`
* The service should be able to calculate that:
  
  ```[ Apple, Apple, Orange, Apple ] => $2.05```
  
* Make reasonable assumptions about the inputs to your solution; for example,
candidates may take a list of strings as input

Add unit tests that validate your code

## Step 2: Simple offer
The shop decides to introduce two new offers:
* buy one get one free on Apples
* 3 for the price of 2 on Oranges

Update your functions & unit tests accordingly

## Step 3: Build a Customer Notification Service
Customers complained that they don’t know if their orders made it through or not as there is no notification of success
* Build a service that listens for when orders are complete and sends a notification to
the customer regarding its status and estimated delivery time
* The Mail service subscribes to events from the Orders service and publishes an
appropriate event that the customer (you) is able to read from the terminal

## Step 4: Limited Stock
Stock can now run out, this means that customers need to be notified that their order
failed

## Optional Step 5: Events via Kafka (or another appropriate message queue, ie. Rabbit MQ)
When the customer submits an order, this data is published via Kafka