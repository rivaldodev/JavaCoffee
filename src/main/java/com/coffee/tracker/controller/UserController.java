package com.coffee.tracker.controller;

import com.coffee.tracker.dto.*;
import com.coffee.tracker.dto.UserUpdateRequest;
import com.coffee.tracker.exception.UnauthorizedOperationException;
import com.coffee.tracker.model.User;
import com.coffee.tracker.repository.UserRepository;
import com.coffee.tracker.service.CoffeeLogService;
import com.coffee.tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate; import java.time.LocalDateTime; import java.time.LocalTime; import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService; private final CoffeeLogService coffeeLogService; private final UserRepository userRepository;
    public UserController(UserService userService, CoffeeLogService coffeeLogService, UserRepository userRepository){ this.userService=userService; this.coffeeLogService=coffeeLogService; this.userRepository=userRepository; }
    private Long authId(Principal p){ return Long.valueOf(p.getName()); }
    @GetMapping({"","/"}) public Page<UserResponse> list(@RequestParam(defaultValue="0") int page,
                                              @RequestParam(name="size", defaultValue="20") int size,
                                              @RequestParam(name="limit", required=false) Integer limit){
        int pageSize = (limit!=null && limit>0)? limit : size;
        if(pageSize<=0 || pageSize>200) pageSize = 20; // simples saneamento
        Pageable pageable= PageRequest.of(page,pageSize);
        return userService.list(pageable);
    }
    @GetMapping("/{iduser}") public UserResponse get(@PathVariable("iduser") Long iduser){ return userService.getById(iduser); }
    @PutMapping("/{iduser}") public ResponseEntity<?> update(@PathVariable("iduser") Long iduser, @Valid @RequestBody UserUpdateRequest request, Principal principal){ if(!authId(principal).equals(iduser)) throw new UnauthorizedOperationException("Só pode editar o próprio usuário"); userService.update(iduser, request); return ResponseEntity.ok().build(); }
    @DeleteMapping("/{iduser}") public ResponseEntity<?> delete(@PathVariable("iduser") Long iduser, Principal principal){ if(!authId(principal).equals(iduser)) throw new UnauthorizedOperationException("Só pode deletar o próprio usuário"); userService.delete(iduser); return ResponseEntity.noContent().build(); }
    @PostMapping("/{iduser}/drink") public ResponseEntity<?> drink(@PathVariable("iduser") Long iduser, @Valid @RequestBody CoffeeDrinkRequest request, Principal principal){ if(!authId(principal).equals(iduser)) throw new UnauthorizedOperationException("Só pode registrar o próprio consumo"); User user=userService.getEntity(iduser); coffeeLogService.add(user, request.getDrink()); return ResponseEntity.ok().build(); }
    @GetMapping("/{iduser}/history") public List<CoffeeLogResponse> history(@PathVariable("iduser") Long iduser, @RequestParam(name="date", required=false) String date, Principal principal){ if(!authId(principal).equals(iduser)) throw new UnauthorizedOperationException("Só pode consultar o próprio histórico"); User user=userService.getEntity(iduser); if(date==null || date.isBlank()){ return coffeeLogService.fullHistory(user); } LocalDate d= LocalDate.parse(date); return coffeeLogService.dayHistory(user,d); }
    @GetMapping("/ranking/day") public List<UserRankingResponse> rankingDay(@RequestParam String date){ LocalDate d= LocalDate.parse(date); LocalDateTime start=d.atStartOfDay(); LocalDateTime end=d.atTime(LocalTime.MAX); return userRepository.findRankingBetween(start,end); }
    @GetMapping("/ranking/last") public List<UserRankingResponse> rankingLast(@RequestParam int days){ LocalDateTime end= LocalDateTime.now(); LocalDateTime start=end.minusDays(days); return userRepository.findRankingBetween(start,end); }
}
