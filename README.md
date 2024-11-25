## **Receipt Processor**

## **Docker Setup**

Navigate to the App Directory
Change your directory to the project folder where the Dockerfile is located:
1. Clone the repository
2. Navigate to the App Directory
  Change your directory to the project folder where the Dockerfile is located:
   `cd ReceiptProcessor`
4. Build the Docker Image
  Use the following command to build the Docker image:
  `docker build -t receipt-processor-app .`
5. Run the Docker Container
  Start the application using the built image:
  `docker run -p 8080:8080 receipt-processor-app`


## **Testing**

1. In postman or your favorite api testing tool
   Post request to localhost:8080/receipts/process include the body as provided in the example
   

Example Body:
```json
{
    "retailer": "Target",
    "purchaseDate": "2022-01-01",
    "purchaseTime": "13:01",
    "items": [
      {
        "shortDescription": "Mountain Dew 12PK",
        "price": "6.49"
      },{
        "shortDescription": "Emils Cheese Pizza",
        "price": "12.25"
      },{
        "shortDescription": "Knorr Creamy Chicken",
        "price": "1.26"
      },{
        "shortDescription": "Doritos Nacho Cheese",
        "price": "3.35"
      },{
        "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
        "price": "12.00"
      }
    ],
    "total": "35.35"
}
```

  Output : 
  ```json
    {
      "id": "a73f8763-63d7-47fc-a0d4-a7c1edfa9b31"
    }
  ```

2. Using the uuid generated
  Send a Get request to localhost:8080/receipts/{id}/points
  example : localhost:8080/receipts/5bea98d4-ae4e-4930-b69f-5991d98cec64/points

   Output : 
  ```json
    {
      "points": 28
    }
  ```
