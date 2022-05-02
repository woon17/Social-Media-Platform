package com.dxc.smp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dxc.smp.entity.Post;
import com.dxc.smp.entity.User;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	public void deleteById(int id);
	public Post findById(int id);
	public List<Post> findAll();
	public List<Post> findByUser(User user);
}
