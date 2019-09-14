package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Ho");
        customer1.setLastName("Hi");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setLastName("Na");
        customer2.setFirstName("Ne");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // when
        List<CustomerDTO> customerDtos = customerService.getAllCustomers();

        assertEquals(2, customerDtos.size());
    }

    @Test
    public void getCustomerById() {

        // given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

        // when
        CustomerDTO customerDto = customerService.getCustomerById(1L);

        assertEquals("Michale", customerDto.getFirstName());
    }

    @Test
    public void createNewCustomer() {

        // given
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDto.getFirstName());
        savedCustomer.setLastName(customerDto.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDto);

        // then
        assertEquals(customerDto.getFirstName(), savedDto.getFirstName());
        assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDto() throws Exception {

        // given
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDto.getFirstName());
        savedCustomer.setLastName(customerDto.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDto = customerService.saveCustomerByDto(1L, customerDto);

        // then
        assertEquals(customerDto.getFirstName(), savedDto.getFirstName());
        assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById() throws Exception {

        Long id = 1L;

        customerRepository.deleteById(id);

        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}