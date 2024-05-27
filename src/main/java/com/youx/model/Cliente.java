package com.youx.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Table(name = "tb_cliente")
public class Cliente implements UserDetails {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String login;

	private String senha;

	@NotBlank
	private String nome;

	@NotBlank
	private String email;

	@NotBlank
	private String telefone;

	@NotBlank
	private String uf;

	@NotBlank
	private String localizacao;

	@OneToMany(mappedBy = "cliente", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Venda> vendas = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cli_usuarios_role", uniqueConstraints = @UniqueConstraint(columnNames = { "usuario_id",
			"role_id" }, name = "unique_role_user"), joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "cliente", unique = false, foreignKey = @javax.persistence.ForeignKey(name = "cliente_fk", value = ConstraintMode.CONSTRAINT)), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role", unique = false, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
	private List<Role> roles = new ArrayList<Role>();

	private String token = "";

	@ManyToOne
	private Estado estado;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return roles;
	}

	@JsonIgnore
	@Override
	public String getPassword() {

		return this.senha;
	}

	@JsonIgnore
	@Override
	public String getUsername() {

		return this.login;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {

		return true;
	}

}
