# Background

This is the homework java implementation of every-matrix interview.

This project based on springboot 3.4.3 and enabled spring aot and using graalvm.

# Usage
1. Env preparation:
   1. install java 17
   2. configure java home
2. run with gradle:
   1. cd to the project root
   2. run "./gradlew bootRun"
3. access from browser or curl cmd: 
   1. http://localhost:8080/user1/session
   2. curl -X POST -H "Content-Type: text/plain" http://localhost:8080/123/stake?sessionKey=42838d4f-2f50-46bd-8e9c-65ce22520281 -d 5
   3. http://localhost:8080/offer1/highstakes
