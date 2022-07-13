package ru.pavlov.practiceREST.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.pavlov.practiceREST.dto.CustomerDTO;
import ru.pavlov.practiceREST.models.Customer;
import ru.pavlov.practiceREST.services.CustomersService;
import ru.pavlov.practiceREST.util.CustomerErrorResponce;
import ru.pavlov.practiceREST.util.CustomerNotCreatedExeption;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomersService customersService;
    private final ModelMapper modelMapper;



    @Autowired
    public CustomerController(CustomersService customersService, ModelMapper modelMapper) {
        this.customersService = customersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<CustomerDTO> getCustomers(){
        return customersService.findAll().stream().map(this::convertToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") int id) {
        return convertToCustomerDTO(customersService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CustomerDTO customerDTO,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMsg.append(error.getField()).append(" -- ").append(error.getDefaultMessage()).append(";");

            }
            throw  new CustomerNotCreatedExeption(errorMsg.toString());

        }

        customersService.save(convertToCustomer(customerDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Customer> update(@RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customersService.update(convertToCustomer(customerDTO)), HttpStatus.CREATED);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> delete(@PathVariable("id") int id){
        boolean isRemoved = customersService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<CustomerErrorResponce> handleException(CustomerNotCreatedExeption e){
        CustomerErrorResponce responce = new CustomerErrorResponce(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
    }

    private Customer convertToCustomer(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO, Customer.class);
    }

    private CustomerDTO convertToCustomerDTO(Customer customer){
        return modelMapper.map(customer, CustomerDTO.class);
    }

/*    @RequestMapping("/getAllUsers")
    public ResponseEntity<Object> getAllUsers(){
        return customersService.getAllUsers();
    }*/
}
