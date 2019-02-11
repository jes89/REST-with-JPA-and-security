package com.spring.blog.listener;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

import com.spring.blog.model.Contents;

public class ContentsListener {

	@PrePersist
	public void perPersist(Contents contents) {
		System.out.println("Contents.perPersist obj=" + contents);
	}

	@PostPersist
	public void postPersist(Contents contents) {
		System.out.println("Contents.postPersist obj=" + contents);
	}

}
