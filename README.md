# Saga_Pattern

1)Build individual services by below command.

mvn clean install -DskipTests

2) Start the services

3)Open swagger for Order Service 

4) Put the below request and see it in console . All command will be worked.

Negative Case:

1)Stop : Shipping Service 
Ht the request
See the console or debug. U can see the CancelOrderCommand ,CancelInvoiceCommnads gets called.

2)Stop : Payment Service
Ht the request
See the console or debug. U can see the CancelOrderCommand gets called.




