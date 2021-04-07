
Step 1 : 
1) Run Gradle
	gradlew clean build

Note : 
we can see the test data in resource folder.
1) For Day capping transaction : day.txt
2) For Weekly capping transaction : weekly.txt
3) For Both capping transaction : data.txt

Core classes :
FareStrategy.java is utility class to calculate Fare 
DailyCapPaymentService.java : this service calculate the fare of day capping
WeeklyCapPaymentService.java : this service calculate the fare of weekly capping

Main Class:
TigerCardApplication.java class is an entry point of application





