package co.com.coomeva.bank.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "transaction_type", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "trty_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer trtyId;

	
	@Column(name = "enable", nullable = false)
	private String enable;
	
	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transactionType")
	private List<Transaction> transactions = new ArrayList<>();

}