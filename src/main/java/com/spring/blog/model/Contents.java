package com.spring.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.spring.blog.listener.ContentsListener;

@Entity
@EntityListeners(ContentsListener.class)
@Table(name = "contents", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "idx"
        }),
})
public class Contents {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;
	
    @NotBlank
    @Size(max = 200)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String contents;
    
    @NotBlank
    @Size(min=3, max = 50)
    private String userId;
    
    @NotBlank
    @Column
    private String regDtm;
    

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRegDtm() {
		return regDtm;
	}

	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}
}
