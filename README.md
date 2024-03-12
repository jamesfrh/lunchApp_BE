# LunchDecisionApp

## Screenshots of Front End Pages
__Screenshots of the front end pages can be found in src/assets/images of the Angular Repository for reference__

## Design Considerations and Assumptions Made
Some assumptions were made to facilitate the development of the application.
1. Assuming that the current data is non-sensitive/non restricted, no password is required for the login.
3. Each user can only initate one session. They cannot create multiple sessions.
4. When a session is initiated by a user, the backend sends an email notifcation to all users about the new session code. 
5. Users are not restricted to the number of submissions per session, they may submit as many as they want.
6. Users can join multiple sessions and make submissions in the session joined.

## Features Developed
1. A user can initiate a session and invite others to join it.
2. Other users who have joined the session may submit a restaurant of their choice.
3. All users in the session are able to see restaurants that others have submitted.
4. Only the user who initiated the session is able to end the session.
5. At the end of a session, a restaurant is randomly picked from all submitted restaurants. 
6. A user should not be able to join a session that has already ended.
7. A user may join and make submissions to multiple sessions.
8. A user may search for an active session code and view the current list of restaurants submitted.
9. A user may search for an closed session code and view the randomly selected restaurant.


## API Documentation
API documentation can be found on http://localhost:8080/swagger-ui/index.html after the Springboot App has started. 
Alternatively, the lunchApp-api-docs.json file is located in src/main/resources of the SpringBoot project folder and can be downloaded and imported into https://editor.swagger.io/ for viewing.

## Prerequisites
1. 3 User accounts have been created in the backend. Use these email address to login. You may create more accounts in UserConfiguration.java file in the Springboot app.
    1. james@tech.gov.sg
    2. bob@tech.gov.sg
    3. jane@tech.gov.sg
2. MySQL has been configured as the database in the Springboot application. In the Springboot application.properties, change the username and password to your own credentials if it is not the same.
   Create a schema called lunchapp in the database before starting the Springboot application.
3. Mailtrap is used to test the sending of emails by the Springboot application when a user session is initiated to notify all other users about the session code.
   Steps to login to Mailtrap:
   1. Login to https://mailtrap.io/ using the google account (email: lunchapp.springboot@gmail.com, password: lunchap2024)
   2. Once Logged in, navigate to Email Testing -> My Inbox. In My Inbox.
   You may view the emails that are being sent from the Springboot application when a user has created a new session, sending out the session code to all other users in the     database.
   


## Installation
### Frontend Setup for Angular version 16.2.7.
This Angular application was developed in Visual Studio Code, Angular version 16.2.7

Clone the project and change into the project directory : URL to clone: https://github.com/jamesfrh/lunchApp_FE.git
1. cd to \lunch_app\app-frontend
2. run "npm install"
4. run "ng serve" to start the front end application
5. On the browser, go to http://localhost:4200 to start using the application

### Backend Setup for SpringBoot
This Springboot application was developed in Spring Tool Suite IDE(STS), you may use eclispe and the process to setup is similar

Cloning the project in the IDE : URL to clone: https://github.com/jamesfrh/lunchApp_BE.git
1. In STS, go to the "File" menu and select "Import."
2. In the "Import" dialog, choose "Git" > "Projects from Git" and click "Next."
3.Select "Clone URI" and click "Next."
4. Enter the repository URI in the "URI" field. It should look like https://github.com/your-username/your-repository.git. Replace your-username and your-repository with your GitHub username and repository name.
5. Click "Next" and follow the wizard to complete the cloning process.

Once the cloning process is complete, follow these steps to import the project into STS:
1. In STS, go to the "File" menu and select "Import."
2. Choose "Maven" > "Existing Maven Projects" and click "Next."
3. Browse to the location where you cloned your GitHub repository and select the project folder.
4. Click "Finish" to import the Maven project.

After importing the project, you can run the Spring Boot application:
1.Locate the main class with the main method (usually annotated with @SpringBootApplication).
2.Right-click on the main class and select "Run As" > "Spring Boot App."

## Future releases that can be considered for automatic deployment and scalability
1. Dockerize the application by creating an image
2. Pushing the image to AWS ECR and then deploying on ECS fargate
3. Use RDS mysql
4. Create a pipeline on AWS which handles the automatic deployment of codes from codeCommit -> codebuild -> codedeploy -> ECR -> ECS
