package mainFiles.controllers;

public class UserController {

    //Methods

    //TODO CreateNewUser() method
        //Should take in 
            //username
            //password
            //email
        //Should randomly make a id for the user.
        //needs to check in the data base if the random user id allready exists
        //if not we let the user have that id. If it does exist we generate a new one and check again.


    //TODO DeleateUser() method
        //should take in:
            //username
            //userID
        //Should check if user name and id exists in the database
        //if the username exists and is associated with the user id we remove
        //that from the database.

    //TODO UpdateUsername() method
        //should take in
            //username
            //newUsername
            //userID
        //Should check if the username and userID exist in the data base
        //if they do exist and are related we update the username in the data base
        //to be the newUsername that we take inn.


    //TODO UpdateEmail() method
        //Should take in
            //email
            //newEmail
            //userID
        //Should check if the email and userID exist in the data base
        //if they do and are related we update the email to the newEmail in the database.

    
        //TODO ResetPassword() method
            //should take in
                //email
                //newPassword
                //userID
            //Should check if the email is associated with a account in the database
            //if it is we send a email to the user to let them change the password of their account.

}
