package com.demo.ts.repository;

import com.demo.ts.resources.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Account Repository to execute DB operation
 * @author chitesh
 */
@Repository
public interface AccountRepository extends CrudRepository<Account,Integer>{

    /**
     * custom query to fetch the account by using account name.
     * @param name - name of the account
     * @return - Account resource
     */
    @Query("SELECT acc FROM Account acc where acc.name = :name")
    public Account findAccountByName(@Param("name") String name);

}
