package com.newproject.news.controllers;


import com.newproject.news.config.Utility;
import com.newproject.news.entity.User;
import com.newproject.news.services.UserServise;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPassController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserServise userService;

    @GetMapping("forgot_password")
    public String showForgotPasswordForm() {
         return "forgot-pass";
    }

    @PostMapping("forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "Мы отправили форму восстановления Вам на почту.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Ошибка при отправке почты");
        }
        return "forgot-pass";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("info-ksnknews@ukr.net", "Ksnk-News Support");
        helper.setTo(recipientEmail);
        String subject = "Восстановление пароля на сайте Ksnk-News";
        String content = "<p>Добрый день,</p>"
                + "<p>Вы сделали запрос на генерацию нового пароля.</p>"
                + "<p>Нажмите на ссылку для восстановления пароля:</p>"
                + "<p><a href=\"" + link + "\">Изменить пароль</a></p>"
                + "<br>"
                + "<p>Игнорируйте это сообщение в случае если "
                + "Вы не создавали этот запрос.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @GetMapping("reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (user == null) {
            model.addAttribute("message", "Ошибка токена");
            return "message";
        }
        return "reset_password";
    }


    @PostMapping("reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Сбросьте пароль");
        if (user == null) {
            model.addAttribute("message", "Ошибка токена");
            return "message";
        } else {
           userService.updatePassword(user, password);

            model.addAttribute("message", "Вы успешно обновили Ваш пароль.");
        }
        return "message";
    }
}

