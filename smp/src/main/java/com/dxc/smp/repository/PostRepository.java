package com.dxc.smp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dxc.smp.entity.Post;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	public void deleteById(int id);
	public Post findById(int id);
	public List<Post> findByUserName(String userName);
}
