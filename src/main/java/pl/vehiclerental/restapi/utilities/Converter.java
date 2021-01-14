package pl.vehiclerental.restapi.utilities;

import org.modelmapper.ModelMapper;
import pl.vehiclerental.restapi.dtos.*;
import pl.vehiclerental.restapi.models.*;
import pl.vehiclerental.restapi.repository.CategoryRepository;
import pl.vehiclerental.restapi.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

public class Converter {

    public static Set<Role> stringsToRoles(RoleRepository roleRepository, Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;

                    case "employee":
                        Role regularRole = roleRepository.findByName(ERole.ROLE_REGULAR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(regularRole);

                        break;
                    default:
                        throw new RuntimeException("Error: Role is not found.");
                }
            });
        }
        return roles;
    }

    public static Category stringsToCategory(CategoryRepository categoryRepository, String strCategory) {
        Category category;
        switch (strCategory) {
            case "SEDAN":
                category = categoryRepository.findByName(ECategory.SEDAN)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                break;
            case "COUPE":
                category = categoryRepository.findByName(ECategory.COUPE)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                break;
            case "SPORTS":
                category = categoryRepository.findByName(ECategory.SPORTS)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                break;
            case "HATCHBACK":
                category = categoryRepository.findByName(ECategory.HATCHBACK)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                break;
            case "SUV":
                category = categoryRepository.findByName(ECategory.SUV)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                break;
            default:
                throw new RuntimeException("Error: Category is not found.");
        }
        return category;
    }

    public static CommentDto convertCommentToCommentDto(Comment comment) {
        CommentDto commentDto = new ModelMapper().map(comment, CommentDto.class);
        commentDto.setVehicleId(comment.getVehicle().getId());

        if (comment.getCustomer() != null)
            commentDto.setCustomerUsername(comment.getCustomer().getUser().getUsername());
        else
            commentDto.setCustomerUsername("Customer service");

        return commentDto;
    }

    public static CustomerDto convertCustomerToCustomerDto(Customer customer) {
        CustomerDto customerDto = new ModelMapper().map(customer, CustomerDto.class);
        customerDto.setUserId(customer.getUser().getId());
        if (customer.getVerified())
            customerDto.setStatus("verified");
        else
            customerDto.setStatus("unverified");
        return customerDto;
    }

    public static FAQDto convertFAQToFAQDto(FAQ faq) {
        FAQDto faqDto = new ModelMapper().map(faq, FAQDto.class);
        if (faq.getEmployee() == null) {
            faqDto.setEmployeeId(null);
        } else {
            faqDto.setEmployeeId(faq.getEmployee().getId());
        }
        return faqDto;
    }

    public static EmployeeDto convertEmployeeToEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new ModelMapper().map(employee, EmployeeDto.class);
        employeeDto.setUserId(employee.getUser().getId());
        employeeDto.setJobTitle(employee.getJob().getTitle());
        Employee boss = employee.getBoss();
        if (boss != null) {
            employeeDto.setBossId(boss.getId());
        } else {
            employeeDto.setBossId(null);
        }
        return employeeDto;
    }

    public static OrderDto convertOrderToOrderDto(Order order) {
        OrderDto orderDto = new ModelMapper().map(order, OrderDto.class);
        orderDto.setCustomerId(order
                .getCustomer().getId());

        if (order.getPayment() != null)
            orderDto.setPayment(Converter.convertPaymentToPaymentDto(order.getPayment()));
        else
            orderDto.setPayment(null);

        Set<Rental> rentalSet = order.getRentals();
        Set<RentalDto> rentalDtos = new HashSet<>();

        for (Rental r :
                rentalSet) {
            rentalDtos.add(Converter.convertRentalToRentalDto(r));
        }
        orderDto.setRentals(rentalDtos);
        return orderDto;
    }

    public static PersonalInformationDto convertPersonalInformationToPersonalInformationDto(PersonalInformation personalInformation) {
        PersonalInformationDto personalInformationDto = new ModelMapper().map(personalInformation, PersonalInformationDto.class);
        personalInformationDto.setUserId(personalInformation.getUser().getId());
        return personalInformationDto;
    }

    public static PaymentDto convertPaymentToPaymentDto(Payment payment) {
        PaymentDto paymentDto = new ModelMapper().map(payment, PaymentDto.class);
        paymentDto.setOrderId(payment.getOrder().getId());
        return paymentDto;
    }

    public static RentalDto convertRentalToRentalDto(Rental rental) {
        RentalDto rentalDto = new ModelMapper().map(rental, RentalDto.class);

        if (rental.getComplaint() != null)
            rentalDto.setComplaintId(rental.getComplaint().getId());
        else
            rentalDto.setComplaintId(null);

        rentalDto.setVehicleId(rental.getVehicle().getId());
        rentalDto.setBrand(rental.getVehicle().getBrand());
        rentalDto.setModel(rental.getVehicle().getModel());
        rentalDto.setResPrice(rental.getCost());

        return rentalDto;
    }

    public static VehicleDto convertVehicleToVehicleDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new ModelMapper().map(vehicle, VehicleDto.class);
        vehicleDto.setCategory(vehicle.getCategory().getName().name());
        return vehicleDto;
    }
}
