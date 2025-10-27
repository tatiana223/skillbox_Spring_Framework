package com.example.contactlist.repository;

import com.example.contactlist.exception.ContactNotFoundException;
import com.example.contactlist.model.Contact;
import com.example.contactlist.repository.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
@Primary
public class JdbcContactRepository implements ContactRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("Calling JdbcContactRepository->findAll");

        String sql = "SELECT * FROM contacts_schema.contact ORDER BY id";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Calling JdbcContactRepository->findById with ID: {}", id);

        String sql = "SELECT * FROM contacts_schema.contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );

        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Calling JdbcContactRepository->save with contact: {}", contact);

        if (contact.getId() == null) {
            return insert(contact);
        } else {
            return update(contact);
        }
    }

    private Contact insert(Contact contact) {
        String sql = "INSERT INTO contacts_schema.contact (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";

        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getPhone());
            return ps;
        }, key);

        Long generatedId = key.getKey().longValue();
        contact.setId(generatedId);

        log.info("Successfully inserted contact with ID: {}", generatedId);

        return contact;
    }

    @Override
    public Contact update(Contact contact) {

        log.debug("Calling JdbcContactRepository->update with contact: {}", contact);
        Contact existedContact = findById(contact.getId()).orElse(null);

        if (existedContact != null) {
            String sql = "UPDATE contacts_schema.contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";

            jdbcTemplate.update(sql,
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getEmail(),
                    contact.getPhone(),
                    contact.getId()
            );
            log.info("Successfully updated contact with ID: {}", contact.getId());
            return contact;
        }
        log.warn("Contact with ID {} not found!", contact.getId());
        throw new ContactNotFoundException("Contact for update not found! ID: " + contact.getId());

    }

    @Override
    public void deleteId(Long id) {
        log.debug("Calling JdbcContactRepository->deleteById with ID: {}", id);

        String sql = "DELETE FROM contacts_schema.contact WHERE id = ?";
        int rowAffected = jdbcTemplate.update(sql, id);

        if (rowAffected == 0) {
            log.warn("No contact found with ID: {} for deletion", id);
            throw new ContactNotFoundException("Contact for deletion not found! ID: " + id);
        }
        log.info("Successfully deleted contact with ID: {}", id);

    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("Calling JdbcContactRepository->batchInsert with {} contacts", contacts.size());

        String sql = "INSERT INTO contacts_schema.contact (first_name, last_name, email, phone) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (email) DO NOTHING";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Contact contact = contacts.get(i);
                ps.setString(1, contact.getFirstName());
                ps.setString(2, contact.getLastName());
                ps.setString(3, contact.getEmail());
                ps.setString(4, contact.getPhone());
            }

            @Override
            public int getBatchSize() {
                return contacts.size();
            }
        });
        log.info("Successfully batch inserted {} contacts", contacts.size());
    }
}
