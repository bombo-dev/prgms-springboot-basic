package com.prgms.VoucherApp.domain.customer.storage;

import com.prgms.VoucherApp.domain.customer.Customer;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerStorage implements CustomerStorage {

    private final static Logger logger = LoggerFactory.getLogger(JdbcCustomerStorage.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCustomerStorage(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customer VALUES (:id, :status)";
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", customer.getCustomerId().toString())
                .addValue("status", customer.getCustomerStatus().getStatus());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        List<Customer> customers = namedParameterJdbcTemplate.query(sql, customerRowMapper());
        return customers;
    }


    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "SELECT * FROM customer WHERE id = :id";
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", id.toString());
        try {
            Customer customer = namedParameterJdbcTemplate.queryForObject(sql, paramMap, customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.info("{}로 조회된 결과가 존재하지 않습니다. ", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByCustomerStatus(CustomerStatus customerStatus) {
        String sql = "SELECT * FROM customer WHERE status = :status";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("status", customerStatus.getStatus());
        List<Customer> customers = namedParameterJdbcTemplate.query(sql, paramMap, customerRowMapper());
        return customers;
    }

    @Override
    public void updateStatus(UUID customerId, CustomerStatus status) {
        String sql = "UPDATE customer SET status = :status WHERE id = :id";
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("status", status.getStatus())
                .addValue("id", customerId.toString());

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM customer WHERE id = :id";
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", id.toString());

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    private RowMapper<Customer> customerRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            String status = resultSet.getString("status");
            CustomerStatus customerStatus = CustomerStatus.findByStatus(status);
            return new Customer(UUID.fromString(id), customerStatus);
        };
    }
}
