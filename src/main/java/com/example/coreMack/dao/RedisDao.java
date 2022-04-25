package com.example.coreMack.dao;

import com.example.coreMack.model.TrackAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisDao extends CrudRepository<TrackAccount,String> {

}
