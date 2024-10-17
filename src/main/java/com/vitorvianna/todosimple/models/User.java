package com.vitorvianna.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity // entidade no banco de dados ou seja tabela
@Table(name = User.TABLE_NAME)
public class User {

  public interface CreateUser {}

  public interface UpdateUser {}

  public static final String TABLE_NAME = "user";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // gerar numeros banco de dados
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "username", length = 100, nullable = false, unique = true)
  @NotNull(groups = CreateUser.class) // não poder ser nulo quando for criar o usuario
  @NotEmpty(groups = CreateUser.class) // não poder ser vazia quando for criar o ususario
  @Size(groups = CreateUser.class, min = 2, max = 100) // length
  private String userName;

  @JsonProperty(access = Access.WRITE_ONLY) // não retorna a senha pro usuario na api
  @Column(name = "password", length = 60, nullable = false)
  @NotNull(groups = {CreateUser.class, UpdateUser.class})
  @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
  @Size(
      groups = {CreateUser.class, UpdateUser.class},
      min = 8,
      max = 60)
  private String password;

  @OneToMany(mappedBy = "user")
  private List<Task> tasks = new ArrayList<Task>();

  public User() {}

  public User(Long id, String userName, String password) {
    this.id = id;
    this.userName = userName;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    User other = (User) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (userName == null) {
      if (other.userName != null) return false;
    } else if (!userName.equals(other.userName)) return false;
    if (password == null) {
      if (other.password != null) return false;
    } else if (!password.equals(other.password)) return false;
    return true;
  }
}
