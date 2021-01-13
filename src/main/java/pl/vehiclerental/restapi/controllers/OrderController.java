package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.CustomerDto;
import pl.vehiclerental.restapi.dtos.OrderDto;
import pl.vehiclerental.restapi.dtos.PaymentDto;
import pl.vehiclerental.restapi.dtos.RentalDto;
import pl.vehiclerental.restapi.models.*;
import pl.vehiclerental.restapi.payload.request.AddOrderRequest;
import pl.vehiclerental.restapi.repository.*;
import pl.vehiclerental.restapi.utilities.Converter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addOrder(@RequestBody AddOrderRequest addOrderRequest){
        List<RentalDto> rentalDtos = addOrderRequest.getRentals();

        Set<Rental> rentals = new HashSet<>();
        Rental rental;
        double fullCost = 0;

        for (RentalDto r :
                rentalDtos) {
            Vehicle vehicle = vehicleRepository.findById(r.getId()).get();

            rental = new Rental();
            rental.setVehicle(vehicle);
            rental.setCost(r.getResPrice());
            rental.setStartDate(r.getStartDate());
            rental.setEndDate(r.getEndDate());
            rentalRepository.save(rental);

            rentals.add(rental);
            fullCost += r.getResPrice();
        }

        Customer customer = customerRepository.findById(addOrderRequest.getCustomerId()).get();

        Order order = new Order();
        order.setCustomer(customer);
        order.setComments(addOrderRequest.getComments());
        order.setDate(LocalDate.now());
        order.setCost(fullCost);
        order.setRentals(rentals);
        order.setPaid(false);

        Payment payment = null;
        if (addOrderRequest.getPayment().equals("card")){
            order.setPaid(true);
            payment = new Payment();
            payment.setOrder(order);
            payment.setDate(LocalDate.now());
            payment.setMethod("card");
        }

        order.setPayment(payment);
        orderRepository.save(order);

        if (payment != null)
            paymentRepository.save(payment);

        return ResponseEntity.ok("Order added successfully");
    }

    @GetMapping("/unpaid")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR')")
    public ResponseEntity<?> getAllUnpaidOrders() throws JSONException {
        List<Order> orders = orderRepository.findAllByIsPaid(false);

        JSONArray jOrders = new JSONArray();
        JSONObject jOrder;
        for (Order o :
                orders) {
            jOrder = new JSONObject();
            jOrder.put("orderId", o.getId());
            jOrder.put("customerId", o.getCustomer().getId());
            jOrder.put("firstName", o.getCustomer().getUser().getPersonalInformation().getFirstName());
            jOrder.put("lastName", o.getCustomer().getUser().getPersonalInformation().getLastName());
            jOrder.put("comments", o.getComments());
            jOrder.put("cost", o.getCost());
            jOrder.put("date", o.getDate());
            jOrder.put("date", o.getDate());
            jOrders.put(jOrder);
        }
        return ResponseEntity.ok(jOrders.toString());
    }

    @PostMapping("/payment")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> addPayment(@RequestBody PaymentDto paymentDto){
        Payment payment = new Payment();
        payment.setDate(LocalDate.now());
        payment.setMethod("cash");
        Order order = orderRepository.findById(paymentDto.getOrderId()).get();
        order.setPaid(true);
        orderRepository.save(order);
        payment.setOrder(order);
        paymentRepository.save(payment);
        return ResponseEntity.ok("Payment added successfully");
    }

    @PostMapping("/customerOrders")
    @PreAuthorize("hasRole('REGULAR') or hasRole('USER')")
    public Set<OrderDto> getAllCustomerOrders(@RequestBody CustomerDto customerDto) throws JSONException{
        Customer customer = customerRepository.findById(customerDto.getId()).get();
        List<Order> orderList = orderRepository.findAllByCustomer(customer);
        Set<OrderDto> orderDtoList = new HashSet<>();

        for (Order o :
                orderList) {
            orderDtoList.add(Converter.convertOrderToOrderDto(o));
        }

        return orderDtoList;
    }
}
