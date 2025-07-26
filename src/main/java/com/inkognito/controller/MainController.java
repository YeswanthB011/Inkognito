package com.inkognito.controller;

import com.inkognito.model.DiaryEntry;
import com.inkognito.model.User;
import com.inkognito.service.DiaryEntryService;
import com.inkognito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DiaryEntryService diaryEntryService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            session.setAttribute("user", userOpt.get());
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found. Please register first.");
            return "redirect:/";
        }
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String username, 
                          @RequestParam String professionalJob,
                          @RequestParam String hobbyJob,
                          HttpSession session, 
                          RedirectAttributes redirectAttributes) {
        try {
            User user = userService.createUser(username, professionalJob, hobbyJob);
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        // Refresh user data from database
        user = userService.findByUsername(user.getUsername()).orElse(user);
        session.setAttribute("user", user);
        
        model.addAttribute("user", user);
        model.addAttribute("canUpdateFeeling", userService.canUpdateFeeling(user));
        
        // Get recent entries for dashboard
        List<DiaryEntry> recentEntries = diaryEntryService.findRecentEntries(user, 7);
        model.addAttribute("recentEntries", recentEntries);
        
        // Get category counts
        for (DiaryEntry.Category category : DiaryEntry.Category.values()) {
            long count = diaryEntryService.countByUserAndCategory(user, category);
            model.addAttribute(category.name().toLowerCase() + "Count", count);
        }
        
        return "dashboard";
    }
    
    @PostMapping("/update-feeling")
    public String updateFeeling(@RequestParam String dailyFeeling,
                               @RequestParam String lifeSituation,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        if (!userService.canUpdateFeeling(user)) {
            redirectAttributes.addFlashAttribute("error", "You can only update your feeling once per day.");
            return "redirect:/dashboard";
        }
        
        try {
            user = userService.updateDailyFeeling(user.getUsername(), dailyFeeling, lifeSituation);
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("success", "Daily feeling updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/dashboard";
    }
    
    @GetMapping("/category/{categoryName}")
    public String categoryPage(@PathVariable String categoryName, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        try {
            DiaryEntry.Category category = DiaryEntry.Category.valueOf(categoryName.toUpperCase());
            List<DiaryEntry> entries = diaryEntryService.findByUserAndCategory(user, category);
            
            model.addAttribute("user", user);
            model.addAttribute("category", category);
            model.addAttribute("entries", entries);
            model.addAttribute("categoryName", categoryName);
            
            return "category";
        } catch (IllegalArgumentException e) {
            return "redirect:/dashboard";
        }
    }
    
    @PostMapping("/category/{categoryName}/add")
    public String addEntry(@PathVariable String categoryName,
                          @RequestParam String title,
                          @RequestParam String content,
                          @RequestParam(required = false) Integer rating,
                          @RequestParam(required = false) Integer durationMinutes,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        try {
            DiaryEntry.Category category = DiaryEntry.Category.valueOf(categoryName.toUpperCase());
            diaryEntryService.createEntry(user, category, title, content, rating, durationMinutes);
            redirectAttributes.addFlashAttribute("success", "Entry added successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid category");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding entry: " + e.getMessage());
        }
        
        return "redirect:/category/" + categoryName;
    }
    
    @PostMapping("/entry/{id}/delete")
    public String deleteEntry(@PathVariable Long id, 
                             @RequestParam String categoryName,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        try {
            Optional<DiaryEntry> entryOpt = diaryEntryService.findById(id);
            if (entryOpt.isPresent() && entryOpt.get().getUser().getId().equals(user.getId())) {
                diaryEntryService.deleteEntry(id);
                redirectAttributes.addFlashAttribute("success", "Entry deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Entry not found or access denied");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting entry: " + e.getMessage());
        }
        
        return "redirect:/category/" + categoryName;
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}