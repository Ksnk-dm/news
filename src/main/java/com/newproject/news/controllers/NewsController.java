package com.newproject.news.controllers;

import com.newproject.news.entity.Category;
import com.newproject.news.entity.Comments;
import com.newproject.news.entity.News;
import com.newproject.news.services.CategoryService;
import com.newproject.news.services.CommentService;
import com.newproject.news.services.NewsService;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    CommentService commentService;

    public static String uploadDirectory = System.getProperty("user.dir");

    @PostMapping("/news/addnews")
    public String addNews(@RequestParam String title, @RequestParam String anonce, @RequestParam String img, @RequestParam String imgUrl, @RequestParam String fullText,
                          @RequestParam String video, @RequestParam(value = "category") long categoryId, Model model, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findCategory(categoryId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
        if (img.isEmpty()&&imgUrl.isEmpty()) {
            img = "/img/news/weekly2News4.jpg";
        }
            if (imgUrl.isEmpty()) {
                News news = new News(title, anonce, img, fullText, dateFormat.format(new Date()), video, category);
                newsService.addNews(news);
            } else {
                News news = new News(title, anonce, fullText, dateFormat.format(new Date()), video, category, imgUrl);
                newsService.addNews(news);
            }



        return "redirect:/";
    }

    @PostMapping("/news/upload")
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file,
                                              RedirectAttributes redirectAttributes, Model model) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("Выберите файл для загрузки", HttpStatus.BAD_REQUEST);
        }
        if (file != null && file.getContentType() != null && !file.getContentType().toLowerCase().startsWith("image"))
            return new ResponseEntity<>("файл не есть изображением", HttpStatus.BAD_REQUEST);

        try {
            String uuid = UUID.randomUUID().toString();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + "/uploads/", uuid + file.getOriginalFilename());
            Files.write(path, bytes);
            String filename = "/uploads/" + uuid + file.getOriginalFilename();
            return new ResponseEntity<>(filename, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @PostMapping("/news/del")
    public ResponseEntity<?> singleFileDel(String img2) {
        Path path = Paths.get(uploadDirectory + img2);
        try {
            Files.delete(path);
        } catch (IOException | InvalidPathException e) {
            return new ResponseEntity<>("bad", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/news/addnews")
    public String addNews(Model model) {
        model.addAttribute("category", categoryService.findCategory());
        model.addAttribute("dir", uploadDirectory);
        return "addnews";
    }

    @GetMapping("news/{id}")
    public String newsDetails(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        try {
            News newsPage = newsService.findNewsById(Long.parseLong(id));
            int page = 0;
            int size = 2;
            if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
                page = Integer.parseInt(request.getParameter("page")) - 1;
                if (page < 0) {
                    return "redirect:/";
                }
            }
            if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
                size = Integer.parseInt(request.getParameter("size"));
            }
            Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            model.addAttribute("commentsAll", commentService.findByNews(newsPage, sortedById));
            Long.parseLong(id);
            Optional<News> news = newsService.findNews(Long.parseLong(id));
            List<News> res = new ArrayList<>();
            news.ifPresent(res::add);
            model.addAttribute("news", res);
            model.addAttribute("category", categoryService.findCategory());
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
            model.addAttribute("date", dateFormat.format(new Date()));
            model.addAttribute("title", newsPage.getTitle());


        } catch (NoSuchElementException | NumberFormatException e) {
            return "redirect:/";
        }


        return "details";
    }


    @PostMapping("/news/{id}/del")
    public String delNews(@PathVariable(value = "id") Long id, Model model) {
        News news = newsService.findNewsById(id);
        singleFileDel(news.getImg());
        newsService.deleteNews(id);
        return "redirect:/";
    }


    @GetMapping("/news/{id}/edit")
    public String editNews(@PathVariable(value = "id") Long id, Model model) {
        Optional<News> news = newsService.findNews(id);
        ArrayList<News> res = new ArrayList<>();
        news.ifPresent(res::add);
        model.addAttribute("news", res);
        model.addAttribute("category", categoryService.findCategory());
        return "editnews";
    }

    @PostMapping("/news/{id}/edit")
    public String editNews(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String anonce,
                           @RequestParam String img, @RequestParam String fullText, @RequestParam String date,
                           @RequestParam(value = "category") long categoryId, Model model) {
        News news = newsService.findNewsById(id);
        news.setTitle(title);
        news.setAnonce(anonce);
        news.setFullText(fullText);
        news.setImg(img);
        Category category = categoryService.findCategory(categoryId);
        news.setCategory(category);
        newsService.updateNews(news);
        return "redirect:/";
    }
}
