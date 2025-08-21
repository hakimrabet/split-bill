package com.bill.user.model.dong;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bill.user.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups", indexes = { @Index(name = "ux_groups_group_id", columnList = "groupId", unique = true) })
public class Group implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String groupId;

	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_id")
	private User createdBy;

	@ManyToMany
	@JoinTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id_fk"),
			inverseJoinColumns = @JoinColumn(name = "user_id_fk"))
	private List<User> members = new ArrayList<>();

	private String icon;

	private String description;

	@CreatedDate
	@Column(name = "creation_date")
	private Long creationDate;

	@LastModifiedDate
	@Column(name = "last_modification_date")
	private Long lastModificationDate;

	@Version
	private Long version;

	public void addMembers(List<User> users) {
		this.getMembers()
				.addAll(users);
	}

}
