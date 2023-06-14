package co.com.coomeva.bank.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@Entity
@Table(name = "transaction", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "tran_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tranId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acco_id")
	@NotNull
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trty_id")
	@NotNull
	private TransactionType transactionType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_email")
	@NotNull
	private Users users;
	
	@NotNull
	@Column(name = "amount", nullable = false)
	private Double amount;
	
	@NotNull
	@Column(name = "date", nullable = false)
	private Date date;
}
