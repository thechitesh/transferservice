package com.ingenico.ts.repository;

import com.ingenico.ts.resources.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account,Integer>{

    @Query("SELECT acc FROM Account acc where acc.name = :name")
    public Account findAccountByName(@Param("name") String name);

}
