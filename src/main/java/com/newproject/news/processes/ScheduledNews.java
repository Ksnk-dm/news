package com.newproject.news.processes;


import com.newproject.news.entity.News;
import com.newproject.news.entity.User;
import com.newproject.news.services.NewsService;
import com.newproject.news.services.UserServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Component
public class ScheduledNews {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserServise userServise;
    @Autowired
    private  NewsService newsService;
    @Value("${site.indexSetting.mainCategory}")
    private String mainCategory;


    @Scheduled(fixedRate = 60000)
    public void sendNewsToEmail() throws UnsupportedEncodingException, MessagingException {
        List<User> users = userServise.allUsers();
        for (User email : users) {
            if (email.getSendEmail().equals(true)) {
                sendEmail(email.getEmail());
            }
        }

    }

    public void sendEmail(String email) throws UnsupportedEncodingException, MessagingException {
        List<News> news =newsService.findByCategory(mainCategory);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("info-ksnknews@ukr.net", "Ksnk-News новостная рассылка");
        helper.setTo(email);
        String subject = "Рассылка новостей сайта Ksnk-News";
        String content = "<p>Добрый день,</p>"
                + "<p>Вы Подписаны на россылку новостей</p>"
                + "<p>Последння новости с категории "+mainCategory+":</p>"
                +"<p>"+news.get(news.size()-1).getAnonce()+"</p>"
                + "<p>"+news.get(news.size()-1).getFullText()+"</p>"
                + "<br>"
                + "<p>Отключить подписку Вы сможете в личном кабинете на сайте</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
