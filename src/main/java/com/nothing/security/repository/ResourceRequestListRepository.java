package com.nothing.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nothing.security.db.ResourceRequestList;

@Repository
public interface ResourceRequestListRepository extends JpaRepository<ResourceRequestList,Long>
{

}
