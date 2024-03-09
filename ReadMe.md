### Deployment
1. Deployed here: https://feedback-backend-4cq6.onrender.com/
2. Frontend deployed here: https://main--feedbackui-learningproject.netlify.app/
3. Frontend code: https://github.com/ekanshsaxena/Feedback-UI

### STEPS - for my setup only
1. Change the name of settings.xml located in the .m2 folder of root.
2. Now you can add any dependency in the pom.xml file.
3. Change Java version to 17, for which follow the below steps
4. `sdk list java`
5. `sdk use java 17.0.7-oracle`
6. `mvn clean install` to build the project.
7. ngrok http <url> 
8. @CrossOrigin annotation in the controller class, to allow the request from the frontend.