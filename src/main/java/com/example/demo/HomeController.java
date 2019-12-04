package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CloudinaryConfig cloudc;

    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }

    @RequestMapping("/")
    public String listMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String courseForm(Model model) {
        model.addAttribute("message", new Message());
        return "messageform";
    }

    @PostMapping("/process")
    public String processForm(@ModelAttribute Message message, @RequestParam("file") MultipartFile file, @RequestParam("imageurl") String imageurl) {
        if (!file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                message.setImageurl(uploadResult.get("url").toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.isEmpty() && imageurl.equalsIgnoreCase("")) {
            message.setImageurl(null);
        }
        messageRepository.save(message);
        return "redirect:/";

    }

    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "messageform";
    }

    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/searchlist")
    public String search(Model model, @RequestParam("searchString") String search) {
        model.addAttribute("messages", messageRepository.findByContentContainingIgnoreCaseOrDateContainingIgnoreCaseOrSentbyContainingIgnoreCase(search, search, search));
        return "searchlist";
    }

    @RequestMapping("/secure")
    public String admin() {
        return "admin";
    }



}
