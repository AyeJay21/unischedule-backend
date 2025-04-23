package com.example.unischedule.timetable;

import com.example.unischedule.payload.response.MessageResponse;
import com.example.unischedule.user.User;
import com.example.unischedule.user.UserDetailsImpl;
import com.example.unischedule.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class TimeTableEntryController {

    @Autowired
    private TimeTableEntryRepository timeTableEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/timetable")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserTimeTable(){
        System.out.println("HELLO TIMETABLE");
//        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long userId = userDetails.getId();

        //List<TimeTableEntry> entries = timeTableEntryRepository.findByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/timetable")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTimeTableEntry(@Valid @RequestBody TimeTableEntryRequest entryRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        TimeTableEntry entry = new TimeTableEntry(
                user,
                entryRequest.getDay(),
                entryRequest.getStartTime(),
                entryRequest.getEndTime(),
                entryRequest.getSubject(),
                entryRequest.getColor(),
                entryRequest.getColorType()
        );

        timeTableEntryRepository.save(entry);
        return ResponseEntity.ok(new MessageResponse("Entry added successfully!"));
    }

//
//    @DeleteMapping("/entry/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public ResponseEntity<?> deleteTimeTableEntry(@PathVariable Long id) {
//        // Hole den aktuellen Benutzer aus dem JWT-Token
//        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long userId = userDetails.getId();
//
//        // Prüfe, ob dieser Eintrag dem aktuellen Benutzer gehört
//        TimeTableEntry entry = timeTableEntryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Error: Entry not found."));
//
//        if (!entry.getUser().getId().equals(userId)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        timeTableEntryRepository.delete(entry);
//        return ResponseEntity.ok(new MessageResponse("Entry deleted successfully!"));
//    }
}
