package com.xk.achievement.controller;

import com.xk.achievement.dto.AchievementDTO;
import com.xk.achievement.dto.TemplateDTO;
import com.xk.achievement.model.Achievement;
import com.xk.achievement.service.AchievementService;
import com.xk.achievement.service.TemplateServiceClient;
import com.xk.achievement.util.AchievementMapper;
import com.xk.achievement.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class AchievementController {

    private final AchievementService service;
    private final TemplateServiceClient templateServiceClient;

    @Autowired
    public AchievementController(AchievementService service, TemplateServiceClient templateServiceClient) {
        this.service = service;
        this.templateServiceClient = templateServiceClient;
    }

    @GetMapping
    public String listAchievements(@AuthenticationPrincipal UserDTO user, Model model) {
        UUID userId = UUID.fromString(user.id());
        List<AchievementDTO> dtos = service.findAllByUserId(userId).stream()
                .map(AchievementMapper::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("achievements", dtos);
        return "list";
    }

    @GetMapping("/templates")
    public String showTemplates(Model model) {
        model.addAttribute("templates", templateServiceClient.getAllTemplates());
        return "templates";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam(required = false) UUID templateId, Model model) {
        if (templateId == null) {
            return "redirect:/templates";
        }
        TemplateDTO template = templateServiceClient.getTemplateById(templateId);
        if (template == null) {
            return "redirect:/templates";
        }
        model.addAttribute("template", template);
        return "create";
    }

    @PostMapping("/save")
    public String saveAchievement(@AuthenticationPrincipal UserDTO user, @RequestParam Map<String, String> formData) {
        Achievement achievement = new Achievement();
        achievement.setUserId(UUID.fromString(user.id()));
        
        String templateName = formData.getOrDefault("_templateName", "Generated Achievement");
        String actualName = null;
        
        List<String> criteria = new ArrayList<>();
        LocalDate deadline = null;
        StringBuilder descBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (entry.getKey().startsWith("_")) continue;

            String keyLower = entry.getKey().toLowerCase();
            if (keyLower.contains("name") && actualName == null) {
                actualName = entry.getValue();
            } else if (keyLower.contains("deadline") || keyLower.contains("date")) {
                try {
                    // Typical format from datetime-local is 2026-05-10T10:00
                    if (entry.getValue().contains("T")) {
                        deadline = LocalDate.parse(entry.getValue().split("T")[0]);
                    } else {
                        deadline = LocalDate.parse(entry.getValue());
                    }
                } catch (Exception e) {
                    descBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
            } else if (keyLower.contains("criteria")) {
                String[] parts = entry.getValue().split("[,\\n]+");
                for (String p : parts) {
                    if (!p.trim().isEmpty()) {
                        criteria.add(p.trim());
                    }
                }
            } else {
                descBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }
        
        achievement.setAchievementName(actualName != null && !actualName.trim().isEmpty() ? actualName : templateName);
        achievement.setDescription(descBuilder.toString().trim());
        achievement.setListOfCriteria(criteria);
        achievement.setDeadline(deadline);
        
        service.save(achievement);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@AuthenticationPrincipal UserDTO user, @PathVariable Long id, Model model) {
        Optional<Achievement> achievementOpt = service.findById(id);
        if (achievementOpt.isPresent() && achievementOpt.get().getUserId().equals(UUID.fromString(user.id()))) {
            Achievement a = achievementOpt.get();
            AchievementDTO dto = AchievementMapper.toDTO(a);
            
            // Convert list to comma-separated string for editing
            model.addAttribute("achievement", dto);
            model.addAttribute("criteriaString", String.join(", ", a.getListOfCriteria()));
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateAchievement(@AuthenticationPrincipal UserDTO user, @PathVariable Long id, @ModelAttribute AchievementDTO achievementDetails, @RequestParam(value = "criteriaString", required = false) String criteriaString) {
        Optional<Achievement> optional = service.findById(id);
        if (optional.isPresent() && optional.get().getUserId().equals(UUID.fromString(user.id()))) {
            Achievement achievement = optional.get();
            achievement.setAchievementName(achievementDetails.getAchievementName());
            achievement.setDescription(achievementDetails.getDescription());
            achievement.setDeadline(achievementDetails.getDeadline());
            
            List<String> updatedCriteria = new ArrayList<>();
            if (criteriaString != null && !criteriaString.trim().isEmpty()) {
                String[] parts = criteriaString.split("[,\\n]+");
                for (String p : parts) {
                    if (!p.trim().isEmpty()) {
                        updatedCriteria.add(p.trim());
                    }
                }
            }
            achievement.setListOfCriteria(updatedCriteria);
            
            service.save(achievement);
        }
        return "redirect:/";
    }

    @PostMapping("/complete/{id}")
    public String completeAchievement(@AuthenticationPrincipal UserDTO user, @PathVariable Long id) {
        Optional<Achievement> optional = service.findById(id);
        if (optional.isPresent() && optional.get().getUserId().equals(UUID.fromString(user.id()))) {
            service.complete(id);
        }
        return "redirect:/";
    }
}
