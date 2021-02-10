# aws-lambda-java-api-1

Code challenge to myself to refresh my memory on AWS Lambda, API Gateway

It generates a JSON doc with random Google Maps URLs at different zoom levels so you can use the URLs like a geo-guessing game.

## Usage

You must set up the Lambda and API Gateway stuff on AWS. I have some screenshots in the repo for reference. Exercise left to the reader to write serverless IaaC to generate that stuff automatically. ;-)

But once that's in place, it's a simple matter of going to the invokeUrl/mapgame to get results. Reload for new random coordinates.
