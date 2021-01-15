package pl.vehiclerental.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.CommentDto;
import pl.vehiclerental.restapi.dtos.VehicleDto;
import pl.vehiclerental.restapi.models.Comment;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.models.Vehicle;
import pl.vehiclerental.restapi.repository.CommentRepository;
import pl.vehiclerental.restapi.repository.CustomerRepository;
import pl.vehiclerental.restapi.repository.UserRepository;
import pl.vehiclerental.restapi.repository.VehicleRepository;
import pl.vehiclerental.restapi.utilities.Converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/all")
    public List<CommentDto> getAllComments(@RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId()).get();

        List<Comment> comments = commentRepository.findAllByVehicleOrderByDateDesc(vehicle);

        return comments.stream()
                .map(Converter::convertCommentToCommentDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('REGULAR') or hasRole('USER')")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto){
        Vehicle vehicle = vehicleRepository.findById(commentDto.getVehicleId()).get();
        User user = userRepository.findByUsername(commentDto.getCustomerUsername()).get();
        Comment comment = new Comment();
        comment.setMessage(commentDto.getMessage());

        if (user.getCustomer() != null)
            comment.setCustomer(user.getCustomer());
        else
            comment.setCustomer(null);
        comment.setVehicle(vehicle);
        comment.setDate(LocalDateTime.now());
        commentRepository.save(comment);

        return ResponseEntity.ok("Comment added successfully");
    }
}
