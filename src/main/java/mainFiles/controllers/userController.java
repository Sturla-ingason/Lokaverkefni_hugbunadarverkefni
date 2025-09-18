package mainFiles.controllers;

import org.springframework.stereotype.Controller;

import mainFiles.objects.User;

@Controller
public class userController {

    User user = new User();
    String userName = user.getUsername();


    public void teset(){
        System.out.println(userName);
    }

}
