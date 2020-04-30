package com.zmeev.oauth2Demo.controllers;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.zmeev.oauth2Demo.entities.Role;
import com.zmeev.oauth2Demo.entities.ShoppingCart;
import com.zmeev.oauth2Demo.entities.User;
import com.zmeev.oauth2Demo.services.EmailService;
import com.zmeev.oauth2Demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegistrationController {

    private PasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public RegistrationController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, EmailService emailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {

        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView addUser(ModelAndView modelAndView,
                          @Valid User user,
                          BindingResult bindingResult,
                          HttpServletRequest request) {

        // Lookup  user in database by email
        User userExists = userService.findByEmail(user.getEmail());
        System.out.println(userExists);

        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage",
                    "Oops!  There is already a user registered with the email provided.");
            modelAndView.setViewName("registration");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            // we create user and send confirmation email

            // disable user until click on confirmation link
            user.setActive(false);

            // generate random 36-character string token for confirmation link
            user.setConfirmationToken(UUID.randomUUID().toString());

            // set user role
            user.setRoles(Collections.singleton(Role.USER));

            userService.save(user);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Registration Confirmation");
            registrationEmail.setText("To confirm your email address, please click on link below\n" +
                    appUrl + "/confirm?token=" + user.getConfirmationToken());
            registrationEmail.setFrom("dimaty26rus@mail.ru");

            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("confirmationMessage","A confirmation e-mail has been sent to " + user.getEmail());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    //Process confirmation link
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView,
                                             @RequestParam("token") String token) {

        User user = userService.findByConfirmationToken(token);

         if (user == null) {
             modelAndView.addObject("invalidToken",
                     "Oops! This is an invalid confirmation link.");
         } else {
             modelAndView.addObject("confirmationToken", user.getConfirmationToken());
         }

         modelAndView.setViewName("confirm");

         return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView,
                                                BindingResult bindingResult,
                                                @RequestParam Map requestParams,
                                                RedirectAttributes redir) {

        modelAndView.setViewName("confirm");

        Zxcvbn passwordCheck = new Zxcvbn();

        Strength strength = passwordCheck.measure(requestParams.get("password").toString());

        if (strength.getScore() < 3) {
            bindingResult.reject("password");

            redir.addFlashAttribute("errorMessage", "Password is too weak.");

            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));

            return modelAndView;
        }

        User user = userService.findByConfirmationToken(requestParams.get("token").toString());

        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password").toString()));

        user.setActive(true);

        // set user's shopping cart
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);

        userService.save(user);

        modelAndView.addObject("successMessage", "Your password has been set!");

        return modelAndView;
    }
}
