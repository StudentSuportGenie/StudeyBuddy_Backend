package com.example.studyBuddy.Controler;

import com.example.studyBuddy.Config.CustomFilter.TokenDecodeServices;
import com.example.studyBuddy.DTO.KnowledgeableDTO;
import com.example.studyBuddy.Services.KnowdgleItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/V1/")
public class KnowledgeItemController {

    @Autowired
    private TokenDecodeServices tokenDecodeServices;

    @Autowired
    private KnowdgleItemServices knowdgleItemServices;

    @PostMapping("addItems")
    public ResponseEntity<?> addKnowledgeItems(@RequestBody KnowledgeableDTO knowledgeableDTO, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        try{
            String Role = tokenDecodeServices.getJobTitle(token);

            if(Role.equals("Admin")) {
                KnowledgeableDTO addKnowledgeItems = knowdgleItemServices.addKnowdgleItem(knowledgeableDTO);
                return ResponseEntity.ok(addKnowledgeItems);
            }
            return ResponseEntity.status(403).body("Unauthorized");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("getItems")
    public ResponseEntity<?> getKnowledgeItems() {
        try{
            List<KnowledgeableDTO> semimetallic = knowdgleItemServices.getAllKnowdgleItems();
            return ResponseEntity.ok(semimetallic);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("deleteItems")
    public ResponseEntity<?> deleteKnowledgeItems(@RequestHeader("Authorization") String authHeader, @RequestParam Integer knowledgeItemID) {
        String token = authHeader.replace("Bearer ", "").trim();
        try{
            String Role = tokenDecodeServices.getJobTitle(token);
            if(Role.equals("Admin")) {
                KnowledgeableDTO deleteItems = knowdgleItemServices.deleteKnowdgleItem(knowledgeItemID);
                return ResponseEntity.ok(deleteItems);
            }
            return ResponseEntity.status(403).body("Unauthorized");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("updateItems")
    public ResponseEntity<?> updateKnowledgeItems(@RequestBody KnowledgeableDTO knowledgeableDTO, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        try{
            String Role = tokenDecodeServices.getJobTitle(token);
            if(Role.equals("Admin")) {
               KnowledgeableDTO updateItem =  knowdgleItemServices.updateKnowdgleItem(knowledgeableDTO);
               return ResponseEntity.ok(updateItem);
            }
            return ResponseEntity.status(403).body("Unauthorized");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
