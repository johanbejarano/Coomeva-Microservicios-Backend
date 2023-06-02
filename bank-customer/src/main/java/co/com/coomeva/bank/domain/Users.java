package co.com.coomeva.bank.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */
@Entity
@Table(name = "users", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_email", unique = true, nullable = false)
	private String userEmail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usty_id")
	private UserType userType;


	@Column(name = "enable", nullable = false)
	private String enable;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "token")
	private String token;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	private List<Transaction> transactions = new ArrayList<>();

}