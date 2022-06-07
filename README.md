# sqji-hunter


## Getting Started
Clone this project and open project directory from your IDE (Intellij idea)
If you want to run from the terminal, then do:
```
mvn cleam package
```
```
mvn compile 
```
```
mvn package tomcat9:run-war
```
After you can open [localhost:8080](http://localhost:8080/hello), you will see:
![image](https://user-images.githubusercontent.com/41242817/172462760-066cacdc-f7a3-4c61-8581-c20cf5cde01f.png)

To check the entered request, copy it and enter it into the address by assigning the username parameter
![image](https://user-images.githubusercontent.com/41242817/172463338-e7f471f4-f2e4-4f1f-9ab2-3c1e4d3333bf.png)

If the query contains sql injection, then the server will not skip it and switch you to another site:
![image](https://user-images.githubusercontent.com/41242817/172463546-4b3e23c2-fb16-410d-b2c7-260269ce125d.png)

If everything is fine with the request, then it will be output as a name on the http server (without anonymous, as it was at the beginning):
![image](https://user-images.githubusercontent.com/41242817/172463960-4059952f-73a7-473c-8a0a-b66096532cfb.png)

Requests are taken from a file Queries20.txt . if desired, it can be supplemented and changed
The request parameter can be changed in the return of the choose function (in this case from 0 to 33, since requests are taken from the input file and processed into a string)

If you want stop the service just execute following command:
```
mvn tomcat9:shutdown
```
or just click Ctrl+C
