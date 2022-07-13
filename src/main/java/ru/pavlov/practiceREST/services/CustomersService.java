package ru.pavlov.practiceREST.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavlov.practiceREST.dto.CustomerDTO;
import ru.pavlov.practiceREST.models.Customer;
import ru.pavlov.practiceREST.repositories.CustomersRepository;
import ru.pavlov.practiceREST.util.CustomerNotFoundExeption;


import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {
    private final CustomersRepository customersRepository;

    @Autowired
    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<Customer> findAll(){return customersRepository.findAll();}

    public Customer findOne(int id){
        Optional<Customer> foundCustomer = customersRepository.findById(id);
        return foundCustomer.orElseThrow(CustomerNotFoundExeption::new);
    }

    @Transactional
    public void save(Customer customer){
        customersRepository.save(customer);
    }

    @Transactional
    public Customer update(Customer customer){
       return customersRepository.save(customer);
    }

    @Transactional
    public boolean delete(int id){
        Optional<Customer> op = customersRepository.findById(id);
        if(op.isEmpty())
            return false;
        customersRepository.delete(op.get());
        return true;
    }




/*
    public ResponseEntity<Object> getAllUsers(){
        List<Customer> customers = customersRepository.findAll();
        if(customers.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
*/

}
