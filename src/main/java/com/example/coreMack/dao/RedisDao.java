package com.example.coreMack.dao;

import com.example.coreMack.model.TrackAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * Redis Repository
 *
 * Redis Repositories do not work with Redis transactions
 * as operations that change the underlying data are queued and executed at the end of the transaction
 */
public interface RedisDao extends CrudRepository<TrackAccount,String> {

}
