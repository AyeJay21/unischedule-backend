package com.example.unischedule.timetable;

import com.example.unischedule.payload.response.MessageResponse;
import com.example.unischedule.security.JwtUtils;
import com.example.unischedule.user.User;
import com.example.unischedule.user.UserDetailsImpl;
import com.example.unischedule.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/timetable")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserTimeTable(){
        System.out.println("HELLO TIMETABLE");
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        List<TimeTableEntry> entries = timeTableEntryRepository.findByUserId(userId);
        return ResponseEntity.ok(entries);
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

    @PostMapping("/timetable/clear")
    public ResponseEntity<?> clearTimeTable(HttpServletRequest request) {
        // JWT aus Cookie holen
        String token = jwtUtils.getJwtFromCookies(request);
        if (token == null || !jwtUtils.validateJwtToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Username aus Token extrahieren
        String username = jwtUtils.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findByUsername(username)
                .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Lösche alle Timetable-Einträge für diesen User
        timeTableEntryRepository.deleteAll(timeTableEntryRepository.findByUserId(user.getId()));
        return ResponseEntity.ok().build();
    }
}
